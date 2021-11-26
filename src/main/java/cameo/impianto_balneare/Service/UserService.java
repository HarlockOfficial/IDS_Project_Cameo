package cameo.impianto_balneare.Service;

import cameo.impianto_balneare.Entity.Role;
import cameo.impianto_balneare.Entity.User;
import cameo.impianto_balneare.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<User> getAllUsers(String uuid) {
        if (tokenService.checkToken(uuid, Role.ADMIN)) {
            return userRepository.findAll();
        }
        return new ArrayList<>();
    }

    public User getUser(String id, String uuid) {
        if (tokenService.checkToken(uuid, Role.ADMIN)) {
            var user = userRepository.findById(UUID.fromString(id));
            return user.orElse(null);
        }
        return null;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user, String uuid) {
        var userToUpdate = userRepository.findById(user.getId());
        var userFromUUID = tokenService.getUserFromUUID(uuid);
        if (userToUpdate.isPresent() &&
                (
                        (userFromUUID != null && userFromUUID.getId().equals(user.getId()))
                                ||
                        (tokenService.checkToken(uuid, Role.ADMIN))
                )
        ) {
            var userToEdit = userToUpdate.get();
            userToEdit.setEmail(user.getEmail());
            userToEdit.setPassword(user.getPassword());
            userToEdit.setBirthDate(user.getBirthDate());
            userToEdit.setName(user.getName());
            userToEdit.setSurname(user.getSurname());
            userToEdit.setUsername(user.getUsername());
            return userRepository.save(userToEdit);
        }
        return null;
    }

    public User deleteUser(String id, String uuid) {
        var userToDelete = userRepository.findById(UUID.fromString(id));
        var userFromUUID = tokenService.getUserFromUUID(uuid);
        if (userToDelete.isPresent() &&
                (
                        (userFromUUID != null && userFromUUID.getId().equals(UUID.fromString(id)))
                                ||
                        (tokenService.checkToken(uuid, Role.ADMIN))
                )
        ) {
            userRepository.delete(userToDelete.get());
            return userToDelete.get();
        }
        return null;
    }
}
