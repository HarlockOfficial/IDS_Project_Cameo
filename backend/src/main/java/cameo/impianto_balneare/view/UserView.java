package cameo.impianto_balneare.view;

import cameo.impianto_balneare.entity.User;
import cameo.impianto_balneare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class UserView implements GlobalExceptionHandler {
    private final UserService userService;

    @Autowired
    public UserView(UserService userService) {
        this.userService = userService;
    }

    /**
     * Restituisce la lista di tutti gli utenti con i corrispondenti dati
     *
     * @param token il token di autenticazione dell'utente
     * @return lista di tutti gli utenti con i loro dati
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers(@RequestHeader("token") String token) {
        var users = userService.getAllUsers(token);
        if (users == null || users.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    /**
     * Restituisce i dati di un singolo utente.
     *
     * @param id    id dell'utente in questione.
     * @param token il token di autenticazione dell'utente
     * @return i dati dell'utente.
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable UUID id, @RequestHeader("token") String token) {
        var user = userService.getUser(id, token);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * Aggiorna i dati di un utente
     *
     * @param user  i dati di un utente
     * @param token il token di autenticazione dell'utente
     * @return user
     */
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@RequestBody User user, @RequestHeader("token") String token) {
        var updatedUser = userService.updateUser(user, token);
        if (updatedUser == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Cancella un utente
     *
     * @param id    id dell'utente
     * @param token il token di autenticazione dell'utente
     * @return user
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable UUID id, @RequestHeader("token") String token) {
        var deletedUser = userService.deleteUser(id, token);
        if (deletedUser == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(deletedUser);
    }
}