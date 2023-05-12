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
    private final Date dateOfBirth;

    /**
     * Default constructor for newborn person
     *
     * @param fullName the name of the person
     */
    public Person(String fullName) {
        this(fullName, new Date());
    }

    /**
     * Default constructor
     *
     * @param fullName    the name of the person
     * @param dateOfBirth the date of birth of the person
     *                    if it is empty, then current date is used, so it is a newborn person
     */
    public Person(String fullName, Date dateOfBirth) {
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.age = Period.between(dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now()).getYears();
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public Optional<Date> getDateOfBirth() {
        return Optional.ofNullable(dateOfBirth);
    }

}