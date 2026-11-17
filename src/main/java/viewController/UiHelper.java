package viewController;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Asiento;
import model.Funcion;

import java.util.List;

public class UiHelper {
    private static final String ESTILO_ASIENTO_LIBRE =
            "-fx-background-color: linear-gradient(to bottom, #43a047, #1b5e20); " +
                    "-fx-background-radius: 10 10 4 4; -fx-border-color: #a5d6a7; -fx-border-radius: 10 10 4 4; " +
                    "-fx-text-fill: white; -fx-font-weight: bold; -fx-alignment: center; -fx-cursor: hand;";

    private static final String ESTILO_ASIENTO_SELECCIONADO =
            "-fx-background-color: linear-gradient(to bottom, #ffe082, #f5c542); " +
                    "-fx-background-radius: 10 10 4 4; -fx-border-color: #fff8e1; -fx-border-radius: 10 10 4 4; " +
                    "-fx-text-fill: #2b1118; -fx-font-weight: bold; -fx-alignment: center; -fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, #ffd54f, 12, 0.5, 0, 0);";

    private static final String ESTILO_ASIENTO_OCUPADO =
            "-fx-background-color: linear-gradient(to bottom, #c62828, #7f0000); " +
                    "-fx-background-radius: 10 10 4 4; -fx-border-color: #ef9a9a; -fx-border-radius: 10 10 4 4; " +
                    "-fx-text-fill: #ffcdd2; -fx-font-weight: bold; -fx-alignment: center; -fx-opacity: 1;";

    public static void aplicarEstilo(Alert a) {
        a.getDialogPane().setStyle("-fx-background-color: #2b1118; -fx-font-family: 'Segoe UI'; -fx-font-size: 14px;");
        Node contenido = a.getDialogPane().lookup(".content.label");
        if (contenido != null) {
            contenido.setStyle("-fx-text-fill: #f3e9dc;");
        }
    }

    public static void mensaje(String texto) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, texto);
        a.setHeaderText(null);
        aplicarEstilo(a);
        a.showAndWait();
    }

    public static void error(String texto) {
        Alert a = new Alert(Alert.AlertType.ERROR, texto);
        a.setHeaderText(null);
        aplicarEstilo(a);
        a.showAndWait();
    }

    public static <T> void recargarCombo(ComboBox<T> combo, List<T> datos) {
        T seleccionado = combo.getValue();
        combo.setItems(FXCollections.observableArrayList(datos));
        if (seleccionado != null && datos.contains(seleccionado)) {
            combo.setValue(seleccionado);
        } else {
            combo.setValue(null);
        }
    }

    public static <T> void recargarLista(ListView<T> lista, List<T> datos) {
        lista.setItems(FXCollections.observableArrayList(datos));
    }

    public static Integer leerEntero(TextField campo, String nombre) {
        try {
            return Integer.parseInt(campo.getText().trim());
        } catch (NumberFormatException e) {
            error(nombre + " debe ser un numero entero");
            return null;
        }
    }

    public static Double leerDecimal(TextField campo, String nombre) {
        try {
            return Double.parseDouble(campo.getText().trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            error(nombre + " debe ser un numero");
            return null;
        }
    }

    public static ToggleGroup dibujarMapa(GridPane grid, Funcion f, boolean seleccionable) {
        grid.getChildren().clear();
        ToggleGroup grupoAsientos = seleccionable ? new ToggleGroup() : null;
        if (f == null) {
            return grupoAsientos;
        }
        for (int i = 0; i < f.getAsientos().size(); i++) {
            Asiento a = f.getAsientos().get(i);
            int columna = a.getNumero() - 1;
            int fila = a.getFila() - 'A';
            if (seleccionable) {
                ToggleButton b = new ToggleButton(a.getCodigo());
                b.setPrefWidth(56);
                b.setUserData(a.getCodigo());
                if (a.estaDisponible()) {
                    b.setToggleGroup(grupoAsientos);
                    b.setStyle(ESTILO_ASIENTO_LIBRE);
                    b.selectedProperty().addListener((obs, ant, sel) ->
                            b.setStyle(sel ? ESTILO_ASIENTO_SELECCIONADO : ESTILO_ASIENTO_LIBRE));
                } else {
                    b.setDisable(true);
                    b.setStyle(ESTILO_ASIENTO_OCUPADO);
                }
                grid.add(b, columna, fila);
            } else {
                Label l = new Label(a.getCodigo());
                l.setPrefWidth(56);
                l.setAlignment(Pos.CENTER);
                l.setPadding(new Insets(6));
                l.setStyle(a.estaDisponible() ? ESTILO_ASIENTO_LIBRE : ESTILO_ASIENTO_OCUPADO);
                grid.add(l, columna, fila);
            }
        }
        return grupoAsientos;
    }
}