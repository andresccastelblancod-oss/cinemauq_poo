package viewController;

import controller.TarifaController;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.Tarifa;
import model.TipoCliente;

import java.util.ArrayList;

public class TarifaViewController {
    private TarifaController controller;
    private Runnable onActualizarGlobal;
    private ListView<String> lista;

    public TarifaViewController(TarifaController controller, Runnable onActualizarGlobal) {
        this.controller = controller;
        this.onActualizarGlobal = onActualizarGlobal;
    }

    public Node construirVista() {
        lista = new ListView<>();
        ComboBox<TipoCliente> cmbTipo = new ComboBox<>();
        cmbTipo.getItems().addAll(TipoCliente.ESTUDIANTE, TipoCliente.NINO, TipoCliente.ADULTO_MAYOR);
        cmbTipo.setValue(TipoCliente.ESTUDIANTE);
        TextField txtDescuento = new TextField();
        txtDescuento.setPromptText("0 a 100");

        GridPane form = UiHelper.crearFormulario();
        UiHelper.agregar(form, 0, "Tipo de cliente:", cmbTipo);
        UiHelper.agregar(form, 1, "Nuevo descuento (%):", txtDescuento);

        Button btnAplicar = new Button("Aplicar descuento");
        btnAplicar.getStyleClass().add("boton-principal");
        btnAplicar.setOnAction(e -> {
            Double porcentaje = UiHelper.leerDecimal(txtDescuento, "El descuento");
            if (porcentaje == null) return;
            if (porcentaje < 0 || porcentaje > 100) {
                UiHelper.error("El descuento debe estar entre 0 y 100");
                return;
            }
            controller.definirDescuento(cmbTipo.getValue(), porcentaje / 100.0);
            onActualizarGlobal.run();
        });

        return UiHelper.contenedor(new Label("Tarifas actuales:"), lista, form, btnAplicar);
    }

    public void refrescar() {
        ArrayList<String> filas = new ArrayList<>();
        for (int i = 0; i < controller.getTarifas().size(); i++) {
            Tarifa t = controller.getTarifas().get(i);
            filas.add(t.getTipo() + "  -  descuento " + Math.round(t.getDescuento() * 100) + "%");
        }
        lista.setItems(FXCollections.observableArrayList(filas));
    }
}