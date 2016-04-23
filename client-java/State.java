import java.util.Vector;


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
    
    public static final int CAPTURE_FALSE = 0;
    public static final int CAPTURE_TRUE = 1;
    public static final int CAPTURE_ONLY = 2;
    
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
    
    public void move(String in) {
        move(new Move(in));
    }
    
    public void move(Move m) {
        int fromX = m.getFromSquare().getX();
        int fromY = m.getFromSquare().getY();
        int toX = m.getToSquare().getX();
        int toY = m.getToSquare().getY();
        
        char from = board[fromY][fromX];
        char to = board[toY][toX];
        
        if(!isOwn(from) || isOwn(to)) {
            //throw exception
        }
        
        board[toY][toX] = from;
        board[fromY][fromX] = '.';
        
        if(from == 'P' && toY == 0) {
            board[toY][toX] = 'Q';
        }
        else if(from == 'p' && toY == 5) {
            board[toY][toX] = 'q';
        }
        
        if(whoseTurn == 'W') {
            whoseTurn = 'B';
        }
        else {
            whoseTurn = 'W';
            currentTurn++;
        }
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
    
    public Vector<String> moveGen() {
        Vector<String> moves = new Vector<String>();
    
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 5; j++) {
                char piece = board[i][j];
                if(isOwn(piece)) {
                    moves.addAll(moveList(j, i, piece));
                }
            }
        }
        
        return moves;
    }
    
    private static String getMoveString(int fromX, int fromY, int toX, int toY) {
        StringBuffer sb = new StringBuffer(6);
        
        sb.append(xToChar(fromX));
        sb.append(yToChar(fromY));
        sb.append('-');
        sb.append(xToChar(toX));
        sb.append(yToChar(toY));
        sb.append('\n');
        
        return sb.toString();
    }
    
    private static char xToChar(int x) {
        return (char)('a' + x);
    }
    
    private static char yToChar(int y) {
        return (char)('0' + (6 - y));
    }
    
    private Vector<String> moveScan(int x0, int y0, int dx, int dy,
                                    int capture, boolean stopShort) {
        Vector<String> moves = new Vector<String>();
        int x = x0;
        int y = y0;
        boolean stop = stopShort;
        
        do {
            x += dx;
            y += dy;
            if(!chess.isValid(x, y)) {
                break;
            } 
            char piece = board[y][x];
            if(!isNothing(piece)) {
                if(isOwn(piece)) {
                    break;
                }
                if(capture == CAPTURE_FALSE) {
                    break;
                }
                stop = true;
            }
            else if(capture == CAPTURE_ONLY) {
                break;
            }
            
            moves.add(getMoveString(x0, y0, x, y));               
            
        } while(!stop);
        
        return moves;
    }
    
    private Vector<String> symScan(int x, int y, int dx, int dy, int capture, boolean stopShort) {
        Vector<String> moves = new Vector<String>();
        int ddx = dx;
        int ddy = dy;
        int temp;
        
        for(int i = 0; i < 4; i++) {
            moves.addAll(moveScan(x, y, ddx, ddy, capture, stopShort));
            temp = ddy;
            ddy = ddx;
            ddx = temp;
            ddy *= -1;
        }
        return moves;
    }
    
    private Vector<String> moveList(int x, int y, char piece) {
        Vector<String> moves = new Vector<String>();
        int capture = CAPTURE_TRUE;
        boolean stopShort = false;
        int dir = -1;
        
        switch (piece) {
            case 'k':
            case 'K':
                stopShort = true; 
            case 'q':
            case 'Q':
                moves.addAll(symScan(x, y, 0, 1, capture, stopShort));
                moves.addAll(symScan(x, y, 1, 1, capture, stopShort));
                break;
            case 'b':
            case 'B':
                stopShort = true;
                capture = CAPTURE_FALSE;
            case 'r':
            case 'R':
                moves.addAll(symScan(x, y, 0, 1, capture, stopShort));
                if(piece == 'b' || piece == 'B') {
                    capture = CAPTURE_TRUE;
                    stopShort = false;
                    moves.addAll(symScan(x, y, 1, 1, capture, stopShort));
                }
                break;
            case 'n':
            case 'N':
                moves.addAll(symScan(x, y, 1, 2, capture, true));
                moves.addAll(symScan(x, y, -1, 2, capture, true));
                break;
            case 'p':
                dir = 1;
            case 'P':
                moves.addAll(moveScan(x, y, -1, dir, CAPTURE_ONLY, true));
                moves.addAll(moveScan(x, y, 1, dir, CAPTURE_ONLY, true));
                moves.addAll(moveScan(x, y, 0, dir, CAPTURE_FALSE, true));
        }
        
        return moves;
    }

}
