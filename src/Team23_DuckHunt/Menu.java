package Team23_DuckHunt;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;


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

public class Menu extends JFrame
{	
	public final static int PORT_NUMBER = 7000;
	
	public static void main( String[] args )
	{
		Menu gameMenu = new Menu(PORT_NUMBER);
		
		gameMenu.gameManager();
	}
	
	/**
	 * ID for serialization
	 */
	private static final long serialVersionUID = 1L;
	
	private enum GameType
	{
		ONE_DUCK{
			public String toString() {
				return "Duck Huntin'";
			}
		},
		CLAY_PIGEON{
			public String toString() {
				return "Sportin' Clays";
			}
		}, 
		TEST_GAME{
			public String toString() {
				return "Test Game";
			}
		}
	}
	
	private JFrame gameFrame;
	private Game currentGame;
	private JPanel splashScreen;
	private JPanel startScreen;
	private JPanel initialsScreen;
	private ScoreClient client;
	private GridBagLayout gridBagLayout = new GridBagLayout();
	private boolean gameStarted = false;
	public boolean gameEnded = false;
	public int latest;
	private boolean hasEnteredInitials;
	
	public Menu(int portNumber)
	{
		try
		{
			client = new ScoreClient(InetAddress.getLocalHost(), portNumber);
		} 
		
		catch (UnknownHostException e)
		{
			// TODO Add error handling for failing to create a ScoreClient
			e.printStackTrace();
		}
		
		gameFrameCreator();
		startScreenCreator();
	}
	
	private void gameFrameCreator()
	{
		gameFrame =  new JFrame("Duck Hunt");
		
		gameFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		//gameFrame.setSize(1000,1000);
		gameFrame.setVisible( true );
		gameFrame.setResizable( false );
	}
	
	private void startScreenCreator()
	{
		startScreen = new JPanel();
		startScreen.setLayout( gridBagLayout );
		startScreen.setPreferredSize(new Dimension(800,600));
		startScreen.setBackground(Color.GREEN);
		
		JPanel startScreenTitlePanel = new JPanel();
		JLabel startScreenTitle = new JLabel("Duck Hunt");
		startScreenTitlePanel.add(startScreenTitle);
		
		addComponent(startScreenTitlePanel, 0, 0, 5, 1);
		
		ButtonHandler handler = new ButtonHandler();		
		
		int gridButtonRowLocation = 2;
		
		for( GameType game : GameType.values()){
			JButton gameButton = new JButton(game.toString());
			
			gameButton.addActionListener( handler );
			addComponent(gameButton, gridButtonRowLocation, 3, 1, 1);
			gridButtonRowLocation++;
		}	
	}
	
	private void addComponent( Component component, int row, int column, int width, int height)
	{
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = column;
		constraints.gridy = row;
		constraints.gridwidth = width;
		constraints.gridheight = height;
		gridBagLayout.setConstraints( component, constraints);
		startScreen.add(component);
		
		
	}
	
	private class ButtonHandler implements ActionListener
	{
		public void actionPerformed( ActionEvent event ){
			
			for( GameType game : GameType.values())
			{
				if(game.toString().equals(event.getActionCommand()))
				{
					selectGame(game);
				}
			}
		}
	}
	
	private void splashScreenCreator()
	{
		splashScreen = new JPanel();
		splashScreen.setPreferredSize(new Dimension(800,600));
		splashScreen.setBackground(Color.BLACK);
		splashScreen.setLayout( new BorderLayout(5,5) );
		
		client.requestHighScoreList();
		
		JPanel splashScreenTitlePanel = new JPanel();
		JLabel splashScreenTitle = new JLabel("High Scores");
		splashScreenTitle.setFont( new Font( "Serif", Font.BOLD, 16));
		splashScreenTitlePanel.add(splashScreenTitle);
		splashScreenTitlePanel.setBackground(Color.YELLOW);
		splashScreen.add(splashScreenTitlePanel, BorderLayout.NORTH);
		
		JPanel splashScreenScoresPanel = new JPanel();
		splashScreenScoresPanel.setLayout( new GridLayout( client.getHighScoreList().getScoreListSize(), 1));
		splashScreenScoresPanel.setBackground(Color.YELLOW);
		
		for(int i = 0; i < client.getHighScoreList().getScoreListSize(); i++)
		{
			JLabel splashScreenScores = new JLabel( i+1 + ".  " + client.getHighScoreList().getHighScoresList().get(i).toString());
			splashScreenScores.setHorizontalAlignment( JLabel.CENTER);
			splashScreenScoresPanel.add(splashScreenScores);
		}
		
		splashScreen.add(splashScreenScoresPanel, BorderLayout.CENTER);
	}
	
	private void NoHighScoreInitialsScreenCreator()
	{
		initialsScreen = new JPanel();
		initialsScreen.setPreferredSize(new Dimension(800,600));
		initialsScreen.setBackground(Color.BLACK); 
		initialsScreen.setLayout( new GridLayout(5,1) );
		
		JPanel gameOverPanel = new JPanel();
		JLabel gameOverPanelLabel = new JLabel("<html><font color = 'white'>GAME OVER</html>");
		gameOverPanelLabel.setFont( new Font( "Serif", Font.BOLD, 30));
		gameOverPanelLabel.setHorizontalAlignment( JLabel.CENTER );
		gameOverPanel.setBackground(Color.BLACK); 
		gameOverPanel.add(gameOverPanelLabel);
		initialsScreen.add(gameOverPanel);
		
		JPanel yourScorePanel = new JPanel();
		JLabel yourScoreLabel = new JLabel("Score: " + currentGame.getGameScore());
		yourScoreLabel.setFont( new Font( "Serif", Font.BOLD, 20));
		yourScorePanel.setBackground(Color.YELLOW); 
		yourScorePanel.add(yourScoreLabel);
		initialsScreen.add(yourScorePanel);
	}
	
