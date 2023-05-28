package com.example.demo.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Model.PlacedOrder;
import com.example.demo.Repository.PlacedOrderRepository;
@Service
public class PlacedOrderServiceImpl implements PlacedOrderService{
@Autowired PlacedOrderRepository POrepo;
    @Override
    public List<PlacedOrder> getCart(int id) {
        return POrepo.getByCustomerID(id);
    }

    @Override
    public void deleteCart() {
     POrepo.deleteAll();
    }

    @Override
    public void setCart(List<PlacedOrder> list) {
        POrepo.saveAll(list);
    }

    @Override
    public void deleteOneItem(PlacedOrder cart){
        POrepo.deleteById(cart.getId());
    }
}
