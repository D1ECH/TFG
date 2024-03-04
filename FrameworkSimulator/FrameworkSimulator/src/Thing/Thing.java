package Thing;

import java.time.LocalDate;

public class Thing {

	private int thingID;
	private LocalDate lastSupervision;
	private int nbPastDefaillances;
	private boolean securityCertification;
	
	public Thing(final int thingID, final LocalDate lastSupervision,
			final int nbPastDefaillances, final boolean securityCertification){
		this.thingID = thingID;
		this.lastSupervision = lastSupervision;
		this.nbPastDefaillances = nbPastDefaillances;
		this.securityCertification = securityCertification;
	}
	
	public int getThingID(){
		return thingID;
	}
	
	public LocalDate getLastSupervision(){
		return lastSupervision;
	}
	
	public int getNbPastDefaillances(){
		return nbPastDefaillances;
	}
	
	public boolean getSecurityCertification(){
		return securityCertification;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * 
	@Override
	public String toString() {
		return "Thing [thingID=" + thingID + ", lastSupervision=" + lastSupervision + ", nbPastDefaillances="
				+ nbPastDefaillances + ", securityCertification=" + securityCertification + "]";
	} */
	
	/* (non-Javadoc)
	 * REWRITE
	 * @see java.lang.Object#toString()
	 * */
	@Override
	public String toString() {
		return "" + thingID ;
	}
	
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
		if (!(obj instanceof Thing)) {
			return false;
		}
		Thing other = (Thing) obj;
		if (thingID != other.thingID) {
			return false;
		}
		return true;
	}
	
	
}
