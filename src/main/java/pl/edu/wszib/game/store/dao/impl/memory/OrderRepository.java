package pl.edu.wszib.game.store.dao.impl.memory;

import org.springframework.stereotype.Repository;
import pl.edu.wszib.game.store.dao.IOrderDAO;
import pl.edu.wszib.game.store.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository implements IOrderDAO {

    private final List<Order> orders = new ArrayList<>();

    private final IdSequence idSequence;

    public OrderRepository(IdSequence idSequence) {
        this.idSequence = idSequence;
    }

    @Override
    public void persist(Order order) {
        order.setId(this.idSequence.getId());
        this.orders.add(order);
    }

    @Override
    public List<Order> getOrderByUserId(final int id) {
        return this.orders.stream().filter(order -> order.getUser().getId() == id).toList();
    }

    @Override
    public Optional<Order> getOrderById(final int id) {
        return this.orders.stream().filter(order -> order.getId() == id).findFirst();
    }
}
