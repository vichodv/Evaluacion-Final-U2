package Controlador;

import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.TextField;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

public class Controladorempleado {

    public void agregarRegistro(int idEmpleado, String nombreEmpleado,
                                String fechaInicioStr, String fechaTerminoStr,
                                String tipoContrato, boolean planSalud, boolean afp,
                                DefaultTableModel modeloTabla) {

        String query = "INSERT INTO empleado (IdEmpleado, nombreEmpleado, fechaInicio, fechaTermino, tipoContrato, planSalud, afp) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            Date fechaInicio = new SimpleDateFormat("yyyy-MM-dd").parse(fechaInicioStr);
            Date fechaTermino = new SimpleDateFormat("yyyy-MM-dd").parse(fechaTerminoStr);

            statement.setInt(1, idEmpleado);
            statement.setString(2, nombreEmpleado);
            statement.setDate(3, new java.sql.Date(fechaInicio.getTime()));
            statement.setDate(4, new java.sql.Date(fechaTermino.getTime()));
            statement.setString(5, tipoContrato);
            statement.setBoolean(6, planSalud);
            statement.setBoolean(7, afp);

            statement.executeUpdate();

            modeloTabla.addRow(new Object[]{
                idEmpleado,
                nombreEmpleado,
                new SimpleDateFormat("yyyy-MM-dd").format(fechaInicio),
                new SimpleDateFormat("yyyy-MM-dd").format(fechaTermino),
                tipoContrato,
                planSalud,
                afp
            });

        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void cargarRegistros(DefaultTableModel modeloTabla) {

        String query = "SELECT * FROM empleado";

        try (Connection connection = conexion.getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)) {

            modeloTabla.setRowCount(0);

            while (result.next()) {

                int id = result.getInt("IdEmpleado");
                String nombre = result.getString("nombreEmpleado");
                Date fechaInicio = result.getDate("fechaInicio");
                Date fechaTermino = result.getDate("fechaTermino");
                String tipoContrato = result.getString("tipoContrato");
                boolean planSalud = result.getBoolean("planSalud");
                boolean afp = result.getBoolean("afp");

                String fi = new SimpleDateFormat("yyyy-MM-dd").format(fechaInicio);
                String ft = new SimpleDateFormat("yyyy-MM-dd").format(fechaTermino);

                modeloTabla.addRow(new Object[]{
                    id, nombre, fi, ft, tipoContrato, planSalud, afp
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void consultarRegistro(int filaSeleccionada, DefaultTableModel modeloTabla,
                                  TextField txtId, TextField txtNombre,
                                  TextField txtFechaInicio, TextField txtFechaTermino,
                                  Choice choiceTipoContrato,
                                  Checkbox chkPlanSalud, Checkbox chkAfp) {

        txtId.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
        txtNombre.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
        txtFechaInicio.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
        txtFechaTermino.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
        choiceTipoContrato.select(modeloTabla.getValueAt(filaSeleccionada, 4).toString());

        chkPlanSalud.setState(Boolean.parseBoolean(
                modeloTabla.getValueAt(filaSeleccionada, 5).toString()));
        chkAfp.setState(Boolean.parseBoolean(
                modeloTabla.getValueAt(filaSeleccionada, 6).toString()));
    }

    public void modificarRegistro(int idEmpleado, String nombreEmpleado,
                                  String fechaInicioStr, String fechaTerminoStr,
                                  String tipoContrato, boolean planSalud, boolean afp,
                                  DefaultTableModel modeloTabla, int filaSeleccionada) {

        String query = "UPDATE empleado SET nombreEmpleado = ?, fechaInicio = ?, fechaTermino = ?, tipoContrato = ?, planSalud = ?, afp = ? "
                     + "WHERE IdEmpleado = ?";

        try (Connection connection = conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            Date fechaInicio = new SimpleDateFormat("yyyy-MM-dd").parse(fechaInicioStr);
            Date fechaTermino = new SimpleDateFormat("yyyy-MM-dd").parse(fechaTerminoStr);

            statement.setString(1, nombreEmpleado);
            statement.setDate(2, new java.sql.Date(fechaInicio.getTime()));
            statement.setDate(3, new java.sql.Date(fechaTermino.getTime()));
            statement.setString(4, tipoContrato);
            statement.setBoolean(5, planSalud);
            statement.setBoolean(6, afp);
            statement.setInt(7, idEmpleado);

            statement.executeUpdate();

            String fi = new SimpleDateFormat("yyyy-MM-dd").format(fechaInicio);
            String ft = new SimpleDateFormat("yyyy-MM-dd").format(fechaTermino);

            modeloTabla.setValueAt(nombreEmpleado, filaSeleccionada, 1);
            modeloTabla.setValueAt(fi, filaSeleccionada, 2);
            modeloTabla.setValueAt(ft, filaSeleccionada, 3);
            modeloTabla.setValueAt(tipoContrato, filaSeleccionada, 4);
            modeloTabla.setValueAt(planSalud, filaSeleccionada, 5);
            modeloTabla.setValueAt(afp, filaSeleccionada, 6);

        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void eliminarRegistro(int idEmpleado, DefaultTableModel modeloTabla, int filaSeleccionada) {

        String query = "DELETE FROM empleado WHERE IdEmpleado = ?";

        try (Connection connection = conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, idEmpleado);
            statement.executeUpdate();

            modeloTabla.removeRow(filaSeleccionada);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}