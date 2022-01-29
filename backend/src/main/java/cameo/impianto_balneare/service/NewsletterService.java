package cameo.impianto_balneare.service;

import cameo.impianto_balneare.entity.Newsletter;
import cameo.impianto_balneare.entity.Role;
import cameo.impianto_balneare.quartz.service.SendMailService;
import cameo.impianto_balneare.repository.NewsletterRepository;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
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
    private final SendMailService sendMailService;
    private final Scheduler scheduler;

    @Autowired
    public NewsletterService(NewsletterRepository newsletterRepository, TokenService tokenService, SendMailService sendMailService, Scheduler scheduler) {
        this.newsletterRepository = newsletterRepository;
        this.tokenService = tokenService;
        this.sendMailService = sendMailService;
        this.scheduler = scheduler;
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
            return newsletterRepository.findAll().stream().filter(e->e.getId().equals(id)).findFirst().orElse(null);
        }
        return null;
    }

    public Newsletter addNewsletter(Newsletter newsletter, String token) throws SchedulerException {
        if(tokenService.checkToken(token, Role.ADMIN) || tokenService.checkToken(token, Role.EVENT_MANAGER)) {
            scheduleJob(newsletter.getId());
            return newsletterRepository.save(newsletter);
        }
        return null;
    }

    public Newsletter editNewsletter(Newsletter newsletter, String token) throws SchedulerException {
        if(tokenService.checkToken(token, Role.ADMIN) || tokenService.checkToken(token, Role.EVENT_MANAGER)) {
            var oldNewsletter = getNewsletterById(newsletter.getId(), token);
            if(oldNewsletter != null) {
                oldNewsletter.setEvent(newsletter.getEvent());
                oldNewsletter.setMailContent(newsletter.getMailContent());
                oldNewsletter.setTimes(newsletter.getTimes());
                scheduleJob(oldNewsletter.getId());
                return newsletterRepository.save(oldNewsletter);
            }
        }
        return null;
    }

    private void scheduleJob(UUID newsletterId) throws SchedulerException {
        if(!scheduler.checkExists(JobKey.jobKey(newsletterId.toString(), "email_job"))) {
            var job = sendMailService.createJob(newsletterId);
            var trigger = sendMailService.createTrigger(job);
            scheduler.scheduleJob(job, trigger);
        }
        if(!scheduler.isStarted() && !scheduler.isShutdown()) {
            scheduler.start();
        }
    }

    public Newsletter deleteNewsletter(UUID id, String token) throws SchedulerException {
        if(tokenService.checkToken(token, Role.ADMIN) || tokenService.checkToken(token, Role.EVENT_MANAGER)) {
            var newsletter = getNewsletterById(id, token);
            if(newsletter != null) {
                scheduler.deleteJob(JobKey.jobKey(newsletter.getId().toString(), "email_job"));
                if(!scheduler.isStarted() && !scheduler.isShutdown()) {
                    scheduler.start();
                }
                newsletterRepository.delete(newsletter);
                return newsletter;
            }
        }
        return null;
    }
}
