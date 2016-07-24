package com.lordjoe.utilities;

import com.lordjoe.lib.xml.*;

import java.io.*;
import java.util.*;


public class COMSurrogateBuilder {
    private boolean m_doubleIsDate; //

    public COMSurrogateBuilder() {
        m_doubleIsDate = true;
    }

    public void setDoubleIsDate(boolean doit) {
        m_doubleIsDate = doit;
    }

    public void buildCOMSurrogate(String Package, String Prefix, Class in, File TargetDirectory) throws IOException {
        buildCOMSurrogateBase(Package, Prefix, in, TargetDirectory);
        buildCOMSurrogateWrapper(Package, Prefix, in, TargetDirectory);
    }

    protected void buildCOMSurrogateWrapper(String Package, String Prefix, Class in, File TargetDirectory) throws IOException {
        String FileName = Package + "." + Prefix + ClassUtilities.shortClassName(in);
        FileName = FileName.replace('.', '/');
        FileName = FileName.replace('\\', '/');
        FileName += ".java";
        if (TargetDirectory != null)
            FileName = TargetDirectory.getAbsolutePath().replace('\\', '/') + "/" + FileName;
        FileUtilities.guaranteeDirectory(FileName);
        File Test = new File(FileName);
        if (Test.exists())
            return; // do not overwrite
        PrintWriter out = new PrintWriter(new FileOutputStream(Test));
        buildCOMSurrogateWrapper(Package, Prefix, out, in);
    }

    protected void buildCOMSurrogateBase(String Package, String Prefix, Class in, File TargetDirectory) throws IOException {
        String FileName = Package + ".gen._" + Prefix + ClassUtilities.shortClassName(in);
        FileName = FileName.replace('.', '/');
        FileName = FileName.replace('\\', '/');
        FileName += ".java";
        if (TargetDirectory != null)
            FileName = TargetDirectory.getAbsolutePath().replace('\\', '/') + "/" + FileName;
        FileUtilities.guaranteeDirectory(FileName);
        File Test = new File(FileName);
        PrintWriter out = new PrintWriter(new FileOutputStream(Test));
        buildCOMSurrogate(Package, Prefix, out, in);
    }

    protected void buildCOMSurrogateWrapper(String Package, String Prefix, PrintWriter out, Class in) throws IOException {
        String ClassName = Prefix + ClassUtilities.shortClassName(in);
        writeWrapperHeader(Package, ClassName, out, in);
        writeWrapperConstructors(ClassName, out, in);
        writeFooter(ClassName, out, in);
        out.close();
    }

    protected void buildCOMSurrogate(String Package, String Prefix, PrintWriter out, Class in) throws IOException {
        String ClassName = "_" + Prefix + ClassUtilities.shortClassName(in);
        ClassProperty[] properties = ClassAnalyzer.getProperties(in);
        properties = filterProperties(properties);
        writeHeader(Package, ClassName, out, in);
        writePropertiesFields(out, properties, in);
        writeConstructors(ClassName, out, properties, in);
        writeTargetSetter(ClassName, out, properties, in);
        writePropertiesAccessors(out, properties, in);
        writeFooter(ClassName, out, in);
        out.close();
    }

    /**
     * drop properties which do not work
     * @param in non-null properties array
     * @return non-null properties array
     */
    protected ClassProperty[] filterProperties(ClassProperty[] properties) {
        List holder = new ArrayList(properties.length);
        for (int i = 0; i < properties.length; i++) {
            String name = properties[i].getName();
            if (name.equals("Class") || name.equals("_Class"))
                continue; // not a real property
            Class[] args = properties[i].getGetMethod().getParameterTypes();
            if (properties[i].isIndexed())
                continue; // for now we do not support indexed properties
            holder.add(properties[i]);
        }
        return ((ClassProperty[]) Util.collectionToArray(holder, ClassProperty.class));
    }

    protected String realPropName(ClassProperty in) {
        String ret = in.getName();
        if (ret.startsWith("_"))
            ret = ret.substring(1);
        return (ret);
    }

    public String adjustTypeName(ClassProperty in) {
        Class retType = in.getGetMethod().getReturnType();
        if (m_doubleIsDate && retType == Double.TYPE)
            return ("Calendar");
        return (ClassUtilities.shortClassName(retType));
    }

    public String doTransience(ClassProperty in) {
        Class retType = in.getGetMethod().getReturnType();
        if (retType.isPrimitive())
            return ("");
        if (Serializable.class.isAssignableFrom(retType))
            return ("");
        return ("transient ");
    }

