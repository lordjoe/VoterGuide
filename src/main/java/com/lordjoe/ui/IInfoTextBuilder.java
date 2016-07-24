package com.lordjoe.ui;

/**
 * com.lordjoe.ui.IInfoTextBuilder
 * return a string describing the object
 * @author Steve Lewis
 * @date Oct 11, 2005
 */
public interface IInfoTextBuilder
{
    /**
     * build a string describing the current object
     * @return
     */
    public String buildInfoText();

    /**
     * build a string describing the current object and parents but
     * not child elements
     * @return
     */
     public String buildSelfInfoText(int indent);

    /**
      * build a string describing the current object
      * @param indent indent level
      * @return
      */
      public String buildInfoText(int indent,boolean asChild);


}
