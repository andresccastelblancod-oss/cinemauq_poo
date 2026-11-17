package controller;

import model.Cinema;
import model.Sala;
import java.util.List;

public class SalaController {
    private Cinema cinema;

    public SalaController(Cinema cinema) {
        this.cinema = cinema;
    }

    public List<Sala> getSalas() {
        return cinema.getSalas();
    }

    public boolean registrarSala(Sala s) {
        return cinema.registrarSala(s);
    }

    public String getMensajeError() {
        return cinema.getMensajeError();
    }
}