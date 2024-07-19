package pl.edu.wszib.game.store.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Position {
    private int id;
    private Game game;
}
