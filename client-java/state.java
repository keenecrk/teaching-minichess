


public class state {

    private char[][] board = new char[6][5];
    private int currentTurn = 1;
    private char whoseTurn = 'W';
    
    public String print() { 
        String outString = "";
        outString += Integer.toString(currentTurn) + " ";
        outString += whoseTurn + "\n";
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 5; j++) {
                outString += board[i][j];
            }
        outString += "\n";
        }
        
        return outString;    
    }
    
    public void read(String initString) {
        int n = initString.indexOf(" ");
        currentTurn = Integer.parseInt(initString.substring(0, n));
        n++;
        whoseTurn = initString.charAt(n);
        n++;
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 5; j++) {
                if(initString.charAt(n) == '\n') {
                    n++;
                }
                board[i][j] = initString.charAt(n++);
            }
        }
    }
    
    public void init() {
        currentTurn = 1;
        whoseTurn = 'W';
        
        char[][] newBoard = {
            {'k', 'q', 'b', 'n', 'r'},
            {'p', 'p', 'p', 'p', 'p'},
            {'.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.'},
            {'P', 'P', 'P', 'P', 'P'},
            {'R', 'N', 'B', 'Q', 'K'}
        };
        
        board = newBoard;
    }
    
    public char winner() {
        char whoWon = '?';
        if(currentTurn > 40) {
            whoWon = '=';
        }
        else {
            boolean whiteFound = false;
            boolean blackFound = false;
            for(int i = 0; i < 6; i++) {
                for(int j = 0; j < 5; j++) {
                    char piece = board[i][j];
                    if(piece == 'k') {
                        blackFound = true;
                    }
                    else if(piece == 'K') {
                        whiteFound = true;
                    }
                }
            }
            if(!blackFound) {
                whoWon = 'W';
            }
            else if(!whiteFound) {
                whoWon = 'B';
            }
        }
        
        return whoWon;
    }
    
    public boolean isOwn(char charPiece) {
        return (getOwner(charPiece) == whoseTurn);
    }
    
    public boolean isEnemy(char charPiece) {
        char owner = getOwner(charPiece);
        return (owner != '?' && owner != whoseTurn);
    }
    
    private char getOwner(char charPiece) {
        char owner = '?';
        if(charPiece >= 'a' && charPiece <= 'z') {
            owner = 'B';
        }
        else if(charPiece >= 'A' && charPiece <= 'Z') {
            owner = 'W';
        }
        
        return owner;
    }

}
