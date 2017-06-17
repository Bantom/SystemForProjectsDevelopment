package com.bulgakov.exceptions;

/**
 * @author Bulgakov
 * @since 17.02.2017
 */
public class UnknownObjectType extends Exception{
    public UnknownObjectType(String message) {
        System.out.println(message);
    }

    public UnknownObjectType() {}
}
