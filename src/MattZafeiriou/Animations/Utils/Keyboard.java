package MattZafeiriou.Animations.Utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import MattZafeiriou.Animations.Screen.Input.ScreenKeyboard;

public class Keyboard implements KeyListener
{

	private static Keyboard keyboard;

	private Keyboard()
	{
	}

	public static Keyboard getInstance()
	{
		if( keyboard == null )
			keyboard = new Keyboard();
		return keyboard;
	}

	@Override
	public void keyPressed( KeyEvent e )
	{
		ScreenKeyboard.getInstance().KeyDown( e.getKeyCode() );
	}

	@Override
	public void keyReleased( KeyEvent e )
	{
		ScreenKeyboard.getInstance().KeyUp( e.getKeyCode() );
	}

	@Override
	public void keyTyped( KeyEvent e )
	{
	}

}
