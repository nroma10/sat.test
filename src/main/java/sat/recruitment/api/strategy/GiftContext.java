package sat.recruitment.api.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sat.recruitment.api.model.User;
import java.util.HashMap;
import java.util.Map;
import static sat.recruitment.api.utils.Constants.*;

@Component
public class GiftContext {
    private Map<String, GiftStrategy> strategyContext;

    public GiftContext(@Autowired NormalGiftStrategy normalGiftStrategy, @Autowired PremiumGiftStrategy premiumGiftStrategy,
                            @Autowired SuperUserGiftStrategy superUserGiftStrategy){
        this.strategyContext = new HashMap<String, GiftStrategy>() {{
            put(NORMAL, normalGiftStrategy);
            put(PREMIUM, premiumGiftStrategy);
            put(SUPER_USER, superUserGiftStrategy);
        }};
    }
    private GiftStrategy getStrategy(String userType){
        return this.strategyContext.get(userType);
    }

    public Double executeStrategy(User user) {
        return this.getStrategy(user.getUserType()).calculateGift(user);
    }
}
