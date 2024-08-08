package Dispositivos;
import java.sql.Date;
import java.time.LocalDate;

public class Dispositivo {
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// VARIABLES
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private int thingID;
	private Date lastSupervision;
	private int numPastVulns;
	private boolean securityCertification;
	private String tipo;
	private int reputacionInicial;
	private String estadoActual;
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// CONSTRUCTOR
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Dispositivo(final int thingID, final Date lastSupervision,
			final int numPastVulns, final boolean securityCertification, final String tipo, final int reputacionInicial, final String estadoActual){
		this.thingID = thingID;
		this.lastSupervision = lastSupervision;
		this.numPastVulns = numPastVulns;
		this.securityCertification = securityCertification;
		this.tipo = tipo;
		this.reputacionInicial = reputacionInicial;
		this.estadoActual = estadoActual;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// GETTERS Y SETTERS
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public int getThingID(){
		return thingID;
	}
	
	public Date getLastSupervision(){
		return lastSupervision;
	}
	
	public int getNumPastVulns(){
		return numPastVulns;
	}
	
	public boolean getSecurityCertification(){
		return securityCertification;
	}

	public String getTipo(){
		return tipo;
	}

	public int getReputacionInicial(){
		return reputacionInicial;
	}

	public String getEstadoActual(){
		return estadoActual;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// TO-STRING
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * 
	@Override
	public String toString() {
		return "Thing [thingID=" + thingID + ", lastSupervision=" + lastSupervision + ", numPastVulns="
				+ numPastVulns + ", securityCertification=" + securityCertification + "]";
	} */
	
	/* (non-Javadoc)
	 * REWRITE
	 * @see java.lang.Object#toString()
	 * */
	@Override
	public String toString() {
		return "" + thingID + " --> [" + lastSupervision.toString() + " | " + numPastVulns + " | " + securityCertification + " | " + tipo + " | " + reputacionInicial + " | " + estadoActual + "]";
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// HASHCODE
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + thingID;
		return result;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// EQUALS
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Dispositivo)) {
			return false;
		}
		Dispositivo other = (Dispositivo) obj;
		if (thingID != other.thingID) {
			return false;
		}
		return true;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
}
