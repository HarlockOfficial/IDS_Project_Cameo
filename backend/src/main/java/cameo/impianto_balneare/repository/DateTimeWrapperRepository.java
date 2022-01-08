package cameo.impianto_balneare.repository;

import cameo.impianto_balneare.entity.DateTimeWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DateTimeWrapperRepository extends JpaRepository<DateTimeWrapper, UUID> { }
