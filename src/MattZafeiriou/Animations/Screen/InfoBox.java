package MattZafeiriou.Animations.Screen;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import MattZafeiriou.Animations.Utils.ImageUtils;
import MattZafeiriou.Animations.Utils.Mouse;
import MattZafeiriou.Animations.Utils.MouseCursor;

public class InfoBox
{

	private static int currentY = 10;

	public static List< InfoBox > boxes = new ArrayList< InfoBox >();
	private static JFrame frame;

	public static enum Type
	{
		CHECKBOX, INPUT
	}

	/**
	 * Creates a new infoBox object and returns it
	 *
	 * @param name     The name of the infobox. Must be unique.
	 * @param minimumY The minimum y location on the canvas we can draw it
	 * @param x        The x position on the canvas we should draw it
	 * @param type     The type of the infobox
	 * @param jframe   The frame the canvas is. We need some variables from it.
	 * @param maxChars The maximum amount of the chars the text field should have.
	 *                 Only for type == INPUT
	 * @return The created infobox
	 */
	public static InfoBox createBox( String name, int minimumY, int x, Type type, JFrame jframe, int maxChars )
	{
		if( getBoxByName( name ) == null )
		{
			InfoBox ib = new InfoBox( name, x, currentY + minimumY, type, maxChars );
			frame = jframe;
			boxes.add( ib );
			currentY += 30;
			return ib;
		} else
			return null;
	}

	public static void makeSection( int height )
	{
		currentY += height;
	}

	public static void deleteBoxes()
	{
		currentY = 10;
		boxes = new ArrayList< InfoBox >();
	}

