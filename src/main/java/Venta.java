import java.time.LocalDateTime;

public class Venta {
    private String codigo;
    private Cliente cliente;
    private Funcion funcion;
    private Asiento asiento;
    private String fechaHora;
    private double valorPagado;
    private EstadoVenta estado;

    public Venta(String codigo, Cliente cliente, Funcion funcion, Asiento asiento, double valorPagado) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.funcion = funcion;
        this.asiento = asiento;
        this.valorPagado = valorPagado;
        this.fechaHora = LocalDateTime.now().withNano(0).toString();
        this.estado = EstadoVenta.ACTIVA;
    }

    public void cancelar() {
        estado = EstadoVenta.CANCELADA;
        asiento.liberar();
    }

    public String generarEntrada() {
        return "ENTRADA " + codigo
                + "\nPelicula: " + funcion.getPelicula().getNombre()
                + "\nSala: " + funcion.getSala().getNombre()
                + "\nFecha y hora de la funcion: " + funcion.getFecha() + " " + funcion.getHora()
                + "\nAsiento: " + asiento.getCodigo()
                + "\nCliente: " + cliente.getNombre() + " (" + cliente.getTipoCliente() + ")"
                + "\nValor pagado: $" + valorPagado
                + "\nFecha y hora de venta: " + fechaHora;
    }

    public String getCodigo() {
        return codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public double getValorPagado() {
        return valorPagado;
    }

    public EstadoVenta getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return codigo + " | " + funcion.getPelicula().getNombre() + " | " + asiento.getCodigo()
                + " | $" + valorPagado + " | " + estado;
    }
}
