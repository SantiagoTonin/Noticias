package com.eggNoticias.egg.entidades;

// @author Belltwon
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
public class Noticia {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String titulo;

    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    private String cuerpo;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] foto;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDeCreacion;
    
    @Column(nullable = false,columnDefinition = "MEDIUMTEXT")
    private String descripcion;

    @ManyToOne
    private Periodista creador;


    

}
