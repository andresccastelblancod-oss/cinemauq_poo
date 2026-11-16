package model;

public class ElementoNoEncontradoException extends CinemaException {
    public ElementoNoEncontradoException(String detalle) {
        super("No se encontro el elemento: " + detalle);
    }
}
