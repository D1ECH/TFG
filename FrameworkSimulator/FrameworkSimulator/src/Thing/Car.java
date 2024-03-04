package Thing;

import java.time.LocalDate;

public class Car extends Thing {

	private float petrolLevel;
	
	public Car (final int idThing, final LocalDate lastSupervision,
			final int nbPastDefaillances, final boolean securityCertification, final float petrolLevel){
		super(idThing, lastSupervision, nbPastDefaillances, securityCertification);
		
		this.petrolLevel = petrolLevel;
	}
		
	public float getPetrolLevel(){
		return petrolLevel;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Car [petrolLevel=" + petrolLevel + "]";
	}
}
