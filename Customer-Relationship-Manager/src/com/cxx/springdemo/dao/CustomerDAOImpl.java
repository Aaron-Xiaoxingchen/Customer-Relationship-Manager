package com.cxx.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cxx.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Customer>  getCustomers() {
		// TODO Auto-generated method stub
		
		//get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		//create a query
		Query<Customer> theQuery = 
				currentSession.createQuery("from Customer order by lastName",Customer.class );
				
		//execute query and get result list
		List<Customer> customers = theQuery.getResultList();
		//return the results
		return customers;
		
	}

	@Override
	public void saveCustomer(Customer theCustomer) {
		// TODO Auto-generated method stub
		
		//get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		//save the customer
		currentSession.saveOrUpdate(theCustomer);
	}

	@Override
	public Customer getCustomer(int theId) {
		// TODO Auto-generated method stub
		
		//get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		//now retrieve/read from database using the primary key
		Customer theCustomer =  currentSession.get(Customer.class, theId);
		
		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theId) {
		// TODO Auto-generated method stub
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query theQuery = currentSession.createQuery("delete from Customer where id = :customerId");
		theQuery.setParameter("customerId", theId);
		
		theQuery.executeUpdate();
		
	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		// TODO Auto-generated method stub
		//get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Customer> theQuery = null;
		
		//only search by name if theSearchName is not empty
		if(theSearchName != null && theSearchName.trim().length()>0) {
			//search for firstName or lastName...case insensitive
			theQuery = currentSession.createQuery("from Customer where lower(firstName)like: theName or lower(lastName) like:theName", Customer.class);
			theQuery.setParameter("theName", "%"+theSearchName.toLowerCase()+"%" );
			}
		else {
			//theSearchName is empty ... so just get all customers
			theQuery = currentSession.createQuery("from Customer", Customer.class);
		}
		//exectute query and get resultlist
		List<Customer> customers = theQuery.getResultList();
		
		//return results
		return customers ;
	}

	
}
