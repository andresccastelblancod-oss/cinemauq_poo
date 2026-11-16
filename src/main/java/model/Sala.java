package model;

import java.util.ArrayList;

public class Sala implements Habilitable {
    private String codigo;
    private String nombre;
    private EstadoSala estado;
    private ArrayList<Asiento> asientos;

    public Sala(String codigo, String nombre, int filas, int columnas) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.estado = EstadoSala.DISPONIBLE;
        this.asientos = new ArrayList<>();
        for (int i = 0; i < filas; i++) {
            for (int j = 1; j <= columnas; j++) {
                asientos.add(new Asiento((char) ('A' + i), j));
            }
        }
    }

    public int getCapacidad() {
        return asientos.size();
    }

    @Override
    public void deshabilitar() {
        estado = EstadoSala.FUERA_DE_SERVICIO;
    }

    @Override
    public boolean estaHabilitado() {
        return estado == EstadoSala.DISPONIBLE;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public EstadoSala getEstado() {
        return estado;
    }

    public ArrayList<Asiento> getAsientos() {
        return asientos;
    }

    @Override
    public String toString() {
        return codigo + " | " + nombre + " | capacidad " + getCapacidad() + " | " + estado;
    }
}
