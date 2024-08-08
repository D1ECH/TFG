package sockets_pruebas;
import java.io.*;
import java.net.*;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import Dispositivos.Dispositivo;

public class servidor_socket {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Tratamiento de ID y anomalía
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static String ID_existente(String mensajeCliente, List<Dispositivo> operatorThings){
        /*
         * Comprobar si el ID existe en la BD de dispositivos.
         *  --> Si existe se procede a calcular el nivel de riesgo para actualizar el ESTADO del dispositivo --> [prohibido | pendiente_evaluacion] --> Cálculo de confianza --> [cuarentena | confiable]   
         *  --> No existe directamente no se hace nada --> Erro: dispositivo no existente.
         */

        String res = "No se ha encontrado el dispositivo entre los existentes";

        // Separar la cadena en partes usando el delimitador " ==> "
        String[] partes = mensajeCliente.split(" ==> ");

        // Obtener la subcadena antes del delimitador " ==> "
        String idAnomalia = partes[0].split(":")[0];
        String amenaza = partes[0].split(":")[1];

        // Obtener la subcadena después del delimitador " ==> "
        String info = partes[1];

        // Separar la información en partes usando el delimitador " --> "
        String[] partesInfo = info.split(" --> ");

        // Obtener thingID, probabilidad, gravedad y detectabilidad
        String thingID = partesInfo[0].trim();
        String[] valores = partesInfo[1].split(" \\| ");
        String probabilidad = valores[0].substring(1).trim();
        String gravedad = valores[1].trim();
        String detectabilidad = valores[2].substring(0, valores[2].indexOf("]")).trim();

        // Imprimir los valores obtenidos
        System.out.println(" # ID de Anomalía: " + idAnomalia);
        System.out.println(" # Amenaza: " + amenaza);
        System.out.println(" # thingID: " + thingID);
        System.out.println(" # Probabilidad: " + probabilidad);
        System.out.println(" # Gravedad: " + gravedad);
        System.out.println(" # Detectabilidad: " + detectabilidad);


        //String nivel_riesgo = nivel_riesgo(probabilidad,gravedad,detectabilidad);


        // Quizás habría que buscar antes los dispositivos en la BD y a partir de ahí ya comparar con los datos obtenidos ?????

        for (Dispositivo dispositivo : operatorThings) {
            if(dispositivo.getThingID()==Integer.parseInt(thingID)){
                    String nivel_riesgo = nivel_riesgo(probabilidad,gravedad,detectabilidad);
                    res = "El dispositivo se encuentra entre los existentes.\nNivel de riesgo = " + nivel_riesgo;
                    
                break;
            }
        }
        return res;
    }

   /*
    * Cáculo del nivel de riesgo en base a los datos de la anomalía: criticidad, probabilidad y detectabilidad.
    * Los valores se combinan entre ellos mediante una multiplicación. Este es un enfoque común utilizado 
    * en muchos métodos de riesgo [2]. Si el resultado es inferior a 9, tenemos un riesgo bajo. Si el resultado 
    * está entre 9 y 27, tenemos un riesgo medio. Si el valor es superior a 27, tenemos un riesgo alto. El valor del riesgo 
    * global se ha elegido según los siguientes criterios:
    * 
    *  1) Es el mismo nivel de todos los parámetros si pertenecen al mismo nivel (es decir, bajo si L, S y D son bajos).
    *  2) Bajo, si solo hay un parámetro medio y los otros dos parámetros son bajos.
    *  3) Alto, si hay dos o más parámetros configurados en alto o dos parámetros configurados en medio y uno configurado en alto.
    *  4) Medio, en caso contrario.
    * En el caso de que el riesgo calculado sea alto, el dispositivo no se podrá agregar a la red o se deberá prohibir. 
    * En el caso de que el valor del riesgo sea bajo o medio el dispositivo puede unirse o permanecer en la red dependiendo de otros criterios
    */

