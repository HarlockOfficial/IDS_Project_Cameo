package cameo.impianto_balneare.view;

import cameo.impianto_balneare.entity.Prenotazione;
import cameo.impianto_balneare.service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class PrenotazioneView implements GlobalExceptionHandler{
    private final PrenotazioneService prenotazioneService;

    @Autowired
    public PrenotazioneView(PrenotazioneService prenotazioneService) {
        this.prenotazioneService = prenotazioneService;
    }

    @RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
    public ResponseEntity<Prenotazione> getPrenotazioneById(@PathVariable UUID id, @RequestHeader("token") String token) {
        var p = prenotazioneService.getPrenotazioneById(id, token);
        if(p == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(p);
    }

    @RequestMapping(value = "/book/{id}", method = RequestMethod.POST)
    public ResponseEntity<Prenotazione> checkoutPrenotazione(@PathVariable UUID id, @RequestHeader("token") String token) {
        Prenotazione p = prenotazioneService.checkoutPrenotazione(id, token);
        if(p == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(p);
    }

    @RequestMapping(value = "/book/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Prenotazione> deletePrenotazione(@PathVariable UUID id, @RequestHeader("token") String token) {
        Prenotazione p = prenotazioneService.deletePrenotazione(id, token);
        if(p == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(p);
    }

    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public ResponseEntity<List<Prenotazione>> getPrenotazioni(@RequestHeader String token) {
        List<Prenotazione> lst = prenotazioneService.getPrenotazioni(token);
        if(lst == null || lst.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lst);
    }

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public ResponseEntity<Prenotazione> addPrenotazione(@RequestBody Prenotazione prenotazione, @RequestHeader("token") String token) {
        Prenotazione p = prenotazioneService.createPrenotazione(prenotazione, token);
        if(p == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.ok(p);
    }

    @RequestMapping(value = "/book", method = RequestMethod.PUT)
    public ResponseEntity<Prenotazione> editPrenotazione(@RequestBody Prenotazione prenotazione, @RequestHeader("token") String token) {
        Prenotazione p = prenotazioneService.updatePrenotazione(prenotazione, token);
        if(p == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.ok(p);
    }
}
