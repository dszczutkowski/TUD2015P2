package service;

import java.util.List;

import domain.Client;
import domain.Meal;

public interface Manager {
	
	public void addClient(Client client);
	public void editClient(Client client);
	public void deleteClient(int clientId);
	public Client getClient(int clientId);
	public List<Client> getAllClients();
	
	public void addMeal(Meal meal);
	public void editMeal(Meal meal);
	public void deleteMeal(int mealId);
	public Client getMeal(int mealId);
	public List<Meal> getAllMeals();
	
}
