package pl.edu.wszib.game.store.model;

import lombok.*;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Game {
    private int id;
    private String title;
    private String publisher;
    private double price;
    private String tags;
    private String description;
    private String picture;

    public List<String> getTagsList() {
        return Arrays.asList(tags.split(", "));
    }
}

