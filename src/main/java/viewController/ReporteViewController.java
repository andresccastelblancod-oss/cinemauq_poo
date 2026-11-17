package viewController;

import controller.ReporteController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Funcion;
import model.Pelicula;

public class ReporteViewController {
    private ReporteController controller;

    @FXML private Label lblEntradas;
    @FXML private Label lblIngresos;
    @FXML private Label lblMasVendida;
    @FXML private ComboBox<Funcion> cmbFuncion;
    @FXML private Label lblOcupacion;
    @FXML private DatePicker dpFecha;
    @FXML private ListView<Funcion> listaFecha;

    public void init(ReporteController controller) {
        this.controller = controller;

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