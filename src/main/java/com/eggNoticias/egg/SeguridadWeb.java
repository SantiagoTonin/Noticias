package com.eggNoticias.egg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.eggNoticias.egg.servicios.UsuarioServicio;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWeb extends WebSecurityConfigurerAdapter {

   @Autowired
   UsuarioServicio usuarioServicio;

   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

      auth.userDetailsService(usuarioServicio).passwordEncoder(new BCryptPasswordEncoder());
   }

   @Override
   protected void configure(HttpSecurity http) throws Exception {

      http
            .authorizeRequests()
            .antMatchers("/admin/*").hasRole("ADMIN")// "*" es admin lo q sea
            .antMatchers("/css/*", "/js/*", "/img/*", "/**")
            .permitAll()
            .and().formLogin()
            .loginPage("/")
            .loginProcessingUrl("/logincheck") // loginProcessingUrl es la ruta q se usa en el action del html
            .usernameParameter("usuario")
            .passwordParameter("password")
            .defaultSuccessUrl("/inicio") // si todo sale correcto es la ruta donde nos mandara
            .permitAll()
            .and().logout().logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .permitAll()
            .and()
            .csrf()
            .disable();

   };

}
