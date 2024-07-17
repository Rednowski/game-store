package pl.edu.wszib.game.store.dao.impl.memory;

import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;
import pl.edu.wszib.game.store.dao.IUserDAO;
import pl.edu.wszib.game.store.exceptions.LoginAlreadyExistsException;
import pl.edu.wszib.game.store.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements IUserDAO {
    private final List<User> users = new ArrayList<>();

    private final IdSequence idSequence;

    public UserRepository(IdSequence idSequence) {
        this.idSequence = idSequence;
        this.users.add(new User(this.idSequence.getId(), "Jan", "Kowalski", "janek",
                DigestUtils.md5DigestAsHex("janek123".getBytes()), User.Role.USER));
        this.users.add(new User(this.idSequence.getId(), "Maciek", "Nowak", "maciek",
                DigestUtils.md5DigestAsHex("maciek123".getBytes()), User.Role.USER));
        this.users.add(new User(this.idSequence.getId(), "Admin", "Admin", "admin",
                DigestUtils.md5DigestAsHex("admin".getBytes()), User.Role.ADMIN));
    }

    @Override
    public Optional<User> getById(final int id) {
        return this.users.stream().filter(user -> user.getId() == id)
                .findAny().map(this::copy);
    }

    @Override
    public Optional<User> getByLogin(final String login) {
        return this.users.stream().filter(user -> user.getLogin().equals(login))
                .findAny().map(this::copy);
    }

    @Override
    public List<User> getAll() {
        return this.users.stream().map(this::copy).toList();
    }

    @Override
    public void save(User user) {
        user.setId(this.idSequence.getId());
        this.getByLogin(user.getLogin()).ifPresent(user1 -> {
            throw new LoginAlreadyExistsException();
        });

        this.users.add(user);
    }

    @Override
    public void remove(final int id) {
        this.users.removeIf(user -> user.getId() == id);
    }

    @Override
    public void update(User user) {
        this.users.stream().filter(user1 -> user1.getId() == user.getId())
                .findAny().ifPresent(user1 -> {
            user1.setName(user.getLogin());
            user1.setRole(user.getRole());
            user1.setSurname(user.getSurname());
            user1.setLogin(user.getLogin());
            user1.setPassword(user.getPassword());
        });
    }

    private User copy(User user) {
        User user1 = new User();
        user1.setId(user.getId());
        user1.setRole(user.getRole());
        user1.setName(user.getName());
        user1.setSurname(user.getSurname());
        user1.setLogin(user.getLogin());
        user1.setPassword(user.getPassword());
        return user1;
    }
}
