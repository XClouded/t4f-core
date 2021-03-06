/**
 * Based on code from JSON.org, modified to support Rhino. The original code
 * came with the following notice:
 * <p>
 * Copyright (c) 2002 JSON.org
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * The Software shall be used for Good, not Evil.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.threecrickets.rhino.internal;

/**
 * The JsonException is thrown by the JSON.org classes when things are amiss.
 * 
 * @author JSON.org
 * @version 2008-09-18
 */
public class JsonException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 0;

	private Throwable cause;

	/**
	 * Constructs a JsonException with an explanatory message.
	 * 
	 * @param message
	 *        Detail about the reason for the exception.
	 */
	public JsonException( String message )
	{
		super( message );
	}

	public JsonException( Throwable t )
	{
		super( t.getMessage() );
		this.cause = t;
	}

	@Override
	public Throwable getCause()
	{
		return this.cause;
	}
}