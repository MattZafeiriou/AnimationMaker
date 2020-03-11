package MattZafeiriou.Animations.Utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ImageUtils
{

	public static BufferedImage makeTextToImage( String text, int width, int height, Font font, Color color,
			int mousePos )
	{
		BufferedImage image = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB );

		Graphics g = image.getGraphics();

		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext( affinetransform, true, true );
		int textheight = (int) ( font.getStringBounds( text, frc ).getHeight() );
		int textwidth = (int) ( font.getStringBounds( text, frc ).getWidth() );

		g.setFont( font );
		g.setColor( color );
		if( textwidth > width )
		{
			g.drawString( text, - Math.abs( textwidth - width ) - 4, textheight );
		} else
			g.drawString( text, 0, textheight );

		if( mousePos >= 0 && mousePos <= text.length() )
		{
			String finalStr = text.substring( 0, mousePos );
			int txtW = 0;
			if( textwidth > width )
			{
				txtW = (int) ( font.getStringBounds( finalStr, frc ).getWidth() ) + 1 - Math.abs( textwidth - width )
						- 4;
			} else
				txtW = (int) ( font.getStringBounds( finalStr, frc ).getWidth() ) + 1;

			g.setColor( Color.DARK_GRAY );
			g.drawLine( txtW, 1, txtW, textheight );
		}

		return image;
	}

}
