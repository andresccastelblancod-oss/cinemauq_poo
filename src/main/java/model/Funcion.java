package model;

import java.util.ArrayList;

public class Funcion {
    private String codigo;
    private Pelicula pelicula;
    private Sala sala;
    private String fecha;
    private String hora;
    private double valorBase;
    private EstadoFuncion estado;
    private ArrayList<Asiento> asientos;

    public Funcion(String codigo, Pelicula pelicula, Sala sala, String fecha, String hora, double valorBase) {
        this.codigo = codigo;
        this.pelicula = pelicula;
        this.sala = sala;
        this.fecha = fecha;
        this.hora = hora;
        this.valorBase = valorBase;
        this.estado = EstadoFuncion.PROGRAMADA;
        // cada funcion tiene su propia copia de los asientos de la sala
        this.asientos = new ArrayList<>();
        for (int i = 0; i < sala.getAsientos().size(); i++) {
            asientos.add(sala.getAsientos().get(i).copiar());
        }
    }

    public Asiento buscarAsiento(String codigoAsiento) {
        for (int i = 0; i < asientos.size(); i++) {
            if (asientos.get(i).getCodigo().equalsIgnoreCase(codigoAsiento)) {
                return asientos.get(i);
            }
        }
        return null;
    }

    public int totalAsientos() {
        return asientos.size();
    }

    public int asientosDisponibles() {
        int cont = 0;
        for (int i = 0; i < asientos.size(); i++) {
            if (asientos.get(i).estaDisponible()) {
                cont++;
            }
        }
        return cont;
    }

    public int asientosOcupados() {
        return totalAsientos() - asientosDisponibles();
    }

    public void cancelar() {
        estado = EstadoFuncion.CANCELADA;
    }

    public void finalizar() {
        estado = EstadoFuncion.FINALIZADA;
    }

    public String getCodigo() {
        return codigo;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public Sala getSala() {
        return sala;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public double getValorBase() {
        return valorBase;
    }

    public EstadoFuncion getEstado() {
        return estado;
    }

    public ArrayList<Asiento> getAsientos() {
        return asientos;
    }

    @Override
    public String toString() {
        return codigo + " | " + pelicula.getNombre() + " | " + sala.getNombre() + " | " + fecha + " " + hora
                + " | $" + valorBase + " | disponibles " + asientosDisponibles() + " | " + estado;
    }
}
