package Dispositivos;

import java.sql.Date;
import java.time.LocalDate;

public class DispFisico extends Dispositivo{

    public DispFisico(int thingID, Date lastSupervision, int numPastVulns, boolean securityCertification, String tipo,
            int reputacionInicial, String estadoActual) {
        super(thingID, lastSupervision, numPastVulns, securityCertification, tipo, reputacionInicial, estadoActual);
        //TODO Auto-generated constructor stub
    }
    
}
