package MattZafeiriou.Animations.Utils;

import java.io.FileWriter;
import java.io.IOException;

import MattZafeiriou.Animations.Logs.Logger;

public class Writer
{

	public static void write( String text, String path )
	{
		try
		{
			FileWriter myWriter = new FileWriter( path );
			myWriter.write( text );
			myWriter.close();
			Logger.Log( "Wrote to file " + path );
		} catch( IOException e )
		{
			Logger.Log( "An error occurred." );
			e.printStackTrace();
		}
	}

}
