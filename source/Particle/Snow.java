package Particle;

public class Snow {

    private ParticleSystem parts;
    private String[] spriteTags;
    public Snow(int xpos, int ypos, int xrange,
                int yrange, int minlife, int maxlife,
                int numparticles) {
        spriteTags = new String[5];
        spriteTags[0] = "rain1";
        spriteTags[1] = "rain2";
        spriteTags[2] = "rain3";
        spriteTags[3] = "rain4";
        spriteTags[4] = "rain5";
        int xspeed = 0;
        int yspeed = 5;
        parts = new ParticleSystem(numparticles, xpos, ypos, xrange, yrange,
                minlife, maxlife, xspeed, yspeed, 16, 18, spriteTags);
    }

    // Methods
    /* Update the particles based on the lifecycle (and the tile set) */
    private void updateParticleSprites() {
        Particle[] pa = parts.getParticleArray();
        for(int i = 0; i < pa.length; i++) {
            int stages = spriteTags.length;
            int life = pa[i].getLifeCycle();
            int range = life / stages;
            int age = pa[i].getAge();
            for(int j = 0; j < stages; j++) {
                if(age >= (range*j) && age < (range*(j+1))) {
                    pa[i].changeSprite(spriteTags[j]);
                    break;
                }
            }
        }
    }

    public ParticleSystem getParticleSystem() {
        updateParticleSprites();
        return parts;
    }

}