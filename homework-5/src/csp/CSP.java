package csp;

import java.time.LocalDate;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
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
     * @param nMeetings The number of meetings that must be scheduled, indexed from 0 to	 n-1
     * @param rangeStart The start date (inclusive) of the domains of each of the n meeting-variables
     * @param rangeEnd The end date (inclusive) of the domains of each of the n meeting-variables
     * @param constraints Date constraints on the meeting times (unary and binary for this assignment)
     * @return A list of dates that satisfies each of the constraints for each of the n meetings,
     *         indexed by the variable they satisfy, or null if no solution exists.
     */
    public static List<LocalDate> solve (int nMeetings, LocalDate rangeStart, LocalDate rangeEnd, 
    						Set<DateConstraint> constraints) {
    	
    	
    	LinkedHashSet<LocalDate> fullDomain = getDomain(rangeStart, rangeEnd);
    	
    	HashMap<Integer, DateVar> variables = new HashMap<Integer, DateVar>();	
    	for (int n = 0; n < nMeetings; n++) {
    		variables.put(n, new DateVar(n, fullDomain));
    	}

    	
    	return recursiveBackTracking(variables, constraints, new HashMap<>());
    }
    
    
    private static List<LocalDate> recursiveBackTracking (HashMap<Integer, DateVar> variables, 
    							Set<DateConstraint> constraints, 
    							HashMap<Integer, LocalDate> assignments) {

        if (variables.size() == assignments.size() && validAssignments(assignments, constraints)) {
        	return new ArrayList<>(assignments.values());
        }
    	
    	DateVar unassigned = getUnassigned(variables, assignments);	
    	if (unassigned == null) return null;
    	
    	for (LocalDate date : unassigned.domain) {
    		assignments.put(unassigned.meeting, date);
    		if (validAssignments(assignments, constraints)) {
    			List<LocalDate> result = recursiveBackTracking(variables, constraints, assignments);
    			if (result != null) return result;	
    		}
			assignments.remove(unassigned.meeting);	
    	}
    	
		return null;
    	
    }
    
    private static DateVar getUnassigned (HashMap<Integer, DateVar> variables, 
											HashMap<Integer, LocalDate> assignments) {
    	for (int n : variables.keySet()) {
    		if (!assignments.containsKey(n)) return variables.get(n);
    	}
    	return null;
    }

    private static boolean validAssignments (HashMap<Integer, LocalDate> assignments, Set<DateConstraint> constraints) {
		for (DateConstraint c : constraints) {
			LocalDate lDate = assignments.get(c.L_VAL);
			LocalDate rDate = c.arity() == 1 ? ((UnaryDateConstraint) c).R_VAL
					: assignments.get(((BinaryDateConstraint) c).R_VAL);
			if (lDate == null || rDate == null) continue;
			if (!isConsistent(lDate, rDate, c.OP)) return false;
		}
		return true;
    }
    
	private static boolean isConsistent (LocalDate lDate, LocalDate rDate, String op) {
		switch (op) {
			case "==": if (lDate.isEqual(rDate))  return true; break;
			case "!=": if (!lDate.isEqual(rDate)) return true; break;	
			case ">":  if (lDate.isAfter(rDate))  return true; break;
			case "<":  if (lDate.isBefore(rDate)) return true; break;
			case ">=": if (lDate.isAfter(rDate) || lDate.isEqual(rDate))  return true; break;
			case "<=": if (lDate.isBefore(rDate) || lDate.isEqual(rDate)) return true; break;
		}
		return false;
	}
	
	private static LinkedHashSet<LocalDate> getDomain (LocalDate start, LocalDate end) {
		LinkedHashSet<LocalDate> domain = new LinkedHashSet<LocalDate>();
        while (start.isBefore(end)) {
        	domain.add(start);
        	start = start.plusDays(1);
        }
        domain.add(end);
		return domain;
	}
	
	private static void nodeConsistency(HashMap<Integer, DateVar> variables, Set<DateConstraint> constraints) {
		for (DateConstraint c : constraints) {
			if (c.arity() != 1) {
				break;
			}
			LinkedHashSet<LocalDate> currDomain = variables.get(c.L_VAL).domain;
			
			for (LocalDate d : currDomain) {
				if (!isConsistent(d, ((UnaryDateConstraint) c).R_VAL, c.OP)) {
					currDomain.remove(d);
				}
			}

		}
	}

    private static class DateVar {
    	int meeting;
    	LinkedHashSet<LocalDate> domain;
    	
    	DateVar(int meeting, LinkedHashSet<LocalDate> domain) {
    		this.meeting = meeting;
    		this.domain = domain;
    	}

    }
    
}
