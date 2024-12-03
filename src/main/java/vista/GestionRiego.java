package vista;

import com.toedter.calendar.JCalendar;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

/**
 * Clase para gestionar la interfaz de riego
 * 
 * @author Yordan
 */
public class GestionRiego extends javax.swing.JFrame {

    // Declarar componentes adicionales
    private JPanel panelCalendarioRiego;
    private JCalendar calendar;

    /**
     * Creates new form GestionRiego
     */
    public GestionRiego() {
        initComponents(); // Inicializa los componentes del formulario generados por NetBeans
        // Configuración adicional
        agregarCalendario(); // Agregar el calendario al panel
        cargarOrquideasEnComboBox();
    }

    /**
/**
 * Método para agregar un calendario y selector de hora al panel de riego
 */
private void agregarCalendario() {
    // Crear el calendario
    calendar = new JCalendar();
    
    // Crear el panel para contener el calendario y el selector de hora
    panelCalendarioRiego = new JPanel();
    panelCalendarioRiego.setLayout(null); // Layout absoluto para posicionar manualmente
    panelCalendarioRiego.setBounds(50, 50, 500, 500); // Ajustar posición y tamaño del panel
    
    // Configurar el tamaño y posición del calendario
    calendar.setBounds(50, 50, 400, 300); 
    
    // Crear el selector de hora (JSpinner)
    JLabel labelHora = new JLabel("Hora de riego:");
    labelHora.setBounds(50, 370, 100, 25); // Posición del label
    
    SpinnerDateModel modeloHora = new SpinnerDateModel();
    JSpinner spinnerHora = new JSpinner(modeloHora);
    spinnerHora.setBounds(150, 370, 100, 25); // Posición del spinner
    
    // Configurar formato de hora en el spinner
    JSpinner.DateEditor editorHora = new JSpinner.DateEditor(spinnerHora, "HH:mm");
    spinnerHora.setEditor(editorHora);
    
    // Agregar componentes al panel
    panelCalendarioRiego.add(calendar);
    panelCalendarioRiego.add(labelHora);
    panelCalendarioRiego.add(spinnerHora);
    
    // Agregar el panel al JFrame principal
    getContentPane().setLayout(null); // Layout absoluto en el JFrame
    getContentPane().add(panelCalendarioRiego);
    
    // Actualizar la ventana para mostrar cambios
    revalidate();
    repaint();
}

    public void cargarOrquideasEnComboBox() {
    String sql = "SELECT nombreOrquidea FROM orquideas";  // Solo seleccionamos el nombre de la orquídea
    
    jComboBox1.removeAllItems(); // Limpiar el JComboBox antes de cargar nuevos elementos
    
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/orquideas_basedatos", "root", "");
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        // Recorrer los resultados y llenar el JComboBox
        while (rs.next()) {
            String nombreOrquidea = rs.getString("nombreOrquidea");  // Obtener solo el nombre de la orquídea
            
            // Agregar el nombre de la orquídea al JComboBox
            jComboBox1.addItem(nombreOrquidea);  // Esto agregará solo el nombre al JComboBox
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al cargar las orquídeas: " + e.getMessage());
    }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setText("Programar Riego");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(337, 337, 337)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(180, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(421, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      JCalendar calendar = new JCalendar();
panelCalendarioRiego.add(calendar); // Panel donde agregarás el calendario

    // Inicializar variables
    String orquideaSeleccionada = (String) jComboBox1.getSelectedItem(); // Obteniendo la orquídea seleccionada
    String fechaRiego = "";

    // Verificar si el calendario tiene una fecha seleccionada
    if (calendar.getDate() != null) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            fechaRiego = sdf.format(calendar.getDate()); // Convertir la fecha seleccionada a String
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al formatear la fecha: " + e.getMessage());
            return;
        }
    } else {
        JOptionPane.showMessageDialog(null, "Por favor selecciona una fecha en el calendario.");
        return;
    }

    // Validar que se haya seleccionado una orquídea
    if (orquideaSeleccionada == null || orquideaSeleccionada.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Por favor selecciona una orquídea.");
        return;
    }

    // Guardar el registro en la base de datos
    String sqlOrquidea = "SELECT idOrquidea FROM orquideas WHERE nombreOrquidea = ?";
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/orquideas_basedatos", "root", "");
         PreparedStatement stmtOrquidea = conn.prepareStatement(sqlOrquidea)) {

        stmtOrquidea.setString(1, orquideaSeleccionada);
        try (ResultSet rs = stmtOrquidea.executeQuery()) {
            if (rs.next()) {
                int idOrquidea = rs.getInt("idOrquidea");

                // Insertar el riego en la tabla
                String sqlInsertRiego = "INSERT INTO calendario_riego (idOrquidea, fechaHoraRiego) VALUES (?, ?)";
                try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsertRiego)) {
                    stmtInsert.setInt(1, idOrquidea);
                    stmtInsert.setString(2, fechaRiego);
                    stmtInsert.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Riego registrado exitosamente.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la orquídea seleccionada.");
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al registrar el riego: " + e.getMessage());
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GestionRiego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestionRiego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestionRiego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestionRiego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GestionRiego().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    // End of variables declaration//GEN-END:variables
}
