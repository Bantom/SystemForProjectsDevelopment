package com.bulgakov.db;

import com.bulgakov.db.dbOracle.OracleDAO;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author Bulgakov
 * @since 16.03.2017
 */
//@WebListener
public class ServletListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        OracleDAO dao = new OracleDAO();
        sce.getServletContext().setAttribute("dao", dao);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
