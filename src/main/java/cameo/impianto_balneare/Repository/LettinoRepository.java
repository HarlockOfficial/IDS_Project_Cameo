package cameo.impianto_balneare.Repository;

import cameo.impianto_balneare.Entity.Lettino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LettinoRepository extends JpaRepository<Lettino, UUID> {
}
