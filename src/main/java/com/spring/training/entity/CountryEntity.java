package com.spring.training.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "countries")
public class CountryEntity {
    @Id
    String name;
    String capital;
    int population;
}