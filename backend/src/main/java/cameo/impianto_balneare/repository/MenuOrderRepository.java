package cameo.impianto_balneare.repository;

import cameo.impianto_balneare.entity.MenuOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MenuOrderRepository extends JpaRepository<MenuOrder, UUID> {}
