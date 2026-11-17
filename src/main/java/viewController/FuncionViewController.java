package viewController;

import controller.FuncionController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Funcion;
import model.Pelicula;
import model.Sala;

public class FuncionViewController {
    private FuncionController controller;
    private Runnable onActualizarGlobal;

    @FXML private ListView<Funcion> listaFunciones;
    @FXML private TextField txtCod;
    @FXML private ComboBox<Pelicula> cmbPelicula;
    @FXML private ComboBox<Sala> cmbSala;
    @FXML private DatePicker dpFecha;
    @FXML private TextField txtHora;
    @FXML private TextField txtValor;
    @FXML private Button btnProgramar;
    @FXML private Button btnCancelarFuncion;

    public void init(FuncionController controller, Runnable onActualizarGlobal) {
        this.controller = controller;
        this.onActualizarGlobal = onActualizarGlobal;

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

        btnCancelarFuncion.setOnAction(e -> {
            Funcion f = listaFunciones.getSelectionModel().getSelectedItem();
            if (f == null) {
                UiHelper.error("Seleccione una funcion de la lista");
                return;
            }
            f.cancelar();
            onActualizarGlobal.run();
        });
    }

    public void refrescar() {
        UiHelper.recargarLista(listaFunciones, controller.getFunciones());
        UiHelper.recargarCombo(cmbPelicula, controller.getPeliculas());
        UiHelper.recargarCombo(cmbSala, controller.getSalas());
    }
}