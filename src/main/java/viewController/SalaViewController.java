package viewController;

import controller.SalaController;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.Sala;

public class SalaViewController {
    private SalaController controller;
    private Runnable onActualizarGlobal;
    private ListView<Sala> lista;

    public SalaViewController(SalaController controller, Runnable onActualizarGlobal) {
        this.controller = controller;
        this.onActualizarGlobal = onActualizarGlobal;
    }

    public Node construirVista() {
        lista = new ListView<>();
        TextField txtCod = new TextField();
        TextField txtNombre = new TextField();
        TextField txtFilas = new TextField();
        TextField txtColumnas = new TextField();

        GridPane form = UiHelper.crearFormulario();
        UiHelper.agregar(form, 0, "Codigo:", txtCod);
        UiHelper.agregar(form, 1, "Nombre:", txtNombre);
        UiHelper.agregar(form, 2, "Filas:", txtFilas);
        UiHelper.agregar(form, 3, "Columnas:", txtColumnas);

        lista.getSelectionModel().selectedItemProperty().addListener((obs, anterior, s) -> {
            if (s != null) {
                txtCod.setText(s.getCodigo());
                txtNombre.setText(s.getNombre());
            }
        });

        Button btnRegistrar = new Button("Registrar");
        btnRegistrar.getStyleClass().add("boton-principal");
        btnRegistrar.setOnAction(e -> {
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

        Button btnNombre = new Button("Cambiar nombre de la seleccionada");
        btnNombre.setOnAction(e -> {
            Sala s = lista.getSelectionModel().getSelectedItem();
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

        Button btnDeshabilitar = new Button("Deshabilitar seleccionada");
        btnDeshabilitar.getStyleClass().add("boton-peligro");
        btnDeshabilitar.setOnAction(e -> {
            Sala s = lista.getSelectionModel().getSelectedItem();
            if (s == null) {
                UiHelper.error("Seleccione una sala de la lista");
                return;
            }
            s.deshabilitar();
            onActualizarGlobal.run();
        });

        return UiHelper.contenedor(new Label("Salas:"), lista, form, new HBox(8, btnRegistrar, btnNombre, btnDeshabilitar));
    }

    public void refrescar() {
        UiHelper.recargarLista(lista, controller.getSalas());
    }
}