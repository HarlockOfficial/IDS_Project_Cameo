package cameo.impianto_balneare.View;

import cameo.impianto_balneare.Entity.Event;
import cameo.impianto_balneare.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class EventView {

    private final EventService eventService;

    @Autowired
    public EventView(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Restisuice una lista di tutti gli eventi
     * @param header
     * @return lista di tutti gli eventi
     */
    @RequestMapping(value = "/event", method = RequestMethod.GET)
    public ResponseEntity<List<Event>> getAllEvents(@RequestHeader Map<String, String> header) {
        var events = eventService.getAllEvents(header.get("token"));
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    /**
     * Restituisce un evento specifico
     * @param id id dell'evento
     * @param header
     * @return successo "HttpStatus.OK" o evento non trovato "HttpStatus.NOT_FOUND"
     */
    @RequestMapping(value = "/event/{id}", method = RequestMethod.GET)
    public ResponseEntity<Event> getEvent(@PathVariable String id, @RequestHeader Map<String, String> header) {
        var event = eventService.getEvent(id, header.get("token"));
        if (event != null)
            return new ResponseEntity<>(event, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Crea un nuovo evento
     * @param event
     * @return l'evento creato
     */
    @RequestMapping(value = "/event", method = RequestMethod.POST)
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        var newEvent = eventService.createEvent(event);
        return new ResponseEntity<>(newEvent, HttpStatus.CREATED);
    }

    /**
     * Modifica un evento
     * @param event l'evento da modificare
     * @param header
     * @return l'evento modificato
     */
    @RequestMapping(value = "/event", method = RequestMethod.PUT)
    public ResponseEntity<Event> updateEvent(@RequestBody Event event, @RequestHeader Map<String, String> header) {
        var updatedEvent = eventService.updateEvent(event, header.get("token"));
        return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
    }

    /**
     * Elimina l'evento
     * @param id id dell'evento da eliminare
     * @param header
     * @return l'evento eliminato
     */
    @RequestMapping(value = "/event/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Event> deleteEvent(@PathVariable String id, @RequestHeader Map<String, String> header) {
        var deletedEvent = eventService.deleteEvent(id, header.get("token"));
        return new ResponseEntity<>(deletedEvent, HttpStatus.OK);
    }

}
