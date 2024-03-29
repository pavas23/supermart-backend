package com.example.demo.Controller;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Service.CustomerService;
import com.example.demo.Service.HistoryService;
import com.example.demo.Service.ManagerService;
import com.example.demo.Service.OrderService;
import com.example.demo.Service.ProductService;
import com.example.demo.Service.AdminService;
import com.example.demo.Model.Customer;
import com.example.demo.Model.History;
import com.example.demo.Model.Manager;
import com.example.demo.Model.OrderList;
import com.example.demo.Model.Product;
import com.example.demo.Model.Admin;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    ProductService productService;
    @Autowired
    ManagerService managerService;
    @Autowired
    CustomerService customerService;
    @Autowired
    HistoryService historyService;
    @Autowired
    OrderService orderService;

    @Autowired
    AdminService adminService;

    @PostMapping("/getAdmin")
    public Admin getAdminDetails(@RequestBody Admin admin) {
        return adminService.getAdmin(admin.getId());
    }

    @PostMapping("/add")
    public boolean saveAdmin(@RequestBody Admin admin){
        return adminService.saveAdmin(admin);
    }

    @GetMapping("/allCustomers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();

    }

    @DeleteMapping("/deleteCustomer")
    public String deleteCustomer(@RequestBody Customer customer) {
        customerService.deleteById(customer);
        return "Customer has been deleted";
    }

    @GetMapping("/getCustomer")
    public Customer getCustomer(@RequestBody Customer customer) {
        return customerService.getCustomer(customer.getId());
    }

    @GetMapping("/customerHistory")
    public List<History> getCustomerHistory(@RequestBody Customer customer) {
        return historyService.getCustomerHistory(customer.getId());
    }

    @PostMapping("/addManager")
    public boolean addManager(@RequestBody Manager manager) {
        return managerService.saveManager(manager);
    }

    @GetMapping("/allManagers")
    public List<Manager> getAllManagers() {
        return managerService.getAllManagers();
    }

    @GetMapping("/getManager")
    public Manager getCustomerManager(@RequestBody Manager manager) {
        return managerService.getManager(manager.getId());
    }

    @PostMapping("/updateManager")
    public void updateManager(@RequestBody Manager manager) {
        managerService.updateManager(manager);
    }

    @GetMapping("/allProducts")
    public List<Product> getAllCProducts() {
        return productService.getAllProducts();

    }

    @DeleteMapping("/deleteManager")
    public void deleteManager(@RequestBody Manager manager) {
        managerService.deleteManager(manager);
    }

    @DeleteMapping("/deleteProduct")
    public String deleteProduct(@RequestBody Product product) {
        productService.deleteProduct(product);
        return "Product has been deleted";
    }

    @GetMapping("/getProduct")
    public Product getProduct(@RequestBody Product product) {
        return productService.getProduct(product.getId());
    }

    @PostMapping("/updateProduct")
    public void updateProduct(@RequestBody Product product) {
        productService.updateProduct(product);
    }

    @GetMapping("/monthlyReport")
    public List<History> getMonthlyReport(@RequestBody Integer month) {
        return historyService.monthlyReportGenerator(month);
    }

    @GetMapping("/totalRevenue")
    public int totalRevenue() {
        int revenue = 0;
        List<History> history = historyService.getFullHistory();
        for (History obj : history) {
            revenue += obj.getTotalCost();
        }
        return revenue;
    }

    @GetMapping("/monthlyRevenue")
    public int monthlyRevenue(@RequestBody Integer month) {
        int revenue = 0;
        List<History> history = historyService.monthlyReportGenerator(month);
        for (History obj : history) {
            revenue += obj.getTotalCost();
        }
        return revenue;
    }

    @GetMapping("/orders")
    public List<OrderList> getOrders() {
        List<OrderList> list = orderService.getAllOrders();
        System.out.println(list.toString());
        Collections.reverse(list);
        return list;
    }
}
