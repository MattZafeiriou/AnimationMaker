package MattZafeiriou.Animations.Utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Loader
{

	public static BufferedImage loadImage( String path )
	{
		BufferedImage img = null;
		try
		{
			img = ImageIO.read( new File( path ) );
		} catch( IOException e )
		{
		}
		return img;
	}

	public static Font loadFont( String path )
	{
		// create the font to use. Specify the size!
		Font customFont = null;
		try
		{
			customFont = Font.createFont( Font.TRUETYPE_FONT, new File( path ) ).deriveFont( 12f );
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			// register the font
			ge.registerFont( customFont );
		} catch( IOException e )
		{
			e.printStackTrace();
		} catch( FontFormatException e )
		{
			e.printStackTrace();
		}
		return customFont;
	}

}
