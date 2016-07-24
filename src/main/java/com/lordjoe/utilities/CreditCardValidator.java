// ------------------------------------------------------------------------
// Credit Card Validation Solution, Version 4.4                 JSP Edition
// 30 July 2002
//
// COPYRIGHT NOTICE:
// a) This code is property of The Analysis and Solutions Company.
// b) It is being distributed free of charge and on an "as is" basis.
// c) Use of this code, or any part thereof, is contingent upon leaving
//     this copyright notice, name and address information in tact.
// d) Written permission must be obtained from us before this code, or any
//     part thereof, is sold or used in a product which is sold.
// e) By using this code, you accept full responsibility for its use
//     and will not hold the Analysis and Solutions Company, its employees
//     or officers liable for damages of any sort.
// f) This code is not to be used for illegal purposes.
// g) Please email us any revisions made to this code.
// h) This code can not be reposted.  Sites such as code repositories
//     need to provide a link directly to our URI, below.
//
// Copyright 2002     http://www.AnalysisAndSolutions.com/code/ccvs-jsp.htm
// The Analysis and Solutions Company         info@AnalysisAndSolutions.com
//
// ------------------------------------------------------------------------
// DESCRIPTION:
// Ensures credit card numbers are keyed in correctly. Includes checks that
// the length is correct, the first four digits are within accepted ranges,
// the number passes the Mod 10 Checksum and that you accept the given type
// of card.
// ------------------------------------------------------------------------

package com.lordjoe.utilities;

import com.lordjoe.utilities.*;
import java.util.*;

public class CreditCardValidator
{
    private static Map gNameToValidator  = new HashMap();

    public static boolean acceptCard(String type,String number)
    {
        if(CreditCardType.getCreditCardType(type) == null)
            throw new IllegalArgumentException("Never hear of credit cart type " + type);
        CreditCardValidator tester = null;
        synchronized (gNameToValidator) {
            tester = (CreditCardValidator)gNameToValidator.get(type);
            if(tester == null) {
                String[] cards = {type};
                tester = new CreditCardValidator(cards);
                gNameToValidator.put(type,tester);
            }
        }
        return(tester.acceptCard(number));

    }
    private Vector m_AcceptList;


    public CreditCardValidator()
    {
        m_AcceptList = new Vector();
    }
    public CreditCardValidator(String[] accept)
    {
        this();
        for (int i = 0; i < accept.length; i++) {
            String s = accept[i];
             m_AcceptList.addElement(s);
        }
    }

    public boolean acceptCard(String cardNumber)
    {
        CreditCardType type = CreditCardType.getCreditCardTypeByNumber(cardNumber);
        if(type == null)
            return(false);
        if(!m_AcceptList.contains(type.getName()))
            return(false); // do not acept
        if(!CreditCardType.passesChecksum(cardNumber))
            return(false); // bad check sum
        return(true);
    }


    public static void main(String[] args)
    {
         boolean accept = acceptCard("Mastercard","5291491741018293");
        accept = acceptCard("Visa","4111111111111111");
        CreditCardType master = CreditCardType.getCreditCardType("Mastercard");
        CreditCardType visa = CreditCardType.getCreditCardType("Visa");
        CreditCardType discover = CreditCardType.getCreditCardType("Discover");
        String number = null;
        number = master.buildValidNumber();
        accept = acceptCard("Mastercard",number);
        accept = acceptCard("Visa",number);
        number = visa.buildValidNumber();
         accept = acceptCard("Mastercard",number);
         accept = acceptCard("Visa",number);
        number = discover.buildValidNumber();
          accept = acceptCard("Mastercard",number);
        accept = acceptCard("Visa",number);
        accept = acceptCard("Discover",number);

    }





 }
