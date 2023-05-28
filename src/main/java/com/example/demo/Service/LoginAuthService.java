package com.example.demo.Service;

import com.example.demo.Model.Admin;
import com.example.demo.Model.Customer;
import com.example.demo.Model.Manager;
import java.util.Map;

public interface LoginAuthService {
    public Customer verifyCustomer(Customer customer);
    public Manager verifyManager(Manager manager);
    public Admin verifyAdmin(Admin admin);
    public int getCustomerID(Map<String,String> authToken);
}
