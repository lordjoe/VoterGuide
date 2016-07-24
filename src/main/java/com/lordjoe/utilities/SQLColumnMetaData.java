package com.lordjoe.utilities;

/*
 * com.lordjoe.Utilities.SQLColumnMetaData
 * @author smlewis
 * Date: Jun 13, 2002
 */

public class SQLColumnMetaData implements INameable
{
    public static final SQLColumnMetaData[] EMPTY_ARRAY = {};

    private String m_Name;
    private int m_SQLType;
    private String m_TypeName;
    private int m_Width;
    private boolean m_Nullable;
    private boolean m_PrimaryKey;
    private String m_TableName;
    private String m_ForeignKey;

    public SQLColumnMetaData() {

    }

    public String getName()
    {
        return m_Name;
    }

    public void setName(String name)
    {
        m_Name = name;
    }

    public int getSQLType()
    {
        return m_SQLType;
    }

    public void setSQLType(int SQLType)
    {
        m_SQLType = SQLType;
    }

    public String getTypeName()
    {
        return m_TypeName;
    }

    public void setTypeName(String typeName)
    {
        m_TypeName = typeName;
    }

    public int getWidth()
    {
        return m_Width;
    }

    public void setWidth(int width)
    {
        m_Width = width;
    }

    public boolean isNullable()
    {
        return m_Nullable;
    }

    public void setNullable(boolean nullable)
    {
        m_Nullable = nullable;
    }

    public String getTableName()
    {
        return m_TableName;
    }

    public void setTableName(String tableName)
    {
        m_TableName = tableName;
    }

    public boolean isPrimaryKey()
    {
        return m_PrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey)
    {
        m_PrimaryKey = primaryKey;
    }

    public String getForeignKey()
    {
        return m_ForeignKey;
    }

    public void setForeignKey(String foreignKey)
    {
        m_ForeignKey = foreignKey;
    }


}