    public static String nivel_riesgo(String probabilidad, String gravedad, String detectabilidad){
        //Almacenamos el estado actual tras haberlo recuperado de la BD;
        String estado_actual = "";
    
        int calculoRiesgo = Integer.parseInt(probabilidad) * Integer.parseInt(gravedad) * Integer.parseInt(detectabilidad);
        if(calculoRiesgo > 27){
            //El riesgo es alto, el dispositivo debe prohibirse, no podrá ser agregado en la red.
            //Localizar el dispositivo por su ID --> thingID y actualizar su estado a PROHIBIDO
            estado_actual = "prohibido - " + calculoRiesgo;
        }else if(calculoRiesgo <= 27 && calculoRiesgo >= 9){
            //El riesgo es medio, el dispositivo puede unirse/permanecer en la red siempre y cuando el nivel de CONFIANZA cumple los requisitos mínimos
            estado_actual = "pendiente_evaluacion - " + calculoRiesgo;
        }else if(calculoRiesgo < 9){
            //El riesgo es bajo, el dispositivo puede unirse/permanecer en la red siempre y cuando el nivel de CONFIANZA cumple los requisitos mínimos
            estado_actual = "pendiente_evaluacion - " + calculoRiesgo;
        }
    
        
        
        /*
        * Actualización del estado del dispositivo en la base de datos
        */

                
        
        /*
        * Si se requiere --> cálculo de confianza
        * Si no se requiere --> prohibir al dispositivo en la red
        */
        if (estado_actual.equals("pendiente_evaluacion")) {
            //Se calcula la confianza y se actualiza luego el estado en la BD.

            /*La confianza se calcula multiplicando los valores de las distintas variables almacenadas sobre el dispositivo 
            en la BD por el peso/relevancia de cada una. Según el resultado se determina si el dispositivo es confiable o queda
            en cuarentena*/

            estado_actual += " --> Nivel Confianza:" + nivel_confianza + " /1";
            
    
        }
        
        
        /*
        * Actualización del estado del dispositivo en la base de datos
        */


        /*
        * Actualización reputación
        */


        /*
        * Actualización histórico de reputaciones
        */


        /*
        * Comunicar el estado al resto de dispositivos
        */
        
        
        return estado_actual;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //BASE DE DATOS
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // private static String consultaBD(){
    //     final String DB_URL = "jdbc:mysql://localhost/trust_management";
    //     final String USERNAME = "root";
    //     final String PASSWORD = "";

    //     Connection conn = null;
    //     Statement stmt = null;

    //     try {
    //         conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    //         System.out.println("Connected database successfully...");

    //         stmt = conn.createStatement();

    //         String sql = "SELECT * FROM PRUEBA";
    //         ResultSet miResultSet = stmt.executeQuery(sql);

    //         while (miResultSet.next()){
    //             System.out.println(miResultSet.getString(1) + " " + miResultSet.getString(2));
    //         }

    //         System.out.println("Database created successfully...");
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }

