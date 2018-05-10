package pl.piomin.graphite.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.annotation.Timed;
import pl.piomin.graphite.service.data.PersonRepository;
import pl.piomin.graphite.service.model.Person;

@RestController
@Timed
public class PersonController {

	protected Logger logger = Logger.getLogger(PersonController.class.getName());

	@Autowired
	PersonRepository repository;

	@GetMapping("/persons/pesel/{pesel}")
	public List<Person> findByPesel(@PathVariable("pesel") String pesel) {
		logger.info(String.format("Person.findByPesel(%s)", pesel));
		return repository.findByPesel(pesel);
	}

	@GetMapping("/persons/{id}")
	public Person findById(@PathVariable("id") Integer id) {
		logger.info(String.format("Person.findById(%d)", id));
		return repository.findById(id).get();
	}

	@GetMapping("/persons")
	public List<Person> findAll() {
		logger.info(String.format("Person.findAll()"));
		return (List<Person>) repository.findAll();
	}

	@PostMapping("/persons")
	public Person add(@RequestBody Person person) {
		logger.info(String.format("Person.add(%s)", person));
		return repository.save(person);
	}

	@PutMapping("/persons")
	public Person update(@RequestBody Person person) {
		logger.info(String.format("Person.update(%s)", person));
		return repository.save(person);
	}

	@DeleteMapping("/persons/{id}")
	public void remove(@PathVariable("id") Integer id) {
		logger.info(String.format("Person.remove(%d)", id));
		repository.deleteById(id);
	}

}
