package cameo.impianto_balneare.quartz.service;

import cameo.impianto_balneare.quartz.job.SendMailJob;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SendMailService {
    public JobDetail createJob(UUID mailUUID) {
        return JobBuilder.newJob(SendMailJob.class)
                .withIdentity(mailUUID.toString(), "email_job")
                .withDescription("Send Email Job")
                .storeDurably()
                .requestRecovery()
                .usingJobData("mailUUID", mailUUID.toString())
                .build();
    }
    public Trigger createTrigger(JobDetail job){
        return TriggerBuilder.newTrigger()
                .forJob(job)
                .withIdentity(job.getKey().getName(), "email_trigger")
                .withDescription("Send Email Trigger")
                .withSchedule(SimpleScheduleBuilder.repeatHourlyForever())
                .build();
    }
}