	public static InfoBox getBoxByName( String name )
	{
		InfoBox box = null;

		for( InfoBox i : boxes )
		{
			if( i.name.equals( name ) )
			{
				box = i;
				break;
			}
		}

		return box;
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

	public static void mouseButtonUp( int button, int x, int y )
	{
		for( InfoBox box : boxes )
		{
			box.mouseUp( button, x, y );
		}
	}

	public static void keyButtonDown( char key, int keyCode )
	{
		for( InfoBox box : boxes )
		{
			box.keyDown( key, keyCode );
		}
	}

	private String name;
	private int x, y, stringWidth = 0, stringHeight = 0, cursorPos = - 1, toggleCursor = 0, maxChars = 25;
	private Type type;
	private Font inputFont = null;
	private boolean cursorShown = false;

	// for type: Type.CHECKBOX
	private boolean isChecked = false;
	// for type: Type.INPUT
	private String input = "";
	private boolean canType = false;

	private InfoBox( String name, int x, int y, Type type, int maxChars )
	{
		this.name = name;
		this.x = x;
		this.y = y;
		this.type = type;
		this.maxChars = maxChars;
	}

	public void render( Graphics g )
	{
		if( ++toggleCursor == 25 )
		{
			toggleCursor = 0;
			cursorShown = ! cursorShown;
		}
		if( inputFont == null )
		{
			inputFont = new Font( g.getFont().getName(), Font.PLAIN, (int) ( g.getFont().getSize() * 0.8f ) );

		}

		g.setColor( Color.white );

		if( stringWidth == 0 )
		{
			AffineTransform affinetransform = new AffineTransform();
			FontRenderContext frc = new FontRenderContext( affinetransform, true, true );
			Font font = g.getFont();
			stringWidth = (int) ( font.getStringBounds( name, frc ).getWidth() );
			stringHeight = (int) ( font.getStringBounds( name, frc ).getHeight() );
		}

		g.drawString( name, x, y + stringHeight );

		if( type == Type.CHECKBOX )
		{
			int posX = this.x + stringWidth + 10;
			g.setColor( Color.black );
			g.fillRect( posX, y + 2, stringHeight + 2, stringHeight + 2 );
			g.setColor( new Color( 220, 220, 220 ) );
			g.fillRect( posX + 1, y + 3, stringHeight, stringHeight );

			if( isChecked )
			{
				g.drawLine( posX + 1, y + 3, posX + 1 + stringHeight, y + 3 + stringHeight );
				g.drawLine( posX + 1 + stringHeight, y + 3, posX + 1, y + 3 + stringHeight );

			}
		}

		if( type == Type.INPUT )
		{
			int posX = this.x + stringWidth + 10;
			int width = frame.getWidth() - posX - 10;
			int mouseX = Mouse.getInstance().getX();
			int mouseY = Mouse.getInstance().getY();
			if( mouseX >= posX && mouseX <= posX + width && mouseY >= this.y + 2
					&& mouseY <= this.y + 4 + stringHeight )
			{
				MouseCursor.changeCursor( Cursor.getPredefinedCursor( Cursor.TEXT_CURSOR ), 10 );
			}

			g.setColor( Color.black );
			g.setColor( Color.black );
			g.fillRect( posX, y + 2, width, stringHeight + 2 );
			g.setColor( new Color( 220, 220, 220 ) );
			g.fillRect( posX + 1, y + 3, width - 2, stringHeight );

			BufferedImage imgText = ImageUtils.makeTextToImage( input, width - 4, stringHeight, inputFont, Color.black,
					( cursorShown ) ? cursorPos : - 1 );

			g.drawImage( imgText, posX + 2, y + 4, null );
		}
	}

	public void setInputValue( String value )
	{
		input = value;
	}

	public void setCheckBoxValue( boolean value )
	{
		this.isChecked = value;
	}

	public String getInputValue()
	{
		return input;
	}

	public boolean getCheckBoxValue()
	{
		return isChecked;
	}

	public void mouseUp( int button, int x, int y )
	{
		if( type == Type.CHECKBOX )
		{
			int posX = this.x + stringWidth + 10;
			if( x >= this.x && x <= posX + stringHeight && y >= this.y + 2 && y <= this.y + 2 + stringHeight )
			{
				isChecked = ! isChecked;
			}
		} else if( type == Type.INPUT )
		{
			int posX = this.x + stringWidth + 10;
			int width = frame.getWidth() - posX - 10;
			if( x >= this.x && x <= posX + width && y >= this.y + 2 && y <= this.y + 2 + stringHeight )
			{
				if( ! canType )
				{
					canType = true;
					toggleCursor = 0;
					cursorShown = true;
					cursorPos = input.length();
				}
			} else
			{
				canType = false;
				toggleCursor = 0;
				cursorShown = false;
				cursorPos = - 1;
			}
		}
	}

	public void keyDown( char key, int keyCode )
	{
		if( type == Type.INPUT )
		{
			if( canType )
			{
				if( inputFont.canDisplay( key ) && input.length() < maxChars )
				{
					if( input.length() > 0 )
					{
						if( ! ( input.charAt( input.length() - 1 ) == ' ' && key == ' ' ) )
						{
							input = input.substring( 0, cursorPos ) + key + input.substring( cursorPos );
							cursorPos++;
						}
					} else
					{
						input += key;
						cursorPos++;
					}

					toggleCursor = 0;
					cursorShown = true;
				} else if( keyCode == 8 )
				{
					if( input.length() != 0 )
					{
						if( cursorPos != 0 )
						{
							input = input.substring( 0, cursorPos - 1 ) + input.substring( cursorPos );
							cursorPos--;
						}
						toggleCursor = 0;
						cursorShown = true;
					}
				} else if( keyCode == 37 )
				{
					if( --cursorPos < 0 )
						cursorPos = 0;

					toggleCursor = 0;
					cursorShown = true;
				} else if( keyCode == 39 )
				{
					if( ++cursorPos > input.length() )
						cursorPos = input.length();

					toggleCursor = 0;
					cursorShown = true;
				}
			}
		}
	}

	public void setX( int x )
	{
		this.x = x;
	}

}
