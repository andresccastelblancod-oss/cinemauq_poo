package viewController;

import controller.DisponibilidadController;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.Funcion;

public class DisponibilidadViewController {
    private DisponibilidadController controller;
    private ComboBox<Funcion> cmbFuncion;

    public DisponibilidadViewController(DisponibilidadController controller) {
        this.controller = controller;
    }

    public Node construirVista() {
        cmbFuncion = new ComboBox<>();
        cmbFuncion.setPrefWidth(520);
        Label lblResumen = new Label("");
        lblResumen.getStyleClass().add("resumen");
        GridPane mapa = UiHelper.nuevoMapa();

        cmbFuncion.setOnAction(e -> {
            Funcion f = cmbFuncion.getValue();
            UiHelper.dibujarMapa(mapa, f, false);
            if (f == null) {
                lblResumen.setText("");
            } else {
                lblResumen.setText("Total: " + f.totalAsientos() + "   Disponibles: " + f.asientosDisponibles()
                        + "   Ocupados: " + f.asientosOcupados());
            }
        });

        return UiHelper.contenedor(new Label("Funcion:"), cmbFuncion, lblResumen, UiHelper.envolverMapa(mapa));
    }

    public void refrescar() {
        UiHelper.recargarCombo(cmbFuncion, controller.getFunciones());
        if (cmbFuncion.getOnAction() != null) {
            cmbFuncion.getOnAction().handle(null);
        }
    }
}