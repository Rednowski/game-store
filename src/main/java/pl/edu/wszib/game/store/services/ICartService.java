package pl.edu.wszib.game.store.services;

public interface ICartService {
    double calculateTotalSum();
    void addGameToCart(int id);
}
