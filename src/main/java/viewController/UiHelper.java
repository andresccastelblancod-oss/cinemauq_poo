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
    private static String hojaEstilo;

    public static void setHojaEstilo(String css) {
        hojaEstilo = css;
    }

    public static void aplicarEstilo(Alert a) {
        if (hojaEstilo != null) {
            a.getDialogPane().getStylesheets().add(hojaEstilo);
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

    public static GridPane crearFormulario() {
        GridPane g = new GridPane();
        g.setHgap(10);
        g.setVgap(8);
        return g;
    }

    public static void agregar(GridPane g, int fila, String texto, Node control) {
        g.add(new Label(texto), 0, fila);
        g.add(control, 1, fila);
    }

    public static VBox contenedor(Node... nodos) {
        VBox v = new VBox(10, nodos);
        v.setPadding(new Insets(12));
        v.getStyleClass().add("panel");
        return v;
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
                    b.getStyleClass().add("asiento-libre");
                } else {
                    b.setDisable(true);
                    b.getStyleClass().add("asiento-ocupado");
                }
                grid.add(b, columna, fila);
            } else {
                Label l = new Label(a.getCodigo());
                l.setPrefWidth(56);
                l.setAlignment(Pos.CENTER);
                l.setPadding(new Insets(6));
                l.getStyleClass().add(a.estaDisponible() ? "asiento-libre" : "asiento-ocupado");
                grid.add(l, columna, fila);
            }
        }
        return grupoAsientos;
    }

    public static GridPane nuevoMapa() {
        GridPane mapa = new GridPane();
        mapa.setHgap(5);
        mapa.setVgap(5);
        mapa.setAlignment(Pos.CENTER);
        mapa.setPadding(new Insets(10));
        return mapa;
    }

    public static Node envolverMapa(GridPane mapa) {
        Label pantalla = new Label("P A N T A L L A");
        pantalla.setMaxWidth(Double.MAX_VALUE);
        pantalla.setAlignment(Pos.CENTER);
        pantalla.getStyleClass().add("pantalla");
        ScrollPane scroll = new ScrollPane(mapa);
        scroll.setFitToWidth(true);
        scroll.setPrefHeight(300);
        Label leyenda = new Label("Verde = disponible    Rojo = ocupado    Dorado = tu seleccion");
        leyenda.getStyleClass().add("leyenda");
        return new VBox(6, pantalla, scroll, leyenda);
    }
}