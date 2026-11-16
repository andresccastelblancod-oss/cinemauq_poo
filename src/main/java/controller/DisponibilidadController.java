package controller;

import model.Cinema;
import model.Funcion;
import java.util.List;

public class DisponibilidadController {
    private Cinema cinema;

    public DisponibilidadController(Cinema cinema) {
        this.cinema = cinema;
    }

    public List<Funcion> getFunciones() {
        return cinema.getFunciones();
    }
}
