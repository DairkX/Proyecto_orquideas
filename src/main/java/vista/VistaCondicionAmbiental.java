package vista;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class VistaCondicionAmbiental extends JFrame {

    private JTable tablaCondiciones;
    private JTable tablaAlertas;
    private DefaultTableModel modeloTablaCondiciones;
    private DefaultTableModel modeloTablaAlertas;

    public VistaCondicionAmbiental() {
        // Configuración de la ventana principal
        setTitle("Condiciones Ambientales y Alertas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Crear el panel principal y el layout
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        add(panelPrincipal);
        
        // Crear la tabla para mostrar las condiciones ambientales
        modeloTablaCondiciones = new DefaultTableModel(new Object[]{"Fecha y Hora", "Tipo de Sensor", "Valor", "Estado de Alerta"}, 0);
        tablaCondiciones = new JTable(modeloTablaCondiciones);
        JScrollPane scrollPaneCondiciones = new JScrollPane(tablaCondiciones);
        panelPrincipal.add(scrollPaneCondiciones, BorderLayout.CENTER);
        
        // Crear la tabla para mostrar las alertas
        modeloTablaAlertas = new DefaultTableModel(new Object[]{"ID Alerta", "ID Condición", "Mensaje", "Fecha", "Hora"}, 0);
        tablaAlertas = new JTable(modeloTablaAlertas);
        JScrollPane scrollPaneAlertas = new JScrollPane(tablaAlertas);
        panelPrincipal.add(scrollPaneAlertas, BorderLayout.SOUTH);

        // Iniciar el temporizador para cargar los datos automáticamente cada 30 minutos
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Obtener los valores de temperatura y humedad
                float temperatura = obtenerTemperaturaActual();
                float humedad = obtenerHumedadActual();
                
                // Registrar los valores en la tabla de condiciones
                agregarCondicionATabla(1, temperatura); // 1 es el idSensor para Temperatura
                agregarCondicionATabla(2, humedad);    // 2 es el idSensor para Humedad
                
                // Verificar si los valores están fuera de los rangos y generar alertas
                verificarYGenerarAlerta(temperatura, humedad);
            }
        }, 0, 1800000); // Ejecutar cada 30 minutos (30 * 60 * 1000 ms)
    }

    // Método para obtener la temperatura actual (simulada o desde un sensor real)
    private float obtenerTemperaturaActual() {
        // Simulamos que obtenemos un valor entre 15 y 30 grados (cambiar por sensor real si es necesario)
        return 18 + (float) Math.random() * (30 - 15);
    }

    // Método para obtener la humedad actual (simulada o desde un sensor real)
    private float obtenerHumedadActual() {
        // Simulamos que obtenemos un valor entre 50 y 90% (cambiar por sensor real si es necesario)
        return 50 + (float) Math.random() * (90 - 50);
    }

    // Agregar la condición a la tabla de la interfaz
    private void agregarCondicionATabla(int idSensor, float valor) {
        String estadoAlerta = "Normal";
        if ((idSensor == 1 && (valor < 18 || valor > 24)) || (idSensor == 2 && (valor < 60 || valor > 80))) {
            estadoAlerta = "¡ALERTA!";
        }
        String fechaHora = getFechaHoraActual();
        
        // Insertar en la tabla de condiciones
        modeloTablaCondiciones.addRow(new Object[]{fechaHora, idSensor == 1 ? "Temperatura" : "Humedad", valor, estadoAlerta});
        
        // Guardar en la base de datos
        int idCondicion = guardarCondicionEnBD(idSensor, valor, fechaHora);
        
        // Si es una alerta, generar alerta
        if (estadoAlerta.equals("¡ALERTA!")) {
            generarAlerta(idCondicion, valor, idSensor == 1 ? "Temperatura" : "Humedad");
        }
    }

    // Verificar si la condición está fuera de rango y generar la alerta
    private void verificarYGenerarAlerta(float temperatura, float humedad) {
        if (temperatura < 18 || temperatura > 24) {
            String mensaje = "ALERTA: La temperatura está fuera del rango permitido: " + temperatura;
            // Se generará la alerta y se vinculará con el idCondicion correspondiente
        } else if (humedad < 60 || humedad > 80) {
            String mensaje = "ALERTA: La humedad está fuera del rango permitido: " + humedad;
            // Se generará la alerta y se vinculará con el idCondicion correspondiente
        }
    }

    // Método para generar la alerta y vincularla con la condición
    private void generarAlerta(int idCondicion, float valor, String tipoSensor) {
        String mensaje = tipoSensor + " fuera de rango: " + valor;
        String fecha = getFechaHoraActual().split(" ")[0];
        String hora = getFechaHoraActual().split(" ")[1];
        
        // Insertar alerta en la base de datos
        String query = "INSERT INTO alertas (idCondicion, mensaje, fecha, hora) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/orquideas_basedatos", "root", "");
             PreparedStatement ps = conn.prepareStatement(query)) {
             
            ps.setInt(1, idCondicion);
            ps.setString(2, mensaje);
            ps.setString(3, fecha);
            ps.setString(4, hora);
            ps.executeUpdate();
            
            // Luego, actualizamos la vista con la nueva alerta
            agregarAlertaATabla(idCondicion, mensaje, fecha, hora);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al generar la alerta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para agregar una alerta a la tabla de alertas
    private void agregarAlertaATabla(int idCondicion, String mensaje, String fecha, String hora) {
        // Agregar la alerta a la tabla en la vista
        int idAlerta = modeloTablaAlertas.getRowCount() + 1;  // ID generado de forma sencilla para la demostración
        modeloTablaAlertas.addRow(new Object[]{idAlerta, idCondicion, mensaje, fecha, hora});
    }

    // Método para guardar la condición ambiental en la base de datos y retornar el idCondicion
    private int guardarCondicionEnBD(int idSensor, float valor, String fechaHora) {
        String query = "INSERT INTO condiciones_ambientales (idSensor, fechaHora, valor) VALUES (?, ?, ?)";
        int idCondicion = -1;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/orquideas_basedatos", "root", "");
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
             
            ps.setInt(1, idSensor);  // 1 para Temperatura, 2 para Humedad
            ps.setString(2, fechaHora);
            ps.setFloat(3, valor);
            ps.executeUpdate();
            
            // Obtener el ID generado automáticamente (idCondicion)
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idCondicion = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar la condición ambiental: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return idCondicion;
    }

    // Método para obtener la fecha y hora actual
    private String getFechaHoraActual() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
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
            java.util.logging.Logger.getLogger(VistaCondicionAmbiental.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaCondicionAmbiental.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaCondicionAmbiental.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaCondicionAmbiental.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaCondicionAmbiental().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
