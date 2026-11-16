package viewController;

import controller.FuncionController;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.Funcion;
import model.Pelicula;
import model.Sala;

public class FuncionViewController {
    private FuncionController controller;
    private Runnable onActualizarGlobal;
    private ListView<Funcion> lista;
    private ComboBox<Pelicula> cmbPelicula;
    private ComboBox<Sala> cmbSala;

    public FuncionViewController(FuncionController controller, Runnable onActualizarGlobal) {
        this.controller = controller;
        this.onActualizarGlobal = onActualizarGlobal;
    }

    public Node construirVista() {
        lista = new ListView<>();
        TextField txtCod = new TextField();
        cmbPelicula = new ComboBox<>();
        cmbSala = new ComboBox<>();
        DatePicker dpFecha = new DatePicker();
        TextField txtHora = new TextField();
        txtHora.setPromptText("HH:MM");
        TextField txtValor = new TextField();

        GridPane form = UiHelper.crearFormulario();
        UiHelper.agregar(form, 0, "Codigo:", txtCod);
        UiHelper.agregar(form, 1, "Pelicula:", cmbPelicula);
        UiHelper.agregar(form, 2, "Sala:", cmbSala);
        UiHelper.agregar(form, 3, "Fecha:", dpFecha);
        UiHelper.agregar(form, 4, "Hora:", txtHora);
        UiHelper.agregar(form, 5, "Valor base:", txtValor);

        Button btnProgramar = new Button("Programar funcion");
        btnProgramar.getStyleClass().add("boton-principal");
        btnProgramar.setOnAction(e -> {
            Pelicula p = cmbPelicula.getValue();
            Sala s = cmbSala.getValue();
            if (txtCod.getText().trim().isEmpty() || p == null || s == null || dpFecha.getValue() == null) {
                UiHelper.error("Complete codigo, pelicula, sala y fecha");
                return;
            }
            if (!p.estaHabilitado()) {
                UiHelper.error("La pelicula " + p.getNombre() + " esta deshabilitada");
                return;
            }
            String hora = txtHora.getText().trim();
            if (!hora.matches("\\d{2}:\\d{2}")) {
                UiHelper.error("La hora debe tener el formato HH:MM");
                return;
            }
            Double valor = UiHelper.leerDecimal(txtValor, "El valor base");
            if (valor == null) return;
            if (valor <= 0) {
                UiHelper.error("El valor base debe ser mayor que cero");
                return;
            }
            if (controller.programarFuncion(txtCod.getText().trim(), p, s, dpFecha.getValue().toString(), hora, valor)) {
                UiHelper.mensaje("Funcion programada");
                onActualizarGlobal.run();
            } else {
                UiHelper.error(controller.getMensajeError());
            }
        });

        Button btnCancelar = new Button("Cancelar funcion seleccionada");
        btnCancelar.getStyleClass().add("boton-peligro");
        btnCancelar.setOnAction(e -> {
            Funcion f = lista.getSelectionModel().getSelectedItem();
            if (f == null) {
                UiHelper.error("Seleccione una funcion de la lista");
                return;
            }
            f.cancelar();
            onActualizarGlobal.run();
        });

        return UiHelper.contenedor(new Label("Funciones:"), lista, form, new HBox(8, btnProgramar, btnCancelar));
    }

    public void refrescar() {
        UiHelper.recargarLista(lista, controller.getFunciones());
        UiHelper.recargarCombo(cmbPelicula, controller.getPeliculas());
        UiHelper.recargarCombo(cmbSala, controller.getSalas());
    }
}