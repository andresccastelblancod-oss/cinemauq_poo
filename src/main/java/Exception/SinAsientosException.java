package Exception;

public class SinAsientosException extends CinemaException {
    public SinAsientosException(String detalle) {
        super("No hay asientos disponibles: " + detalle);
    }
}
