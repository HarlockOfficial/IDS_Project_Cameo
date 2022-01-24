package cameo.impianto_balneare.service;

import cameo.impianto_balneare.entity.Prenotazione;
import cameo.impianto_balneare.entity.Role;
import cameo.impianto_balneare.entity.StatoPrenotazione;
import cameo.impianto_balneare.repository.PrenotazioneRepository;
import cameo.impianto_balneare.repository.PrenotazioneSpiaggiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
        if (user.getRole() == Role.ADMIN || user.getRole() == Role.RECEPTION) {
            return prenotazioneRepository.findAll();
        }
        if (user.getRole() != Role.USER) {
            return null;
        }
        return user.getPrenotazioni();
    }

    public Prenotazione createPrenotazione(Prenotazione prenotazione, String token) {
        var user = tokenService.getUserFromUUID(token);
        if (user.getRole() == Role.USER) {
            prenotazione.setUser(user);
        }
        if(prenotazione.getSpiaggiaPrenotazioniList().size() != 0){
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