    //     return null;
    // }
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void reputacionBD(){
        

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
    
        // //Number of things generated: 4
        // for (int j = 0 ; j < 4 ; j++){
            
        //     //thingID: 0 to 3200
        //     SecureRandom randomThingID = new SecureRandom();
        //     int thingID = randomThingID.nextInt(32001);  	
            
        //     //LastSupervision: 01/01/2017 to 31/12/2017 
        //     long minDayThing = LocalDate.of(2017, 1, 1).toEpochDay();
        //     long maxDayThing = LocalDate.of(2017, 12, 31).toEpochDay();
        //     long randomDayThing = ThreadLocalRandom.current().nextLong(minDayThing, maxDayThing);
        //     LocalDate localDate = LocalDate.ofEpochDay(randomDayThing);
        //     Date lastSupervision = java.sql.Date.valueOf(localDate);
	
            
        //     //NbPastDefaillances: 0 to 30
        //     SecureRandom randomDefaillances = new SecureRandom();
        //     int nbPastDefaillances = randomDefaillances.nextInt(31);
            
        //     //SecurityCertification: True or False
        //     SecureRandom randomCertification = new SecureRandom();
        //     boolean securityCertification = randomCertification.nextBoolean();

        //     // Tipo de dispositivo: Físico || Virtual
        //     String tipo = (randomCertification.nextBoolean()) ? "Fisico" : "Virtual";

        //     // Reputación inicial: 0 a 100 (o cualquier otro rango que elijas)
        //     int reputacionInicial = randomThingID.nextInt(101); // Generar un valor aleatorio entre 0 y 100

        //     // // Crear un array de Strings con los posibles valores del estado
        //     // String[] estados = {"pendiente_evaluacion", "confiable", "cuarentena"};
            
        //     // Random random = new Random();
        //     // int indice = random.nextInt(estados.length);
            
        //     // String estado = estados[indice];

        //     // Constructor de Dispositivo
        //     thingRandom = new Dispositivo(thingID, lastSupervision, nbPastDefaillances, securityCertification, tipo, reputacionInicial, "pendiente_evaluacion");
        //     operatorThings.add(thingRandom);


        // }

        
        //Dispositivo no aleatorio para que coincida con el creado en el cliente
        Dispositivo dispositivoCoincide = new Dispositivo(1, Date.valueOf("2002-01-01"), 1, true, "Fisico", 1, "pendiente_evaluacion");
        operatorThings.add(dispositivoCoincide);
    
        //TRAZA
        //Mostramos los IDs, de los dispositivos creados
        for (Dispositivo dispositivo : operatorThings) {
            System.out.println(dispositivo);
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // CONEXIÓN A BD Y CREAR TABLAS DISPOSITIVOS Y REPUTACIÓN
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        final String DB_URL = "jdbc:mysql://localhost/trust_management";
        final String USERNAME = "root";
        final String PASSWORD = "";

        Connection conn = null;
        Statement stmt = null;

        try {
            
            //CONEXIÓN A LA BASE DE DATOS
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Connected database successfully...");

            // Verificar si la tabla ya existe
            DatabaseMetaData metadata = conn.getMetaData();
            ResultSet tables = metadata.getTables(null, null, "DISPOSITIVOS_INFO", null);

            if (!tables.next()) {
                // La tabla no existe, por lo tanto, podemos proceder a crearla

                // CREAR STATEMENT
                stmt = conn.createStatement();

                // CREACIÓN DE LA TABLA
                String sql = "CREATE TABLE DISPOSITIVOS_INFO " +
                        "(ID_DISPOSITIVO INTEGER not NULL, " +
                        " LAST_SUPERVISION DATE, " + 
                        " NUM_PAST_VULNS INTEGER, " + 
                        " SECURITY_CERTIFICATION BOOLEAN, " +
                        " TIPO VARCHAR(128), " +
                        " REPUTACION INTEGER, "+ 
                        " ESTADO_ACTUAL VARCHAR(128), " +
                        " PRIMARY KEY ( ID_DISPOSITIVO ))"; 

                stmt.executeUpdate(sql);
                System.out.println("Created table in given database..."); 
            } else {
                System.out.println("Table already exists in the database.");
            }


            // Verificar si la tabla REPUTACION ya existe
            ResultSet tablesReputacion = metadata.getTables(null, null, "REPUTACION", null);

            if (!tablesReputacion.next()) {
                // La tabla REPUTACION no existe, por lo tanto, podemos proceder a crearla

                // CREAR STATEMENT
                stmt = conn.createStatement();

                // CREACIÓN DE LA TABLA REPUTACION
                String sqlReputacion = "CREATE TABLE REPUTACION " +
                "(ID_HISTORICO INT AUTOINCREMENT PRIMARY KEY not NULL, " +
                " ID_DISPOSITIVO INTEGER not NULL, " + 
                " TIMESTAMP VARCHAR(128) not NULL, " + 
                " VALOR_REPUTACION INTEGER not NULL)"; 

                stmt.executeUpdate(sqlReputacion);
                System.out.println("Created REPUTACION table in the database..."); 
            } else {
                System.out.println("REPUTACION table already exists in the database.");
            }

            
            
            ///////////////////////////////////////////////// INSERTAR INFORMACIÓN EN LA TABLA ////////////////////////////////////////////////////////////////////////////////////////
            String insertarSQL = "INSERT INTO DISPOSITIVOS_INFO (ID_DISPOSITIVO, LAST_SUPERVISION, NUM_PAST_VULNS, SECURITY_CERTIFICATION, TIPO, REPUTACION, ESTADO_ACTUAL) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertarSQL);

            for (Dispositivo dispositivo : operatorThings) {
                pstmt.setInt(1, dispositivo.getThingID());
                pstmt.setDate(2, dispositivo.getLastSupervision());
                pstmt.setInt(3, dispositivo.getNumPastVulns());
                pstmt.setBoolean(4, dispositivo.getSecurityCertification());
                pstmt.setString(5, dispositivo.getTipo());
                pstmt.setInt(6, dispositivo.getReputacionInicial());
                pstmt.setString(7, dispositivo.getEstadoActual());


                pstmt.executeUpdate();
            }
            
            System.out.println("Datos insertados correctamente...");


            ///////////////////////////////////////////////// RECUPERAR LOS VALORES MÁS ACTUALIZADOS DE LA TABLA DE DISPOSITIVOS ////////////////////////////////////////////////////////////////////////////////////////
            String obtenerValoresSQL = "SELECT ID_DISPOSITIVO, REPUTACION FROM DISPOSITIVOS_INFO";
            Statement stmtObtener = conn.createStatement();
            ResultSet rs = stmtObtener.executeQuery(obtenerValoresSQL);

            Map<Integer, Integer> reputaciones = new HashMap<>();

            while (rs.next()) {
                int dispositivoID = rs.getInt("ID_DISPOSITIVO");
                int reputacion = rs.getInt("REPUTACION");

                // Almacenar el ID del dispositivo y su reputación más reciente en un mapa
                reputaciones.put(dispositivoID, reputacion);
            }

            ///////////////////////////////////////////////// INSERTAR INFORMACIÓN EN LA TABLA DE REPUTACIÓN ////////////////////////////////////////////////////////////////////////////////////////
            String insertarReputacionSQL = "INSERT INTO REPUTACION (ID_DISPOSITIVO, TIMESTAMP, VALOR_REPUTACION) " +
                    "VALUES (?, ?, ?)";
            PreparedStatement pstmtReputacion = conn.prepareStatement(insertarReputacionSQL);

            for (Map.Entry<Integer, Integer> entry : reputaciones.entrySet()) {
                int dispositivoID = entry.getKey();
                int reputacion = entry.getValue();

                // Generar un timestamp actual en Java
                long timestampMillis = System.currentTimeMillis();
                Timestamp timestamp = new Timestamp(timestampMillis);

                pstmtReputacion.setInt(1, dispositivoID);
                pstmtReputacion.setString(2,timestamp.toString());
                pstmtReputacion.setInt(3,reputacion);

                pstmtReputacion.executeUpdate();
            }

            System.out.println("Datos de reputación insertados correctamente...");



        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // SOCKETS
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        int ID_NuevaConfianza = 0;
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

                    while (true) {
                        // Recibir mensaje del cliente
                        String mensajeCliente = entrada.readLine();
                    
                        // Verificar si se ha recibido un mensaje válido
                        if (mensajeCliente == null || mensajeCliente.isEmpty()) {
                            // Si el mensaje es nulo o está vacío, se ha alcanzado el final de la transmisión
                            System.out.println("No hay más mensajes que recibir. Terminando la conexión.");
                            break; // Salir del bucle
                        }
                    
                        // Procesar el mensaje recibido
                        System.out.println("Mensaje del cliente:\n" + mensajeCliente + "\n");
                        
                        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        System.out.println(ID_existente(mensajeCliente, operatorThings));
                        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        
                        // Enviar respuesta al cliente
                        salida.println("Hola, soy el servidor. Recibí tu mensaje");
                        salida.println("Esta es mi respuesta: " + ID_NuevaConfianza);
                    }                    
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


