package com.spring.training.service;

import com.spring.training.domain.Country;
import com.spring.training.exception.EntityNotFoundException;
import com.spring.training.exception.RequestException;
import com.spring.training.mapping.CountryMapper;
import com.spring.training.repository.CountryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class CountryService {

    final CountryRepository countryRepository;
    final CountryMapper countryMapper;

    @Transactional(readOnly = true)
    public List<Country> getCountries() {
        return StreamSupport.stream(countryRepository.findAll().spliterator(), false)
                .map(countryMapper::toCountry)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Country getCountry(String name) {
        return countryMapper.toCountry(countryRepository.findByNameIgnoreCase(name).orElseThrow(() ->
                new EntityNotFoundException("country not found with name : " + name)));
    }

    @Transactional
    public Country createCountry(Country country) {
        countryRepository.findByNameIgnoreCase(country.getName())
                .ifPresent(entity -> {
                    throw new RequestException("country already created with name : " + country.getName(), HttpStatus.CONFLICT);
                });
        return countryMapper.toCountry(countryRepository.save(countryMapper.fromCountry(country)));
    }

    @Transactional
    public Country updateCountry(String name, Country country) {
        return countryRepository.findByNameIgnoreCase(name)
                .map(entity -> {
                    country.setName(name);
                    return countryMapper.toCountry(countryRepository.save(countryMapper.fromCountry(country)));
                }).orElseThrow(() -> new EntityNotFoundException("country not found with name : " + name));
    }

    @Transactional
    public void deleteCountry(String name) {
        try {
            countryRepository.deleteById(name);
        } catch (Exception e) {
            throw new RequestException("cannot delete country with name : " + name, HttpStatus.CONFLICT);
        }
    }

}