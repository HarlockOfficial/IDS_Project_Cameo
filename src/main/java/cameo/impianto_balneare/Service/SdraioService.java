package cameo.impianto_balneare.Service;

import cameo.impianto_balneare.Entity.Sdraio;
import cameo.impianto_balneare.Entity.Role;
import cameo.impianto_balneare.Repository.SdraioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SdraioService {
    private final SdraioRepository sdraioRepository;
    private final TokenService tokenService;

    @Autowired
    public SdraioService(SdraioRepository sdraioRepository, TokenService tokenService) {
        this.sdraioRepository = sdraioRepository;
        this.tokenService = tokenService;
    }

    /**
     * Ritorna lista di tutti gli sdraio
     *
     * @return lista di tutti gli sdraio
     */
    public List<Sdraio> getAllSdraio() {
        return sdraioRepository.findAll();
    }

    //TODO
    public List<Sdraio> getAllFreeSdraio(ZonedDateTime fromDate, ZonedDateTime toDate) {
        return null;
    }

    /**
     * restituisce un sdraio dato un id
     *
     * @param id l'id dello sdraio da restituire
     * @return l'sdraio identificato dall'id
     */
    public Sdraio getSdraio(UUID id) {
        var sdraio = sdraioRepository.findById(id);
        return sdraio.orElse(null);
    }

    /**
     * creazione di uno sdraio
     *
     * @param sdraio sdraio da creare
     * @param tokenId    il token
     * @return sdraio creato
     */
    public Sdraio createSdraio(Sdraio sdraio, String tokenId) {
        if (!tokenService.checkToken(tokenId, Role.ADMIN)) {
            return null;
        }
        return sdraioRepository.save(sdraio);
    }

    /**
     * update di uno sdraio
     * @param sdraio sdraio gia modificato
     * @param tokenId    token
     * @return lo sdraio modificato
     */
    public Sdraio updateSdraio(Sdraio sdraio, String tokenId) {
        if (!tokenService.checkToken(tokenId, Role.ADMIN)) {
            return null;
        }
        var sdraioToUpdate = sdraioRepository.findById(sdraio.getId());
        if (sdraioToUpdate.isPresent()) {
            var sdraioToEdit = sdraioToUpdate.get();
            sdraioToEdit.setPrezzo(sdraio.getPrezzo());
            return sdraioRepository.save(sdraioToEdit);
        }
        return null;
    }

    /**
     * TODO controllare prenotazioni pendenti;
     * eliminazione di uno sdraio
     * @param id id dello sdraio
     * @param tokenId token
     * @return sdraio eliminato
     */
    public Sdraio deleteSdraio(UUID id, String tokenId) {
        if (!tokenService.checkToken(tokenId, Role.ADMIN)) {
            return null;
        }
        var sdraioToDelete = sdraioRepository.findById(id);
        if (sdraioToDelete.isPresent()) {
            sdraioRepository.delete(sdraioToDelete.get());
            return sdraioToDelete.get();
        }
        return null;
    }
    
}
