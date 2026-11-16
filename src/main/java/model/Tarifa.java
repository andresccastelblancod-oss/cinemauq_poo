package model;

public interface Tarifa {

    TipoCliente getTipo();

    double getDescuento();

    void setDescuento(double descuento);

    double calcularValor(double valorBase);
}
