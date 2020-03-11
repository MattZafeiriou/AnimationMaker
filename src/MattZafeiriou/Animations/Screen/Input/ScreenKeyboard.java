package MattZafeiriou.Animations.Screen.Input;

import MattZafeiriou.Animations.Screen.MainScreen;

public class ScreenKeyboard
{

	private static ScreenKeyboard keyboard;

	private ScreenKeyboard()
	{
	}

	public static ScreenKeyboard getInstance()
	{
		if( keyboard == null )
			keyboard = new ScreenKeyboard();
		return keyboard;
	}

	private MainScreen mainScreen;

	public void KeyDown( int key )
	{
		if( key <= 256 )
		{
			mainScreen.isKeyDown[ key ] = true;
		}
	}

	public void KeyUp( int key )
	{
		if( key <= 256 )
		{
			mainScreen.isKeyDown[ key ] = false;
		}
	}

	public void setMainScreen( MainScreen mainScreen )
	{
		this.mainScreen = mainScreen;
	}

}
