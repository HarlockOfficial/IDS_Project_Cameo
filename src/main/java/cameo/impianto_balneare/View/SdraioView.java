package cameo.impianto_balneare.View;

import cameo.impianto_balneare.Entity.Sdraio;
import cameo.impianto_balneare.Service.SdraioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@RestController
public class SdraioView {
    private final SdraioService sdraioService;

    @Autowired
    public SdraioView(SdraioService sdraioService) {
        this.sdraioService = sdraioService;
    }

    /**
     * Restituisce una lista contenente tutte le sdraio
     *
     * @return lista con sdraio
     */
    @RequestMapping(value = "/sdraio/all", method = RequestMethod.GET)
    public ResponseEntity<List<Sdraio>> getAllSdraio() {
        var sdraio = sdraioService.getAllSdraio();
        if (sdraio == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(sdraio);
    }

    /**
     * Restituisce una lista contenente tutte le sdraio libere
     *
     * @return lista con sdraio
     */
    @RequestMapping(value = "/sdraio/free", method = RequestMethod.POST)
    public ResponseEntity<List<Sdraio>> getAllFreeSdraio(@RequestParam ZonedDateTime fromDate, @RequestParam ZonedDateTime toDate) {
        var sdraio = sdraioService.getAllFreeSdraio(fromDate, toDate);
        if (sdraio == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(sdraio);
    }

    /**
     * Restituisce uno specifico sdraio
     *
     * @param id id dello sdraio
     * @return lo sdraio richiesto
     */
    @RequestMapping(value = "/sdraio/{id}", method = RequestMethod.GET)
    public ResponseEntity<Sdraio> getSdraio(@PathVariable UUID id) {
        var sdraio = sdraioService.getSdraio(id);
        if (sdraio == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(sdraio);
    }

    /**
     * Creo un nuovo sdraio
     *
     * @param sdraio sdraio da creare
     * @param token      token per capire chi lo vuole creare
     * @return sdraio creato
     */
    @RequestMapping(value = "/sdraio", method = RequestMethod.POST)
    public ResponseEntity<Sdraio> createSdraio(@RequestBody Sdraio sdraio, @RequestHeader("token") String token) {
        var newSdraio = sdraioService.createSdraio(sdraio, token);
        if (newSdraio == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.created(URI.create("/sdraio")).body(newSdraio);
    }

    /**
     * Modifico uno sdraio
     *
     * @param sdraio sdraio da aggiornare
     * @param token  token per capire chi lo vuole modificare
     * @return sdraio modificato con successo
     */
    @RequestMapping(value = "/sdraio", method = RequestMethod.PUT)
    public ResponseEntity<Sdraio> updateSdraio(@RequestBody Sdraio sdraio, @RequestHeader("token") String token) {
        var updatedSdraio = sdraioService.updateSdraio(sdraio, token);
        if (updatedSdraio == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedSdraio);
    }

    /**
     * Elimina uno sdraio
     *
     * @param id    id dello sdraio
     * @param token token per capire chi lo vuole eliminare
     * @return sdraio eliminato con successo
     */
    @RequestMapping(value = "/sdraio/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Sdraio> deleteSdraio(@PathVariable UUID id, @RequestHeader("token") String token) {
        var deletedSdraio = sdraioService.deleteSdraio(id, token);
        if (deletedSdraio == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(deletedSdraio);
    }
}
