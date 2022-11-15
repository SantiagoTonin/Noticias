/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eggNoticias.egg.repository;

import com.eggNoticias.egg.entidades.Noticia;



import org.springframework.data.jpa.repository.JpaRepository;


/**
 *
 * @author Belltwon
 */
public interface NoticiasRepositorio extends JpaRepository<Noticia, String>{

    


}
