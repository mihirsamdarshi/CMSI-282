package csp;

import java.time.LocalDate;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * CSP: Calendar Satisfaction Problem Solver
 * Provides a solution for scheduling some n meetings in a given
 * period of time and according to some unary and binary constraints
 * on the dates of each meeting.
 *  @author <DiBiagio, Will>
 * @author <Samdarshi, Mihir>
 */
public class CSP {

    /**
     * Public interface for the CSP solver in which the number of meetings,
     * range of allowable dates for each meeting, and constraints on meeting
     * times are specified.
     * @param nMeetings The number of meetings that must be scheduled, indexed from 0 to n-1
     * @param rangeStart The start date (inclusive) of the domains of each of the n meeting-variables
     * @param rangeEnd The end date (inclusive) of the domains of each of the n meeting-variables
     * @param constraints Date constraints on the meeting times (unary and binary for this assignment)
     * @return A list of dates that satisfies each of the constraints for each of the n meetings,
     *         indexed by the variable they satisfy, or null if no solution exists.
     */
    public static List<LocalDate> solve (int nMeetings, LocalDate rangeStart, LocalDate rangeEnd, 
    						Set<DateConstraint> constraints) {
    	
    	HashMap<Integer, DateVar> variables = new HashMap<Integer, DateVar>();	
    	for (int n = 0; n < nMeetings; n++) {
    		variables.put(n, new DateVar(n, rangeStart, rangeEnd));
    	}
    	
    	
    	//List<LocalDate> soln = backTracking(variables, constraints, new HashMap<>());
    	
        throw new UnsupportedOperationException();
    }
    
    
    private static List<LocalDate> backTracking (HashMap<Integer, DateVar> variables, 
    							Set<DateConstraint> contstraints, 
    							HashMap<Integer, LocalDate> assignments) {
    	
    	if (assignments.size() == variables.size()) return new ArrayList<>(assignments.values());	
    	DateVar unassigned = null;	
    	for (int n : variables.keySet()) {
    		if (!assignments.containsKey(n)) {
    			unassigned = variables.get(n);
    			break;
    		}
    	}
    	if (unassigned == null) return null;
    	for (LocalDate date : unassigned.domain) {
    		if (true) {
    			assignments.put(unassigned.meeting, date);
    			List<LocalDate> result = backTracking(variables, contstraints, assignments);
    			if (result != null) return result;
    			assignments.remove(unassigned.meeting);
    		}
    	}
    	
		return null;
    	
    }
    
    private static boolean isValidAssignment(HashMap<Integer, DateVar> variables, 
    						Set<DateConstraint> contstraints) {
    	
    	return false;
    }
    

    
    private static class DateVar {
    	int meeting;
    	Set<LocalDate> domain;
    	
    	DateVar(int meeting, LocalDate rangeStart, LocalDate rangeEnd) {
    		this.meeting = meeting;
        	while(!rangeStart.equals(rangeEnd)) {
        		this.domain.add(rangeStart);
        		rangeStart.plusDays(1);
        	}
        	this.domain.add(rangeEnd);
    	}

    }
    
}
