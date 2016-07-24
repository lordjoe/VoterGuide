/**

 *

 * .
 *
 *
 *
 */

package com.lordjoe.lang;


import java.util.*;
import java.net.*;
import java.text.*;

/**
 * Title:        Miscellaneous
 * Description:
 * Copyright:    Copyright (c) David M. Thal
 * Company:      Lordjoe

 */


/**
 ***************************************************************************************************
 *                                                                                              <p>
 * <!-- StringOps -->
 *                                                                                              <p>
 * This class defines static methods that perform various String related operations.
 *                                                                                              <p>
 ***************************************************************************************************
 */
public class StringOps
{
//    private static Logger logger = Logger.getLogger(StringOps.class);
    private static final String LINE_SEP = System.getProperty("line.separator");

    public static final String INDENT_STRING = "    ";

    public static final int NO_MAXIMUM_LINES = -1;
    public static final int NO_MAXIMUM_LINE_LENGTH = -1;

    /**
     * This class should not be instantiated.
     */
    private StringOps()
    {
    }


    public static final int DEFAULT_LINE_LENGTH = 80;

    /**
     * break the string into lines no longer than lineLength assuming
     * no word exceeds line length -
     *
     * words end with any non  JavaIdentifierPart
     * @param s non-null string
     * @param lineLength  max line langht
     * @return  String with added CRs
     */
    public static String insertNeededReturns(String s, int lineLength)
    {
        if(s == null)
            return "";
        if (s.length() < lineLength)
            return s;
        StringBuffer sb = new StringBuffer();
        StringBuffer wordBuf = new StringBuffer();
        int wordEnd = getWord(s, wordBuf, 0);
        String word = wordBuf.toString();
        wordBuf.setLength(0);
        int currentLine = 0;
        while (wordEnd < s.length()) {
            int wordLength = word.length();
            if (wordLength > 0) {
                if ((currentLine + wordLength) < lineLength) {
                    currentLine += wordLength;
                    sb.append(word);
                }
                else {
                    sb.append("\n");
                    currentLine = 0;
                    currentLine += wordLength;
                    sb.append(word);
                }
            }
            wordEnd = getWord(s, wordBuf, wordEnd);
            word = wordBuf.toString();
            wordBuf.setLength(0);
        }
        return sb.toString();
    }

    public static int getWord(String s, StringBuffer sb, int start)
    {
        for (int i = start; i < s.length(); i++) {
            char c = s.charAt(i);
            sb.append(c);
            if (!Character.isJavaIdentifierPart(c)) {
                return i + 1;
            }
        }
        return s.length();
    }

    /**
     * concatenate an aphhabetized list to one string with
     * line breaks after DEFAULT_LINE_LENGTH chars
     *
     * @param pNames non-null list - willbe alphabetized
     * @return non-null string
     */
    public static String buildNameString(String[] pNames)
    {
        return buildNameString(pNames, DEFAULT_LINE_LENGTH);
    }


    /**
     *  test contains ignoring case
     * @param original  non-null non-empty string
     * @param test  non-null non-empty string
     * @return true if original contains test
     */
    public static boolean containsIgnoreCase(String original, String test)
    {
        return original.toLowerCase().indexOf(test.toLowerCase()) > -1;
    }

    /**
     *
     * @param original  non-null non-empty string
     * @param test  non-null non-empty string
     * @return true if original contains test
     */
    public static boolean contains(String original, String test)
    {
        return original.indexOf(test) > -1;
    }


    /**
     * return the part of original following fragment
     * @param original non-null non-empty string containing fragment
     * @param fragment  non-null non-empty fragment contained in original
     * @return non-null substring following fragment
     */
    public static String textAfterFragment(String original, String fragment)
    {
        int index = original.indexOf(fragment);
        if (index == -1)
            throw new IllegalArgumentException("String \'" + original +
                    "\' does not contain fragment \'" +
                    fragment + "\'");
        return original.substring(index + fragment.length());
    }

