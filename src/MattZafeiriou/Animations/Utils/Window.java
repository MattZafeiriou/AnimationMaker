package MattZafeiriou.Animations.Utils;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

import MattZafeiriou.Animations.Logs.Logger;

public class Window
{

	public static JFrame createWindow( String name, int width, int height, boolean decorated )
	{

		JFrame frame = new JFrame( name );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setUndecorated( ! decorated );
		frame.setSize( width, height );
		frame.setLocationRelativeTo( null );
		frame.setVisible( true );
		Runtime.getRuntime().addShutdownHook( new Thread( new Runnable()
		{

			public void run()
			{
				Logger.saveLog();
			}
		} ) );
		return frame;
	}

	public static Canvas createCanvas( JFrame frame )
	{

		Canvas canvas = new Canvas();
		canvas.setMinimumSize( new Dimension( frame.getWidth(), frame.getHeight() ) );
		canvas.setMaximumSize( new Dimension( frame.getWidth(), frame.getHeight() ) );
		canvas.setVisible( true );

		frame.add( canvas );
		frame.setVisible( true );

		return canvas;
	}

}