    public void writeGetMethod(PrintWriter out, ClassProperty field, String getName, String setName) {
        String RealName = realPropName(field);
        int indent = 1;
        Util.indent(out, indent);
        out.println("/**");
        Util.indent(out, indent);
        out.println("* code to get parameter " + RealName);
        Util.indent(out, indent);
        out.println("* @return <Add Comment Here>");
        Util.indent(out, indent);
        out.println("* @see " + getName);
        Util.indent(out, indent);
        out.println("*/");
        Util.indent(out, indent);
        out.print("public ");
        String TypeName = adjustTypeName(field);
        out.print(TypeName);
        out.println(" " + getName + "()");
        Util.indent(out, indent);
        out.println("{");
        Util.indent(out, indent + 1);
        out.println("return(m_" + RealName + ");");
        Util.indent(out, indent);
        out.println("}");
        out.println();
    }

    public void writeSetMethod(PrintWriter out, ClassProperty prop, String getName, String setName) {
        String RealName = realPropName(prop);
        int indent = 1;
        Util.indent(out, indent);
        out.println("/**");
        Util.indent(out, indent);
        out.println("* code to set parameter " + RealName);
        Util.indent(out, indent);
        out.println("* @param in <Add Comment Here>");
        Util.indent(out, indent);
        out.println("* @see " + getName);
        Util.indent(out, indent);
        out.println("*/");
        Util.indent(out, indent);
        out.print("public void ");
        out.print(setName);
        out.print("(");
        out.print(adjustTypeName(prop));
        out.println(" in)");
        Util.indent(out, indent);
        out.println("{");
        Util.indent(out, indent + 1);
        out.println("m_" + RealName + " = in;");
        Util.indent(out, indent);
        out.println("}");
        out.println();
    }

    protected void writeWrapperConstructors(String ClassName, PrintWriter out, Class in) {
        int indent = 1;
        Util.indent(out, indent);
        out.println("/**");
        Util.indent(out, indent);
        out.println("* default constructor");
        Util.indent(out, indent);
        out.println("*/");
        Util.indent(out, indent);
        out.println("public " + ClassName + "()");
        Util.indent(out, indent);
        out.println("{");
        Util.indent(out, indent);
        out.println("}");

        Util.indent(out, indent);
        out.println("/**");
        Util.indent(out, indent);
        out.println("* populating constructor");
        Util.indent(out, indent);
        out.println("*/");
        Util.indent(out, indent);
        out.println("public " + ClassName + "(" + ClassUtilities.shortClassName(in) + " in)");
        Util.indent(out, indent);
        out.println("{");
        Util.indent(out, indent + 1);
        out.println("super(in);");
        Util.indent(out, indent);
        out.println("}");
    }

    protected void writeConstructors(String ClassName, PrintWriter out, ClassProperty[] properties, Class in) {
        int indent = 1;
        Util.indent(out, indent);
        out.println("/**");
        Util.indent(out, indent);
        out.println("* default constructor");
        Util.indent(out, indent);
        out.println("*/");
        Util.indent(out, indent);
        out.println("public " + ClassName + "()");
        Util.indent(out, indent);
        out.println("{");
        Util.indent(out, indent);
        out.println("}");

        Util.indent(out, indent);
        out.println("/**");
        Util.indent(out, indent);
        out.println("* populating constructor");
        Util.indent(out, indent);
        out.println("*/");
        Util.indent(out, indent);
        out.println("public " + ClassName + "(" + ClassUtilities.shortClassName(in) + " in)");
        Util.indent(out, indent);
        out.println("{");
        Util.indent(out, indent + 1);
        out.println("this();");
        for (int i = 0; i < properties.length; i++)
            writeConstructorSetter(out, properties[i], in, indent + 1);
        Util.indent(out, indent);
        out.println("}");
    }

    protected void writeConstructorSetter(PrintWriter out, ClassProperty prop, Class in, int indent) {
        Util.indent(out, indent);
        Class retType = prop.getGetMethod().getReturnType();
        if (m_doubleIsDate && retType == Double.TYPE) {
            out.println("// Convert double VB Data to Calendar");
            Util.indent(out, indent);
            out.println("set" + realPropName(prop) + "(COMUtilities.fromVBDate(in.get" + prop.getName() + "()));");
        } else {
            out.println("set" + realPropName(prop) + "(in.get" + prop.getName() + "());");
        }
    }

