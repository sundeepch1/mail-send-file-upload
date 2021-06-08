package com.skc.entity.response.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("service6/{yearOfBirth}")
    public ResponseEntity<String> service6(@PathVariable Integer yearOfBirth){
        ResponseEntity<String> responseEntity = null;
        Integer age = null;
        if(yearOfBirth>2020){
            responseEntity = ResponseEntity.badRequest().body("Year of birth should not be greater than 2020.");
        }else {
            age = 2020 - yearOfBirth;
            responseEntity = ResponseEntity.ok().body("Your age is " + age);
        }
        return responseEntity;
    }

    @GetMapping("service7")
    public ResponseEntity<String> service7(){
        return ResponseEntity.ok().body("<h1>Hello to all</h1>");
    }

    @GetMapping("service8")
    public ResponseEntity<String> service8(){
        return ResponseEntity.ok()
                .header("content-type", "text/plain").body("<h1>Hello to all</h1>");
    }

    @GetMapping("service9")
    public ResponseEntity<String> service9(){
        return ResponseEntity.ok()
                .header("content-type", "text/html")
                .body("<h1>Hello to all</h1>");
    }

    int counter = 0;

    @GetMapping("service10")
    public ResponseEntity<String> service10(){
        counter++;
        return ResponseEntity.ok()
                .header("content-type", "text/html")
                .header("refresh", "4")
                .body("<h1>counter value : " + counter + "</h1>");
    }

    @GetMapping("service11")
    public ResponseEntity<String> service11(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "text/html");
        headers.add("refresh", "4");

        counter ++;

        String responseBody = "<h1>counter value : " + counter + "</h1>";

        ResponseEntity<String> responseEntity =
                new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
        return responseEntity;
    }
}
