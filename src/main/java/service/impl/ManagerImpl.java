package service.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Client;
import domain.Meal;
import service.Manager;

@Component
@Transactional
public class ManagerImpl implements Manager {

	@Autowired
	private SessionFactory sf;
	
	public SessionFactory getSessionFactory() {
		return sf;
	}
	
	public void setSessionFactory(SessionFactory sf) {
		this.sf = sf;
	}
	
	public Client getClient(int clientId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getAllClients() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addClient(Client client) {
		// TODO Auto-generated method stub
		
	}

	public void editClient(Client client) {
		// TODO Auto-generated method stub
		
	}

	public void deleteClient(int clientId) {
		// TODO Auto-generated method stub
		
	}

	public void addMeal(Meal meal) {
		// TODO Auto-generated method stub
		
	}

	public void editMeal(Meal meal) {
		// TODO Auto-generated method stub
		
	}

	public void deleteMeal(int mealId) {
		// TODO Auto-generated method stub
		
	}

	public Client getMeal(int mealId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Meal> getAllMeals() {
		// TODO Auto-generated method stub
		return null;
	}

}
