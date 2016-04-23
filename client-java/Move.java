

public class Move {
    
    private Square fromSquare, toSquare;
    
    public Move(String in) {
        int fromX = getX(in.charAt(0));
        int fromY = getY(in.charAt(1));
        int toX = getX(in.charAt(3));
        int toY = getY(in.charAt(4));
        
        fromSquare = new Square(fromX, fromY);
        toSquare = new Square(toX, toY);
    }
    
    public Square getFromSquare() {
        return fromSquare;
    }
    
    public Square getToSquare() {
        return toSquare;
    }
    
    private int getX(char c) {
        return c - 'a';
    }
    
    private int getY(char c) {
        return 6 - Character.getNumericValue(c);
    }
    
}
