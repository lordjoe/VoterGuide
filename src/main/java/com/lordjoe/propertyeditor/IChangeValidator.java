package com.lordjoe.propertyeditor;

import java.util.*;

/**
 * com.lordjoe.propertyeditor.IChangeValidator
 *    A change validator can test proposed changes before
 * inplementing them allowing tests that cross single property values
 * @author Steve Lewis
 * @date Jan 28, 2008
 */
public interface IChangeValidator {
    public static IChangeValidator[] EMPTY_ARRAY = {};
    public static Class THIS_CLASS = IChangeValidator.class;

    /**
     * method used for group validation
     *
     * @param proposed non-null list of proposed changes
     * @return if null all op otherwise a list of properties which
     *         are problematic
     */
    public IValidationReason[] validateProposedChange(Map<String, Object> proposed);
}
