package cameo.impianto_balneare.service;

import cameo.impianto_balneare.entity.*;
import cameo.impianto_balneare.repository.PrenotazioneRepository;
import cameo.impianto_balneare.repository.PrenotazioneSpiaggiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PrenotazioneService {
    private final TokenService tokenService;
    private final PrenotazioneRepository prenotazioneRepository;
    private final PrenotazioneSpiaggiaRepository prenotazioneSpiaggiaRepository;

    @Autowired
    public PrenotazioneService(TokenService tokenService, PrenotazioneRepository prenotazioneRepository, PrenotazioneSpiaggiaRepository prenotazioneSpiaggiaRepository) {
        this.tokenService = tokenService;
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
        if (prenotazione.isEmpty()) {
            return null;
        }
        var user = tokenService.getUserFromUUID(token);
        var p = prenotazione.get();
        if (user.getRole() == Role.ADMIN || p.getUser().equals(user)) {
            // delete prenotazioni ombrellone (otherwise foreign key check fails)
            var spiaggiaPrenotazioneSet = p.getSpiaggiaPrenotazioniList();
            if(spiaggiaPrenotazioneSet != null && !spiaggiaPrenotazioneSet.isEmpty()) {
                prenotazioneSpiaggiaRepository.deleteAllInBatch(spiaggiaPrenotazioneSet);
            }
            prenotazioneRepository.deleteAllInBatch(Collections.singleton(p));
            return p;
        }
        return null;
    }

    public List<Prenotazione> getPrenotazioni(String tokenId) {
        var user = tokenService.getUserFromUUID(tokenId);
        if (user.getRole() == Role.ADMIN || user.getRole() == Role.RECEPTION) {
            return prenotazioneRepository.findAll();
        }
        if (user.getRole() == Role.USER) {
            return new ArrayList<>(user.getPrenotazioni());
        }
        return null;
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