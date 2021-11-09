package cameo.impianto_balneare.Service;

import cameo.impianto_balneare.Entity.Utente;
import cameo.impianto_balneare.Repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtenteService {
    private final UtenteRepository utenteRepository;

    @Autowired
    public UtenteService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    public List<Utente> getAllUsers(){
        return utenteRepository.findAll();
    }

    public Utente getUser(Long id){
       var user = utenteRepository.findById(id);
       return user.orElse(null);
    }
}
