package com.example.demo.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Service.LoginAuthService;
import com.example.demo.Model.Admin;
import com.example.demo.Model.Customer;
import com.example.demo.Model.Manager;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    LoginAuthService loginAuthService;
    @PostMapping("/auth/customer")
    public Customer auth(@RequestBody Customer customer) {
              return loginAuthService.verifyCustomer(customer);
    }
    @PostMapping("/auth/manager")
    public Manager auth(@RequestBody Manager manager) {
              return loginAuthService.verifyManager(manager);
    }
    @PostMapping("/auth/admin")
    public Admin auth(@RequestBody Admin admin) {
              return loginAuthService.verifyAdmin(admin);
    }
    @PostMapping("/auth/customer/getID")
    public int auth(@RequestBody Map<String,String> authToken){
        return loginAuthService.getCustomerID(authToken);
    }
    @PostMapping("/auth/admin/getID")
    public int authAdmin(@RequestBody Map<String,String> authToken){
        return loginAuthService.getAdminID(authToken);
    }
    @PostMapping("/auth/manager/getID")
    public int authManager(@RequestBody Map<String,String> authToken){
        return loginAuthService.getManagerID(authToken);
    }
}
