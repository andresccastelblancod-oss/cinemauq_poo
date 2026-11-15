public class Pelicula implements Habilitable {
    private String codigo;
    private String nombre;
    private String genero;
    private int duracion;
    private Clasificacion clasificacion;
    private EstadoPelicula estado;

    public Pelicula(String codigo, String nombre, String genero, int duracion, Clasificacion clasificacion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.genero = genero;
        this.duracion = duracion;
        this.clasificacion = clasificacion;
        this.estado = EstadoPelicula.DISPONIBLE;
    }

    @Override
    public void deshabilitar() {
        estado = EstadoPelicula.NO_DISPONIBLE;
    }

    @Override
    public boolean estaHabilitado() {
        return estado == EstadoPelicula.DISPONIBLE;
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public Clasificacion getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(Clasificacion clasificacion) {
        this.clasificacion = clasificacion;
    }

    public EstadoPelicula getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return codigo + " | " + nombre + " | " + genero + " | " + duracion + " min | " + clasificacion + " | " + estado;
    }
}
