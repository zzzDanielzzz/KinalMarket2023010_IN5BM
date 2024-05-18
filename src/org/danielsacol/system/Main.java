/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package org.danielsacol.system;

import java.io.InputStream;

import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import org.danielsacol.controller.ClienteVistaController;
import org.danielsacol.controller.MenuPrincipalController;
import org.danielsacol.controller.ProgramadorController;

import javafx.application.Application;

import javafx.stage.Stage;

import javafx.scene.Scene;
import org.danielsacol.controller.CargoEmpleadoController;
import org.danielsacol.controller.ComprasController;
import org.danielsacol.controller.EmailProveedorController;
import org.danielsacol.controller.EmpleadosController;
import org.danielsacol.controller.ProductosController;
import org.danielsacol.controller.ProveedorController;
import org.danielsacol.controller.TelefonoProveedorController;
import org.danielsacol.controller.TipoProductoController;

/**
 * Nombre: Daniel Eduardo Sacol Cojón Carné: 2023010 Codigo Tecnico: IN5BM Fecha
 * de creacion: 16/04/2024 Fecha de modificaciones: 17/04/2024, 23/04/2024,
 * 24/04/2024, 25/04/2024, 07/05/2024, 09/05/2024
 *
 */
public class Main extends Application {

    private Stage escenarioPrincipal;
    private Scene escena;
    private final String URLVIEW = "/org/danielsacol/view/";

    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Kinal Market");
        escenarioPrincipal.getIcons().add(new Image(Main.class.getResourceAsStream("/org/danielsacol/images/LogoKinalMarket.png")));

        menuPrincipalView();

        escenarioPrincipal.show();
        //FXMLLoader.load(getClass().getResource("/org/danielsacol/view/MenuPrincipalView.fxml"));

    }

    public Initializable cambiarEscena(String fxmlname, int width, int height) throws Exception {
        Initializable resultado;
        FXMLLoader loader = new FXMLLoader();

        InputStream file = Main.class.getResourceAsStream(URLVIEW + fxmlname);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(URLVIEW + fxmlname));

        escena = new Scene((AnchorPane) loader.load(file), width, height);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();

        resultado = (Initializable) loader.getController();

        return resultado;
    }

    public void menuPrincipalView() {
        try {
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 800, 500);
            menuPrincipalView.setEscenarioPrincipal(this);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void menuClienteView() {
        try {
            ClienteVistaController menuClienteView = (ClienteVistaController) cambiarEscena("ClienteVista.fxml", 944, 556);
            menuClienteView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void menuProgramadorView() {
        try {
            ProgramadorController programadorView = (ProgramadorController) cambiarEscena("ProgramadorView.fxml", 912, 513);
            programadorView.setEscenarioPrincipal(this);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void menuProveedorView() {
        try {
            ProveedorController proveedorView = (ProveedorController) cambiarEscena("ProveedorView.fxml", 1035, 606);
            proveedorView.setEscenarioPrincipal(this);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void menuComprasView() {
        try {
            ComprasController comprasView = (ComprasController) cambiarEscena("ComprasView.fxml", 944, 556);
            comprasView.setEscenarioPrincipal(this);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void menuTipoProductoView() {
        try {
            TipoProductoController tipoProductoView = (TipoProductoController) cambiarEscena("TipoDeProductoView.fxml", 944, 556);
            tipoProductoView.setEscenarioPrincipal(this);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void menuCargoEmpleadoView() {
        try {
            CargoEmpleadoController cargoEmpleadoView = (CargoEmpleadoController) cambiarEscena("CargoEmpleadoView.fxml", 944, 556);
            cargoEmpleadoView.setEscenarioPrincipal(this);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void menuProductosView() {
        try {
            ProductosController productosView = (ProductosController) cambiarEscena("ProductosView.fxml", 1139, 629);
            productosView.setEscenarioPrincipal(this);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void menuTelefonoProveedorView() {
        try {
            TelefonoProveedorController telefonoProveedorView = (TelefonoProveedorController) cambiarEscena("TelefonoProveedorView.fxml", 944, 556);
            telefonoProveedorView.setEscenarioPrincipal(this);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void menuEmailProveedorView() {
        try {
            EmailProveedorController emailProveedorView = (EmailProveedorController) cambiarEscena("EmailProveedorView.fxml", 912, 574);
            emailProveedorView.setEscenarioPrincipal(this);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void menuEmpleadosView() {
        try {
            EmpleadosController empleadosView = (EmpleadosController) cambiarEscena("EmpleadosView.fxml", 1000, 575);
            empleadosView.setEscenarioPrincipal(this);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
