import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CinemaTest {
    private Cinema cinema;
    private Cliente estudiante;

    @BeforeEach
    void preparar() {
        cinema = new Cinema();
        cinema.cargarDatosDemo();
        estudiante = new Cliente("1", "Ana", TipoCliente.ESTUDIANTE);
    }

    @Test
    void ventaOcupaAsientoYCalculaValor() {
        assertTrue(cinema.venderEntrada("F1", estudiante, "A1"));
        Venta v = cinema.getUltimaVenta();
        assertEquals(12000, v.getValorPagado(), 0.01);
        assertEquals(EstadoVenta.ACTIVA, v.getEstado());
        assertEquals(EstadoAsiento.OCUPADO, v.getAsiento().getEstado());
    }

    @Test
    void asientoOcupadoLanzaExcepcion() {
        assertTrue(cinema.venderEntrada("F1", estudiante, "A1"));
        assertFalse(cinema.venderEntrada("F1", estudiante, "A1"));
        assertTrue(cinema.getMensajeError().contains("ocupado"));
    }

    @Test
    void funcionInexistenteLanzaExcepcion() {
        assertFalse(cinema.venderEntrada("X", estudiante, "A1"));
        assertNull(cinema.buscarFuncion("X"));
    }

    @Test
    void sinAsientosDisponibles() {
        cinema.registrarSala(new Sala("S3", "Mini", 1, 1));
        cinema.programarFuncion("F3", cinema.buscarPelicula("P1"), cinema.buscarSala("S3"), "2026-11-20", "20:00", 10000);
        assertTrue(cinema.venderEntrada("F3", estudiante, "A1"));
        assertFalse(cinema.venderEntrada("F3", estudiante, "A1"));
        assertTrue(cinema.getMensajeError().contains("No hay asientos"));
    }

    @Test
    void cancelarLiberaAsientoYNoSePuedeCancelarDosVeces() {
        cinema.venderEntrada("F1", estudiante, "A2");
        Venta v = cinema.getUltimaVenta();
        assertTrue(cinema.cancelarVenta(v.getCodigo()));
        assertEquals(EstadoVenta.CANCELADA, v.getEstado());
        assertTrue(cinema.buscarFuncion("F1").buscarAsiento("A2").estaDisponible());
        assertFalse(cinema.cancelarVenta(v.getCodigo()));
    }

    @Test
    void cancelarVentaInexistente() {
        assertFalse(cinema.cancelarVenta("V99"));
    }

    @Test
    void codigosDuplicados() {
        assertFalse(cinema.registrarPelicula(new Pelicula("P1", "Otra", "Drama", 90, Clasificacion.TODOS)));
        assertFalse(cinema.registrarSala(new Sala("S1", "Dup", 2, 2)));
        assertEquals(2, cinema.getPeliculas().size());
    }

    @Test
    void salaFueraDeServicioNoPermiteFunciones() {
        cinema.buscarSala("S1").deshabilitar();
        assertFalse(cinema.programarFuncion("F9", cinema.buscarPelicula("P1"), cinema.buscarSala("S1"),
                "2026-11-21", "10:00", 10000));
        assertNull(cinema.buscarFuncion("F9"));
    }

    @Test
    void tarifasPolimorficas() {
        assertEquals(15000, cinema.buscarTarifa(TipoCliente.GENERAL).calcularValor(15000), 0.01);
        assertEquals(12000, cinema.buscarTarifa(TipoCliente.ESTUDIANTE).calcularValor(15000), 0.01);
        assertEquals(10500, cinema.buscarTarifa(TipoCliente.NINO).calcularValor(15000), 0.01);
        assertEquals(11250, cinema.buscarTarifa(TipoCliente.ADULTO_MAYOR).calcularValor(15000), 0.01);
    }

    @Test
    void cambiarDescuentoAfectaElPrecio() {
        cinema.definirDescuento(TipoCliente.ESTUDIANTE, 0.5);
        cinema.venderEntrada("F1", estudiante, "A1");
        assertEquals(7500, cinema.getUltimaVenta().getValorPagado(), 0.01);
    }

    @Test
    void deshabilitarPeliculaYSala() {
        Habilitable p = cinema.buscarPelicula("P1");
        Habilitable s = cinema.buscarSala("S1");
        p.deshabilitar();
        s.deshabilitar();
        assertFalse(p.estaHabilitado());
        assertFalse(s.estaHabilitado());
    }

    @Test
    void ocupacionDeUnaFuncion() {
        Funcion f = cinema.buscarFuncion("F1");
        assertEquals(24, f.totalAsientos());
        cinema.venderEntrada("F1", estudiante, "A1");
        assertEquals(23, f.asientosDisponibles());
        assertEquals(1, f.asientosOcupados());
    }

    @Test
    void reportes() {
        cinema.venderEntrada("F1", estudiante, "A1");
        cinema.venderEntrada("F1", estudiante, "A2");
        cinema.venderEntrada("F2", estudiante, "A1");
        assertEquals(3, cinema.totalEntradasVendidas());
        assertEquals(36000, cinema.totalIngresos(), 0.01);
        assertEquals("Interestelar", cinema.peliculaMasVendida().getNombre());
        assertEquals(2, cinema.funcionesPorFecha("2026-11-20").size());
        assertEquals(0, cinema.funcionesPorFecha("2030-01-01").size());
    }
}
