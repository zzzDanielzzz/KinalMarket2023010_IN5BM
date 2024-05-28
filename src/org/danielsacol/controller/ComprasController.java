
package org.danielsacol.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.danielsacol.bean.Compras;
import org.danielsacol.db.Conexion;
import org.danielsacol.system.Main;

/**
 *
 * @author compu
 */
public class ComprasController implements Initializable {

    private Main escenarioPrincipal;
    private ObservableList<Compras> listaCompras;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    @FXML
    private Button btnHomeCompras;
   @FXML
    private Button btnDetalleCompra;
    @FXML
    private TableView tblCompras;

    @FXML
    private TableColumn colNumeroDocumento;

    @FXML
    private TableColumn colFechaDocumento;

    @FXML
    private TableColumn colDescripcion;

    @FXML
    private TableColumn colTotalDocumento;

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
    private TextField txtNumeroDocumento;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtTotalDocumento;

    @FXML
    private DatePicker DPCompras;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();

    }

    public void cargarDatos() {
        tblCompras.setItems(getCompras());
        colNumeroDocumento.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("numeroDocumento"));
        colFechaDocumento.setCellValueFactory(new PropertyValueFactory<Compras, String>("fechaDocumento"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Compras, String>("descripcion"));
        colTotalDocumento.setCellValueFactory(new PropertyValueFactory<Compras, Double>("totalDocumento"));

    }

    public void seleccionarElementos() {
        Compras fechaSeleccionada = (Compras) tblCompras.getSelectionModel().getSelectedItem();
        String fecha = fechaSeleccionada.getFechaDocumento();
        LocalDate fechaCompras = null;
        fechaCompras = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        txtNumeroDocumento.setText(String.valueOf(fechaSeleccionada.getNumeroDocumento()));
        DPCompras.setValue(fechaCompras);
        txtDescripcion.setText(fechaSeleccionada.getDescripcion());
        txtTotalDocumento.setText(String.valueOf(fechaSeleccionada.getTotalDocumento()));

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

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                limpiarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReportes.setDisable(true);
                imgAgregar.setImage(new Image("/org/danielsacol/images/IconoGuadar.png"));
                imgEliminar.setImage(new Image("/org/danielsacol/images/IconoCancelar.png"));
                tipoDeOperaciones = ComprasController.operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("/org/danielsacol/images/IconoAgregar.png"));
                imgEliminar.setImage(new Image("/org/danielsacol/images/IconoEliminar.png"));
                tipoDeOperaciones = ComprasController.operaciones.NINGUNO;
                break;
        }
    }

    public void guardar() {
        Compras registro = new Compras();
        LocalDate fechaSeleccionada = DPCompras.getValue();
        String fechaFormateada = fechaSeleccionada.toString();
        registro.setNumeroDocumento(Integer.parseInt(txtNumeroDocumento.getText()));
        registro.setFechaDocumento(fechaFormateada);
        registro.setDescripcion(txtDescripcion.getText());
        registro.setTotalDocumento(Double.parseDouble("0.00"));
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarCompra(?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setString(2, registro.getFechaDocumento());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setDouble(4, registro.getTotalDocumento());
            procedimiento.execute();
            listaCompras.add(registro);
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
                tipoDeOperaciones = ComprasController.operaciones.NINGUNO;
                break;

            default:
                if (tblCompras.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro", "Eliminar registro",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarCompra(?)}");
                            procedimiento.setInt(1, ((Compras) tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento());
                            procedimiento.execute();
                            listaCompras.remove(tblCompras.getSelectionModel().getSelectedItem());
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

    // EDITAR LLEVA EL MISMO 
    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblCompras.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/danielsacol/images/IconoActualizar.png"));
                    imgReportes.setImage(new Image("/org/danielsacol/images/IconoCancelar.png"));
                    activarControles();
                    txtNumeroDocumento.setEditable(false);
                    tipoDeOperaciones = ComprasController.operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de selecionar un registro para editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReportes.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/danielsacol/images/IconoEditar.png"));
                imgReportes.setImage(new Image("/org/danielsacol/images/IconoReportes.png"));
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = ComprasController.operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarCompra(?, ?, ?, ?)}");
            Compras registro = (Compras) tblCompras.getSelectionModel().getSelectedItem();
            LocalDate fechaSeleccionada = DPCompras.getValue();
            String fechaFormateada = fechaSeleccionada.toString();
            registro.setNumeroDocumento(Integer.parseInt(txtNumeroDocumento.getText()));
            registro.setFechaDocumento(fechaFormateada);
            registro.setDescripcion(txtDescripcion.getText());
            registro.setTotalDocumento(Double.parseDouble(txtTotalDocumento.getText()));

            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setString(2, registro.getFechaDocumento());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setDouble(4, registro.getTotalDocumento());

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
                btnHomeCompras.setDisable(false);
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = ComprasController.operaciones.NINGUNO;
                cargarDatos();
            case NINGUNO:
                break;
        }
    }

    public void desactivarControles() {
        txtNumeroDocumento.setEditable(false);
        DPCompras.setEditable(false);
        txtDescripcion.setEditable(false);
        txtTotalDocumento.setEditable(false);

    }

    public void activarControles() {
        txtNumeroDocumento.setEditable(true);
        DPCompras.setEditable(true);
        txtDescripcion.setEditable(true);
        txtTotalDocumento.setEditable(true);
    }

    public void limpiarControles() {
        txtNumeroDocumento.clear();
        DPCompras.getEditor().clear();
        txtDescripcion.clear();
        txtTotalDocumento.clear();
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnHomeCompras) {
            escenarioPrincipal.menuPrincipalView();
        } else if (event.getSource() == btnDetalleCompra){
            escenarioPrincipal.menuDetalleCompraView();
        }
    }
}
