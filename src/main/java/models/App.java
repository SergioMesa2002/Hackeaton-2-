package models;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private BorderPane root; // layout principal
    Agenda agenda = new Agenda(10); // simulación de datos

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Agenda de Contactos");

        root = new BorderPane();

        // --- Simulamos contactos ---
        agenda.addContact(new Contact("Juan", "gomez", "313431254"));
        agenda.addContact(new Contact("Juanito", "gomez", "313431254"));
        agenda.addContact(new Contact("Juancho", "gomez", "313431254"));
        agenda.addContact(new Contact("Jua", "gomez", "313431254"));
        agenda.addContact(new Contact("lolo", "gomez", "313431254"));
        agenda.addContact(new Contact("hujhvjdo", "gomez", "313431254"));
        agenda.addContact(new Contact("fhgvdhsu", "gomez", "313431254"));
        agenda.addContact(new Contact("fijbehv", "gomez", "313431254"));
        agenda.addContact(new Contact("jfivudhsbhj", "gomez", "313431254"));
        agenda.addContact(new Contact("hvgdhiuv", "gomez", "313431254"));

        // --- Menú lateral ---
        VBox menu = new VBox(10);
        Button btnListar = new Button("Listar Contactos");
        Button btnAgregar = new Button("Agregar Contacto");
        Button btnEliminar = new Button("Eliminar Contacto");
        Button btnBuscar = new Button("Buscar Contacto");
        Button btnModificar = new Button("Modificar Contacto");
        Button btnCupos = new Button("Cupos disponibles");
        Button btnSalir = new Button("Salir");

        menu.getChildren().addAll(btnListar,btnAgregar, btnBuscar, btnEliminar, btnModificar,btnCupos, btnSalir);

        root.setLeft(menu);
        root.setCenter(new Label("Bienvenido a tu agenda 📒"));

        // --- Acciones ---
        btnListar.setOnAction(e -> mostrarLista());
        btnBuscar.setOnAction(e -> mostrarFormulario("buscar"));
        btnEliminar.setOnAction(e -> mostrarFormulario("eliminar"));
        btnModificar.setOnAction(e -> mostrarFormulario("editar"));
        btnAgregar.setOnAction(e -> mostrarFormulario("agregar"));
        btnCupos.setOnAction(e -> mostrarCupos());
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

    // --- Formulario genérico (buscar / eliminar) ---
    private void mostrarFormulario(String tipo) {
        VBox formulario = new VBox(10);

        Label lblNombre = new Label("Nombre:");
        TextField txtNombre = new TextField();

        Label lblApellido = new Label("Apellido:");
        TextField txtApellido = new TextField();

        Label lblTelefono = new Label("telefono:");
        TextField txtTelefono = new TextField();

        Button btnBuscar = new Button("Buscar");
        Button btnEliminar = new Button("Eliminar");
        Button btnEditar = new Button("Editar");
        Button btnAgregar = new Button("Agregar");


        Label resultado = new Label();

        // Acción Buscar
        btnBuscar.setOnAction(e -> {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String nombreCompleto = nombre + " " + apellido;

            if (agenda.buscarContacto(nombre, apellido).isPresent()) {
                resultado.setText("✅ Contacto encontrado: " + nombreCompleto + " teléfono: " + agenda.buscarContacto(nombre, apellido).get().getPhone());
            } else {
                resultado.setText("❌ No se encontró el contacto.");
            }
        });

        // Acción Eliminar
        btnEliminar.setOnAction(e -> {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();

            if (agenda.buscarContacto(nombre, apellido).isPresent()) {
                agenda.eliminarContacto(nombre, apellido);
                resultado.setText("✅ Contacto eliminado");
            } else {
                resultado.setText("❌ No se encontró el contacto.");
            }
        });

        // Acción editar
        btnEditar.setOnAction(e -> {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String telefono = txtTelefono.getText().trim();

            if (agenda.buscarContacto(nombre, apellido).isPresent()) {
                agenda.modificarTelefono(nombre,apellido,telefono);
                resultado.setText("✅ Contacto editado");
            } else {
                resultado.setText("❌ No se editó el contacto.");
            }
        });

        // Acción agregar
        btnAgregar.setOnAction(e -> {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String telefono = txtTelefono.getText().trim();



            if (agenda.buscarContacto(nombre, apellido).isEmpty()) {
                try {
                    agenda.addContact(new Contact(nombre,apellido,telefono));
                    resultado.setText("✅ Contacto Agregado");
                } catch (Exception ex) {
                    resultado.setText(ex.getMessage());
                }
            } else {
                resultado.setText("El contacto ya existe!!");
            }
        });

        // Mostrar formulario dependiendo del tipo
        if (tipo.equals("buscar")) {
            formulario.getChildren().addAll(lblNombre, txtNombre, lblApellido, txtApellido, btnBuscar, btnEliminar, resultado);
        } else if (tipo.equals("eliminar")) {
            formulario.getChildren().addAll(lblNombre, txtNombre, lblApellido, txtApellido, btnEliminar, resultado);
        } else if (tipo.equals("editar")) {
            formulario.getChildren().addAll(lblNombre, txtNombre, lblApellido, txtApellido,lblTelefono,txtTelefono, btnEditar, resultado);
        } else if (tipo.equals("agregar")) {
            formulario.getChildren().addAll(lblNombre, txtNombre, lblApellido, txtApellido,lblTelefono,txtTelefono, btnAgregar, resultado);
        }


        root.setCenter(formulario);
    }

    private void mostrarCupos(){

        int cupos = agenda.espaciosLibres();
        Label labelCupos = new Label("Cupos disponibles: " + cupos);
        root.setCenter(labelCupos);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
