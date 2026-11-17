package viewController;

import controller.TarifaController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Tarifa;
import model.TipoCliente;

import java.util.ArrayList;

public class TarifaViewController {
    private TarifaController controller;
    private Runnable onActualizarGlobal;

    @FXML private ListView<String> listaTarifas;
    @FXML private ComboBox<TipoCliente> cmbTipo;
    @FXML private TextField txtDescuento;
    @FXML private Button btnAplicarDescuento;

    public void init(TarifaController controller, Runnable onActualizarGlobal) {
        this.controller = controller;
        this.onActualizarGlobal = onActualizarGlobal;

        cmbTipo.getItems().addAll(TipoCliente.ESTUDIANTE, TipoCliente.NINO, TipoCliente.ADULTO_MAYOR);
        cmbTipo.setValue(TipoCliente.ESTUDIANTE);

        btnAplicarDescuento.setOnAction(e -> {
            Double porcentaje = UiHelper.leerDecimal(txtDescuento, "El descuento");
            if (porcentaje == null) return;
            if (porcentaje < 0 || porcentaje > 100) {
                UiHelper.error("El descuento debe estar entre 0 y 100");
                return;
            }
            controller.definirDescuento(cmbTipo.getValue(), porcentaje / 100.0);
            onActualizarGlobal.run();
        });
    }

    public void refrescar() {
        ArrayList<String> filas = new ArrayList<>();
        for (int i = 0; i < controller.getTarifas().size(); i++) {
            Tarifa t = controller.getTarifas().get(i);
            filas.add(t.getTipo() + "  -  descuento " + Math.round(t.getDescuento() * 100) + "%");
        }
        listaTarifas.setItems(FXCollections.observableArrayList(filas));
    }
}