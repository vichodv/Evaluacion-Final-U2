package Vista;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Controlador.Controladorempleado;

public class Ventanaempleado extends Frame {

    private Label lblTitulo, lblIdEmpleado, lblNombre, lblFechaInicio, lblFechaTermino,
                  lblTipoContrato, lblPlanSalud, lblAfp;

    private TextField txtIdEmpleado, txtNombre, txtFechaInicio, txtFechaTermino;
    private Choice choiceTipoContrato;
    private Checkbox chkPlanSalud, chkAfp;

    private Button btnAgregar, btnConsultar, btnModificar, btnEliminar;

    private JTable tablaEmpleados;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollPane;

    private Controladorempleado controlador = new Controladorempleado();

    public Ventanaempleado() {

        setLayout(null);
        setSize(1000, 500);
        setTitle("Sistema de Gestion de Pagos - Vicente Del Valle y Benjamin Alvarez");
        setBackground(new Color(255, 200, 200));
        setLocationRelativeTo(null);

        configurarCampos();
        configurarTabla();
        configurarBotones();
        configurarEventos();

        controlador.cargarRegistros(modeloTabla);
    }

    private void configurarCampos() {

        lblTitulo = new Label("Sistema de Gestion de Pagos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setBounds(50, 40, 400, 30);
        add(lblTitulo);

        lblIdEmpleado = new Label("ID Empleado:");
        lblIdEmpleado.setBounds(50, 90, 100, 20);
        add(lblIdEmpleado);

        txtIdEmpleado = new TextField();
        txtIdEmpleado.setBounds(170, 90, 150, 20);
        add(txtIdEmpleado);

        lblNombre = new Label("Nombre:");
        lblNombre.setBounds(50, 120, 100, 20);
        add(lblNombre);

        txtNombre = new TextField();
        txtNombre.setBounds(170, 120, 150, 20);
        add(txtNombre);

        lblFechaInicio = new Label("Fecha Inicio:");
        lblFechaInicio.setBounds(50, 150, 100, 20);
        add(lblFechaInicio);

        txtFechaInicio = new TextField("YYYY-MM-DD");
        txtFechaInicio.setBounds(170, 150, 150, 20);
        add(txtFechaInicio);

        lblFechaTermino = new Label("Fecha Termino:");
        lblFechaTermino.setBounds(50, 180, 100, 20);
        add(lblFechaTermino);

        txtFechaTermino = new TextField("YYYY-MM-DD");
        txtFechaTermino.setBounds(170, 180, 150, 20);
        add(txtFechaTermino);

        lblTipoContrato = new Label("Tipo Contrato:");
        lblTipoContrato.setBounds(50, 210, 100, 20);
        add(lblTipoContrato);

        choiceTipoContrato = new Choice();
        choiceTipoContrato.add("Plazo Fijo");
        choiceTipoContrato.add("Indefinido");
        choiceTipoContrato.add("Honorarios");
        choiceTipoContrato.setBounds(170, 210, 150, 20);
        add(choiceTipoContrato);

        lblPlanSalud = new Label("Plan de Salud:");
        lblPlanSalud.setBounds(50, 240, 100, 20);
        add(lblPlanSalud);

        chkPlanSalud = new Checkbox();
        chkPlanSalud.setBounds(170, 240, 20, 20);
        add(chkPlanSalud);

        lblAfp = new Label("AFP:");
        lblAfp.setBounds(50, 270, 100, 20);
        add(lblAfp);

        chkAfp = new Checkbox();
        chkAfp.setBounds(170, 270, 20, 20);
        add(chkAfp);
    }

    private void configurarTabla() {

        String[] columnas = {
            "ID", "Nombre", "Fecha Inicio", "Fecha Término",
            "Tipo Contrato", "Plan Salud", "AFP"
        };

        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaEmpleados = new JTable(modeloTabla);

        scrollPane = new JScrollPane(tablaEmpleados);
        scrollPane.setBounds(350, 90, 600, 300);
        add(scrollPane);
    }

    private void configurarBotones() {

        btnAgregar = new Button("Agregar");
        btnAgregar.setBounds(50, 320, 80, 30);
        add(btnAgregar);

        btnConsultar = new Button("Consultar");
        btnConsultar.setBounds(140, 320, 80, 30);
        add(btnConsultar);

        btnModificar = new Button("Modificar");
        btnModificar.setBounds(230, 320, 80, 30);
        add(btnModificar);

        btnEliminar = new Button("Eliminar");
        btnEliminar.setBounds(140, 360, 80, 30);
        add(btnEliminar);
    }

    private boolean validarCampos() {

        if (txtIdEmpleado.getText().trim().isEmpty() ||
            txtNombre.getText().trim().isEmpty() ||
            txtFechaInicio.getText().trim().isEmpty() ||
            txtFechaTermino.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(null,
                    "Todos los campos de texto son obligatorios",
                    "Errror de validacion",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            Integer.parseInt(txtIdEmpleado.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "El id del empleado debe ser numerico",
                    "Errror de validacion",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String patron = "\\d{4}-\\d{2}-\\d{2}";
        if (!txtFechaInicio.getText().matches(patron) ||
            !txtFechaTermino.getText().matches(patron)) {

            JOptionPane.showMessageDialog(null,
                    "Las fechas deben tener formato YYYY-MM-DD",
                    "Errror de validacion",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void configurarEventos() {

        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!validarCampos()) return;

                controlador.agregarRegistro(
                    Integer.parseInt(txtIdEmpleado.getText().trim()),
                    txtNombre.getText().trim(),
                    txtFechaInicio.getText().trim(),
                    txtFechaTermino.getText().trim(),
                    choiceTipoContrato.getSelectedItem(),
                    chkPlanSalud.getState(),
                    chkAfp.getState(),
                    modeloTabla
                );

                controlador.cargarRegistros(modeloTabla);
            }
        });

        btnConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int fila = tablaEmpleados.getSelectedRow();

                if (fila >= 0) {
                    controlador.consultarRegistro(
                        fila, modeloTabla,
                        txtIdEmpleado, txtNombre,
                        txtFechaInicio, txtFechaTermino,
                        choiceTipoContrato,
                        chkPlanSalud, chkAfp
                    );
                }
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int fila = tablaEmpleados.getSelectedRow();
                if (fila < 0) return;

                if (!validarCampos()) return;

                controlador.modificarRegistro(
                    Integer.parseInt(txtIdEmpleado.getText().trim()),
                    txtNombre.getText().trim(),
                    txtFechaInicio.getText().trim(),
                    txtFechaTermino.getText().trim(),
                    choiceTipoContrato.getSelectedItem(),
                    chkPlanSalud.getState(),
                    chkAfp.getState(),
                    modeloTabla,
                    fila
                );

                controlador.cargarRegistros(modeloTabla);
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int fila = tablaEmpleados.getSelectedRow();
                if (fila < 0) return;

                int id = Integer.parseInt(
                        modeloTabla.getValueAt(fila, 0).toString());

                controlador.eliminarRegistro(id, modeloTabla, fila);
                controlador.cargarRegistros(modeloTabla);
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}