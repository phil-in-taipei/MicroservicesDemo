package UserMicroservice.UserMicroservice.services;
import java.util.Optional;
import UserMicroservice.UserMicroservice.models.User;
import UserMicroservice.UserMicroservice.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User getUserById(Long id) {
        Optional<User> optional;
        if ((optional = userRepository.findById(id)).isEmpty()) {
            return null;
        } else {
            return optional.get();
        }
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User createNewUser(User newUser) {
        newUser.setPassword(newUser.getPassword());
        return userRepository.save(newUser);
    }

    public User updateUser(User updatedUser) {
        User user = userRepository.findByUsername(updatedUser.getUsername());
        BeanUtils.copyProperties(updatedUser, user);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
