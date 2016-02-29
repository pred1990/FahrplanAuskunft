package de.fa.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
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
	private User user;
	
	@PostConstruct
	public void init(){
		try {
			transaction.begin();
			
			entityManager.persist(new User("msk", "msk"));
			entityManager.persist(new User("Daniel", "daniel"));
			
			users = new ListDataModel<>();
			
			users.setWrappedData(entityManager.createQuery("select k from User k").getResultList());
			transaction.commit();
		} catch (Exception e) {
			//To many exceptions
			e.printStackTrace();
		}
	}
	
	public String login(){
		
		Query statement = entityManager.createQuery("SELECT k FROM User k WHERE k.name = :name AND k.password = :password");
		statement.setParameter("name", name);
		statement.setParameter("password", password);

		List<User> userList = statement.getResultList();
		if(userList.size() == 1) {
			user = userList.get(0);
			return "/LoggedIn.xhtml";
		} else {
			return null;
		}
		
	}
	
	public void isLoggedIn(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		if (user == null) {
			context.getApplication().getNavigationHandler().handleNavigation(context, null, "/index.xhtml?faces-redirect=true");
		}
		
	}
	
	public void logout(){
		user = null;
		
	}
	
	public String register(){
		if(password.equals(passwordRepeat)){
			
			try {
				transaction.begin();

				Query query = entityManager.createQuery("select k from User k where name = :name");
				
				query.setParameter("name", name);

				List<User> entrys = query.getResultList();
				
				if(entrys.size() > 0){
					return null;
				}
					

				User newUser = new User(name, password);
				entityManager.persist(newUser);

				transaction.commit();
				System.out.println("Done");
				return "/index.xhtml";
			}  catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else {
			FacesContext.getCurrentInstance().addMessage("register:reg", new FacesMessage("Passwort stimmt nicht überein"));
			
			return null;
		}
		
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
	
	
	

}
