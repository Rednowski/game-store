package pl.edu.wszib.game.store.services;

public interface IAuthenticationService {

    void login(String login, String password);
    void register(String login, String password, String name, String surname);
    void logout();
    String getLoginInfo();
}
