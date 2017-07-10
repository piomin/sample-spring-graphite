package pl.piomin.graphite.service.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pl.piomin.graphite.service.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {

	public List<Person> findByPesel(String pesel);
	
}
