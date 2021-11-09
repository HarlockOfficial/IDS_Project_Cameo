package cameo.impianto_balneare.View;

import cameo.impianto_balneare.Entity.Utente;
import cameo.impianto_balneare.Service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UtenteView {
    private final UtenteService utenteService;

    @Autowired
    public UtenteView(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @RequestMapping(value="/user", method= RequestMethod.GET)
    public ResponseEntity<List<Utente>> getAllUsers(){
        return new ResponseEntity<>(utenteService.getAllUsers(), HttpStatus.OK);
    }

    @RequestMapping(value="/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<Utente> getUser(@PathVariable Long id){
        var user = utenteService.getUser(id);
        if(user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
