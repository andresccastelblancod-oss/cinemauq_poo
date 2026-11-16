package model;

public class CodigoDuplicadoException extends CinemaException {
    public CodigoDuplicadoException(String detalle) {
        super("Ya existe un registro con ese codigo: " + detalle);
    }
}
