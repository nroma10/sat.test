package sat.recruitment.api.strategy;

import org.springframework.stereotype.Component;
import sat.recruitment.api.model.User;

@Component
public interface GiftStrategy {
    public Double calculateGift(User user);
}
