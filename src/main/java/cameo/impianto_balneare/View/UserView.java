package cameo.impianto_balneare.View;

import cameo.impianto_balneare.Entity.User;
import cameo.impianto_balneare.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserView {
    private final UserService userService;

    @Autowired
    public UserView(UserService userService) {
        this.userService = userService;
    }

    /**
     * Restituisce la lista di tutti gli utenti con i corrispondenti dati
     * @param header
     * @return lista di tutti gli utenti con i loro dati
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers(@RequestHeader Map<String, String> header) {
        var users = userService.getAllUsers(header.get("token"));
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Restituisce i dati di un singolo utente.
     * @param id id dell'utente in questione.
     * @return i dati dell'utente.
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable String id, @RequestHeader Map<String, String> header) {
        var user = userService.getUser(id, header.get("token"));
        if (user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Crea un utente
     * @param user
     * @return user
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        var newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    /**
     * Aggiorna i dati di un utente
     * @param user
     * @return user
     */
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@RequestBody User user, @RequestHeader Map<String, String> header) {
        var updatedUser = userService.updateUser(user, header.get("token"));
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    /**
     * Cancella un utente
     * @param id id dell'utente
     * @return user
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable String id, @RequestHeader Map<String, String> header) {
        var deletedUser = userService.deleteUser(id, header.get("token"));
        return new ResponseEntity<>(deletedUser, HttpStatus.OK);
    }
}