package cameo.impianto_balneare.View;

import cameo.impianto_balneare.Entity.User;
import cameo.impianto_balneare.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class AuthenticationView {
    private final UserService userService;

    @Autowired
    public AuthenticationView(UserService userService) {
        this.userService = userService;
    }

    /**
     * Autentica un utente
     *
     * @param username l'username dell'utente
     * @param password la password dell'utente
     * @return json string with error or token
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        String token = userService.login(username, password);
        if (token == null) {
            return ResponseEntity.badRequest().body("{\"error\":\"Invalid username or password\"}");
        }
        return ResponseEntity.ok().header("token", token).body("{\"token\":\"" + token + "\"}");
    }

    /**
     * Crea un utente
     *
     * @param user i dati dell'utente da creare
     * @return user
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        var newUser = userService.register(user);
        if (newUser == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.created(URI.create("/login")).body(newUser);
    }

    /**
     * Effettua il logout di un utente
     *
     * @param token il token dell'utente presente nell'header http
     * @return json string with error or confirmation of logout
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity<String> logout(@RequestHeader("token") String token) {
        userService.logout(token);
        return ResponseEntity.ok("{\"message\":\"Logout successful\"}");
    }

}
