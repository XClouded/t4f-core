/**
 * Copyright 2010-2013 Three Crickets LLC.
 * <p>
 * The contents of this file are subject to the terms of the Mozilla Public
 * License version 1.1: http://www.mozilla.org/MPL/MPL-1.1.html
 * <p>
 * Alternatively, you can obtain a royalty free commercial license with less
 * limitations, transferable or non-transferable, directly from Three Crickets
 * at http://threecrickets.com/
 */

package com.threecrickets.rhino.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JavaScript formatting utilities.
 * 
 * @author Tal Liron
 */
public class JavaScriptUtil
{
	//
	// Static operations
	//

	/**
	 * Escape JavaScript literal strings (assumes they are surrounded by double
	 * quotes, not single quotes).
	 * 
	 * @param string
	 *        The string
	 * @return The literal string
	 */
	public static String escape( String string )
	{
		for( int i = 0, length = ESCAPE_PATTERNS.length; i < length; i++ )
			string = ESCAPE_PATTERNS[i].matcher( string ).replaceAll( ESCAPE_REPLACEMENTS[i] );
		return string;
	}

	// //////////////////////////////////////////////////////////////////////////
	// Private

	private static Pattern[] ESCAPE_PATTERNS = new Pattern[]
	{
		Pattern.compile( "\\\\" ), Pattern.compile( "\\n" ), Pattern.compile( "\\r" ), Pattern.compile( "\\t" ), Pattern.compile( "\\f" ), Pattern.compile( "\\\"" )
	};

	private static String[] ESCAPE_REPLACEMENTS = new String[]
	{
		Matcher.quoteReplacement( "\\\\" ), Matcher.quoteReplacement( "\\n" ), Matcher.quoteReplacement( "\\r" ), Matcher.quoteReplacement( "\\t" ), Matcher.quoteReplacement( "\\f" ), Matcher.quoteReplacement( "\\\"" )
	};
}
