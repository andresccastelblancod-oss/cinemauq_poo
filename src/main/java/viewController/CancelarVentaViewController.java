package viewController;

import controller.CancelarVentaController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.Venta;

public class CancelarVentaViewController {
    private CancelarVentaController controller;
    private Runnable onActualizarGlobal;

    @FXML private ListView<Venta> listaVentas;
    @FXML private Button btnCancelar;

    public void init(CancelarVentaController controller, Runnable onActualizarGlobal) {
        this.controller = controller;
        this.onActualizarGlobal = onActualizarGlobal;

        btnCancelar.setOnAction(e -> {
            Venta v = listaVentas.getSelectionModel().getSelectedItem();
            if (v == null) {
                UiHelper.error("Seleccione una venta de la lista");
                return;
            }
            if (controller.cancelarVenta(v.getCodigo())) {
                UiHelper.mensaje("Venta cancelada. El asiento quedo libre de nuevo.");
                onActualizarGlobal.run();
            } else {
                UiHelper.error(controller.getMensajeError());
            }
        });
    }

    public void refrescar() {
        UiHelper.recargarLista(listaVentas, controller.getVentas());
    }
}