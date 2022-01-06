package cameo.impianto_balneare.Repository;

import cameo.impianto_balneare.Entity.MenuOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MenuOrderRepository extends JpaRepository<MenuOrder, UUID> {}
