package MattZafeiriou.Animations.Logs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import MattZafeiriou.Animations.Program.ProgramVariables;
import MattZafeiriou.Animations.Utils.Writer;

public class Logger
{

	public static boolean CAN_LOG = true;

	private static String log = "";

	public static void Log( String message )
	{
		if( ! CAN_LOG )
			return;

		DateFormat dateFormat = new SimpleDateFormat( "yyyy/MM/dd HH:mm" );
		Date date = new Date();

		System.out.println( dateFormat.format( date ) + ": " + message );
		log += dateFormat.format( date ) + ": " + message + "\n";
	}

	public static void EmptyLine()
	{
		if( ! CAN_LOG )
			return;

		DateFormat dateFormat = new SimpleDateFormat( "yyyy/MM/dd HH:mm" );
		Date date = new Date();

		System.out.println( dateFormat.format( date ) + ": " );
		log += dateFormat.format( date ) + ": " + "\n";
	}

	public static void saveLog()
	{
		if( log.length() != 0 )
			log = log.substring( 0, log.length() - 1 ); // remove the last \n
		Writer.write( log, ProgramVariables.DEFAULT_LOCATION + "Noob.log" );
	}

}
