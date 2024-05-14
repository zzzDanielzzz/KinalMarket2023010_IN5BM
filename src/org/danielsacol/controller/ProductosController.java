/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.danielsacol.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import org.danielsacol.bean.Productos;
import org.danielsacol.bean.Proveedores;
import org.danielsacol.bean.TipoProducto;
import org.danielsacol.db.Conexion;
import org.danielsacol.system.Main;

/**
 *
 * @author informatica
 */
public class ProductosController implements Initializable{

    private Main escenarioPrincipal;
    
     private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
     private operaciones tipoDeOperaciones = operaciones.NINGUNO;
     private ObservableList<Productos> listaProductos;
     private ObservableList<Proveedores> listaProveedores;
     private ObservableList<TipoProducto> listaTipoProducto;
         public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
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
    private TableView tblProductos;

    @FXML
    private TableColumn colCodProd;

    @FXML
    private TableColumn colDescProd;

    @FXML
    private TableColumn colPrecUnit;

    @FXML
    private TableColumn colPrecDoc;

    @FXML
    private TableColumn colPrecMay;

    @FXML
    private TableColumn colImgProd;

    @FXML
    private TableColumn colExist;

    @FXML
    private TableColumn colCodTipoProd;

    @FXML
    private TableColumn colCodProv;

    @FXML
    private TextField txtCodProd;

    @FXML
    private TextField txtDescProd;

    @FXML
    private TextField txtPrecUnit;

    @FXML
    private TextField txtPrecDoc;

    @FXML
    private TextField txtPrecMay;

    @FXML
    private TextField txtImgProd;

    @FXML
    private TextField txtExist;

    @FXML
    private ComboBox combCodProducto;

    @FXML
    private ComboBox combCodProveedor;

    @FXML
    private Button btnHomeProductos;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        combCodProveedor.setItems(getProveedores());
        combCodProducto.setItems(getTipoProducto());
    }
    
    public void desactivarControles(){
        txtCodProd.setDisable(false);
        txtDescProd.setDisable(false);
        txtPrecUnit.setDisable(false);
        txtPrecDoc.setDisable(false);
        txtPrecMay.setDisable(false);
        txtImgProd.setDisable(false);
        txtExist.setDisable(false);
        combCodProducto.setDisable(false);
        combCodProveedor.setDisable(false);
    }
    
        public void activarControles(){
        txtCodProd.setDisable(true);
        txtDescProd.setDisable(true);
        txtPrecUnit.setDisable(true);
        txtPrecDoc.setDisable(true);
        txtPrecMay.setDisable(true);
        txtImgProd.setDisable(true);
        txtExist.setDisable(true);
        combCodProducto.setDisable(true);
        combCodProveedor.setDisable(true);
    }
    
    public void limpiarControles(){
        txtCodProd.clear();
        txtDescProd.clear();
        txtPrecUnit.clear();
        txtPrecDoc.clear();
        txtPrecMay.clear();
        txtImgProd.clear();
        txtExist.clear();
        combCodProducto.getSelectionModel().getSelectedItem();
        combCodProveedor.getSelectionModel().getSelectedItem();
    }
    
    public void cargarDatos(){
        tblProductos.setItems(getProductos());
        colCodProd.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("codigoProducto"));
        colDescProd.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("descripcionProducto"));
        colPrecUnit.setCellValueFactory(new PropertyValueFactory<Proveedores, Double>("precioUnitario"));
        colPrecDoc.setCellValueFactory(new PropertyValueFactory<Proveedores, Double>("precioDocena"));
        colPrecMay.setCellValueFactory(new PropertyValueFactory<Proveedores, Double>("precioMayor"));
        colImgProd.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("imagenProducto"));
        colExist.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("existencia"));
        colCodTipoProd.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("codigoTipoProducto"));
        colCodProv.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("codigoProveedor"));
        
        
        
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
        
           public ObservableList<TipoProducto> getTipoProducto() {
        ArrayList<TipoProducto> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarTipoProducto()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new TipoProducto(resultado.getInt("codigoTipoProducto"),
                        resultado.getString("descripcion")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaTipoProducto = FXCollections.observableList(lista);
    }
           
    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReportes.setDisable(true);
                imgAgregar.setImage(new Image("/org/danielsacol/images/IconoGuadar.png"));
                imgEliminar.setImage(new Image("/org/danielsacol/images/IconoCancelar.png"));
                tipoDeOperaciones = ProductosController.operaciones.ACTUALIZAR;
                break;
        }
        
        
    }
    public void guardar(){
        Productos registro = new Productos();
           
        
        }
}
