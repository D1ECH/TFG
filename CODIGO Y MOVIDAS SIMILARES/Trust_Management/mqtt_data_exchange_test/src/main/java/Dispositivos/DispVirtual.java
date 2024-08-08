package Dispositivos;

import java.time.LocalDate;

public class DispVirtual extends Dispositivo{

    public DispVirtual(int thingID, LocalDate lastSupervision, int numPastVulns, boolean securityCertification, int reputacionInicial) {
        super(thingID, lastSupervision, numPastVulns, securityCertification, "Virtual", reputacionInicial);
        //TODO Auto-generated constructor stub
    }
    
}
