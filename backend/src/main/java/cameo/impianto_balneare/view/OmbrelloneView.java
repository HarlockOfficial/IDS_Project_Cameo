package cameo.impianto_balneare.view;

import cameo.impianto_balneare.entity.Ombrellone;
import cameo.impianto_balneare.entity.Prenotazione;
import cameo.impianto_balneare.service.OmbrelloneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@RestController
public class OmbrelloneView implements GlobalExceptionHandler{
    private final OmbrelloneService ombrelloneService;

    @Autowired
    public OmbrelloneView(OmbrelloneService ombrelloneService) {
        this.ombrelloneService = ombrelloneService;
    }

    /**
     * Restituisce una lista contenente tutti gli ombrelloni
     *
     * @return lista con ombrelloni
     */
    @RequestMapping(value = "/ombrellone/all", method = RequestMethod.GET)
    public ResponseEntity<List<Ombrellone>> getAllOmbrelloni() {
        var ombrellone = ombrelloneService.getAllOmbrelloni();
        if (ombrellone == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ombrellone);
    }

    /**
     * Restituisce una lista contenente tutti gli ombrelloni liberi
     *
     * @return lista con ombrelloni
     */
    @RequestMapping(value = "/ombrellone/free", method = RequestMethod.POST)
    public ResponseEntity<List<Ombrellone>> getAllFreeOmbrelloni(@RequestParam ZonedDateTime fromDate, @RequestParam ZonedDateTime toDate) {
        var ombrellone = ombrelloneService.getAllFreeOmbrelloni(fromDate, toDate);
        if (ombrellone == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ombrellone);
    }

    /**
     * Restituisce uno specifico ombrellone
     *
     * @param id id dell'ombrellone
     * @return l'ombrellone richiesto
     */
    @RequestMapping(value = "/ombrellone/{id}", method = RequestMethod.GET)
    public ResponseEntity<Ombrellone> getOmbrellone(@PathVariable UUID id) {
        var ombrellone = ombrelloneService.getOmbrellone(id);
        if (ombrellone == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ombrellone);
    }

    /**
     * Creo un nuovo ombrellone
     *
     * @param ombrellone Ombrellone da creare
     * @param token      token per capire chi lo vuole creare
     * @return ombrellone creato
     */
    @RequestMapping(value = "/ombrellone", method = RequestMethod.POST)
    public ResponseEntity<Ombrellone> createOmbrellone(@RequestBody Ombrellone ombrellone, @RequestHeader("token") String token) {
        var newOmbrellone = ombrelloneService.createOmbrellone(ombrellone, token);
        if (newOmbrellone == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.created(URI.create("/ombrellone")).body(newOmbrellone);
    }

    /**
     * Modifico un ombrellone
     *
     * @param ombrellone ombrellone da aggiornare
     * @param token      token per capire chi lo vuole modificare
     * @return ombrellone modificato con successo
     */
    @RequestMapping(value = "/ombrellone", method = RequestMethod.PUT)
    public ResponseEntity<Ombrellone> updateOmbrellone(@RequestBody Ombrellone ombrellone, @RequestHeader("token") String token) {
        var updatedOmbrellone = ombrelloneService.updateOmbrellone(ombrellone, token);
        if (updatedOmbrellone == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedOmbrellone);
    }

    /**
     * Elimina un ombrellone
     *
     * @param id    id dell'ombrellone
     * @param token token per capire chi lo vuole eliminare
     * @return ombrellone eliminato con successo
     */
    @RequestMapping(value = "/ombrellone/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Ombrellone> deleteOmbrellone(@PathVariable UUID id, @RequestHeader("token") String token) {
        var deletedOmbrellone = ombrelloneService.deleteOmbrellone(id, token);
        if (deletedOmbrellone == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(deletedOmbrellone);
    }

    @RequestMapping(value = "/ombrellone/{id}/book", method = RequestMethod.GET)
    public ResponseEntity<List<Prenotazione>> getPrenotazioniByOmbrellone(@PathVariable UUID id,
                                                                          @RequestParam ZonedDateTime dataInizio,
                                                                          @RequestParam ZonedDateTime dataFine, @RequestHeader("token") String token) {
        var prenotazioni = ombrelloneService.getPrenotazioneByOmbrellone(id, dataInizio, dataFine, token);
        if(prenotazioni == null || prenotazioni.size() == 0)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(prenotazioni);
    }
}
