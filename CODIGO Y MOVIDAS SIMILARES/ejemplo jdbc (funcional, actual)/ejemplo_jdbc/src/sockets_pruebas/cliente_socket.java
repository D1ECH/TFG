package sockets_pruebas;
import java.io.*;
import java.net.*;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;

import Anomalias.Anomalia;

public class cliente_socket {

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // MAIN
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // CREACIÓN DE ANOMALÍAS ALEATORIAS
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        int[] VALORES_PERMITIDOS = {1, 3, 9};
        SecureRandom random = new SecureRandom();
        
        // Número de anomalías a generar
        int numAnomalias = 5;

        // Lista donde se guardan las anomalías para ser enviadas
        List<Anomalia> anomaList = new LinkedList<Anomalia>();

        // Generar y mostrar las anomalías aleatorias
        System.out.println("Anomalías generadas:");
        for (int i = 0; i < numAnomalias; i++) {
            // Seleccionar un valor aleatorio de los valores permitidos para probabilidad, detectabilidad y gravedad
            int probabilidad = VALORES_PERMITIDOS[random.nextInt(VALORES_PERMITIDOS.length)];
            int detectabilidad = VALORES_PERMITIDOS[random.nextInt(VALORES_PERMITIDOS.length)];
            int gravedad = VALORES_PERMITIDOS[random.nextInt(VALORES_PERMITIDOS.length)];
            
            // Otros valores aleatorios
            int thingID = random.nextInt(1000); // Ejemplo: ID del dispositivo entre 0 y 999
            int anomaliaID = random.nextInt(100); // Ejemplo: ID de la anomalía entre 0 y 99
            String amenaza = "Vulnerabilidad CVE-" + (i + 1); // Ejemplo: Amenaza aleatoria
            String descripcion = "Esta vuln consiste en ... " + (i + 1); // Ejemplo: Descripción aleatoria
            
            // Crear y mostrar la anomalía generada
            Anomalia anomalia = new Anomalia(thingID, anomaliaID, probabilidad, gravedad, detectabilidad, amenaza, descripcion);
            System.out.println(anomalia);

            anomaList.add(anomalia);
        }

        Anomalia anomaliaCoincide = new Anomalia(1, 1, 1, 1, 1, "CVE-0", "Descripción 0");
        anomaList.add(anomaliaCoincide);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // SOCKETS
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        final String host = "localhost";
        final int puerto = 12345;
        
        try (Socket cliente = new Socket(host, puerto);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
             PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true);
             BufferedReader entradaUsuario = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Conectado al servidor en " + host + ":" + puerto);

            // Enviar mensaje al servidor
            for (Anomalia a : anomaList) {
                
                System.out.println("Envío de anomalía al servidor");
                String mensajeUsuario = a.toString();
                salida.println(mensajeUsuario);
                
                // Recibir respuesta del servidor
                String respuestaServidor = entrada.readLine();
                System.out.println("Respuesta del servidor: " + respuestaServidor);
                // Recibir respuesta del servidor
                String respuestaServidor2 = entrada.readLine();
                System.out.println("Respuesta del servidor: " + respuestaServidor2 + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}

