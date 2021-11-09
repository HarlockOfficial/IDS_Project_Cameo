package cameo.impianto_balneare.Repository;

import cameo.impianto_balneare.Entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {}
