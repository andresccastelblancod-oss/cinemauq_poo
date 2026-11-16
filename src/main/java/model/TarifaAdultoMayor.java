package model;

public class TarifaAdultoMayor extends TarifaBase {
    private static final double DESCUENTO_INICIAL = 0.25;

    public TarifaAdultoMayor() {
        super(TipoCliente.ADULTO_MAYOR, DESCUENTO_INICIAL);
    }
}
