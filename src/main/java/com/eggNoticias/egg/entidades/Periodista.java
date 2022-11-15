package com.eggNoticias.egg.entidades;

import java.util.ArrayList;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Entity
public class Periodista extends Usuario {
    private ArrayList<Noticia> cantDeNoticias;

    private double sueldoMensual;
    
    public void setPeriodista(Usuario usuario) {
        this.setId(usuario.getId());
        this.setEmail(usuario.getEmail());
        this.setUsuario(usuario.getUsuario());
        this.setPassword(usuario.getPassword());
        this.setFoto(usuario.getFoto());
    }
}
