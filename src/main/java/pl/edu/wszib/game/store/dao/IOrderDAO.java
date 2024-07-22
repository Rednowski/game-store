package pl.edu.wszib.game.store.dao;

import pl.edu.wszib.game.store.model.Order;

import java.util.List;
import java.util.Optional;

public interface IOrderDAO {
    void persist(Order order);
    List<Order> getOrderByUserId(int id);
    Optional<Order> getOrderById(int id);
}
