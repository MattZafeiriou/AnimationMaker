package MattZafeiriou.Animations.Screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class InfoBox
{

	private static int currentY = 100;

	public static List< InfoBox > boxes = new ArrayList< InfoBox >();

	public static enum Type
	{
		CHECKBOX, INPUT
	}

	public static InfoBox createBox( String name, int x, Type type )
	{
		InfoBox ib = new InfoBox( name, x, currentY, type );
		boxes.add( ib );
		currentY += 20;
		return ib;
	}

	public static void setBoxesX( int x )
	{
		for( InfoBox box : boxes )
		{
			box.setX( x );
		}
	}

	public static void renderBoxes( Graphics g )
	{
		for( InfoBox box : boxes )
		{
			box.render( g );
		}
	}

	private String name;
	private int x, y;
	private Type type;

	// for type: Type.CHECKBOX
	private boolean isChecked = false;
	// for type: Type.INPUT
	private String input = "";

	private InfoBox( String name, int x, int y, Type type )
	{
		this.name = name;
		this.x = x;
		this.y = y;
		this.type = type;
	}

	public void render( Graphics g )
	{
		g.setColor( Color.white );

		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext( affinetransform, true, true );
		Font font = g.getFont();
		int textwidth = (int) ( font.getStringBounds( name, frc ).getWidth() );
		int textheight = (int) ( font.getStringBounds( name, frc ).getHeight() );

		g.drawString( name, x, y + textheight );

		if( type == Type.CHECKBOX )
		{
			int posX = this.x + textwidth + 10;
			g.setColor( Color.black );
			g.fillRect( posX, y + 2, textheight + 2, textheight + 2 );
			g.setColor( new Color( 220, 220, 220 ) );
			g.fillRect( posX + 1, y + 3, textheight, textheight );
		}
	}

	public void setX( int x )
	{
		this.x = x;
	}

}
