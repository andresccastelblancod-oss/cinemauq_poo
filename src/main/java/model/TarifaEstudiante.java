package model;

public class TarifaEstudiante extends TarifaBase {
    private static final double DESCUENTO_INICIAL = 0.20;

    public TarifaEstudiante() {
        super(TipoCliente.ESTUDIANTE, DESCUENTO_INICIAL);
    }
}
