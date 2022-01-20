package cameo.impianto_balneare.view;

import cameo.impianto_balneare.entity.Event;
import cameo.impianto_balneare.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
public class EventView implements GlobalExceptionHandler{

    private final EventService eventService;

    @Autowired
    public EventView(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Restisuice una lista di tutti gli eventi
     *
     * @return lista di tutti gli eventi
     */
    @RequestMapping(value = "/event", method = RequestMethod.GET)
    public ResponseEntity<List<Event>> getAllEvents() {
        var events = eventService.getAllFutureEvents();
        if (events == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(events);
    }

    /**
     * Restituisce un evento specifico
     *
     * @param id id dell'evento
     * @return successo "HttpStatus.OK" o evento non trovato "HttpStatus.NOT_FOUND"
     */
    @RequestMapping(value = "/event/{id}", method = RequestMethod.GET)
    public ResponseEntity<Event> getEvent(@PathVariable UUID id) {
        var event = eventService.getEvent(id);
        if (event == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(event);
    }

    /**
     * Crea un nuovo evento
     *
     * @param event l'evento da creare
     * @return l'evento creato
     */
    @RequestMapping(value = "/event", method = RequestMethod.POST)
    public ResponseEntity<Event> createEvent(@RequestBody Event event, @RequestHeader("token") String token) {
        var newEvent = eventService.createEvent(event, token);
        if (newEvent == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.created(URI.create("/event")).body(newEvent);
    }

    /**
     * Modifica un evento
     *
     * @param event l'evento da modificare
     * @param token il token di autenticazione dell'utente
     * @return l'evento modificato
     */
    @RequestMapping(value = "/event", method = RequestMethod.PUT)
    public ResponseEntity<Event> updateEvent(@RequestBody Event event, @RequestHeader("token") String token) {
        var updatedEvent = eventService.updateEvent(event, token);
        if (updatedEvent == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedEvent);
    }

    /**
     * Elimina l'evento
     *
     * @param id    id dell'evento da eliminare
     * @param token il token di autenticazione dell'utente
     * @return l'evento eliminato
     */
    @RequestMapping(value = "/event/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Event> deleteEvent(@PathVariable UUID id, @RequestHeader("token") String token) {
        var deletedEvent = eventService.deleteEvent(id, token);
        if (deletedEvent == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(deletedEvent);
    }
}
