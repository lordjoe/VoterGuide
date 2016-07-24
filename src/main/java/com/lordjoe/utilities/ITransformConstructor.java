package com.lordjoe.utilities;

/**
 *  Implementers promise to construct one object from another
 */
public interface ITransformConstructor {
    public Object build(Object in);
}
