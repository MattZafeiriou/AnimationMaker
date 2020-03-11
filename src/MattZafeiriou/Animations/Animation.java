package MattZafeiriou.Animations;

import java.awt.Canvas;

import javax.swing.JFrame;

import MattZafeiriou.Animations.Images.ImageType;
import MattZafeiriou.Animations.Logs.Logger;
import MattZafeiriou.Animations.Program.ProgramVariables;
import MattZafeiriou.Animations.Topbar.Bar;
import MattZafeiriou.Animations.Topbar.ElementAction;
import MattZafeiriou.Animations.Utils.Keyboard;
import MattZafeiriou.Animations.Utils.Mouse;
import MattZafeiriou.Animations.Utils.Window;

public class Animation implements Runnable
{

	private static boolean running = false;

	private static JFrame frame;
	private static Canvas canvas;
	private State state;
	private Thread thread;
	private static Mouse mouse;
	private static Keyboard keyboard;

	public static enum State
	{
		RENDERING, UPDATING
	}

	public static void main( String[] args )
	{
		running = true;
		new Animation( State.RENDERING );
		new Animation( State.UPDATING );
	}

	public Animation( State state )
	{
		this.state = state;
		if( state == State.RENDERING )
		{
			frame = Window.createWindow( "Animation Creator", 800, 600, false );
			canvas = Window.createCanvas( frame );
			mouse = Mouse.getInstance( frame );
			keyboard = Keyboard.getInstance();
			canvas.addKeyListener( keyboard );
			canvas.addMouseListener( mouse );
			canvas.addMouseMotionListener( mouse );
			canvas.addMouseWheelListener( mouse );
		}
		mouse = Mouse.getInstance( frame );
		start();
	}

	@Override
	public void run()
	{

		if( state == State.UPDATING )
			init();
		else
			Renderer.init( frame, canvas, 3 );

		int fps = 60;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		while( running )
		{

			double timePerTick = 1000000000 / fps;
			now = System.nanoTime();
			delta += ( now - lastTime ) / timePerTick;
			lastTime = now;
			if( state == State.RENDERING )
			{
				if( delta >= 1 )
				{
					Renderer.prepare( frame.getWidth(), frame.getHeight() );
					Renderer.render( frame );
					Renderer.show();
					delta = 0;
				}
			}
			if( state == State.UPDATING )
			{
				update();
			}
		}
		stop();
	}

	private void update()
	{
		Bar.updateBars();
		ImageType.updateImages();

		Renderer.update();
	}

	private void init()
	{
		Logger.Log( "Weliopy Version " + ProgramVariables.VERSION + " started running." );
		Logger.Log( "Default Folder Location: " + ProgramVariables.DEFAULT_LOCATION );
		Logger.Log( "Running on:" );
		Logger.Log( "  Java version: " + System.getProperty( "java.version" ) );
		Logger.Log( "  OS Name: " + System.getProperty( "os.name" ) );
		Logger.Log( "  OS Version: " + System.getProperty( "os.version" ) );
		Logger.EmptyLine();

		ElementAction[] oof = new ElementAction[]
		{ new ElementAction( "Print something on console" )
		{
			@Override
			public void run()
			{
				Logger.Log( "hi" );
			}
		}, new ElementAction( "Exit" )
		{
			@Override
			public void run()
			{
				System.exit( 0 );
			}
		} };
		Bar.createBar( "File", oof );
		Bar.createBar( "noob", oof );

	}

	public synchronized void start()
	{
		if( thread == null )
		{
			thread = new Thread( this, state == State.RENDERING ? "Renderer" : "Updater" );
			thread.start();
		}
	}

	public synchronized void stop()
	{
		try
		{
			thread.join();
		} catch( InterruptedException e )
		{
			e.printStackTrace();
		}
	}
}
