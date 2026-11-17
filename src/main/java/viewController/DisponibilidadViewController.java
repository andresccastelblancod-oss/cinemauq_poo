package viewController;

import controller.DisponibilidadController;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.Funcion;

public class DisponibilidadViewController {
    private DisponibilidadController controller;

    @FXML private ComboBox<Funcion> cmbFuncion;
    @FXML private Label lblResumen;
    @FXML private GridPane mapaDisponibilidad;

    public void init(DisponibilidadController controller) {
        this.controller = controller;

        cmbFuncion.setOnAction(e -> {
            Funcion f = cmbFuncion.getValue();
            UiHelper.dibujarMapa(mapaDisponibilidad, f, false);
            if (f == null) {
                lblResumen.setText("");
            } else {
                lblResumen.setText("Total: " + f.totalAsientos() + "   Disponibles: " + f.asientosDisponibles()
                        + "   Ocupados: " + f.asientosOcupados());
            }
        });
    }

    public void refrescar() {
        UiHelper.recargarCombo(cmbFuncion, controller.getFunciones());
        if (cmbFuncion.getOnAction() != null) {
            cmbFuncion.getOnAction().handle(null);
        }
    }
}