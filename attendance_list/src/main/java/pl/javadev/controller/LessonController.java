package pl.javadev.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import pl.javadev.model.Lesson;
import pl.javadev.repository.LessonRepository;

@RestController
public class LessonController {

	private LessonRepository lessonRepository;
	
	@Autowired
	public LessonController(LessonRepository lessonRepository) {
		this.lessonRepository = lessonRepository;
	}
	
	@GetMapping(path = "/api/lessons")
	public List<Lesson> getAll(){
		return lessonRepository.findAll();
	}
	
	@GetMapping(path = "/api/lessons/{id}")
	public ResponseEntity<Lesson> getById(@PathVariable Long id){
		Lesson lesson = lessonRepository.findById(id).get();
		if (lesson != null) {
			return ResponseEntity.ok(lesson);
		}else {
			return ResponseEntity.notFound().build();
		}		
	}
	
	@PostMapping(path = "/api/lessons")
	public ResponseEntity<?> save(@RequestBody Lesson lesson){
		if (lesson.getId() == null) {
			Lesson saved = lessonRepository.save(lesson);
			URI location = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(saved.getId())
					.toUri();
			return ResponseEntity.created(location).body(saved);
		}else {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}		
	}	
}
