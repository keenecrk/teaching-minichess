import java.util.Vector;


public class State {

    private char[][] board = new char[6][5];
    private int currentTurn = 1;
    private char whoseTurn = 'W';
    private int evalScore = 0;
    private char winner = '?';
    
    public static final int PAWN_VALUE = 100;
    public static final int KNIGHT_VALUE = 300;
    public static final int BISHOP_VALUE = 300;
    public static final int ROOK_VALUE = 500;
    public static final int QUEEN_VALUE = 900;
    public static final int KING_VALUE = 10000;
    
    public static final int CAPTURE_FALSE = 0;
    public static final int CAPTURE_TRUE = 1;
    public static final int CAPTURE_ONLY = 2;
    
    public State() {
        //Empty default constructor
    }
    
    public State(State s) {
        this.currentTurn = s.currentTurn;
        this.whoseTurn = s.whoseTurn;
        this.evalScore = s.evalScore;
        
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 5; j++) {
                this.board[i][j] = s.board[i][j];
            }
        }
    }
    
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
        boolean hasWhiteKing = false;
        boolean hasBlackKing = false;
        winner = '?';
        
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
                char piece = initString.charAt(n++);
                board[i][j] = piece;
                addPiece(piece);
                if(piece == 'k') {
                    hasBlackKing = true;
                }
                else if(piece == 'K') {
                    hasWhiteKing = true;
                }
            }
        }
        
        if(!hasWhiteKing) {
            evalScore = -10000;
            winner = 'B';
        }
        else if (!hasBlackKing) {
            evalScore = 10000;
            winner = 'W';
        }
        else if (currentTurn > 40) {
            winner = '=';
        }
    }
    
    public void init() {
        currentTurn = 1;
        whoseTurn = 'W';
        evalScore = 0;
        winner = '?';
        
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
    
    public int turnsLeft() {
        return 41 - currentTurn;
    }
    
    public char winner() {
        return this.winner;
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
        if(whoseTurn == 'W') {
            return evalScore;
        }
        else {
            return -evalScore;
        }
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
        
        if(!isNothing(to)) {
            capture(to);
        }
        
        board[toY][toX] = from;
        board[fromY][fromX] = '.';
        
        if(from == 'P' && toY == 0) {
            board[toY][toX] = 'Q';
            evalScore += (QUEEN_VALUE - PAWN_VALUE);
        }
        else if(from == 'p' && toY == 5) {
            board[toY][toX] = 'q';
            evalScore -= (QUEEN_VALUE - PAWN_VALUE);
        }
        
        if(whoseTurn == 'W') {
            whoseTurn = 'B';
        }
        else {
            whoseTurn = 'W';
            currentTurn++;
            if(currentTurn > 40 && winner == '?') {
                winner = '=';
                evalScore = 0;
            }
        }
    }
    
    private void capture(char piece) {
        switch(piece) {
            case 'p':
                evalScore += PAWN_VALUE;
                break;
            case 'P':
                evalScore -= PAWN_VALUE;
                break;
            case 'n':
                evalScore += KNIGHT_VALUE;
                break;
            case 'N':
                evalScore -= KNIGHT_VALUE;
                break;
            case 'b':
                evalScore += BISHOP_VALUE;
                break;
            case 'B':
                evalScore -= BISHOP_VALUE;
                break;
            case 'r':
                evalScore += ROOK_VALUE;
                break;
            case 'R':
                evalScore -= ROOK_VALUE;
                break;
            case 'q':
                evalScore += QUEEN_VALUE;
                break;
            case 'Q':
                evalScore -= QUEEN_VALUE;
                break;
            case 'k':
                evalScore = 10000;
                winner = 'W';
                break;
            case 'K':
                evalScore = -10000;
                winner = 'B';
                break;
        }
    }
    
    private void addPiece(char piece) {
        switch(piece) {
            case 'p':
                evalScore -= PAWN_VALUE;
                break;
            case 'P':
                evalScore += PAWN_VALUE;
                break;
            case 'n':
                evalScore -= KNIGHT_VALUE;
                break;
            case 'N':
                evalScore += KNIGHT_VALUE;
                break;
            case 'b':
                evalScore -= BISHOP_VALUE;
                break;
            case 'B':
                evalScore += BISHOP_VALUE;
                break;
            case 'r':
                evalScore -= ROOK_VALUE;
                break;
            case 'R':
                evalScore += ROOK_VALUE;
                break;
            case 'q':
                evalScore -= QUEEN_VALUE;
                break;
            case 'Q':
                evalScore += QUEEN_VALUE;
                break;
            case 'k':
                evalScore -= KING_VALUE;
                break;
            case 'K':
                evalScore += KING_VALUE;
                break;
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
