package cameo.impianto_balneare.Service;

import cameo.impianto_balneare.Entity.Event;
import cameo.impianto_balneare.Entity.Role;
import cameo.impianto_balneare.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final TokenService tokenService;

    @Autowired
    public EventService(EventRepository eventRepository, TokenService tokenService) {
        this.eventRepository = eventRepository;
        this.tokenService = tokenService;
    }


    public List<Event> getAllFutureEvents() {
        return eventRepository.findAll().stream().filter(e->e.getDate().after(Calendar.getInstance().getTime())).collect(Collectors.toList());
    }

    public Event getEvent(UUID id) {
        var event = eventRepository.findById(id);
        return event.orElse(null);
    }

    public Event createEvent(Event event, String tokenId) {
        if(!tokenService.checkToken(tokenId, Role.ADMIN) && !tokenService.checkToken(tokenId, Role.EVENT_MANAGER)) {
            return null;
        }
        if (eventRepository.findAll().stream().anyMatch(e -> event.getName().equals(e.getName()))) {
            return null;
        }
        return eventRepository.save(event);
    }

    public Event updateEvent(Event event, String tokenId) {
        var eventToUpdate = eventRepository.findById(event.getId());
        if (eventToUpdate.isPresent() &&
                (
                        (tokenService.checkToken(tokenId, Role.EVENT_MANAGER))
                                ||
                                (tokenService.checkToken(tokenId, Role.ADMIN))
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

    public Event deleteEvent(UUID id, String tokenId) {
        var eventToDelete = eventRepository.findById(id);
        if (eventToDelete.isPresent() &&
                (
                        (tokenService.checkToken(tokenId, Role.EVENT_MANAGER))
                                ||
                                (tokenService.checkToken(tokenId, Role.ADMIN))
                )
        ) {
            eventRepository.delete(eventToDelete.get());
            return eventToDelete.get();
        }
        return null;
    }
}
