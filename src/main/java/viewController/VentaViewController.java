package viewController;

import controller.VentaController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.Cliente;
import model.Funcion;
import model.TipoCliente;

public class VentaViewController {
    private VentaController controller;
    private Runnable onActualizarGlobal;
    private ToggleGroup grupoAsientos = new ToggleGroup();

    @FXML private ComboBox<Funcion> cmbFuncionVenta;
    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private ComboBox<TipoCliente> cmbTipo;
    @FXML private GridPane mapaVenta;
    @FXML private TextArea txtEntrada;
    @FXML private Button btnVender;

    public void init(VentaController controller, Runnable onActualizarGlobal) {
        this.controller = controller;
        this.onActualizarGlobal = onActualizarGlobal;

        cmbTipo.setItems(FXCollections.observableArrayList(TipoCliente.values()));
        cmbTipo.setValue(TipoCliente.GENERAL);

        cmbFuncionVenta.setOnAction(e -> {
            grupoAsientos = UiHelper.dibujarMapa(mapaVenta, cmbFuncionVenta.getValue(), true);
        });

        btnVender.setOnAction(e -> {
            Funcion f = cmbFuncionVenta.getValue();
            if (f == null) {
                UiHelper.error("Seleccione una funcion");
                return;
            }
            if (txtId.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty()) {
                UiHelper.error("Ingrese la identificacion y el nombre del cliente");
                return;
            }
            Toggle elegido = grupoAsientos.getSelectedToggle();
            if (elegido == null) {
                UiHelper.error("Seleccione un asiento en el mapa");
                return;
            }
            String codigoAsiento = (String) elegido.getUserData();
            Cliente cliente = new Cliente(txtId.getText().trim(), txtNombre.getText().trim(), cmbTipo.getValue());
            if (controller.venderEntrada(f, cliente, codigoAsiento)) {
                txtEntrada.setText(controller.getUltimaVenta().generarEntrada());
                onActualizarGlobal.run();
                grupoAsientos = UiHelper.dibujarMapa(mapaVenta, f, true);
            } else {
                UiHelper.error(controller.getMensajeError());
            }
        });
    }

    public void refrescar() {
        UiHelper.recargarCombo(cmbFuncionVenta, controller.funcionesProgramadas());
    }
}