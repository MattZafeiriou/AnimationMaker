package MattZafeiriou.Animations.Screen;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class InfoScreen
{

	private JFrame frame;

	private int percentX = 0, percentY = 0, percentW = 0, percentH = 0;
	private int Width = 0, Height = 0, X = 0, Y = 0;

	public void init( JFrame frame, int percentX, int percentY, int percentW, int percentH )
	{
		this.frame = frame;
		this.percentX = percentX;
		this.percentY = percentY;
		this.percentW = percentW;
		this.percentH = percentH;

		X = ( frame.getWidth() / 100 ) * percentX;
		Y = ( frame.getWidth() / 100 ) * percentY + 25;
		Width = ( frame.getWidth() / 100 ) * percentW;
		Height = ( frame.getWidth() / 100 ) * percentH - 25;

		InfoBox.createBox( "Name", X + 10, InfoBox.Type.CHECKBOX );
	}

	public void render( Graphics g )
	{
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
		InfoBox.setBoxesX( X + 10 );
	}

}
