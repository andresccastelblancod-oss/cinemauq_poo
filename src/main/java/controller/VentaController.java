package controller;

import model.*;
import java.util.ArrayList;

public class VentaController {
    private Cinema cinema;

    public VentaController(Cinema cinema) {
        this.cinema = cinema;
    }

    public ArrayList<Funcion> funcionesProgramadas() {
        ArrayList<Funcion> lista = new ArrayList<>();
        for (Funcion f : cinema.getFunciones()) {
            if (f.getEstado() == EstadoFuncion.PROGRAMADA) {
                lista.add(f);
            }
        }
        return lista;
    }

    public boolean venderEntrada(Funcion funcion, Cliente cliente, String codigoAsiento) {
        if (funcion == null || cliente == null || codigoAsiento == null) return false;
        return cinema.venderEntrada(funcion.getCodigo(), cliente, codigoAsiento);
    }

    public Venta getUltimaVenta() {
        return cinema.getUltimaVenta();
    }

    public String getMensajeError() {
        return cinema.getMensajeError();
    }
}
