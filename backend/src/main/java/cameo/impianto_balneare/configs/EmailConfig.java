package cameo.impianto_balneare.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {
    private final static String EMAIL_USERNAME = "";
    private final static String EMAIL_PASSWORD = "";
    private final static String EMAIL_HOST = "smtp.gmail.com";
    private final static int EMAIL_PORT = 587;
    private final static String EMAIL_PROTOCOL = "smtp";
    private final static String EMAIL_SMTP_AUTH = "true";
    private final static String EMAIL_SMTP_STARTTLS_ENABLE = "true";
    private final static String EMAIL_DEBUG = "true";

    @Bean
    public JavaMailSender getMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(EMAIL_HOST);
        mailSender.setPort(EMAIL_PORT);

        mailSender.setUsername(EMAIL_USERNAME);
        mailSender.setPassword(EMAIL_PASSWORD);

        Properties javaMailProperties = mailSender.getJavaMailProperties();
        javaMailProperties.put("mail.smtp.starttls.enable", EMAIL_SMTP_STARTTLS_ENABLE);
        javaMailProperties.put("mail.smtp.auth", EMAIL_SMTP_AUTH);
        javaMailProperties.put("mail.transport.protocol", EMAIL_PROTOCOL);
        javaMailProperties.put("mail.debug", EMAIL_DEBUG);

        return mailSender;
    }
}
