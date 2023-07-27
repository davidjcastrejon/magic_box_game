package Data;

public class Click {
    // Fields
    private int x;
    private int y;
    private int button;

    // Defined constants for mouse buttons in Java
    public static final int LEFT_BUTTON = 1;
    public static final int MIDDLE_BUTTON = 2;
    public static final int RIGHT_BUTTON = 3;
    public static final int BACK_BUTTON = 4;
    public static final int FORWARD_BUTTON = 5;

    // Constructor
    public Click(int x, int y, int button) {
        this.x = x;
        this.y = y;
        this.button = button;
    }

    // Methods
    public int getX() {
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getButton() {
        return this.button;
    }

    public String toString() {
        return "x: " + x + ", y: " + y + ", button: " + button;
    }
}
