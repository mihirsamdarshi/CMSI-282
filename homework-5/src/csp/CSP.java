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
     * @param nMeetings The number of meetings that must be scheduled, indexed from 0 to   n-1
     * @param rangeStart The start date (inclusive) of the domains of each of the n meeting-variables
     * @param rangeEnd The end date (inclusive) of the domains of each of the n meeting-variables
     * @param constraints Date constraints on the meeting times (unary and binary for this assignment)
     * @return A list of dates that satisfies each of the constraints for each of the n meetings,
     *         indexed by the variable they satisfy, or null if no solution exists.
     */
    public static List<LocalDate> solve (int nMeetings, LocalDate rangeStart, LocalDate rangeEnd, 
                Set<DateConstraint> constraints) {

      
      List<DateVar> variables = new ArrayList<DateVar>();

      for (int n = 0; n < nMeetings; n++) {
        variables.add(new DateVar(getDomain(rangeStart, rangeEnd)));
      }

      nodeConsistency(variables, constraints);
      arcConsistency(variables, constraints);
      
      for (DateVar d : variables) {
    	  System.out.println(d.domain.toString());
      }
      
      return recursiveBackTracking(variables, constraints);
    }
    
    
    private static List<LocalDate> recursiveBackTracking (List<DateVar> variables, Set<DateConstraint> constraints) {

        if (variables.get(variables.size() - 1).date != null && validAssignments(variables, constraints)) {
            List<LocalDate> soln = new ArrayList<LocalDate>();
            for (DateVar v : variables) {
                soln.add(v.date);
            }
            return soln;
        }
      
      DateVar unassigned = getUnassigned(variables); 
      if (unassigned == null) return null;
      
      for (LocalDate date : unassigned.domain) {
    	  unassigned.date = date;
          if (validAssignments(variables, constraints)) {
              List<LocalDate> result = recursiveBackTracking(variables, constraints);
              if (result != null) return result;
            }
          unassigned.date = null;
      }
      
    return null;
      
    }
    
    private static DateVar getUnassigned (List<DateVar> variables) {
      for (DateVar v : variables) {
        if (v.date == null) return v;
      }
      return null;
    }

    private static boolean validAssignments (List<DateVar> variables, Set<DateConstraint> constraints) {
	    for (DateConstraint c : constraints) {
	      LocalDate lDate = variables.get(c.L_VAL).date;
	      LocalDate rDate = c.arity() == 1 ? ((UnaryDateConstraint) c).R_VAL
	          : variables.get(((BinaryDateConstraint) c).R_VAL).date;
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
  
  private static List<LocalDate> getDomain (LocalDate start, LocalDate end) {
	List<LocalDate> domain = new ArrayList<LocalDate>();
	while (start.isBefore(end)) {
	  domain.add(start);
	  start = start.plusDays(1);
	}
	domain.add(end);
	return domain;
  }
  
  private static void nodeConsistency(List<DateVar> variables, Set<DateConstraint> constraints) {
    for (DateConstraint c : constraints) {
      if (c.arity() == 1) {
          List<LocalDate> currDomain = variables.get(c.L_VAL).domain;
          List<LocalDate> toRemove = new ArrayList<LocalDate>();  
          for (LocalDate d : currDomain) {
            if (!isConsistent(d, ((UnaryDateConstraint) c).R_VAL, c.OP)) {
              toRemove.add(d);
            }
          }
          currDomain.removeAll(toRemove);
        }
      }
  }
  
    private static void arcConsistency(List<DateVar> variables, Set<DateConstraint> constraints) {
        for (DateConstraint c: constraints) {        
            if (c.arity() == 2) {
                List<LocalDate> tailDomain = variables.get(c.L_VAL).domain;
                List<LocalDate> headDomain = variables.get(((BinaryDateConstraint) c).R_VAL).domain;
                
                removeArcDomain(tailDomain, headDomain, c.OP);
                
                // switch the domains
                tailDomain = variables.get(((BinaryDateConstraint) c).R_VAL).domain;
                headDomain = variables.get(c.L_VAL).domain;
                
                removeArcDomain(tailDomain, headDomain, c.OP);
            }
        }
    }

    private static void removeArcDomain(List<LocalDate> tailDomain, List<LocalDate> headDomain, String op) {
        List<LocalDate> tToRemove = new ArrayList<LocalDate>();  
        
        for (LocalDate tDate : tailDomain) {
        	if (!atLeastOne(tDate, headDomain, op)) {
        		tToRemove.add(tDate);
        	}
        }
        tailDomain.removeAll(tToRemove);
    }
    
    private static boolean atLeastOne (LocalDate date, List<LocalDate> domain, String op)  {
    	for (LocalDate d : domain) {
    		if (isConsistent(date, d, op)) return true;
    	}
    	return false;
    }
    
    private static boolean containsEmptyDomain (List<DateVar> variables) {
    	for (DateVar v : variables) {
    		if (v.domain.isEmpty()) return true;
    	}
    	
    	return false;
    }

    private static class DateVar {
    	LocalDate date;
        List<LocalDate> domain;
        
      
      DateVar(List<LocalDate> domain) {
        this.domain = domain;
      }

    }
    
}