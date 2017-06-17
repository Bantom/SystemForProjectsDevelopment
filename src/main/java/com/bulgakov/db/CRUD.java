package com.bulgakov.db;

import com.bulgakov.exceptions.UnknownObjectType;

/**
 * @author Bulgakov
 * @since 13.02.2017
 */
public interface CRUD {

    public void create(Object obj) throws UnknownObjectType;

    public Object read(Integer id) throws UnknownObjectType;

    public void update(Object obj) throws UnknownObjectType;

    public void delete(Integer id) throws UnknownObjectType;
}
