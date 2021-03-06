package cameo.impianto_balneare.view;

import cameo.impianto_balneare.entity.User;
import cameo.impianto_balneare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;


@RestController
public class AuthenticationView implements GlobalExceptionHandler{
    private final UserService userService;

    @Autowired
    public AuthenticationView(UserService userService) {
        this.userService = userService;
    }

    /**
     * Effettua il logout di un utente
     *
     * @param token il token dell'utente presente nell'header http
     * @return json string with error or confirmation of logout
     */
    //mapping required by spring security
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<String> logoutConfirm(@Nullable @RequestParam String successful, @RequestHeader("token") String token) {
        userService.logout(token);
        return ResponseEntity.ok("{\"message\":\"Logout successful\"}");
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
        return ResponseEntity.ok().body(newUser);
    }
}
