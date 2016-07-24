package com.lordjoe.exceptions.useradmin;

/**
 * Title:        Roche-CBMX Hybreader App
 * Description:  Exception indicating that an error occurred in the underlying
 *      user admin system.
 * Copyright:    Copyright (c) 2000
 * Company:      CombiMatrix
 * @author       Ben Dagnon
 * @version 1.0
 */

public class UserAdminSystemException extends RuntimeException
{
    public UserAdminSystemException(final Throwable cause)
    {
        super(cause);
    }

    public UserAdminSystemException(String message)
    {
        super(message);
    }

    public UserAdminSystemException(String message, final Throwable cause)
    {
        super(message, cause);
    }
}