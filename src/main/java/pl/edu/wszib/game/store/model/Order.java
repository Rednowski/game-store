package pl.edu.wszib.game.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    private int id;
    private LocalDateTime date;
    private Status status;
    private User user;
    private Set<Position> positions = new HashSet<>();
    private double totalSum;



    public enum Status {
        NEW,
        PAID,
        DONE
    }
}
