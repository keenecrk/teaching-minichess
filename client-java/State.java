


public class State {

    private char[][] board = new char[6][5];
    private int currentTurn = 1;
    private char whoseTurn = 'W';
    
    public static final int PAWN_VALUE = 100;
    public static final int KNIGHT_VALUE = 300;
    public static final int BISHOP_VALUE = 300;
    public static final int ROOK_VALUE = 500;
    public static final int QUEEN_VALUE = 900;
    public static final int KING_VALUE = 10000;
    
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
        
        return whoWon;
    }
    
    public boolean isNothing(char charPiece) {
        return (charPiece == '.');
    }
    
    public boolean isOwn(char charPiece) {
        return (getOwner(charPiece) == whoseTurn);
    }
    
    public boolean isEnemy(char charPiece) {
        char owner = getOwner(charPiece);
        return (owner != '?' && owner != whoseTurn);
    }
    
    public int eval() {
        int value = 0;
        int white, black;
        
        if(whoseTurn == 'W') {
            white = 1;
            black = -1;
        }
        else {
            white = -1;
            black = 1;
        }
        
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 5; j++) {
                switch (board[i][j]) {
                    case 'p':
                        value += (PAWN_VALUE * black);
                        break;
                    case 'P':
                        value += (PAWN_VALUE * white);
                        break;
                    case 'n':
                        value += (KNIGHT_VALUE * black);
                        break;
                    case 'N':
                        value += (KNIGHT_VALUE * white);
                        break;
                    case 'b':
                        value += (BISHOP_VALUE * black);
                        break;
                    case 'B':
                        value += (BISHOP_VALUE * white);
                        break;
                    case 'r':
                        value += (ROOK_VALUE * black);
                        break;
                    case 'R':
                        value += (ROOK_VALUE * white);
                        break;
                    case 'q':
                        value += (QUEEN_VALUE * black);
                        break;
                    case 'Q':
                        value += (QUEEN_VALUE * white);
                        break;
                    case 'k':
                        value += (KING_VALUE * black);
                        break;
                    case 'K':
                        value += (KING_VALUE * white);
                        break;
                    default:
                        break;
                    
                }
            }
        }
        
        return value;
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
