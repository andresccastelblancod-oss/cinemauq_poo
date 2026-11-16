package controller;

import model.Cinema;
import model.Pelicula;
import java.util.List;

public class PeliculaController {
    private Cinema cinema;

    public PeliculaController(Cinema cinema) {
        this.cinema = cinema;
    }

    public List<Pelicula> getPeliculas() {
        return cinema.getPeliculas();
    }

    public boolean registrarPelicula(Pelicula p) {
        return cinema.registrarPelicula(p);
    }

    public String getMensajeError() {
        return cinema.getMensajeError();
    }
}
