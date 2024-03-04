package Operator;

import Thing.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class OperatorContext {

	private int contextID;
	private LocalDate timeContext;
	private List<Thing> setOfThings = new ArrayList<Thing>();
	
	public OperatorContext(final int contextID, final LocalDate timeContext, final List<Thing> setOfThings){
		this.contextID = contextID;
		this.timeContext = timeContext;
		this.setOfThings = setOfThings;
	}

	/**
	 * @return the time
	 */
	public LocalDate getTimeContext() {
		return timeContext;
	}
	
	/**
	 * @return the contextID
	 */
	public int getContextID() {
		return contextID;
	}
	
	/**
	 * @return the setOfThings
	 */
	public List<Thing> getSetOfThings() {
		return setOfThings;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OperatorContext [contextID=" + contextID + ", timeContext=" + timeContext + ", setOfThings=" + setOfThings + "]";
	}

		

}
