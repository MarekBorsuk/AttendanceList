package pl.javadev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.javadev.model.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {


}
