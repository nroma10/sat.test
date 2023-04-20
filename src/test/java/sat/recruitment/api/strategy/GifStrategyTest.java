package sat.recruitment.api.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import sat.recruitment.api.model.User;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static sat.recruitment.api.utils.Constants.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GifStrategyTest {

    private User user;
    @Autowired
    private GiftContext giftContext;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setName("Prueba");
        user.setAddress("Dir. prueba");
        user.setMoney(101.0);
        user.setUserType(NORMAL);
        user.setPhone("15654678");
        user.setEmail("prueba@gmail.com");
    }

    @Test
    public void testGiftNormalUserMoneyGreater100(){
        user.setMoney(110.0);
        Double gift = giftContext.executeStrategy(user);
        assertThat(user.getMoney() + gift).isEqualTo(123.2);
    }

    @Test
    public void testGiftNormalUserMoneyLess10(){
        user.setMoney(9.0);
        Double gift = giftContext.executeStrategy(user);
        assertThat(user.getMoney() + gift).isEqualTo(user.getMoney());
    }

    @Test
    public void testGiftNormalUserMoneyGreater10Less100(){
        user.setMoney(15.0);
        Double gift = giftContext.executeStrategy(user);
        assertThat(user.getMoney() + gift).isEqualTo(27);
    }

    @Test
    public void testGiftPremiumUserMoneyGreater100(){
        user.setUserType(PREMIUM);
        user.setMoney(110.0);
        Double gift = giftContext.executeStrategy(user);
        assertThat(user.getMoney() + gift).isEqualTo(330.0);
    }

    @Test
    public void testGiftPremiumUserMoneyLess100(){
        user.setUserType(PREMIUM);
        user.setMoney(90.0);
        Double gift = giftContext.executeStrategy(user);
        assertThat(user.getMoney() + gift).isEqualTo(user.getMoney());
    }

    @Test
    public void testGiftSuperUserMoneyGreater100(){
        user.setUserType(SUPER_USER);
        user.setMoney(110.0);
        Double gift = giftContext.executeStrategy(user);
        assertThat(user.getMoney() + gift).isEqualTo(132.0);
    }

    @Test
    public void testGiftSuperUserMoneyLess100(){
        user.setUserType(SUPER_USER);
        user.setMoney(90.0);
        Double gift = giftContext.executeStrategy(user);
        assertThat(user.getMoney() + gift).isEqualTo(user.getMoney());
    }
}
