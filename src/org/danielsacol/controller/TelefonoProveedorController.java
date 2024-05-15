package org.danielsacol.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.danielsacol.bean.Proveedores;
import org.danielsacol.bean.TelefonoProveedor;
import org.danielsacol.system.Main;

/**
 *
 * @author informatica
 */
public class TelefonoProveedorController implements Initializable {

    private Main escenarioPrincipal;
        private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<TelefonoProveedor> listaTelefonosProveedor;
    private ObservableList<Proveedores> listaProveedores;
 
    @FXML
    private Button btnHomeTelProv;

        @FXML
    private TableView tblTelProv;

    @FXML
    private TableColumn colCodTelProv;

    @FXML
    private TableColumn colNumPrincipal;

    @FXML
    private TableColumn colNumSecundario;

    @FXML
    private TableColumn colObservaciones;

    @FXML
    private TableColumn colCodProv;

    @FXML
    private Button btnAgregar;

    @FXML
    private ImageView imgAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private Button btnEditar;

    @FXML
    private ImageView imgEditar;

    @FXML
    private Button btnReportes;

    @FXML
    private ImageView imgReportes;

    @FXML
    private TextField txtCodTelProv;

    @FXML
    private TextField txtNumPrincipal;

    @FXML
    private TextField txtNumSecundario;

    @FXML
    private TextField txtObservaciones;

    @FXML
    private ComboBox cmbCodProv;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
       public void cargarDatos() {
        tblTelProv.setItems(getTelefonoProveedor());
        colCodTelProv.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("codigoTelefonoProveedor"));
        colNumPrincipal.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("numeroPrincipal"));
        colNumSecundario.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("numeroSecundario"));
        colObservaciones.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("observaciones"));
        colCodProv.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("codigoProveedor"));
    }

        public void desactivarControles() {
        txtCodTelProv.setDisable(true);
        txtNumPrincipal.setDisable(true);
        txtNumSecundario.setDisable(true);
        txtObservaciones.setDisable(true);
        cmbCodProv.setDisable(true);
    }

    public void activarControles() {
        txtCodTelProv.setDisable(false);
        txtNumPrincipal.setDisable(false);
        txtNumSecundario.setDisable(false);
        txtObservaciones.setDisable(false);
        cmbCodProv.setDisable(false);
    }

    public void limpiarControles() {
        txtCodTelProv.clear();
        txtNumPrincipal.clear();
        txtNumSecundario.clear();
        txtObservaciones.clear();
        cmbCodProv.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnHomeTelProv) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
