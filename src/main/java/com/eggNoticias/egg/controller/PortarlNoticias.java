
package com.eggNoticias.egg.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eggNoticias.egg.entidades.Noticia;
import com.eggNoticias.egg.entidades.Usuario;
import com.eggNoticias.egg.servicios.NoticiasServicios;

@Controller
@RequestMapping("/")
public class PortarlNoticias {

    @Autowired
    private NoticiasServicios noticiasServicios;

    @GetMapping("/")
    public String login(@RequestParam(required = false) String error,ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Usuario o contraseña invalidos");
        }
        return "Login.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_Restringido')")
    @GetMapping("/inicio")
    public String inicioPortal(ModelMap modelo,HttpSession session) {

        Usuario logueado = (Usuario) session.getAttribute("usersession");

        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
            
        }

        List<Noticia> lista = noticiasServicios.listarNoticias();

        modelo.addAttribute("noticia", lista);

        return "inicio.html";
    }

    @GetMapping("/inicio/{id}")
    public ResponseEntity<byte[]> imagenNoticia(@PathVariable String id) {
        Noticia noticia = noticiasServicios.getOne(id);
        byte[] imagen = noticia.getFoto();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }

    @GetMapping("/inicio/eliminar/{id}")
    public String removerNoticia(@PathVariable String id) {
        noticiasServicios.borrarNoticia(id);
        return "redirect:/inicio";
    }

}
