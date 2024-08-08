package Thing;

import java.time.LocalDate;

public class Smartwatch extends Thing {

	public Smartwatch (final int idThing, final LocalDate lastSupervision,
			final int nbPastDefaillances, final boolean securityCertification, final float petrolLevel){
		super(idThing, lastSupervision, nbPastDefaillances, securityCertification);
	}
}
