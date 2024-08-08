package Task;

import java.time.LocalDate;
import java.util.Arrays;

public class Task {
	private float estimatedDuration;
	private float estimatedComplexity;
	private int criticality;
	private String preferredProfile;
	private float[] locationTask = new float[2];
	private LocalDate timeTask;
	
	public Task(final float estimatedDuration, final float estimatedComplexity, final int criticality,
			final String preferredProfile, final float[] locationTask, final LocalDate timeTask){
		this.estimatedDuration = estimatedDuration;
		this.estimatedComplexity = estimatedComplexity;
		this.criticality = criticality;
		this.preferredProfile = preferredProfile;
		this.locationTask = locationTask;
		this.timeTask = timeTask;
	}

	/**
	 * @return the estimatedDuration
	 */
	public float getEstimatedDuration() {
		return estimatedDuration;
	}

	/**
	 * @return the estimatedComplexity
	 */
	public float getEstimatedComplexity() {
		return estimatedComplexity;
	}

	/**
	 * @return the criticality
	 */
	public float getCriticality() {
		return criticality;
	}

	/**
	 * @return the preferredProfile
	 */
	public String getPreferredProfile() {
		return preferredProfile;
	}
	
	/**
	 * @return the locationTask
	 */
	public String getLocationTask() {
		return Arrays.toString(locationTask);
	}
	
	/**
	 * @return the timeTask
	 */
	public LocalDate getTimeTask() {
		return timeTask;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Task [estimatedDuration=" + estimatedDuration + ", estimatedComplexity=" + estimatedComplexity
				+ ", criticality=" + criticality + ", preferredProfile=" + preferredProfile + ", locationTask="
				+ Arrays.toString(locationTask) + "]";
	}
	
}
