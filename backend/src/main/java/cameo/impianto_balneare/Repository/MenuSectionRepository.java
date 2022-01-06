package cameo.impianto_balneare.Repository;

import cameo.impianto_balneare.Entity.MenuSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MenuSectionRepository extends JpaRepository<MenuSection, UUID> {
}
