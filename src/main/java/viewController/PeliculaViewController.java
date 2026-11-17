package viewController;

import controller.PeliculaController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.Clasificacion;
import model.Pelicula;

public class PeliculaViewController {
    private PeliculaController controller;
    private Runnable onActualizarGlobal;

    @FXML private ListView<Pelicula> listaPeliculas;
    @FXML private TextField txtCod;
    @FXML private TextField txtNombre;
    @FXML private TextField txtGenero;
    @FXML private TextField txtDuracion;
    @FXML private ComboBox<Clasificacion> cmbClasificacion;
    @FXML private Button btnRegistrar;
    @FXML private Button btnModificar;
    @FXML private Button btnDeshabilitar;
    @FXML private Button btnLimpiar;

    public void init(PeliculaController controller, Runnable onActualizarGlobal) {
        this.controller = controller;
        this.onActualizarGlobal = onActualizarGlobal;

        cmbClasificacion.setItems(FXCollections.observableArrayList(Clasificacion.values()));
        cmbClasificacion.setValue(Clasificacion.TODOS);

        listaPeliculas.getSelectionModel().selectedItemProperty().addListener((obs, anterior, p) -> {
            if (p != null) {
                txtCod.setText(p.getCodigo());
                txtNombre.setText(p.getNombre());
                txtGenero.setText(p.getGenero());
                txtDuracion.setText("" + p.getDuracion());
                cmbClasificacion.setValue(p.getClasificacion());
            }
        });

        btnRegistrar.setOnAction(e -> {
            if (txtCod.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty()
                    || txtGenero.getText().trim().isEmpty()) {
                UiHelper.error("Complete codigo, nombre y genero");
                return;
            }
            Integer duracion = UiHelper.leerEntero(txtDuracion, "La duracion");
            if (duracion == null) return;
            if (duracion <= 0) {
                UiHelper.error("La duracion debe ser mayor que cero");
                return;
            }
            Pelicula p = new Pelicula(txtCod.getText().trim(), txtNombre.getText().trim(),
                    txtGenero.getText().trim(), duracion, cmbClasificacion.getValue());
            if (controller.registrarPelicula(p)) {
                UiHelper.mensaje("Pelicula registrada");
                onActualizarGlobal.run();
            } else {
                UiHelper.error(controller.getMensajeError());
            }
        });

        btnModificar.setOnAction(e -> {
            Pelicula p = listaPeliculas.getSelectionModel().getSelectedItem();
            if (p == null) {
                UiHelper.error("Seleccione una pelicula de la lista");
                return;
            }
            Integer duracion = UiHelper.leerEntero(txtDuracion, "La duracion");
            if (duracion == null) return;
            if (duracion <= 0) {
                UiHelper.error("La duracion debe ser mayor que cero");
                return;
            }
            p.setNombre(txtNombre.getText().trim());
            p.setGenero(txtGenero.getText().trim());
            p.setDuracion(duracion);
            p.setClasificacion(cmbClasificacion.getValue());
            onActualizarGlobal.run();
        });

        btnDeshabilitar.setOnAction(e -> {
            Pelicula p = listaPeliculas.getSelectionModel().getSelectedItem();
            if (p == null) {
                UiHelper.error("Seleccione una pelicula de la lista");
                return;
            }
            p.deshabilitar();
            onActualizarGlobal.run();
        });

        btnLimpiar.setOnAction(e -> {
            listaPeliculas.getSelectionModel().clearSelection();
            txtCod.clear();
            txtNombre.clear();
            txtGenero.clear();
            txtDuracion.clear();
            cmbClasificacion.setValue(Clasificacion.TODOS);
        });
    }

    public void refrescar() {
        UiHelper.recargarLista(listaPeliculas, controller.getPeliculas());
    }
}