package service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class connectionTest {

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("projekt");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		entityManager.close();
		entityManagerFactory.close();
	}

}
