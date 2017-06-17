package com.bulgakov.db.dbOracle;

import com.bulgakov.model.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.bulgakov.db.dbOracle.OracleUtils.*;

/**
 * @author Bulgakov
 * @since 11.03.2017
 */
public class OracleReadMethods {

    /**
     * Method finds customer with ID from params and return it.
     *
     * @param id ID of customer.
     * @return customer object.
     */
    public static Customer readCustomer(Integer id){
        Customer customer = new Customer();
        customer.setPersonId(id);
        Connection conn = null;
        try {
            conn = OracleDAO.getDBConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT Data.value, att.name FROM Data JOIN Attributes att ON att.att_id = Data.att_id WHERE Data.object_id = ?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                switch (rs.getString("name")) {
                    case "Password":
                        customer.setPassword(rs.getString("value"));
                        break;
                    case "Email":
                        customer.setEmail(rs.getString("value"));
                        break;
                    case "Name":
                        customer.setName(rs.getString("value"));
                        break;
                    case "Invoice":
                        customer.setInvoice(rs.getString("value"));
                        break;
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
        return customer;
    }

    /**
     * Method finds user with ID from params and return it. It also returns boss object.
     *
     * @param id ID of user.
     * @return user object.
     */
    public static User readUser(Integer id){
        User user = new User();
        user.setPersonId(id);
        Connection conn = null;

        try {
            conn = OracleDAO.getDBConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT Data.value, att.name FROM Data JOIN Attributes att ON att.att_id = Data.att_id WHERE Data.object_id = ?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                switch (rs.getString("name")) {
                    case "Password":
                        user.setPassword(rs.getString("value"));
                        break;
                    case "Email":
                        user.setEmail(rs.getString("value"));
                        break;
                    case "Name":
                        user.setName(rs.getString("value"));
                        break;
                    case "Surname":
                        user.setSurname(rs.getString("value"));
                        break;
                    case "Second_name":
                        user.setSecondName(rs.getString("value"));
                        break;
                    case "Identity_code":
                        user.setIdentityCode(Double.parseDouble(rs.getString("value")));
                        break;
                    case "Role":
                        user.setRole(rs.getString("value"));
                        break;
                }
            }
            String qualification = readQualification(getObjectIdFromLink(id, "Qualification"), conn);
            user.setQualification(qualification);
            // get Boss of user
            PreparedStatement preparedStatement2 = conn.prepareStatement("SELECT Object_id_parent FROM Objects WHERE Object_id = ?");
            preparedStatement2.setInt(1, id);
            rs = preparedStatement2.executeQuery();
            while (rs.next()) {
                Integer tmp = rs.getInt("Object_id_parent");
                if (tmp != 0) {
                    User parentUser = readUser(tmp);
                    user.setBoss(parentUser);
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
        return user;
    }

    /**
     * Method finds project with ID from params and return it. It also returns customer of this project.
     *
     * @param id ID of project.
     * @return project object.
     */
    public static Project readProject(Integer id){
        Project project = new Project();
        project.setProjectId(id);
        Connection conn = null;
        try {
            conn = OracleDAO.getDBConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT Data.value, att.name FROM Data JOIN Attributes att ON att.att_id = Data.att_id WHERE Data.object_id = ?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                switch (rs.getString("name")) {
                    case "Name":
                        project.setName(rs.getString("value"));
                        break;
                    case "Date_begin":
                        project.setDateBegin(getDateBeginFromDB(id));
                        break;
                    case "Date_end":
                        project.setDateEnd(getDateEndFromDB(id));
                        break;
                    case "Price":
                        project.setPrice(Double.parseDouble(rs.getString("value")));
                        break;
                    case "Description":
                        project.setDescription(rs.getString("value"));
                        break;
                    case "Project has customer":
                        Customer customer = readCustomer(getObjectIdFromLink(id, rs.getString("name")));
                        project.setCustomer(customer);
                        break;
                }
            }
            if (project.getDateBegin().equals(project.getDateEnd())){
                project.setDateEnd(null);
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
        return project;
    }

    /**
     * Method finds sprint with ID from params and return it. It also returns project of this sprint.
     *
     * @param id ID of sprint.
     * @return sprint object.
     */
    public static Sprint readSprint(Integer id){
        Sprint sprint = new Sprint();
        sprint.setSprintId(id);
        Connection conn = null;
        try {
            conn = OracleDAO.getDBConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT Data.value, att.name FROM Data JOIN Attributes att ON att.att_id = Data.att_id WHERE Data.object_id = ?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                switch (rs.getString("name")) {
                    case "Name":
                        sprint.setName(rs.getString("value"));
                        break;
                    case "Date_begin":
                        sprint.setDateBegin(getDateBeginFromDB(id));
                        break;
                    case "Date_end":
                        sprint.setDateEnd(getDateEndFromDB(id));
                        break;
                    case "Estimate":
                        sprint.setEstimate(Double.parseDouble(rs.getString("value")));
                        break;
                    case "Description":
                        sprint.setDescription(rs.getString("value"));
                        break;
                    case "Sprint has project":
                        Project project = readProject(getObjectIdFromLink(id, rs.getString("name")));
                        sprint.setProject(project);
                        break;
                }
                if (sprint.getDateBegin().equals(sprint.getDateEnd())) {
                    sprint.setDateEnd(null);
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
        return sprint;
    }

    /**
     * Method finds task with ID from params and return it. It also returns sprint, parent task and list depends on tasks of this task.
     *
     * @param id ID of task.
     * @return task object.
     */
    public static Task readTask(Integer id){
        Task task = new Task();
        Connection conn = null;
        try {
            conn = OracleDAO.getDBConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT Data.value, att.name FROM Data JOIN Attributes att ON att.att_id = Data.att_id WHERE Data.object_id = ?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                switch (rs.getString("name")) {
                    case "Qualification":
                        task.setQualification(readQualification(getObjectIdFromLink(id, rs.getString("name")), conn));
                        break;
                    case "Estimate":
                        task.setEstimate(Double.parseDouble(rs.getString("value")));
                        break;
                    case "Status":
                        task.setStatus(rs.getString("value"));
                        break;
                    case "Description":
                        task.setDescription(rs.getString("value"));
                        break;
                    case "Task has sprint":
                        Sprint sprint = readSprint(getObjectIdFromLink(id, rs.getString("name")));
                        task.setSprint(sprint);
                        break;
                    case "Task depends on task":
                        List<Task> dependsOnTasks = new ArrayList<>();
                        dependsOnTasks = getDependsTasks(id);
                        task.setDependsOnTasks(dependsOnTasks);
                        break;
                }
            }
            // get Parent Task
            PreparedStatement preparedStatement2 = conn.prepareStatement("SELECT Object_id_parent FROM Objects WHERE Object_id = ?");
            preparedStatement2.setInt(1, id);
            rs = preparedStatement2.executeQuery();
            while (rs.next()) {
                Integer tmp = rs.getInt("Object_id_parent");
                if (tmp != 0) {
                    Task parentTask = readTask(tmp);
                    parentTask.addToDependsOnTasksList(task);
                    task.setParentTask(parentTask);
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
        return task;
    }

    /**
     * Method finds request with ID from params and return it. It also returns task and user of this request.
     *
     * @param id ID of request for increasing time.
     * @return RequestForIncreasingTim object.
     */
    public static RequestForIncreasingTime readRequest(Integer id) {
        RequestForIncreasingTime requestForIncreasingTime = new RequestForIncreasingTime();
        Connection conn = null;
        try {
            conn = OracleDAO.getDBConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT Data.value, att.name FROM Data JOIN Attributes att ON att.att_id = Data.att_id WHERE Data.object_id = ?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                switch (rs.getString("name")) {
                    case "Current_date":
                        requestForIncreasingTime.setCurrentDate(getCurrentDateFromDB(id));
                        break;
                    case "Hours":
                        requestForIncreasingTime.setHours(Double.parseDouble(rs.getString("value")));
                        break;
                    case "Description":
                        requestForIncreasingTime.setDescription(rs.getString("value"));
                        break;
                    case "Task is in request":
                        Task task = readTask(getObjectIdFromLink(id, rs.getString("name")));
                        requestForIncreasingTime.setTask(task);
                        break;
                    case "User pull request for increasing time":
                        User user = readUser(getObjectIdFromLink(id, rs.getString("name")));
                        requestForIncreasingTime.setUser(user);
                        break;
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
        return requestForIncreasingTime;
    }

    /**
     * Method finds doTask with ID from params and return it. It also returns task and user of this doTask.
     *
     * @param id ID of doTask.
     * @return doTask object.
     */
    public static DoTask readDoTask(Integer id){
        DoTask doTask =  new DoTask();
        doTask.setDoTaskId(id);
        Connection conn = null;
        try {
            conn = OracleDAO.getDBConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT Data.value, att.name FROM Data JOIN Attributes att ON att.att_id = Data.att_id WHERE Data.object_id = ?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                switch (rs.getString("name")) {
                    case "User do task":
                        User user = readUser(getObjectIdFromLink(id, rs.getString("name")));
                        doTask.setUser(user);
                        break;
                    case "Date_begin":
                        doTask.setDateBegin(getDateBeginFromDB(id));
                        break;
                    case "Date_end":
                        doTask.setDateEnd(getDateEndFromDB(id));
                        break;
                    case "Task is made by user":
                        Task task = readTask(getObjectIdFromLink(id, rs.getString("name")));
                        doTask.setTask(task);
                        break;
                }
            }
            if (doTask.getDateBegin().equals(doTask.getDateEnd())) {
                doTask.setDateEnd(null);
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
        return doTask;
    }
}

