package com.example.streams;

import com.example.streams.model.Course;
import com.example.streams.model.Student;
import com.example.streams.service.StudentQueries;
import com.example.streams.util.SampleData;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StudentQueriesTest {

    @Test
    void bestByCourseShouldReturnSomeone() {
        List<Student> data = SampleData.generate(20);
        Course any = SampleData.COURSES.get(0);
        Optional<Student> best = StudentQueries.bestByCourse(data, any);
        assertTrue(best.isPresent());
        assertTrue(best.get().getGrades().containsKey(any));
    }

    @Test
    void topByAverageRespectsLimit() {
        List<Student> data = SampleData.generate(100);
        List<Student> top = StudentQueries.topByAverage(data, 70, 7);
        assertTrue(top.size() <= 7);
        // Відсортовано за прізвищем, потім ім'ям (лексикографічно)
        List<Student> sorted = top.stream().sorted((a, b) -> {
            int c = a.getLastName().compareToIgnoreCase(b.getLastName());
            return c != 0 ? c : a.getFirstName().compareToIgnoreCase(b.getFirstName());
        }).toList();
        assertEquals(sorted, top);
    }

    @Test
    void leaderboardIsDescending() {
        List<Student> data = SampleData.generate(30);
        List<Student> lb = StudentQueries.leaderboard(data, 10);
        for (int i = 1; i < lb.size(); i++) {
            double prev = lb.get(i-1).averageGrade().orElse(0);
            double cur  = lb.get(i).averageGrade().orElse(0);
            assertTrue(prev >= cur);
        }
    }
}
