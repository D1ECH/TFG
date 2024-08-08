package sockets_pruebas;
import java.io.*;
import java.net.*;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import Dispositivos.Dispositivo;


public class servidor_socket {

    private static int ID_NuevaConfianza = 0;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Tratamiento de ID y anomalía
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static String ID_existente(String mensajeCliente, List<Dispositivo> operatorThings){
                String res = null;
        
        String[] words = mensajeCliente.split(":");
        // for (String word : words) {
        //     System.out.println(word);
        // }
        for (Dispositivo dispositivo : operatorThings) {
            if(dispositivo.getThingID()==Integer.parseInt(words[0])){
                    res = "El dispositivo se encuentra entre los existentes";
                break;
            }
        }
       
        return res;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // MAIN
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) throws Exception {
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // GENERACIÓN DISPOSITIVOS
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        List<Dispositivo> operatorThings = new ArrayList<Dispositivo>();
        Dispositivo thingRandom;
        
        /**
         * Generación de Dispositivos --> Se generan 4 con IDs random
         * 
         */
    
        //Number of things generated: 4
        //Car, PDA, Smartglass, Smartwatch
        for (int j = 0 ; j < 4 ; j++){
            
            //thingID: 0 to 3200
            SecureRandom randomThingID = new SecureRandom();
            int thingID = randomThingID.nextInt(32001);  	
            
            //LastSupervision: 01/01/2017 to 31/12/2017 
            long minDayThing = LocalDate.of(2017, 1, 1).toEpochDay();
            long maxDayThing = LocalDate.of(2017, 12, 31).toEpochDay();
            long randomDayThing = ThreadLocalRandom.current().nextLong(minDayThing, maxDayThing);
            LocalDate lastSupervision = LocalDate.ofEpochDay(randomDayThing);	
            
            //NbPastDefaillances: 0 to 30
            SecureRandom randomDefaillances = new SecureRandom();
            int nbPastDefaillances = randomDefaillances.nextInt(31);
            
            //SecurityCertification: True or False
            SecureRandom randomCertification = new SecureRandom();
            boolean securityCertification = randomCertification.nextBoolean();
            
            thingRandom = new Dispositivo(thingID, lastSupervision, nbPastDefaillances, securityCertification, "Fisico", nbPastDefaillances);
            operatorThings.add(thingRandom);
            
            
        }
        
        //TRAZA
        //Mostramos los IDs, de los dispositivos creados
        for (Dispositivo dispositivo : operatorThings) {
            System.out.println(dispositivo);
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // SOCKETS
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        final int puerto = 12345;

        try (
                ServerSocket servidor = new ServerSocket(puerto)
            ) {

            System.out.println("Esperando conexiones en el puerto " + puerto);

            while (true) {
                
                try (
                        /*El servidor espera y acepta conexiones entrantes de clientes. Una vez que se establece una 
                        conexión con un cliente, se crea un nuevo objeto Socket para manejar la comunicación con ese cliente.*/
                        Socket cliente = servidor.accept();
                        
                        //Recoge mensaje enviado por el cliente desde el BufferedReader asociado con el InputStream del socket del cliente.
                        BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                        
                        //Utiliza el PrintWriter asociado con el OutputStream del socket del cliente para enviar una respuesta al cliente.
                        PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true)
                    ) {

                    System.out.println("Cliente conectado desde " + cliente.getInetAddress() + ":" + cliente.getPort());

                    // Recibir mensaje del cliente
                    String mensajeCliente = entrada.readLine();
                    System.out.println("Mensaje del cliente: " + mensajeCliente);
                    
                    System.out.println(ID_existente(mensajeCliente, operatorThings));
                    
                    // Enviar respuesta al cliente
                    salida.println("Hola, soy el servidor. Recibí tu mensaje");
                    salida.println("Esta es mi respuesta: " + ID_NuevaConfianza);
                }
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


