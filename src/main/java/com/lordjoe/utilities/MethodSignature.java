package com.lordjoe.utilities;
import java.util.Vector;
/**
* This class provides a useful routine to convert signatures to
* regular java type declarations.
*/
public class MethodSignature {
    public String[] m_ArgumentTypes;
    public String   m_ReturnType;
    public String   m_SignatureString;

    public MethodSignature(String[] ArgumentTypes,String ReturnType) {
        m_ArgumentTypes = ArgumentTypes;
        m_ReturnType    = ReturnType;
        m_SignatureString = makeSignatureString();
    }

    public MethodSignature(String SignatureString) {
        m_SignatureString = SignatureString;
        makeArguments();
    }

    protected String makeSignatureString()
    {
        String ret = "(";
        for(int i = 0; i < m_ArgumentTypes.length; i++) {
            ret += typeToSignatureString(m_ArgumentTypes[i]);
        }
        ret = ")";
        ret += typeToSignatureString(m_ReturnType);
        return(ret);
    }

    protected void makeArguments()
    {
        int retIndex = m_SignatureString.indexOf(")");
        String retSig = m_SignatureString.substring(retIndex + 1);
        if(retSig.endsWith(";"))
            retSig = retSig.substring(0,retSig.length() - 1);
        int ArrayCount = 0;
        while(retSig.startsWith("[")) {
            ArrayCount++;
            retSig = retSig.substring(1);
        }
        
        m_ReturnType = signatureTypeToType(retSig,ArrayCount);
        String ArgString = m_SignatureString.substring(1,retIndex);
        if(ArgString.length() == 0) {
            m_ArgumentTypes = new String[0];
            return;
        }
        Vector ArgNames = new Vector();
        int ArrayIndex = 0; 
        String TestArg;
        while(ArgString.length() > 0) {
            switch( ArgString.charAt(0)) {
        		case '[':
        		    ArgString = ArgString.substring(1);
        		    ArrayIndex++;
        		    break;
        		case 'V':
        		case 'B':
        		case 'S':
        		case 'I':
        		case 'J':
        		case 'F':
        		case 'D':
        		case 'Z':
        		    TestArg = ArgString.substring(0,1);
        		    ArgNames.addElement(signatureTypeToType(TestArg,ArrayIndex));
        		    ArgString = ArgString.substring(1);
        		    ArrayIndex = 0;
        			break;
    		    case 'L':
                    int semiIndex = ArgString.indexOf(";");
                    TestArg = ArgString.substring(0,semiIndex);
                    ArgNames.addElement(signatureTypeToType(TestArg,ArrayIndex));
                    if(semiIndex < ArgString.length() - 1) 
                        ArgString = ArgString.substring(semiIndex + 1);
                    else
                        ArgString = "";
                    ArrayIndex = 0;
            }
        }
        m_ArgumentTypes = new String[ArgNames.size()];
        ArgNames.copyInto(m_ArgumentTypes);
    }

    public static String typeToSignatureString(String in)
    {
        if(in == null) return("V");
        String ret = "";
        while(in.endsWith("[]")) {
            ret += "[";
            in = in.substring(0,in.length() - 2);
        }
        if(in.equals("void"))
            return(ret +"V");
        if(in.equals("byte"))
            return(ret +"B");
        if(in.equals("short"))
            return(ret +"S");
        if(in.equals("int"))
            return(ret +"I");
        if(in.equals("long"))
            return(ret +"J");
        if(in.equals("float"))
            return(ret +"F");
        if(in.equals("double"))
            return(ret +"D");
        if(in.equals("boolean"))
            return(ret +"Z");
        return(ret +"L" + in.replace('.','/'));
    }

	/**
	* Convert a signature into a normal type declaration.
	* <ul>
	* <li>"I",null -&gt; "int"
	* <li>"Z","foo" -&gt; "boolean foo"
	* <li>"[J","foo" -&gt; "long[] foo"
	* <li>"Lmypackage/MyClass;",null -&gt; "mypackage.MyClass"
	* <li>"()V",null -&gt; "void ()"
	* <li>"(S[Ljava/lang/String;)V","foo" -&gt; "void foo (short, String[])"
	* </ul>
	* @param sig the signature.
	* @param name the name of the field or method.  This can be null.
	*/

	public static String signatureTypeToType(String sig,int ArrayCount) {
	    String arrays = "";
	    for(int i = 0; i < ArrayCount; i++) {
	        arrays += "[]";
	    }
		String result = "";

		switch (sig.charAt(0)) {
    		case 'V':
    			result = "void";
    			sig = sig.substring(1);
    			break;
    		case 'B':
    			result = "byte";
    			sig = sig.substring(1);
    			break;
    		case 'S':
    			result = "short";
    			sig = sig.substring(1);
    			break;
    		case 'I':
    			result = "int";
    			sig = sig.substring(1);
    			break;
    		case 'J':
    			result = "long";
    			sig = sig.substring(1);
    			break;
    		case 'F':
    			result = "float";
    			sig = sig.substring(1);
    			break;
    		case 'D':
    			result = "double";
    			sig = sig.substring(1);
    			break;
    		case 'Z':
    			result = "boolean";
    			sig = sig.substring(1);
    			break;
    		case 'L':
    			{
    				result = sig.substring(1).replace('/', '.');
    				if (result.startsWith("java.lang."))
    					result = result.substring(10);
    			}
    			break;
    	    default:
    	        Assertion.fatalError();
		}
        return(result + arrays);
	}
// end class MethodSignature
}
