package recipes.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void addNewUser(User newUser){
        boolean exist = userRepository.existsByEmail(newUser.getEmail());
        if(exist) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setRoles("ROLE_USER");
        userRepository.save(newUser);
    }


}
