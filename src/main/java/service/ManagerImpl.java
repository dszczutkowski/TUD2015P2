package service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Client;
import domain.Meal;

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

	@Override
	public void addClient(Client client) {
		Long id = (Long) sf.getCurrentSession().save(client);
		client.setIdClient(id);
	}

	@Override
	public void editClient(Client c, int seatNumber, double payment, boolean wine) {
		c = (Client) sf.getCurrentSession().get(Client.class, c.getIdClient());
		c.setSeatNumber(seatNumber);
		c.setPayment(payment);
		c.setWine(wine);
		sf.getCurrentSession().update(c);
	}

	@Override
	public void deleteClient(Client c) {
		c = (Client) sf.getCurrentSession().get(Client.class, c.getIdClient());
		sf.getCurrentSession().delete(c);
	}

	@Override
	public Client getClient(Long idClient) {
		return (Client) sf.getCurrentSession().get(Client.class, idClient);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Client> getAllClients() {
		return sf.getCurrentSession().getNamedQuery("client.all").list();
	}

	@Override
	public void addMeal(Meal meal) {
		Long id = (Long) sf.getCurrentSession().save(meal);
		meal.setIdMeal(id);
		Client client = (Client) sf.getCurrentSession().get(Client.class, meal.getClient().getIdClient());
		client.getMeals().add(meal);
	}

	@Override
	public void editMeal(Meal m, String name, int amount, double price, Client client) {
		m = (Meal) sf.getCurrentSession().get(Meal.class, m.getIdMeal());
		Client c = (Client) sf.getCurrentSession().get(Client.class, m.getClient().getIdClient());
		int i = 0;
		for(Meal meal : c.getMeals()) {
			if(meal == m)
				break;
			i++;
		}
		m.setName(name);
		m.setAmount(amount);
		m.setPrice(price);
		m.setClient(client);
		c.getMeals().set(i, m);
		sf.getCurrentSession().update(m);
	}

	@Override
	public void deleteMeal(Meal m) {
		m = (Meal) sf.getCurrentSession().get(Meal.class, m.getIdMeal());
		Client c = (Client) sf.getCurrentSession().get(Client.class, m.getClient().getIdClient());
		c.getMeals().remove(m);
		sf.getCurrentSession().delete(m);
	}

	@Override
	public Meal getMeal(Long idMeal) {
		return (Meal) sf.getCurrentSession().get(Meal.class, idMeal);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Meal> getAllMeals() {
		return sf.getCurrentSession().getNamedQuery("meal.all").list();
	}

	@Override
	public List<Meal> getMealByPattern(String name) {
		List<Meal> meals = new ArrayList<Meal>();
		Pattern p = Pattern.compile(".*" + name + ".*");
		Matcher match;
		for(Meal m : getAllMeals())
		{
			match = p.matcher(m.getName());
			if(match.matches())
				meals.add(m);
		}
		return meals;
	}

	@Override
	public List<Meal> getClientWithMeal(Client c) {
		List<Meal> all = getAllMeals();
		List<Meal> mc = new ArrayList<Meal>();
		for(Meal m : all)
			if(m.getClient().getIdClient() == c.getIdClient())
				mc.add(m);
		
		return mc;
	}
	
}
