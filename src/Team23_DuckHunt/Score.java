package Team23_DuckHunt;

public class Score
{
	private int score;
	private String playerInitials;
	
	public Score(String initials, int score)
	{
		this.setInitials(initials);
		this.setScore(score);
	}
	
	public int getScore()
	{
		return score;
	}
	
	public void setScore(int score)
	{
		this.score = score;
	}
	
	public String getInitials()
	{
		return playerInitials;
	}
	
	public void setInitials(String playerInitials)
	{
		this.playerInitials = playerInitials;
	}
	
	@Override
	public String toString()
	{
		return playerInitials + " " + score;
	}
}
