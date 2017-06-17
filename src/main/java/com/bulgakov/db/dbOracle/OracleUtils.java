package com.bulgakov.db.dbOracle;

import com.bulgakov.model.Customer;
import com.bulgakov.model.LightVersion.CustomerLight;
import com.bulgakov.model.LightVersion.UserLight;
import com.bulgakov.model.Person;
import com.bulgakov.model.Task;
import com.bulgakov.model.User;
import org.springframework.jca.cci.core.ConnectionCallback;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Bulgakov
 * @since 10.03.2017
 */
public class OracleUtils {

    private static OracleDAO dao = new OracleDAO();

    /**
     * Method gets all type qualification from database.
     *
     * @return list of qualification types.
     */
    public static List<String> getQualifications() {
        List<String> recordsList = new ArrayList<>();
        Connection conn = null;
        try {
            conn = OracleDAO.getDBConnection();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT VALUE FROM DATA WHERE OBJECT_ID IN (SELECT OBJECT_ID FROM OBJECTS WHERE OBJECTS.TYPE_NAME = 'Qualification')");
            while (rs.next()) {
                String qualification = rs.getString("VALUE");
                recordsList.add(qualification);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return recordsList;
    }

    /**
     * Method returns all types of roles.
     *
     * @return list of roles.
     */
    public static List<String> getRoles() {
        List<String> roles = new ArrayList<>();
        roles.add("User");
        roles.add("Manager");
        return roles;
    }

    /**
     * Method transform String value to Boolean value. "True" -> True, "False" -> False.
     *
     * @param status string value of boolean type.
     * @return boolean type of string value or NULL if unknown string value.
     */
    public static Boolean stringStatusToBoolean(String status) {
        if ("true".equals(status)) {
            return true;
        } else if ("false".equals(status)){
            return false;
        }else{
            return null;
        }
    }

    /**
     * Method gets unoccupied id for new object.
     *
     * @return id for new object.
     */
    public static Integer getIdForObject() {
        Integer id = null;
        Connection con = null;
        try {
            con = OracleDAO.getDBConnection();
            CallableStatement proc = null;
            proc = con.prepareCall("{ call getMaxId(?) }");
            proc.registerOutParameter(1, Types.INTEGER);
            proc.execute();
            id = proc.getInt(1) + 1;
        } catch (Exception e) {
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }

    /**
     * Method returns object with short info about user or customer such as name, surname,role.
     *
     * @param id of user.
     * @return object with short info about user.
     */
    public static Person getShortInfo(Integer id) {
        Person person = null;
        Connection conn = null;
        try {
            conn = OracleDAO.getDBConnection();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT type_name FROM OBJECTS WHERE object_id = "+ id);
            while (rs.next()) {
                if (rs.getString("type_name").equals("User")) {
                    User user = new User();
                    user.setPersonId(id);
                    conn.setAutoCommit(false);
                    PreparedStatement preparedStatement = conn.prepareStatement("SELECT value FROM DATA WHERE Object_id = ? AND ATT_ID IN (SELECT ATT_ID FROM ATTRIBUTES WHERE Name = ?)");
                    preparedStatement.setInt(1, id);
                    preparedStatement.setString(2, "Name");
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        user.setName(resultSet.getString("value"));
                    }

                    preparedStatement.setInt(1, id);
                    preparedStatement.setString(2, "Surname");
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        user.setSurname(resultSet.getString("value"));
                    }

                    preparedStatement.setInt(1, id);
                    preparedStatement.setString(2, "Role");
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        user.setRole(resultSet.getString("value"));
                    }
                    person = user;
                } else if (rs.getString("type_name").equals("Customer")) {
                    Customer customer = new Customer();
                    customer.setPersonId(id);
                    conn.setAutoCommit(false);
                    PreparedStatement preparedStatement = conn.prepareStatement("SELECT value FROM DATA WHERE Object_id = ? AND ATT_ID IN (SELECT ATT_ID FROM ATTRIBUTES WHERE Name = ?");
                    preparedStatement.setInt(1, id);
                    preparedStatement.setString(2, "Name");
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        customer.setName(resultSet.getString("value"));
                    }
                    person = customer;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return person;
    }

    /**
     * Method checks for same login and password in Database.
     *
     * @param login user login.
     * @param password user password.
     * @return status of checking for existing cortege <login, password>
     */
    public static Integer checkLoginPassword(String login, String password) {
        Integer personId = null;
        Connection conn = null;
        try {
            conn = OracleDAO.getDBConnection();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT login, password, id FROM LOGINPASSWORD");
            while (rs.next()) {
                String loginDB = rs.getString("login");
                String passwordDB = rs.getString("password");
                if (loginDB.equals(login) && passwordDB.equals(password)) {
                    personId = rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return personId;
    }

    /**
     * Method returns type of object with id from params.
     *
     * @param id id of object.
     * @return type of object.
     */
    public static String getObjectType(Integer id){
        String type = null;
        Connection conn = null;
        try {
            conn = OracleDAO.getDBConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT TYPE_NAME FROM OBJECTS WHERE OBJECT_ID = ?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                type = rs.getString("TYPE_NAME");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return type;
    }

    /**
     * Method returns value of field with name "DateBegin" of object with id from params. If such object does not have, it returns null.
     *
     * @param id id of object.
     * @return DateBegin in Calendar type.
     */
    public static Calendar getDateBeginFromDB(Integer id){
        Connection con = null;
        Calendar cal = Calendar.getInstance();
        try{
            con = OracleDAO.getDBConnection();
            CallableStatement proc = null;
            proc = con.prepareCall("{ call getDate(?,?,?) }");
            proc.setInt(1, id);
            proc.setInt(2, 8);
            proc.registerOutParameter(3, Types.DATE);
            proc.execute();
            Date date = proc.getDate(3);
            cal.setTime(date);
        }catch(Exception e){
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cal;
    }

    /**
     * Method returns value of field with name "DateEnd" of object with id from params. If such object does not have, it returns null.
     *
     * @param id id of object.
     * @return DateEnd in Calendar type.
     */
    public static Calendar getDateEndFromDB(Integer id){
        Connection con = null;
        Calendar cal = Calendar.getInstance();
        try{
            con = OracleDAO.getDBConnection();
            CallableStatement proc = null;
            proc = con.prepareCall("{ call getDate(?,?,?) }");
            proc.setInt(1, id);
            proc.setInt(2, 9);
            proc.registerOutParameter(3, Types.DATE);
            proc.execute();
            Date date = proc.getDate(3);
            cal.setTime(date);
        }catch(Exception e){
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cal;
    }

    /**
     * Method returns value of field with name "CurrentDate" of object with id from params. If such object does not have, it returns null.
     *
     * @param id id of object.
     * @return CurrentDate in Calendar type.
     */
    public static Calendar getCurrentDateFromDB(Integer id){
        Connection con = null;
        Calendar cal = Calendar.getInstance();
        try{
            con = OracleDAO.getDBConnection();
            CallableStatement proc = null;
            proc = con.prepareCall("{ call getDate(?,?,?) }");
            proc.setInt(1, id);
            proc.setInt(2, 13);
            proc.registerOutParameter(3, Types.DATE);
            proc.execute();
            Date date = proc.getDate(3);
            cal.setTime(date);
        }catch(Exception e){
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cal;
    }

    /**
     * Method returns id of object from Link with attName from params and with another object with objectId from Param.
     *
     * @param object1Id first object id in Link.
     * @param attName attribute name.
     * @return second object Id from link.
     */
    public static Integer getObjectIdFromLink(Integer object1Id, String attName){
        Integer object2Id = null;
        Connection conn = null;
        try {
            conn = OracleDAO.getDBConnection();
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT Object2_id FROM LINKS WHERE Object1_id = ? AND Att_id = (SELECT Att_id FROM Attributes WHERE Name = ? AND att_type = 'Link')");
            preparedStatement.setInt(1, object1Id);
            preparedStatement.setString(2, attName);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                object2Id = rs.getInt("Object2_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return object2Id;
    }

    /**
     * Method gets string value of qualification with id from params.
     *
     * @param qualificationId qualification Id.
     * @return string value of qualification.
     */
    public static String readQualification(Integer qualificationId, Connection conn) {
        String qualificationName = null;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT value FROM data WHERE object_id = ?");
            preparedStatement.setInt(1, qualificationId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                qualificationName = rs.getString("value");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return qualificationName;
    }

    /**
     * Method returns list of depended tasks from the main task with id from params.
     *
     * @param id id of main task.
     * @return list of depended tasks.
     */
    public static List<Task> getDependsTasks(Integer id) {
        List<Integer> listId = new ArrayList<>();
        List<Task> dependOnTasks = new ArrayList<>();
        Connection conn = null;
        try {
            conn = OracleDAO.getDBConnection();
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT object1_id FROM LINKS WHERE Object2_id = ? AND ATT_ID IN (SELECT att_id FROM Attributes WHERE name = 'Task depends on task')");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                listId.add(rs.getInt("object1_id"));
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < listId.size(); i++) {
            dependOnTasks.add((Task) dao.read(listId.get(i)));
        }
        return dependOnTasks;
    }

    /**
     * Method gets list of id of childs of main object with id from params.
     *
     * @param objectId main object id.
     * @return list of child ids.
     */
    public static List<Integer> getChildsOfObject(Integer objectId, Connection conn){
        List<Integer> childIdList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT Object_id FROM OBJECTS WHERE Object_id_Parent = ?");
            preparedStatement.setInt(1, objectId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                childIdList.add(rs.getInt("Object_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return childIdList;
    }

    /**
     * Method returns parent object id of object with id from params.
     *
     * @param objectId id of object.
     * @return id pf parent object.
     */
    public static Integer getParentId(Integer objectId){
        Integer parentId = null;
        Connection conn = null;
        try {
            conn = OracleDAO.getDBConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT Object_id_parent FROM OBJECTS WHERE Object_id = ?");
            preparedStatement.setInt(1, objectId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                parentId = rs.getInt("Object_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return parentId;
    }

    /**
     * Methods returns list of id of tasks on which depends on main task with id from params.
     *
     * @param id main task id.
     * @return list of id tasks.
     */
    public static List<Integer> getTasksIdOnWhichDependsOnTask(Integer id) {
        List<Integer> listId = new ArrayList<>();
        Connection conn = null;
        try {
            conn = OracleDAO.getDBConnection();
            conn.setAutoCommit(false);
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT object2_id FROM LINKS WHERE Object1_id = ? AND ATT_ID IN (SELECT att_id FROM Attributes WHERE name = 'Task depends on task')");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                listId.add(rs.getInt("object2_id"));
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listId;
    }

    /**
     * Method returns ids of all doTasks and Requests of user with id from params. If attribute id is null, it finds among all attributes, another among attribute with id from params.
     *
     * @param object2Id user id.
     * @param conn connection to DB.
     * @param attributeId attribute id.
     * @return list of ids.
     * @throws SQLException
     */
    public static List<Integer> getObjects1IdFromLinksUsingObject2Id(Integer object2Id, Integer attributeId,Connection conn) throws SQLException {
        PreparedStatement preparedStatement = null;
        List<Integer> object1IdList = new ArrayList<>();
        if (attributeId != null){
            preparedStatement = conn.prepareStatement("SELECT Object1_id FROM LINKS WHERE Object2_id = ? AND att_id = ?");
            preparedStatement.setInt(2, attributeId);
        }else {
            preparedStatement = conn.prepareStatement("SELECT Object1_id FROM LINKS WHERE Object2_id = ?");
        }
        preparedStatement.setInt(1, object2Id);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            object1IdList.add(rs.getInt("object1_id"));
        }
        return object1IdList;
    }

    /**
     * Method get ids of requests are connected with DoTask with id from params.
     *
     * @param doTaskId doTask id.
     * @param conn connection to database object.
     * @return List of ids.
     * @throws SQLException
     */
    public static List<Integer> getRequestsIdAreConnectedWithDoTask(Integer doTaskId, Connection conn) throws SQLException {
        List<Integer> idRequests = new ArrayList<>();
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT Object1_id FROM LINKS WHERE att_id = 23 AND Object2_id IN (SELECT Object2_id FROM LINKS WHERE Object1_id = ? AND att_id = 21))");
        preparedStatement.setInt(1, doTaskId);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            idRequests.add(rs.getInt("Object1_id"));
        }
        return idRequests;
    }

    /**
     * Method returns info about all user in UserLight form.
     *
     * @return list of users.
     */
    public static List<UserLight> getAllUsers(){
        List<UserLight> listOfUsers = new ArrayList<>();
        Connection conn = null;

        try {
            conn = OracleDAO.getDBConnection();
            List<Integer> usersId = getAllUsersId();
            for (int i = 0; i < usersId.size(); i++) {
                listOfUsers.add(getUserLightInfo(usersId.get(i),conn));
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listOfUsers;
    }

    /**
     * Method returns ids of all users.
     *
     * @return list of ids.
     */
    private static List<Integer> getAllUsersId() {
        List<Integer> usersId = new ArrayList<>();
        Connection conn = null;
        try {
            conn = OracleDAO.getDBConnection();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT object_id FROM Objects WHERE Type_name = 'User' OR Type_name = 'Manager'");
            while (rs.next()) {
                usersId.add(rs.getInt("object_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usersId;
    }

    /**
     * Method returns info about user in UserLight form.
     *
     * @param id user id.
     * @param conn Connection to DB.
     * @return UserLight.
     */
    public static UserLight getUserLightInfo(Integer id, Connection conn) {
        UserLight userLight = new UserLight();
        userLight.personId = id;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT Data.value, att.name FROM Data JOIN Attributes att ON att.att_id = Data.att_id WHERE Data.object_id = ?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                switch (rs.getString("name")) {
                    case "Password":
                        userLight.password = rs.getString("value");
                        break;
                    case "Email":
                        userLight.email = rs.getString("value");
                        break;
                    case "Name":
                        userLight.name = rs.getString("value");
                        break;
                    case "Surname":
                        userLight.surname = rs.getString("value");
                        break;
                    case "Second_name":
                        userLight.secondName = rs.getString("value");
                        break;
                    case "Identity_code":
                        userLight.identityCode = Double.parseDouble(rs.getString("value"));
                        break;
                    case "Role":
                        userLight.role = rs.getString("value");
                        break;
                }
            }
            String qualification = readQualification(getObjectIdFromLink(id, "Qualification"), conn);
            userLight.qualification = qualification;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userLight;
    }

    /**
     * Method returns info about all customers in CustomerLight form.
     *
     * @return list of customers.
     */
    public static List<CustomerLight> getAllCustomers(){
        List<CustomerLight> listOfCustomers = new ArrayList<>();
        Connection conn = null;

        try {
            conn = OracleDAO.getDBConnection();
            List<Integer> customersId = getAllCustomersId();
            for (int i = 0; i < customersId.size(); i++) {
                listOfCustomers.add(getCustomerLightInfo(customersId.get(i),conn));
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listOfCustomers;
    }

    /**
     * Method returns ids of all customers.
     *
     * @return list of ids.
     */
    private static List<Integer>  getAllCustomersId() {
        List<Integer> customersId = new ArrayList<>();
        Connection conn = null;
        try {
            conn = OracleDAO.getDBConnection();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT object_id FROM Objects WHERE Type_name = 'Customer'");
            while (rs.next()) {
                customersId.add(rs.getInt("object_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return customersId;
    }

    /**
     * Method returns info about customer in CustomerLight form.
     *
     * @param id user id.
     * @param conn Connection to DB.
     * @return CustomerLight.
     */
    public static CustomerLight getCustomerLightInfo(Integer id, Connection conn) {
        CustomerLight customer = new CustomerLight();
        customer.personId = id;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT Data.value, att.name FROM Data JOIN Attributes att ON att.att_id = Data.att_id WHERE Data.object_id = ?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                switch (rs.getString("name")) {
                    case "Password":
                        customer.password = rs.getString("value");
                        break;
                    case "Email":
                        customer.email = rs.getString("value");
                        break;
                    case "Name":
                        customer.name = rs.getString("value");
                        break;
                    case "Invoice":
                        customer.invoice = rs.getString("value");
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
}
