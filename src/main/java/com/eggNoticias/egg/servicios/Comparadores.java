package com.eggNoticias.egg.servicios;

import com.eggNoticias.egg.entidades.Noticia;
import java.util.Comparator;

public class Comparadores{

public static Comparator<Noticia> ordenAsc = new Comparator<Noticia>() {
    @Override
    public int compare(Noticia nt,Noticia nt2){
        return nt2.getFechaDeCreacion().compareTo(nt.getFechaDeCreacion());
    }
};


}