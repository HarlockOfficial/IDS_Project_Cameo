package cameo.impianto_balneare.Service;

import cameo.impianto_balneare.Entity.Role;
import cameo.impianto_balneare.Entity.Lettino;
import cameo.impianto_balneare.Repository.LettinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class LettinoService {
    private final LettinoRepository lettinoRepository;
    private final TokenService tokenService;

    @Autowired
    public LettinoService(LettinoRepository lettinoRepository, TokenService tokenService) {
        this.lettinoRepository = lettinoRepository;
        this.tokenService = tokenService;
    }

    /**
     * Ritorna lista di tutti i lettini
     *
     * @return lista di tutti i lettini
     */
    public List<Lettino> getAllLettino() {
        return lettinoRepository.findAll();
    }

    //TODO
    public List<Lettino> getAllFreeLettino(ZonedDateTime fromDate, ZonedDateTime toDate) {
        return null;
    }

    /**
     * restituisce un lettino dato un id
     *
     * @param id l'id del lettino da restituire
     * @return lettino identificato dall'id
     */
    public Lettino getLettino(UUID id) {
        var lettino = lettinoRepository.findById(id);
        return lettino.orElse(null);
    }

    /**
     * creazione di un lettino
     *
     * @param lettino lettino da creare
     * @param tokenId    il token
     * @return lettino creato
     */
    public Lettino createLettino(Lettino lettino, String tokenId) {
        if (!tokenService.checkToken(tokenId, Role.ADMIN)) {
            return null;
        }
        return lettinoRepository.save(lettino);
    }

    /**
     * update di un lettino
     * @param lettino lettino gia modificato
     * @param tokenId    token
     * @return lettino modificato
     */
    public Lettino updateLettino(Lettino lettino, String tokenId) {
        if (!tokenService.checkToken(tokenId, Role.ADMIN)) {
            return null;
        }
        var lettinoToUpdate = lettinoRepository.findById(lettino.getId());
        if (lettinoToUpdate.isPresent()) {
            var lettinoToEdit = lettinoToUpdate.get();
            lettinoToEdit.setPrice(lettino.getPrice());
            return lettinoRepository.save(lettinoToEdit);
        }
        return null;
    }

    /**
     * TODO controllare prenotazioni pendenti;
     * eliminazione di un lettino
     * @param id id dello lettino
     * @param tokenId token
     * @return lettino eliminato
     */
    public Lettino deleteLettino(UUID id, String tokenId) {
        if (!tokenService.checkToken(tokenId, Role.ADMIN)) {
            return null;
        }
        var lettinoToDelete = lettinoRepository.findById(id);
        if (lettinoToDelete.isPresent()) {
            lettinoRepository.delete(lettinoToDelete.get());
            return lettinoToDelete.get();
        }
        return null;
    }
    
}
