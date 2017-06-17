package com.bulgakov.db.dbOracle.propertyFile;

import com.bulgakov.db.dbOracle.propertyFile.DBProperties;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @author Bulgakov
 * @since 13.02.2017
 */
public class WorkWithPropertyFile {

    /**
     * Method writes data for connection to database in application.properties file.
     *
     * @param host host of db.
     * @param userName user name.
     * @param password password.
     */
    public static void writeInPropertyFile(String host, String userName, String password) {
        Properties prop = new Properties();
        OutputStream output = null;
        try {
            output = new FileOutputStream("src/main/resources/application.properties");
            prop.setProperty("host", host);
            prop.setProperty("dbuser", userName);
            prop.setProperty("dbpassword", password);
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Method get data for connection to database from file with name from params.
     *
     * @param fileName file with information.
     * @return object with data for connection to database.
     */
    public static DBProperties getFromPropertyFile(String fileName) {
        Properties prop = new Properties();
        DBProperties dbproperties = new DBProperties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = null;
        try {
            input = classLoader.getResourceAsStream(fileName);
            prop.load(input);
            dbproperties.setHost(prop.getProperty("host"));
            dbproperties.setUserName(prop.getProperty("dbuser"));
            dbproperties.setPassword(prop.getProperty("dbpassword"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return dbproperties;
    }
}
