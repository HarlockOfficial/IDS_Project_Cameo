package cameo.impianto_balneare.quartz.job;

import cameo.impianto_balneare.entity.Event;
import cameo.impianto_balneare.quartz.service.DateTimeWrapperPrivateService;
import cameo.impianto_balneare.quartz.service.NewsletterPrivateService;
import cameo.impianto_balneare.quartz.service.UserPrivateService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class SendMailJob implements Job {
    private static final String emailSender = "";
    private final NewsletterPrivateService newsletterService;
    private final DateTimeWrapperPrivateService dateTimeWrapperService;
    private final UserPrivateService userService;
    private final JavaMailSender mailSender;

    @Autowired
    public SendMailJob(NewsletterPrivateService newsletterService, DateTimeWrapperPrivateService dateTimeWrapperService,
                       UserPrivateService userService, JavaMailSender mailSender) {
        this.newsletterService = newsletterService;
        this.dateTimeWrapperService = dateTimeWrapperService;
        this.userService = userService;
        this.mailSender = mailSender;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        var mailUUID = jobExecutionContext.getJobDetail().getJobDataMap().getString("mailUUID");

        var newsletter = newsletterService.getNewsletterById(UUID.fromString(mailUUID));

        var mailContent = newsletter.getMailContent();
        var event = newsletter.getEvent();
        var dateList = newsletter.getTimes();

        if (dateList == null || dateList.isEmpty()) {
            return;
        }
        var date = new ArrayList<>(dateList).get(0);

        if (date.getDateTime().isBefore(ZonedDateTime.now()) || date.getDateTime().isEqual(ZonedDateTime.now())) {
            var result = false;
            if (event == null) {
                result = sendMailToAll(mailContent);
            } else {
                result = sendMailToEventParticipants(mailContent, event);
            }
            if (result) {
                dateTimeWrapperService.deleteNewsletterDateTime(date.getId());
            }
        }
    }

    private boolean sendMailToAll(String mailContent) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        var subject = "Newsletter Impianto Balneare del " + formatter.format(ZonedDateTime.now());
        var mailingList = userService.getAllUserEmail();
        return sendMail(mailingList, subject, mailContent);
    }

    private boolean sendMailToEventParticipants(String mailContent, Event event) {
        var prenotazione = event.getPrenotazione();
        if(prenotazione == null) {
            return false;
        }
        var mailingList = prenotazione.stream()
                .map(p -> p.getUser().getEmail()).collect(Collectors.toList());
        var subject = "Evento: " + event.getName() + " - Newsletter Impianto Balneare";
        return sendMail(mailingList, subject, mailContent);
    }

    private boolean sendMail(List<String> to, String subject, String content) {
        try {
            var message = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.toString());
            helper.setFrom(emailSender);
            for (String s : to) {
                helper.addBcc(s);
            }
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
            return true;
        } catch (MessagingException e) {
            System.out.println("Error sending mail: " + e.getMessage());
        }
        return false;
    }
}
