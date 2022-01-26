package cameo.impianto_balneare.service;

import cameo.impianto_balneare.entity.Ombrellone;
import cameo.impianto_balneare.entity.Prenotazione;
import cameo.impianto_balneare.entity.PrenotazioneSpiaggia;
import cameo.impianto_balneare.entity.Role;
import cameo.impianto_balneare.repository.OmbrelloneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OmbrelloneService {
    private final OmbrelloneRepository ombrelloneRepository;
    private final PrenotazioneService prenotazioneService;
    private final TokenService tokenService;

    @Autowired
    public OmbrelloneService(OmbrelloneRepository ombrelloneRepository, PrenotazioneService prenotazioneService, TokenService tokenService) {
        this.ombrelloneRepository = ombrelloneRepository;
        this.prenotazioneService = prenotazioneService;
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

    public List<Ombrellone> getAllFreeOmbrelloni(ZonedDateTime startDate, ZonedDateTime endDate) {
        var ombrelloniOccupati = prenotazioneService.getAllPrenotazioni().stream()
                .filter(e -> e.getSpiaggiaPrenotazioniList() != null)
                .flatMap(e -> e.getSpiaggiaPrenotazioniList().stream())
                //la prenotazione deve iniziare prima della data finale decisa dall'utente
                .filter(e -> e.getStartDate().isBefore(endDate) &&
                        // e deve iniziare dopo la data iniziale decisa dall'utente
                        (e.getStartDate().isAfter(startDate) || e.getStartDate().equals(startDate)) &&
                        // e deve finire dopo della data iniziale decisa dall'utente
                        e.getEndDate().isAfter(startDate) &&
                        // e deve finire prima della data finale decisa dall'utente
                        (e.getEndDate().isBefore(endDate) || e.getEndDate().equals(endDate)) &&
                        // e la data di fine deve essere successiva a quella d'inizio
                        e.getStartDate().isBefore(e.getEndDate()) &&
                        // e l'ombrellone deve essere disponibile, quindi deve avere data d'inizio successiva a quella decisa dall'utente
                        (e.getOmbrellone().getStartDate().isAfter(startDate) || e.getOmbrellone().getStartDate().equals(startDate)) &&
                        // e l'ombrellone deve avere data di fine precedente a quella decisa dall'utente
                        (e.getOmbrellone().getEndDate().isAfter(endDate) || e.getOmbrellone().getEndDate().equals(endDate)))
                .map(PrenotazioneSpiaggia::getOmbrellone).collect(Collectors.toList());
        var ombrelloniList = getAllOmbrelloni();
        ombrelloniOccupati.forEach(e ->
            ombrelloniList.stream().filter(om -> om.getId().equals(e.getId()))
                    .findFirst().ifPresent(ombrelloniList::remove)
        );
        return ombrelloniList;
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
        if (ombrelloneRepository.findAll()
                .stream().anyMatch(e ->
                        ombrellone.getNumberColumn() == e.getNumberColumn()
                                &&
                        ombrellone.getNumberRow() == e.getNumberRow()
                )
        ) {
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
            ombrelloneToEdit.setNumberColumn(ombrellone.getNumberColumn());
            ombrelloneToEdit.setPrice(ombrellone.getPrice());
            ombrelloneToEdit.setNumberRow(ombrellone.getNumberRow());
            return ombrelloneRepository.save(ombrelloneToEdit);
        }
        return null;
    }

    /**
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
            var now = ZonedDateTime.now();
            var hasPrenotazioniPendenti = prenotazioneService.getAllPrenotazioni().stream()
                    .filter(e -> e.getSpiaggiaPrenotazioniList() != null)
                    .flatMap(e -> e.getSpiaggiaPrenotazioniList().stream())
                    .filter(e -> e.getStartDate().isAfter(now) ||
                            e.getStartDate().equals(now) ||
                            e.getEndDate().isAfter(now) ||
                            e.getEndDate().equals(now))
                    .anyMatch(e -> e.getOmbrellone().getId().equals(id));
            if (hasPrenotazioniPendenti) {
                ombrelloneToDelete.get().setEndDate(now);
                //TODO create cron task to remove ombrellone when all current prenotazioni are past
            }else {
                ombrelloneRepository.delete(ombrelloneToDelete.get());
                return ombrelloneToDelete.get();
            }
        }
        return null;
    }

    public List<Prenotazione> getPrenotazioneByOmbrellone(UUID ombrelloneId, ZonedDateTime dataInizio, ZonedDateTime dataFine, String tokenId) {
        var ombrellone = ombrelloneRepository.findById(ombrelloneId);
        if (!tokenService.checkToken(tokenId, Role.ADMIN) && !tokenService.checkToken(tokenId, Role.RECEPTION)) {
            return null;
        }
        return ombrellone.map(value -> value
                .getListaPrenotazioni().stream().filter(
                        prenotazione ->
                                (prenotazione.getStartDate().isAfter(dataInizio) || prenotazione.getStartDate().equals(dataInizio)) &&
                                        (prenotazione.getEndDate().isBefore(dataFine) || prenotazione.getEndDate().equals(dataFine))
                ).map(PrenotazioneSpiaggia::getPrenotazione).collect(Collectors.toList())).orElse(null);
    }
}