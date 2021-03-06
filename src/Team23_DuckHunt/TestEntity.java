package Team23_DuckHunt;

/* importing the SpriteStore authored by Kevin Glass */
import org.newdawn.spaceinvaders.SpriteStore;

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
 * A game Entity for testing and demonstration.
 * 
 * @author Andrew Goettler
 *
 */
public class TestEntity extends Entity
{	
	public TestEntity(Game game, String ref, int x, int y)
	{
		super(game, ref, x, y);
		this.setXVel(50);
		this.setYVel(50);
	}
	
	@Override public void move(long deltaTime)
	{
		
		// if the entity has hit the left side of the screen and is moving left, change direction
		if( (this.getXCoord() < 10) && (this.getXVel() < 0))
		{
			this.setXVel(-this.getXVel());
		}
		
		// if the entity has hit the right side of the screen and is moving right, change direction
		if( (this.getXCoord() > ( (this.getGame().getWidth() - 10) - this.getSprite().getWidth()) ) && 
				(this.getXVel() > 0) )
		{
			this.setXVel(-this.getXVel());
		}
		
		// if the entity has hit the top of the screen and is moving up, change direction
		if( (this.getYCoord() < 10) && (this.getYVel() < 0) )
		{
			this.setYVel(-this.getYVel());
		}
		
		// if the entity has hit the bottom of the screen and is moving down, change direction
		if( (this.getYCoord() > ( (this.getGame().getHeight() - 10) - this.getSprite().getHeight()) ) &&
				(this.getYVel() > 0) )
		{
			this.setYVel(-this.getYVel());
		}
		
		// call the superclass move method
		super.move(deltaTime);
	}
	
	@Override public void doGameLogic()
	{
		// When a TestEntity is hit, all the bugs change direction
		this.setYVel(-this.getYVel());
	}

	@Override public void collidedWith(Entity entity)
	{
		// TestEntity currently doesn't need to collide with anything
		
	}

	@Override
	public void hitByMouse()
	{
		// when a TestEntity gets hit, remove it from the game
		//this.getGame().removeEntity(this);
		// make all the other TestEntities change direction
		this.setSprite(SpriteStore.get().getSprite("sprites/claydebris.png"));
		this.getGame().updateGameLogic();
		//((TestGame)this.getGame()).notifyAlienKilled();
	}
}
