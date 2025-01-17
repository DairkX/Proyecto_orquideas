package vista;

import controlador.SensorController;
import modelo.Sensor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class VistaSensor extends JFrame {
    private JTextField txtTipoSensor;
    private JButton btnAgregarSensor, btnVerSensores;
    private JTable tablaSensores;
    private SensorController sensorController;
    
    public VistaSensor() {
        sensorController = new SensorController(); // Crea el controlador de sensores
        
        // Configuración del panel
        setTitle("Gestión de Sensores");
        setSize(500, 400);
        setLocationRelativeTo(null); // Centra la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Configuración de layout
        setLayout(new BorderLayout());
        
        // Panel para controles
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        
        JLabel lblTipoSensor = new JLabel("Tipo de Sensor:");
        txtTipoSensor = new JTextField(15);
        btnAgregarSensor = new JButton("Agregar Sensor");
        btnVerSensores = new JButton("Ver Sensores");
        
        // Tabla para mostrar sensores
        tablaSensores = new JTable();
        
        // Añadir componentes al panel
        panel.add(lblTipoSensor);
        panel.add(txtTipoSensor);
        panel.add(btnAgregarSensor);
        panel.add(btnVerSensores);
        add(panel, BorderLayout.NORTH);
        
        JScrollPane scrollPane = new JScrollPane(tablaSensores);
        add(scrollPane, BorderLayout.CENTER);

        // Acciones de los botones
        btnAgregarSensor.addActionListener(e -> agregarSensor());
        btnVerSensores.addActionListener(e -> mostrarSensores());
        
        // Agregar MouseListener al panel para detectar clics
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Acción que quieres que ocurra cuando se hace clic en el panel
                JOptionPane.showMessageDialog(VistaSensor.this, "¡Panel de Sensores Clickeado!");
                // Aquí puedes abrir otra vista o hacer lo que necesites
            }
        });
    }

    private void agregarSensor() {
        String tipoSensor = txtTipoSensor.getText();
        Sensor sensor = new Sensor(0, tipoSensor); // El ID se auto-genera
        boolean agregado = sensorController.agregarSensor(sensor);
        if (agregado) {
            JOptionPane.showMessageDialog(this, "Sensor agregado exitosamente");
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar sensor");
        }
    }

    private void mostrarSensores() {
        List<Sensor> sensores = sensorController.obtenerSensores();
        String[] columnas = {"ID", "Tipo de Sensor"};
        Object[][] datos = new Object[sensores.size()][2];
        
        for (int i = 0; i < sensores.size(); i++) {
            datos[i][0] = sensores.get(i).getIdSensor();
            datos[i][1] = sensores.get(i).getTipoSensor();
        }
        
        tablaSensores.setModel(new javax.swing.table.DefaultTableModel(datos, columnas));
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
       // Método main para lanzar la aplicación
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaSensor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new VistaSensor().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
