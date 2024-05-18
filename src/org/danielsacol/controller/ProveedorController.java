package org.danielsacol.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.danielsacol.bean.Proveedores;
import org.danielsacol.db.Conexion;
import org.danielsacol.system.Main;

/**
 *
 * @author compu
 */
public class ProveedorController implements Initializable {

    private Main escenarioPrincipal;
    private ObservableList<Proveedores> listaProveedores;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    @FXML
    private Button btnTelProv;
    @FXML
    private Button btnEmailProv;

    @FXML
    private Button btnAgregarProveedor;

    @FXML
    private ImageView imgAgregarProveedor;

    @FXML
    private Button btnEliminarProveedor;

    @FXML
    private ImageView imgEliminarProveedor;

    @FXML
    private Button btnEditarProveedor;

    @FXML
    private ImageView imgEditarProveedor;

    @FXML
    private Button btnReporteProveedor;

    @FXML
    private ImageView imgReporteProveedor;

    @FXML
    private TableView tblProveedores;

    @FXML
    private TableColumn colCodigoProveedor;

    @FXML
    private TableColumn colNitProveedor;

    @FXML
    private TableColumn colNombresProveedor;

    @FXML
    private TableColumn colApellidosProveedor;

    @FXML
    private TableColumn colDireccionProveedor;

    @FXML
    private TableColumn colRazonSocialProveedor;

    @FXML
    private TableColumn colContactoProveedor;

    @FXML
    private TableColumn colPaginaProveedor;

    @FXML
    private TextField txtCodigoProveedor;

    @FXML
    private TextField txtNITProveedor;

    @FXML
    private TextField txtNombresProveedor;

    @FXML
    private TextField txtApellidosProveedor;

    @FXML
    private TextField txtDireccionProveedor;

    @FXML
    private TextField txtRazonSocialProveedor;

    @FXML
    private TextField txtContactoProveedor;

    @FXML
    private TextField txtPaginaWeb;

