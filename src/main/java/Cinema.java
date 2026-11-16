import java.util.ArrayList;

public class Cinema {
    private ArrayList<Pelicula> peliculas;
    private ArrayList<Sala> salas;
    private ArrayList<Funcion> funciones;
    private ArrayList<Venta> ventas;
    private ArrayList<Cliente> clientes;
    private ArrayList<Tarifa> tarifas;
    private int consecutivoVenta;
    private String mensajeError;
    private Venta ultimaVenta;

    public Cinema() {
        peliculas = new ArrayList<>();
        salas = new ArrayList<>();
        funciones = new ArrayList<>();
        ventas = new ArrayList<>();
        clientes = new ArrayList<>();
        tarifas = new ArrayList<>();
        consecutivoVenta = 1;
        mensajeError = "";

        tarifas.add(new TarifaGeneral());
        tarifas.add(new TarifaEstudiante());
        tarifas.add(new TarifaNino());
        tarifas.add(new TarifaAdultoMayor());
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public Venta getUltimaVenta() {
        return ultimaVenta;
    }

 
    public boolean registrarPelicula(Pelicula pelicula) {
        boolean yaRegistrada = false;
        for (int i = 0; i < peliculas.size(); i++) {
            if (peliculas.get(i).getCodigo().equals(pelicula.getCodigo())) {
                yaRegistrada = true;

            }
        }
        if (yaRegistrada) {
            mensajeError = "Ya existe una pelicula con el codigo " + pelicula.getCodigo();
            return false;
        }
        peliculas.add(pelicula);
        return true;
    }

    public Pelicula buscarPelicula(String codigo) {
        for (int i = 0; i < peliculas.size(); i++) {
            if (peliculas.get(i).getCodigo().equals(codigo)) {
                return peliculas.get(i);
            }
        }
        return null;
    }

    public ArrayList<Pelicula> getPeliculas() {
        return peliculas;
    }

   
    public boolean registrarSala(Sala sala) {
        boolean yaRegistrada = false;
        for (int i = 0; i < salas.size(); i++) {
            if (salas.get(i).getCodigo().equals(sala.getCodigo())) {
                yaRegistrada = true;
            }
        }
        if (yaRegistrada) {
            mensajeError = "Ya existe una sala con el codigo " + sala.getCodigo();
            return false;
        }
        salas.add(sala);
        return true;
    }

    public Sala buscarSala(String codigo) {
        for (int i = 0; i < salas.size(); i++) {
            if (salas.get(i).getCodigo().equals(codigo)) {
                return salas.get(i);
            }
        }
        return null;
    }

    public ArrayList<Sala> getSalas() {
        return salas;
    }

    public boolean programarFuncion(String codigo, Pelicula pelicula, Sala sala, String fecha, String hora,
                                    double valorBase) {
        boolean yaRegistrada = false;
        for (int i = 0; i < funciones.size(); i++) {
            if (funciones.get(i).getCodigo().equals(codigo)) {
                yaRegistrada = true;
            }
        }
        if (yaRegistrada) {
            mensajeError = "Ya existe una funcion con el codigo " + codigo;
            return false;
        }
        if (!sala.estaHabilitado()) {
            mensajeError = "La sala " + sala.getNombre() + " esta fuera de servicio";
            return false;
        }
        funciones.add(new Funcion(codigo, pelicula, sala, fecha, hora, valorBase));
        return true;
    }

    public Funcion buscarFuncion(String codigo) {
        for (int i = 0; i < funciones.size(); i++) {
            if (funciones.get(i).getCodigo().equals(codigo)) {
                return funciones.get(i);
            }
        }
        return null;
    }

    public ArrayList<Funcion> getFunciones() {
        return funciones;
    }

   
    public Tarifa buscarTarifa(TipoCliente tipo) {
        for (int i = 0; i < tarifas.size(); i++) {
            if (tarifas.get(i).getTipo() == tipo) {
                return tarifas.get(i);
            }
        }
        return null;
    }

    public void definirDescuento(TipoCliente tipo, double descuento) {
        buscarTarifa(tipo).setDescuento(descuento);
    }

    public ArrayList<Tarifa> getTarifas() {
        return tarifas;
    }

   
    public boolean venderEntrada(String codigoFuncion, Cliente cliente, String codigoAsiento) {
        Funcion funcion = buscarFuncion(codigoFuncion);
        if (funcion == null || funcion.getEstado() != EstadoFuncion.PROGRAMADA) {
            mensajeError = "La funcion " + codigoFuncion + " no existe o no esta programada";
            return false;
        }
        if (funcion.asientosDisponibles() == 0) {
            mensajeError = "No hay asientos disponibles en la funcion " + codigoFuncion;
            return false;
        }
        Asiento asiento = funcion.buscarAsiento(codigoAsiento);
        if (asiento == null) {
            mensajeError = "El asiento " + codigoAsiento + " no existe";
            return false;
        }
        if (!asiento.estaDisponible()) {
            mensajeError = "El asiento " + codigoAsiento + " ya esta ocupado";
            return false;
        }
        double valor = buscarTarifa(cliente.getTipoCliente()).calcularValor(funcion.getValorBase());
        asiento.ocupar();
        registrarCliente(cliente);
        ultimaVenta = new Venta("V" + consecutivoVenta, cliente, funcion, asiento, valor);
        consecutivoVenta++;
        ventas.add(ultimaVenta);
        return true;
    }

    private void registrarCliente(Cliente cliente) {
        boolean yaRegistrado = false;
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getIdentificacion().equals(cliente.getIdentificacion())) {
                yaRegistrado = true;
            }
        }
        if (!yaRegistrado) {
            clientes.add(cliente);
        }
    }

    public boolean cancelarVenta(String codigoVenta) {
        for (int i = 0; i < ventas.size(); i++) {
            Venta v = ventas.get(i);
            if (v.getCodigo().equals(codigoVenta) && v.getEstado() == EstadoVenta.ACTIVA) {
                v.cancelar();
                return true;
            }
        }
        mensajeError = "La venta " + codigoVenta + " no existe o ya fue cancelada";
        return false;
    }

    public ArrayList<Venta> getVentas() {
        return ventas;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

   
    public int totalEntradasVendidas() {
        int cont = 0;
        for (int i = 0; i < ventas.size(); i++) {
            if (ventas.get(i).getEstado() == EstadoVenta.ACTIVA) {
                cont++;
            }
        }
        return cont;
    }

    public double totalIngresos() {
        double total = 0;
        for (int i = 0; i < ventas.size(); i++) {
            if (ventas.get(i).getEstado() == EstadoVenta.ACTIVA) {
                total = total + ventas.get(i).getValorPagado();
            }
        }
        return total;
    }

    public Pelicula peliculaMasVendida() {
        Pelicula mejor = null;
        int mayor = 0;
        for (int i = 0; i < peliculas.size(); i++) {
            int cont = 0;
            for (int j = 0; j < ventas.size(); j++) {
                Venta v = ventas.get(j);
                if (v.getEstado() == EstadoVenta.ACTIVA && v.getFuncion().getPelicula() == peliculas.get(i)) {
                    cont++;
                }
            }
            if (cont > mayor) {
                mayor = cont;
                mejor = peliculas.get(i);
            }
        }
        return mejor;
    }

    public ArrayList<Funcion> funcionesPorFecha(String fecha) {
        ArrayList<Funcion> resultado = new ArrayList<>();
        for (int i = 0; i < funciones.size(); i++) {
            Funcion f = funciones.get(i);
            if (f.getFecha().equals(fecha) && f.getEstado() == EstadoFuncion.PROGRAMADA) {
                resultado.add(f);
            }
        }
        return resultado;
    }

    public void cargarDatosDemo() {
        registrarPelicula(new Pelicula("P1", "Interestelar", "Ciencia ficcion", 169, Clasificacion.MAYORES_12));
        registrarPelicula(new Pelicula("P2", "El Ultimo Algoritmo", "Drama", 110, Clasificacion.TODOS));
        registrarSala(new Sala("S1", "Sala 1", 4, 6));
        registrarSala(new Sala("S2", "Sala 2", 5, 8));
        programarFuncion("F1", buscarPelicula("P1"), buscarSala("S1"), "2026-11-20", "14:00", 15000);
        programarFuncion("F2", buscarPelicula("P2"), buscarSala("S2"), "2026-11-20", "18:30", 15000);
    }
}
