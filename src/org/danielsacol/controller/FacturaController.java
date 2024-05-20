/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import org.danielsacol.bean.Clientes;
import org.danielsacol.bean.Empleados;
import org.danielsacol.bean.Factura;
import org.danielsacol.db.Conexion;
import org.danielsacol.system.Main;

/**
 *
 * @author compu
 */
public class FacturaController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Factura> listaFacturas;
    private ObservableList<Clientes> listaClientes;
    private ObservableList<Empleados> listaEmpleados;
    @FXML
    private Button btnHomeFactura;
     @FXML
    private Button btnDetFac;
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
    private TableView tblFactura;

    @FXML
    private TableColumn colNumFactura;

    @FXML
    private TableColumn colEstado;

    @FXML
    private TableColumn colTotalFactura;

    @FXML
    private TableColumn colFecha;

    @FXML
    private TableColumn colCodCliente;

    @FXML
    private TableColumn colCodEmpleado;

    @FXML
    private TextField txtNumFactura;

    @FXML
    private TextField txtEstado;

    @FXML
    private TextField txtTotalFactura;

    @FXML
    private TextField txtFecha;

    @FXML
    private ComboBox cmbCodCliente;

    @FXML
    private ComboBox cmbCodEmpleado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodCliente.setItems(getClientes());
        cmbCodEmpleado.setItems(getEmpleados());
    }

    public void cargarDatos() {
        tblFactura.setItems(getFacturas());
        colNumFactura.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("numeroFactura"));
        colEstado.setCellValueFactory(new PropertyValueFactory<Factura, String>("estado"));
        colTotalFactura.setCellValueFactory(new PropertyValueFactory<Factura, Double>("totalFactura"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Factura, String>("fechaFactura"));
        colCodCliente.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("codigoCliente"));
        colCodEmpleado.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("codigoEmpleado"));
    }

    public void seleccionarElementos() {
        txtNumFactura.setText(String.valueOf(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getNumeroFactura()));
        txtTotalFactura.setText(String.valueOf(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getTotalFactura()));
        txtEstado.setText((((Factura) tblFactura.getSelectionModel().getSelectedItem()).getEstado()));
        txtFecha.setText((((Factura) tblFactura.getSelectionModel().getSelectedItem()).getFechaFactura()));
        cmbCodCliente.getSelectionModel().select(buscarCliente(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        cmbCodEmpleado.getSelectionModel().select(buscarEmpleado(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));

    }

    public Clientes buscarCliente(int codigoCliente) {
        Clientes resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarCliente(?)}");
            procedimiento.setInt(1, codigoCliente);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Clientes(registro.getInt("codigoCliente"),
                        registro.getString("NITCliente"),
                        registro.getString("nombreCliente"),
                        registro.getString("apellidosCliente"),
                        registro.getString("direccionCliente"),
                        registro.getString("telefonoCliente"),
                        registro.getString("correoCliente"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public Empleados buscarEmpleado(int codigoEmpleado) {
        Empleados resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarEmpleado(?)}");
            procedimiento.setInt(1, codigoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Empleados(registro.getInt("codigoEmpleado"),
                        registro.getString("nombresEmpleado"),
                        registro.getString("apellidosEmpleado"),
                        registro.getDouble("sueldo"),
                        registro.getString("direccion"),
                        registro.getString("turno"),
                        registro.getInt("codigoCargoEmpleado"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public ObservableList<Factura> getFacturas() {
        ArrayList<Factura> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarFacturas()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Factura(resultado.getInt("numeroFactura"),
                        resultado.getString("estado"),
                        resultado.getDouble("totalFactura"),
                        resultado.getString("fechaFactura"),
                        resultado.getInt("codigoCliente"),
                        resultado.getInt("codigoEmpleado")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaFacturas = FXCollections.observableList(lista);
    }

    public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarCliente()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Clientes(resultado.getInt("codigoCliente"),
                        resultado.getString("NITCliente"),
                        resultado.getString("nombreCliente"),
                        resultado.getString("apellidosCliente"),
                        resultado.getString("direccionCliente"),
                        resultado.getString("telefonoCliente"),
                        resultado.getString("correoCliente")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaClientes = FXCollections.observableList(lista);
    }

    public ObservableList<Empleados> getEmpleados() {
        ArrayList<Empleados> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Empleados(resultado.getInt("codigoEmpleado"),
                        resultado.getString("nombresEmpleado"),
                        resultado.getString("apellidosEmpleado"),
                        resultado.getDouble("sueldo"),
                        resultado.getString("direccion"),
                        resultado.getString("turno"),
                        resultado.getInt("codigoCargoEmpleado")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaEmpleados = FXCollections.observableList(lista);
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
                tipoDeOperaciones = FacturaController.operaciones.ACTUALIZAR;
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
                tipoDeOperaciones = FacturaController.operaciones.NINGUNO;
                cargarDatos();
                break;
        }

    }

    public void guardar() {
        Factura registro = new Factura();
        registro.setNumeroFactura(Integer.parseInt(txtNumFactura.getText()));
        registro.setCodigoCliente(((Clientes) cmbCodCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
        registro.setCodigoEmpleado(((Empleados) cmbCodEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
        registro.setEstado(txtEstado.getText());
        registro.setTotalFactura(Double.parseDouble(txtTotalFactura.getText()));
        registro.setFechaFactura(txtFecha.getText());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{Call sp_agregarFactura(?,?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setDouble(3, registro.getTotalFactura());
            procedimiento.setString(4, registro.getFechaFactura());
            procedimiento.setInt(5, registro.getCodigoCliente());
            procedimiento.setInt(6, registro.getCodigoEmpleado());
            procedimiento.execute();

            listaFacturas.add(registro);
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
                if (tblFactura.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro", "Eliminar registro",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarFactura(?)}");
                            procedimiento.setInt(1, ((Factura) tblFactura.getSelectionModel().getSelectedItem()).getNumeroFactura());
                            procedimiento.execute();
                            listaFacturas.remove(tblFactura.getSelectionModel().getSelectedItem());
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
                if (tblFactura.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/danielsacol/images/IconoActualizar.png"));
                    imgReportes.setImage(new Image("/org/danielsacol/images/IconoCancelar.png"));
                    activarControles();
                    txtNumFactura.setEditable(false);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarFactura(?, ?, ?, ?, ?, ?)}");
            Factura registro = (Factura) tblFactura.getSelectionModel().getSelectedItem();

            registro.setCodigoCliente(((Clientes) cmbCodCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
            registro.setCodigoEmpleado(((Empleados) cmbCodEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
            registro.setEstado(txtEstado.getText());
            registro.setTotalFactura(Double.parseDouble(txtTotalFactura.getText()));
            registro.setFechaFactura(txtFecha.getText());

            procedimiento.setInt(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setDouble(3, registro.getTotalFactura());
            procedimiento.setString(4, registro.getFechaFactura());
            procedimiento.setInt(5, registro.getCodigoCliente());
            procedimiento.setInt(6, registro.getCodigoEmpleado());

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
                tipoDeOperaciones = FacturaController.operaciones.NINGUNO;
                cargarDatos();
            case NINGUNO:
                break;
        }
    }

    public void desactivarControles() {
        txtNumFactura.setDisable(true);
        txtEstado.setDisable(true);
        txtTotalFactura.setDisable(true);
        txtFecha.setDisable(true);
        cmbCodCliente.setDisable(true);
        cmbCodEmpleado.setDisable(true);

    }

    public void activarControles() {
        txtNumFactura.setDisable(false);
        txtEstado.setDisable(false);
        txtTotalFactura.setDisable(false);
        txtFecha.setDisable(false);
        cmbCodCliente.setDisable(false);
        cmbCodEmpleado.setDisable(false);

    }

    public void limpiarControles() {
        txtNumFactura.clear();
        txtEstado.clear();
        txtTotalFactura.clear();
        txtFecha.clear();
        cmbCodCliente.getSelectionModel().getSelectedItem();
        cmbCodEmpleado.getSelectionModel().getSelectedItem();
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnHomeFactura) {
            escenarioPrincipal.menuPrincipalView();
        } else if(event.getSource() == btnDetFac){
        escenarioPrincipal.menuDetalleFacturaView();
        }
    }
}
