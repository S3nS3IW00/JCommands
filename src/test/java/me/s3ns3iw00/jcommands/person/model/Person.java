package me.s3ns3iw00.jcommands.person.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

/**
 * Represents a person
 */
public class Person {

    // Required fields
    private final String fullName;
    private final int age;
    private final Optional<Date> dateOfBirth;

    /**
     * Default constructor
     *
     * @param fullName    the name of the person
     * @param dateOfBirth the date of birth of the person
     *                    if it is empty, then current date is used, so it is a newborn person
     */
    public Person(String fullName, Optional<Date> dateOfBirth) {
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.age = Period.between(dateOfBirth.orElse(new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now()).getYears();
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public Optional<Date> getDateOfBirth() {
        return dateOfBirth;
    }

}