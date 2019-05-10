package csp;

import java.time.LocalDate;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

/**
 * CSP: Calendar Satisfaction Problem Solver
 * Provides a solution for scheduling some n meetings in a given
 * period of time and according to some unary and binary constraints
 * on the dates of each meeting.
 * @author <DiBiagio, Will>
 * @author <Samdarshi, Mihir>
 */
public class CSP {

    /**
     * Public interface for the CSP solver in which the number of meetings,
     * range of allowable dates for each meeting, and constraints on meeting
     * times are specified.
     * This algorithm additionally calls the nodeConsistency and arcConsistency methods
     * which check for nodes for any unary constraints and perform arc consistency on arcs
     * for any binary constraints. It then calls the recursive backtracking algorithm
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

        return recursiveBackTracking(variables, constraints);

    }

    /**
     * Recursive backtracking algorithm that pulls the unassigned variables,
     * then sets a date to it, which then asks if it is valid assignment
     * @param variables List of DateVar that contains the domain of each variable
     * @param constraints Set contains the Date constraints passed through the problem
     * @return ArrayList of LocalDate representing the solution
     */

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

    /**
     * Method which returns the first unassigned variable from the list of variables
     * @param variables List of DateVar that contains the domain of each variable
     * @return DateVar representing the unassigned variable
     */

    private static DateVar getUnassigned (List<DateVar> variables) {
        for (DateVar v : variables) {
            if (v.date == null) return v;
        }
        return null;
    }

    /**
     * Method which returns a boolean based on whether or not the variable in question
     * has already been assigned a value or not
     * @param variables List of DateVar that contains the domain of each variable
     * @param constraints contains the Date constraints passed through the problem
     * @return boolean about the status of the variable assignment
     */

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

    /**
     * Method which determines whether the first and second date parameter satisfy
     * the operator String that is passed through
     * @param lDate is the first LocalDate formatted parameter that is being compared with rDate
     * @param rDate is the second LocalDate formatted parameter that is being compared with lDate
     * @param op is a String operator that has been passed through as a part of the DateConstraint class
     * @return a boolean regarding whether the condition passed through is satisfied or not
     */

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

    /**
     * Method which returns all dates that could possibly be in the domain given a start and end date
     * @param start is the first LocalDate formatted parameter that is the start of the domain
     * @param end is the second LocalDate formatted parameter that is the end of the domain
     * @return a List of all values in between the start and end date, including start and end
     */

    private static List<LocalDate> getDomain (LocalDate start, LocalDate end) {
        List<LocalDate> domain = new ArrayList<LocalDate>();
        while (start.isBefore(end)) {
            domain.add(start);
            start = start.plusDays(1);
        }
        domain.add(end);
        return domain;
    }

    /**
     * Method which removes all values not consistent with any unary constraints of the CSP from the 
     * domain of the variables being passed through. The method is void, as it doesn't have to return any
     * new variables, but rather is only changing existing variables.
     * @param variables List of DateVar that contains the domain of each variable
     * @param constraints contains the Date constraints passed through the problem
     */

    private static void nodeConsistency (List<DateVar> variables, Set<DateConstraint> constraints) {
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

    /**
     * Method which sets up the removal of all values not consistent with binary constraints between an arc.
     * The method first sets the tail to equal the L_VAL, and head domain to R_VAL, then switches the two. The method is 
     * void, as it doesn't have to return any new variables, but rather is only changing existing variables.
     * @param variables List of DateVar that contains the domain of each variable
     * @param constraints contains the Date constraints passed through the problem
     */

    private static void arcConsistency (List<DateVar> variables, Set<DateConstraint> constraints) {
        for (DateConstraint c: constraints) {
            if (c.arity() == 2) {
                
                List<LocalDate> tailDomain = variables.get(c.L_VAL).domain;
                List<LocalDate> headDomain = variables.get(((BinaryDateConstraint) c).R_VAL).domain;

                removeArcDomain(tailDomain, headDomain, c.OP);

                tailDomain = variables.get(((BinaryDateConstraint) c).R_VAL).domain;
                headDomain = variables.get(c.L_VAL).domain;

                removeArcDomain(tailDomain, headDomain, reverseOP(c.OP));
            }
        }
    }

    /**
     * Method removes all values not consistent with binary constraints between an arc. The method iterates through the
     * domain of the list provided for each value of the tail. If the atLeast one boolean is not satisfied, then the method
     * removes the value from the tail.
     * @param tailDomain List containing the entire domain of the tail variable being assessed
     * @param headDomain List containing the entire domain of the head variable being assessed
     * @param op is the operator being passed through
     */

    private static void removeArcDomain (List<LocalDate> tailDomain, List<LocalDate> headDomain, String op) {
        List<LocalDate> tRemove = new ArrayList<LocalDate>();  

        for (LocalDate tDate : tailDomain) {
            if (!atLeastOne(tDate, headDomain, op)) {
                tRemove.add(tDate);
            }
        }
        tailDomain.removeAll(tRemove);
    }

    /**
     * Method returns a boolean based on whether or not at least one value exists in the domain
     * parameter being passed through that satisfies the given operator and date
     * @param date Local Date that is the date being assessed
     * @param domain List containing the entire domain of the head variable being assessed
     * @param op is the operator being passed through
     * @return boolean whether or not at least one value exists in the domain that satisfies the operator
     */

    private static boolean atLeastOne (LocalDate date, List<LocalDate> domain, String op)  {
        for (LocalDate d : domain) {
            if (isConsistent(date, d, op)) return true;
        }
        return false;
    }

    /**
     * Method that reverses the operator when we switch the head and tail during arc consistency checks
     * @param op is the operator being passed through
     * @return boolean whether or not at least one value exists in the domain that satisfies the operator
     */

    private static String reverseOP (String op) {
        switch (op) {
            case "==": return "==";
            case "!=": return "!=";
            case ">":  return "<";
            case "<":  return ">";
            case ">=": return "<=";
            case "<=": return ">=";
        }
        return null;
    }

    /**
     * DateVar: Date Variables
     * Contains the date assignment (null when initialized) and domain for the variables
     */

    private static class DateVar {
        LocalDate date;
        List<LocalDate> domain;

        DateVar(List<LocalDate> domain) {
            this.domain = domain;
        }
    }

}