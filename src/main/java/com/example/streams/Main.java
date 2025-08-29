package com.example.streams;

import com.example.streams.model.Course;
import com.example.streams.model.Student;
import com.example.streams.service.StudentQueries;
import com.example.streams.service.StudentStatistics;
import com.example.streams.util.SampleData;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Student> students = SampleData.generate(50);
        Course java = SampleData.COURSES.stream()
                .filter(c -> c.getCode().equals("CS105")).findFirst().get();

        System.out.println("=== Перші 5 студентів з avg >= 80, відсортовані за ПРІЗВИЩЕМ ===");
        StudentQueries.topByAverage(students, 80, 5).forEach(System.out::println);

        System.out.println("\n=== Середній бал по групах ===");
        StudentQueries.averageByGroup(students).forEach((g, avg) ->
                System.out.printf("%s -> %.1f%n", g, avg));

        System.out.println("\n=== Найкращий по курсу Java Programming ===");
        StudentQueries.bestByCourse(students, java)
                .ifPresentOrElse(
                        s -> System.out.println(s + " (Java=" + s.getGrades().get(java) + ")"),
                        () -> System.out.println("Немає даних")
                );

        System.out.println("\n=== Топ-10 за середнім балом ===");
        StudentQueries.leaderboard(students, 10).forEach(System.out::println);

        System.out.println("\n=== Усі унікальні курси ===");
        System.out.println(StudentQueries.allCourseCodes(students));

        System.out.println("\n=== Розподіл за групами ===");
        System.out.println(StudentQueries.groupHistogram(students));

        System.out.println("\n=== Середні оцінки по курсах ===");
        StudentQueries.courseAverages(students).forEach((c, avg) ->
                System.out.printf("%s -> %.1f%n", c, avg));

        System.out.println("\n=== Глобальне середнє (паралельно) ===");
        System.out.printf("%.2f%n", StudentQueries.globalAverageParallel(students));

        System.out.println("\n=== Статистика оцінок (min/max/avg/count/sum) ===");
        System.out.println(StudentStatistics.gradeSummary(students));
    }
}
