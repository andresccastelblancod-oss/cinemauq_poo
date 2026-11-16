package model;

public class FuncionNoEncontradaException extends CinemaException {
    public FuncionNoEncontradaException(String detalle) {
        super("La funcion no existe o no esta programada: " + detalle);
    }
}
