package viewController;

import controller.PeliculaController;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.Clasificacion;
import model.Pelicula;

public class PeliculaViewController {
    private PeliculaController controller;
    private Runnable onActualizarGlobal;
    private ListView<Pelicula> lista;

    public PeliculaViewController(PeliculaController controller, Runnable onActualizarGlobal) {
        this.controller = controller;
        this.onActualizarGlobal = onActualizarGlobal;
    }

    public Node construirVista() {
        lista = new ListView<>();
        TextField txtCod = new TextField();
        TextField txtNombre = new TextField();
        TextField txtGenero = new TextField();
        TextField txtDuracion = new TextField();
        ComboBox<Clasificacion> cmbClasificacion =
                new ComboBox<>(FXCollections.observableArrayList(Clasificacion.values()));
        cmbClasificacion.setValue(Clasificacion.TODOS);

        GridPane form = UiHelper.crearFormulario();
        UiHelper.agregar(form, 0, "Codigo:", txtCod);
        UiHelper.agregar(form, 1, "Nombre:", txtNombre);
        UiHelper.agregar(form, 2, "Genero:", txtGenero);
        UiHelper.agregar(form, 3, "Duracion (min):", txtDuracion);
        UiHelper.agregar(form, 4, "Clasificacion:", cmbClasificacion);

        lista.getSelectionModel().selectedItemProperty().addListener((obs, anterior, p) -> {
            if (p != null) {
                txtCod.setText(p.getCodigo());
                txtNombre.setText(p.getNombre());
                txtGenero.setText(p.getGenero());
                txtDuracion.setText("" + p.getDuracion());
                cmbClasificacion.setValue(p.getClasificacion());
            }
        });

        Button btnRegistrar = new Button("Registrar");
        btnRegistrar.getStyleClass().add("boton-principal");
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

        Button btnModificar = new Button("Modificar seleccionada");
        btnModificar.setOnAction(e -> {
            Pelicula p = lista.getSelectionModel().getSelectedItem();
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

        Button btnDeshabilitar = new Button("Deshabilitar seleccionada");
        btnDeshabilitar.getStyleClass().add("boton-peligro");
        btnDeshabilitar.setOnAction(e -> {
            Pelicula p = lista.getSelectionModel().getSelectedItem();
            if (p == null) {
                UiHelper.error("Seleccione una pelicula de la lista");
                return;
            }
            p.deshabilitar();
            onActualizarGlobal.run();
        });

        Button btnLimpiar = new Button("Limpiar formulario");
        btnLimpiar.setOnAction(e -> {
            lista.getSelectionModel().clearSelection();
            txtCod.clear();
            txtNombre.clear();
            txtGenero.clear();
            txtDuracion.clear();
            cmbClasificacion.setValue(Clasificacion.TODOS);
        });

        return UiHelper.contenedor(new Label("Peliculas:"), lista, form,
                new HBox(8, btnRegistrar, btnModificar, btnDeshabilitar, btnLimpiar));
    }

    public void refrescar() {
        UiHelper.recargarLista(lista, controller.getPeliculas());
    }
}