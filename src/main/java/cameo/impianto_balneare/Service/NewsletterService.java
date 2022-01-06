package cameo.impianto_balneare.Service;

import cameo.impianto_balneare.Entity.Newsletter;
import cameo.impianto_balneare.Entity.Role;
import cameo.impianto_balneare.Repository.NewsletterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NewsletterService {
    private final NewsletterRepository newsletterRepository;
    private final TokenService tokenService;

    @Autowired
    public NewsletterService(NewsletterRepository newsletterRepository, TokenService tokenService) {
        this.newsletterRepository = newsletterRepository;
        this.tokenService = tokenService;
    }

    public List<Newsletter> getAllNewsletter(String token) {
        if(tokenService.checkToken(token, Role.ADMIN) || tokenService.checkToken(token, Role.EVENT_MANAGER)) {
            return newsletterRepository.findAll();
        }
        return null;
    }

    public List<Newsletter> getAllFutureNewsletter(String token) {
        var newsletters = getAllNewsletter(token);
        if(newsletters != null) {
            return newsletters.stream()
                    .filter(newsletter ->
                            newsletter.getTimes().stream().anyMatch(time ->
                                    time.getDateTime().isAfter(ZonedDateTime.now())
                            )
                    ).collect(Collectors.toList());
        }
        return null;
    }

    public Newsletter getNewsletterById(UUID id, String token) {
        if(tokenService.checkToken(token, Role.ADMIN) || tokenService.checkToken(token, Role.EVENT_MANAGER)) {
            return newsletterRepository.findById(id).orElse(null);
        }
        return null;
    }

    public Newsletter addNewsletter(Newsletter newsletter, String token) {
        if(tokenService.checkToken(token, Role.ADMIN) || tokenService.checkToken(token, Role.EVENT_MANAGER)) {
            return newsletterRepository.save(newsletter);
        }
        return null;
    }

    public Newsletter editNewsletter(Newsletter newsletter, String token) {
        if(tokenService.checkToken(token, Role.ADMIN) || tokenService.checkToken(token, Role.EVENT_MANAGER)) {
            var oldNewsletter = getNewsletterById(newsletter.getId(), token);
            if(oldNewsletter != null) {
                oldNewsletter.setEvent(newsletter.getEvent());
                oldNewsletter.setMailContent(newsletter.getMailContent());
                oldNewsletter.setTimes(newsletter.getTimes());
                return newsletterRepository.save(oldNewsletter);
            }
        }
        return null;
    }

    public Newsletter deleteNewsletter(UUID id, String token) {
        if(tokenService.checkToken(token, Role.ADMIN) || tokenService.checkToken(token, Role.EVENT_MANAGER)) {
            var newsletter = getNewsletterById(id, token);
            if(newsletter != null) {
                newsletterRepository.delete(newsletter);
                return newsletter;
            }
        }
        return null;
    }
}
