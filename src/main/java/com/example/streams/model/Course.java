package com.example.streams.model;

import java.util.Objects;

public final class Course {
    private final String code;   // e.g. CS101
    private final String title;  // e.g. Algorithms
    private final int credits;

    public Course(String code, String title, int credits) {
        this.code = code;
        this.title = title;
        this.credits = credits;
    }

    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return Objects.equals(code, course.code);
    }

    @Override public int hashCode() { return Objects.hash(code); }

    @Override public String toString() {
        return code + " (" + title + ", " + credits + "cr)";
    }
}
