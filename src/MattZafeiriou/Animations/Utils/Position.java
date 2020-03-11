package MattZafeiriou.Animations.Utils;

public class Position
{

	public static float xPos = 0, yPos = 0;

	public static int getXPoint( int x, float xOffset, float scale, int start )
	{
		return (int) ( - xOffset + xPos * scale + ( ( x ) * scale ) + start );
	}

	public static int getYPoint( int y, float yOffset, float scale, int start )
	{
		return (int) ( - yOffset + ( ( y + yPos ) * scale ) + start );
	}
}