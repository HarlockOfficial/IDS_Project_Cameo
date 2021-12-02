package cameo.impianto_balneare.Repository;

import cameo.impianto_balneare.Entity.MenuElement;
import cameo.impianto_balneare.Entity.Ombrellone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OmbrelloneRepository extends JpaRepository<Ombrellone, UUID> {
}
