package codesquad.springcafe.users.service;

import codesquad.springcafe.users.controller.UserController;
import db.UserDatabase;
import model.User;
import model.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public void createUser(UserVO userVO) {
        String email = userVO.getEmail();
        String userId = userVO.getUserId();
        String password = userVO.getPassword();

        User user = new User(email, userId, password);
        logger.debug("User Created : {}", user);

        UserDatabase.addUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return UserDatabase.getAllUsers();
    }
}
