package cameo.impianto_balneare.Service;

import cameo.impianto_balneare.Entity.Role;
import cameo.impianto_balneare.Entity.User;
import cameo.impianto_balneare.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Autowired
    public UserService(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public List<User> getAllUsers(String tokenId) {
        if (tokenService.checkToken(tokenId, Role.ADMIN)) {
            return userRepository.findAll();
        }
        return null;
    }

    public User getUser(UUID id, String tokenId) {
        if (tokenService.checkToken(tokenId, Role.ADMIN)) {
            var user = userRepository.findById(id);
            return user.orElse(null);
        }
        return null;
    }

    public User updateUser(User user, String tokenId) {
        var userToUpdate = userRepository.findById(user.getId());
        var userFromUUID = tokenService.getUserFromUUID(tokenId);
        var isChangeRequestedByAdmin = tokenService.checkToken(tokenId, Role.ADMIN);
        if (userToUpdate.isPresent() &&
                (
                        (userFromUUID != null && userFromUUID.getId().equals(user.getId()))
                                ||
                                isChangeRequestedByAdmin
                )
        ) {
            var userToEdit = userToUpdate.get();
            userToEdit.setEmail(user.getEmail());
            userToEdit.setPassword(user.getPassword());
            userToEdit.setBirthDate(user.getBirthDate());
            userToEdit.setName(user.getName());
            userToEdit.setSurname(user.getSurname());
            userToEdit.setUsername(user.getUsername());
            if(isChangeRequestedByAdmin)
                userToEdit.setRole(user.getRole());
            return userRepository.save(userToEdit);
        }
        return null;
    }

    public User deleteUser(UUID id, String tokenId) {
        var userToDelete = userRepository.findById(id);
        var userFromUUID = tokenService.getUserFromUUID(tokenId);
        if (userToDelete.isPresent() &&
                (
                        (userFromUUID != null && userFromUUID.getId().equals(id))
                                ||
                                (tokenService.checkToken(tokenId, Role.ADMIN))
                )
        ) {
            userRepository.delete(userToDelete.get());
            return userToDelete.get();
        }
        return null;
    }

    public String login(String username, String password) {
        var user = userRepository.findAll().stream().filter(u -> username.equals(u.getUsername()) &&
                password.equals(u.getPassword())).findFirst();
        return user.map(tokenService::createToken).orElse(null);
    }

    public User register(User user) {
        user.setRole(Role.USER);
        if (userRepository.findAll().stream().anyMatch(u ->
                user.getEmail().equals(u.getEmail()) ||
                        user.getUsername().equals(u.getUsername()))) {
            return null;
        }
        return userRepository.save(user);
    }
}
