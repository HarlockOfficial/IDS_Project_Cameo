package cameo.impianto_balneare.repository;

import cameo.impianto_balneare.entity.MenuOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MenuOrderRepository extends JpaRepository<MenuOrder, UUID> {}
