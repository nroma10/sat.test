package sat.recruitment.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sat.recruitment.api.exception.UserAlreadyExistsException;
import sat.recruitment.api.model.User;
import sat.recruitment.api.repository.UserRepository;
import sat.recruitment.api.strategy.GiftContext;
import static sat.recruitment.api.utils.Constants.USER_DUPLICATED;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GiftContext giftContext;
    @Override
    public User createUser(User newUser) {
        if(userRepository.existsUserByNameOrPhoneOrByNameAndAddress(newUser).isPresent()){
            throw new UserAlreadyExistsException(USER_DUPLICATED);
        }
        newUser.setMoney(newUser.getMoney() + giftContext.executeStrategy(newUser));
        userRepository.save(newUser);
        return newUser;
    }
}
