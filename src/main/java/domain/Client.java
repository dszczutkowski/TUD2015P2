package domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@NamedQueries({
	@NamedQuery(name = "client.all", query = "SELECT c FROM Client c"),
})
public class Client {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long idClient;
	private int seatNumber;
	private double payment;
	private boolean wine;

	public Client() {

	}

	public Client(long idClient, int seatNumber, double payment, boolean wine, List<Meal> meals) {
		super();
		this.idClient = idClient;
		this.seatNumber = seatNumber;
		this.payment = payment;
		this.wine = wine;
		this.meals = meals;
	}

	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Meal> meals = new ArrayList<Meal>();

	public long getIdClient() {
		return idClient;
	}

	public void setIdClient(long idClient) {
		this.idClient = idClient;
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	public double getPayment() {
		return payment;
	}

	public void setPayment(double payment) {
		this.payment = payment;
	}

	public boolean isWine() {
		return wine;
	}

	public void setWine(boolean wine) {
		this.wine = wine;
	}

	public List<Meal> getMeals() {
		return meals;
	}

	public void setMeals(List<Meal> meals) {
		this.meals = meals;
	}

}
