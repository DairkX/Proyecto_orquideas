import controlador.CondicionAmbientalController;
import java.util.Timer;
import java.util.TimerTask;

public class CondicionAmbientalScheduler {

    private Timer timer;

    public CondicionAmbientalScheduler() {
        timer = new Timer();
    }

    // Inicia el proceso que se ejecutará cada 30 minutos
    public void iniciarProceso() {
        // Ejecutar la tarea cada 30 minutos (1800000 milisegundos)
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                registrarYVerificarCondicion();
            }
        }, 0, 1800000);  // 1800000 ms = 30 minutos
    }

    // Método que se ejecuta cada 30 minutos
    private void registrarYVerificarCondicion() {
        // Aquí es donde se obtiene el valor de la temperatura o humedad
        // Puede obtenerse de un sensor o de alguna otra fuente de datos
        float valor = obtenerValorCondicionAmbiental();

        // Registro de la condición ambiental en la base de datos
        boolean exito = registrarCondicionAmbiental(valor);
        
        // Si el registro fue exitoso, verificamos la condición
        if (exito) {
            // Verificamos si el valor está fuera del rango y generamos la alerta si es necesario
            if (valor < 18 || valor > 25) {
                generarAlerta(valor);
            }
        }
    }

    // Simulamos la obtención de un valor de temperatura o humedad (esto debería venir de un sensor)
    private float obtenerValorCondicionAmbiental() {
        // Aquí puedes obtener el valor real del sensor, por ejemplo:
        // float valor = sensor.getTemperatura();
        // Para el ejemplo, usaremos un valor fijo:
        return 26.5f; // Ejemplo, valor fuera del rango
    }

    // Registramos la condición ambiental en la base de datos
    private boolean registrarCondicionAmbiental(float valor) {
        // Suponiendo que tienes un método para registrar la condición en la base de datos
        CondicionAmbientalController controller = new CondicionAmbientalController();
        return controller.agregarCondicion("Temperatura", valor);  // Suponiendo que es temperatura
    }

    // Generamos la alerta si el valor está fuera del rango
    private void generarAlerta(float valor) {
        System.out.println("¡Alerta! La temperatura está fuera de rango: " + valor);
        // Aquí podrías agregar más lógica para notificar, por ejemplo:
        // - Mostrar una ventana emergente
        // - Enviar un correo electrónico o mensaje
        // - Guardar la alerta en la base de datos
    }

    public static void main(String[] args) {
        // Iniciamos el proceso de verificación periódica
        new CondicionAmbientalScheduler().iniciarProceso();
    }
}
