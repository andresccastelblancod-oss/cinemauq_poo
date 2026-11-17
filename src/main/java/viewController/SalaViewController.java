package viewController;

import controller.SalaController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Sala;

public class SalaViewController {
    private SalaController controller;
    private Runnable onActualizarGlobal;

    @FXML private ListView<Sala> listaSalas;
    @FXML private TextField txtCod;
    @FXML private TextField txtNombre;
    @FXML private TextField txtFilas;
    @FXML private TextField txtColumnas;
    @FXML private Button btnRegistrarSala;
    @FXML private Button btnCambiarNombre;
    @FXML private Button btnDeshabilitarSala;

    public void init(SalaController controller, Runnable onActualizarGlobal) {
        this.controller = controller;
        this.onActualizarGlobal = onActualizarGlobal;

        listaSalas.getSelectionModel().selectedItemProperty().addListener((obs, anterior, s) -> {
            if (s != null) {
                txtCod.setText(s.getCodigo());
                txtNombre.setText(s.getNombre());
            }
        });

        btnRegistrarSala.setOnAction(e -> {
            if (txtCod.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty()) {
                UiHelper.error("Complete codigo y nombre");
                return;
            }
            Integer filas = UiHelper.leerEntero(txtFilas, "Las filas");
            if (filas == null) return;
            Integer columnas = UiHelper.leerEntero(txtColumnas, "Las columnas");
            if (columnas == null) return;
            if (filas < 1 || filas > 26 || columnas < 1) {
                UiHelper.error("Use entre 1 y 26 filas (letras A-Z) y al menos 1 columna");
                return;
            }
            if (controller.registrarSala(new Sala(txtCod.getText().trim(), txtNombre.getText().trim(), filas, columnas))) {
                UiHelper.mensaje("Sala registrada");
                onActualizarGlobal.run();
            } else {
                UiHelper.error(controller.getMensajeError());
            }
        });

        btnCambiarNombre.setOnAction(e -> {
            Sala s = listaSalas.getSelectionModel().getSelectedItem();
            if (s == null) {
                UiHelper.error("Seleccione una sala de la lista");
                return;
            }
            if (txtNombre.getText().trim().isEmpty()) {
                UiHelper.error("Escriba el nuevo nombre");
                return;
            }
            s.setNombre(txtNombre.getText().trim());
            onActualizarGlobal.run();
        });

        btnDeshabilitarSala.setOnAction(e -> {
            Sala s = listaSalas.getSelectionModel().getSelectedItem();
            if (s == null) {
                UiHelper.error("Seleccione una sala de la lista");
                return;
            }
            s.deshabilitar();
            onActualizarGlobal.run();
        });
    }

    public void refrescar() {
        UiHelper.recargarLista(listaSalas, controller.getSalas());
    }
}