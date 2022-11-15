
package com.eggNoticias.egg.servicios;

// @author Belltwon
import com.eggNoticias.egg.entidades.Noticia;
import com.eggNoticias.egg.exepciones.ExeptionNotcias;
import com.eggNoticias.egg.repository.NoticiasRepositorio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class NoticiasServicios {

    @Autowired
    private NoticiasRepositorio noticiasRepo;

    @Transactional
    public void crearNoticia(String titulo, String cuerpo, MultipartFile foto) throws ExeptionNotcias, IOException {
        validar(titulo, cuerpo, foto);

        Noticia noticia = new Noticia();

        noticia.setTitulo(titulo);
        noticia.setCuerpo(cuerpo);
        noticia.setDescripcion(crearDescripcion(cuerpo));
        noticia.setFechaDeCreacion(new Date());
        noticia.setFoto(foto.getBytes());
        noticiasRepo.save(noticia);
    }

    @Transactional
    public void modificarNoticia(String id, String titulo, String cuerpo, MultipartFile foto)
            throws ExeptionNotcias, IOException {

        validar(titulo, cuerpo, foto);
        Optional<Noticia> respuesta = noticiasRepo.findById(id);

        if (respuesta.isPresent()) {

            Noticia noticia = respuesta.get();
            noticia.setTitulo(titulo);
            noticia.setCuerpo(cuerpo);
            noticia.setDescripcion(crearDescripcion(cuerpo));
            noticia.setFoto(foto.getBytes());
            noticiasRepo.save(noticia);
        }
    }

    public void borrarNoticia(String id) {
        noticiasRepo.deleteById(id);
    }

    public List<Noticia> listarNoticias() {

        List<Noticia> lista = new ArrayList<>();

        lista = noticiasRepo.findAll();

        Collections.sort(lista, Comparadores.ordenAsc);

        return lista;
    }

    public void validar(String titulo, String cuerpo, MultipartFile foto) throws ExeptionNotcias {

        if (titulo == null || titulo.isEmpty()) {

            throw new ExeptionNotcias("El Titulo no puede ser nulo");
        }
        if (cuerpo == null || cuerpo.isEmpty() || cuerpo.length() < 100) {

            throw new ExeptionNotcias("El Cuerpo no puede ser nulo o contener menos de 100 caracteres");
        }

        if (foto.isEmpty()) {
            throw new ExeptionNotcias("La foto no puede ser nula");
        }

    }

    private String crearDescripcion(String cuerpo) {

        String[] palabra = cuerpo.split(" ");
        System.out.println(palabra.length);
        String palabra2 = "";
        int numero = Math.toIntExact(Math.round(palabra.length * 0.20));
        for (int i = 0; i < numero; i++) {
            palabra2 = palabra2 + palabra[i] + " ";
        }

        return palabra2 + "...";

    }

    public Noticia getOne(String id) {
        return noticiasRepo.getById(id);
    }

}
