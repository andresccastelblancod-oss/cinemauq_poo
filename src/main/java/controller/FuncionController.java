
package controller;

import model.Cinema;
import model.Funcion;
import model.Pelicula;
import model.Sala;
import java.util.List;

public class FuncionController {
    private Cinema cinema;

    public FuncionController(Cinema cinema) {
        this.cinema = cinema;
    }

    public List<Funcion> getFunciones() {
        return cinema.getFunciones();
    }

    public List<Pelicula> getPeliculas() {
        return cinema.getPeliculas();
    }

    public List<Sala> getSalas() {
        return cinema.getSalas();
    }

    public boolean programarFuncion(String codigo, Pelicula p, Sala s, String fecha, String hora, double valor) {
        return cinema.programarFuncion(codigo, p, s, fecha, hora, valor);
    }

    public String getMensajeError() {
        return cinema.getMensajeError();
    }
}
