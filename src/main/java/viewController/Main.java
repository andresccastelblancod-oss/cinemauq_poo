package viewController;

import controller.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Cinema;

public class Main extends Application {
    private Cinema cinema = new Cinema();
    private VentaViewController ventaView;
    private CancelarVentaViewController cancelarView;
    private DisponibilidadViewController disponibilidadView;
    private PeliculaViewController peliculaView;
    private FuncionViewController funcionView;
    private SalaViewController salaView;
    private TarifaViewController tarifaView;
    private ReporteViewController reporteView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        cinema.cargarDatosDemo();

        java.net.URL css = getClass().getResource("/cinema.css");
        String hojaEstilo = (css != null) ? css.toExternalForm() : null;
        UiHelper.setHojaEstilo(hojaEstilo);

        // Instanciar cada ViewController con su respectivo Controller
        ventaView = new VentaViewController(new VentaController(cinema), this::refrescarTodo);
        cancelarView = new CancelarVentaViewController(new CancelarVentaController(cinema), this::refrescarTodo);
        disponibilidadView = new DisponibilidadViewController(new DisponibilidadController(cinema));
        peliculaView = new PeliculaViewController(new PeliculaController(cinema), this::refrescarTodo);
        funcionView = new FuncionViewController(new FuncionController(cinema), this::refrescarTodo);
        salaView = new SalaViewController(new SalaController(cinema), this::refrescarTodo);
        tarifaView = new TarifaViewController(new TarifaController(cinema), this::refrescarTodo);
        reporteView = new ReporteViewController(new ReporteController(cinema));

        TabPane tabs = new TabPane();
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabs.getTabs().addAll(
                new Tab("Venta", ventaView.construirVista()),
                new Tab("Cancelar venta", cancelarView.construirVista()),
                new Tab("Disponibilidad", disponibilidadView.construirVista()),
                new Tab("Peliculas", peliculaView.construirVista()),
                new Tab("Funciones", funcionView.construirVista()),
                new Tab("Salas", salaView.construirVista()),
                new Tab("Tarifas", tarifaView.construirVista()),
                new Tab("Reportes", reporteView.construirVista())
        );

        for (Tab t : tabs.getTabs()) {
            t.setClosable(false);
        }
        tabs.getSelectionModel().selectedItemProperty().addListener((obs, ant, nue) -> refrescarTodo());

        refrescarTodo();

        BorderPane raiz = new BorderPane();
        raiz.setTop(crearEncabezado());
        raiz.setCenter(tabs);

        Scene escena = new Scene(raiz, 980, 780);
        if (hojaEstilo != null) {
            escena.getStylesheets().add(hojaEstilo);
        }

        stage.setTitle("CINEMAUQ");
        stage.setScene(escena);
        stage.show();
    }

    private void refrescarTodo() {
        ventaView.refrescar();
        cancelarView.refrescar();
        disponibilidadView.refrescar();
        peliculaView.refrescar();
        funcionView.refrescar();
        salaView.refrescar();
        tarifaView.refrescar();
        reporteView.refrescar();
    }

    private Node crearEncabezado() {
        StringBuilder bombillos = new StringBuilder();
        for (int i = 0; i < 80; i++) {
            bombillos.append("\u25CF  ");
        }
        Label luces = new Label(bombillos.toString());
        luces.getStyleClass().add("luces");
        luces.setMinWidth(0);
        luces.setPrefWidth(10);
        luces.setMaxWidth(Double.MAX_VALUE);

        Label titulo = new Label("\u2605  CINEMAUQ  \u2605");
        titulo.getStyleClass().add("titulo");

        Label subtitulo = new Label("Sistema de gestion de cine");
        subtitulo.getStyleClass().add("subtitulo");

        VBox encabezado = new VBox(2, luces, titulo, subtitulo);
        encabezado.setAlignment(Pos.CENTER);
        encabezado.getStyleClass().add("encabezado");
        return encabezado;
    }
}