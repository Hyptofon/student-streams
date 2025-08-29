package com.example.streams.service;

import com.example.streams.model.Student;

import java.util.IntSummaryStatistics;
import java.util.List;

public final class StudentStatistics {
    private StudentStatistics(){}

    /** Повертає статистику оцінок (мін/макс/середнє/сума/кількість) по всіх курсах */
    public static IntSummaryStatistics gradeSummary(List<Student> students) {
        return students.stream()
                .flatMap(s -> s.getGrades().values().stream())
                .mapToInt(Integer::intValue)
                .summaryStatistics();
    }
}
