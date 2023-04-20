package sat.recruitment.api.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import sat.recruitment.api.model.User;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import static org.junit.jupiter.api.Assertions.*;
import static sat.recruitment.api.utils.Constants.NORMAL;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRepositoryTest {
    private User user;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setName("Juan");
        user.setEmail("prueba@gmail.com");
        user.setAddress("Peru 2464");
        user.setPhone("15654678");
        user.setUserType(NORMAL);
        user.setMoney(100.0);
    }

    @Test
    public void testGetUsers() {
        List<User> actualUsers = userRepository.getUsers();
        assertFalse(actualUsers.isEmpty());
        assertEquals(actualUsers.get(0).getName(), "Juan");
        assertEquals(actualUsers.get(0).getAddress(), "Peru 2464");
        assertEquals(actualUsers.get(0).getEmail(), "Juan@marmol.com");
        assertEquals(actualUsers.get(0).getPhone(), "+5491154762312");
        assertEquals(actualUsers.get(0).getUserType(), "Normal");
        assertEquals(actualUsers.get(0).getMoney(), 1234.0);
    }

    @Test
    public void testExistsUserByNameAndAddress() {
        //user con name y address, existente.
        Optional<User> foundUser = userRepository.existsUserByNameOrPhoneOrByNameAndAddress(user);

        // Verifica si el usuario fue encontrado correctamente
        assertEquals(user.getName(), foundUser.get().getName());
        assertEquals(user.getAddress(), foundUser.get().getAddress());
    }

    @Test
    public void testExistsUserByNameOrPhone() {
        user.setPhone("0000000000000");
        //user con name=Juan, existente.
        Optional<User> foundUserByName = userRepository.existsUserByNameOrPhoneOrByNameAndAddress(user);
        assertEquals(user.getName(), foundUserByName.get().getName());

        user.setName("Nombre inexistente");
        user.setPhone("+5491154762312");
        //User con phone=+5491154762312, existente.
        Optional<User> foundUserByPhone = userRepository.existsUserByNameOrPhoneOrByNameAndAddress(user);
        assertEquals(user.getPhone(), foundUserByPhone.get().getPhone());
    }
}
