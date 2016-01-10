package projekt2;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import domain.Client;
import domain.Meal;
import service.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class ManagerTest {

	@Autowired
	Manager manager;
	
	private final static int SEAT_1 = 1;
	private final static double PAYMENT_1 = 350;
	private final static boolean WINE_1 = true;
	
	private final static String NAME_1 = "Salmon";
	private final static int AMOUNT_1 = 2; 
	private final static double PRICE_1 = 35;
	
	private final static int SEAT_2 = 3;
	private final static double PAYMENT_2 = 70;
	private final static boolean WINE_2 = false;
	
	private final static String NAME_2 = "Beef Steak";
	private final static int AMOUNT_2 = 1; 
	private final static double PRICE_2 = 60;
	
	private List<Long> addedClients = new ArrayList<Long>();
	private List<Long> addedMeals	= new ArrayList<Long>();
	
	@Before
	public void checkAddedData() {
		
		 List<Client> clients = manager.getAllClients();
		 List<Meal> meals = manager.getAllMeals();
		 
		 for(Client client : clients)
			 addedClients.add(client.getIdClient());
		
		 for(Meal meal : meals)
			 addedMeals.add(meal.getIdMeal());
		 
	}
	
	@After
	public void deleteTestedData() {
		
		List<Client> clients = manager.getAllClients();
		List<Meal> meals = manager.getAllMeals();
		
		boolean del;
		
		for(Client client : clients) {
			del = true;
			for(Long client2 : addedClients) {
				if(client.getIdClient() == client2 || client.getIdClient() == 0) {
					del = false;
					break;
				}
			if(del)
				manager.deleteClient(client);			}
		}
		
		for(Meal meal : meals) {
			del = true;
			for(Long meal2 : addedMeals) {
				if(meal.getIdMeal() == meal2 || meal.getIdMeal() == 0) {
					del = false;
					break;
				}
			if(del)
				manager.deleteMeal(meal);			}
		}
	}
	
	@Test
	public void testGetById() {
		
		Client c = new Client();
		c.setSeatNumber(SEAT_1);
		c.setPayment(PAYMENT_1);
		c.setWine(WINE_1);
		manager.addClient(c);
		Long idClient = c.getIdClient();
		Client getClient = manager.getClient(idClient);
		
		assertEquals(c.getIdClient(), getClient.getIdClient());
	
		Meal m = new Meal();
		m.setName(NAME_1);
		m.setAmount(AMOUNT_1);
		m.setPrice(PRICE_1);
		m.setClient(c);
		manager.addMeal(m);
		Long idMeal = m.getIdMeal();
		Meal getMeal = manager.getMeal(idMeal);
		
		assertEquals(m.getIdMeal(), getMeal.getIdMeal());
		
	}
	
	@Test
	public void testAdd() {
		
		Client c = new Client();
		c.setSeatNumber(SEAT_1);
		c.setPayment(PAYMENT_1);
		c.setWine(WINE_1);
		manager.addClient(c);
		Long idClient = c.getIdClient();
		Client getClient = manager.getClient(idClient);
		
		assertEquals(c.getIdClient(), getClient.getIdClient());
		assertEquals(SEAT_1, getClient.getSeatNumber());
		assertEquals(PAYMENT_1, getClient.getPayment(), 0);
		assertEquals(WINE_1, getClient.isWine());
	
		Meal m = new Meal();
		m.setName(NAME_1);
		m.setAmount(AMOUNT_1);
		m.setPrice(PRICE_1);
		m.setClient(c);
		manager.addMeal(m);
		Long idMeal = m.getIdMeal();
		Meal getMeal = manager.getMeal(idMeal);
		
		assertEquals(m.getIdMeal(), getMeal.getIdMeal());
		assertEquals(c.getSeatNumber(), getMeal.getClient().getSeatNumber());
		assertEquals(c.getPayment(), getMeal.getClient().getPayment(), 0);
		assertEquals(c.isWine(), getMeal.getClient().isWine());
		assertEquals(NAME_1, getMeal.getName());
		assertEquals(AMOUNT_1, getMeal.getAmount());
		assertEquals(PRICE_1, getMeal.getPrice(), 0);
	
	}
	
	@Test
	public void testDelete() {
		
		Client c = new Client();
		c.setSeatNumber(SEAT_1);
		c.setPayment(PAYMENT_1);
		c.setWine(WINE_1);
		manager.addClient(c);
		Long idClient = c.getIdClient();
		List<Client>clients = manager.getAllClients();
		
		Meal m = new Meal();
		m.setName(NAME_1);
		m.setAmount(AMOUNT_1);
		m.setPrice(PRICE_1);
		m.setClient(c);
		manager.addMeal(m);
		Long idMeal = m.getIdMeal();
		List<Meal>meals = manager.getAllMeals();
		
		manager.deleteMeal(m);
		Meal getMeal = manager.getMeal(idMeal);
		assertNull(getMeal);
		
		manager.deleteClient(c);
		Client getClient = manager.getClient(idClient);
		assertNull(getClient);
		
		List<Client>clientsAfter = manager.getAllClients();
		List<Meal>mealsAfter = manager.getAllMeals();
		
		assertEquals(clientsAfter.size(), clients.size()-1);
		assertEquals(mealsAfter.size(), meals.size()-1);
		
	}
	
	@Test
	public void testCascadeDelete() {
		
		Client c = new Client();
		c.setSeatNumber(SEAT_1);
		c.setPayment(PAYMENT_1);
		c.setWine(WINE_1);
		manager.addClient(c);
		Long idClient = c.getIdClient();
		List<Client>clients = manager.getAllClients();
		
		Meal m = new Meal();
		m.setName(NAME_1);
		m.setAmount(AMOUNT_1);
		m.setPrice(PRICE_1);
		m.setClient(c);
		manager.addMeal(m);
		Long idMeal = m.getIdMeal();
		List<Meal>meals = manager.getAllMeals();
		
		manager.deleteClient(c);
		Client getClient = manager.getClient(idClient);
		Meal getMeal = manager.getMeal(idMeal);
		assertNull(getClient);
		assertNull(getMeal);
		
		List<Client>clientsAfter = manager.getAllClients();
		List<Meal>mealsAfter = manager.getAllMeals();
		
		assertEquals(clientsAfter.size(), clients.size()-1);
		assertEquals(mealsAfter.size(), meals.size()-1);
		
	}
	
	@Test
	public void testGetAll() {
		
		Client c = new Client();
		c.setSeatNumber(SEAT_1);
		c.setPayment(PAYMENT_1);
		c.setWine(WINE_1);
		manager.addClient(c);
		List<Client>clients = manager.getAllClients();
		
		Meal m = new Meal();
		m.setName(NAME_1);
		m.setAmount(AMOUNT_1);
		m.setPrice(PRICE_1);
		m.setClient(c);
		manager.addMeal(m);
		List<Meal>meals = manager.getAllMeals();
		
		for(Client client : clients) {
			c = manager.getClient(client.getIdClient());
			assertNotNull(c);
		}
		
		for(Meal meal : meals) {
			m = manager.getMeal(meal.getIdMeal());
			assertNotNull(m);
		}
		
	}
	
	@Test
	public void testEdit() {
		
		Client c = new Client();
		c.setSeatNumber(SEAT_1);
		c.setPayment(PAYMENT_1);
		c.setWine(WINE_1);
		manager.addClient(c);
		List<Client>clients = manager.getAllClients();
		
		Meal m = new Meal();
		m.setName(NAME_1);
		m.setAmount(AMOUNT_1);
		m.setPrice(PRICE_1);
		m.setClient(c);
		manager.addMeal(m);
		List<Meal>meals = manager.getAllMeals();
		
		manager.editClient(c, SEAT_2, PAYMENT_2, WINE_2);
		manager.editMeal(m, NAME_2, AMOUNT_2, PRICE_2, c);

		List<Meal>meals2 = manager.getAllMeals();
		List<Client>clients2 = manager.getAllClients();
		
		assertEquals(clients.size(), clients2.size());
		assertEquals(meals.size(), meals2.size());
		
		int ne = 0;
		int e = 0;
		
		for(Client client : clients) {
			for(Client client2 : clients2) {
				if(client.getIdClient() == client2.getIdClient()) {
					if(client.getIdClient() != c.getIdClient()) {
						assertEquals(clients2.get(ne).getSeatNumber(), client.getSeatNumber());
						assertEquals(clients2.get(ne).getPayment(), client.getPayment(), 0);
						assertEquals(clients2.get(ne).isWine(), client.isWine());
						ne++;
					} else if(client.getIdClient() == c.getIdClient()) {
						assertEquals(SEAT_2, client.getSeatNumber());
						assertEquals(PAYMENT_2, client.getPayment(), 0);
						assertEquals(WINE_2, client.isWine());
						e++;
					}
				}
			}
		}
		
		assertEquals(e, 1);
		assertEquals(e+ne, clients2.size());
		assertEquals(clients.size(), clients2.size());
		
		ne = 0;
		e = 0;
		
		for(Meal meal : meals) {
			for(Meal meal2 : meals2) {
				if(meal.getIdMeal() == meal2.getIdMeal()) {
					if(meal.getIdMeal() != m.getIdMeal()) {
						assertEquals(meals2.get(ne).getName(), meal.getName());
						assertEquals(meals2.get(ne).getAmount(), meal.getAmount());
						assertEquals(meals2.get(ne).getPrice(), meal.getPrice(), 0);
						assertEquals(meals2.get(ne).getClient().getSeatNumber(), meal.getClient().getSeatNumber());
						assertEquals(meals2.get(ne).getClient().getPayment(), meal.getClient().getPayment(), 0);
						assertEquals(meals2.get(ne).getClient().isWine(), meal.getClient().isWine());
						ne++;
					} else if(meal.getIdMeal() == m.getIdMeal()) {
						assertEquals(NAME_2, meal.getName());
						assertEquals(AMOUNT_2, meal.getAmount());
						assertEquals(PRICE_2, meal.getPrice(), 0);
						assertEquals(SEAT_2, meal.getClient().getSeatNumber());
						assertEquals(PAYMENT_2, meal.getClient().getPayment(), 0);
						assertEquals(WINE_2, meal.getClient().isWine());
						e++;
					}
				}
			}
		}
		
		assertEquals(e, 1);
		assertEquals(e+ne, meals2.size());
		assertEquals(meals.size(), meals2.size());
		
	}
}
