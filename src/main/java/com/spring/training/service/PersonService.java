package com.spring.training.service;

import com.spring.training.domain.Person;
import com.spring.training.exception.EntityNotFoundException;
import com.spring.training.exception.RequestException;
import com.spring.training.mapping.PersonMapper;
import com.spring.training.repository.CountryRepository;
import com.spring.training.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class PersonService {

    final PersonRepository personRepository;
    final CountryRepository countryRepository;
    final PersonMapper personMapper;

    @Transactional(readOnly = true)
    public List<Person> getPersons() {
        return StreamSupport.stream(personRepository.findAll().spliterator(), false)
                .map(personMapper::toPerson)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Person getPerson(Long id) {
        return personMapper.toPerson(personRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("person not found with id : " + id)));
    }

    @Transactional
    public Person createPerson(Person person) {
        countryRepository.findByNameIgnoreCase(person.getCountry().getName()).orElseThrow(() ->
                new EntityNotFoundException("country not found with name : " + person.getCountry().getName()));
        person.setId(null);
        return personMapper.toPerson(personRepository.save(personMapper.fromPerson(person)));
    }

    @Transactional
    public Person updatePerson(Long id, Person person) {
        return personRepository.findById(id)
                .map(entity -> {
                    countryRepository.findByNameIgnoreCase(person.getCountry().getName()).orElseThrow(() ->
                            new EntityNotFoundException("country not found with name : " + person.getCountry().getName()));
                    person.setId(id);
                    return personMapper.toPerson(personRepository.save(personMapper.fromPerson(person)));
                }).orElseThrow(() -> new EntityNotFoundException("person not found with id : " + id));
    }

    @Transactional
    public void deletePerson(Long id) {
        try {
            personRepository.deleteById(id);
        } catch (Exception e) {
            throw new RequestException("cannot delete person with id : " + id, HttpStatus.CONFLICT);
        }
    }

}