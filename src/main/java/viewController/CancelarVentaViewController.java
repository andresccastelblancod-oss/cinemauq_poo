package viewController;

import controller.CancelarVentaController;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Venta;

public class CancelarVentaViewController {
    private CancelarVentaController controller;
    private Runnable onActualizarGlobal;
    private ListView<Venta> lista;

    public CancelarVentaViewController(CancelarVentaController controller, Runnable onActualizarGlobal) {
        this.controller = controller;
        this.onActualizarGlobal = onActualizarGlobal;
    }

    public Node construirVista() {
        lista = new ListView<>();
        Button btnCancelar = new Button("Cancelar venta seleccionada");
        btnCancelar.getStyleClass().add("boton-peligro");

        btnCancelar.setOnAction(e -> {
            Venta v = lista.getSelectionModel().getSelectedItem();
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

        return UiHelper.contenedor(new Label("Ventas registradas:"), lista, btnCancelar);
    }

    public void refrescar() {
        UiHelper.recargarLista(lista, controller.getVentas());
    }
}