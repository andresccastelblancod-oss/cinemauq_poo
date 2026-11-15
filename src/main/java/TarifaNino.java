public class TarifaNino extends TarifaBase {
    private static final double DESCUENTO_INICIAL = 0.30;

    public TarifaNino() {
        super(TipoCliente.NINO, DESCUENTO_INICIAL);
    }
}
