package pathfinder.informed;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Maze Pathfinding algorithm that implements a basic, uninformed, breadth-first tree search.
 * @author <DiBiagio, Will>
 * @author <Samdarshi, Mihir>
 */
public class Pathfinder {

    /**
     * Given a MazeProblem, which specifies the actions and transitions available in the
     * search, returns a solution to the problem as a sequence of actions that leads from
     * the initial to a goal state. The loop's first pass attempts to find the key state.
     * It does this by first establishing a PriorityQueue for the frontier as well as a
     * HashSet of already visited states, for memoization. The first SearchTreeNode initialized
     * on the frontier estimates future cost using a getDistance function. If the node is
     * the solution it returns the ArrayList containing the path. Otherwise, it creates a
     * Map of all possible transitions and adds all transitions to the PriorityQueue in
     * order of the least expected heuristic cost. It then repeats once at the Key state
     * to search its way to the goal state.
     *
     * @param problem A MazeProblem that specifies the maze, actions, transitions.
     * @return An ArrayList of Strings representing actions that lead from the initial to
     * the goal state, of the format: ["R", "R", "L", ...]
     */
	public static ArrayList<String> solve (MazeProblem problem) {

		if (problem.KEY_STATE == null) {
			return null;
		}

		boolean foundKey = false;

		ArrayList<String> pathSoln = new ArrayList<>();

		for (int i = 0; i < 2; i++) {
			MazeState startingState = foundKey ? problem.KEY_STATE : problem.INITIAL_STATE;
			PriorityQueue<SearchTreeNode> frontier = new PriorityQueue<>();
			HashSet<MazeState> visitedStates = new HashSet<>();

            frontier.add(new SearchTreeNode(startingState, null, null, 0,
            		problem.getDistance(startingState, foundKey)));

	        while (!frontier.isEmpty()) {
	            SearchTreeNode expanding = frontier.poll();

	           if (problem.isObjective(expanding.state, foundKey)) {
	        	   pathSoln.addAll(getPath(expanding));

	        	   if (foundKey) {
	        		   return pathSoln;
	        	   } else {
	        		   foundKey = true;
		               break;
	        	   }
	           }

	            Map<String, MazeState> transitions = problem.getTransitions(expanding.state);
	            for (Map.Entry<String, MazeState> transition : transitions.entrySet()) {
	            	if (visitedStates.add(transition.getValue())) {
	            		int pastCost = expanding.pastCost + problem.getCost(transition.getValue());
	            		int futureCost = problem.getDistance(transition.getValue(), foundKey);
	            		frontier.add(new SearchTreeNode(transition.getValue(),
	            				transition.getKey(), expanding, pastCost, futureCost));
	            	}
	            }
	        }
		}

        return null;
    }

    /**
     * Given a leaf node in the search tree (a goal), returns a solution by traversing
     * up the search tree, collecting actions along the way, until reaching the root
     *
     * @param last SearchTreeNode to start the upward traversal at (a goal node)
     * @return ArrayList sequence of actions; solution of format ["U", "R", "U", ...]
     */
    private static ArrayList<String> getPath (SearchTreeNode last) {
        ArrayList<String> result = new ArrayList<>();
        for (SearchTreeNode current = last; current.parent != null; current = current.parent) {
            result.add(current.action);
        }
        Collections.reverse(result);
        return result;
    }

}

/**
 * SearchTreeNode that is used in the Search algorithm to construct the Search
 * tree.
 */
class SearchTreeNode implements Comparable<SearchTreeNode> {

    MazeState state;
    String action;
    SearchTreeNode parent;
    int pastCost;
    int futureCost;
    int heuristic;

    /**
     * Constructs a new SearchTreeNode to be used in the Search Tree.
     *
     * @param state The MazeState (col, row) that this node represents.
     * @param action The action that *led to* this state / node.
     * @param parent Reference to parent SearchTreeNode in the Search Tree.
     */
    SearchTreeNode (MazeState state, String action, SearchTreeNode parent, int pastCost, int futureCost) {
        this.state = state;
        this.action = action;
        this.parent = parent;
        this.pastCost = pastCost;
        this.futureCost = futureCost;
        this.heuristic = pastCost + futureCost;
    }

    @Override
    public int compareTo(SearchTreeNode node) {
        if(this.heuristic > node.heuristic) {
            return 1;
        } else if (this.heuristic < node.heuristic) {
            return -1;
        } else {
            return 0;
        }
    }

}
