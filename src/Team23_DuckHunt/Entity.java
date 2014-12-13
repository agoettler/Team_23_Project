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

import java.awt.Graphics;
import java.awt.Rectangle;


/* importing the SpriteStore authored by Kevin Glass */
import org.newdawn.spaceinvaders.SpriteStore;

/**
 * An abstract class representing an entity in the game. An Entity records its own position
 * and velocity.
 * 
 * @author Andrew Goettler
 *
 */
public abstract class Entity 
{
	// Instance fields
	/**
	 * The game this entity exists in
	 */
	private Game game;
	/**
	 * The sprite representing this entity on the game Canvas.
	 */
	private Sprite sprite;
	/**
	 * The current x-coordinate.
	 */
	private double xCoord;
	/**
	 * The current y-coordinate.
	 */
	private double yCoord;
	/**
	 * The current horizontal speed, in pixels/second.
	 */
	private double xVel;
	/**
	 * The current vertical speed, in pixels/second.
	 */
	private double yVel;
	/**
	 * The bounding box for this entity.
	 */
	private Rectangle boundBox = new Rectangle();
	/**
	 * Determines if this entity is checked for collisions.
	 */
	private boolean collideAble;
	
	
	/**
	 * This constructor sets the sprite image and initial location of this  entity.
	 * 
	 * @param ref a string representing an image file to be used as a sprite for this entity
	 * @param x initial x-coordinate of this entity
	 * @param y initial y-coordinate of this entity
	 */
	public Entity(Game game, String ref, int x, int y)
	{
		this.setGame(game); // get a handle on the game this entity will exisit in
		
		this.sprite = SpriteStore.get().getSprite(ref); // retrieve a sprite from the SpriteStore
		
		this.xCoord = x;
		
		this.yCoord = y;
		
		this.collideAble = true; // make the entity collideable by default
	}
	
	// Instance methods
	/**
	 * This method updates the position of this entity according to how far it should have moved
	 * 		since the previous game cycle.
	 * 
	 * @param delta time since the previous game cycle, in milliseconds
	 */
	public void move(long delta)
	{	
		/* since the time is given in milliseconds and speed is given in pixels per second,
				must divide by 1000 to get pixels */
		this.setXCoord(xCoord + ( (delta * xVel) / 1000 )); // update the x-coordinate
		
		this.setYCoord(yCoord + ( (delta * yVel) / 1000 )); // update the y-coordinate
	}
	
	/**
	 * This method draws the sprite for this entity using the specified graphics context.
	 * 
	 * @param g graphics context used for drawing this entity's sprite
	 */
	public void draw(Graphics g)
	{
		sprite.draw(g, (int) xCoord, (int) yCoord); // draw the sprite at the specified location
	}
	
	/**
	 * This method performs actions that should apply to all entities in a game.
	 */
	public void doGameLogic()
	{
		
	}
	
	/**
	 * This method performs actions that should apply only to selected entities.
	 */
	public void doEntityLogic()
	{
		
	}
	
	/**
	 * This setter method sets the game this entity exists in.
	 * 
	 * @param game game this entity exists in
	 */
	public void setGame(Game game)
	{
		this.game = game;
	}
	
	/**
	 * This getter method returns the game this entity exists in.
	 * 
	 * @return game this entity exists in
	 */
	public Game getGame()
	{
		return this.game;
	}
	
	/**
	 * This getter method returns the sprite object of this entity.
	 * 
	 * @return sprite for this entity
	 */
	public Sprite getSprite()
	{
		return this.sprite;
	}
	
	/**
	 * This setter method sets the sprite object for this entity.
	 * 
	 * @param sprite sprite this entity should use
	 */
	public void setSprite(Sprite sprite)
	{
		this.sprite = sprite;
	}
	
	/**
	 * This getter method gets the current x-coordinate of this entity
	 * 
	 * @return current x-coordinate of this entity
	 */
	public int getXCoord()
	{
		return (int) this.xCoord;
	}
	
	/**
	 * This setter method sets the current x-coordinate of this entity.
	 * 
	 * @param xValue x-coordinate this entity should be at
	 */
	public void setXCoord(double xValue)
	{
		this.xCoord = xValue;
	}
	
	/**
	 * This getter method returns the current y-coordinate of this entity.
	 * 
	 * @return current y-coordinate of this entity
	 */
	public int getYCoord()
	{
		return (int) this.yCoord;
	}
	
	/**
	 * This setter method sets the current y-coordinate of this entity.
	 * 
	 * @param yValue y-coordinate this entity should be at
	 */
	public void setYCoord(double yValue)
	{
		this.yCoord = yValue;
	}
	
	/**
	 * This getter method returns the current x-velocity of this entity.
	 * 
	 * @return current x-velocity of this entity
	 */
	public double getXVel()
	{
		return this.xVel;
	}
	
	/**
	 * This setter method sets the current x-velocity of this entity.
	 * 
	 * @param velocity x-velocity this entity should move at
	 */
	public void setXVel(double velocity)
	{
		this.xVel = velocity;
	}
	
	/**
	 * This getter method gets the current y-velocity of this entity.
	 * 
	 * @return current y-velocity of this entity
	 */
	public double getYVel()
	{
		return this.yVel;
	}
	
	/**
	 * This setter method sets the current y-velocity of this entity.
	 * 
	 * @param velocity y-velocity this entity should move at
	 */
	public void setYVel(double velocity)
	{
		this.yVel = velocity;
	}
	
	/**
	 * This method returns the bounding box of this entity.
	 * 
	 * @return bounding box of this entity
	 */
	public Rectangle getBounds()
	{
		this.boundBox.setBounds(this.getXCoord(), this.getYCoord(), this.getSprite().getWidth(), this.getSprite().getHeight());
		return this.boundBox;
	}
	
	/**
	 * This method returns whether or not collisions are enabled for this entity.
	 * 
	 * @return true if collisions are enabled for this entity
	 */
	public boolean getCollideAble()
	{
		return this.collideAble;
	}
	
	/**
	 * This method sets whether or not collisions are enabled for this entity.
	 * 
	 * @param collisionStatus 
	 */
	public void setCollideAble(boolean collisionStatus)
	{
		this.collideAble = collisionStatus;
	}
	
	/**
	 * This method determines if this entity collides with the specified entity. Collision
	 * is determined by intersection of the bounding boxes of the entities.
	 * 
	 * @param entity entity to be checked for collisions
	 * @return true if the bounding boxes of this entity and the specified entity intersect
	 */
	public boolean collidesWith(Entity entity)
	{
		if (this.getCollideAble())
		{
			return this.getBounds().intersects(entity.getBounds());
		}
		
		else
		{
			return false;
		}
	}
	
	/* I forsee a better way to do collisions, if we had more than a couple of entities:
	 * public void collidesWith(Entity entity)
	 * {
	 * 		if (this.getBounds().intersects(entity.getBounds());
	 * 		{
	 * 			this.collidesWith(entity);
	 * 			entity.collidesWith(this);
	 * 		}
	 * 
	 * 		// remove this entity from the collision list after testing it
	 * 			against every other entity on the list
	 */
	
	/**
	 * This method determines what occurs when this entity collides with the specified entity.
	 * Must be implemented by the subclass.
	 * 
	 * @param entity the entity this entity collided with
	 */
	public abstract void collidedWith(Entity entity);
	
	/**
	 * This method determines what occurs when this entity is hit by the mouse.
	 * Must be implemented by the subclass.
	 */
	public abstract void hitByMouse();
}
