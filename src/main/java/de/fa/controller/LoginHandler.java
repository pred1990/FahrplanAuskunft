package de.fa.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import de.fa.model.ClockTime;
import de.fa.model.Route;
import de.fa.model.Station;
import de.fa.model.Trip;
import de.fa.model.User;

/**
 * Backing bean for the login page and the register page.
 * @author Daniel
 *
 */
@ManagedBean
@SessionScoped
public class LoginHandler {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Resource
	private UserTransaction transaction;
	
	private DataModel<User> users;
	
	private String name;
	private String password;
	private String passwordRepeat;
	private Date birthdate;
	private User user;
	
	@PostConstruct
	public void init(){
		try{
			initUsers();
			initRoutes();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Generate and persist default users if absent.
	 * @throws Exception
	 */
	private void initUsers() throws Exception {
		transaction.begin();
		List<?> result = entityManager.createQuery("select k from User k").getResultList();
		if(result.size() <= 0){
			entityManager.persist(new User("msk", "msk", Calendar.getInstance().getTime()));
			System.out.println("Info: creating user: msk");
			entityManager.persist(new User("daniel", "daniel", Calendar.getInstance().getTime()));
			System.out.println("Info: creating user daniel");
		}
		
		users = new ListDataModel<>();
		
		users.setWrappedData(entityManager.createQuery("select k from User k").getResultList());

		transaction.commit();
	}
	
	/**
	 * Generate and persist default routes.
	 * @throws Exception
	 */
	private void initRoutes() throws Exception {
		transaction.begin();
		
		List<?> result = entityManager.createQuery("select k from Station k ").getResultList();
		if(result.size() == 0){
			
			//generate and persist stations
			List<Station> route10 = new ArrayList<Station>();
			List<Station> route2 = new ArrayList<Station>();
			
			String[] baseStations = {
					"Gröpelingen", "Lindenhofstraße", "Moorstraße", "Altenescher Straße",
					"Waller Fiedhof", "Waller Straße", "Waldau-Theater", "Gustavstraße",
					"Utbremer Straße", "Wartburgerstraße", "Hansestraße", "Haferkamp"
			};				
			for(String station : baseStations){
				Station s = new Station(station);
				entityManager.persist(s);
				route10.add(s);
				route2.add(s);
			}
			
			//route 10 extension
			String[] route10Ext = {
					"Doventorsteinweg", "Daniel-von-Büren-Straße", "Falkenstraße", "Hauptbahnhof"
			};
			for(String station : route10Ext){
				Station s = new Station(station);
				entityManager.persist(s);
				route10.add(s);
			}
			
			String[] route2Ext = {
					"Lloydstraße", "Doventor", "Radio Bremen/VHS", "Am Brill"
			};
			for(String station : route2Ext){
				Station s = new Station(station);
				entityManager.persist(s);
				route2.add(s);
			}
			
			//generate and persist routes
			List<Station> route2inv = new ArrayList<Station>(route2);
			List<Station> route10inv = new ArrayList<Station>(route10);
			Collections.reverse(route2inv);
			Collections.reverse(route10inv);
			
			List<Route> routes = new ArrayList<Route>();
			routes.add(new Route("2", "Sebaldsbrück", route2));
			routes.add(new Route("2", "Gröpelingen", route2inv));
			routes.add(new Route("10", "Sebaldsbrück", route10));
			routes.add(new Route("10", "Gröpelingen", route10inv));
			
			for(Route route : routes){
				entityManager.persist(route);
			}
			
			//generate some trips for our routes
			final int startInterval = 12;
			final int timeBetweenStations = 3;
			for(Route route : routes){
				int nextTime = 0;
				while(nextTime < ClockTime.MAX_VALUE){
					List<Integer> times = new ArrayList<Integer>();
					for(int i = 0; i < route.getStations().size(); ++i){
						times.add(nextTime + i * timeBetweenStations);
					}
					
					Trip trip = new Trip();
					trip.setRoute(route);
					trip.setTimes(times);
					entityManager.persist(trip);
					
					nextTime += startInterval;
				}
			}
		}
		transaction.commit();
	}
	
	/**
	 * Logs the user in and redirects to the search page.
	 * @return
	 */
	public String login(){
		Query statement = entityManager.createQuery("SELECT k FROM User k WHERE k.name = :name AND k.password = :password");
		statement.setParameter("name", name);
		statement.setParameter("password", password);
		
		List<?> userList = statement.getResultList();
		if(userList.size() != 1){
			FacesContext.getCurrentInstance().addMessage("login:action", new FacesMessage("Benutzer oder Passwort sind ungültig"));
			return null;
		}
		
		user = (User) userList.get(0);
		return "/search.xhtml?faces-redirect=true";
		
	}
	
	/**
	 * Checks if a user is logged in.
	 */
	public void isLoggedIn(){
		FacesContext context = FacesContext.getCurrentInstance();
		if (user == null) {
			context.getApplication().getNavigationHandler().handleNavigation(context, null, "/index.xhtml?faces-redirect=true");
		}
		
	}
	
	/**
	 * Logs the user out and redirects to the login page.
	 * @return
	 */
	public String logout(){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		user = null;
		return "/index.xhtml?faces-redirect=true";
	}
	
	/**
	 * Registers a new user and redirects to the login page.
	 * @return
	 */
	public String register(){
		if(!password.equals(passwordRepeat)){
			FacesContext.getCurrentInstance().addMessage("register:action", new FacesMessage("Die Passwörter stimmen nicht überein"));
			return null;
		}
		
		try {
			transaction.begin();

			Query query = entityManager.createQuery("select k from User k where name = :name");
			query.setParameter("name", name);
			List<?> entrys = query.getResultList();
			
			if(entrys.size() > 0){
				return null;
			}
			
			User newUser = new User(name, password, birthdate);
			entityManager.persist(newUser);

			transaction.commit();
//			System.out.println("Done");
			return "/index.xhtml";
			
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public DataModel<User> getUsers() {
		return users;
	}

	public void setUsers(DataModel<User> users) {
		this.users = users;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordRepeat() {
		return passwordRepeat;
	}

	public void setPasswordRepeat(String passwordRepeat) {
		this.passwordRepeat = passwordRepeat;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
}
