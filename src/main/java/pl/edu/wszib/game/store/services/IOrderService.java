package pl.edu.wszib.game.store.services;

import pl.edu.wszib.game.store.model.Order;

import java.util.List;

public interface IOrderService {
    public void confirmOrder();
    List<Order> getOrdersForCurrentUser();
}
