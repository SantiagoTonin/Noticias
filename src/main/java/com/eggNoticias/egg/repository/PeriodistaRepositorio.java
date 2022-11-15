package com.eggNoticias.egg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eggNoticias.egg.entidades.Periodista;

public interface PeriodistaRepositorio extends JpaRepository<Periodista,String> {
    
}
