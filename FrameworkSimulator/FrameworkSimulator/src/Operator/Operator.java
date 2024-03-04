package Operator;


import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;


public class Operator {
	private int operatorID;
	private String firstName;
	private String lastName;
	private String professionalProfile;
	private int nbWorkedHours;
	private int nbTasksCompleted;
	private float globalReputation;
	private float[] locationOperator = new float[2];
	private int yearsExperience;
	private boolean taskAssignation;
	private List<OperatorContext> operatorContexts = new ArrayList<OperatorContext>();
	private int nbContexts;
	
	
	public Operator(final int operatorID, final String firstName, final String lastName,
			final String professionalProfile, final int nbWorkedHours, final int nbTasksCompleted,
			final float globalReputation, final float[] locationOperator, final int yearsExperience, 
			final boolean taskAssignation, final int nbContexts, final List<OperatorContext> operatorContexts){
		
		this.operatorID = operatorID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.professionalProfile = professionalProfile;
		this.nbWorkedHours = nbWorkedHours;
		this.nbTasksCompleted = nbTasksCompleted;
		this.globalReputation = globalReputation;
		this.yearsExperience = yearsExperience;
		this.taskAssignation = taskAssignation;		
		this.locationOperator = locationOperator;
		this.nbContexts = nbContexts;
		this.operatorContexts = operatorContexts;
	}

	/**
	 * @return the idOperator
	 */
	public int getOperatorID() {
		return operatorID;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the professionalProfile
	 */
	public String getProfessionalProfile() {
		return professionalProfile;
	}

	/**
	 * @return the nbWorkedHours
	 */
	public int getNbWorkedHours() {
		return nbWorkedHours;
	}

	/**
	 * @return the nbTasksCompleted
	 */
	public int getNbTasksCompleted() {
		return nbTasksCompleted;
	}

	/**
	 * @return the reputation
	 */
	public float getGlobalReputation() {
		return globalReputation;
	}

	/**
	 * @return the locationOperator
	 */
	public String getLocationOperator() {
		return Arrays.toString(locationOperator);
	}

	/**
	 * @return the yearsExperience
	 */
	public int getYearsExperience() {
		return yearsExperience;
	}

	/**
	 * @return the taskAssignation
	 */
	public boolean isTaskAssignation() {
		return taskAssignation;
	}
	
	/**
	 * @return the time
	 */
	public int getNbContexts() {
		return nbContexts;
	}
	
	/**
	 * @param globalReputation the globalReputation to set
	 */
	public void setGlobalReputation(float globalReputation) {
		this.globalReputation = globalReputation;
	}

	/**
	 * @param taskAssignation the taskAssignation to set
	 */
	public void setTaskAssignation(boolean taskAssignation) {
		this.taskAssignation = taskAssignation;
	}
	
	
	
	@Override
	public String toString() {
		return "Operator [operatorID=" + operatorID + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", professionalProfile=" + professionalProfile + ", nbWorkedHours=" + nbWorkedHours
				+ ", nbTasksCompleted=" + nbTasksCompleted + ", globalReputation=" + globalReputation + ", locationOperator="
				+ Arrays.toString(locationOperator) + ", yearsExperience=" + yearsExperience + ", taskAssignation="
				+ taskAssignation + ", nbContexts=" + nbContexts + ", operatorContexts = " + operatorContexts + "]";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + operatorID;
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
		if (!(obj instanceof Operator)) {
			return false;
		}
		Operator other = (Operator) obj;
		if (operatorID != other.operatorID) {
			return false;
		}
		return true;
	}	
	
}
