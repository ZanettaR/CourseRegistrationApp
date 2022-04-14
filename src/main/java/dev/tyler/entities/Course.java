package dev.tyler.entities;

import java.util.Date;

public class Course {
    private int id;
    private String courseName;
    private String description;
    private int capacity;
    private int availability;
    private long registrationDate;

    public Course() {
    }

    public Course(int id, String courseName, String description, int capacity, int availability, long registrationDate) {
        this.id = id;
        this.courseName = courseName;
        this.description = description;
        this.capacity = capacity;
        this.availability = availability;
        this.registrationDate = registrationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public long getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(long registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "Class{" +
                "id=" + id +
                ", courseNumber=" + courseName +
                ", description='" + description + '\'' +
                ", capacity=" + capacity +
                ", availability=" + availability +
                ", registrationDate=" + new Date(registrationDate).toString() +
                '}';
    }
}
