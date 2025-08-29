package com.example.streams.util;

import com.example.streams.model.*;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class SampleData {
    private SampleData(){}

    public static final List<Course> COURSES = Arrays.asList(
            new Course("CS101","Algorithms",5),
            new Course("CS102","Data Structures",5),
            new Course("CS103","Databases",4),
            new Course("CS104","Networks",4),
            new Course("CS105","Java Programming",6)
    );

    public static List<Student> generate(int count) {
        String[] fn = {"Ivan","Olena","Petro","Iryna","Andrii","Sofiia","Mykola","Nazar","Kateryna","Oksana"};
        String[] ln = {"Shevchenko","Kovalenko","Tkachenko","Melnyk","Bondarenko","Morozenko","Boyko","Lysenko"};
        Gender[] genders = Gender.values();
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        return IntStream.range(0, count).mapToObj(i -> {
            Student.Builder b = new Student.Builder()
                    .id(i + 1)
                    .firstName(fn[rnd.nextInt(fn.length)])
                    .lastName(ln[rnd.nextInt(ln.length)])
                    .gender(genders[rnd.nextInt(genders.length)])
                    .birthDate(LocalDate.now().minusYears(18 + rnd.nextInt(5))
                            .withMonth(1 + rnd.nextInt(12))
                            .withDayOfMonth(1 + rnd.nextInt(25)))
                    .group("CS-" + (10 + rnd.nextInt(5)));
            // випадкові оцінки
            COURSES.forEach(c -> b.grade(c, 50 + rnd.nextInt(51)));
            return b.build();
        }).collect(Collectors.toList());
    }
}
