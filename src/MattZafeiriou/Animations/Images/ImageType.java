package MattZafeiriou.Animations.Images;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import MattZafeiriou.Animations.Program.ProgramVariables;
import MattZafeiriou.Animations.Screen.MainScreen;
import MattZafeiriou.Animations.Utils.Mouse;
import MattZafeiriou.Animations.Utils.Position;

public class ImageType
{

	/*
	 * Static
	 */
	private static List< ImageType > getImageTypes = new ArrayList< ImageType >();

	public static ImageType getSelectedImage;

	public static ImageType makeImage( BufferedImage shownImage, int x, int y )
	{
		ImageType newImage = new ImageType( shownImage, x, y );
		getImageTypes.add( newImage );
		return newImage;
	}

	public static void drawAllImages( Graphics g, int getCanvasPositionX, int getCanvasPositionY, float XOffset,
			float YOffset, float scale )
	{

		for( ImageType image : getImageTypes )
		{
			image.draw( g, getCanvasPositionX, getCanvasPositionY, XOffset, YOffset, scale );
		}
	}

	public static void updateImages()
	{
		for( ImageType image : getImageTypes )
		{
			image.update();
		}
	}

	public static void mouseButtonDown( int button, int x, int y )
	{
		for( ImageType image : getImageTypes )
		{
			image.mouseDown( button, x, y );
		}
	}
	/*
	 * Non-Static
	 */

	private BufferedImage shownImage;

	// image's coords
	public int getPositionX, getPositionY;
	private float XOffset = 0, YOffset = 0, scale = 1;
	private int getCanvasPositionX = 0, getCanvasPositionY = 0;
	private boolean isHovering = false;

	// make this imageType object selected
	public void setSelected( boolean isSelected )
	{
		if( isSelected )
			getSelectedImage = this;
		else
			getSelectedImage = null;
	}

	protected void draw( Graphics g, int getCanvasPositionX, int getCanvasPositionY, float XOffset, float YOffset,
			float scale )
	{
		int x = Position.getXPoint( getPositionX, XOffset, scale, getCanvasPositionX );
		int y = Position.getYPoint( getPositionY, YOffset, scale, getCanvasPositionY );
		int w = (int) ( shownImage.getWidth() * scale );
		int h = (int) ( shownImage.getHeight() * scale );

		int msX = MainScreen.getCanvasPositionX;
		int msY = MainScreen.getCanvasPositionY;
		int msW = MainScreen.getCanvasWidth;
		int msH = MainScreen.getCanvasHeight;

		if( x >= msX + msW || x + w <= msX || y >= msY + msH || y + h <= msY )
			return;

		this.getCanvasPositionX = getCanvasPositionX;
		this.getCanvasPositionY = getCanvasPositionY;
		this.XOffset = XOffset;
		this.YOffset = YOffset;
		this.scale = scale;
		g.drawImage( shownImage, Position.getXPoint( getPositionX, XOffset, scale, getCanvasPositionX ),
				Position.getYPoint( getPositionY, YOffset, scale, getCanvasPositionY ),
				(int) ( shownImage.getWidth() * scale ), (int) ( shownImage.getHeight() * scale ), null );

		if( getSelectedImage == this )
		{
			( (Graphics2D) g ).setStroke( new BasicStroke( 3 * scale ) );

			g.setColor( new Color( 52, 235, 198 ) );
			g.drawRect( Position.getXPoint( getPositionX, XOffset, scale, getCanvasPositionX ),
					Position.getYPoint( getPositionY, YOffset, scale, getCanvasPositionY ),
					(int) ( shownImage.getWidth() * scale ), (int) ( shownImage.getHeight() * scale ) );

			( (Graphics2D) g ).setStroke( new BasicStroke( 1 ) );
		}

		if( getSelectedImage != this && isHovering )
		{
			( (Graphics2D) g ).setStroke( new BasicStroke( 3 * scale ) );

			g.setColor( new Color( 194, 194, 194 ) );
			g.drawRect( Position.getXPoint( getPositionX, XOffset, scale, getCanvasPositionX ),
					Position.getYPoint( getPositionY, YOffset, scale, getCanvasPositionY ),
					(int) ( shownImage.getWidth() * scale ), (int) ( shownImage.getHeight() * scale ) );

			( (Graphics2D) g ).setStroke( new BasicStroke( 1 ) );
		}
	}

	public void update()
	{
		int x = Position.getXPoint( getPositionX, XOffset, scale, getCanvasPositionX );
		int y = Position.getYPoint( getPositionY, YOffset, scale, getCanvasPositionY );
		int w = (int) ( shownImage.getWidth() * scale );
		int h = (int) ( shownImage.getHeight() * scale );

		int msX = MainScreen.getCanvasPositionX;
		int msY = MainScreen.getCanvasPositionY;
		int msW = MainScreen.getCanvasWidth;
		int msH = MainScreen.getCanvasHeight;

		if( x >= msX + msW || x + w <= msX || y >= msY + msH || y + h <= msY )
			return;

		int mouseX = Mouse.getInstance().getX();
		int mouseY = Mouse.getInstance().getY();
		if( mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h )
		{
			isHovering = true;
		} else
			isHovering = false;
	}

	public void mouseDown( int button, int x, int y )
	{
		int msX = MainScreen.getCanvasPositionX;
		int msY = MainScreen.getCanvasPositionY;
		int msW = MainScreen.getCanvasWidth;
		int msH = MainScreen.getCanvasHeight;

		if( x >= msX + msW || x <= msX || y >= msY + msH || y <= msY )
			return;

		if( button == ProgramVariables.SELECTION_BUTTON )
		{
			int posX = Position.getXPoint( getPositionX, XOffset, scale, getCanvasPositionX );
			int posY = Position.getYPoint( getPositionY, YOffset, scale, getCanvasPositionY );
			int w = (int) ( shownImage.getWidth() * scale );
			int h = (int) ( shownImage.getHeight() * scale );
			if( x >= posX && x <= posX + w && y >= posY && y <= posY + h )
			{
				setSelected( true );
			} else
				setSelected( false );
		}
	}

	/*
	 * Constructor
	 */

	private ImageType( BufferedImage shownImage, int x, int y )
	{
		setSelected( false );
		this.shownImage = shownImage;
		getPositionX = x;
		getPositionY = y;
	}

}
