package MattZafeiriou.Animations.Utils;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import MattZafeiriou.Animations.Images.ImageType;
import MattZafeiriou.Animations.Program.ProgramVariables;
import MattZafeiriou.Animations.Screen.MainScreen;
import MattZafeiriou.Animations.Topbar.Bar;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener
{

	private static Mouse mouse;
	private JFrame frame;
	private MainScreen mainScreen;
	private int[] xPositions = new int[ 10 ], yPositions = new int[ 10 ];
	private int xPosition = 0, yPosition = 0;
	private int x = 0, y = 0;

	private Mouse( JFrame frame )
	{
		this.frame = frame;
	}

	public static Mouse getInstance( JFrame frame )
	{
		if( mouse == null )
			mouse = new Mouse( frame );
		return mouse;
	}

	public static Mouse getInstance()
	{
		return mouse;
	}

	@Override
	public void mouseClicked( MouseEvent arg0 )
	{
	}

	@Override
	public void mouseEntered( MouseEvent arg0 )
	{
	}

	@Override
	public void mouseExited( MouseEvent arg0 )
	{
	}

	@Override
	public void mousePressed( MouseEvent e )
	{
		if( e.getButton() == ProgramVariables.OK_BUTTON )
		{ // left mouse click
			// minimize
			if( mouse.pointIsInside( frame.getWidth() - 130, - 10, 40, 30, e.getX(), e.getY() ) )
			{
				frame.setState( JFrame.ICONIFIED );
			}
			// maximize
			if( mouse.pointIsInside( frame.getWidth() - 90, - 10, 40, 30, e.getX(), e.getY() ) )
			{
				if( frame.getExtendedState() == JFrame.MAXIMIZED_BOTH )
					frame.setExtendedState( JFrame.NORMAL );
				else
					frame.setExtendedState( JFrame.MAXIMIZED_BOTH );
			}
			// close
			if( mouse.pointIsInside( frame.getWidth() - 50, - 10, 40, 30, e.getX(), e.getY() ) )
			{
				System.exit( 0 );
			}
		}
		mainScreen.mouseClick( e.getButton(), e.getX(), e.getY() );

		if( ! Bar.mouseDown( e.getButton(), e.getX(), e.getY() ) && ! MainScreen.isDraggingCanvas()
				&& ! MainScreen.isDraggingScale() )
			ImageType.mouseButtonDown( e.getButton(), e.getX(), e.getY() );
	}

	@Override
	public void mouseReleased( MouseEvent e )
	{
		mainScreen.mouseUp( e.getButton() );
	}

	@Override
	public void mouseDragged( MouseEvent e )
	{
		if( Math.abs( e.getX() - x ) > 50 )
		{
			xPosition = 0;
		} else
			xPositions[ xPosition ] = e.getX() - x;

		if( Math.abs( e.getY() - y ) > 50 )
		{
			yPosition = 0;
		} else
			yPositions[ yPosition ] = e.getY() - y;

		if( SwingUtilities.isLeftMouseButton( e ) )
			mainScreen.mouseClick( 1, e.getX(), e.getY() );
		if( SwingUtilities.isMiddleMouseButton( e ) )
			mainScreen.mouseClick( 2, e.getX(), e.getY() );
		if( SwingUtilities.isRightMouseButton( e ) )
			mainScreen.mouseClick( 3, e.getX(), e.getY() );
		x = e.getX();
		y = e.getY();

		xPosition++;
		yPosition++;
		if( xPosition == 10 )
			xPosition = 0;
		if( yPosition == 10 )
			yPosition = 0;
	}

	@Override
	public void mouseWheelMoved( MouseWheelEvent e )
	{
		mainScreen.wheelMove( e.getWheelRotation() );
	}

	@Override
	public void mouseMoved( MouseEvent e )
	{
		x = e.getX();
		y = e.getY();
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public void setMainScreen( MainScreen mainScreen )
	{
		this.mainScreen = mainScreen;
	}

	public boolean mouseIsInside( int x, int y, int width, int height )
	{
		return pointIsInside( x, y, width, height, getX(), getY() );
	}

	public boolean pointIsInside( int x, int y, int width, int height, int positionX, int positionY )
	{
		if( positionX >= x && positionX <= x + width && positionY >= y && positionY <= y + height )
			return true;
		else
			return false;
	}

	public float getAverageXPosition()
	{
		return (float) Arrays.stream( xPositions ).average().orElse( Double.NaN );
	}

	public float getAverageYPosition()
	{
		return (float) Arrays.stream( yPositions ).average().orElse( Double.NaN );
	}

}
