package com.lordjoe.utilities;

/**
 * com.lordjoe.utilities.IVisitor
 *  This is an implementation of the visitor pattern
 * visitor pattern
 * @author Steve Lewis
 * @date Dec 29, 2007
 */
public interface IVisitor<T> {
    public static IVisitor[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IVisitor.class;

    /**
     * do whatever a visitor does
     * @param target non-null target
     */
    public void visit(T target );
}
