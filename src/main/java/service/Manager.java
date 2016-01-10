package service;

import java.util.List;

import domain.Client;
import domain.Meal;

public interface Manager {
	
	public void addClient(Client client);
	public void editClient(Client c, int seatNumber, double payment, boolean wine);
	public void deleteClient(Client c);
	public Client getClient(Long idClient);
	public List<Client> getAllClients();
	
	public void addMeal(Meal meal);
	public void editMeal(Meal m, String name, int amount, double price, Client client);
	public void deleteMeal(Meal m);
	public Meal getMeal(Long idMeal);
	public List<Meal> getAllMeals();
	
}
