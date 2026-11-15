public class VentaNoEncontradaException extends CinemaException {
    public VentaNoEncontradaException(String detalle) {
        super("La venta no existe o ya fue cancelada: " + detalle);
    }
}
