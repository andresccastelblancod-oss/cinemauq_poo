public class Asiento {
    private String codigo;
    private char fila;
    private int numero;
    private EstadoAsiento estado;

    public Asiento(char fila, int numero) {
        this.fila = fila;
        this.numero = numero;
        this.codigo = "" + fila + numero;
        this.estado = EstadoAsiento.DISPONIBLE;
    }

    public Asiento copiar() {
        return new Asiento(fila, numero);
    }

    public void ocupar() {
        estado = EstadoAsiento.OCUPADO;
    }

    public void liberar() {
        estado = EstadoAsiento.DISPONIBLE;
    }

    public boolean estaDisponible() {
        return estado == EstadoAsiento.DISPONIBLE;
    }

    public String getCodigo() {
        return codigo;
    }

    public char getFila() {
        return fila;
    }

    public int getNumero() {
        return numero;
    }

    public EstadoAsiento getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return codigo + " " + estado;
    }
}
