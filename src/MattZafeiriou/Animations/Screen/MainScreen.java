package MattZafeiriou.Animations.Screen;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import MattZafeiriou.Animations.Images.ImageType;
import MattZafeiriou.Animations.Program.ProgramVariables;
import MattZafeiriou.Animations.Screen.Input.ScreenKeyboard;
import MattZafeiriou.Animations.Utils.Loader;
import MattZafeiriou.Animations.Utils.Mouse;
import MattZafeiriou.Animations.Utils.MouseCursor;
import MattZafeiriou.Animations.Utils.Position;
import MattZafeiriou.Animations.Utils.Scaler;

public class MainScreen
{

	// canvasScale = the scale of the canvas
	// scaleKeyValue = the scale of the scale key on the interface
	private float canvasScale = 1, scaleKeyValue = 1;
	// canvas' offsets
	private float XOffset = 0, YOffset = 0, lastXOffset = 0, lastYOffset = 0;
	// used when dragging
	private float lastX = 0, lastY = 0;
	// auto dragging (when stopped dragging)
	private float draggedXCanvas = 0, draggedYCanvas = 0, dragSpeed = 2f;
	// getCanvasPositionX = the starting getCanvasPositionX of the canvas
	// getCanvasPositionY = the starting getCanvasPositionY of the canvas
	// getCanvasWidth = the getCanvasWidth of the canvas
	// getCanvasHeight = the getCanvasHeight of the canvas
	// BackgroundSquaresSize = the default size of every square in the background
	public static int getCanvasPositionX, getCanvasPositionY, getCanvasWidth, getCanvasHeight;
	private int BackgroundSquaresSize = 10, PercentW = 0, PercentH = 0, PercentX = 0, PercentY = 0;
	private static boolean isDraggingScale = false, isDraggingCanvas = false;
	public boolean[] isKeyDown = new boolean[ 256 ];

	private JFrame frame;

	public List< Point > pins = new ArrayList< Point >();

	private Mouse mouse;
	private ScreenKeyboard keyboard;

