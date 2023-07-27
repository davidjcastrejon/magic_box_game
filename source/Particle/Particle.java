package Particle;
import timer.*;
import Data.*;

public class Particle {
    // Fields
    private int x, y;
    private String particleSpriteTag;
    private int lifecycle;
    private int age;
    private int xMove, yMove;
    private stopWatchX timer;
    private int rootX, rootY;
    private boolean isReset;

    // Constructor
    public Particle(int minX, int maxX, int minY, int maxY, String particleSpriteTag,
                    int minLife, int maxLife, int xMove, int yMove, int mindelay, int maxdelay) {
        // Initialize all of the needed data for a single particle
        this.particleSpriteTag = particleSpriteTag;
        this.x = getRandomInt(minX, maxX);
        this.y = getRandomInt(minY, maxY);
        lifecycle = getRandomInt(minLife, maxLife);
        this.xMove = xMove;
        this.yMove = yMove;
        int delay = getRandomInt(mindelay, maxdelay);
        timer = new stopWatchX(delay);
        rootX = x;
        rootY = y;
    }

    public Particle(int minX, int maxX, int minY, int maxY, String particleSpriteTag,
                    int minLife, int maxLife, int mindelay, int maxdelay) {
        // Initialize all of the needed data for a single snow particle
        this.particleSpriteTag = particleSpriteTag;
        this.x = getRandomInt(minX, maxX);
        this.y = getRandomInt(minY, maxY);
        lifecycle = getRandomInt(minLife, maxLife);
        int delay = getRandomInt(mindelay, maxdelay);
        timer = new stopWatchX(delay);
        rootX = x;
        rootY = y;
    }

    // Methods
    public boolean hasBeenReset() {
        return isReset;
    }

    public void changeX(int newX) {
        x = newX;
    }

    public int getX() {
        return x;
    }

    public int getLifeCycle() {
        return lifecycle;
    }

    public int getAge() {
        return age;
    }

    public void changeSprite(String newSpriteTag) {
        particleSpriteTag = newSpriteTag;
    }

    public boolean isParticleDead() {
        if (age >= lifecycle) {
            return true;
        }
        if (y > 720 || x > 1279) {
            return true;
        }
        return false;
    }

    /* This helps solve the problem of the "plume" in the
     ** beginning by artificially ageing them off-screen. */
    public void simulateAge() {
        age++;
        x += xMove;
        y += yMove;
        if (isParticleDead()) {
            // Reset
            x = rootX;
            y = rootY;
            age = 0;
            isReset = true;
        }
    }

    public Frame getCurrentFrame() {
        // Update the particle and return results
        if(timer.isTimeUp()) {
            age++;
            x += xMove;
            y += yMove;
            if(isParticleDead()) {
                // Reset
                x = rootX;
                y = rootY;
                age = 0;
                isReset = true;
            }
            timer.resetWatch();
        }
        return new Frame(x, y, particleSpriteTag);
    }

    public static int getRandomInt(int first, int last) {
        int diff = last - first;
        double num = Math.random() * diff;
        int intNum = (int)num;
        return first + intNum;
    }

    /* This function seeks to emulate the roll of
    ** a die in a paper and pencil RPG. dieSides is
    ** how many "sides the die has (d2, d4, d6, d8, etc.)
    */

    public static int rollDie(int dieSides) {
        double result = Math.random() * dieSides;
        int res = (int) result;
        res++;
        return res;
    }

}