	private void HighScoreInitialsScreenCreator()
	{
		hasEnteredInitials = false;
		
		NoHighScoreInitialsScreenCreator();
		
		JPanel highScoreProclamationPanel = new JPanel();
		JLabel highScoreProclamation = new JLabel("<html><<font color = 'white'>Nice! New High Score!</html>");
		highScoreProclamation.setHorizontalAlignment( JLabel.CENTER );
		highScoreProclamationPanel.setBackground(Color.BLACK); 
		highScoreProclamation.setFont( new Font( "Serif", Font.BOLD, 18));
		highScoreProclamationPanel.add(highScoreProclamation);
		initialsScreen.add(highScoreProclamation);
		
		JPanel enterInitials = new JPanel();
		JLabel enterInitialsLabel = new JLabel("<html><<font color = 'white'>Enter Initials</html>");
		enterInitials.setBackground(Color.BLACK); 
		enterInitialsLabel.setFont( new Font( "Serif", Font.BOLD, 28));
		enterInitials.add(enterInitialsLabel);
		initialsScreen.add(enterInitials);
		
		JPanel initials = new JPanel();
		JTextField initialsTextField = new JTextField(3);
		initials.setBackground(Color.BLACK); 
		initials.add(initialsTextField);
		initialsScreen.add(initials);
		
		TextFieldHandler handler = new TextFieldHandler();
		initialsTextField.addActionListener( handler );
	}
	
	private class TextFieldHandler implements ActionListener
	{
		public void actionPerformed( ActionEvent event )
		{	
			Score newHighScore = new Score(event.getActionCommand(), currentGame.getGameScore());
			client.sendHighScore(newHighScore);
			hasEnteredInitials = true;
		}
	}
	
	public JFrame getGameFrame()
	{
		return this.gameFrame;
	}
	
	public void gameManager()
	{
		/*
		 * infinite while loop
		 * 		get high scores
		 * 		show splash screen
		 * 		show select screen
		 * 		get user game selection
		 * 		create and launch game object
		 * 		show game over screen
		 * 		if high score
		 * 			send score and initials to server
		 */
		while(true)
		{
			splashScreenCreator();
			gameFrame.setContentPane(splashScreen);
			gameFrame.pack();
			gameFrame.revalidate();
			
			try
			{
				Thread.sleep(5000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			gameFrame.remove(splashScreen);
			gameFrame.setContentPane(startScreen);
			gameFrame.pack();
			gameFrame.revalidate();
			
			while(gameStarted == false)
			{
				try{
					Thread.sleep(1000);
				} catch ( InterruptedException e){
				}
			}
			
			while(gameEnded == false)
			{
				try{
					Thread.sleep(1000);
				} catch ( InterruptedException e){
				}
			}
			
			if(gameEnded == true)
			{		
				gameFrame.remove(startScreen);
				
				client.requestHighScoreList();
				if(client.checkIfHighScore(currentGame.getGameScore()) == true)
				{
					HighScoreInitialsScreenCreator();
					gameFrame.setContentPane(initialsScreen);
					gameFrame.pack();
					gameFrame.revalidate();
					
					while(hasEnteredInitials == false)
					{
						try{
							Thread.sleep(500);
						} catch (InterruptedException e){
							
						}
					}
				}
				else
				{
					NoHighScoreInitialsScreenCreator();
					gameFrame.setContentPane(initialsScreen);
					gameFrame.pack();
					gameFrame.revalidate();
					
					try{
						Thread.sleep(7000);
					} catch (InterruptedException e){
					}
				}
				
				gameFrame.remove(initialsScreen);
				gameFrame.pack();
				gameFrame.revalidate();
				
				gameStarted = false;
				gameEnded = false;
			}
		}	
	}
	
	private void selectGame(GameType selectedGame)
	{
		gameStarted = true;
		
		currentGame = null;
		
		gameFrame.remove(startScreen);
		
		switch (selectedGame)
		{
			
			case ONE_DUCK: 
				// create a one duck game
				currentGame = new DuckHuntGame(gameFrame);
				break;
				
			case CLAY_PIGEON:
				// create a clay pigeon game
				currentGame = new ClayPigeonGame(gameFrame);
				break;
				
			case TEST_GAME:
				currentGame = new TestGame(gameFrame);
				break;
		}
		
		// try creating an anonymous inner SwingWorker
		SwingWorker<Integer, Void> gameTask = new SwingWorker<Integer, Void>()
		{
			@Override public Integer doInBackground()
			{
				currentGame.gameLoop();
				return currentGame.getGameScore();
			}
			
			@Override protected void done()
			{
				try
				{
					gameEnded = true;
				}
				
				catch(Exception ex)
				{
					
				}
			}
		};
		
		// execute the game task
		gameTask.execute();
		//gameEnded = true;
	}
}
