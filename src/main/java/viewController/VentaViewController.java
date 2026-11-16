package viewController;

import controller.VentaController;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.Cliente;
import model.Funcion;
import model.TipoCliente;

public class VentaViewController {
    private VentaController controller;
    private Runnable onActualizarGlobal;
    private ToggleGroup grupoAsientos = new ToggleGroup();
    private ComboBox<Funcion> cmbFuncionVenta;
    private GridPane mapaVenta;

    public VentaViewController(VentaController controller, Runnable onActualizarGlobal) {
        this.controller = controller;
        this.onActualizarGlobal = onActualizarGlobal;
    }

    public Node construirVista() {
        cmbFuncionVenta = new ComboBox<>();
        cmbFuncionVenta.setPrefWidth(520);
        TextField txtId = new TextField();
        TextField txtNombre = new TextField();
        ComboBox<TipoCliente> cmbTipo = new ComboBox<>(FXCollections.observableArrayList(TipoCliente.values()));
        cmbTipo.setValue(TipoCliente.GENERAL);
        mapaVenta = UiHelper.nuevoMapa();
        TextArea txtEntrada = new TextArea();
        txtEntrada.setEditable(false);
        txtEntrada.setPrefRowCount(8);
        Button btnVender = new Button("Vender entrada");
        btnVender.getStyleClass().add("boton-principal");

        GridPane form = UiHelper.crearFormulario();
        UiHelper.agregar(form, 0, "Funcion:", cmbFuncionVenta);
        UiHelper.agregar(form, 1, "Identificacion:", txtId);
        UiHelper.agregar(form, 2, "Nombre:", txtNombre);
        UiHelper.agregar(form, 3, "Tipo de cliente:", cmbTipo);

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

        return UiHelper.contenedor(form, UiHelper.envolverMapa(mapaVenta), btnVender, new Label("Entrada generada:"), txtEntrada);
    }

    public void refrescar() {
        UiHelper.recargarCombo(cmbFuncionVenta, controller.funcionesProgramadas());
    }
}