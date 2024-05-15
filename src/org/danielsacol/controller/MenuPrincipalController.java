package org.danielsacol.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import org.danielsacol.system.Main;

public class MenuPrincipalController implements Initializable {

    private Main escenarioPrincipal;
    @FXML
    MenuItem btnMenuClientes;
    @FXML
    MenuItem btnProgramador;
    @FXML
    MenuItem btnMenuProveedores;
    @FXML
    MenuItem btnMenuCompras;
    @FXML
    MenuItem btnMenuTipoProducto;
    @FXML
    MenuItem btnMenuCargoEmpleado;
    @FXML
    MenuItem btnMenuProductos;
    @FXML
    MenuItem btnMenuTelProveedor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;

    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnMenuClientes) {
            escenarioPrincipal.menuClienteView();

        } else if (event.getSource() == btnProgramador) {
            escenarioPrincipal.menuProgramadorView();
        } else if (event.getSource() == btnMenuProveedores) {
            escenarioPrincipal.menuProveedorView();
        } else if (event.getSource() == btnMenuCompras) {
            escenarioPrincipal.menuComprasView();
        } else if (event.getSource() == btnMenuTipoProducto) {
            escenarioPrincipal.menuTipoProductoView();
        } else if (event.getSource() == btnMenuCargoEmpleado) {
            escenarioPrincipal.menuCargoEmpleadoView();
        } else if (event.getSource() == btnMenuProductos) {
            escenarioPrincipal.menuProductosView();
        } else if (event.getSource() == btnMenuTelProveedor) {
            escenarioPrincipal.menuTelefonoProveedorView();
        }
    }
}
