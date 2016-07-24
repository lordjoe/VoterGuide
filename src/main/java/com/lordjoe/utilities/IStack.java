
/**{ file
    @name IStack.java
    @function <Add Comment Here>
    @author> Steven M. Lewis
    @copyright> 
	************************
	*  Copyright (c) 1996,97,98
	*  Steven M. Lewis
	*  www.LordJoe.com
	************************

    @date> Mon Jun 22 21:48:24 PDT 1998
    @version> 1.0
}*/ 
package com.lordjoe.utilities;
/**{ class
    @name IStack
    @function <Add Comment Here>
}*/ 
public class IStack {
    public static final int DEFAULT_SIZE = 8;
    /**{ field
        @name m_OutPointer
        @function <Add Comment Here>
    }*/ 
    private int m_top;


    /**{ field
        @name m_TheStack
        @function <Add Comment Here>
    }*/ 
    int[] m_TheStack;


    //- ******************* 
    //- Methods 
    /**{ constructor
        @name IStack
        @function Constructor of IStack
    }*/ 
    public IStack() {
        m_TheStack = new int[DEFAULT_SIZE];
        clear();
    }

    /**{ method
        @name clear
        @function <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    public void clear() {
        m_top = 0;
     }

    /**{ method
        @name isEmpty
        @function <Add Comment Here>
        @return <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    public boolean isEmpty() {
        return(m_top == 0);
    }

    /**{ method
        @name isEmpty
        @function <Add Comment Here>
        @return <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    public int size() {
        return(m_top);
    }
 
    /**{ method
        @name push
        @function <Add Comment Here>
        @param Plus <Add Comment Here>
        @return <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    public int push(int Plus) {
        if(m_top  >= m_TheStack.length) 
            /* increase m_queuesize if needed and allowed */
        {
            extend();
        }
        m_TheStack[m_top++] = Plus;
        return(m_top);
    }

    /**{ method
        @name member
        @function <Add Comment Here>
        @param Plus <Add Comment Here>
        @return <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    boolean member(int Plus) {
        for(int i = 0; i < m_top; i++) {
            if(m_TheStack[i] == Plus) {
                return(true);
            }
        }
        return(false);
        //	All	OK
    }

    /**{ method
        @name top
        @function <Add Comment Here>
        @return <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    public int top() {
        int theret = 0;
        if(! isEmpty()) {
            /* anything in the queue */
            theret = m_TheStack[m_top - 1];
        }
        return(theret);
    }

    /**{ method
        @name pop
        @function <Add Comment Here>
        @return <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    public int pop() {
        int theret = 0;
        if(! isEmpty()) {
            /* anything in the queue */
           theret = m_TheStack[--m_top];
           return(theret);
        }
        return(theret);
    }


    /**{ method
        @name extend
        @function <Add Comment Here>
        @return <Add Comment Here>
        @policy <Add Comment Here>
    }*/ 
    protected int extend() {
        int i;
        int TheSize = Math.max(2 * m_TheStack.length,DEFAULT_SIZE);
        int[] Old = m_TheStack;
        m_TheStack = new int[TheSize];
        for(i = 0; i < m_top; i ++) {
            m_TheStack[i] = Old[i];
        }
        return(TheSize);
    }


//- ******************* 
//- End Class IStack
}
