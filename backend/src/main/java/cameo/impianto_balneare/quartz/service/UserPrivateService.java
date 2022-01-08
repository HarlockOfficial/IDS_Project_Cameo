package cameo.impianto_balneare.quartz.service;

import cameo.impianto_balneare.entity.User;
import cameo.impianto_balneare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserPrivateService {
    private final UserRepository userRepository;

    @Autowired
    public UserPrivateService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<String> getAllUserEmail() {
        return userRepository.findAll().stream().map(User::getEmail).collect(Collectors.toList());
    }
}
