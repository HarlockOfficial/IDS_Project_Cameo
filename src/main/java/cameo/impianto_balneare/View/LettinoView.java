package cameo.impianto_balneare.View;

import cameo.impianto_balneare.Entity.Lettino;
import cameo.impianto_balneare.Service.LettinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@RestController
public class LettinoView {
    private final LettinoService lettinoService;

    @Autowired
    public LettinoView(LettinoService lettinoService) {
        this.lettinoService = lettinoService;
    }

    /**
     * Restituisce una lista contenente tutti i lettini
     *
     * @return lista con lettini
     */
    @RequestMapping(value = "/lettino/all", method = RequestMethod.GET)
    public ResponseEntity<List<Lettino>> getAllLettino() {
        var lettino = lettinoService.getAllLettino();
        if (lettino == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(lettino);
    }

    /**
     * Restituisce una lista contenente tutti i lettini liberi
     *
     * @return lista con lettini liberi
     */
    @RequestMapping(value = "/lettino/free", method = RequestMethod.POST)
    public ResponseEntity<List<Lettino>> getAllFreeLettino(@RequestParam ZonedDateTime fromDate, @RequestParam ZonedDateTime toDate) {
        var lettino = lettinoService.getAllFreeLettino(fromDate, toDate);
        if (lettino == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(lettino);
    }

    /**
     * Restituisce uno specifico lettino
     *
     * @param id id dello lettino
     * @return lettino richiesto
     */
    @RequestMapping(value = "/lettino/{id}", method = RequestMethod.GET)
    public ResponseEntity<Lettino> getLettino(@PathVariable UUID id) {
        var lettino = lettinoService.getLettino(id);
        if (lettino == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(lettino);
    }

    /**
     * Creo un nuovo lettino
     *
     * @param lettino lettino da creare
     * @param token      token per capire chi lo vuole creare
     * @return lettino creato
     */
    @RequestMapping(value = "/lettino", method = RequestMethod.POST)
    public ResponseEntity<Lettino> createLettino(@RequestBody Lettino lettino, @RequestHeader("token") String token) {
        var newLettino = lettinoService.createLettino(lettino, token);
        if (newLettino == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.created(URI.create("/lettino")).body(newLettino);
    }

    /**
     * Modifico un lettino
     *
     * @param lettino lettino da aggiornare
     * @param token  token per capire chi lo vuole modificare
     * @return lettino modificato con successo
     */
    @RequestMapping(value = "/lettino", method = RequestMethod.PUT)
    public ResponseEntity<Lettino> updateLettino(@RequestBody Lettino lettino, @RequestHeader("token") String token) {
        var updatedLettino = lettinoService.updateLettino(lettino, token);
        if (updatedLettino == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedLettino);
    }

    /**
     * Elimina un lettino
     *
     * @param id    id dello lettino
     * @param token token per capire chi lo vuole eliminare
     * @return lettino eliminato con successo
     */
    @RequestMapping(value = "/lettino/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Lettino> deleteLettino(@PathVariable UUID id, @RequestHeader("token") String token) {
        var deletedLettino = lettinoService.deleteLettino(id, token);
        if (deletedLettino == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(deletedLettino);
    }
}
