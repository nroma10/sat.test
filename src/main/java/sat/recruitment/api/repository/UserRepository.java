package sat.recruitment.api.repository;

import sat.recruitment.api.model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> getUsers();
    public Optional<User> existsUserByNameOrPhoneOrByNameAndAddress(User user);
    void save(User user);
}
