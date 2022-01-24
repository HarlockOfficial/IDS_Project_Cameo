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
                .filter(e -> e.getPrenotazioneSpiaggia() != null)
                .flatMap(e -> e.getPrenotazioneSpiaggia().stream())
                //la prenotazione deve iniziare prima della data finale decisa dall'utente
                .filter(e -> e.getDataInizio().isBefore(endDate) &&
                        // e deve iniziare dopo la data iniziale decisa dall'utente
                        (e.getDataInizio().isAfter(startDate) || e.getDataInizio().equals(startDate)) &&
                        // e deve finire dopo della data iniziale decisa dall'utente
                        e.getDataFine().isAfter(startDate) &&
                        // e deve finire prima della data finale decisa dall'utente
                        (e.getDataFine().isBefore(endDate) || e.getDataFine().equals(endDate)) &&
                        // e la data di fine deve essere successiva a quella d'inizio
                        e.getDataInizio().isBefore(e.getDataFine()) &&
                        // e l'ombrellone deve essere disponibile, quindi deve avere data d'inizio successiva a quella decisa dall'utente
                        (e.getOmbrellone().getDataInizio().isAfter(startDate) || e.getOmbrellone().getDataInizio().equals(startDate)) &&
                        // e l'ombrellone deve avere data di fine precedente a quella decisa dall'utente
                        (e.getOmbrellone().getDataFine().isAfter(endDate) || e.getOmbrellone().getDataFine().equals(endDate)))
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
            System.out.println("Token non valido");
            return null;
        }
        if (ombrelloneRepository.findAll().stream().anyMatch(e -> ((ombrellone.getOmbrelloneColumnNumber() == e.getOmbrelloneColumnNumber()) && (ombrellone.getOmbrelloneRowNumber() == ombrellone.getOmbrelloneRowNumber())))) {
            System.out.println("Ombrellone già esistente");
            return null;
        }
        System.out.println("Ombrellone creato");
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
                    .filter(e -> e.getPrenotazioneSpiaggia() != null)
                    .flatMap(e -> e.getPrenotazioneSpiaggia().stream())
                    .filter(e -> e.getDataInizio().isAfter(now) ||
                            e.getDataInizio().equals(now) ||
                            e.getDataFine().isAfter(now) ||
                            e.getDataFine().equals(now))
                    .anyMatch(e -> e.getOmbrellone().getId().equals(id));
            if (hasPrenotazioniPendenti) {
                ombrelloneToDelete.get().setDataFine(now);
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
                                (prenotazione.getDataInizio().isAfter(dataInizio) || prenotazione.getDataInizio().equals(dataInizio)) &&
                                        (prenotazione.getDataFine().isBefore(dataFine) || prenotazione.getDataFine().equals(dataFine))
                ).map(PrenotazioneSpiaggia::getPrenotazione).collect(Collectors.toList())).orElse(null);
    }
}