package viewController;

import controller.ReporteController;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Funcion;
import model.Pelicula;

public class ReporteViewController {
    private ReporteController controller;
    private Label lblEntradas;
    private Label lblIngresos;
    private Label lblMasVendida;
    private ComboBox<Funcion> cmbFuncion;
    private DatePicker dpFecha;

    public ReporteViewController(ReporteController controller) {
        this.controller = controller;
    }

    public Node construirVista() {
        lblEntradas = new Label();
        lblIngresos = new Label();
        lblMasVendida = new Label();
        lblEntradas.getStyleClass().add("dato");
        lblIngresos.getStyleClass().add("dato");
        lblMasVendida.getStyleClass().add("dato");
        cmbFuncion = new ComboBox<>();
        cmbFuncion.setPrefWidth(520);
        Label lblOcupacion = new Label("");
        lblOcupacion.getStyleClass().add("resumen");
        dpFecha = new DatePicker();
        ListView<Funcion> listaFecha = new ListView<>();

        cmbFuncion.setOnAction(e -> {
            Funcion f = cmbFuncion.getValue();
            if (f == null) {
                lblOcupacion.setText("");
            } else {
                lblOcupacion.setText("Disponibles: " + f.asientosDisponibles() + "   Ocupados: " + f.asientosOcupados());
            }
        });

        dpFecha.setOnAction(e -> {
            if (dpFecha.getValue() == null) {
                listaFecha.getItems().clear();
            } else {
                listaFecha.setItems(FXCollections.observableArrayList(
                        controller.funcionesPorFecha(dpFecha.getValue().toString())));
            }
        });

        return UiHelper.contenedor(lblEntradas, lblIngresos, lblMasVendida,
                new Label("Ocupacion de una funcion:"), cmbFuncion, lblOcupacion,
                new Label("Funciones programadas en una fecha:"), dpFecha, listaFecha);
    }

    public void refrescar() {
        lblEntradas.setText("Total de entradas vendidas: " + controller.totalEntradasVendidas());
        lblIngresos.setText("Total de ingresos: $" + controller.totalIngresos());
        Pelicula p = controller.peliculaMasVendida();
        lblMasVendida.setText("Pelicula mas vendida: " + (p == null ? "(sin ventas)" : p.getNombre()));
        UiHelper.recargarCombo(cmbFuncion, controller.getFunciones());
        if (cmbFuncion.getOnAction() != null) cmbFuncion.getOnAction().handle(null);
        if (dpFecha.getOnAction() != null) dpFecha.getOnAction().handle(null);
    }
}