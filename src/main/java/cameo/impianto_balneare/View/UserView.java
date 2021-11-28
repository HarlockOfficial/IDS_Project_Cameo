package cameo.impianto_balneare.View;

import cameo.impianto_balneare.Entity.User;
import cameo.impianto_balneare.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class UserView {
    private final UserService userService;

    @Autowired
    public UserView(UserService userService) {
        this.userService = userService;
    }

    /**
     * Restituisce la lista di tutti gli utenti con i corrispondenti dati
     *
     * @param header l'header http contenente il token
     * @return lista di tutti gli utenti con i loro dati
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers(@RequestHeader("token") String token) {
        var users = userService.getAllUsers(token);
        if (users == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Restituisce i dati di un singolo utente.
     *
     * @param id id dell'utente in questione.
     * @return i dati dell'utente.
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable UUID id, @RequestHeader("token") String token) {
        var user = userService.getUser(id, token);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Aggiorna i dati di un utente
     *
     * @param user i dati di un utente
     * @return user
     */
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@RequestBody User user, @RequestHeader("token") String token) {
        var updatedUser = userService.updateUser(user, token);
        if (updatedUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    /**
     * Cancella un utente
     *
     * @param id id dell'utente
     * @return user
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable UUID id, @RequestHeader("token") String token) {
        var deletedUser = userService.deleteUser(id, token);
        if (deletedUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(deletedUser, HttpStatus.OK);
    }
}