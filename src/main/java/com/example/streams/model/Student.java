package com.example.streams.model;

import java.time.LocalDate;
import java.util.*;

public final class Student {
    private final long id;
    private final String firstName;
    private final String lastName;
    private final Gender gender;
    private final LocalDate birthDate;
    private final String group; // академгрупа, наприклад "CS-12"
    private final Map<Course, Integer> grades; // 0..100

    private Student(Builder b) {
        this.id = b.id;
        this.firstName = Objects.requireNonNull(b.firstName);
        this.lastName  = Objects.requireNonNull(b.lastName);
        this.gender    = Objects.requireNonNull(b.gender);
        this.birthDate = Objects.requireNonNull(b.birthDate);
        this.group     = Objects.requireNonNull(b.group);
        this.grades    = Collections.unmodifiableMap(new LinkedHashMap<>(b.grades));
    }

    public long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public Gender getGender() { return gender; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getGroup() { return group; }
    public Map<Course, Integer> getGrades() { return grades; }

    public OptionalDouble averageGrade() {
        return grades.values().stream().mapToInt(i -> i).average();
    }

    public static class Builder {
        private long id;
        private String firstName;
        private String lastName;
        private Gender gender;
        private LocalDate birthDate;
        private String group;
        private Map<Course, Integer> grades = new LinkedHashMap<>();

        public Builder id(long id) { this.id = id; return this; }
        public Builder firstName(String v) { this.firstName = v; return this; }
        public Builder lastName(String v) { this.lastName = v; return this; }
        public Builder gender(Gender v) { this.gender = v; return this; }
        public Builder birthDate(LocalDate v) { this.birthDate = v; return this; }
        public Builder group(String v) { this.group = v; return this; }
        public Builder grade(Course c, int g) { this.grades.put(c, g); return this; }
        public Student build() { return new Student(this); }
    }

    @Override public String toString() {
        return String.format("%d %s %s [%s] %s avg=%.1f",
                id, firstName, lastName, group, gender,
                averageGrade().orElse(Double.NaN));
    }
}
