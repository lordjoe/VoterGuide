package com.lordjoe.utilities;

/**
 *  Implementers promise to construct one object from another
 */
public interface ITransformMap {
    public ITransformConstructor getTransform(Class Result);
}