    protected void writeTargetSetter(String ClassName, PrintWriter out, ClassProperty[] properties, Class in) {
        int indent = 1;

        Util.indent(out, indent);
        out.println("/**");
        Util.indent(out, indent);
        out.println("* populating call");
        Util.indent(out, indent);
        out.println("* @param out non-null item to populate");
        Util.indent(out, indent);
        out.println("*/");
        Util.indent(out, indent);
        out.println("public void setTarget(" + ClassUtilities.shortClassName(in) + " out)");
        Util.indent(out, indent);
        out.println("{");
        for (int i = 0; i < properties.length; i++) {
            if (properties[i].getSetMethod() != null)
                writeTargetSetter(out, properties[i], in, indent + 1);
        }
        Util.indent(out, indent);
        out.println("}");
    }

    protected void writeTargetSetter(PrintWriter out, ClassProperty prop, Class in, int indent) {
        Util.indent(out, indent);
        String PropName = realPropName(prop);
        Class PropType = prop.getType();
        if (PropType == null)
            PropType = prop.getGetMethod().getReturnType();
        if (!PropType.isPrimitive()) {
            String HolderName = "Holder" + PropName;
            out.println(ClassUtilities.shortClassName(PropType) + " " + HolderName + " = get" + PropName + "();");
            Util.indent(out, indent);
            out.println("if(" + HolderName + " != null)");
            Util.indent(out, indent + 1);
            out.println("out.set" + prop.getName() + "(" + HolderName + ");");
        } else {
            if (m_doubleIsDate && PropType == Double.TYPE) {
                // convert from Calendar to Date
                String HolderName = "Holder" + PropName;
                out.println("Calendar " + HolderName + " = get" + PropName + "();");
                Util.indent(out, indent);
                out.println("if(" + HolderName + " != null)");
                Util.indent(out, indent + 1);
                out.println("out.set" + prop.getName() + "(COMUtilities.vbDate(" + HolderName + "));");
            } else {
                out.println("out.set" + prop.getName() + "(get" + PropName + "());");
            }
        }
    }

    protected void writePropertiesFields(PrintWriter out, ClassProperty[] properties, Class in) {
        out.println("// ============================================");
        out.println("// Derived fields");
        out.println("// ============================================");
        for (int i = 0; i < properties.length; i++)
            writePropertyField(out, properties[i], in);
    }

    protected void writePropertyField(PrintWriter out, ClassProperty prop, Class in) {
        out.println("     private " + doTransience(prop) + adjustTypeName(prop) + " m_" + realPropName(prop) + ";");
    }

    protected void writePropertiesAccessors(PrintWriter out, ClassProperty[] properties, Class in) {
        out.println("// ============================================");
        out.println("// accessor methods");
        out.println("// ============================================");
        for (int i = 0; i < properties.length; i++)
            writePropertyAccessor(out, properties[i], in);
    }

    protected void writePropertyAccessor(PrintWriter out, ClassProperty prop, Class in) {
        String RealName = realPropName(prop);
        String getName = "get" + RealName;
        String setName = "set" + RealName;
        writeSetMethod(out, prop, getName, setName);
        writeGetMethod(out, prop, getName, setName);
    }

    protected void writeWrapperHeader(String Package, String ClassName, PrintWriter out, Class in) throws IOException {
        out.println("package " + Package + ";");
        out.println("import " + Package + ".gen.*;");
        out.println("import " + ClassUtilities.packageName(in) + ".*;");
        out.println("import java.util.*;");
        if (m_doubleIsDate) {
            out.println("import com.lordjoe.Utilities.COMUtilities;");
        }

        out.println();
        out.println("/**");
        out.println("* java surrogate for com object " + in.getName());
        out.println("* It is OK to edit this code ");
        out.println("* @author S Lewis");
        out.println("*/");
        out.println("public class " + ClassName + " extends _" + ClassName);
        out.println("{");

    }

    protected void writeHeader(String Package, String ClassName, PrintWriter out, Class in) throws IOException {
        out.println("package " + Package + ".gen;");
        out.println("import " + ClassUtilities.packageName(in) + ".*;");
        out.println("import java.io.*;");
        out.println("import java.util.*;");
        if (m_doubleIsDate) {
            out.println("import com.lordjoe.Utilities.COMUtilities;");
        }

        out.println();
        out.println("/**");
        out.println("* java surrogate for com object " + in.getName());
        out.println("* DO NOT EDIT THIS CODE ");
        out.println("* EDIT the subclass " + ClassName.substring((1)));
        out.println("* @author S Lewis");
        out.println("*/");
        out.println("public class " + ClassName + " implements Serializable ");
        out.println("{");

    }

    protected void writeFooter(String Prefix, PrintWriter out, Class in) throws IOException {
        out.println("}");

    }
}
