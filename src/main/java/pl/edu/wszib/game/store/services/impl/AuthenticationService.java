package pl.edu.wszib.game.store.services.impl;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import pl.edu.wszib.game.store.dao.IUserDAO;
import pl.edu.wszib.game.store.model.User;
import pl.edu.wszib.game.store.services.IAuthenticationService;
import pl.edu.wszib.game.store.session.SessionConstants;

import java.util.HashSet;
import java.util.Optional;

@Service
public class AuthenticationService implements IAuthenticationService {

    private final IUserDAO userDAO;
    private final HttpSession httpSession;

    public AuthenticationService(IUserDAO userDAO, HttpSession httpSession) {
        this.userDAO = userDAO;
        this.httpSession = httpSession;
    }

    @Override
    public void login(String login, String password) {
        Optional<User> user = this.userDAO.getByLogin(login);
        if(user.isPresent() &&
                DigestUtils.md5DigestAsHex(password.getBytes())
                        .equals(user.get().getPassword())) {
            httpSession.setAttribute(SessionConstants.USER_KEY, user.get());
            httpSession.setAttribute(SessionConstants.CART_KEY, new HashSet<>());
            return;
        }
        this.httpSession.setAttribute("loginInfo", "bad login data");
    }

    @Override
    public void register(String login, String password, String name, String surname) {
        Optional<User> user = this.userDAO.getByLogin(login);
        if(user.isPresent()) {
            this.httpSession.setAttribute("loginInfo", "That login is already taken.");
        } else {
            return;
            // TODO walidacja i registeracja
        }

    }

    @Override
    public void logout() {
        this.httpSession.removeAttribute(SessionConstants.USER_KEY);
        this.httpSession.removeAttribute(SessionConstants.CART_KEY);

    }

    @Override
    public String getLoginInfo() {
        String temp = (String) this.httpSession.getAttribute("loginInfo");
        this.httpSession.removeAttribute("loginInfo");
        return temp;
    }
}
