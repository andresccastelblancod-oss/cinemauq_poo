package controller;

import model.Cinema;
import model.Venta;
import java.util.List;

public class CancelarVentaController {
    private Cinema cinema;

    public CancelarVentaController(Cinema cinema) {
        this.cinema = cinema;
    }

    public List<Venta> getVentas() {
        return cinema.getVentas();
    }

    public boolean cancelarVenta(String codigoVenta) {
        return cinema.cancelarVenta(codigoVenta);
    }

    public String getMensajeError() {
        return cinema.getMensajeError();
    }
}

