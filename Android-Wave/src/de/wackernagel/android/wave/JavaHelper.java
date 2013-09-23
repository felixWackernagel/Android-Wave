package de.wackernagel.android.wave;

import java.io.IOException;

public class JavaHelper {
	
	public static boolean isEmpty( final String toCheck )
	{
		return ( toCheck.length() == 0 );
	}
	
	public static IOException newIOException( final Throwable toWrap )
	{
		return new IOException( toWrap.getMessage() );
	}
}
