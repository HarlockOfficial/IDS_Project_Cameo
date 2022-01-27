package cameo.impianto_balneare.service;

import cameo.impianto_balneare.entity.Role;
import cameo.impianto_balneare.entity.User;
import cameo.impianto_balneare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers(String tokenId) {
        if (tokenService.checkToken(tokenId, Role.ADMIN) || tokenService.checkToken(tokenId, Role.RECEPTION)) {
            return userRepository.findAll();
        }
        var user = tokenService.getUserFromUUID(tokenId);
        var users = new ArrayList<User>();
        users.add(user);
        return users;
    }

    public User getUser(UUID id, String tokenId) {
        if (tokenService.checkToken(tokenId, Role.ADMIN)) {
            var user = userRepository.findAll().stream().filter(u -> u.getId().equals(id)).findFirst();
            return user.orElse(null);
        }
        var requestUser = tokenService.getUserFromUUID(tokenId);
        if(requestUser.getId().equals(id)) {
            return tokenService.getUserFromUUID(tokenId);
        }
        return null;
    }

    public User updateUser(User user, String tokenId) {
        var isChangeRequestedByAdmin = tokenService.checkToken(tokenId, Role.ADMIN);
        if (!isChangeRequestedByAdmin && !tokenService.getUserFromUUID(tokenId).getId().equals(user.getId())) {
            return null;
        }
        var userToUpdate = userRepository.findAll().stream().filter(u -> u.getId().equals(user.getId())).findFirst();
        if (userToUpdate.isPresent()) {
            var userToEdit = userToUpdate.get();
            userToEdit.setEmail(user.getEmail());
            userToEdit.setPassword(user.getPassword());
            userToEdit.setBirthDate(user.getBirthDate());
            userToEdit.setName(user.getName());
            userToEdit.setSurname(user.getSurname());
            userToEdit.setUsername(user.getUsername());
            if (isChangeRequestedByAdmin)
                userToEdit.setRole(user.getRole());
            return userRepository.save(userToEdit);
        }
        return null;
    }

    public User deleteUser(UUID id, String tokenId) {
        if (!tokenService.checkToken(tokenId, Role.ADMIN) && !tokenService.getUserFromUUID(tokenId).getId().equals(id)) {
            return null;
        }
        var userToDelete = userRepository.findAll().stream().filter(u -> u.getId().equals(id)).findFirst();
        if (userToDelete.isPresent()) {
            userRepository.delete(userToDelete.get());
            return userToDelete.get();
        }
        return null;
    }

    public String login(String username, String password) {
        var user = userRepository.findAll().stream().filter(u -> username.equals(u.getUsername()) &&
                passwordEncoder.matches(password, u.getPassword())).findFirst();
        return user.map(tokenService::createToken).orElse(null);
    }

    public User register(User user) {
        user.setRole(Role.USER);
        if (userRepository.findAll().stream().anyMatch(u ->
                user.getEmail().equals(u.getEmail()) ||
                        user.getUsername().equals(u.getUsername()))) {
            return null;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void logout(String token) {
        tokenService.deleteToken(token);
    }
}
