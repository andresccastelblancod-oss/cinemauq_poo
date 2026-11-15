public class SalaNoDisponibleException extends CinemaException {
    public SalaNoDisponibleException(String detalle) {
        super("La sala esta fuera de servicio: " + detalle);
    }
}
