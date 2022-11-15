package com.eggNoticias.egg.servicios;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eggNoticias.egg.Enumerated.Rol;
import com.eggNoticias.egg.entidades.Noticia;
import com.eggNoticias.egg.entidades.Periodista;
import com.eggNoticias.egg.entidades.Usuario;
import com.eggNoticias.egg.repository.PeriodistaRepositorio;

@Service
public class PeriodiastaServicio {

    @Autowired
    PeriodistaRepositorio periodistaRepositorio;

    @Transactional
    public void crearPeriodiasta(Usuario usuario) {

        Periodista periodista = new Periodista();

        periodista.setPeriodista(usuario);
        periodista.setSueldoMensual(0.0);
        periodista.setRol(Rol.Restringido);
        periodistaRepositorio.save(periodista);

    }

    @Transactional
    public void guardarNoticia(String id, Noticia noticia) {

        Periodista periodista = getOne(id);
        ArrayList<Noticia> listaNoticia = periodista.getCantDeNoticias();
        listaNoticia.add(noticia);
        periodista.setCantDeNoticias(listaNoticia);
        periodistaRepositorio.save(periodista);

    }

    public Periodista getOne(String id) {
        return periodistaRepositorio.getById(id);
    }

    public String calcularSueldo(String id) {

        Periodista periodista = getOne(id);
        ArrayList<Noticia> cantidad = periodista.getCantDeNoticias();

            Double sueldo = (double) cantidad.size() * 1200;

        String mostrarSueldo = String.valueOf(sueldo);
        return mostrarSueldo;
    }
}
