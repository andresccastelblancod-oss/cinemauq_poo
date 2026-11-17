package viewController;

import controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Cinema;

import java.io.IOException;

public class Main extends Application {
    private Cinema cinema = new Cinema();
    private VentaViewController ventaController;
    private CancelarVentaViewController cancelarController;
    private DisponibilidadViewController disponibilidadController;
    private PeliculaViewController peliculaController;
    private FuncionViewController funcionController;
    private SalaViewController salaController;
    private TarifaViewController tarifaController;
    private ReporteViewController reporteController;

    @Override
    public void start(Stage stage) {
        cinema.cargarDatosDemo();

        try {
            TabPane tabs = new TabPane();
            tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
            tabs.setStyle("-fx-background-color: #14070b;");

            tabs.getTabs().addAll(
                    crearTab("Venta", "/venta.fxml",
                            ctrl -> { ventaController = (VentaViewController) ctrl; ventaController.init(new VentaController(cinema), this::refrescarTodo); }),
                    crearTab("Cancelar venta", "/cancelarVenta.fxml",
                            ctrl -> { cancelarController = (CancelarVentaViewController) ctrl; cancelarController.init(new CancelarVentaController(cinema), this::refrescarTodo); }),
                    crearTab("Disponibilidad", "/disponibilidad.fxml",
                            ctrl -> { disponibilidadController = (DisponibilidadViewController) ctrl; disponibilidadController.init(new DisponibilidadController(cinema)); }),
                    crearTab("Peliculas", "/peliculas.fxml",
                            ctrl -> { peliculaController = (PeliculaViewController) ctrl; peliculaController.init(new PeliculaController(cinema), this::refrescarTodo); }),
                    crearTab("Funciones", "/funciones.fxml",
                            ctrl -> { funcionController = (FuncionViewController) ctrl; funcionController.init(new FuncionController(cinema), this::refrescarTodo); }),
                    crearTab("Salas", "/salas.fxml",
                            ctrl -> { salaController = (SalaViewController) ctrl; salaController.init(new SalaController(cinema), this::refrescarTodo); }),
                    crearTab("Tarifas", "/tarifas.fxml",
                            ctrl -> { tarifaController = (TarifaViewController) ctrl; tarifaController.init(new TarifaController(cinema), this::refrescarTodo); }),
                    crearTab("Reportes", "/reportes.fxml",
                            ctrl -> { reporteController = (ReporteViewController) ctrl; reporteController.init(new ReporteController(cinema)); })
            );

            tabs.getSelectionModel().selectedItemProperty().addListener((obs, ant, nue) -> refrescarTodo());
            refrescarTodo();

            BorderPane raiz = new BorderPane();
            raiz.setStyle("-fx-base: #2b1118; -fx-background: #14070b; -fx-control-inner-background: #1e0c11; " +
                    "-fx-control-inner-background-alt: #260f16; -fx-accent: #f5c542; -fx-focus-color: #f5c542; " +
                    "-fx-text-base-color: #f3e9dc; -fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");
            raiz.setTop(crearEncabezado());
            raiz.setCenter(tabs);

            Scene escena = new Scene(raiz, 980, 780);
            stage.setTitle("CINEMAUQ");
            stage.setScene(escena);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Tab crearTab(String titulo, String fxmlRuta, java.util.function.Consumer<Object> accionController) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlRuta));
        Parent vista = loader.load();
        accionController.accept(loader.getController());
        Tab tab = new Tab(titulo, vista);
        tab.setClosable(false);
        tab.setStyle("-fx-background-color: #3a1520; -fx-background-radius: 8 8 0 0; -fx-padding: 6 14 6 14; -fx-font-weight: bold;");
        return tab;
    }

    private void refrescarTodo() {
        if (ventaController != null) ventaController.refrescar();
        if (cancelarController != null) cancelarController.refrescar();
        if (disponibilidadController != null) disponibilidadController.refrescar();
        if (peliculaController != null) peliculaController.refrescar();
        if (funcionController != null) funcionController.refrescar();
        if (salaController != null) salaController.refrescar();
        if (tarifaController != null) tarifaController.refrescar();
        if (reporteController != null) reporteController.refrescar();
    }

    private Node crearEncabezado() {
        StringBuilder bombillos = new StringBuilder();
        for (int i = 0; i < 80; i++) {
            bombillos.append("\u25CF  ");
        }
        Label luces = new Label(bombillos.toString());
        luces.setStyle("-fx-text-fill: #ffd54f; -fx-font-size: 11px; -fx-text-overrun: clip; -fx-effect: dropshadow(gaussian, #ffb300, 8, 0.4, 0, 0);");
        luces.setMinWidth(0);
        luces.setPrefWidth(10);
        luces.setMaxWidth(Double.MAX_VALUE);

        Label titulo = new Label("\u2605  CINEMAUQ  \u2605");
        titulo.setStyle("-fx-text-fill: #f5c542; -fx-font-family: 'Georgia'; -fx-font-size: 34px; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(245, 197, 66, 0.6), 14, 0.3, 0, 0);");

        Label subtitulo = new Label("Sistema de gestion de cine");
        subtitulo.setStyle("-fx-text-fill: #f3e9dc; -fx-font-size: 13px; -fx-font-style: italic;");

        VBox encabezado = new VBox(2, luces, titulo, subtitulo);
        encabezado.setAlignment(Pos.CENTER);
        encabezado.setStyle("-fx-background-color: linear-gradient(to bottom, #6b1222, #2b0a12); -fx-padding: 6 0 10 0; -fx-border-color: transparent transparent #f5c542 transparent; -fx-border-width: 0 0 3 0;");
        return encabezado;
    }
}