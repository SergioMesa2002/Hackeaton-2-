package models;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class App3 extends Application {

    private BorderPane root; // layout principal
    List<Contact> contactos;
    Agenda agenda = new Agenda(10);// simulación de datos

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Agenda de Contactos");

        root = new BorderPane();

        // --- Simulamos contactos ---
//        contactos = new ArrayList<>();
        agenda.addContact(new Contact("Juan","gomez","313431254"));
        agenda.addContact(new Contact("Juanito","gomez","313431254"));
        agenda.addContact(new Contact("Juancho","gomez","313431254"));

        // --- Menú lateral ---
        VBox menu = new VBox(10);
        Button btnListar = new Button("Listar Contactos");
        Button btnBuscar = new Button("Buscar Contacto");
        Button btnEliminar = new Button("Eliminar Contacto");
        Button btnModificar = new Button("Modificar Contacto");
        Button btnCupos = new Button("Cupos disponibles");
        Button btnSalir = new Button("Salir");

        menu.getChildren().addAll(btnListar, btnBuscar, btnEliminar, btnModificar, btnCupos, btnSalir);

        root.setLeft(menu);
        root.setCenter(new Label("Bienvenido a tu agenda 📒"));

        // --- Acciones ---
        btnListar.setOnAction(e -> mostrarLista());
        btnBuscar.setOnAction(e -> mostrarFormularioBuscar());
        btnSalir.setOnAction(e -> stage.close());

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    // --- Listar contactos ---
    private void mostrarLista() {
        ListView<Contact> listView = new ListView<>();
        listView.getItems().addAll(agenda.listarContactos());
        root.setCenter(listView);
    }

    private void buscarContacto(){

    }

    // --- Formulario buscar contacto ---
    private void mostrarFormularioBuscar() {
        VBox formulario = new VBox(10);

        Label lblNombre = new Label("Nombre:");
        TextField txtNombre = new TextField();

        Label lblApellido = new Label("Apellido:");
        TextField txtApellido = new TextField();

        Button btnBuscar = new Button("Buscar");

        Label resultado = new Label();

        btnBuscar.setOnAction(e -> {

            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String nombreCompleto = nombre + " " + apellido;

            if (agenda.buscarContacto(nombre,apellido).isPresent()) {
                resultado.setText("✅ Contacto encontrado: " + nombreCompleto);
            } else {
                resultado.setText("❌ No se encontró el contacto.");
            }
        });

        formulario.getChildren().addAll(lblNombre, txtNombre, lblApellido, txtApellido, btnBuscar, resultado);
        root.setCenter(formulario);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
