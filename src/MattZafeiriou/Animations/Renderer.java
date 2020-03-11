package MattZafeiriou.Animations;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import MattZafeiriou.Animations.Screen.InfoScreen;
import MattZafeiriou.Animations.Screen.MainScreen;
import MattZafeiriou.Animations.Topbar.Bar;
import MattZafeiriou.Animations.Utils.Loader;
import MattZafeiriou.Animations.Utils.Mouse;
import MattZafeiriou.Animations.Utils.MouseCursor;

public class Renderer
{

	private static Graphics g;
	private static BufferStrategy bs;
	private static Canvas canvas;
	private static int width, height;
	private static Mouse mouse;
	private static MainScreen mainScreen = new MainScreen();
	private static InfoScreen infoScreen = new InfoScreen();

	private static BufferedImage Minimize = Loader.loadImage( "AnimationMaker/res/Buttons/Minimize.png" );
	private static BufferedImage Maximize = Loader.loadImage( "AnimationMaker/res/Buttons/Maximize.png" );
	private static BufferedImage Close = Loader.loadImage( "AnimationMaker/res/Buttons/Close.png" );

	public static void init( JFrame frame, Canvas c, int buffers )
	{
		canvas = c;
		if( bs == null )
		{
			canvas.createBufferStrategy( buffers );
			bs = canvas.getBufferStrategy();
		}
		mainScreen.init( 0, 0, 80, 100, frame );
		infoScreen.init( frame, 80, 0, 20, 100 );
		mouse = Mouse.getInstance( frame );
	}

	public static void prepare( int Width, int Height )
	{
		width = Width;
		height = Height;

		g = bs.getDrawGraphics();
		try
		{
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont( Font.createFont( Font.TRUETYPE_FONT, new File( "/Fonts/IbarraRealNova-Regular.ttf" ) ) );
		} catch( IOException | FontFormatException e )
		{
			// Handle exception
		}
		( (Graphics2D) g ).setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		Font currentFont = g.getFont();
		Font newFont = currentFont.deriveFont( 18f );
		g.setFont( newFont );
		g.clearRect( 0, 0, width, height );
	}

	public static void render( JFrame frame )
	{
		// screen draw
		mainScreen.render( g );
		infoScreen.render( g );

		// menu bar
		g.setColor( new Color( 80, 80, 80 ) );
		g.fillRect( 0, 0, width, 25 );

		drawTopButtons( 40, 30 );

		Bar.renderBars( g );

		MouseCursor.setCursor();
	}

	private static void drawTopButtons( int ButtonWidth, int ButtonHeight )
	{
		// buttons bar
		g.setColor( new Color( 30, 30, 30 ) );
		g.drawRoundRect( width - ButtonWidth * 3 - 10, - 10, ButtonWidth * 3, ButtonHeight, 10, 20 );
		// minimize button
		// button background
		g.setColor( mouse.mouseIsInside( width - 10 - ButtonWidth * 3, - 10, ButtonWidth, ButtonHeight )
				? new Color( 20, 20, 20 )
				: new Color( 50, 50, 50 ) );
		g.fillRoundRect( width - 10 - ButtonWidth * 3, - 10, ButtonWidth + 4, ButtonHeight, 10, 20 );

		// draw image
		g.drawImage( Minimize, width - ButtonWidth * 3 + 2, 8, null );

		// close button
		// button background
		g.setColor( mouse.mouseIsInside( width - 10 - ButtonWidth, - 10, ButtonWidth, ButtonHeight )
				? new Color( 20, 20, 20 )
				: new Color( 50, 50, 50 ) );
		g.fillRoundRect( width - 10 - ButtonWidth - 2, - 10, ButtonWidth + 2, ButtonHeight, 10, 20 );

		// draw image
		g.drawImage( Close, width - ButtonWidth + 3, 5, null );

		// maximize button
		// button background
		g.setColor( mouse.mouseIsInside( width - 10 - ButtonWidth * 2, - 10, ButtonWidth, ButtonHeight )
				? new Color( 20, 20, 20 )
				: new Color( 50, 50, 50 ) );
		g.fillRect( width - 10 - ButtonWidth * 2, - 10, ButtonWidth, ButtonHeight );

		// draw image
		g.drawImage( Maximize, width - ButtonWidth * 2 + 5, 5, null );

		// button separator lines
		g.setColor( new Color( 30, 30, 30 ) );
		g.drawLine( width - ButtonWidth - 10, 0, width - ButtonWidth - 10, ButtonHeight - 10 );
		g.drawLine( width - ButtonWidth * 2 - 10, 0, width - ButtonWidth * 2 - 10, ButtonHeight - 10 );
	}

	public static void update()
	{
		mainScreen.tick();
		infoScreen.update();
	}

	public static void show()
	{
		bs.show();
		g.dispose();
	}

}
