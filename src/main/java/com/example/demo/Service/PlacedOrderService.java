package com.example.demo.Service;
import java.util.List;

import com.example.demo.Model.PlacedOrder;

public interface PlacedOrderService {
    public List<PlacedOrder> getCart(int id);
    public void deleteCart();
    public void setCart(List<PlacedOrder> list);
    public void deleteOneItem(PlacedOrder cart);
}
