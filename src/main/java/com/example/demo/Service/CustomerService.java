package com.example.demo.Service;
import java.util.List;
import com.example.demo.Model.Customer;

public interface CustomerService {
	boolean saveCustomer(Customer customer);
	List<Customer> getAllCustomers();
	Customer getCustomer(int id);
	void deleteCustomer(Customer customer);
	void updateCustomer(Customer customer);
	void addCredit(Customer customer);
	String  isPresent(Customer customer);
	void deleteById(Customer customer);
	Customer findByMail(Customer customer);
	void updateCustomerByMail(Customer customer);
	void addReview(Customer customer);
}
