package cameo.impianto_balneare.repository;

import cameo.impianto_balneare.entity.DateTimeWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DateTimeWrapperRepository extends JpaRepository<DateTimeWrapper, UUID> { }
