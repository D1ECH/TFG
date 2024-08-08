import org.eclipse.paho.client.mqttv3.*;

public class clienteMQTT {
    public static void main(String[] args) {
        String servidorBroker = "tcp://localhost:1883";
        String clientId = "Cliente";

        try {
            MqttClient cliente = new MqttClient(servidorBroker, clientId);
            cliente.connect();
            System.out.println("Cliente MQTT conectado al broker.");

            // Enviar un mensaje al topic "mi-topic"
            String mensaje = "Hola desde el cliente MQTT";
            cliente.publish("mi-topic", new MqttMessage(mensaje.getBytes()));

            // Desconectar el cliente
            cliente.disconnect();
            System.out.println("Cliente MQTT desconectado.");

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
