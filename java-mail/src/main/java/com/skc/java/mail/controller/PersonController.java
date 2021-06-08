package com.skc.java.mail.controller;

import com.skc.java.mail.entity.Person;
import com.skc.java.mail.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import java.util.UUID;

@RestController
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @PostMapping("signup")
    public String save(@RequestBody Person person) throws Exception{
        person.setStatus(0);
        String token = UUID.randomUUID().toString();
        person.setToken(token);

        personRepository.save(person);

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(person.getUsername());
        helper.setSubject("Verify your email");
        String url = "http://localhost:8091/verifyEmail/" + person.getUsername() + "/" + token;
        helper.setText("<h1><a href='"+ url +"'> Verify your email </a></h1>", true);

        javaMailSender.send(msg);

        return "Successfully Registered.";
    }

    @GetMapping("verifyEmail/{email}/{token}")
    public String verifyEmail(@PathVariable String email, @PathVariable String token){
        Person person = personRepository.findByUsername(email);

        String status = "failure";
        if(person.getToken().equals(token)){
            status = "Verified successfully";
            person.setStatus(1);
            personRepository.save(person);
        }
        return status;
    }

}
