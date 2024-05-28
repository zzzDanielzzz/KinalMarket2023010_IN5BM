
package org.danielsacol.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;
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
import org.danielsacol.bean.Productos;
import org.danielsacol.bean.Proveedores;
import org.danielsacol.bean.TipoProducto;
import org.danielsacol.db.Conexion;
import org.danielsacol.system.Main;

/**
 *
 * @author informatica
 */
public class ProductosController implements Initializable {

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
    private Button btnTipoProd;
    
    @FXML
    private Button btnHomeProductos;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        combCodProveedor.setItems(getProveedores());
        combCodProducto.setItems(getTipoProducto());
    }

    public void desactivarControles() {
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

    public void activarControles() {
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

    public void limpiarControles() {
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

    public void cargarDatos() {
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

    public void seleccionarElementos() {
        txtCodProd.setText((((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto()));
        txtDescProd.setText((((Productos) tblProductos.getSelectionModel().getSelectedItem()).getDescripcionProducto()));
        txtPrecUnit.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
        txtPrecDoc.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getPrecioDocena()));
        txtPrecMay.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getPrecioMayor()));
        txtImgProd.setText((((Productos) tblProductos.getSelectionModel().getSelectedItem()).getImagenProducto()));
        txtExist.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getExistencia()));
        //combCodProducto.setValue(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
        //combCodProveedor.setValue(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        combCodProducto.getSelectionModel().select(buscarTipoProducto(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
        combCodProveedor.getSelectionModel().select(buscarProveedor(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
    }

    public TipoProducto buscarTipoProducto(int codigoTipoProducto) {
        TipoProducto resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarTipoProducto(?)}");
            procedimiento.setInt(1, codigoTipoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new TipoProducto(registro.getInt("codigoTipoProducto"),
                        registro.getString("descripcion"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
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
                limpiarControles();
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReportes.setDisable(true);
                imgAgregar.setImage(new Image("/org/danielsacol/images/IconoGuadar.png"));
                imgEliminar.setImage(new Image("/org/danielsacol/images/IconoCancelar.png"));
                tipoDeOperaciones = ProductosController.operaciones.ACTUALIZAR;
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
                tipoDeOperaciones = ProductosController.operaciones.NINGUNO;
                cargarDatos();
                break;
        }

    }

    public void guardar() {
        Productos registro = new Productos();
        registro.setCodigoProducto(txtCodProd.getText());
        registro.setCodigoProveedor(((Proveedores) combCodProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        registro.setCodigoTipoProducto((((TipoProducto) combCodProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
        registro.setDescripcionProducto(txtDescProd.getText());
        registro.setPrecioDocena(Double.parseDouble("0.00"));
        registro.setPrecioMayor(Double.parseDouble("0.00"));
        registro.setExistencia(Integer.parseInt(txtExist.getText()));
        registro.setImagenProducto(txtImgProd.getText());
        registro.setPrecioUnitario(Double.parseDouble("0.00"));

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{Call sp_agregarProducto(?,?,?,?,?,?,?,?,?)}");
            procedimiento.setString(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setDouble(3, registro.getPrecioUnitario());
            procedimiento.setDouble(4, registro.getPrecioDocena());
            procedimiento.setDouble(5, registro.getPrecioMayor());
            procedimiento.setString(6, registro.getImagenProducto());
            procedimiento.setInt(7, registro.getExistencia());
            procedimiento.setInt(8, registro.getCodigoTipoProducto());
            procedimiento.setInt(9, registro.getCodigoProveedor());
            procedimiento.execute();

            listaProductos.add(registro);
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
                if (tblProductos.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro", "Eliminar registro",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarProducto(?)}");
                            procedimiento.setString(1, ((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
                            procedimiento.execute();
                            listaProductos.remove(tblProductos.getSelectionModel().getSelectedItem());
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
                if (tblProductos.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/danielsacol/images/IconoActualizar.png"));
                    imgReportes.setImage(new Image("/org/danielsacol/images/IconoCancelar.png"));
                    activarControles();
                    txtCodProd.setEditable(false);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarProducto(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            Productos registro = (Productos) tblProductos.getSelectionModel().getSelectedItem();

            registro.setDescripcionProducto(txtDescProd.getText());
            registro.setPrecioUnitario(Double.parseDouble(txtPrecUnit.getText()));
            registro.setPrecioDocena(Double.parseDouble(txtPrecDoc.getText()));
            registro.setPrecioMayor(Double.parseDouble(txtPrecDoc.getText()));
            registro.setImagenProducto(txtImgProd.getText());
            registro.setExistencia(Integer.parseInt(txtExist.getText()));
            registro.setCodigoProveedor(((Proveedores) combCodProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            registro.setCodigoTipoProducto((((TipoProducto) combCodProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));

            procedimiento.setString(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setDouble(3, registro.getPrecioUnitario());
            procedimiento.setDouble(4, registro.getPrecioDocena());
            procedimiento.setDouble(5, registro.getPrecioMayor());
            procedimiento.setString(6, registro.getImagenProducto());
            procedimiento.setInt(7, registro.getExistencia());
            procedimiento.setInt(8, registro.getCodigoTipoProducto());
            procedimiento.setInt(9, registro.getCodigoProveedor());

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
                tipoDeOperaciones = ProductosController.operaciones.NINGUNO;
                cargarDatos();
            case NINGUNO:
                break;
        }
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnHomeProductos) {
            escenarioPrincipal.menuPrincipalView();
        }  else if(event.getSource() == btnTipoProd){
            escenarioPrincipal.menuTipoProductoView();
        }
    }
}
