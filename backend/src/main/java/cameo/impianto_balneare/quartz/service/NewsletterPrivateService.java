package cameo.impianto_balneare.quartz.service;

import cameo.impianto_balneare.entity.Newsletter;
import cameo.impianto_balneare.repository.NewsletterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NewsletterPrivateService {
    private final NewsletterRepository newsletterRepository;

    @Autowired
    public NewsletterPrivateService(NewsletterRepository newsletterRepository) {
        this.newsletterRepository = newsletterRepository;
    }

    public Newsletter getNewsletterById(UUID id) {
        return newsletterRepository.findAll().stream().filter(newsletter -> newsletter.getId().equals(id)).findFirst().orElse(null);
    }
}
