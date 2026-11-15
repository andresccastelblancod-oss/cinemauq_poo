public abstract class TarifaBase implements Tarifa {
    private TipoCliente tipo;
    private double descuento;

    public TarifaBase(TipoCliente tipo, double descuento) {
        this.tipo = tipo;
        this.descuento = descuento;
    }

    @Override
    public TipoCliente getTipo() {
        return tipo;
    }

    @Override
    public double getDescuento() {
        return descuento;
    }

    @Override
    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    @Override
    public double calcularValor(double valorBase) {
        return valorBase - valorBase * descuento;
    }
}
