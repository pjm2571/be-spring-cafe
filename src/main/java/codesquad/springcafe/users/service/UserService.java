package codesquad.springcafe.users.service;

import codesquad.springcafe.users.model.User;
import codesquad.springcafe.exception.PasswordMismatchException;
import codesquad.springcafe.exception.UserNotFoundException;
import codesquad.springcafe.users.model.dto.*;
import codesquad.springcafe.users.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(UserCreateDto userCreateDto) {
        User user = new User(userCreateDto.getUserId(), userCreateDto.getEmail(), userCreateDto.getName(), userCreateDto.getPassword());
        logger.debug("User Created : {}", user);

        userRepository.createUser(user);
    }

    /*
     * 필요한 값
     * userId, name, email, creationDate
     * */
    public ArrayList<UserPreviewDto> getAllUsers() {
        Optional<ArrayList<UserPreviewDto>> articles = userRepository.getAllUsers();
        return articles.orElseThrow(() -> new UserNotFoundException("유저를 찾을 수 없습니다."));
    }

    public UserPreviewDto findUserById(String userId) {
        return userRepository.findUserById(userId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다"));
    }

    public void updateUser(String userId, UserUpdateData updateData) {
        // Repository에서 User 객체를 가지고 와야 한다..?
        UserCredentialDto userCredentialDto = userRepository.getUserCredential(userId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        validatePassword(updateData.getCurrentPassword(), userCredentialDto);
        userRepository.updateUser(userId, updateData);
        logger.debug("User Updated : {}", userId);
    }

    public UserPreviewDto loginUser(UserLoginDto userLoginDto) {
        UserCredentialDto userCredentialDto = userRepository.getUserCredential(userLoginDto.getUserId()).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
        validatePassword(userLoginDto.getPassword(), userCredentialDto);
        return userRepository.findUserById(userLoginDto.getUserId()).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
    }

    private void validatePassword(String userPassword, UserCredentialDto userCredentialDto) {
        if (!userCredentialDto.validatePassword(userPassword)) {
            throw new PasswordMismatchException("입력한 비밀번호가 일치하지 않습니다.");
        }
    }
}
