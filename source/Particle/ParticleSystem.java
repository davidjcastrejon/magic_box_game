package Particle;

import Data.Frame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParticleSystem {
    // Fields
    private Particle[] particles;
    private int x, y;
    private int xrange, yrange;
    private int maxlife;
    private String spriteTags[];

    // Constructor
    public ParticleSystem(int numParticles, int x, int y, int xrange, int yrange, int minlife, int maxlife,
        int xmove, int ymove, int mindelay, int maxdelay, String[] spriteTags) {
        this.xrange = xrange;
        this.yrange = yrange;
        this.x = x;
        this.y = y;
        this.maxlife = maxlife;
        particles = new Particle[numParticles];
        this.spriteTags = spriteTags;
        initParticles(xmove, ymove ,mindelay, maxdelay, minlife);
    }

    // Methods
    /* This sets up all of the particles individually in the system... */
    private void initParticles(int xmove, int ymove, int mindelay, int maxdelay, int _minlife) {
        for(int i = 0; i < particles.length; i++) {
            int n = spriteTags.length;
            int index = Particle.getRandomInt(0, n-1);
            particles[i] = new Particle(x, (x + xrange), y, (y + yrange), spriteTags[index],
                    _minlife, maxlife, xmove, ymove, mindelay, maxdelay);
        }
        // Age them until they are all through at least one life cycle...
        boolean isDone = false;
        while(isDone == false) {
            isDone = true;
            for(int i = 0; i < particles.length; i++) {
                particles[i].simulateAge();
                if(particles[i].hasBeenReset() == false) {
                    isDone = false;
                }
            }
        }
        // All particles past this point should be aged!
    }

    /* Access to the actual Particle array, as needed by the Fire class... */
    public Particle[] getParticleArray() {
        return particles;
    }
    public Iterator<Frame> getParticles() {
        List<Frame> parts = new ArrayList<>();
        for(int i = 0; i < particles.length; i++) {
            Frame tmp = particles[i].getCurrentFrame();
            parts.add(tmp);
        }
        return parts.iterator();
    }
}

