package cameo.impianto_balneare.Repository;

import cameo.impianto_balneare.Entity.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NewsletterRepository extends JpaRepository<Newsletter, UUID> { }
