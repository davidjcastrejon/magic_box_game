package Main;

import Command.Command;
import Command.Interpreter;
import Data.*;
import Data.RECT;
import Data.Frame;
import FileIO.EZFileRead;
import FileIO.EZFileWrite;
import Particle.Snow;
import Particle.ParticleSystem;
import Particle.Rain;
import logic.Control;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Random;
import Input.Mouse;
import Sound.*;
//import timer.*;
import Data.AText;

public class Main{
	// Fields (Static) below...s
	public static AText lvl1text = new AText("Choose wisely...", 80);
	public static AText lvl2text = new AText("Feeling lucky I suppose...", 80);
	public static AText lvl3text = new AText("So close but so far...", 80);
	public static String s = "";
	public static String s2 = "";
	public static String s3 = "";
	public static Rain rain;
	public static Animation anim;
	public static Snow custom1;
	public static Snow custom2;
	private static ArrayList<Command> commands;
	public static String scene;
	private static int[] buffer; // Some hypothetical game variables
	//private static RECT save;
	private static final int dropShadow = 2;
	public static Sprite l1;
	public static Sprite l2;
	public static Sprite l3;
	public static int level1;
	public static int level2;
	public static int level3;
	public static final int l2lives = 1;
	public static final int l3lives = 2;
	public static boolean l1has;
	public static boolean l2has;
	public static boolean l3has;
	public static boolean coinUsed;
	public static boolean coindisplay;
	public static boolean coinclicked;
	public static int l1coin;
	public static int l2coin;
	public static int l3coin;
	public static int attempts;
	public static int tries;
	public static Sound song = new Sound("Sound/persephone_farewell.wav");
	public static Sound sfx = new Sound("Sound/soundeffect.wav");
	// End Static fields...
	
	public static void main(String[] args) {
		Control ctrl = new Control();				// Do NOT remove!
		ctrl.gameLoop();							// Do NOT remove!
	}
	
	/* This is your access to things BEFORE the game loop starts */
	public static void start(Control ctrl){
		// TODO: Code your starting conditions here...NOT DRAW CALLS HERE! (no addSprite or drawString)

		// Hiding default cursor
		ctrl.hideDefaultCursor();

		// Setting up losing animation
		anim = new Animation(120, true);
		for(int i = 0; i < 7; i++) {
			anim.addFrame(new Frame(1000, 200, "frown" + i));
		}

		// Setting up song
		song.setLoop();

		// Keeps track of how many attempts have been made
		tries = 0;

		// Start off the game having not used the coin
		coinUsed = false;

		// Checking if a coin box has been clicked
		coinclicked = false;

		/*******************************/
		/* Create the level one canvas */
		/*******************************/
		BufferedImage li1 = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_ARGB);
		// Creating buffered image of purple background
		BufferedImage bimage = ctrl.getSpriteFromBackBuffer("background").getSprite();
		// Creating buffered image of each box
		BufferedImage boximage = ctrl.getSpriteFromBackBuffer("box").getSprite();
		Graphics g = li1.getGraphics();
		// Drawing purple background
		g.drawImage(bimage, 0, 0, null);
		// Drawing two boxes in the middle of the screen
		for(int i = 0; i < 2; i++) {
			BufferedImage boxcopy = boximage.getSubimage(0, 0, 128, 128);
			g = li1.getGraphics();
			g.drawImage(boxcopy, 457 + (i * 200), 296, null);
		}
		l1 = new Sprite(0, 0, li1, "li1");


