package MattZafeiriou.Animations.Screen;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;

import javax.swing.JFrame;

import MattZafeiriou.Animations.Utils.Mouse;
import MattZafeiriou.Animations.Utils.MouseCursor;

public class InfoScreen
{

	private static JFrame frame;

	private int percentX = 0, percentY = 0, percentW = 0, percentH = 0;
	private static int Width = 0, Height = 0, X = 0, Y = 0;

	public void init( JFrame jframe, int percentX, int percentY, int percentW, int percentH )
	{
		frame = jframe;
		this.percentX = percentX;
		this.percentY = percentY;
		this.percentW = percentW;
		this.percentH = percentH;

		X = ( frame.getWidth() / 100 ) * percentX;
		Y = ( frame.getWidth() / 100 ) * percentY + 25;
		Width = ( frame.getWidth() / 100 ) * percentW;
		Height = ( frame.getWidth() / 100 ) * percentH - 25;
	}

	public void render( Graphics g )
	{
		int mouseX = Mouse.getInstance().getX();
		int mouseY = Mouse.getInstance().getY();
		if( mouseX >= X && mouseX <= X + Width && mouseY >= Y && mouseY <= Y + Height )
		{
			MouseCursor.changeCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ), 5 );
		}

		g.setColor( new Color( 60, 60, 60 ) );
		g.fillRect( X, Y, Width, Height );

		InfoBox.renderBoxes( g );

		g.setColor( Color.black );
		g.drawRoundRect( X, Y, Width, Height, 3, 3 );
	}

	public void update()
	{
		X = (int) ( ( frame.getWidth() / 100.0d ) * percentX );
		Y = (int) ( ( frame.getWidth() / 100.0d ) * percentY + 25 );
		Width = (int) ( ( frame.getWidth() / 100.0d ) * percentW );
		Height = (int) ( ( frame.getWidth() / 100.0d ) * percentH - 25 );
	}

	public static void createBox( String name, InfoBox.Type type, int maxChars )
	{
		InfoBox.createBox( name, Y, X + 10, type, frame, maxChars );
	}

	public static void createBox( String name, InfoBox.Type type, int maxChars, String value )
	{
		InfoBox.createBox( name, Y, X + 10, type, frame, maxChars ).setInputValue( value );
		;
	}

	public static void createBox( String name, InfoBox.Type type, int maxChars, boolean value )
	{
		InfoBox.createBox( name, Y, X + 10, type, frame, maxChars ).setCheckBoxValue( value );
		;
	}

}
