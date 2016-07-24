/**
 * @author David M. Thal
 *
 * "Dave's Coding Conventions.doc" contains information that is helpful
 * for other developers who are maintaining or reviewing any of Dave's code.
 *
 * http://cvs-int.combimatrix.com/cgi-bin/cvsweb.cgi/main/UtilDT/docs/Dave%27s%20Coding%20Conventions.doc
 *
 */

package com.lordjoe.exceptions;


/***************************************************************************************************
 *
 *  <!-- InvalidNameAttributeException() -->
 *
 *  This class is an unchecked exception because it is presumed that the
 *  occurrence of an invalid name attribute is a bug.
 *
 ***************************************************************************************************
 */
public class InvalidNameAttributeException extends RuntimeException
{
    private String invalidName;
    private int position;
    
    public InvalidNameAttributeException(final String invalidName, final int position)
    {
        this.invalidName = invalidName;
        this.position = position;
    }
    
    public String getInvalidName()
    {
        return this.invalidName;
    }
    
    public int getPosition()
    {
        return this.position;
    }

}