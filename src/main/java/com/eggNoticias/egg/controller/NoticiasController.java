package com.eggNoticias.egg.controller;

import com.eggNoticias.egg.entidades.Noticia;
// @author Belltwon
import com.eggNoticias.egg.exepciones.ExeptionNotcias;
import com.eggNoticias.egg.servicios.NoticiasServicios;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/noticias")
public class NoticiasController {

    @Autowired
    public NoticiasServicios noticiaServicio;

    @GetMapping("/crear")
    public String creacionNoticia() {
        return "crearNoticias.html";
    }

    @PostMapping("/creado")
    public String crearNoticias(@RequestParam String titulo, @RequestParam String cuerpo,
            @RequestParam(required = false) MultipartFile foto, ModelMap modelo) throws ExeptionNotcias, IOException {

        try {
            noticiaServicio.crearNoticia(titulo, cuerpo, foto);
            modelo.put("exito", "Se ha guardado Correctamente");
            return "crearNoticias.html";
        } catch (ExeptionNotcias e) {
            modelo.put("error", e.getMessage());
            return "crearNoticias.html";
        }
    }

    @GetMapping("/verNoticia/{id}")
    public String verNoticia(@PathVariable String id, ModelMap modelo) {

        Noticia noticias = noticiaServicio.getOne(id);
        modelo.put("noticia", noticias);
        return "noticia.html";

    }

    @GetMapping("/ver/{id}")
    public ResponseEntity<byte[]> imagenNoticia(@PathVariable String id) {
        Noticia noticia = noticiaServicio.getOne(id);
        byte[] imagen = noticia.getFoto();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }

    @GetMapping("/modificar/{id}")
    public String modificarNoticia(@PathVariable String id, ModelMap modelo) {
        Noticia noticia = noticiaServicio.getOne(id);
        modelo.put("modNoticia", noticia);
        return "modNoticias.html";
    }

    @PostMapping("/modificar/{id}")
    public String noticiaModificada(@PathVariable String id, String titulo, String cuerpo,
            @RequestParam(required = false) MultipartFile foto, ModelMap modelo) throws ExeptionNotcias, IOException {

        try {
            noticiaServicio.modificarNoticia(id, titulo, cuerpo, foto);
            modelo.put("exito", "La modificacion se realizo de manera satisfactoria");
            return "redirect:../../inicio";

        } catch (ExeptionNotcias ex) {
            Noticia noticia = noticiaServicio.getOne(id);
            modelo.put("modNoticia", noticia);
            modelo.put("error", ex.getMessage());
            // return "redirect:../modificar/{id}";
            return "modNoticias.html";

        }

    }

}
