package com.eggNoticias.egg.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.eggNoticias.egg.exepciones.ExeptionNotcias;
import com.eggNoticias.egg.servicios.UsuarioServicio;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioServicio usuarioServicio;

    @GetMapping("/registrar")
    public String registroUsuario() {
        return "Registro.html";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@RequestParam String usuario, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2,@RequestParam MultipartFile imagen,ModelMap modelo) throws IOException {

                try {
                    usuarioServicio.crearUsuario(usuario, email, password, password2, imagen);
                    return "redirect:/";
                } catch (ExeptionNotcias ex) {
                    modelo.put("error", ex.getMessage());
                    modelo.put("usuario",usuario);
                    modelo.put("email",email);
                    return "Registro.html";
                }


    }
}
