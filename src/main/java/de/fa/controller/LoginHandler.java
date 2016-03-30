package de.fa.controller;

import java.util.Calendar;
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

import de.fa.model.User;

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
		try {
			transaction.begin();
			List<?> result = entityManager.createQuery("select k from User k where k.name = 'msk'").getResultList();
			if(result.size() <= 0){
				entityManager.persist(new User("msk", "msk", Calendar.getInstance().getTime()));
				System.out.println("Info: creating user: msk");
			}
			result = entityManager.createQuery("select k from User k where k.name = 'daniel'").getResultList();
			if(result.size() <= 0){
				entityManager.persist(new User("daniel", "daniel", Calendar.getInstance().getTime()));
				System.out.println("Info: creating user daniel");
			}
			
			users = new ListDataModel<>();
			
			users.setWrappedData(entityManager.createQuery("select k from User k").getResultList());

			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
	
	public void isLoggedIn(){
		FacesContext context = FacesContext.getCurrentInstance();
		if (user == null) {
			context.getApplication().getNavigationHandler().handleNavigation(context, null, "/index.xhtml?faces-redirect=true");
		}
		
	}
	
	public String logout(){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		user = null;
		return "/index.xhtml?faces-redirect=true";
	}
	
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