    /**
     * convert a message to a printable string
     *
     * @param text non-null message
     * @return as shown
     */
    public static String toPrintable(String text)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isJavaIdentifierPart(c) || Character.isWhitespace(c))
                sb.append(c);
            else
                sb.append("\0x" + Integer.toString((int) c, 16));
        }
        return sb.toString();
    }

    /**
     * convert a message limiting number of lines and line length
     * and converting non-printing characters to hex
     *
     * @param message  non-null text
     * @param maxLines mac lines -1 is no max
     * @return non-null return text
     */
    public static String cleanupMessageText(String message, int maxLines)
    {
        return cleanupMessageText(message, maxLines, DEFAULT_LINE_LENGTH);
    }

    /**
     * convert a message limiting number of lines and line length
     * and converting non-printing characters to hex
     *
     * @param message       non-null text
     * @param maxLines      max lines -1 is no max
     * @param maxLineLength max line lenght -1 is no max
     * @return non-null return text
     */
    public static String cleanupMessageText(String message, int maxLines, int maxLineLength)
    {
        String[] lines = message.split("\r");
        switch (lines.length) {
            case 0:
                return "";
            case 1:
                return cleanupMessageLine(lines[0], maxLineLength);
            default:
                return cleanupMessageLines(lines, maxLines, maxLineLength);
        }
    }

    public static String cleanupMessageLine(String message, int maxLineLength)
    {
        if (message.length() < maxLineLength)
            return message;
        else
            return message.substring(0, maxLineLength) + "...";
    }

    protected static String cleanupMessageLines(String[] lines, int maxLines, int maxLineLength)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            line = cleanupMessageLine(line, maxLineLength);
            if (maxLines >= 0 && i >= maxLines) {
                sb.append("\n...\n");
                break;
            }
        }
        return sb.toString();
    }

    /**
     * concatenate an aphhabetized list to one string with
     * line breaks after linelength chars
     *
     * @param pNames     non-null list - willbe alphabetized
     * @param lineLength line length
     * @return non-null string
     */

    public static String buildNameString(String[] pNames, int lineLength)
    {
        Arrays.sort(pNames);
        StringBuffer sb = new StringBuffer();
        int lineL = 0;
        for (int i = 0; i < pNames.length; i++) {
            if (sb.length() > 0) {
                sb.append(",");
                lineL++;
            }
            sb.append(pNames[i]);
            lineL += pNames[i].length();
            if (lineL >= lineLength) {
                lineL = 0;
                sb.append("\n");
            }

        }
        return sb.toString();
    }

    private static final Object gTimeLock = new Object();
    private static long gTime;

    /**
     * return a String that is unique for this
     * VM and usually globally uniqie on one box
     *
     * @return
     */
    public static String getUniqueName()
    {
        synchronized (gTimeLock) {
            long now = System.currentTimeMillis();
            if (now > gTime) {
                gTime = now;
            }
            else {
                gTime++;
            }
            return Long.toString(gTime);
        }

    }

    /**
     * change teh direction of the string
     * @param s non-null string
     * @return non-null string with characters reversed
     */
    public static String reverse(String s)
    {
        if (s.length() == 0)
            return s;
        StringBuffer sb = new StringBuffer();
        int last = s.length() - 1;
        for (; last >= 0; last--)
            sb.append(s.charAt(last));
        return sb.toString();
    }

    /**
     * convert an array of ints as a comma delimited string to
     * an array of ints i.e. 1,23,45,67
     *
     * @param in non-null input string
     * @return non-null output array
     */
    public static int[] stringToIntArray(String in)
    {
        String[] items = in.split(",");
        int[] ret = new int[items.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = Integer.parseInt(items[i]);

        }
        return ret;
    }


    /**
     * a good format for numbers in the 10-3 range
     *
     * @param n number to format
     * @return
     */
    public static String formatNano(double n)
    {
        StringBuffer sb = new StringBuffer();
        DecimalFormat df1 = new DecimalFormat("####0.00");
        String str = df1.format(n * 1000000000.0);
        sb.append(str);
        sb.append("E-9");
        return sb.toString();
    }

    /**
     * a good format for numbers in the 0.01-1 range
     *
     * @param n number to format
     * @return
     */
    public static String formatDeci(double n)
    {
        StringBuffer sb = new StringBuffer();
        DecimalFormat df1 = new DecimalFormat("#######0.000");
        String str = df1.format(n);
        sb.append(str);
        return sb.toString();
    }
    
    /**
     * mrege the items in an array of strings inserting the
     * delimiter - this basically reverses the action of split
     *
     * @param items     non-null array of items
     * @param delimiter non-null delimiter
     * @return the merged string - "" if items is empty
     */
    public static String merge(String[] items, String delimiter)
    {

        return merge(items, delimiter, 0);
    }

    /**
     * mrege the items in an array of strings inserting the
     * delimiter - this basically reverses the action of split
     *
     * @param items     non-null array of items
     * @param delimiter non-null delimiter
     * @param start     start index
     * @return the merged string - "" if items is empty
     */
    public static String merge(String[] items, String delimiter, int start)
    {
        if (items.length <= start)
            return "";
        if (items.length == start + 1)
            return items[start];
        StringBuffer sb = new StringBuffer(items[start]);
        for (int i = start + 1; i < items.length; i++) {
            sb.append(delimiter);
            String item = items[i];
            sb.append(item);
        }

        return sb.toString();
    }

    /**
     * @param in non-null String
     * @return possibly null Integer - null says not an int
     */
    public static Integer stringToInt(String in)
    {
        try {
            return new Integer(in);
        }
        catch (Exception ex) {
            return null;
        }
    }

    /**
     * return true of the string is null or empty
     */
    public static boolean isBlank(String text)
    {
        if (text == null)
            return true;
        if (text.length() == 0)
            return true;
        return false;
    }

    /**
     * Return inputString or, if inputString is longer than maxLength,
     * inputString.substring(0, maxLength) + "...".  Return null if
     * input is null
     *
     * @param inputString
     * @param maxLength
     * @return
     */
    public static String limitLength(String inputString, int maxLength)
    {
        if (inputString == null) {
            return (null);
        }

        if (maxLength >= inputString.length()) {
            return (inputString);
        }
        else {
            return (inputString.substring(0, maxLength) + "...");
        }
    }


    public static String indentString(int indent)
    {
        switch (indent)
        {
            case 0 :
                return "";
            case 1 :
                return INDENT_STRING;
            case 2 :
                return INDENT_STRING + INDENT_STRING;
            case 3 :
                return INDENT_STRING + INDENT_STRING + INDENT_STRING;
            default :
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < indent; i++)
                    sb.append(INDENT_STRING);
                return sb.toString();
        }
    }



    /**
     ***********************************************************************************************
     *                                                                                          <p>
     * <!-- isAlphaNumeric() -->
     *                                                                                          <p>
     * Determines if the given string is alphanumeric.
     *                                                                                          <p>
     ***********************************************************************************************
     */
    public static boolean isAlphaNumeric(final String string, final int start, final int length)
    {
        for (int i = start; i < start + length; i++)
        {
            if (!Character.isLetterOrDigit(string.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }



    /**
     ***********************************************************************************************
     *                                                                                          <p>
     * <!-- isAlphaNumeric() -->
     *                                                                                          <p>
     * Determines if the given string is alphanumeric.
     *                                                                                          <p>
     ***********************************************************************************************
     */
    public static boolean isAlphaNumeric(final String string)
    {
        return isAlphaNumeric(string, 0, string.length());
    }



    /**
     ***********************************************************************************************
     *                                                                                          <p>
     * <!-- isDigits() -->
     *                                                                                          <p>
     * Determines if the given string is a series of numbers.
     *                                                                                          <p>
     ***********************************************************************************************
     */
    public static boolean isDigits(final String string, final int start, final int length)
    {
        for (int i = start; i < start + length; i++)
        {
            if (!Character.isDigit(string.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }


    /**
     ***********************************************************************************************
     *                                                                                          <p>
     * <!-- isDigits() -->
     *                                                                                          <p>
     * Determines if the given string is a series of numbers.
     *                                                                                          <p>
     ***********************************************************************************************
     */
    public static boolean isDigits(final String string)
    {
        return isDigits(string, 0, string.length());
    }

    /**
     ***********************************************************************************************
     *                                                                                          <p>
     * <!-- isLetters() -->
     *                                                                                          <p>
     * Determines if the given string is a series of letters.
     *                                                                                          <p>
     ***********************************************************************************************
     */
    public static boolean isLetters(final String string, final int start, final int length)
    {
        for (int i = start; i < start + length; i++)
        {
            if (!Character.isLetter(string.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }


    /**
     ***********************************************************************************************
     *                                                                                          <p>
     * <!-- isLetters() -->
     *                                                                                          <p>
     * Determines if the given string is a series of letters.
     *                                                                                          <p>
     ***********************************************************************************************
     */
    public static boolean isLetters(final String string)
    {
        return isLetters(string, 0, string.length());
    }



    /**
     ***********************************************************************************************
     *                                                                                          <p>
     *  <!-- isUpperCase() -->
     *                                                                                          <p>
     *  Determines if the given string is uppercase.
     *                                                                                          <p>
     ***********************************************************************************************
     */
    public static boolean isUpperCase(final String string)
    {
        final boolean isUpperCase = string.equals(string.toUpperCase());
        return isUpperCase;
    }



    /**
     ***********************************************************************************************
     *                                                                                          <p>
     *  <!-- isUpperCaseLetters() -->
     *                                                                                          <p>
     *  Determines if the given string is uppercase letters.
     *                                                                                          <p>
     ***********************************************************************************************
     */
    public static boolean isUpperCaseLetters(final String string)
    {
        final boolean isUpperCase = string.equals(string.toUpperCase());
        final boolean isLetters = isLetters(string);
        final boolean isUpperCaseLetters = isUpperCase && isLetters;
        return isUpperCaseLetters;
    }





    /**
     ***********************************************************************************************
     *                                                                                          <p>
     *  <!-- containsCharacter() -->
     *                                                                                          <p>
     *  Determines if the given string contains any of the given characters.
     *                                                                                          <p>
     ***********************************************************************************************
     */
    public static boolean containsCharacter(final String string, final char[] charArray)
    {
        for (int s = 0; s < string.length(); s++)
        {
            final char ch = string.charAt(s);
            for (int c = 0; c < charArray.length; c++)
            {
                if ( charArray[c] == ch )
                {
                    return true;
                }
            }
        }
        return false;
    }



    public static final int MINIMUM_IP_LENGTH = 7;
    public static final int MAXIMUM_IP_LENGTH = 15;

    /**
     * is the string of the form ddd.d.dd.d
     *
     * @param in non-null test string
     * @return as above
     */
    public static boolean isRawIPAddress(String in)
    {
        in = in.trim();
        int len = in.length();
        if (len < MINIMUM_IP_LENGTH || len > MAXIMUM_IP_LENGTH) {
            return false;
        }
        int numberPeriods = 0;
        int numberGroups = 1;
        int numberDigits = 0;
        for (int i = 0; i < len; i++) {
            char c = in.charAt(i);
            if (c == '.') {
                if (numberDigits == 0)
                    return false;
                numberPeriods++;
                numberDigits = 0;
                numberGroups++;
                continue;
            }
            if (Character.isDigit(c)) {
                numberDigits++;
            }
            else {
                return false;
            }

        }
        if (numberDigits == 0 || numberDigits > 3)
            return false;
        return true;
    }

    /**
     * internal method to extract bytes from a string which is
     * a raw IP address
     *
     * @param in
     * @return
     */
    protected static byte[] parseRawIPAddress(String in)
    {
        byte[] ret = new byte[4];
        String[] items = in.split("\\.");
        for (int i = 0; i < ret.length; i++) {
            ret[i] = (byte) Integer.parseInt(items[i]);
        }
        return ret;
    }

    /**
     * there is a but in the JDK 1.5 socket class which
     * does a DNS lookup in raw IP Addresses - this
     * forces that not to happen
     *
     * @param in non-null host string
     * @return possibly null INetAddress - null says the address in not
     *         properly formed
     */
    public static InetAddress getINetAddress(String in)
    {
        if (isRawIPAddress(in)) {
            byte[] bytes = parseRawIPAddress(in);
            try {
                return InetAddress.getByAddress(in, bytes);
            }
            catch (UnknownHostException ex) {
                return null;
            }

        }
        else {
            try {
                return InetAddress.getByName(in);
            }
            catch (UnknownHostException ex) {
                return null;
            }
        }
    }


    // tempdt This method has been deprecated in favor of countAsciiSpaceToTildePlusWhitespace()
    /***********************************************************************************************
     *
     *  <!-- countAsciiLettersDigitsWhitespaceDeprecated() -->
     *
     *  Counts the number of characters in the given array that are in [0-9, a-z, A-Z, whitepace].
     *
     ***********************************************************************************************
     */
    protected static int countAsciiLettersDigitsWhitespaceDeprecated(final char[] buffer)
    {
        // for detailed debugging
        // logger.debug(StringOps.toStringChars(new String(buffer)));

        final int chInt_A = (int) 'A';
        final int chInt_z = (int) 'z';
        final int chInt_0 = (int) '0';
        final int chInt_9 = (int) '9';

        int count = 0;
        for (int i = 0; i < buffer.length; i++) {
            final int chValue = (int) buffer[i];
            if (chValue >= chInt_A && chValue <= chInt_z)
                count++;
            else if (chValue >= chInt_0 && chValue <= chInt_9)
                count++;
            else if (Character.isWhitespace(buffer[i]))
                count++;
            else if (Character.isWhitespace(buffer[i]))
                count++;
        }
        return count;
    }

    /**
     * build a string consysting of printable characters
     *
     * @param inp
     * @return
     */
    public static String toPrintableString(String inp)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < inp.length(); i++) {
            char c = inp.charAt(i);
            if (c < 10)
                c += '0';
            if (c < 32)
                c += ('a' - 10);
            sb.append(c);
        }
        return sb.toString();
    }


    /**
     ***********************************************************************************************
     *                                                                                          <p>
     * <!-- countAsciiSpaceToTildePlusWhitespace() -->
     *                                                                                          <p>
     * Counts the number of characters in the given array that are in the range of ASCII
     * characters from 'sp' = 0x40 = 20 to '~' = 0x7E = 126
     *                                                                                          <p>
     * Here is an excerpt of an ASCII chart from ' ' (space) to '~' (tilde).
     * Decimal	Octal	Hex	Binary	Value	Note
     * 32	40	20	100000	SP	(Space)
     * 33	41	21	100001	!	(exclamation mark)
     * 34	42	22	100010	"	(double quote)
     * 35	43	23	100011	#	(number sign)
     * 36	44	24	100100	$	(dollar sign)
     * 37	45	25	100101	%	(percent)
     * 38	46	26	100110	&	(ampersand)
     * 39	47	27	100111	'	(single quote)
     * 40	50	28	101000	(	(left/opening parenthesis)
     * 41	51	29	101001	)	(right/closing parenthesis)
     * 42	52	02A	101010	*	(asterisk)
     * 43	53	02B	101011	+	(plus)
     * 44	54	02C	101100	,	(comma)
     * 45	55	02D	101101	-	(minus or dash)
     * 46	56	02E	101110	.	(dot)
     * 47	57	02F	101111	/	(forward slash)
     * 48	60	30	110000	0
     * 49	61	31	110001	1
     * 50	62	32	110010	2
     * 51	63	33	110011	3
     * 52	64	34	110100	4
     * 53	65	35	110101	5
     * 54	66	36	110110	6
     * 55	67	37	110111	7
     * 56	70	38	111000	8
     * 57	71	39	111001	9
     * 58	72	03A	111010	:	(colon)
     * 59	73	03B	111011	;	(semi-colon)
     * 60	74	03C	111100	<	(less than)
     * 61	75	03D	111101	=	(equal sign)
     * 62	76	03E	111110	>	(greater than)
     * 63	77	03F	111111	?	(question mark)
     * 64	100	40	1000000	@	(AT symbol)
     * 65	101	41	1000001	A
     * 66	102	42	1000010	B
     * 67	103	43	1000011	C
     * 68	104	44	1000100	D
     * 69	105	45	1000101	E
     * 70	106	46	1000110	F
     * 71	107	47	1000111	G
     * 72	110	48	1001000	H
     * 73	111	49	1001001	I
     * 74	112	04A	1001010	J
     * 75	113	04B	1001011	K
     * 76	114	04C	1001100	L
     * 77	115	04D	1001101	M
     * 78	116	04E	1001110	N
     * 79	117	04F	1001111	O
     * 80	120	50	1010000	P
     * 81	121	51	1010001	Q
     * 82	122	52	1010010	R
     * 83	123	53	1010011	S
     * 84	124	54	1010100	T
     * 85	125	55	1010101	U
     * 86	126	56	1010110	V
     * 87	127	57	1010111	W
     * 88	130	58	1011000	X
     * 89	131	59	1011001	Y
     * 90	132	05A	1011010	Z
     * 91	133	05B	1011011	[	(left/opening bracket)
     * 92	134	05C	1011100	\	(back slash)
     * 93	135	05D	1011101	]	(right/closing bracket)
     * 94	136	05E	1011110	^	(caret/cirumflex)
     * 95	137	05F	1011111	_	(underscore)
     * 96	140	60	1100000	`
     * 97	141	61	1100001	a
     * 98	142	62	1100010	b
     * 99	143	63	1100011	c
     * 100	144	64	1100100	d
     * 101	145	65	1100101	e
     * 102	146	66	1100110	f
     * 103	147	67	1100111	g
     * 104	150	68	1101000	h
     * 105	151	69	1101001	i
     * 106	152	06A	1101010	j
     * 107	153	06B	1101011	k
     * 108	154	06C	1101100	l
     * 109	155	06D	1101101	m
     * 110	156	06E	1101110	n
     * 111	157	06F	1101111	o
     * 112	160	70	1110000	p
     * 113	161	71	1110001	q
     * 114	162	72	1110010	r
     * 115	163	73	1110011	s
     * 116	164	74	1110100	t
     * 117	165	75	1110101	u
     * 118	166	76	1110110	v
     * 119	167	77	1110111	w
     * 120	170	78	1111000	x
     * 121	171	79	1111001	y
     * 122	172	07A	1111010	z
     * 123	173	07B	1111011	{	(left/opening brace)
     * 124	174	07C	1111100	|	(vertical bar)
     * 125	175	07D	1111101	}	(right/closing brace)
     * 126	176	07E	1111110	~	(tilde)
     *                                                                                          <p>
     ***********************************************************************************************
     */
    public static int countAsciiSpaceToTildePlusWhitespace(final char[] buffer)
    {
        // for detailed debugging
        // logger.debug(StringOps.toStringChars(new String(buffer)));

        final int chInt_space = (int) ' ';
        final int chInt_tilda = (int) '~';

        int count = 0;
        for (int i = 0; i < buffer.length; i++) {
            final int chValue = (int) buffer[i];
            if (chValue >= chInt_space && chValue <= chInt_tilda)
                count++;
            else if (Character.isWhitespace(buffer[i]))
                count++;
        }
        return count;
    }

    public static int countAsciiText(final char[] buffer)
    {
        return countAsciiSpaceToTildePlusWhitespace(buffer);
    }

    public static int countAsciiText(final String text)
    {
        return countAsciiSpaceToTildePlusWhitespace(text.toCharArray());
    }

    /**
     * Count the number of occurences of the character in the given string.
     *
     * @param text String
     * @param ch   char
     * @return int
     */
    public static int count(final String text, final char ch)
    {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ch) {
                count++;
            }
        }
        return count;
    }


    /**
     ***********************************************************************************************
     *                                                                                          <p>
     *  <!-- findNonWhitespace() -->
     *                                                                                          <p>
     *  Finds the first non-whitespace character in the given String.
     *                                                                                          <p>
     *  @return index of the first non-whitespace character in the given String; -1 if the
     *          given String is all whitespace
     *                                                                                          <p>
     ***********************************************************************************************
     */
    public static int findNonWhitespace(final String string)
    {
        final int length = string.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(string.charAt(i))) {
                return i;
            }
        }
        return -1;
    }


    /**
     ***********************************************************************************************
     *                                                                                          <p>
     * <!-- findWhitespace() -->
     *                                                                                          <p>
     * Finds the first whitespace character in the given String.
     *
     * @return index of the first whitespace character in the given String; -1 if the
     *         given String contains no whitespace
     *         <p/>
     ***********************************************************************************************
     */
    public static int findWhitespace(final String string)
    {
        for (int i = 0; i < string.length(); i++) {
            if (Character.isWhitespace(string.charAt(i))) {
                return i;
            }
        }
        return -1;
    }


    /**
     ***********************************************************************************************
     *                                                                                          <p>
     * <!-- hasWhitespace() -->
     *                                                                                          <p>
     * Determines if the given string contains whitespace.
     *
     * @return index of the first whitespace character in the given String; -1 if the
     *         given String contains no whitespace
     *         <p/>
     ***********************************************************************************************
     */
    public static boolean hasWhitespace(final String string)
    {
        for (int i = 0; i < string.length(); i++) {
            if (Character.isWhitespace(string.charAt(i))) {
                return true;
            }
        }
        return false;
    }


    /**
     ***********************************************************************************************
     *                                                                                          <p>
     * <!-- isWhitespace() -->
     *                                                                                          <p>
     * Determines if the given String is entirely whitespace.
     *
     * @return true if the given String contains whitespace only; false if it contains non-whitespace
     *         characters
     *         <p/>
     ***********************************************************************************************
     */
    public static boolean isWhitespace(final String string)
    {
        return findNonWhitespace(string) == -1;
    }


    /**
     ***********************************************************************************************
     *                                                                                          <p>
     * <!-- findLastNonNewline() -->
     *                                                                                          <p>
     * Finds the last non-newline character in the given String.
     *
     * @return index of the last character in the given string that is not a newline character; -1 if the
     *         given String contains no newlines
     *         <p/>
     ***********************************************************************************************
     */
    public static int findLastNonNewline(final String string)
    {
        for (int i = string.length() - 1; i >= 0; i--) {
            if (string.charAt(i) != '\n') {
                return i;
            }
        }
        return -1;
    } // end findLastNonNewline()


    /**
     ***********************************************************************************************
     *                                                                                          <p>
     * <!-- findLastNonWhitespace() -->
     *                                                                                          <p>
     * Finds the last non-newline character in the given String.
     *
     * @return index of the last character in the given string that is not a whitespace character;
     *         -1 if the given String contains no whitespace
     *         <p/>
     ***********************************************************************************************
     */
    public static int findLastNonWhitespace(final String string)
    {
        for (int i = string.length() - 1; i >= 0; i--) {
            if (!Character.isWhitespace(string.charAt(i))) {
                return i;
            }
        }
        return -1;
    } // end findLastNonWhitespace()


    /**
     ***********************************************************************************************
     *                                                                                          <p>
     * <!-- removeTrailingNewlines() -->
     *                                                                                          <p>
     * Removes any newlines at the end of the string.
     *
     * @return a String with trailing newlines stripped off
     *         <p/>
     ***********************************************************************************************
     */
    public static String removeTrailingNewlines(final String string)
    {
        final int lastNonNewline = findLastNonNewline(string);

        if (lastNonNewline == -1) {
            // the string does not contain any newline
            return "";
        }
        else {
            return string.substring(0, lastNonNewline + 1);
        }
    } // end removeTrailingNewlines()


    /**
     ***********************************************************************************************
     *                                                                                          <p>
     * <!-- removeTrailingWhitespace() -->
     *                                                                                          <p>
     * Removes any whitespace at the end of the string.
     *
     * @return a String with trailing whitespace stripped off
     *         <p/>
     ***********************************************************************************************
     */
    public static String removeTrailingWhitespace(final String string)
    {
        final int lastNonWhitespace = findLastNonWhitespace(string);

        if (lastNonWhitespace == -1) {
            // the string does not contain any newline
            return "";
        }
        else {
            return string.substring(0, lastNonWhitespace + 1);
        }
    } // end removeTrailingWhitespace()


    /**
     * This method extracts the carriage-return character from a string.  JTextArea
     * has problems with \r characters.  Also, dialog boxes don't display messages with \r
     * characters well.
     */
    public static String extractCarriageReturns(final String text)
    {
        return extractDelimiter(text, "\r");
    }

    public static String extractLineFeed(final String text)
    {
        return extractDelimiter(text, "\n");
    }

    public static String extractDelimiter(final String text, final String delimiter)
    {
        StringTokenizer st = new StringTokenizer(text, delimiter);
        StringBuffer sb = new StringBuffer();
        while (st.hasMoreTokens()) {
            sb.append(st.nextToken());
        }
        return sb.toString();
    }

    public static String extractMultipleWhiteSpace(final String text)
    {
        StringTokenizer st = new StringTokenizer(text, " \t");
        StringBuffer sb = new StringBuffer();
        while (st.hasMoreTokens()) {
            sb.append(st.nextToken());
            sb.append(" ");
        }
        return sb.toString().trim();
    }


    /**
     * This method extracts the carriage-return character from a string.  JTextArea
     * has problems with \r characters.  Also, dialog boxes don't display messages with \r
     * characters well.
     */
    public static String extractWhiteSpace(final String text)
    {
        StringTokenizer st = new StringTokenizer(text, " \t\n\r");
        StringBuffer sb = new StringBuffer();
        while (st.hasMoreTokens()) {
            sb.append(st.nextToken());
        }
        return sb.toString();
    }


    /**
     ***********************************************************************************************
     *                                                                                          <p>
     * <!-- toStringChars() -->
     *                                                                                          <p>
     * Traces the int values as hex values for each character in the String.  This is useful
     * when trying to diagnose whitespace problems in strings.  The string is traced as 2
     * columns.  The first column contains the character (for example 'a') and the second
     * column contains the hex value of the character (for example 64).  To avoid problems
     * with newlines, carriage returns and other whitespace, the first column only displays
     * non-whitespace characters (the second column must be used in this case to determine the
     * actual character).  This formatting leads to output like the following (for the
     * string "wait 1 1 2.0"):
     *                                                                                          <p>
     * --------------------
     * wait 1 1 2.0
     * --------------------
     * 0	w	0x77	119
     * 1	a	0x61	97
     * 2	i	0x69	105
     * 3	t	0x74	116
     * 4	sp	0x20	32
     * 5	1	0x31	49
     * 6	sp	0x20	32
     * 7	1	0x31	49
     * 8	sp	0x20	32
     * 9	2	0x32	50
     * 10	.	0x2e	46
     * 11	0	0x30	48
     * --------------------
     *                                                                                          <p>
     * Note:                                                                                   <br>
     * This method allows the caller to specify a range (rather than passing in a substring)
     * so that the index values traced will correspond to absolute indexes into the string
     * instead of relative to the substring.  This is often more useful for the
     * developer.
     *
     * @param string the String whose characters are to be traced
     * @param start  the beginning of the range of characters to be traced, inclusive
     * @param end    the end of the range of characters to be traced, exclusive
     *               <p/>
     *               **********************************************************************************************
     */
    public static String toStringChars(final String string, final int start, final int end)
    {
        StringBuffer sb = new StringBuffer();

        sb.append("--------------------" + LINE_SEP);
        sb.append(string.substring(start, end) + LINE_SEP);
        sb.append("--------------------" + LINE_SEP);
        for (int i = start; i < end; i++) {
            final char ch = string.charAt(i);
            final int chInt = ch;

            if (string.charAt(i) == '\n') {
                // display "\n" for newlines
                sb.append(i + "\t" + "\\n" + "\t0x" + Integer.toString(chInt,
                        16) + "\t" + Integer.toString(chInt, 10) + LINE_SEP);
            }
            else if (string.charAt(i) == '\t') {
                // display "\t" for tabs
                sb.append(i + "\t" + "\\t" + "\t0x" + Integer.toString(chInt,
                        16) + "\t" + Integer.toString(chInt, 10) + LINE_SEP);
            }
            else if (string.charAt(i) == '\r') {
                // display "\r" for carriage returns
                sb.append(i + "\t" + "\\r" + "\t0x" + Integer.toString(chInt,
                        16) + "\t" + Integer.toString(chInt, 10) + LINE_SEP);
            }
            else if (string.charAt(i) == ' ') {
                // display "sp" for spaces
                sb.append(i + "\t" + "sp" + "\t0x" + Integer.toString(chInt,
                        16) + "\t" + Integer.toString(chInt, 10) + LINE_SEP);
            }
            else {
                sb.append(i + "\t" + ch + "\t0x" + Integer.toString(chInt,
                        16) + "\t" + Integer.toString(chInt, 10) + LINE_SEP);
            }
        }
        sb.append("--------------------" + LINE_SEP);

        return sb.toString();
    }


    public static String toStringChars(final String string)
    {
        return toStringChars(string, 0, string.length());
    }


    /**
     ***********************************************************************************************
     *                                                                                          <p>
     * <!-- toStringChars() -->
     *                                                                                          <p>
     * Traces the int values as hex values for each character in the String.  This is useful
     * when trying to diagnose whitespace problems in strings.
     * This formatting leads to output like the following (for the
     * string "wait 1 1 2.0"):
     *                                                                                          <p>
     * w  a  i  t  sp  1  sp  2  .  0  \r
     *                                                                                          <p>
     * Note:                                                                       <br>
     * This method allows the caller to specify a range (rather than passing in a substring)
     * so that the index values traced will correspond to absolute indexes into the string
     * instead of relative to the substring.  This is often more useful for the
     * developer.
     *
     * @param string the String whose characters are to be traced
     * @param start  the beginning of the range of characters to be traced, inclusive
     * @param end    the end of the range of characters to be traced, exclusive
     *               <p/>
     *               **********************************************************************************************
     */
    public static String toStringChars2(final String string, final int start, final int end)
    {
        StringBuffer sb = new StringBuffer();

        for (int i = start; i < end; i++) {
            final char ch = string.charAt(i);
            final int chInt = ch;

            if (string.charAt(i) == '\n') {
                // display "\n" for newlines
                sb.append("|" + "\\n");
            }
            else if (string.charAt(i) == '\t') {
                // display "\t" for tabs
                sb.append("|" + "\\t");
            }
            else if (string.charAt(i) == '\r') {
                // display "\r" for carriage returns
                sb.append("|" + "\\r");
            }
            else if (string.charAt(i) == ' ') {
                // display "sp" for spaces
                sb.append("|" + "sp");
            }
            else {
                sb.append("|" + ch);
            }
        }
        sb.append("|");

        return sb.toString();
    }


    public static String toStringChars2(final String string)
    {
        return toStringChars2(string, 0, string.length());
    }

    /**
    ***********************************************************************************************
     *                                                                                          <p>
     *  <!-- getFilenamePrefixNew() -->
     *                                                                                          <p>
     *  This method returns the filename prefix (the filename without any extension).  For
     *  example, if filename is "junk.txt", this method will return "junk".
     *                                                                                          <p>
     *  More examples:
     *    C:\work\xxx\chipdesign.xml.zip ->  "C:\work\xxx\chipdesign.xml"
     *    C:\work\xxx\chipdesign.csv.zip ->  "C:\work\xxx\chipdesign.csv"
     *    C:\work\xxx\chipdesign.log.txt ->  "C:\work\xxx\chipdesign.xml"
     *    C:\work\xxx\.properties ->  "C:\work\xxx\"
     *
     *  Please check with Dave before modifying this method.  It has recently been rolled
     *  back to a more original implementation (which deals with 1 trailing extension and
     *  ignores any preceeding '.' characters).
     *
     ***********************************************************************************************
     */
    public static String getFilenamePrefixNew(final String filename) // tempdt "New" suffix due to getFilenamePrefixNew(); New's can be removed once everything builds okay
    {
        // assert pre-conditions

        final int extensionPos = filename.toLowerCase().lastIndexOf(".");
        String filenamePrefix;
        if (extensionPos != -1)
        {
            filenamePrefix = filename.substring(0, extensionPos);
        }
        else
        {
            filenamePrefix = filename;
        }

        return(filenamePrefix);
    }



    /**
     ***********************************************************************************************
     *                                                                                          <p>
     * <!-- getFilenameExtension() -->
     *                                                                                          <p>
     * This method returns the filename extension.  For example, if filename is "junk.txt",
     * this method will return ".txt".
     *
     * I am changing this to return .txt versus txt to keep it the same a FileLocation.getExtension()
     * --chris 7/26/06
     *                                                                                          <p>
     ***********************************************************************************************
     */
    public static String getFilenameExtension(final String filename)
    {
        // assert pre-conditions

        final int extensionPos = filename.lastIndexOf(".");
        if (extensionPos == -1) {
            // there is no extension (i.e., "junk" instead of "junk.txt")
            return null;
        }
        else {
            final String extension = filename.substring(extensionPos);
            return extension;
        }
    }

    /**
     * Converts arbitrary string into a string that consists of only
     * alphanumeric charcters and '_'. This is needed for legal file names.
     * This is actually more restrictive then filenames. If there is a need
     * we can add other legal charcters.
     *
     * @param name String input string
     * @return String output alphanumeric string
     */
    public static String makeLegalFileName(String name)
    {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < name.length(); i++) {
            char oneChar = name.charAt(i);
            if (Character.isLetterOrDigit(oneChar) || oneChar == '_') {
                buffer.append(oneChar);
            }
        }

        return buffer.toString();
    }


    public static String[] toStringArray(final ArrayList strings)
    {
        String[] stringArray = new String[strings.size()];
        for (int i = 0; i < strings.size(); i++) {
            stringArray[i] = (String) strings.get(i);
        }
        return stringArray;
    }

    /**
     * a good format for numbers in the 10-6 range
     *
     * @param n number to format
     * @return
     */
    public static String formatMicro(double n)
    {
        StringBuffer sb = new StringBuffer();
        DecimalFormat df1 = new DecimalFormat("####0.00");
        String str = df1.format(n * 1000000.0);
        sb.append(str);
        sb.append("E-6");
        return sb.toString();
    }

    /**
     * a good format for numbers in the 10-6 range
     *
     * @param n number to format
     * @return
     */
    public static String formatMega(double n)
    {
        StringBuffer sb = new StringBuffer();
        DecimalFormat df1 = new DecimalFormat("####0.00");
        String str = df1.format(n / 1000000.0);
        sb.append(str);
        return sb.toString();
    }

    /**
     * a good format for numbers in the 10-3 range
     *
     * @param n number to format
     * @return
     */
    public static String formatMilli(double n)
    {
        StringBuffer sb = new StringBuffer();
        DecimalFormat df1 = new DecimalFormat("####0.00");
        String str = df1.format(n * 1000.0);
        sb.append(str);
        sb.append("E-3");
        return sb.toString();
    }

    /**
     * This method takes a char like '*' and fills a new string to 'length'
     * For example,
     * If you want a string with 5 '*' you would call fill(5, '*');
     * @param length int
     * @param charToFill char
     * @return String
     */
    public static String fill(int length, char charToFill)
    {
        StringBuffer newString = new StringBuffer(length);
        for(int stringNum = 0; stringNum < length; stringNum ++)
        {
            newString.append(charToFill);
        }
        return newString.toString();
    }

    /**
     * a good format for numbers in the 1-10000 range
     *
     * @param n number to format
     * @return
     */
    public static String formatUni(double n)
    {
        StringBuffer sb = new StringBuffer();
        DecimalFormat df1 = new DecimalFormat("#####0.00");
        String str = df1.format(n);
        sb.append(str);
        return sb.toString();
    }

    /**
     * fint the part of a string which start with a digig
     * i.e. cbr3 -> 3
     *
     * @param s non-null string
     * @return non-null string
     */
    public static String dropNonNumeric(String s)
    {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c))
                return s.substring(i);
        }
        return s;
    }

     /**
     * Tests if the string starts with one of the keys
     * @param line String
     * @param startStr String[]
     * @return int index in the array of startStr, or -1 if not found
     */
    public static int startsWithOneOfStrings (String line, String [] startStr)
    {
        int indexOfStartStr = -1;

        for(int i = 0 ; i < startStr.length; i++)
        {
            if(line.startsWith(startStr[i]))
            {
                indexOfStartStr = i;
                break;
            }
        }
        return indexOfStartStr;
    }


} // end StringOps
