package cameo.impianto_balneare.Service;

import cameo.impianto_balneare.Entity.Ombrellone;
import cameo.impianto_balneare.Entity.Prenotazione;
import cameo.impianto_balneare.Entity.PrenotazioneSpiaggia;
import cameo.impianto_balneare.Entity.Role;
import cameo.impianto_balneare.Repository.OmbrelloneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OmbrelloneService {
    private final OmbrelloneRepository ombrelloneRepository;
    private final TokenService tokenService;

    @Autowired
    public OmbrelloneService(OmbrelloneRepository ombrelloneRepository, TokenService tokenService) {
        this.ombrelloneRepository = ombrelloneRepository;
        this.tokenService = tokenService;
    }

    /**
     * Ritorna lista di tutti gli ombrelloni
     *
     * @return lista di tutti gli ombrelloni
     */
    public List<Ombrellone> getAllOmbrelloni() {
        return ombrelloneRepository.findAll();
    }

    //TODO
    public List<Ombrellone> getAllFreeOmbrelloni(ZonedDateTime fromDate, ZonedDateTime toDate) {
        return null;
    }

    /**
     * restituisce un ombrellone dato un id
     *
     * @param id l'id dell'ombrellone da restituire
     * @return l'ombrellone identificato dall'id
     */
    public Ombrellone getOmbrellone(UUID id) {
        var ombrellone = ombrelloneRepository.findById(id);
        return ombrellone.orElse(null);
    }

    /**
     * creazione di un ombrellone
     *
     * @param ombrellone l'ombrellone da creare
     * @param tokenId    il token
     * @return l'ombrellone creato
     */
    public Ombrellone createOmbrellone(Ombrellone ombrellone, String tokenId) {
        if (!tokenService.checkToken(tokenId, Role.ADMIN)) {
            return null;
        }
        if (ombrelloneRepository.findAll().stream().anyMatch(e -> ((ombrellone.getOmbrelloneColumnNumber() == e.getOmbrelloneColumnNumber()) && (ombrellone.getOmbrelloneRowNumber() == ombrellone.getOmbrelloneRowNumber())))) {
            return null;
        }
        return ombrelloneRepository.save(ombrellone);
    }

    /**
     * update di un ombrellone
     * @param ombrellone ombrellone gia modificato
     * @param tokenId    token
     * @return l'ombrellone modificato
     */
    public Ombrellone updateOmbrellone(Ombrellone ombrellone, String tokenId) {
        if (!tokenService.checkToken(tokenId, Role.ADMIN)) {
            return null;
        }
        var ombrelloneToUpdate = ombrelloneRepository.findById(ombrellone.getId());
        if (ombrelloneToUpdate.isPresent()) {
            var ombrelloneToEdit = ombrelloneToUpdate.get();
            ombrelloneToEdit.setOmbrelloneColumnNumber(ombrellone.getOmbrelloneColumnNumber());
            ombrelloneToEdit.setPrezzo(ombrellone.getPrezzo());
            ombrelloneToEdit.setOmbrelloneRowNumber(ombrellone.getOmbrelloneRowNumber());
            return ombrelloneRepository.save(ombrelloneToEdit);
        }
        return null;
    }

    /**
     * TODO controllare prenotazioni pendenti;
     * eliminazione di un ombrellone
     * @param id id dell'ombrellone
     * @param tokenId token
     * @return ombrellone eliminato
     */
    public Ombrellone deleteOmbrellone(UUID id, String tokenId) {
        if (!tokenService.checkToken(tokenId, Role.ADMIN)) {
            return null;
        }
        var ombrelloneToDelete = ombrelloneRepository.findById(id);
        if (ombrelloneToDelete.isPresent()) {
            ombrelloneRepository.delete(ombrelloneToDelete.get());
            return ombrelloneToDelete.get();
        }
        return null;
    }

    public List<Prenotazione> getPrenotazioneByOmbrellone(UUID ombrelloneId, ZonedDateTime dataInizio, ZonedDateTime dataFine, String tokenId) {
        var ombrellone = ombrelloneRepository.findById(ombrelloneId);
        if (!tokenService.checkToken(tokenId, Role.ADMIN) && !tokenService.checkToken(tokenId, Role.RECEPTION)) {
            return null;
        }
        if (ombrellone.isPresent()) {
            return ombrellone.get()
                    .getListaPrenotazioni().stream().filter(
                            prenotazione ->
                                    (prenotazione.getDataInizio().isAfter(dataInizio) || prenotazione.getDataInizio().equals(dataInizio)) &&
                                    (prenotazione.getDataFine().isBefore(dataFine) || prenotazione.getDataFine().equals(dataFine))
                    ).map(PrenotazioneSpiaggia::getPrenotazione).collect(Collectors.toList());
        }
        return null;
    }
}