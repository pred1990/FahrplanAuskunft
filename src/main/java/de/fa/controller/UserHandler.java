package de.fa.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import de.fa.model.User;

@ManagedBean
@SessionScoped
public class UserHandler {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Resource
	private UserTransaction transaction;
	
	private DataModel<User> users;
	
	private User user = new User();
	
	@PostConstruct
	public void init(){
		try {
			transaction.begin();
			
			entityManager.persist(new User("msk", "msk"));
			entityManager.persist(new User("Daniel", "daniel"));
			
			users = new ListDataModel<>();
			
			
			List<User> test = entityManager.createQuery("select k from User k").getResultList();
			System.out.println(test.size());
			
			users.setWrappedData(test);
			transaction.commit();
		} catch (Exception e) {
			//To many exceptions
			e.printStackTrace();
		}
	}
	
	public String login(){
		
		Query statement = entityManager.createQuery("SELECT k FROM User k WHERE k.user = :user AND k.password = :password");
		statement.setParameter("user", user.getName());
		statement.setParameter("password", user.getPassword());

		List<User> userList = statement.getResultList();
		if(userList.size() == 1) {
			user = userList.get(0);
			return "/LoggedIn.xhtml";
		} else {
			return "/LoggedOut.xhtml";
		}
		
	}
	
	public void isLoggedIn(){
		
		
	}
	
	public void Logout(){
		
		
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
	
	

}
