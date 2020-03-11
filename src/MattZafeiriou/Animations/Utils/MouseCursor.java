package MattZafeiriou.Animations.Utils;

import java.awt.Cursor;

import javax.swing.JFrame;

public class MouseCursor
{

	private static JFrame frame;
	private static Cursor myCursor;
	private static int getPriority = 0;
	private static boolean changed = false;

	public static void setFrame( JFrame jframe )
	{
		frame = jframe;
	}

	public static void changeCursor( Cursor cursor, int priority )
	{
		if( getPriority < priority )
		{
			myCursor = cursor;
			changed = true;
		}
	}

	public static void setCursor()
	{
		if( changed )
		{
			changed = false;
			frame.setCursor( myCursor );
			getPriority = 0;
		}
	}
}
