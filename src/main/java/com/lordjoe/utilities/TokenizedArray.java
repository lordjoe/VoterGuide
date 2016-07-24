/**
*  TokenizedArray.java
*  Window listener to exit when main frame closes
*  swing does this for free
* @author> Steven M. Lewis
* <>copyright>
  ************************
  *  Copyright (c) 1996,97,98
  *  Steven M. Lewis
  *  www.LordJoe.com
  ************************

* <>date> Thu Aug 13 15:56:51 PDT 1998
* @version> 1.0
*/

package com.lordjoe.utilities;
import java.util.*;

  //  Window Listener
public  class TokenizedArray {
    public String[] Tokens;
    private String[] Lines; // lines in the file
    private int CurrentLine;

    public TokenizedArray(String[] NewLines) {
        Lines = NewLines;
        CurrentLine = -1;
        nextLine();
    }
    
    public void nextLine() {
        while(CurrentLine < Lines.length) {
            CurrentLine++;
            if(CurrentLine >= Lines.length) {
                Tokens = null;
                return; // atEnd
            }
            Tokens =  Util.parseTabDelimited(Lines[CurrentLine]);
            if(Tokens.length > 0)
                return;
        }
    }
    public boolean atEnd() { 
        return(CurrentLine >= Lines.length);
    }
 // end class TokenizedArray
}
