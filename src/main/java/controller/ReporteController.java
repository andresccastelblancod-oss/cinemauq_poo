package controller;

import model.Cinema;
import model.Funcion;
import model.Pelicula;
import java.util.List;

public class ReporteController {
    private Cinema cinema;

    public ReporteController(Cinema cinema) {
        this.cinema = cinema;
    }

    public int totalEntradasVendidas() {
        return cinema.totalEntradasVendidas();
    }

    public double totalIngresos() {
        return cinema.totalIngresos();
    }

    public Pelicula peliculaMasVendida() {
        return cinema.peliculaMasVendida();
    }

    public List<Funcion> getFunciones() {
        return cinema.getFunciones();
    }

    public List<Funcion> funcionesPorFecha(String fecha) {
        return cinema.funcionesPorFecha(fecha);
    }
}