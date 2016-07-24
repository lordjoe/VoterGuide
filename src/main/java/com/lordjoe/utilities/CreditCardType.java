package com.lordjoe.utilities;

import java.util.*;

/**
 * com.lordjoe.Utilities.CreditCardType
 * @author smlewis
 * Date:  Sep 18, 2002
 */
public class CreditCardType  implements INameable
{
    public static final CreditCardType[] EMPTY_ARRAY = {};
    public static final Class THIS_CLASS = CreditCardType.class;

    public static CreditCardType getCreditCardType(String name)
      {
          CreditCardType ret = (CreditCardType)gTypes.get(name);
           return(ret);
      }

    public static String getValidNumber(String name)
       {
           CreditCardType ret = getCreditCardType( name);
           if(ret == null)
            throw new IllegalArgumentException("We do not know the credit card " + name);
            return(ret.buildValidNumber());
       }

    public static CreditCardType[] getCreditCardTypes()
     {
         CreditCardType[] ret = new CreditCardType[gTypes.size()];
         gTypes.values().toArray(ret);
         return(ret);
     }

    public static CreditCardType getCreditCardTypeByNumber(String number)
     {
         int length = number.length();
         if(length < 13 || length > 16) {
               throw new IllegalArgumentException("langeh must be 13-16 characters");
         }
         int left = Integer.parseInt(number.substring(0, 4));
         CreditCardType[] types =  getCreditCardTypes();
         for (int i = 0; i < types.length; i++) {
             CreditCardType type = types[i];
             if(type.acceptRange(left))
                return(type);
         }
         return(null);
     }

     public static boolean validExpiration(java.util.Date expDate)
     {
         if(expDate == null)
             return(false);
         long nowTime = new java.util.Date().getTime();
         return(expDate.getTime() > nowTime);
     }

       public static boolean passesChecksum(String CCVSNumber)
        {
            if(Util.isEmptyString(CCVSNumber))
                return(false);
            int numberLength = CCVSNumber.length();
            int Digit;
            //  Begin the checksum process.
            int Checksum = 0;

            //  Add even digits in even length strings
            //  or odd digits in odd length strings.
            int Location = 0;
            for (Location = 1 - (numberLength % 2); Location < numberLength; Location += 2) {
               Digit = Integer.parseInt(CCVSNumber.substring(Location, Location+1));
               Checksum += Digit;
            }

            //  Analyze odd digits in even length strings
            //  or even digits in odd length strings.
            for (Location = (numberLength % 2); Location < numberLength; Location += 2) {
               Digit = Integer.parseInt(CCVSNumber.substring(Location, Location+1)) * 2;
               if (Digit < 10) {
                  Checksum += Digit;
               } else {
                  Checksum += Digit - 9;
               }
            }

            //  If the checksum is divisible by 10, the number passes.
            if (Checksum % 10 == 0) {
                return true;
            } else {
               return false;
            }
        }
    private static Map gTypes;
    protected static void  buildTypes()
    {
        gTypes = new HashMap();
        CreditCardType theCard = null;
        List holder = new ArrayList();
        theCard = new CreditCardType("Diners Club",14);
        theCard.addAcceptRange(3000,3059);
        theCard.addAcceptRange(3600,3699);
        theCard.addAcceptRange(3800,3889);
        gTypes.put(theCard.getName(),theCard);

        theCard = new CreditCardType("American Express",15);
        theCard.addAcceptRange(3400,3499);
        theCard.addAcceptRange(3700,3799);
        gTypes.put(theCard.getName(),theCard);

        theCard = new CreditCardType("JCB",16);
        theCard.addAcceptRange(3528,3589);
        gTypes.put(theCard.getName(),theCard);

        theCard = new CreditCardType("Carte Blanche",14);
        theCard.addAcceptRange(3890,3899);
        gTypes.put(theCard.getName(),theCard);

        theCard = new CreditCardType("Visa",16);
        theCard.addAcceptRange(4000,4999);
        gTypes.put(theCard.getName(),theCard);

        theCard = new CreditCardType("Mastercard",16);
        theCard.addAcceptRange(5100,5599);
        gTypes.put(theCard.getName(),theCard);

        theCard = new CreditCardType("Discover",16);
        theCard.addAcceptRange(6011,6011);
        gTypes.put(theCard.getName(),theCard);

        theCard = new CreditCardType("Australian BankCard",16);
        theCard.addAcceptRange(5610,5610);
        gTypes.put(theCard.getName(),theCard);

    }

    static {
        buildTypes();
    }

    private String m_Name;
    private int m_Length;
    private IPoint[] m_AcceptRanges;

    protected CreditCardType(String name)
    {
        this(name,16);
    }
    protected CreditCardType(String name,int length)
    {
        m_Length = length;
        m_Name = name;
        m_AcceptRanges = IPoint.EMPTY_ARRAY;
    }

    public String buildValidNumber()
    {
        String ret;
        IPoint range = (IPoint)Util.randomElement(m_AcceptRanges);
        if(range.x == range.y) {
            ret = Integer.toString(range.x);
        }
        else  {
           ret = Integer.toString(range.x + Util.randomInt(range.y - range.x));
        }
        while(ret.length() < m_Length - 1)
            ret   += Integer.toString(Util.randomInt(10));
        String start = ret;
        ret = start + Integer.toString(Util.randomInt(10));
        while(!passesChecksum(ret))
           ret = start + Integer.toString(Util.randomInt(10));
        return(ret);
    }

    public String getName()
    {
        return m_Name;
    }

    public void setName(String name)
    {
        m_Name = name;
    }

    public int getLength()
    {
        return m_Length;
    }

    public void setLength(int length)
    {
        m_Length = length;
    }

    protected void addAcceptRange(int min,int max)
    {
         IPoint range = new IPoint(min,max);
         m_AcceptRanges = (IPoint[])Util.addToArray(m_AcceptRanges,range);
    }


    protected boolean acceptRange(int test)
    {
        for (int i = 0; i < m_AcceptRanges.length; i++) {
            IPoint pt = m_AcceptRanges[i];
            if(test >= pt.x && test <= pt.y)
                return(true);
        }
        return(false);
    }
}

