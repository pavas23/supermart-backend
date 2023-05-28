package com.example.demo.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Model.OrderList;
import com.example.demo.Repository.OrderRepository;
@Service
public class OrderServiceImpl implements OrderService{
@Autowired
OrderRepository orderRepo; 
    @Override
    public List<OrderList> getAllOrders() {
        return orderRepo.findAll();
    }
    @Override
    public OrderList saveOrder(OrderList order) {
        return orderRepo.save(order);
    }
}