	public void render( Graphics g )
	{
		int mouseX = Mouse.getInstance().getX();
		int mouseY = Mouse.getInstance().getY();
		if( mouseX >= getCanvasPositionX && mouseX <= getCanvasPositionX + getCanvasWidth
				&& mouseY >= getCanvasPositionY && mouseY <= getCanvasPositionY + getCanvasHeight )
		{
			MouseCursor.changeCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ), 5 );
		}

		canvasScale = Scaler.getCanvasScale();
		scaleKeyValue = Scaler.scaleKeyValue;

		// is in render because we want it to be in the speed of the fps
		drag();

		// clear screen tearing
		float XOffset = this.XOffset;
		float YOffset = this.YOffset;
		float scale = canvasScale;

		// draw background
		drawBackground( g, scale, XOffset, YOffset );

		// draw lines in the center
		g.setColor( Color.black );
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke( new BasicStroke( 2 * canvasScale / 2 ) );
		g.drawLine( Position.getXPoint( 0, XOffset, canvasScale, getCanvasPositionX ), 0,
				Position.getXPoint( 0, XOffset, canvasScale, getCanvasPositionX ),
				getCanvasPositionY + getCanvasHeight );
		g.drawLine( 0, Position.getYPoint( 0, YOffset, canvasScale, getCanvasPositionY ),
				getCanvasPositionX + getCanvasWidth,
				Position.getYPoint( 0, YOffset, canvasScale, getCanvasPositionY ) );
		g2.setStroke( new BasicStroke( 1 ) );

		// draw all user images
		ImageType.drawAllImages( g, getCanvasPositionX, getCanvasPositionY, XOffset, YOffset, canvasScale );
		drawPins( g );

		// draw scale level
		// background
		g.setColor( new Color( 60, 60, 60, 150 ) );
		g.fillRoundRect( getCanvasPositionX + 10, getCanvasPositionY + getCanvasHeight - 150, 50, 140, 8, 8 );
		// foreground
		g.setColor( new Color( 80, 80, 80 ) );
		g.drawRoundRect( getCanvasPositionX + 30, getCanvasPositionY + getCanvasHeight - 130, 10, 100, 5, 8 );

		// + and - strings up and down
		g.setColor( new Color( 50, 50, 50 ) );
		g.drawString( "+", getCanvasPositionX + 30, getCanvasPositionY + getCanvasHeight - 138 );
		g.drawString( "-", getCanvasPositionX + 32, getCanvasPositionY + getCanvasHeight - 13 );

		// circle
		g.setColor( new Color( 36, 103, 112 ) );
		g.fillOval( getCanvasPositionX + 27, getCanvasPositionY + getCanvasHeight - (int) ( 86 * scaleKeyValue ), 16,
				16 );

		// borders
		g.setColor( Color.black );
		g.drawRoundRect( getCanvasPositionX, getCanvasPositionY, getCanvasWidth, getCanvasHeight, 5, 5 );
	}

	private void drawBackground( Graphics g, float scale, float XOffset, float YOffset )
	{
		float xPos = Position.xPos;
		float yPos = Position.yPos;
		int startY = (int) ( YOffset / ( BackgroundSquaresSize * scale )
				+ ( - yPos * scale / ( BackgroundSquaresSize * scale ) ) ) - 1;
		for( int getCanvasPositionY = startY;; getCanvasPositionY++ )
		{
			int yPosition = (int) - YOffset + (int) ( MainScreen.getCanvasPositionY
					+ getCanvasPositionY * scale * BackgroundSquaresSize + yPos * scale );
			// if we got more than the height, stop
			if( yPosition >= MainScreen.getCanvasPositionY + getCanvasHeight )
			{
				break;
			}

			int startX = (int) ( XOffset / ( BackgroundSquaresSize * scale )
					+ ( - xPos * scale / ( BackgroundSquaresSize * scale ) ) ) - 1;

			for( int getCanvasPositionX = startX;; getCanvasPositionX++ )
			{
				int xPosition = (int) - XOffset + (int) ( MainScreen.getCanvasPositionX
						+ getCanvasPositionX * scale * BackgroundSquaresSize + xPos * scale );
				// if we got more than the width, stop
				if( xPosition >= MainScreen.getCanvasPositionX + getCanvasWidth )
					break;

				// change color
				if( ( getCanvasPositionX + getCanvasPositionY ) % 2 == 0 )
					g.setColor( new Color( 80, 80, 80 ) );
				else
					g.setColor( new Color( 120, 120, 120 ) );

				// draw a gray square
				g.fillRect( xPosition, yPosition, (int) ( scale * BackgroundSquaresSize ) + 1,
						(int) ( scale * BackgroundSquaresSize ) + 1 );
			}
		}
	}

	private void drawPins( Graphics g )
	{
		for( int i = 0; i < pins.size(); i++ )
		{

			int x = Position.getXPoint( pins.get( i ).x, XOffset, canvasScale, getCanvasPositionX );
			int y = Position.getYPoint( pins.get( i ).y, YOffset, canvasScale, getCanvasPositionY );

			if( x <= getCanvasPositionX || x >= getCanvasPositionX + getCanvasWidth || y <= getCanvasPositionY
					|| y >= getCanvasPositionY + getCanvasHeight )
			{

				AffineTransform affinetransform = new AffineTransform();
				FontRenderContext frc = new FontRenderContext( affinetransform, true, true );
				Font font = g.getFont();
				int distanceX = (int) Math.abs( XOffset - ( Position.xPos + pins.get( i ).x ) * canvasScale );
				if( x >= getCanvasPositionX + getCanvasWidth )
					distanceX -= getCanvasWidth;

				if( x >= getCanvasPositionX && x <= getCanvasPositionX + getCanvasWidth )
					distanceX = 0;

				int distanceY = (int) Math.abs( YOffset - ( Position.yPos + pins.get( i ).y ) * canvasScale );
				if( y >= getCanvasPositionY + getCanvasHeight )
					distanceY -= getCanvasHeight;

				if( y >= getCanvasPositionY && y <= getCanvasPositionY + getCanvasHeight )
					distanceY = 0;

				String text = "X: " + distanceX + ", Y: " + distanceY;

				int textwidth = (int) ( font.getStringBounds( text, frc ).getWidth() );
				int textheight = (int) ( font.getStringBounds( text, frc ).getHeight() );

				int width = textwidth + 6, height = textheight;
				int finalPositionX = x, finalPositionY = y;

				if( x >= getCanvasPositionX + getCanvasWidth )
					finalPositionX = getCanvasPositionX + getCanvasWidth - width;
				if( x <= getCanvasPositionX )
					finalPositionX = getCanvasPositionX;
				if( y >= getCanvasPositionY + getCanvasHeight - getCanvasPositionY )
					finalPositionY = getCanvasPositionY + getCanvasHeight - height;
				if( y <= getCanvasPositionY )
					finalPositionY = getCanvasPositionY;

				g.setColor( new Color( 50, 50, 50, 220 ) );
				g.fillRect( finalPositionX, finalPositionY, width, height );
				g.setColor( new Color( 255, 255, 255, 220 ) );

				g.drawString( text, finalPositionX + 3, finalPositionY + textheight - 4 );
			} else
			{
				g.setColor( new Color( 204, 25, 54 ) );
				g.fillOval( Position.getXPoint( pins.get( i ).x, XOffset, canvasScale, getCanvasPositionX ) - 2,
						Position.getYPoint( pins.get( i ).y, YOffset, canvasScale, getCanvasPositionY ) - 2, 4, 4 );
			}
		}
	}

	private void drag()
	{
		if( draggedXCanvas != 0 )
		{
			if( draggedXCanvas > 0 )
			{
				XOffset -= draggedXCanvas;
				draggedXCanvas -= dragSpeed;
				if( draggedXCanvas < 0 )
					draggedXCanvas = 0;
			} else
			{
				XOffset -= draggedXCanvas;
				draggedXCanvas += dragSpeed;
				if( draggedXCanvas > 0 )
					draggedXCanvas = 0;
			}
		}
		if( draggedYCanvas != 0 )
		{
			if( draggedYCanvas > 0 )
			{
				YOffset -= draggedYCanvas;
				draggedYCanvas -= dragSpeed;
				if( draggedYCanvas < 0 )
					draggedYCanvas = 0;
			} else
			{
				YOffset -= draggedYCanvas;
				draggedYCanvas += dragSpeed;
				if( draggedYCanvas > 0 )
					draggedYCanvas = 0;
			}
		}
	}

	public void init( int getCanvasPositionX, int getCanvasPositionY, int getCanvasWidth, int getCanvasHeight,
			JFrame frame )
	{
		ImageType.makeImage( Loader.loadImage( "D:/Program Files/User/Pictures/pro.png" ), 10, 10, "Pro" );
		Scaler.init();
		this.PercentW = getCanvasWidth;
		this.PercentH = getCanvasHeight;
		this.PercentX = getCanvasPositionX;
		this.PercentY = getCanvasPositionY;
		MainScreen.getCanvasPositionX = (int) ( frame.getWidth() / 100.0d * PercentX );
		MainScreen.getCanvasPositionY = (int) ( frame.getWidth() / 100.0d * PercentY + 25 );
		MainScreen.getCanvasWidth = (int) ( frame.getWidth() / 100.0d * PercentW );
		MainScreen.getCanvasHeight = (int) ( frame.getHeight() / 100.0d * PercentH - 25 );
		this.frame = frame;
		mouse = Mouse.getInstance( frame );
		mouse.setMainScreen( this );
		keyboard = ScreenKeyboard.getInstance();
		keyboard.setMainScreen( this );
		scaleKeyValue = Scaler.scaleKeyValue;
		XOffset = - MainScreen.getCanvasWidth / 2;
		YOffset = - MainScreen.getCanvasHeight / 2;
		pins.add( new Point( 0, 0 ) );
	}

	public synchronized void tick()
	{
		getCanvasPositionX = (int) ( frame.getWidth() / 100.0d * PercentX );
		getCanvasPositionY = (int) ( frame.getWidth() / 100.0d * PercentY + 25 );
		getCanvasWidth = (int) ( frame.getWidth() / 100.0d * PercentW );
		getCanvasHeight = (int) ( frame.getHeight() / 100.0d * PercentH - 25 );

		if( isDraggingCanvas )
		{
			// if mouse is from the right of the canvas
			if( mouse.getX() > getCanvasPositionX + getCanvasWidth )
			{
				try
				{
					lastX = getCanvasPositionX;
					lastXOffset = XOffset;
					Robot robot = new Robot();
					robot.mouseMove( frame.getX() + getCanvasPositionX + 1, frame.getY() + mouse.getY() );
				} catch( AWTException e )
				{
					e.printStackTrace();
				}
			}
			// if mouse is from the left of the canvas
			else if( getCanvasPositionX > mouse.getX() )
			{
				try
				{
					lastXOffset = XOffset;
					Robot robot = new Robot();
					robot.mouseMove( frame.getX() + getCanvasPositionX + getCanvasWidth, frame.getY() + mouse.getY() );
					lastX = getCanvasPositionX + getCanvasWidth;
				} catch( AWTException e )
				{
					e.printStackTrace();
				}
			}
			// if mouse is from the bottom of the canvas
			else if( mouse.getY() > getCanvasPositionY + getCanvasHeight )
			{
				try
				{
					lastY = getCanvasPositionY;
					lastYOffset = YOffset;
					Robot robot = new Robot();
					robot.mouseMove( frame.getX() + mouse.getX(), frame.getY() + getCanvasPositionY );
				} catch( AWTException e )
				{
					e.printStackTrace();
				}
			}
			// if mouse is from the top of the canvas
			else if( getCanvasPositionY > mouse.getY() )
			{
				try
				{
					lastYOffset = YOffset;
					Robot robot = new Robot();
					robot.mouseMove( frame.getX() + mouse.getX(), frame.getY() + getCanvasPositionY + getCanvasHeight );
					lastY = getCanvasPositionY + getCanvasHeight;
				} catch( AWTException e )
				{
					e.printStackTrace();
				}
			}
		}
	}

	public void mouseClick( int button, int x, int y )
	{
		// left mouse button
		if( button == ProgramVariables.SELECTION_BUTTON )
		{
			if( mouse.mouseIsInside( getCanvasPositionX + 30, getCanvasPositionY + getCanvasHeight - 130, 10, 100 )
					|| isDraggingScale && y >= getCanvasPositionY + getCanvasHeight - 130
							&& y <= getCanvasPositionY + getCanvasHeight - 30 )
			{
				isDraggingScale = true;
				scaleKeyValue -= ( y - ( getCanvasPositionY + getCanvasHeight - (int) ( 86 * scaleKeyValue ) + 8 ) )
						* 0.01;
				Scaler.scaleKeyValue = scaleKeyValue;
				Scaler.Scale( 0, getCanvasWidth, getCanvasHeight, XOffset, YOffset, getCanvasPositionX,
						getCanvasPositionY );
			}
		}

		// right mouse button
		if( button == ProgramVariables.DRAG_BUTTON )
		{
			if( x >= getCanvasPositionX && x <= getCanvasPositionX + getCanvasWidth && y >= getCanvasPositionY
					&& y <= getCanvasPositionY + getCanvasHeight )
			{
				draggedXCanvas = 0;
				draggedYCanvas = 0;
				if( ! isDraggingCanvas )
				{
					lastX = x;
					lastY = y;
					lastXOffset = XOffset;
					lastYOffset = YOffset;
				}
				isDraggingCanvas = true;
				XOffset = lastXOffset + lastX - x;
				YOffset = lastYOffset + lastY - y;
			}
		}
	}

	public void mouseUp( int button )
	{
		if( isDraggingCanvas )
		{
			draggedXCanvas = mouse.getAverageXPosition() * 10;
			draggedYCanvas = mouse.getAverageYPosition() * 10;
		}

		isDraggingScale = false;
		isDraggingCanvas = false;
	}

	public void wheelMove( int direction )
	{
		if( isKeyDown[ KeyEvent.VK_CONTROL ] )
		{
			Scaler.Scale( direction, getCanvasWidth, getCanvasHeight, XOffset, YOffset, getCanvasPositionX,
					getCanvasPositionY );
			scaleKeyValue = Scaler.scaleKeyValue;
		}
	}

	public static boolean isDraggingScale()
	{
		return isDraggingScale;
	}

	public static boolean isDraggingCanvas()
	{
		return isDraggingCanvas;
	}
}
