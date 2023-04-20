package sat.recruitment.api.strategy;

import org.springframework.stereotype.Component;
import sat.recruitment.api.model.User;

@Component
public class PremiumGiftStrategy implements GiftStrategy{
    @Override
    public Double calculateGift(User user) {
        Double money = Double.valueOf(user.getMoney());
        Double gift = 0.0;
        if (money > 100) {
            gift = money * 2;
        }
        return gift;
    }
}
