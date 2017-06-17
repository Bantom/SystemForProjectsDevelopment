package com.bulgakov.db.dbOracle;

import com.bulgakov.db.CRUD;
import com.bulgakov.db.dbOracle.propertyFile.DBProperties;
import com.bulgakov.db.dbOracle.propertyFile.WorkWithPropertyFile;
import com.bulgakov.exceptions.UnknownObjectType;
import com.bulgakov.model.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

import static com.bulgakov.db.dbOracle.OracleDeleteMethods.*;
import static com.bulgakov.db.dbOracle.OracleInsertMethods.*;
import static com.bulgakov.db.dbOracle.OracleReadMethods.*;
import static com.bulgakov.db.dbOracle.OracleUpdateMethods.*;
import static com.bulgakov.db.dbOracle.OracleUtils.*;

/**
 * @author Bulgakov
 * @since 13.02.2017
 */
public class OracleDAO implements CRUD {

    /**
     * Method create connection to database.
     *
     * @return connection to database
     */
    public static Connection getDBConnection() {
//        DBProperties dbProperties = WorkWithPropertyFile.getFromPropertyFile("application.properties");
//        Connection con = null;
//        try {
//            Locale.setDefault(Locale.ENGLISH);
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            con = DriverManager.getConnection(dbProperties.getHost(), dbProperties.getUserName(), dbProperties.getPassword());
//        } catch (Exception e) {
//            try {
//                con.close();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
        InitialContext initContext = null;
        Connection con = null;
        try {
            initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/firmProject");
            con = ds.getConnection();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    @Override
    public void create(Object obj) throws UnknownObjectType {
        if (obj instanceof Customer) {
            Customer customerTmp = (Customer) obj;
            insertCustomer(customerTmp);
        } else if (obj instanceof User) {
            User userTmp = (User) obj;
            insertUser(userTmp);
        } else if (obj instanceof Project) {
            Project projectTmp = (Project) obj;
            insertProject(projectTmp);
        } else if (obj instanceof Sprint) {
            Sprint sprintTmp = (Sprint) obj;
            insertSprint(sprintTmp);
        } else if (obj instanceof Task) {
            Task taskTmp = (Task) obj;
            insertTask(taskTmp);
        } else if (obj instanceof RequestForIncreasingTime) {
            RequestForIncreasingTime requestTmp = (RequestForIncreasingTime) obj;
            insertRequest(requestTmp);
        } else if (obj instanceof DoTask) {
            DoTask doTaskTmp = (DoTask) obj;
            insertDoTask(doTaskTmp);
        } else {
            throw new UnknownObjectType();
        }
    }

    @Override
    public Object read(Integer id) {
        String objectType = getObjectType(id);
        switch (objectType) {
            case "Customer":
                return readCustomer(id);
            case "User":
                return readUser(id);
            case "Project":
                return readProject(id);
            case "Sprint":
                return readSprint(id);
            case "Task":
                return readTask(id);
            case "Request":
                return readRequest(id);
            case "DoTask":
                return readDoTask(id);
            default:
                return null;
        }
    }

    @Override
    public void update(Object obj) throws UnknownObjectType {
        if (obj instanceof Customer) {
            updateCustomer(obj);
        } else if (obj instanceof User) {
            updateUser(obj);
        } else if (obj instanceof Project) {
            updateProject(obj);
        } else if (obj instanceof Sprint) {
            updateSprint(obj);
        } else if (obj instanceof Task) {
            updateTask(obj);
        } else if (obj instanceof RequestForIncreasingTime) {
            updateRequest(obj);
        } else if (obj instanceof DoTask) {
            updateDoTask(obj);
        } else {
            throw new UnknownObjectType();
        }
    }

    @Override
    public void delete(Integer id) {
        String objectType = getObjectType(id);
        switch (objectType) {
            case "Customer":
                deleteCustomer(id);
                break;
            case "User":
                deleteUser(id);
                break;
            case "Project":
                deleteProject(id);
                break;
            case "Sprint":
                deleteSprint(id);
                break;
            case "Task":
                deleteTask(id);
                break;
            case "Request":
                deleteRequest(id);
                break;
            case "DoTask":
                deleteDoTask(id);
                break;
        }
    }
}
