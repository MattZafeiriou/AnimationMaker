package MattZafeiriou.Animations.Topbar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import MattZafeiriou.Animations.Program.ProgramVariables;
import MattZafeiriou.Animations.Utils.Loader;
import MattZafeiriou.Animations.Utils.Mouse;

public class Bar
{

	private static List< Bar > bars = new ArrayList< Bar >();
	private static Font font = Loader.loadFont( "AnimationMaker/res/Fonts/Roboto-Bold.ttf" );

	private static int lastX = 10;

	public static void createBar( String text, ElementAction[] elements )
	{
		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext( affinetransform, true, true );

		int textwidth = (int) ( font.getStringBounds( text, frc ).getWidth() );
		int textheight = (int) ( font.getStringBounds( text, frc ).getHeight() );
		int height = 21;
		int maxWidth = 0;

		for( int i = 0; i < elements.length; i++ )
		{
			int w = (int) ( font.getStringBounds( elements[ i ].text, frc ).getWidth() );
			if( w > maxWidth )
				maxWidth = w;
		}
		// make elements
		List< Element > element = new ArrayList< Element >();
		for( int i = 0; i < elements.length; i++ )
		{
			int txtHeight = (int) ( font.getStringBounds( elements[ i ].text, frc ).getHeight() );
			element.add( new Element( elements[ i ].text, lastX, 2 + height * ( i + 1 ), maxWidth + 2 + 4, height,
					txtHeight, elements[ i ] ) );
		}

		Bar newBar = new Bar( text, element, lastX, 2, textwidth + 10, height, textheight );

		int spaceBetweenBars = 7;
		lastX += textwidth + spaceBetweenBars + 10;
		bars.add( newBar );
	}

	public static void renderBars( Graphics g )
	{
		for( int i = 0; i < bars.size(); i++ )
		{
			bars.get( i ).render( g );
		}
	}

	public static void updateBars()
	{
		for( int i = 0; i < bars.size(); i++ )
		{
			bars.get( i ).update();
		}
	}

	public static boolean mouseDown( int button, int x, int y )
	{
		boolean used = false;
		for( int i = 0; i < bars.size(); i++ )
		{
			if( bars.get( i ).mouseIsDown( button, x, y ) )
				used = true;
		}
		return used;
	}

	private String text = "";
	private List< Element > elements;

	private boolean isHovering = false, isOpen = false;

	private int x, y, width, height, stringHeight;

	private Bar( String text, List< Element > elements, int x, int y, int width, int height, int stringHeight )
	{
		this.text = text;
		this.elements = elements;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.stringHeight = stringHeight;
	}

	public void render( Graphics g )
	{
		g.setFont( font );

		// draw background
		g.setColor( new Color( 5, 5, 5, 100 ) );
		g.fillRoundRect( x - 1, y - 1, width + 2, height + 2, 7, 7 );
		if( isHovering || isOpen )
			g.setColor( new Color( 60, 60, 60 ) );
		else
			g.setColor( new Color( 100, 100, 100 ) );
		g.fillRoundRect( x, y, width, height, 7, 7 );

		// draw name
		g.setColor( Color.white );
		g.drawString( text, x + 5, y + stringHeight );

		// draw drop down
		if( isOpen )
		{
			for( int i = 0; i < elements.size(); i++ )
			{
				elements.get( i ).render( g );
			}
		}
	}

	public void update()
	{
		Mouse mouse = Mouse.getInstance();
		if( mouse.getX() >= x && mouse.getX() <= x + width && mouse.getY() >= y && mouse.getY() <= y + height )
		{
			isHovering = true;
		} else
			isHovering = false;

		for( int i = 0; i < elements.size(); i++ )
		{
			elements.get( i ).update();
			elements.get( i ).isOpen = isOpen;
		}
	}

	public boolean mouseIsDown( int button, int x, int y )
	{
		boolean used = false;
		for( int i = 0; i < elements.size(); i++ )
		{
			if( elements.get( i ).mouseRelease( button, x, y ) )
				used = true;
		}
		if( button == ProgramVariables.OK_BUTTON )
			if( x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height )
			{
				isOpen = ! isOpen;
				used = true;
			} else
				isOpen = false;

		return used;
	}

}
