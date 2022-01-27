package cameo.impianto_balneare.service;

import cameo.impianto_balneare.entity.*;
import cameo.impianto_balneare.repository.PrenotazioneRepository;
import cameo.impianto_balneare.repository.PrenotazioneSpiaggiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        var prenotazione = prenotazioneRepository.findById(id);
        if (tokenService.checkToken(token, Role.ADMIN)) {
            return prenotazione.orElse(null);
        }
        if (prenotazione.isPresent() &&
                prenotazione.get().getUser().getId().equals(tokenService.getUserFromUUID(token).getId())) {
            return prenotazione.get();
        }
        return null;
    }

    public Prenotazione checkoutPrenotazione(UUID id, String token) {
        var prenotazione = prenotazioneRepository.findById(id);
        var user = tokenService.getUserFromUUID(token);
        if (prenotazione.isEmpty()) {
            return null;
        }
        if (user.getRole() == Role.ADMIN || prenotazione.get().getUser().getId().equals(user.getId())) {
            prenotazione.get().setStatoPrenotazione(StatoPrenotazione.CONFERMATO);
            return prenotazioneRepository.save(prenotazione.get());
        }
        return null;
    }

    public Prenotazione deletePrenotazione(UUID id, String token) {
        var prenotazione = prenotazioneRepository.findById(id);
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
        if(!prenotazione.getEventiPrenotatiList().isEmpty()) {
            var prenotazioniToRemove = new ArrayList<Event>();
            for (int i = 0; i < prenotazione.getEventiPrenotatiList().size(); i++) {
                var event = new ArrayList<>(prenotazione.getEventiPrenotatiList()).get(i);
                var isEventPresent = user.getPrenotazioni().stream()
                        .flatMap(e -> e.getEventiPrenotatiList().stream())
                        .anyMatch(e -> e.getId().equals(event.getId()));
                if (isEventPresent) {
                    prenotazioniToRemove.add(event);
                }
            }
            prenotazioniToRemove.forEach(prenotazione.getEventiPrenotatiList()::remove);
        }
        if(!prenotazione.getSpiaggiaPrenotazioniList().isEmpty()) {
            var prenotazioniToRemove = new ArrayList<PrenotazioneSpiaggia>();
            for (int i = 0; i < prenotazione.getSpiaggiaPrenotazioniList().size(); i++) {
                var ombrellone = new ArrayList<>(prenotazione.getSpiaggiaPrenotazioniList()).get(i).getOmbrellone();
                var isOmbrellonePresent = user.getPrenotazioni().stream()
                        .flatMap(e -> e.getSpiaggiaPrenotazioniList().stream())
                        .anyMatch(e -> e.getOmbrellone().getId().equals(ombrellone.getId()));
                if (isOmbrellonePresent) {
                    prenotazioniToRemove.add(new ArrayList<>(prenotazione.getSpiaggiaPrenotazioniList()).get(i));
                }
            }
            prenotazioniToRemove.forEach(prenotazione.getSpiaggiaPrenotazioniList()::remove);
            prenotazione.getSpiaggiaPrenotazioniList().forEach(prenotazioneSpiaggia -> {
                if(prenotazioneSpiaggiaRepository.findById(prenotazioneSpiaggia.getId()).isEmpty()){
                    prenotazioneSpiaggia.setPrenotazione(prenotazione);
                    prenotazioneSpiaggiaRepository.save(prenotazioneSpiaggia);
                }
            });
        }
        return prenotazioneRepository.save(prenotazione);
    }

    public Prenotazione updatePrenotazione(Prenotazione prenotazione, String token) {
        if (!tokenService.checkToken(token, Role.ADMIN) && !tokenService.checkToken(token, Role.RECEPTION)) {
            return null;
        }
        var prenotazioneToUpdate = prenotazioneRepository.findById(prenotazione.getId());
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