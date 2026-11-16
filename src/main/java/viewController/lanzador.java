package viewController;

import model.CinemaException;

public class lanzador {
    public static void main(String[] args) {
        Main.main(args);
    }

    public static class SalaNoDisponibleException extends CinemaException {
        public SalaNoDisponibleException(String detalle) {
            super("La sala esta fuera de servicio: " + detalle);
        }
    }
}