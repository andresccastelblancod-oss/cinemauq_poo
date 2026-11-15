import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static Cinema cinema = new Cinema();

    public static void main(String[] args) {
        cinema.cargarDatosDemo();
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n==============================");
            System.out.println("          CINEMAUQ");
            System.out.println("==============================");
            System.out.println("1. Venta de entrada");
            System.out.println("2. Cancelar venta");
            System.out.println("3. Consultar peliculas");
            System.out.println("4. Consultar funciones");
            System.out.println("5. Consultar disponibilidad");
            System.out.println("6. Administrar peliculas");
            System.out.println("7. Administrar funciones");
            System.out.println("8. Administrar salas");
            System.out.println("9. Generar reportes");
            System.out.println("10. Administrar tarifas");
            System.out.println("0. Salir");
            opcion = leerEntero("Opcion: ");
            switch (opcion) {
                case 1: venderEntrada(); break;
                case 2: cancelarVenta(); break;
                case 3: mostrar(cinema.getPeliculas()); break;
                case 4: mostrar(cinema.getFunciones()); break;
                case 5: consultarDisponibilidad(); break;
                case 6: administrarPeliculas(); break;
                case 7: administrarFunciones(); break;
                case 8: administrarSalas(); break;
                case 9: reportes(); break;
                case 10: administrarTarifas(); break;
                case 0: System.out.println("Hasta pronto"); break;
                default: System.out.println("Opcion invalida");
            }
        }
    }

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static double leerDecimal(String mensaje) {
        System.out.print(mensaje);
        try {
            return Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    private static void mostrar(ArrayList<?> lista) {
        if (lista.isEmpty()) {
            System.out.println("(sin registros)");
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(lista.get(i));
        }
    }

    private static TipoCliente leerTipoCliente() {
        System.out.println("1. GENERAL  2. ESTUDIANTE  3. NINO  4. ADULTO_MAYOR");
        int t = leerEntero("Tipo de cliente: ");
        if (t == 2) return TipoCliente.ESTUDIANTE;
        if (t == 3) return TipoCliente.NINO;
        if (t == 4) return TipoCliente.ADULTO_MAYOR;
        return TipoCliente.GENERAL;
    }

    private static Clasificacion leerClasificacion() {
        System.out.println("1. TODOS  2. MAYORES_7  3. MAYORES_12  4. MAYORES_15  5. MAYORES_18");
        int c = leerEntero("Clasificacion: ");
        if (c == 2) return Clasificacion.MAYORES_7;
        if (c == 3) return Clasificacion.MAYORES_12;
        if (c == 4) return Clasificacion.MAYORES_15;
        if (c == 5) return Clasificacion.MAYORES_18;
        return Clasificacion.TODOS;
    }

    private static void venderEntrada() {
        mostrar(cinema.getFunciones());
        String codFuncion = leerTexto("Codigo de la funcion: ");
        String id = leerTexto("Identificacion del cliente: ");
        String nombre = leerTexto("Nombre del cliente: ");
        TipoCliente tipo = leerTipoCliente();
        String asiento = leerTexto("Asiento (ej. A1): ");
        if (cinema.venderEntrada(codFuncion, new Cliente(id, nombre, tipo), asiento)) {
            System.out.println("\n" + cinema.getUltimaVenta().generarEntrada());
        } else {
            System.out.println("ERROR: " + cinema.getMensajeError());
        }
    }

    private static void cancelarVenta() {
        mostrar(cinema.getVentas());
        if (cinema.cancelarVenta(leerTexto("Codigo de la venta: "))) {
            System.out.println("Venta cancelada");
        } else {
            System.out.println("ERROR: " + cinema.getMensajeError());
        }
    }

    private static void consultarDisponibilidad() {
        Funcion f = cinema.buscarFuncion(leerTexto("Codigo de la funcion: "));
        if (f == null) {
            System.out.println("ERROR: la funcion no existe");
            return;
        }
        System.out.println("Funcion: " + f.getPelicula().getNombre() + " | Sala: " + f.getSala().getNombre()
                + " | Hora: " + f.getHora());
        System.out.println("Total: " + f.totalAsientos() + " | Disponibles: " + f.asientosDisponibles()
                + " | Ocupados: " + f.asientosOcupados());
        mostrar(f.getAsientos());
    }

    private static void administrarPeliculas() {
        System.out.println("1. Registrar  2. Modificar  3. Deshabilitar");
        int op = leerEntero("Opcion: ");
        if (op == 1) {
            String cod = leerTexto("Codigo: ");
            String nombre = leerTexto("Nombre: ");
            String genero = leerTexto("Genero: ");
            int duracion = leerEntero("Duracion (min): ");
            if (cinema.registrarPelicula(new Pelicula(cod, nombre, genero, duracion, leerClasificacion()))) {
                System.out.println("Pelicula registrada");
            } else {
                System.out.println("ERROR: " + cinema.getMensajeError());
            }
        } else if (op == 2) {
            Pelicula p = cinema.buscarPelicula(leerTexto("Codigo: "));
            if (p == null) {
                System.out.println("ERROR: la pelicula no existe");
                return;
            }
            p.setNombre(leerTexto("Nuevo nombre: "));
            p.setGenero(leerTexto("Nuevo genero: "));
            p.setDuracion(leerEntero("Nueva duracion: "));
            p.setClasificacion(leerClasificacion());
        } else if (op == 3) {
            Pelicula p = cinema.buscarPelicula(leerTexto("Codigo: "));
            if (p == null) {
                System.out.println("ERROR: la pelicula no existe");
            } else {
                p.deshabilitar();
            }
        }
    }

    private static void administrarSalas() {
        System.out.println("1. Registrar  2. Consultar  3. Modificar nombre  4. Deshabilitar");
        int op = leerEntero("Opcion: ");
        if (op == 1) {
            String cod = leerTexto("Codigo: ");
            String nombre = leerTexto("Nombre: ");
            int filas = leerEntero("Filas: ");
            int columnas = leerEntero("Columnas: ");
            if (cinema.registrarSala(new Sala(cod, nombre, filas, columnas))) {
                System.out.println("Sala registrada");
            } else {
                System.out.println("ERROR: " + cinema.getMensajeError());
            }
        } else if (op == 2) {
            mostrar(cinema.getSalas());
        } else if (op == 3) {
            Sala s = cinema.buscarSala(leerTexto("Codigo: "));
            if (s == null) {
                System.out.println("ERROR: la sala no existe");
            } else {
                s.setNombre(leerTexto("Nuevo nombre: "));
            }
        } else if (op == 4) {
            Sala s = cinema.buscarSala(leerTexto("Codigo: "));
            if (s == null) {
                System.out.println("ERROR: la sala no existe");
            } else {
                s.deshabilitar();
            }
        }
    }

    private static void administrarFunciones() {
        System.out.println("1. Programar  2. Cancelar funcion");
        int op = leerEntero("Opcion: ");
        if (op == 1) {
            String cod = leerTexto("Codigo de la funcion: ");
            Pelicula p = cinema.buscarPelicula(leerTexto("Codigo de la pelicula: "));
            Sala s = cinema.buscarSala(leerTexto("Codigo de la sala: "));
            if (p == null || s == null) {
                System.out.println("ERROR: la pelicula o la sala no existe");
                return;
            }
            String fecha = leerTexto("Fecha (AAAA-MM-DD): ");
            String hora = leerTexto("Hora (HH:MM): ");
            double valor = leerDecimal("Valor base: ");
            if (cinema.programarFuncion(cod, p, s, fecha, hora, valor)) {
                System.out.println("Funcion programada");
            } else {
                System.out.println("ERROR: " + cinema.getMensajeError());
            }
        } else if (op == 2) {
            Funcion f = cinema.buscarFuncion(leerTexto("Codigo de la funcion: "));
            if (f == null) {
                System.out.println("ERROR: la funcion no existe");
            } else {
                f.cancelar();
            }
        }
    }

    private static void administrarTarifas() {
        for (int i = 0; i < cinema.getTarifas().size(); i++) {
            Tarifa t = cinema.getTarifas().get(i);
            System.out.println(t.getTipo() + " descuento " + (t.getDescuento() * 100) + "%");
        }
        if (leerTexto("Desea modificar un descuento? (s/n): ").equalsIgnoreCase("s")) {
            TipoCliente tipo = leerTipoCliente();
            cinema.definirDescuento(tipo, leerDecimal("Nuevo descuento (0 a 1): "));
        }
    }

    private static void reportes() {
        System.out.println("Total de entradas vendidas: " + cinema.totalEntradasVendidas());
        System.out.println("Total de ingresos: $" + cinema.totalIngresos());
        Pelicula p = cinema.peliculaMasVendida();
        System.out.println("Pelicula mas vendida: " + (p == null ? "(sin ventas)" : p.getNombre()));
        Funcion f = cinema.buscarFuncion(leerTexto("Codigo de funcion para ver ocupacion: "));
        if (f == null) {
            System.out.println("ERROR: la funcion no existe");
        } else {
            System.out.println("Disponibles: " + f.asientosDisponibles() + " | Ocupados: " + f.asientosOcupados());
        }
        mostrar(cinema.funcionesPorFecha(leerTexto("Fecha para ver funciones (AAAA-MM-DD): ")));
    }
}
