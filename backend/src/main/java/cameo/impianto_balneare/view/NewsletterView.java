package cameo.impianto_balneare.view;

import cameo.impianto_balneare.entity.Newsletter;
import cameo.impianto_balneare.service.NewsletterService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class NewsletterView implements GlobalExceptionHandler {
    private final NewsletterService newsletterService;

    @Autowired
    public NewsletterView(NewsletterService newsletterService) {
        this.newsletterService = newsletterService;
    }

    @RequestMapping(value = "/newsletter", method = RequestMethod.GET)
    public ResponseEntity<List<Newsletter>> getAllNewsletter(@RequestHeader("token") String token) {
        List<Newsletter> newsletter = newsletterService.getAllNewsletter(token);
        if (newsletter == null || newsletter.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(newsletter);
    }

    @RequestMapping(value = "/newsletter/future", method = RequestMethod.GET)
    public ResponseEntity<List<Newsletter>> getAllFutureNewsletter(@RequestHeader("token") String token) {
        List<Newsletter> newsletter = newsletterService.getAllFutureNewsletter(token);
        if (newsletter == null || newsletter.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(newsletter);
    }

    @RequestMapping(value = "/newsletter/{id}", method = RequestMethod.GET)
    public ResponseEntity<Newsletter> getNewsletterById(@PathVariable UUID id, @RequestHeader("token") String token) {
        Newsletter newsletter = newsletterService.getNewsletterById(id, token);
        if (newsletter == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(newsletter);
    }

    @RequestMapping(value = "/newsletter", method = RequestMethod.POST)
    public ResponseEntity<Newsletter> addNewsletter(@RequestBody Newsletter newsletter, @RequestHeader("token") String token) throws SchedulerException {
        Newsletter newsletter1 = newsletterService.addNewsletter(newsletter, token);
        if (newsletter1 == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(newsletter1);
    }

    @RequestMapping(value = "/newsletter", method = RequestMethod.PUT)
    public ResponseEntity<Newsletter> editNewsletter(@RequestBody Newsletter newsletter, @RequestHeader("token") String token) throws SchedulerException {
        Newsletter newsletter1 = newsletterService.editNewsletter(newsletter, token);
        if (newsletter1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newsletter1);
    }

    @RequestMapping(value = "/newsletter/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Newsletter> deleteNewsletter(@PathVariable UUID id, @RequestHeader("token") String token) throws SchedulerException {
        Newsletter newsletter = newsletterService.deleteNewsletter(id, token);
        if (newsletter == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newsletter);
    }
}
