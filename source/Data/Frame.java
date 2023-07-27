package Data;

public class Frame {
    private int x;
    private int y;
    private String spriteTag;

    public Frame(int x, int y, String spriteTag) {
        this.x = x;
        this.y = y;
        this.spriteTag = spriteTag;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getSpriteTag() {
        return spriteTag;
    }
}
