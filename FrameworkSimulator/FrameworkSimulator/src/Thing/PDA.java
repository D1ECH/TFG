package Thing;

import java.time.LocalDate;

public class PDA extends Thing {

	public PDA (final int idThing, final LocalDate lastSupervision,
			final int nbPastDefaillances, final boolean securityCertification, final float petrolLevel){
		super(idThing, lastSupervision, nbPastDefaillances, securityCertification);
	}
	
}
