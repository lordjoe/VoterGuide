package com.lordjoe.utilities;
/**
*  This interfaced is implemented by an object which can tell if it is valid
* mostly used by components and Documents
*/ 
public interface IValidatable
{
    /**
    * returns an empty String if the current state is valid
    * if now return a String telling why
    * @return see above - possibly null
    */
    public String isValid();
    
    /**
    * returns String which is an example of good code
    * @return see above - non null
    */
    public String validForm();
    
// end interface IValidatable
}
