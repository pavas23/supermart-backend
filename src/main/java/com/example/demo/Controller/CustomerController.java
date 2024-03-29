package com.example.demo.Controller;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Service.CustomerService;
import com.example.demo.Service.HistoryService;
import com.example.demo.Service.OrderService;
import com.example.demo.Service.PlacedOrderService;
import com.example.demo.Service.ProductService;
import com.example.demo.Model.Customer;
import com.example.demo.Model.History;
import com.example.demo.Model.Order;
import com.example.demo.Model.OrderList;
import com.example.demo.Model.Product;
import com.example.demo.Model.PlacedOrder;
import com.example.demo.SpringMail.EmailDetails;
import com.example.demo.SpringMail.EmailService;

class Reset {
    String email;
    int number;
}

@CrossOrigin
@RestController
@RequestMapping("/customer")
public class CustomerController {
    private String resetToken;
    String verifyotp;
    private Customer c;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private ProductService productService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PlacedOrderService POService;

    @PostMapping("/add")
    public boolean add(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @GetMapping("/getAll")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @DeleteMapping("/deleteOne")
    public String deleteCustomer(@RequestBody Customer customer) {
        customerService.deleteCustomer(customer);
        return "Customer has been deleted";
    }

    @PostMapping("/getCustomer")
    public Customer getCustomerDetails(@RequestBody Customer customer) {
        return customerService.getCustomer(customer.getId());
    }

    @PostMapping("/update")
    public String updateCustomerDetails(@RequestBody Customer customer) {
        customerService.updateCustomer(customer);
        return "Customer with id " + customer.getId() + " is updated";
    }

    @PostMapping("/addCredit")
    public String updateCredit(@RequestBody Customer customer) {
        customerService.addCredit(customer);
        return "Rs." + customer.getCredit() + " added.";
    }

    @PostMapping("/history")
    public List<History> getHistory(@RequestBody Customer customer) {
        return historyService.getCustomerHistory(customer.getId());
    }

    @PostMapping(value = "/placeOrder")
    public boolean placeOrder(@RequestBody List<Order> list) {
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        List<History> historyList = new ArrayList<History>();
        int total_cost = 0;
        String orders = "";
        for (Order obj : list) {
            Product p = productService.getProduct(obj.getProductID());
            if (obj.getquantity() > p.getquantity()) return false;
            total_cost += (obj.getPrice() * obj.getquantity());
            if (obj.getquantity() > 0)
                orders += "Product: " + obj.getName() + " Price: \u20B9" + obj.getPrice() + " Quantity Ordered: " + obj.getquantity() + "\n";
        }
        if (list.get(0).isExpress()) total_cost += 100;

        if (customerService.getCustomer(list.get(0).getCustomerID()).getCredit() - total_cost >= 0) {
            Customer updatedCustomer = new Customer();
            updatedCustomer.setId(list.get(0).getCustomerID());
            updatedCustomer.setCredit((customerService.getCustomer(list.get(0).getCustomerID()).getCredit() - total_cost));
            customerService.updateCustomer(updatedCustomer);

            for (Order obj : list) {
                if (obj.getquantity() == 0) continue;
                History h = new History();
                h.setCustomerID(obj.getCustomerID());
                h.setPrice(obj.getPrice());
                h.setProductID(obj.getProductID());
                h.setQuantity(obj.getquantity());
                h.setDate(ldt.format(myFormatObj));
                h.setTotalCost(obj.getPrice() * obj.getquantity());
                h.setAddress(obj.getAddress());
                if (obj.isExpress()) {
                    h.setExpected_date(ldt.plusHours(1).format(myFormatObj));
                    h.setTotalCost(h.getTotalCost() + 100);
                    h.express = obj.isExpress();
                } else {
                    h.setExpected_date(ldt.plusHours(4).format(myFormatObj));
                }
                Product p = productService.getProduct(obj.getProductID());

                p.setquantity(p.getquantity() - obj.getquantity());
                p.setUnits_sold(obj.getquantity());
                productService.saveProduct(p);

                historyList.add(h);
            }
            historyService.addList(historyList);
            OrderList order = new OrderList();
            order.setAddress(list.get(0).getAddress());
            order.setCustomerID(list.get(0).getCustomerID());
            order.setDate(ldt.format(myFormatObj));
            order.setExpress(list.get(0).isExpress());
            order.setTotal_cost(total_cost);
            order.setExpected_date(ldt.plusHours(4).format(myFormatObj));
            order.setName(customerService.getCustomer(list.get(0).getCustomerID()).getName());
            orderService.saveOrder(order);
            EmailDetails ed = new EmailDetails();
            ed.setRecipient(customerService.getCustomer(historyList.get(0).getCustomerID()).getEmail());
            if (list.get(0).isExpress()) {
                ed.setMsgBody("Thank you for shopping with BBB SuperMart.\n\nYour Order summary is as follows\n\n" + orders + "\n\nTotal Bill Amount: \u20B9" + total_cost + "\n\nShipping Type: Express Shipping (+\u20B9100) \n\nExpected Delivery Date: " + historyList.get(0).getExpected_date() + "\n\nDelivery Address: " + historyList.get(0).getAddress());
            } else {
                ed.setMsgBody("Thank you for shopping with BBB SuperMart.\n\nYour Order summary is as follows\n\n" + orders + "\n\nTotal Bill Amount: \u20B9" + total_cost + "\n\nShipping Type: Regular Shipping\\n\\nExpected Delivery Date: " + historyList.get(0).getExpected_date() + "\n\nDelivery Address: " + historyList.get(0).getAddress());
            }
            ed.setSubject("Tax invoice for " + historyList.get(0).getDate());
            emailService.sendSimpleMail(ed);
            return true;
        } else {
            return false;
        }
    }

    @PostMapping("/forgotPassword")
    public String sendResetMail(@RequestBody Customer customer) {
        c = customerService.findByMail(customer);
        if (c.getName() == null) {
            return "Customer DNE";
        }else{
            Random rand = new Random();
            resetToken = Integer.toString((rand.nextInt((99999 - 100) + 1) + 10));
            EmailDetails ed = new EmailDetails();
            ed.setRecipient(customer.getEmail());
            ed.setSubject("BBB SuperMart : Reset Password");
            ed.setMsgBody("Reset Code is : " + resetToken);
            emailService.sendSimpleMail(ed);
            return resetToken;
        }
    }

    @PostMapping("/sendOTP")
    public String sendVerifyOTP(@RequestBody Customer customer) {
        Random rand = new Random();
        verifyotp = Integer.toString((rand.nextInt((99999 - 100) + 1) + 10));
        EmailDetails ed = new EmailDetails();
        ed.setRecipient(customer.getEmail());
        ed.setSubject("BBB SuperMart : Reset Password");
        ed.setMsgBody("Reset Code is : " + verifyotp);
        String emailString = emailService.sendSimpleMail(ed);
        return "Mail with Reset Code sent to id: " + customer.getId() + " with OTP as " + verifyotp;
    }

    @PostMapping("/reset")
    public boolean resetPassword(@RequestBody Customer customer) {
        Customer c = customerService.findByMail(customer);
        c.setPassword(customer.getPassword());
        customerService.updateCustomer(c);
        return true;
    }

    @PostMapping("/verify")
    public void verifyCustomer(@RequestBody Customer customer) {
        Customer c = customerService.findByMail(customer);
        c.setVerify(customer.isVerify());
        customerService.updateCustomer(c);
    }

    @PostMapping("/getCart")
    public List<PlacedOrder> getCartElements(@RequestBody Integer id) {
        return POService.getCart(id);
    }

    @DeleteMapping("/deleteCart")
    public String deleteCart() {
        POService.deleteCart();
        return "deleted products";
    }

    @PostMapping("/setCart")
    public String setCart(@RequestBody List<PlacedOrder> list) {
        POService.setCart(list);
        return "product added to cart";
    }

    @PostMapping("/deleteCartOne")
    public String deleteCartById(@RequestBody PlacedOrder PO) {
        POService.deleteOneItem(PO);
        return "Deleted";
    }

    @PostMapping("/sendOrder")
    public boolean sendOrder(@RequestBody PlacedOrder PO) {
        String orders = "";
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        int customerCredit = customerService.getCustomer(PO.getCustomerID()).getCredit();
        int totalCost = PO.getTotalCost();
//        if (PO.isExpress()) totalCost += 100; // added this is frontend
//        if(totalCost > customerCredit) return false; otherwise giving error, added check in client side
        Customer updatedCustomer = customerService.getCustomer(PO.getCustomerID());
        updatedCustomer.setId(PO.getCustomerID());
        updatedCustomer.setCredit((customerService.getCustomer(PO.getCustomerID()).getCredit() - totalCost));
        customerService.updateCustomer(updatedCustomer);
        List<History> historyList = new ArrayList<>();
        for (Product obj : PO.getProductList()) {
            History history = new History();
            history.setCustomerID(PO.getCustomerID());
            history.setProductID(obj.getId());
            history.express = PO.isExpress();
            history.setAddress(PO.getAddress());
            history.setDate(ldt.format(myFormatObj));
            history.setQuantity(obj.getquantity());
            history.setPrice(obj.getPrice());
            history.setTotalCost(obj.getPrice() * obj.getquantity());
            orders += "Product: " + obj.getName() + " Price: \u20B9" + obj.getPrice() + " Quantity Ordered: " + obj.getquantity() + "\n";
            Product p = productService.getProduct(obj.getId());
            p.setquantity(p.getquantity() - obj.getquantity());
            p.setUnits_sold(obj.getquantity());
            productService.saveProduct(p);
            if (PO.isExpress()) history.setExpected_date(ldt.plusHours(1).format(myFormatObj));
            else history.setExpected_date(ldt.plusHours(3).format(myFormatObj));
            historyList.add(history);
        }
        historyService.addList(historyList);
        OrderList order = new OrderList();
        order.setAddress(PO.getAddress());
        order.setCustomerID(PO.getCustomerID());
        order.setDate(ldt.format(myFormatObj));
        order.setExpress(PO.isExpress());
        order.setTotal_cost(totalCost);
        order.setExpected_date(ldt.plusHours(4).format(myFormatObj));
        order.setName(customerService.getCustomer(PO.getCustomerID()).getName());
        orderService.saveOrder(order);
        EmailDetails ed = new EmailDetails();
        ed.setRecipient(updatedCustomer.getEmail());
        if (PO.isExpress()) {
            ed.setMsgBody("Thank you for shopping with BBB SuperMart.\n\nYour Order summary is as follows\n\n" + orders + "\n\nTotal Bill Amount: \u20B9" + totalCost + "\n\nShipping Type: Express Shipping (+\u20B9100) \n\nExpected Delivery Date: " + historyList.get(0).getExpected_date() + "\n\nDelivery Address: " + historyList.get(0).getAddress());
        }else {
            ed.setMsgBody("Thank you for shopping with BBB SuperMart.\n\nYour Order summary is as follows\n\n" + orders + "\n\nTotal Bill Amount: \u20B9" + totalCost + "\n\nShipping Type: Regular Shipping\n\nExpected Delivery Date: " + historyList.get(0).getExpected_date() + "\n\nDelivery Address: " + historyList.get(0).getAddress());
        }
        ed.setSubject("Tax invoice for " + historyList.get(0).getDate());
        emailService.sendSimpleMail(ed);
        return true;
    }

    @PostMapping("/addReview")
    public void addReview(@RequestBody Customer customer){
        customerService.addReview(customer);
    }
}
