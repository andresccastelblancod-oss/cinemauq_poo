package controller;

import model.Cinema;
import model.Tarifa;
import model.TipoCliente;
import java.util.List;

public class TarifaController {
    private Cinema cinema;

    public TarifaController(Cinema cinema) {
        this.cinema = cinema;
    }

    public List<Tarifa> getTarifas() {
        return cinema.getTarifas();
    }

    public void definirDescuento(TipoCliente tipo, double descuento) {
        cinema.definirDescuento(tipo, descuento);
    }
}