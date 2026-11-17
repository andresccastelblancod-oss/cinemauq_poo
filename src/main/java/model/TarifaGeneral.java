package model;

public class TarifaGeneral extends TarifaBase {

    public TarifaGeneral() {
        super(TipoCliente.GENERAL, 0);
    }

    @Override
    public double calcularValor(double valorBase) {
        return valorBase;
    }
}
