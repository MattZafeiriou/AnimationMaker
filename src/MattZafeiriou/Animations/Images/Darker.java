package MattZafeiriou.Animations.Images;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Darker
{

	public static BufferedImage makeDark( BufferedImage starting, int alphaDifference )
	{
		return changeColor( starting, Color.black, alphaDifference );
	}

	public static BufferedImage changeColor( BufferedImage starting, Color color, int alphaDifference )
	{
		BufferedImage newImage = new BufferedImage( starting.getWidth(), starting.getHeight(),
				BufferedImage.TYPE_INT_ARGB );
		for( int x = 0; x < starting.getWidth(); x++ )
		{
			for( int y = 0; y < starting.getHeight(); y++ )
			{
				Color pixelColor = new Color( starting.getRGB( x, y ), true );
				if( pixelColor.getAlpha() != 0 )
				{
					int alpha = pixelColor.getAlpha();
					if( alpha + alphaDifference > 255 )
						alpha = 255;
					else
						alpha += alphaDifference;

					Color newColor = new Color( color.getRed(), color.getGreen(), color.getBlue(), alpha );
					newImage.setRGB( x, y, newColor.getRGB() );
					pixelColor = new Color( newImage.getRGB( x, y ), true );
				}
			}
		}
		return newImage;
	}

}