    @FXML
    private Button btnHomeProveedor;

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void cargarDatos() {
        tblProveedores.setItems(getProveedores());
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("codigoProveedor"));
        colNitProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("NITProveedor"));
        colNombresProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nombresProveedor"));
        colApellidosProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("apellidosProveedor"));
        colDireccionProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("direccionProveedor"));
        colRazonSocialProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("razonSocial"));
        colContactoProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("contactoPrincipal"));
        colPaginaProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("paginaWeb"));
    }

    public void seleccionarElementos() {
        txtCodigoProveedor.setText(String.valueOf(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        txtNITProveedor.setText((((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getNITProveedor()));
        txtNombresProveedor.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getNombresProveedor());
        txtApellidosProveedor.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getApellidosProveedor());
        txtDireccionProveedor.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getDireccionProveedor());
        txtRazonSocialProveedor.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getRazonSocial());
        txtContactoProveedor.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getContactoPrincipal());
        txtPaginaWeb.setText(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getPaginaWeb());
    }

    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarProveedor()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Proveedores(resultado.getInt("codigoProveedor"),
                        resultado.getString("NITProveedor"),
                        resultado.getString("nombresProveedor"),
                        resultado.getString("apellidosProveedor"),
                        resultado.getString("direccionProveedor"),
                        resultado.getString("razonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProveedores = FXCollections.observableList(lista);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public void agregarProveedor() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregarProveedor.setText("Guardar");
                btnEliminarProveedor.setText("Cancelar");
                btnEditarProveedor.setDisable(true);
                btnReporteProveedor.setDisable(true);
                imgAgregarProveedor.setImage(new Image("/org/danielsacol/images/IconoGuadar.png"));
                imgEliminarProveedor.setImage(new Image("/org/danielsacol/images/IconoCancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregarProveedor.setText("Agregar");
                btnEliminarProveedor.setText("Eliminar");
                btnEditarProveedor.setDisable(false);
                btnReporteProveedor.setDisable(false);
                imgAgregarProveedor.setImage(new Image("/org/danielsacol/images/IconoAgregar.png"));
                imgEliminarProveedor.setImage(new Image("/org/danielsacol/images/IconoEliminar.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void guardar() {
        Proveedores registro = new Proveedores();
        registro.setCodigoProveedor(Integer.parseInt(txtCodigoProveedor.getText()));
        registro.setNITProveedor(txtNITProveedor.getText());
        registro.setNombresProveedor(txtNombresProveedor.getText());
        registro.setApellidosProveedor(txtApellidosProveedor.getText());
        registro.setDireccionProveedor(txtDireccionProveedor.getText());
        registro.setRazonSocial(txtRazonSocialProveedor.getText());
        registro.setContactoPrincipal(txtContactoProveedor.getText());
        registro.setPaginaWeb(txtPaginaWeb.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarProveedor(?, ?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITProveedor());
            procedimiento.setString(3, registro.getNombresProveedor());
            procedimiento.setString(4, registro.getApellidosProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
            procedimiento.execute();
            listaProveedores.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregarProveedor.setText("Agregar");
                btnEliminarProveedor.setText("Eliminar");
                btnEditarProveedor.setDisable(false);
                btnReporteProveedor.setDisable(false);
                imgAgregarProveedor.setImage(new Image("/org/danielsacol/images/IconoAgregar.png"));
                imgEliminarProveedor.setImage(new Image("/org/danielsacol/images/IconoEliminar.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;

            default:
                if (tblProveedores.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro", "Eliminar clientes",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarProveedor(?)}");
                            procedimiento.setInt(1, ((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                            procedimiento.execute();
                            listaProveedores.remove(tblProveedores.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();

                        }

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un proveedor para eliminar");
                }
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblProveedores.getSelectionModel().getSelectedItem() != null) {
                    btnEditarProveedor.setText("Actualizar");
                    btnReporteProveedor.setText("Cancelar");
                    btnAgregarProveedor.setDisable(true);
                    btnEliminarProveedor.setDisable(true);
                    imgEditarProveedor.setImage(new Image("/org/danielsacol/images/IconoActualizar.png"));
                    imgReporteProveedor.setImage(new Image("/org/danielsacol/images/IconoCancelar.png"));
                    activarControles();
                    txtCodigoProveedor.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de selecionar un proveedor para editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditarProveedor.setText("Editar");
                btnReporteProveedor.setText("Reporte");
                btnAgregarProveedor.setDisable(false);
                btnEliminarProveedor.setDisable(false);
                imgEditarProveedor.setImage(new Image("/org/danielsacol/images/IconoEditar.png"));
                imgReporteProveedor.setImage(new Image("/org/danielsacol/images/IconoReportes.png"));
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarProveedor(?, ?, ?, ?, ?, ?, ?, ?)}");
            Proveedores registro = (Proveedores) tblProveedores.getSelectionModel().getSelectedItem();

            registro.setNITProveedor(txtNITProveedor.getText());
            registro.setNombresProveedor(txtNombresProveedor.getText());
            registro.setApellidosProveedor(txtApellidosProveedor.getText());
            registro.setDireccionProveedor(txtDireccionProveedor.getText());
            registro.setRazonSocial(txtRazonSocialProveedor.getText());
            registro.setContactoPrincipal(txtContactoProveedor.getText());
            registro.setPaginaWeb(txtPaginaWeb.getText());
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITProveedor());
            procedimiento.setString(3, registro.getNombresProveedor());
            procedimiento.setString(4, registro.getApellidosProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
            procedimiento.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reporte() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                btnReporteProveedor.setText("Reporte");
                btnEditarProveedor.setText("Editar");
                imgEditarProveedor.setImage(new Image("/org/danielsacol/images/IconoEditar.png"));
                imgReporteProveedor.setImage(new Image("/org/danielsacol/images/IconoReportes.png"));
                btnAgregarProveedor.setDisable(false);
                btnEliminarProveedor.setDisable(false);
                btnHomeProveedor.setDisable(false);
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
            case NINGUNO:
                break;
        }
    }

    public void desactivarControles() {
        txtCodigoProveedor.setEditable(false);
        txtNITProveedor.setEditable(false);
        txtNombresProveedor.setEditable(false);
        txtApellidosProveedor.setEditable(false);
        txtDireccionProveedor.setEditable(false);
        txtRazonSocialProveedor.setEditable(false);
        txtContactoProveedor.setEditable(false);
        txtPaginaWeb.setEditable(false);

    }

    public void activarControles() {
        txtCodigoProveedor.setEditable(true);
        txtNITProveedor.setEditable(true);
        txtNombresProveedor.setEditable(true);
        txtApellidosProveedor.setEditable(true);
        txtDireccionProveedor.setEditable(true);
        txtRazonSocialProveedor.setEditable(true);
        txtContactoProveedor.setEditable(true);
        txtPaginaWeb.setEditable(true);
    }

    public void limpiarControles() {
        txtCodigoProveedor.clear();
        txtNITProveedor.clear();
        txtNombresProveedor.clear();
        txtApellidosProveedor.clear();
        txtDireccionProveedor.clear();
        txtRazonSocialProveedor.clear();
        txtContactoProveedor.clear();
        txtPaginaWeb.clear();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnHomeProveedor) {
            escenarioPrincipal.menuPrincipalView();
        } else if (event.getSource() == btnTelProv) {
            escenarioPrincipal.menuTelefonoProveedorView();
        } else if (event.getSource() == btnEmailProv) {
            escenarioPrincipal.menuEmailProveedorView();
        }
    }

}
