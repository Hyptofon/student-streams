package com.example.streams.service;

import com.example.streams.model.Course;
import com.example.streams.model.Gender;
import com.example.streams.model.Student;

import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;

public final class StudentQueries {
    private StudentQueries(){}

    /** Фільтрація за мінімальним середнім балом + сортування за прізвищем, потім ім'ям */
    public static List<Student> topByAverage(List<Student> students, double minAvg, int limit) {
        return students.stream()
                .filter(s -> s.averageGrade().orElse(0) >= minAvg)
                .sorted(comparing((Student s) -> s.getLastName().toLowerCase())
                        .thenComparing(s -> s.getFirstName().toLowerCase()))
                .limit(limit)
                .collect(toList());
    }

    /** Групування за групою із середнім балом по кожній групі */
    public static Map<String, Double> averageByGroup(List<Student> students) {
        return students.stream().collect(
                groupingBy(Student::getGroup,
                        collectingAndThen(
                                averagingDouble(s -> s.averageGrade().orElse(0)),
                                d -> Math.round(d * 10.0) / 10.0 // округлення до 0.1
                        )));
    }

    /** Найкращий студент по курсу */
    public static Optional<Student> bestByCourse(List<Student> students, Course course) {
        return students.stream()
                .filter(s -> s.getGrades().containsKey(course))
                .max(comparingInt(s -> s.getGrades().get(course)));
    }

    /** Розподіл за статтю (partitioning) */
    public static Map<Boolean, List<Student>> partitionByPassing(List<Student> students, double passAvg) {
        return students.stream()
                .collect(partitioningBy(s -> s.averageGrade().orElse(0) >= passAvg));
    }

    /** Топ-N за середнім балом (спадаюче) */
    public static List<Student> leaderboard(List<Student> students, int n) {
        return students.stream()
                .sorted(comparingDouble((Student s) -> s.averageGrade().orElse(0)).reversed())
                .limit(n).collect(toList());
    }

    /** Мапа: Стать -> Список ПІБ */
    public static Map<Gender, List<String>> namesByGender(List<Student> students) {
        return students.stream().collect(
                groupingBy(Student::getGender,
                        mapping(s -> s.getFirstName() + " " + s.getLastName(), toList())));
    }

    /** Всі унікальні коди курсів, які хоча б хтось має у grade map (flatMap + distinct) */
    public static Set<String> allCourseCodes(List<Student> students) {
        return students.stream()
                .flatMap(s -> s.getGrades().keySet().stream())
                .map(Course::getCode)
                .collect(toCollection(LinkedHashSet::new));
    }

    /** Частотність груп: group -> count */
    public static Map<String, Long> groupHistogram(List<Student> students) {
        return students.stream().collect(groupingBy(Student::getGroup, counting()));
    }

    /** Середня оцінка по кожному курсу */
    public static Map<Course, Double> courseAverages(List<Student> students) {
        return students.stream()
                .flatMap(s -> s.getGrades().entrySet().stream())
                .collect(groupingBy(Map.Entry::getKey, averagingInt(Map.Entry::getValue)));
    }

    /** Паралельний стрім для великого списку (демо): глобальне середнє */
    public static double globalAverageParallel(List<Student> students) {
        return students.parallelStream()
                .flatMapToInt(s -> s.getGrades().values().stream().mapToInt(Integer::intValue))
                .average().orElse(0);
    }

    /** Безпечний пошук за id */
    public static Optional<Student> findById(List<Student> students, long id) {
        return students.stream().filter(s -> s.getId() == id).findFirst();
    }
}
