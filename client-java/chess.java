import java.util.Vector;
import java.util.Stack;
import java.util.Collections;
import java.util.TreeMap;
import java.util.Random;

public class chess {

    private static State state = new State();
    private static Stack<State> statesStack = new Stack<State>();

	public static void reset() {
		// reset the state of the game / your internal variables - note that this function is highly dependent on your implementation
		state.init();
	}
	
	public static String boardGet() {
		// return the state of the game - one example is given below - note that the state has exactly 40 or 41 characters
		return state.print();
	}
	
	public static void boardSet(String strIn) {
		// read the state of the game from the provided argument and set your internal variables accordingly - note that the state has exactly 40 or 41 characters
		state.read(strIn);
	}
	
	public static char winner() {
		// determine the winner of the current state of the game and return '?' or '=' or 'W' or 'B' - note that we are returning a character and not a string
		
		return state.winner();
	}
	
	public static boolean isValid(int intX, int intY) {
		if (intX < 0) {
			return false;
			
		} else if (intX > 4) {
			return false;
			
		}
		
		if (intY < 0) {
			return false;
			
		} else if (intY > 5) {
			return false;
			
		}
		
		return true;
	}
	
	public static boolean isEnemy(char charPiece) {
		// with reference to the state of the game, return whether the provided argument is a piece from the side not on move - note that we could but should not use the other is() functions in here but probably
		
		return state.isEnemy(charPiece);
	}
	
	public static boolean isOwn(char charPiece) {
		// with reference to the state of the game, return whether the provided argument is a piece from the side on move - note that we could but should not use the other is() functions in here but probably
		
		return state.isOwn(charPiece);
	}
	
	public static boolean isNothing(char charPiece) {
		// return whether the provided argument is not a piece / is an empty field - note that we could but should not use the other is() functions in here but probably
		
		return state.isNothing(charPiece);
	}
	
	public static int eval() {
		// with reference to the state of the game, return the the evaluation score of the side on move - note that positive means an advantage while negative means a disadvantage
		
		return state.eval();
	}
	
	public static Vector<String> moves() {
		// with reference to the state of the game and return the possible moves - one example is given below - note that a move has exactly 6 characters
		
		return state.moveGen();
	}
	
	public static Vector<String> movesShuffled() {
		// with reference to the state of the game, determine the possible moves and shuffle them before returning them - note that you can call the chess.moves() function in here
		
		Vector<String> moves = moves();
		Collections.shuffle(moves, new Random());
		return moves;
	}
	
	public static Vector<String> movesEvaluated() {
		// with reference to the state of the game, determine the possible moves and sort them in order of an increasing evaluation score before returning them - note that you can call the chess.movesShuffled() function in here
		
		Vector<String> moves = movesShuffled();
		TreeMap<Integer, Vector<String>> sortedMoves = new TreeMap<Integer, Vector<String>>();
		
		for(String move : moves) {
		    move(move);
		    int score = eval();
		    if(sortedMoves.containsKey(score)) {
		        sortedMoves.get(score).add(move);
		    }
		    else {
		        Vector<String> values = new Vector<String>();
		        values.add(move);
		        sortedMoves.put(score, values);
		    }
		    undo();
		}
		
		moves.clear();
		
		for(Vector<String> v : sortedMoves.values()) {
		    moves.addAll(v);
		}
		
		return moves;
	}
	
	public static void move(String charIn) {
		// perform the supplied move (for example "a5-a4\n") and update the state of the game / your internal variables accordingly - note that it advised to do a sanity check of the supplied move
		State newState = new State();
		newState.read(state.print());
		statesStack.push(newState);
		state.move(charIn);
	}
	
	public static String moveRandom() {
		// perform a random move and return it - one example output is given below - note that you can call the chess.movesShuffled() function as well as the chess.move() function in here
		
		String move = movesShuffled().firstElement();
		move(move);
		return move;
	}
	
	public static String moveGreedy() {
		// perform a greedy move and return it - one example output is given below - note that you can call the chess.movesEvaluated() function as well as the chess.move() function in here
		
		String move = movesEvaluated().firstElement();
		move(move);
		return move;
	}
	
	public static String moveNegamax(int intDepth, int intDuration) {
		// perform a negamax move and return it - one example output is given below - note that you can call the the other functions in here
		
		Vector<String> moves = movesShuffled();
		String best = "";
		int score = -100000;
		int temp = 0;
		
		for(String move : moves) {
		    move(move);
		    temp = -negamax(intDepth -1);
		    undo();
		    
		    if(temp > score) {
		        best = move;
		        score = temp;
		    }
		}
		
		move(best);
		return best;
	}
	
	public static String moveAlphabeta(int intDepth, int intDuration) {
		// perform a alphabeta move and return it - one example output is given below - note that you can call the the other functions in here
		
		return "a2-a3\n";
	}
	
	public static void undo() {
		// undo the last move and update the state of the game / your internal variables accordingly - note that you need to maintain an internal variable that keeps track of the previous history for this
		state = statesStack.pop();
	}
	
	private static int negamax(int depth) {
	    if((depth == 0) || (winner() != '?')) {
	        return eval();
	    }
	    
	    int score = -100000;
	    
	    Vector<String> moves = moves();
	    
	    for(String move : moves) {
	        move(move);
	        score = Math.max(score, -negamax(depth - 1));
	        undo();
	    }
	    
	    return score;
	}
}