		/*******************************/
		/* Create the level two canvas */
		/*******************************/
		BufferedImage li2 = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_ARGB);
		g = li2.getGraphics();
		// Drawing a purple background
		g.drawImage(bimage, 0, 0, null);
		// Drawing a centered 2 x 2 grid of boxes
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 2; j++) {
				BufferedImage boxcopy = boximage.getSubimage(0, 0, 128, 128);
			    g = li2.getGraphics();
				g.drawImage(boxcopy, 457 + (i * 200), 182 + (j * 200), null);
			}
		}
		l2 = new Sprite(0, 0, li2, "li2");


		/*******************************/
		/* Create the level three canvas */
		/*******************************/
		BufferedImage li3 = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_ARGB);
		g = li3.getGraphics();
		// Drawing a purple background
		g.drawImage(bimage, 0, 0, null);
		// Drawing a centered 2 x 2 grid of boxes
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				BufferedImage boxcopy = boximage.getSubimage(0, 0, 128, 128);
			    g = li3.getGraphics();
				g.drawImage(boxcopy, 372 + (i * 200), 105 + (j * 190), null);
			}
		}
		l3 = new Sprite(0, 0, li3, "li3");




		// Define beginning scene
		scene = "levelone.txt";
		EZFileRead ezr = new EZFileRead("levelone.txt");
		commands = new ArrayList<>();
		for(int i = 0; i < ezr.getNumLines(); i++) {
			String raw = ezr.getLine(i);
			raw = raw.trim();
			if(!raw.equals("")) {
				boolean b = raw.charAt(0) == '#';
				if(!b) {
					commands.add(new Command(raw));
				}
			}
		}

		/* Creating a random int for each level to determine correct block */ 
		Random rand = new Random();
		level1 = rand.nextInt(2);
		level2 = rand.nextInt(4);
		level3 = rand.nextInt(9);

		// Create snow particles
		custom1 = new Snow(0, 0, 1280, 90, 100, 200, 100);
	}

		//custom = new Rain();
		//rain = new Rain(-50, 0, 1200, 90, 25, 60, 150);
		//custom2 = new Snow(0, 0, 1280, 90, 100, 200, 100);

		/* 
		// Set up clickable RECT for disk...
		save = new RECT(601, 52, 662, 112, "saveicon", "Save Game");
		buffer = new int[40]; // Initialize game variables
		// Assign random values to the buffer (for testing)
		for(int i = 0; i < buffer.length; i++) {
			int value = (int) (Math.random() * 100);
			buffer[i] = value;
		}
		*/

	
	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		// TODO: This is where you can code! (Starting code below is just to show you how it works)
		// Running the current script in a loop
		Point point = Mouse.getMouseCoords();
		ctrl.addSpriteToOverlayBuffer(point.x, point.y, "cursor");
		Interpreter.runScript(commands, ctrl);
   
		if(scene.equals("levelone.txt")) {
			ctrl.addSpriteToFrontBuffer(l1);
			ctrl.addSpriteToHudBuffer(1020, 5, "hud");
			ctrl.drawHudString(1055, 80, "1 LIFE REMAINING", Color.GREEN);
			String atext = lvl1text.getCurrentStr();
			ctrl.drawString(540, 80, atext, Color.black);
			for(RECT spot: Interpreter.rects) {
				Point p = Mouse.getMouseCoords();
				int x = (int) p.getX();
				int y = (int) p.getY();
				if(spot.isCollision(x,y)) {
					ctrl.addSpriteToFrontBuffer(spot.getX(), spot.getY(), "hover");
				} 
				if(Control.getMouseInput() != null) {
					if(spot.isClicked(Control.getMouseInput(), Click.LEFT_BUTTON) && spot == Interpreter.rects.get(level1)) {
						sfx.playWAV();
						lvl1text.resetAnimation();
						lvl2text.resetAnimation();
						lvl3text.resetAnimation();
						scene = "leveltwo.txt";
						//atext.setSrcString("Wise choice young one...");
						//atext.resetAnimation();
						readScript(scene);
						break;
					} else if(spot.isClicked(Control.getMouseInput(), Click.LEFT_BUTTON)) {
						sfx.playWAV();
						tries = 0;
						newBoxes();
						newCoins();
						lvl1text.resetAnimation();
						lvl2text.resetAnimation();
						lvl3text.resetAnimation();
						scene = "lose.txt";
						readScript(scene);
						break;
					}
				}
			}
		} else if(scene.equals("leveltwo.txt")) {
			// Display background
			ctrl.addSpriteToFrontBuffer(l2);
			ctrl.addSpriteToHudBuffer(1020, 5, "hud");
			String atext = lvl2text.getCurrentStr();
			ctrl.drawString(500, 80, atext, Color.black);
			//ctrl.drawString(500, 80, antext, Color.black);
			if(tries == 0) {
				ctrl.drawHudString(1050, 80, "2 LIVES REMAINING", Color.GREEN);
			} else if(tries == 1) {
				ctrl.drawHudString(1055, 80, "1 LIFE REMAINING", Color.RED);
			}
			Point p = Mouse.getMouseCoords();
			int x = (int) p.getX();
			int y = (int) p.getY();
			if(Interpreter.rects.get(4).isCollision(x,y) && tries == 0) {
				s = "2 lives remaining";
			} else if(Interpreter.rects.get(4).isCollision(x,y) && tries == 1){
				s = "1 life remaining";
			} else {
				s = "";
			}
			ctrl.drawString(x, (y-2), s, Color.BLACK);
			ctrl.drawString(x - dropShadow, (y - dropShadow) - 2, s, Color.YELLOW);
			for(RECT spot: Interpreter.rects) {
				p = Mouse.getMouseCoords();
				x = (int) p.getX();
				y = (int) p.getY();
				if(spot.isCollision(x,y)) {
					ctrl.addSpriteToFrontBuffer(spot.getX(), spot.getY(), "hover");
				} 
				// If 0 attempts have been made, display good health or else display bad health
				if(tries == 0) {
					ctrl.addSpriteToFrontBuffer(40, 40, "good");
				} else {
					ctrl.addSpriteToFrontBuffer(40, 40, "bad");
				}
				if(Control.getMouseInput() != null) {
					
					if(spot.isClicked(Control.getMouseInput(), Click.LEFT_BUTTON) && spot == Interpreter.rects.get(level2)) {
						sfx.playWAV();
						tries = 0;
						//atext.resetAnimation();
						//atext.setSrcString("Feeling lucky I suppose...");
						lvl1text.resetAnimation();
						lvl2text.resetAnimation();
						lvl3text.resetAnimation();
						scene = "levelthree.txt";
						readScript(scene);
						break;
					} else if(spot.isClicked(Control.getMouseInput(), Click.LEFT_BUTTON) && tries >= l2lives) {
						sfx.playWAV();
						tries = 0;
						newBoxes();
						newCoins();
						//atext.resetAnimation();
						lvl1text.resetAnimation();
						lvl2text.resetAnimation();
						lvl3text.resetAnimation();
						scene = "lose.txt";
						readScript(scene);
						break;
					} else if (spot.isClicked(Control.getMouseInput(), Click.LEFT_BUTTON)) {
						sfx.playWAV();
						tries++;
					} 
				}
			}
		} else if(scene.equals("levelthree.txt")) {
			ctrl.addSpriteToFrontBuffer(l3);
			ctrl.addSpriteToHudBuffer(1020, 5, "hud");
			String atext = lvl3text.getCurrentStr();
			ctrl.drawString(540, 80, atext, Color.black);
			//ctrl.drawString(500, 80, antext, Color.black);
			if(tries == 0) {
				ctrl.drawHudString(1050, 80, "3 LIVES REMAINING", Color.GREEN);
			} else if(tries == 1) {
				ctrl.drawHudString(1055, 80, "2 LIVES REMAINING", Color.ORANGE);
			} else if(tries == 2) {
				ctrl.drawHudString(1055, 80, "1 LIFE REMAINING", Color.RED);
			}
			Point p = Mouse.getMouseCoords();
			int x = (int) p.getX();
			int y = (int) p.getY();
			if(Interpreter.rects.get(9).isCollision(x,y) && tries == 0) {
				s = "3 lives remaining";
			} else if(Interpreter.rects.get(9).isCollision(x,y) && tries == 1) {
				s = "2 lives remaining";
			} else if(Interpreter.rects.get(9).isCollision(x,y) && tries == 2) {
				s = "1 life remaining";
			} else {
				s = "";
			}
			ctrl.drawString(x, (y-2), s, Color.BLACK);
			ctrl.drawString(x - dropShadow, (y - dropShadow) - 2, s, Color.YELLOW);
			for(RECT spot: Interpreter.rects) {
				p = Mouse.getMouseCoords();
				x = (int) p.getX();
				y = (int) p.getY();
				if(spot.isCollision(x,y)) {
					ctrl.addSpriteToFrontBuffer(spot.getX(), spot.getY(), "hover");
				} 
				if(tries == 0) {
					ctrl.addSpriteToFrontBuffer(40, 40, "good");
				} else if(tries == 1) {
					ctrl.addSpriteToFrontBuffer(40, 40, "okay");
				} else if(tries == 2) {
					ctrl.addSpriteToFrontBuffer(40, 40, "bad");
				}
				if(Control.getMouseInput() != null) {
					if(spot.isClicked(Control.getMouseInput(), Click.LEFT_BUTTON) && tries >= l3lives) {
						sfx.playWAV();
						tries = 0;
						newBoxes();
						newCoins();
						lvl1text.resetAnimation();
						lvl2text.resetAnimation();
						lvl3text.resetAnimation();
						scene = "lose.txt";
						readScript(scene);
						break;
					} else if(spot.isClicked(Control.getMouseInput(), Click.LEFT_BUTTON) && spot == Interpreter.rects.get(level3)) {
						sfx.playWAV();
						tries = 0;
						newBoxes();
						newCoins();
						//atext.resetAnimation();
						lvl1text.resetAnimation();
						lvl2text.resetAnimation();
						lvl3text.resetAnimation();
						scene = "win.txt";
						readScript(scene);
						break;
					} else if (spot.isClicked(Control.getMouseInput(), Click.LEFT_BUTTON)) {
						sfx.playWAV();
						tries++;
					} 
				}
			}
		} else if(scene.equals("lose.txt")) {
			ParticleSystem pm2 = custom1.getParticleSystem();
			Iterator<Frame> it2 = pm2.getParticles();
			while(it2.hasNext()) {
				Frame par2 = it2.next();
				ctrl.addSpriteToFrontBuffer(par2.getX(), par2.getY(), par2.getSpriteTag());
			}
			for(RECT spot: Interpreter.rects) {
				if(Control.getMouseInput() != null) {
					if(spot.isClicked(Control.getMouseInput(), Click.LEFT_BUTTON) && spot == Interpreter.rects.get(0)) {
						scene = "levelone.txt";
						readScript(scene);
						break;
					}
				}
			}
		} else if(scene.equals("win.txt")) {
			ParticleSystem pm2 = custom1.getParticleSystem();
			Iterator<Frame> it2 = pm2.getParticles();
			while(it2.hasNext()) {
				Frame par2 = it2.next();
				ctrl.addSpriteToFrontBuffer(par2.getX(), par2.getY(), par2.getSpriteTag());
			}
			for(RECT spot: Interpreter.rects) {
				if(Control.getMouseInput() != null) {
					if(spot.isClicked(Control.getMouseInput(), Click.LEFT_BUTTON) && spot == Interpreter.rects.get(0)) {
						scene = "levelone.txt";
						readScript(scene);
						break;
					}
				}
			}
		}
	}




	/* Create a routine to save the game data*/
	public static void saveData() {
		// Save data to a String to output...
		String out = "";
		for(int i = 0; i < buffer.length; i++) {
			out += buffer[i] + "*";
		}
		out = out.substring(0, out.length()-1); // Remove trailing delimiter
		// Save output String to file
		EZFileWrite ezw = new EZFileWrite("save.text");
		ezw.writeLine(out);
		ezw.saveFile();
	}

	/* Create a routine to restore the game data */
	public static void loadData() {
		// Retrieve data from the file
		EZFileRead ezr = new EZFileRead("save.txt");
		String raw = ezr.getLine(0); // Read our one and only line (index #0)
		// Break this down into tokens
		StringTokenizer st = new StringTokenizer(raw, "*");
		if(st.countTokens() != buffer.length) {
			return; // These must match!
		}
		for(int i = 0; i < buffer.length; i++) {
			String value = st.nextToken();
			int val = Integer.parseInt(value);
			buffer[i] = val;
		}
	}

	public static void readScript(String scene) {
		EZFileRead ezr = new EZFileRead(scene);
		commands = new ArrayList<>();
		for(int i = 0; i < ezr.getNumLines(); i++) {
			String raw = ezr.getLine(i);
			raw = raw.trim();
			if(!raw.equals("")) {
				boolean b = raw.charAt(0) == '#';
				if(!b) {
					commands.add(new Command(raw));
				}
			}
		}
	}

	public static void newBoxes() {
		Random rand = new Random();
		level1 = rand.nextInt(2);
		level2 = rand.nextInt(4);
		level3 = rand.nextInt(9);
	}

	
	public static void newCoins() {
		coinUsed = false;
		Random rand = new Random();
		// Determining if the level has a special coin box
		if(rand.nextInt(7) == 3) {
			l1has = true;
		} else {
			l1has = false;
		}
		if(rand.nextInt(4) == 2) {
			l2has = true;
		} else {
			l2has = false;
		}
		if(rand.nextInt(2) == 1) {
			l3has = true;
		} else {
			l3has = false;
		}

		/* Computing the index of the special coin for each level */
		// Level 1
		if(level1 == 0) {
			l1coin = 1;
		} else if(level1 == 1) {
			l1coin = 0;
		}
		// Level 2
		Boolean finished = false;
		while(finished == false) {
			l2coin = rand.nextInt(4);
			if(l2coin != level2) {
				finished = true;
			}
		}
		// Level 3
		finished = false;
		while(finished == false) {
			l3coin = rand.nextInt(9);
			if(l3coin != level3) {
				finished = true;
			}
		}
	}

}

