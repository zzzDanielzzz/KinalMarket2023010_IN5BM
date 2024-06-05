
package org.danielsacol.report;

import java.io.InputStream;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.danielsacol.db.Conexion;

/**
 *
 * @author informatica
 */
public class GenerarReportes {
    public static void mostrarReportes(String nombreReporte, String titulo, Map parametros){
        InputStream reporte = GenerarReportes.class.getResourceAsStream(nombreReporte);
        try{
            JasperReport ReporteClientes = (JasperReport)JRLoader.loadObject(reporte);
            JasperPrint reporteImpreso = JasperFillManager.fillReport(ReporteClientes, parametros, Conexion.getInstance().getConexion());
            JasperViewer visor = new JasperViewer(reporteImpreso, false);
            visor.setTitle(titulo);
            visor.setVisible(true);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    } 
}
