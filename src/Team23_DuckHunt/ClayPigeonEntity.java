package Team23_DuckHunt;

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

import java.util.Random;

/* importing the SpriteStore authored by Kevin Glass */
import org.newdawn.spaceinvaders.SpriteStore;

public class ClayPigeonEntity extends Entity{
	
	private long vectorSumVel = -100;
	private int timeToRemove = 50;
	private boolean clayDestroyed;

	public ClayPigeonEntity(Game game, String ref, int x, int y) 
	{
		super(game, ref, x, y);
		
		// indicate that the clay hasn't been hit
		this.clayDestroyed = false;
		
		Random random = new Random();
		double value = (random.nextInt(120) + 30)*(Math.PI/180);
		
		vectorSumVel *= ((ClayPigeonGame)this.getGame()).getRound();
		
		this.setXVel(Math.cos(value)*vectorSumVel);
		
		this.setYVel(Math.sin(value)*vectorSumVel);
	}

	@Override public void move(long deltaTime)
	{
		if( this.getXCoord() > this.getGame().getWidth() || this.getXCoord() < (0 - this.getSprite().getWidth()) || this.getYCoord() < (0 - this.getSprite().getHeight()))
		{
			// this is only necessary if the clay has not been destroyed by the time it hits the edge of the screen
			if(!clayDestroyed)
			{
				this.getGame().removeEntity(this);
				this.getGame().initializeEntities();
			}
		}
		super.move(deltaTime);
	}
	
	@Override public void doGameLogic()
	{
		
	}
	
	@Override public void doEntityLogic()
	{
		if(this.timeToRemove > 0)
		{
			this.timeToRemove--;
				this.getGame().updateEntityLogic(this);
		}
		
		else
		{
			this.getGame().removeEntity(this);
			this.getGame().initializeEntities();
		}
	}
	
	@Override public void collidedWith(Entity entity) 
	{
		
	}

	@Override public void hitByMouse() 
	{
		// if the clay has already been hit by the mouse, don't do this again!
		if (!this.clayDestroyed)
		{
			// indicate that the clay has been hit by the mouse
			this.clayHitByMouse();

			// when a clay entity gets hit, change its sprite
			this.setSprite(SpriteStore.get().getSprite("sprites/claydebris.png"));
			
			// ask the game to update the entity logic for this entity
			this.getGame().updateEntityLogic(this);
			
			// notify the game that a pigeon has been shot
			((ClayPigeonGame) this.getGame()).notifyPigeonShot();
		}
	}
	
	private void clayHitByMouse()
	{
		// indicate that the clay has been hit
		this.clayDestroyed = true;
		// reverse the clay's y-direction so it falls
		this.setYVel(-this.getYVel());
	}
}
