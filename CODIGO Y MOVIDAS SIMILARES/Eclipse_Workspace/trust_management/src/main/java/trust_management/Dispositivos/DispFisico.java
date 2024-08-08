package Dispositivos;

import java.time.LocalDate;

public class DispFisico extends Dispositivo{


    public DispFisico(int thingID, LocalDate lastSupervision, int numPastVulns, boolean securityCertification, int reputacionInicial) {
        super(thingID, lastSupervision, numPastVulns, securityCertification, "Fisico", reputacionInicial);
        //TODO Auto-generated constructor stub
    }
    
}
