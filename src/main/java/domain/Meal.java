package domain;

import javax.persistence.*;

@Entity
@NamedQueries({
	@NamedQuery(name = "meal.all", query = "SELECT m FROM Meal m"),
	@NamedQuery(name = "meal.pattern", query = "SELECT m FROM Meal m WHERE m.name LIKE :name")
})
public class Meal {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long idMeal;
	
	private String name;
	private int amount;
	private double price;
	@ManyToOne
	@JoinColumn(name = "idClient")
	private Client client;
	
	public Meal(){
		
	}

	public Meal(long idMeal, String name, int amount, double price, Client client) {
		super();
		this.idMeal = idMeal;
		this.name = name;
		this.amount = amount;
		this.price = price;
		this.client = client;
	}

	public long getIdMeal() {
		return idMeal;
	}

	public void setIdMeal(long idMeal) {
		this.idMeal = idMeal;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
