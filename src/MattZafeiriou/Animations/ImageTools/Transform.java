package MattZafeiriou.Animations.ImageTools;

import java.awt.BasicStroke;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.QuadCurve2D;

import MattZafeiriou.Animations.Program.ProgramVariables;
import MattZafeiriou.Animations.Screen.MainScreen;
import MattZafeiriou.Animations.Utils.Mouse;
import MattZafeiriou.Animations.Utils.MouseCursor;

public class Transform
{

	// the height from the bottom of the main screen
	private int y = 160;
	private int x = 10;
	private int size = 48;
	private boolean hovering = false;

	public void render( Graphics g )
	{
		g.setColor( ProgramVariables.HIGHLIGHT_COLOR );
		// bottom left
		QuadCurve2D.Double curve = new QuadCurve2D.Double( MainScreen.getCanvasPositionX + x,
				MainScreen.getCanvasPositionY + MainScreen.getCanvasHeight - y - ( size / 3 ),
				MainScreen.getCanvasPositionX + x, MainScreen.getCanvasPositionY + MainScreen.getCanvasHeight - y,
				MainScreen.getCanvasPositionX + x + ( size / 3 ),
				MainScreen.getCanvasPositionY + MainScreen.getCanvasHeight - y );
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke( new BasicStroke( 2 ) );
		g2d.draw( curve );

		// bottom right
		curve = new QuadCurve2D.Double( MainScreen.getCanvasPositionX + x + size,
				MainScreen.getCanvasPositionY + MainScreen.getCanvasHeight - y - ( size / 3 ),
				MainScreen.getCanvasPositionX + x + size,
				MainScreen.getCanvasPositionY + MainScreen.getCanvasHeight - y,
				MainScreen.getCanvasPositionX + x + ( size / 3 ) * 2,
				MainScreen.getCanvasPositionY + MainScreen.getCanvasHeight - y );
		g2d.draw( curve );

		// top right
		curve = new QuadCurve2D.Double( MainScreen.getCanvasPositionX + x + size,
				MainScreen.getCanvasPositionY + MainScreen.getCanvasHeight - y - ( size / 3 ) * 2,
				MainScreen.getCanvasPositionX + x + size,
				MainScreen.getCanvasPositionY + MainScreen.getCanvasHeight - y - size,
				MainScreen.getCanvasPositionX + x + ( size / 3 ) * 2,
				MainScreen.getCanvasPositionY + MainScreen.getCanvasHeight - y - size );
		g2d.draw( curve );

		// top left
		curve = new QuadCurve2D.Double( MainScreen.getCanvasPositionX + x,
				MainScreen.getCanvasPositionY + MainScreen.getCanvasHeight - y - ( size / 3 ) * 2,
				MainScreen.getCanvasPositionX + x,
				MainScreen.getCanvasPositionY + MainScreen.getCanvasHeight - y - size,
				MainScreen.getCanvasPositionX + x + ( size / 3 ),
				MainScreen.getCanvasPositionY + MainScreen.getCanvasHeight - y - size );
		g2d.draw( curve );

		if( hovering )
			g.setColor( ProgramVariables.HOVER_TOOLS_COLOR );

		else
			g.setColor( ProgramVariables.TOOLS_COLOR );

		// vertical line
		g.fillRect( MainScreen.getCanvasPositionX + x + ( size / 2 ) - 1,
				MainScreen.getCanvasPositionY + MainScreen.getCanvasHeight - y - size + (int) ( size / 10.0f ), 2,
				(int) ( ( size / 10.0f ) * 8 ) + 1 );
		// horizontal line
		g.fillRect( MainScreen.getCanvasPositionX + x + (int) ( size / 10.0f ),
				MainScreen.getCanvasPositionY + MainScreen.getCanvasHeight - y - ( size / 2 ) + 1,
				// -2 because the stroke == 2
				(int) ( ( size / 10.0f ) * 8 ) + 1, 2 );

		// top triangle
		int x[] =
		{ MainScreen.getCanvasPositionX + this.x + ( size / 2 ) - 2 - ( size / 20 ),
				MainScreen.getCanvasPositionX + this.x + ( size / 2 ),
				MainScreen.getCanvasPositionX + this.x + ( size / 2 ) + 2 + ( size / 20 ) };
		int y[] =
		{ MainScreen.getCanvasPositionY + MainScreen.getCanvasHeight - this.y - size + (int) ( size / 10.0f ) + 2,
				MainScreen.getCanvasPositionY + MainScreen.getCanvasHeight - this.y - size - 1,
				MainScreen.getCanvasPositionY + MainScreen.getCanvasHeight - this.y - size + (int) ( size / 10.0f )
						+ 2 };
		g.fillPolygon( x, y, x.length );

		// bottom triangle
		y = new int[]
		{ MainScreen.getCanvasPositionY + MainScreen.getCanvasHeight - this.y - (int) ( size / 10.0f ) - 2,
				MainScreen.getCanvasPositionY + MainScreen.getCanvasHeight - this.y + 1,
				MainScreen.getCanvasPositionY + MainScreen.getCanvasHeight - this.y - (int) ( size / 10.0f ) - 2 };
		g.fillPolygon( x, y, x.length );

		// left triangle
		x = new int[]
		{ MainScreen.getCanvasPositionX + this.x + (int) ( size / 10.0f ) + 2,
				MainScreen.getCanvasPositionX + this.x - 1,
				MainScreen.getCanvasPositionX + this.x + (int) ( size / 10.0f ) + 2 };
		y = new int[]
		{ MainScreen.getCanvasPositionY + MainScreen.getCanvasHeight - this.y - ( size / 2 ) - ( size / 20 ),
				MainScreen.getCanvasPositionY + MainScreen.getCanvasHeight - this.y - ( size / 2 ) + 2,
				MainScreen.getCanvasPositionY + MainScreen.getCanvasHeight - this.y - ( size / 2 ) + 4
						+ ( size / 20 ) };
		g.fillPolygon( x, y, x.length );

		// right triangle
		x = new int[]
		{ MainScreen.getCanvasPositionX + this.x - (int) ( size / 10.0f ) + size - 2,
				MainScreen.getCanvasPositionX + this.x + 1 + size,
				MainScreen.getCanvasPositionX + this.x - (int) ( size / 10.0f ) + size - 2 };
		g.fillPolygon( x, y, x.length );

		g2d.setStroke( new BasicStroke( 1 ) );
	}

	public void update()
	{
		int mouseX = Mouse.getInstance().getX();
		int mouseY = Mouse.getInstance().getY();
		int x = MainScreen.getCanvasPositionX + this.x;
		int y = MainScreen.getCanvasPositionY + MainScreen.getCanvasHeight - this.y - size + 1;

		if( mouseX >= x && mouseX <= x + size && mouseY >= y && mouseY <= y + size )
		{
			hovering = true;
			MouseCursor.changeCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ), 10 );
		} else
			hovering = false;
	}

}
