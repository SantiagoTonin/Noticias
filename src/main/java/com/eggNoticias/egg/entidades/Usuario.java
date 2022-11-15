package com.eggNoticias.egg.entidades;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.eggNoticias.egg.Enumerated.Rol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private String usuario;
    private String email;
    private String password;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] foto;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDeCreacion;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDeBaja;

    @Enumerated(EnumType.STRING)
    private Rol rol;


}