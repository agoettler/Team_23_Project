package Team23_DuckHunt;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JFrame;

/*
 * Introduction to Software Design Fall 2014
 * Game: Duck Hunt
 * Authors: 
 * 		Andrew Goettler
 * 		Joshua Svenson
 * Team Project: Arcade Game Redux
 * Problem Description: The goal of this project is to implement a classic arcade game in JavaTM 
 * 		with modern twists. A tutorial is provided for help getting started implementing the 
 * 		graphics, but this is intended as a guide and not as free code to just use. 
 * 		Because help is provided for the graphics, you may want to try and differentiate 
 * 		your game from other students by adding new and unique features.
 * Problem Documentation:
 * 		Coding Competition Rules: https://icon.uiowa.edu/d2l/le/content/2352034/fullscreen/3509067/View
 * 		Problem Description: https://icon.uiowa.edu/d2l/le/content/2352034/fullscreen/3509071/View
 */

/**
 * A Game object for experimenting and testing. Code created here can be moved up into the 
 * 		Game abstract class if it will eventually be common to all Game objects.
 * 
 * @author Andrew Goettler
 *
 */
public class TestGame extends Game
{	
	/**
	 * ID for serialization
	 */
	private static final long serialVersionUID = 1L;
	
	private int alienCount;
	
	public TestGame(JFrame gameFrame)
	{
		super(gameFrame);
	}

	@Override public void initializeEntities()
	{
		for(int i = 0; i < 4; i++)
		{
			Entity testEntity = new TestEntity(this, "sprites/Clay.png", i * 100, i * 100);
			this.getEntities().add(testEntity);
			alienCount++;
		}
	}
	
	@Override public void drawBackgroud(Graphics2D g)
	{
		// draw some sky
		g.setColor(Color.blue);
		g.fillRect(0,0,800,500);

		// draw some ground
		g.setColor(Color.yellow);
		g.fillRect(0, 500, 800, 100);
	}
	
	@Override
	public void drawForeground(Graphics2D g)
	{
		
	}
	
	public void notifyAlienKilled()
	{
		alienCount--;
		
		if(alienCount == 0)
		{
			this.endGame();
		}
	}
}
