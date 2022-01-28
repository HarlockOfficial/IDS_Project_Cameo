package cameo.impianto_balneare.service;

import cameo.impianto_balneare.entity.*;
import cameo.impianto_balneare.repository.PrenotazioneRepository;
import cameo.impianto_balneare.repository.PrenotazioneSpiaggiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PrenotazioneService {
    private final TokenService tokenService;
    private final EventService eventService;
    private final PrenotazioneRepository prenotazioneRepository;
    private final PrenotazioneSpiaggiaRepository prenotazioneSpiaggiaRepository;

    @Autowired
    public PrenotazioneService(TokenService tokenService, EventService eventService, PrenotazioneRepository prenotazioneRepository, PrenotazioneSpiaggiaRepository prenotazioneSpiaggiaRepository) {
        this.tokenService = tokenService;
        this.eventService = eventService;
        this.prenotazioneRepository = prenotazioneRepository;
        this.prenotazioneSpiaggiaRepository = prenotazioneSpiaggiaRepository;
    }

    public Prenotazione getPrenotazioneById(UUID id, String token) {
        var prenotazione = prenotazioneRepository.findAll().stream().filter(p -> p.getId().equals(id)).findFirst();
        if (tokenService.checkToken(token, Role.ADMIN)) {
            return prenotazione.orElse(null);
        }
        if (prenotazione.isPresent() &&
                prenotazione.get().getUser().equals(tokenService.getUserFromUUID(token))) {
            return prenotazione.get();
        }
        return null;
    }

    public Prenotazione checkoutPrenotazione(UUID id, String token) {
        var prenotazione = prenotazioneRepository.findAll().stream().filter(p -> p.getId().equals(id)).findFirst();
        var user = tokenService.getUserFromUUID(token);
        if (prenotazione.isEmpty()) {
            return null;
        }
        if (user.getRole() == Role.ADMIN || prenotazione.get().getUser().equals(user)) {
            prenotazione.get().setStatoPrenotazione(StatoPrenotazione.CONFERMATO);
            return prenotazioneRepository.save(prenotazione.get());
        }
        return null;
    }

    public Prenotazione deletePrenotazione(UUID id, String token) {
        var prenotazione = prenotazioneRepository.findAll().stream().filter(p -> p.getId().equals(id)).findFirst();
        var user = tokenService.getUserFromUUID(token);
        if (prenotazione.isEmpty()) {
            return null;
        }
        var p = prenotazione.get();
        if (user.getRole() == Role.ADMIN || p.getUser().getId().equals(user.getId())) {
            prenotazioneRepository.delete(p);
            return p;
        }
        return null;
    }

    public List<Prenotazione> getPrenotazioni(String tokenId) {
        var user = tokenService.getUserFromUUID(tokenId);
        List<Prenotazione> prenotazioni = new ArrayList<>(user.getPrenotazioni());
        if (user.getRole() == Role.ADMIN || user.getRole() == Role.RECEPTION) {
            prenotazioni = prenotazioneRepository.findAll();
        }if (user.getRole() != Role.USER && user.getRole() != Role.ADMIN && user.getRole() != Role.RECEPTION) {
            return null;
        }
        return prenotazioni;
    }

    public Prenotazione createPrenotazione(Prenotazione prenotazione, String token) {
        var user = tokenService.getUserFromUUID(token);
        if (user.getRole() == Role.USER) {
            prenotazione.setUser(user);
        }
        var ombrelloniList = prenotazione.getSpiaggiaPrenotazioniList();
        prenotazioneSpiaggiaRepository.saveAll(ombrelloniList);
        var out = prenotazioneRepository.save(prenotazione);
        ombrelloniList.forEach(ombrelloni -> ombrelloni.setPrenotazione(out));
        prenotazioneSpiaggiaRepository.saveAll(ombrelloniList);
        var eventList = prenotazione.getEventiPrenotatiList();
        eventList.forEach(event -> {
            var prenotazioniPendenti = event.getPrenotazione();
            prenotazioniPendenti.add(out);
            event.setPrenotazione(prenotazioniPendenti);
        });
        //TODO The following line inserts a vulnerability,
        // think about a better way to update the reference on the other side
        eventService.saveAll(eventList);
        return prenotazioneRepository.save(out);
    }

    public Prenotazione updatePrenotazione(Prenotazione prenotazione, String token) {
        if (!tokenService.checkToken(token, Role.ADMIN) && !tokenService.checkToken(token, Role.RECEPTION)) {
            return null;
        }
        var prenotazioneToUpdate = prenotazioneRepository.findAll().stream().filter(p -> p.equals(prenotazione)).findFirst();
        if (prenotazioneToUpdate.isEmpty()) {
            return null;
        }
        var prenotazioneToEdit = prenotazioneToUpdate.get();
        prenotazioneToEdit.setStatoPrenotazione(prenotazione.getStatoPrenotazione());
        prenotazioneToEdit.setEventiPrenotatiList(prenotazione.getEventiPrenotatiList());
        prenotazioneToEdit.setDate(prenotazione.getDate());
        prenotazioneToEdit.setSpiaggiaPrenotazioniList(prenotazione.getSpiaggiaPrenotazioniList());
        prenotazioneToEdit.setUser(prenotazione.getUser());
        return prenotazioneRepository.save(prenotazioneToEdit);
    }

    public List<Prenotazione> getAllPrenotazioni() {
        return prenotazioneRepository.findAll();
    }
}