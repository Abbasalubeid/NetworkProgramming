package se.kth.SpringQuizGame.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.FileInputStream;
import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() throws Exception {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties props = new Properties();
        props.load(new FileInputStream("/Users/abbas/Desktop/mail-config.properties"));

        String username = props.getProperty("mail.username");
        String password = props.getProperty("mail.password");

        mailSender.setHost("smtp.kth.se");
        mailSender.setPort(587);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", "true");
        mailProperties.put("mail.smtp.starttls.enable", "true");

        mailSender.setJavaMailProperties(mailProperties);
        return mailSender;
    }
}
