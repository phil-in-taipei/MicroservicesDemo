package MicroservicesFrontEnd.MicroservicesFrontEnd.services;
import MicroservicesFrontEnd.MicroservicesFrontEnd.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
    @Autowired
    @LoadBalanced
    RestTemplate restTemplate;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserModel registerNewUser(UserModel user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        ResponseEntity<UserModel> response = restTemplate.postForEntity
                ("http://USER-MICROSERVICE/user", user, UserModel.class);
        return checkStatus(response);
    }

    public UserModel getUser(String username) {
        ResponseEntity<UserModel> response = restTemplate.getForEntity
                ("http://USER-MICROSERVICE/user/username/" + username, UserModel.class);
        return checkStatus(response);
    }

    public UserModel checkStatus(ResponseEntity<UserModel> response) {
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            return null;
        }
    }
}
