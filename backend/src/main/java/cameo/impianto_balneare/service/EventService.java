package cameo.impianto_balneare.service;

import cameo.impianto_balneare.entity.Event;
import cameo.impianto_balneare.entity.Prenotazione;
import cameo.impianto_balneare.entity.Role;
import cameo.impianto_balneare.repository.EventRepository;
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
        return eventRepository.findAll().stream().filter(e -> e.getDate().after(Calendar.getInstance().getTime())).collect(Collectors.toList());
    }

    public Event getEvent(UUID id) {
        var event = eventRepository.findAll().stream().filter(e -> e.getId().equals(id)).findFirst();
        return event.orElse(null);
    }

    public Event createEvent(Event event, String tokenId) {
        if (!tokenService.checkToken(tokenId, Role.ADMIN) && !tokenService.checkToken(tokenId, Role.EVENT_MANAGER)) {
            return null;
        }
        if (eventRepository.findAll().stream().anyMatch(e -> event.getName().equalsIgnoreCase(e.getName()))) {
            return null;
        }
        return eventRepository.save(event);
    }

    public Event updateEvent(Event event, String tokenId) {
        if (!tokenService.checkToken(tokenId, Role.EVENT_MANAGER) && !tokenService.checkToken(tokenId, Role.ADMIN)) {
            return null;
        }
        var eventToUpdate = eventRepository.findAll().stream().filter(e -> e.equals(event)).findFirst();
        if (eventToUpdate.isPresent()) {
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
        if (!tokenService.checkToken(tokenId, Role.EVENT_MANAGER) && !tokenService.checkToken(tokenId, Role.ADMIN)) {
            return null;
        }
        var eventToDelete = eventRepository.findAll().stream().filter(e -> e.getId().equals(id)).findFirst();
        if (eventToDelete.isPresent()) {
            eventRepository.delete(eventToDelete.get());
            return eventToDelete.get();
        }
        return null;
    }

    public void deletePrenotazione(Event event, Prenotazione p) {
        event.getPrenotazione().remove(p);
        eventRepository.save(event);
    }
}
