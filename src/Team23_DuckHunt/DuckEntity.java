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

public class DuckEntity extends Entity{

	private long vectorSumVel = -100;
	private int timeToRemove = 500;
	private boolean duckDestroyed;
	private boolean duckEscaped;
	
	public DuckEntity(Game game, String ref, int x, int y)
	{
		super(game, ref, x, y);
		
		// indicate that the duck hasn't been hit
		this.duckDestroyed = false;
		this.duckEscaped = false;
		
		Random random = new Random();
		double value = (random.nextInt(120) + 30)*(Math.PI/180);
		
		vectorSumVel *= ((DuckHuntGame)this.getGame()).getRound();
		
		this.setXVel((Math.cos(value)*vectorSumVel) + 100);
		
		this.setYVel((Math.sin(value)*vectorSumVel) - 100);
		
		this.doEntityLogic();
	}
	
	@Override public void move(long delta)
	{
		if (!this.duckDestroyed)
		{
			// if the entity has hit the left side of the screen and is moving left, change direction
			if ((this.getXCoord() < 10) && (this.getXVel() < 0)) {
				this.setXVel(-this.getXVel());
			}
			// if the entity has hit the right side of the screen and is moving right, change direction
			if ((this.getXCoord() > ((this.getGame().getWidth()) - this
					.getSprite().getWidth())) && (this.getXVel() > 0)) {
				this.setXVel(-this.getXVel());
			}
			// if the entity has hit the top of the screen and is moving up, change direction
			if ((this.getYCoord() < 10) && (this.getYVel() < 0)) 
			{
				if(duckEscaped)
				{
					this.getGame().removeEntity(this);
					this.getGame().initializeEntities();
				}
				else
				{
					this.setYVel(-this.getYVel());
				}
			}
			// if the entity has hit the bottom of the screen and is moving down, change direction
			if ((this.getYCoord() > ((this.getGame().getHeight() - 130) - this
					.getSprite().getHeight())) && (this.getYVel() > 0)) 
			{
				this.setYVel(-this.getYVel());
			}
		}
		else
		{
			this.setYVel(this.getYVel());
			
			if ((this.getYCoord() > ((this.getGame().getHeight()) - this
					.getSprite().getHeight())) && (this.getYVel() > 0)) 
			{
				this.getGame().removeEntity(this);
				this.getGame().initializeEntities();
			}
		}
		
		super.move(delta);
	}

	@Override public void doGameLogic()
	{
		
	}
	
	@Override public void doEntityLogic()
	{
		if(!this.duckDestroyed)
		{
			if(this.timeToRemove > 0)
			{
				this.timeToRemove--;
				this.getGame().updateEntityLogic(this);
			}
			
			else
			{
				this.setYVel(-300);
				this.setXVel(0);
				duckEscaped = true;
			}
		}
	}
	
	@Override public void collidedWith(Entity entity)
	{
		// Duck currently doesn't collide with anything
	}

	@Override public void hitByMouse()
	{
		if(!this.duckDestroyed && !this.duckEscaped)
		{
			// indicate that the duck has been hit by the mouse
			this.duckHitByMouse();
		
			// when a duck entity gets hit, change its sprite
			this.setSprite(SpriteStore.get().getSprite("sprites/DeadDuck.png"));
			
			// make all the other TestEntities change direction
			((DuckHuntGame)this.getGame()).notifyDuckShot();
		}
	}
	
	private void duckHitByMouse()
	{
		// indicate that the duck has been hit
		this.duckDestroyed = true;
		// reverse the clay's y-direction so it falls
		this.setYVel(300);
		this.setXVel(0);
	}
}
