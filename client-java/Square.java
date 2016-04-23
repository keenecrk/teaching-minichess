

public class Square {

    private int x, y;
    
    public Square(int x, int y) {
        if(x >= 0 && x <= 4 &&
           y >=0 && y <= 5) {
            this.x = x;
            this.y = y;    
        }
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }

}
