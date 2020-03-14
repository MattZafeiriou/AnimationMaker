package MattZafeiriou.Animations.Topbar;

import java.awt.Color;
import java.awt.Graphics;

import MattZafeiriou.Animations.Program.ProgramVariables;
import MattZafeiriou.Animations.Utils.Mouse;

public class Element
{

	private int x, y, width, height, stringHeight;
	private ElementAction action;
	private String text;
	private boolean isHovering = false;
	public boolean isOpen = false;

	public Element( String text, int x, int y, int width, int height, int stringHeight, ElementAction action )
	{
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.stringHeight = stringHeight;
		this.action = action;
	}

	public void render( Graphics g )
	{
		if( ! isOpen )
			return;

		if( isHovering )
		{
			g.setColor( ProgramVariables.SECONDARY_COLOR );
		} else
			g.setColor( ProgramVariables.MAIN_COLOR );
		g.fillRect( x, y, width, height );

		g.setColor( new Color( 255, 255, 255, 200 ) );
		g.drawString( text, x + 3, y + stringHeight + ( ( height - stringHeight ) / 2 ) );
	}

	public void update()
	{
		if( ! isOpen )
			return;
		Mouse mouse = Mouse.getInstance();
		if( mouse.getX() >= x && mouse.getX() <= x + width && mouse.getY() >= y && mouse.getY() <= y + height )
		{
			isHovering = true;
		} else
			isHovering = false;
	}

	public boolean mouseRelease( int button, int x, int y )
	{
		boolean used = false;
		if( ! isOpen )
			return used;
		if( button == ProgramVariables.OK_BUTTON )
		{
			if( x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height )
			{
				used = true;
				action.run();
			}
		}
		return used;
	}

}
