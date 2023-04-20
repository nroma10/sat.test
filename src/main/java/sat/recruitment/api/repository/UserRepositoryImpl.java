package sat.recruitment.api.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import sat.recruitment.api.model.User;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository {
    @Value("${db.file.path}")
    String dbFilePath;
    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Path.of(dbFilePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                User user = createUserFromLine(line);
                users.add(user);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return users;
    }

    public Optional<User> existsUserByNameOrPhoneOrByNameAndAddress(User user){
        Optional<User> foundUser = getUsers().stream()
                .filter(u -> u.getName().equals(user.getName())
                        || u.getPhone().equals(user.getPhone())
                        || (u.getName().equals(user.getName()) && u.getAddress().equals(user.getAddress())))
                .findFirst();
        return foundUser;
    }

    @Override
    public void save(User user) {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(dbFilePath), StandardOpenOption.APPEND)) {
            bw.write(System.lineSeparator() + user.toString());
        } catch (IOException e) {
            log.error("Error al escribir en el archivo: " + e.getMessage(), e);
        }
    }
    private User createUserFromLine(String line) {
        String[] parts = line.split(",");
        User user = new User();
        user.setName(parts[0]);
        user.setEmail(parts[1]);
        user.setPhone(parts[2]);
        user.setAddress(parts[3]);
        user.setUserType(parts[4]);
        user.setMoney(Double.valueOf(parts[5]));

        return user;
    }
}
