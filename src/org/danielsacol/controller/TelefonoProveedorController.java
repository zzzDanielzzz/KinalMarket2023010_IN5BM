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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.danielsacol.bean.Proveedores;
import org.danielsacol.bean.TelefonoProveedor;
import org.danielsacol.db.Conexion;
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
    private Button btnProveedores;

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
        cargarDatos();
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

    public void seleccionarElementos() {

        txtCodTelProv.setText(String.valueOf(((TelefonoProveedor) tblTelProv.getSelectionModel().getSelectedItem()).getCodigoTelefonoProveedor()));
        txtNumPrincipal.setText((((TelefonoProveedor) tblTelProv.getSelectionModel().getSelectedItem()).getNumeroPrincipal()));
        txtNumSecundario.setText((((TelefonoProveedor) tblTelProv.getSelectionModel().getSelectedItem()).getNumeroSecundario()));
        txtObservaciones.setText((((TelefonoProveedor) tblTelProv.getSelectionModel().getSelectedItem()).getObservaciones()));
        cmbCodProv.getSelectionModel().select(buscarProveedor(((TelefonoProveedor) tblTelProv.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
    }

    public Proveedores buscarProveedor(int codigoProveedor) {
        Proveedores resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarProveedor(?)}");
            procedimiento.setInt(1, codigoProveedor);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Proveedores(registro.getInt("codigoProveedor"),
                        registro.getString("NITProveedor"),
                        registro.getString("nombresProveedor"),
                        registro.getString("apellidosProveedor"),
                        registro.getString("direccionProveedor"),
                        registro.getString("razonSocial"),
                        registro.getString("contactoPrincipal"),
                        registro.getString("paginaWeb"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public ObservableList<TelefonoProveedor> getTelefonoProveedor() {
        ArrayList<TelefonoProveedor> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarTelefonoProveedor()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new TelefonoProveedor(resultado.getInt("codigoTelefonoProveedor"),
                        resultado.getString("numeroPrincipal"),
                        resultado.getString("numeroSecundario"),
                        resultado.getString("observaciones"),
                        resultado.getInt("codigoProveedor")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaTelefonosProveedor = FXCollections.observableList(lista);
    }

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReportes.setDisable(true);
                imgAgregar.setImage(new Image("/org/danielsacol/images/IconoGuadar.png"));
                imgEliminar.setImage(new Image("/org/danielsacol/images/IconoCancelar.png"));
                tipoDeOperaciones = TelefonoProveedorController.operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                limpiarControles();
                cargarDatos();
                desactivarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("/org/danielsacol/images/IconoAgregar.png"));
                imgEliminar.setImage(new Image("/org/danielsacol/images/IconoEliminar.png"));
                tipoDeOperaciones = TelefonoProveedorController.operaciones.NINGUNO;
                cargarDatos();
                break;
        }

    }

    public void guardar() {
        TelefonoProveedor registro = new TelefonoProveedor();
        registro.setCodigoTelefonoProveedor(Integer.parseInt(txtCodTelProv.getText()));
        registro.setCodigoProveedor(((Proveedores) cmbCodProv.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        registro.setNumeroPrincipal(txtNumPrincipal.getText());
        registro.setNumeroSecundario(txtNumSecundario.getText());
        registro.setObservaciones(txtObservaciones.getText());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarTelefonoProveedor(?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoTelefonoProveedor());
            procedimiento.setString(2, registro.getNumeroPrincipal());
            procedimiento.setString(3, registro.getNumeroSecundario());
            procedimiento.setString(4, registro.getObservaciones());
            procedimiento.setInt(5, registro.getCodigoProveedor());
            procedimiento.execute();

            listaTelefonosProveedor.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("/org/danielsacol/images/IconoAgregar.png"));
                imgEliminar.setImage(new Image("/org/danielsacol/images/IconoEliminar.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;

            default:
                if (tblTelProv.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro", "Eliminar registro",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarTelefonoProveedor(?)}");
                            procedimiento.setInt(1, ((TelefonoProveedor) tblTelProv.getSelectionModel().getSelectedItem()).getCodigoTelefonoProveedor());
                            procedimiento.execute();
                            listaTelefonosProveedor.remove(tblTelProv.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();

                        }

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un registro para eliminar");
                }
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblTelProv.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/danielsacol/images/IconoActualizar.png"));
                    imgReportes.setImage(new Image("/org/danielsacol/images/IconoCancelar.png"));
                    activarControles();
                    txtCodTelProv.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de selecionar un registro para editar");
                }
                break;
            case ACTUALIZAR:
                try {
                actualizar();
            } catch (Exception e) {
                e.printStackTrace();
            }

            btnEditar.setText("Editar");
            btnReportes.setText("Reporte");
            btnAgregar.setDisable(false);
            btnEliminar.setDisable(false);
            imgEditar.setImage(new Image("/org/danielsacol/images/IconoEditar.png"));
            imgReportes.setImage(new Image("/org/danielsacol/images/IconoReportes.png"));
            desactivarControles();
            limpiarControles();
            tipoDeOperaciones = operaciones.NINGUNO;
            cargarDatos();
            break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarTelefonoProveedor(?, ?, ?, ?, ?)}");
            TelefonoProveedor registro = (TelefonoProveedor) tblTelProv.getSelectionModel().getSelectedItem();

            registro.setCodigoProveedor(((Proveedores) cmbCodProv.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            registro.setNumeroPrincipal(txtNumPrincipal.getText());
            registro.setNumeroSecundario(txtNumSecundario.getText());
            registro.setObservaciones(txtObservaciones.getText());

            procedimiento.setInt(1, registro.getCodigoTelefonoProveedor());
            procedimiento.setString(2, registro.getNumeroPrincipal());
            procedimiento.setString(3, registro.getNumeroSecundario());
            procedimiento.setString(4, registro.getObservaciones());
            procedimiento.setInt(5, registro.getCodigoProveedor());

            procedimiento.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reporte() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                btnReportes.setText("Reporte");
                btnEditar.setText("Editar");
                imgEditar.setImage(new Image("/org/danielsacol/images/IconoEditar.png"));
                imgReportes.setImage(new Image("/org/danielsacol/images/IconoReportes.png"));
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = TelefonoProveedorController.operaciones.NINGUNO;
                cargarDatos();
            case NINGUNO:
                break;
        }
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
        } else if(event.getSource() == btnProveedores){
            escenarioPrincipal.menuProveedorView();
        }
    }
}
