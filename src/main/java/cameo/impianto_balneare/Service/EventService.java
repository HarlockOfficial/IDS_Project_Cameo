package cameo.impianto_balneare.Service;

import cameo.impianto_balneare.Entity.Event;
import cameo.impianto_balneare.Entity.Role;
import cameo.impianto_balneare.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final TokenService tokenService;

    @Autowired
    public EventService(EventRepository eventRepository, TokenService tokenService) {
        this.eventRepository = eventRepository;
        this.tokenService = tokenService;
    }


    public List<Event> getAllEvents(String uuid) {
        if (tokenService.checkToken(uuid, Role.ADMIN) || tokenService.checkToken(uuid, Role.EVENT_MANAGER)) {
            return eventRepository.findAll();
        }
        return new ArrayList<>();
    }

    public Event getEvent(String id, String uuid) {
        if (tokenService.checkToken(uuid, Role.ADMIN) || tokenService.checkToken(uuid, Role.EVENT_MANAGER)) {
            var event = eventRepository.findById(UUID.fromString(id));
            return event.orElse(null);
        }
        return null;
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(Event event, String token) {
        var eventToUpdate = eventRepository.findById(event.getId());
        if (eventToUpdate.isPresent() &&
                (
                        (tokenService.checkToken(token, Role.EVENT_MANAGER))
                                ||
                                (tokenService.checkToken(token, Role.ADMIN))
                )
        ) {
            var eventToEdit = eventToUpdate.get();
            eventToEdit.setDate(event.getDate());
            eventToEdit.setDescription(event.getDescription());
            eventToEdit.setLocation(event.getLocation());
            eventToEdit.setName(event.getName());
            eventToEdit.setPrice(event.getPrice());
            return eventRepository.save(eventToEdit);
        }
        return null;
    }

    public Event deleteEvent(String id, String token) {
        var eventToDelete = eventRepository.findById(UUID.fromString(id));
        if (eventToDelete.isPresent() &&
                (
                        (tokenService.checkToken(token, Role.EVENT_MANAGER))
                                ||
                                (tokenService.checkToken(token, Role.ADMIN))
                )
        ) {
            eventRepository.delete(eventToDelete.get());
            return eventToDelete.get();
        }
        return null;
    }
}
