package MattZafeiriou.Animations.Utils;

import MattZafeiriou.Animations.Smoother;

public class Scaler
{

	private static float canvasScale = 0;
	public static float scaleKeyValue;
	private static int speed = 20;
	private static float xDifference = 0, yDifference = 0;

	private static Smoother canvasScaler = new Smoother( 1, 1, 1 );

	public static void init()
	{
		scaleKeyValue = (float) Math.sqrt( 0.25 );
		canvasScale = scaleFunction( scaleKeyValue );
	}

	public static void Scale( int direction, int getCanvasWidth, int getCanvasHeight, float XOffset, float YOffset,
			int getCanvasPositionX, int getCanvasPositionY )
	{
		scaleKeyValue += - direction * 0.025;
		if( scaleKeyValue > 1.6f )
			scaleKeyValue = 1.6f;
		if( scaleKeyValue < .46f )
			scaleKeyValue = .46f;
		float result = scaleFunction( scaleKeyValue );
		if( result > 10 )
			result = 10;
		if( result <= 0 )
			result = 0.1f;

		canvasScale = canvasScaler.getEnd();
		canvasScaler = new Smoother( canvasScale, result, speed );

		int mouseX = Mouse.getInstance().getX();
		int mouseY = Mouse.getInstance().getY();

		// difference between the mouse position and the position 0, 0
		xDifference = ( mouseX - Position.getXPoint( 0, XOffset, canvasScale, getCanvasPositionX ) ) / 2;
		yDifference = ( mouseY - Position.getYPoint( 0, YOffset, canvasScale, getCanvasPositionY ) ) / 2;

		xDifference = Math.abs( xDifference );
		yDifference = Math.abs( yDifference );

		xDifference = Math.min( 8 * ( 11 - canvasScale ), xDifference );
		yDifference = Math.min( 8 * ( 11 - canvasScale ), yDifference );

		xDifference = ( xDifference / 10 ) * ( 11 - canvasScale );
		yDifference = ( yDifference / 10 ) * ( 11 - canvasScale );

		if( mouseX < Position.getXPoint( 0, XOffset, canvasScale, getCanvasPositionX ) )
			xDifference *= - 1;

		if( mouseY < Position.getYPoint( 0, YOffset, canvasScale, getCanvasPositionY ) )
			yDifference *= - 1;

		changePositionCoords();
	}

	public static float getCanvasScale()
	{

		canvasScale = canvasScaler.getValue();
		if( canvasScale > 10 )
			canvasScale = 10;
		if( canvasScale <= 0 )
			canvasScale = 0.1f;
		changePositionCoords();

		return canvasScale;
	}

	private static void changePositionCoords()
	{
		Position.xPos = - xDifference / 2 * canvasScale;
		Position.yPos = - yDifference / 2 * canvasScale;
	}

	private static float scaleFunction( float getCanvasPositionX )
	{
		return (float) ( getCanvasPositionX * getCanvasPositionX * 0.4 * ( getCanvasPositionX > 0 ? 10 : - 10 ) );
	}

}
