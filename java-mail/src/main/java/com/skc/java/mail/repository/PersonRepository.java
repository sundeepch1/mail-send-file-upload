package com.skc.java.mail.repository;

import com.skc.java.mail.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    public Person findByUsername(String email);
}
