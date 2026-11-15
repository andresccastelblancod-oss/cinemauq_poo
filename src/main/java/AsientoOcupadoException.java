public class AsientoOcupadoException extends CinemaException {
    public AsientoOcupadoException(String detalle) {
        super("El asiento ya esta ocupado: " + detalle);
    }
}
