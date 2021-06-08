package com.skc.java.mail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

@RestController
public class MailController {

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("mail1")
    public String mail1(){

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("sundeep.chaurasiya@gmail.com");
        //msg.setCc("a@a.com")
        //msg.setBcc("a@a.com")
        msg.setSubject("Testing from spring boot");
        msg.setText("Hello World \n Spring Boot Email");
        javaMailSender.send(msg);
        System.out.println("Mail Send Successfully.");
        return "success";
    }

    @GetMapping("mail2")
    public String mail2() throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        helper.setTo("sundeep.chaurasiya@gmail.com");
        //msg.setCc("a@a.com")
        //msg.setBcc("a@a.com")
        helper.setSubject("Testing from spring boot");
        helper.setText("<h1>Hello World Spring Boot Email</h1>");
        //default = text/plain
        javaMailSender.send(msg);
        System.out.println("Mail Send Successfully.");
        return "success";
    }

    @GetMapping("mail3")
    public String mail3() throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo("sundeep.chaurasiya@gmail.com");
        helper.setSubject("Testing from spring boot");
        helper.setText("<h1>Hello World Spring Boot Email</h1>", true);
        //default = text/html if we supply the true
        javaMailSender.send(msg);
        System.out.println("Mail Send Successfully.");
        return "success";
    }

    @GetMapping("mail4")
    public String mail4() throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo("sundeep.chaurasiya@gmail.com");
        helper.setSubject("Testing from spring boot");
        helper.setText("<h1>Hello World Spring Boot Email</h1>", true);
        //default = text/html if we supply the true
        helper.addAttachment("myappconfig.properties", new ClassPathResource("application.properties"));
        javaMailSender.send(msg);
        System.out.println("Mail Send Successfully.");
        return "success";
    }

    @GetMapping("mail5")
    public String mail5() throws MessagingException, IOException {
        JavaMailSenderImpl javaMailSender = getJavaMailSender();

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo("sundeep.chaurasiya@gmail.com");
        helper.setSubject("Hello Mail");
        helper.setText("<h1>Hello</h1>", true);
        //default = text/html if we supply the true
        javaMailSender.send(msg);
        System.out.println("Mail Send Successfully.");
        return "success";
    }

    public JavaMailSenderImpl getJavaMailSender() throws MessagingException, IOException {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setUsername("******.******@gmail.com");
    mailSender.setPassword("*******");
    mailSender.setHost("smtp.gmail.com");
    mailSender.setPort(587);

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.starttls.required", true);
        properties.put("mail.smtp.connectiontimeout", 5000);
        properties.put("mail.smtp.timeout", 5000);
        properties.put("mail.smtp.writetimeout", 5000);

        return mailSender;
    }
}
