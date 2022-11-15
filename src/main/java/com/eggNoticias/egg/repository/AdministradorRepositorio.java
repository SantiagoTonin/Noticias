package com.eggNoticias.egg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eggNoticias.egg.entidades.Administradores;

public interface AdministradorRepositorio extends JpaRepository<Administradores,String> {
    
}
