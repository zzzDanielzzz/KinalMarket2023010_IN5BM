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
import org.danielsacol.bean.Compras;
import org.danielsacol.bean.DetalleCompra;
import org.danielsacol.bean.Productos;
import org.danielsacol.db.Conexion;
import org.danielsacol.system.Main;

/**
 *
 * @author compu
 */
public class DetalleCompraController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<DetalleCompra> listaDetalleCompras;
    private ObservableList<Compras> listaCompras;
    private ObservableList<Productos> listaProductos;
    @FXML
    private Button btnHomeDetalleCompra;
   @FXML
    private Button btnCompras;
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
    private TableView tblDetalleCompra;

    @FXML
    private TableColumn colCodDetCompra;

    @FXML
    private TableColumn colCostoUnit;

    @FXML
    private TableColumn colCantidad;

    @FXML
    private TableColumn colCodProd;

    @FXML
    private TableColumn colNumDoc;

    @FXML
    private TextField txtCodDetCompra;

    @FXML
    private TextField txtCostoUnit;

    @FXML
    private TextField txtCantidad;

    @FXML
    private ComboBox cmbCodProd;

    @FXML
    private ComboBox cmbNumDoc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodProd.setItems(getProductos());
        cmbNumDoc.setItems(getCompras());
    }

    public void cargarDatos() {
        tblDetalleCompra.setItems(getDetalleCompra());
        colCodDetCompra.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("codigoDetalleCompra"));
        colCostoUnit.setCellValueFactory(new PropertyValueFactory<DetalleCompra, String>("costoUnitario"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Double>("cantidad"));
        colCodProd.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("codigoProducto"));
        colNumDoc.setCellValueFactory(new PropertyValueFactory<DetalleCompra, String>("numeroDocumento"));
    }

    public void seleccionarElementos() {
        txtCodDetCompra.setText(String.valueOf(((DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra()));
        txtCostoUnit.setText(String.valueOf(((DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem()).getCostoUnitario()));
        txtCantidad.setText(String.valueOf(((DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem()).getCantidad()));
        cmbNumDoc.getSelectionModel().select(buscarCompra(((DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
        cmbCodProd.getSelectionModel().select(buscarProducto(((DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem()).getCodigoProducto()));

    }

    public Compras buscarCompra(int numeroDocumento) {
        Compras resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarCompra(?)}");
            procedimiento.setInt(1, numeroDocumento);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Compras(registro.getInt("numeroDocumento"),
                        registro.getString("fechaDocumento"),
                        registro.getString("descripcion"),
                        registro.getDouble("totalDocumento"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public Productos buscarProducto(String codigoProducto) {
        Productos resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarProductoPorCodigo(?)}");
            procedimiento.setString(1, codigoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Productos(registro.getString("codigoProducto"),
                        registro.getString("descripcionProducto"),
                        registro.getDouble("precioUnitario"),
                        registro.getDouble("precioDocena"),
                        registro.getDouble("precioMayor"),
                        registro.getString("imagenProducto"),
                        registro.getInt("existencia"),
                        registro.getInt("codigoProveedor"),
                        registro.getInt("codigoTipoProducto"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public ObservableList<DetalleCompra> getDetalleCompra() {
        ArrayList<DetalleCompra> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarDetalleCompras()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new DetalleCompra(resultado.getInt("codigoDetalleCompra"),
                        resultado.getDouble("costoUnitario"),
                        resultado.getInt("cantidad"),
                        resultado.getString("codigoProducto"),
                        resultado.getInt("numeroDocumento")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaDetalleCompras = FXCollections.observableList(lista);
    }

    public ObservableList<Compras> getCompras() {
        ArrayList<Compras> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarCompras()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Compras(resultado.getInt("numeroDocumento"),
                        resultado.getString("fechaDocumento"),
                        resultado.getString("descripcion"),
                        resultado.getDouble("totalDocumento")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCompras = FXCollections.observableList(lista);
    }

    public ObservableList<Productos> getProductos() {
        ArrayList<Productos> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarProductos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Productos(resultado.getString("codigoProducto"),
                        resultado.getString("descripcionProducto"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getDouble("precioDocena"),
                        resultado.getDouble("precioMayor"),
                        resultado.getString("imagenProducto"),
                        resultado.getInt("existencia"),
                        resultado.getInt("codigoTipoProducto"),
                        resultado.getInt("codigoProveedor")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProductos = FXCollections.observableList(lista);
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
                tipoDeOperaciones = DetalleCompraController.operaciones.ACTUALIZAR;
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
                tipoDeOperaciones = DetalleCompraController.operaciones.NINGUNO;
                cargarDatos();
                break;
        }

    }

    public void guardar() {
        DetalleCompra registro = new DetalleCompra();
        registro.setCodigoDetalleCompra(Integer.parseInt(txtCodDetCompra.getText()));
        registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
        registro.setCostoUnitario(Double.parseDouble(txtCostoUnit.getText()));
        registro.setCodigoProducto((((Productos) cmbCodProd.getSelectionModel().getSelectedItem()).getCodigoProducto()));
        registro.setNumeroDocumento((((Compras) cmbNumDoc.getSelectionModel().getSelectedItem()).getNumeroDocumento()));

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarDetalleCompra(?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoDetalleCompra());
            procedimiento.setDouble(2, registro.getCostoUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setString(4, registro.getCodigoProducto());
            procedimiento.setInt(5, registro.getNumeroDocumento());
            procedimiento.execute();

            listaDetalleCompras.add(registro);
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
                if (tblDetalleCompra.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro", "Eliminar registro",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarDetalleCompra(?)}");
                            procedimiento.setInt(1, ((DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra());
                            procedimiento.execute();
                            listaDetalleCompras.remove(tblDetalleCompra.getSelectionModel().getSelectedItem());
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
                if (tblDetalleCompra.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/danielsacol/images/IconoActualizar.png"));
                    imgReportes.setImage(new Image("/org/danielsacol/images/IconoCancelar.png"));
                    activarControles();
                    txtCodDetCompra.setEditable(false);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarDetalleCompra(?,?,?,?,?)}");
            DetalleCompra registro = (DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem();

            registro.setCodigoDetalleCompra(Integer.parseInt(txtCodDetCompra.getText()));
            registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
            registro.setCostoUnitario(Double.parseDouble(txtCostoUnit.getText()));
            registro.setCodigoProducto((((Productos) cmbCodProd.getSelectionModel().getSelectedItem()).getCodigoProducto()));
            registro.setNumeroDocumento((((Compras) cmbNumDoc.getSelectionModel().getSelectedItem()).getNumeroDocumento()));

            procedimiento.setInt(1, registro.getCodigoDetalleCompra());
            procedimiento.setDouble(2, registro.getCostoUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setString(4, registro.getCodigoProducto());
            procedimiento.setInt(5, registro.getNumeroDocumento());

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
                tipoDeOperaciones = DetalleCompraController.operaciones.NINGUNO;
                cargarDatos();
            case NINGUNO:
                break;
        }
    }

    public void desactivarControles() {
        txtCodDetCompra.setDisable(true);
        txtCostoUnit.setDisable(true);
        txtCantidad.setDisable(true);
        cmbCodProd.setDisable(true);
        cmbNumDoc.setDisable(true);

    }

    public void activarControles() {
        txtCodDetCompra.setDisable(false);
        txtCostoUnit.setDisable(false);
        txtCantidad.setDisable(false);
        cmbCodProd.setDisable(false);
        cmbNumDoc.setDisable(false);

    }

    public void limpiarControles() {
        txtCodDetCompra.clear();
        txtCostoUnit.clear();
        txtCantidad.clear();
        cmbCodProd.getSelectionModel().getSelectedItem();
        cmbNumDoc.getSelectionModel().getSelectedItem();
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnHomeDetalleCompra) {
            escenarioPrincipal.menuPrincipalView();
        } else if(event.getSource() == btnCompras){
            escenarioPrincipal.menuComprasView();
        }
    }
}
