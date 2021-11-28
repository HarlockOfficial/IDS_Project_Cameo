package cameo.impianto_balneare.View;

import cameo.impianto_balneare.Entity.Event;
import cameo.impianto_balneare.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class EventView {

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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(events, HttpStatus.OK);
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(event, HttpStatus.OK);
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
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(newEvent, HttpStatus.CREATED);
    }

    /**
     * Modifica un evento
     *
     * @param event  l'evento da modificare
     * @param header gli header della richiesta http
     * @return l'evento modificato
     */
    @RequestMapping(value = "/event", method = RequestMethod.PUT)
    public ResponseEntity<Event> updateEvent(@RequestBody Event event, @RequestHeader("token") String token) {
        var updatedEvent = eventService.updateEvent(event, token);
        if (updatedEvent == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
    }

    /**
     * Elimina l'evento
     *
     * @param id     id dell'evento da eliminare
     * @param header gli header della richiesta http
     * @return l'evento eliminato
     */
    @RequestMapping(value = "/event/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Event> deleteEvent(@PathVariable UUID id, @RequestHeader("token") String token) {
        var deletedEvent = eventService.deleteEvent(id, token);
        if (deletedEvent == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(deletedEvent, HttpStatus.OK);
    }
}
