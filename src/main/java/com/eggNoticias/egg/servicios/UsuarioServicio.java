package com.eggNoticias.egg.servicios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.eggNoticias.egg.Enumerated.Rol;
import com.eggNoticias.egg.entidades.Usuario;
import com.eggNoticias.egg.exepciones.ExeptionNotcias;
import com.eggNoticias.egg.repository.UsuarioRepositorio;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void crearUsuario(String usuario, String email, String password, String password2, MultipartFile imagen)
            throws IOException, ExeptionNotcias {

        validar(usuario, email, password, password2);

        Usuario perfil = new Usuario();
        perfil.setUsuario(usuario);
        perfil.setEmail(email);
        // perfil.setPassword(new BCryptPasswordEncoder().encode(password));
        perfil.setPassword(password);
        perfil.setFechaDeCreacion(new Date());
        perfil.setFoto(imagen.getBytes());
        perfil.setRol(Rol.USER);
        usuarioRepositorio.save(perfil);

    }

    @Transactional
    public void modificarUsuario(String id, String usuario, String email, String password, String password2,
            MultipartFile imagen) throws IOException, ExeptionNotcias {

        validar(usuario, email, password, password2);
        Optional<Usuario> persona = usuarioRepositorio.findById(id);

        if (persona.isPresent()) {

            Usuario perfil = getOne(id);
            perfil.setUsuario(usuario);
            perfil.setEmail(email);
            perfil.setPassword(new BCryptPasswordEncoder().encode(password));
            perfil.setFechaDeCreacion(new Date());
            perfil.setFoto(imagen.getBytes());
            usuarioRepositorio.save(perfil);
        }

    }

    public void validar(String usuario, String email, String password, String password2) throws ExeptionNotcias {

        if (usuario == null || usuario.isEmpty()) {
            throw new ExeptionNotcias("El Nombre de Usuario no puede estar vacio");
        }
        if (email == null || email.isEmpty()) {
            throw new ExeptionNotcias("El Email del Usuario no puede estar vacio");
        }
        if (password == null || password.isEmpty() || password.length() < 5) {
            throw new ExeptionNotcias("El Password del Usuario no puede estar vacio o tener menos de 5 caracteres");
        }
        if (!password.equals(password2)) {
            throw new ExeptionNotcias("Las contraseñas ingresadas deben ser iguales");
        }
    }

    public Usuario getOne(String id) {
        return usuarioRepositorio.getById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {

        Usuario userName = usuarioRepositorio.buscarPorUsuario(usuario);

        if (userName != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + userName.getRol().toString());
            permisos.add(p);


            

             BCryptPasswordEncoder encoder = passwordEncoder();

             ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

             HttpSession session = attr.getRequest().getSession(true);

             session.setAttribute("usersession", userName);

            return new User(userName.getUsuario(),encoder.encode(userName.getPassword()), permisos);

        }
        return null;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
