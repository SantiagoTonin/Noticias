package com.eggNoticias.egg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eggNoticias.egg.entidades.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario,String> {

    @Query("SELECT u FROM Usuario u WHERE u.usuario = :usuario")
    public Usuario buscarPorUsuario(@Param("usuario")String usuario);
    
}

