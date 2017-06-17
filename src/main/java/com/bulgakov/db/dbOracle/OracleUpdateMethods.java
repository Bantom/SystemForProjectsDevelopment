package com.bulgakov.db.dbOracle;

import com.bulgakov.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.bulgakov.db.dbOracle.OracleUtils.*;

/**
 * @author Bulgakov
 * @since 11.03.2017
 */
public class OracleUpdateMethods {

    /**
     * Method update Customer object. It checks for coincidence mail from other users and customers.
     * Can't change ID of object.
     * @param obj updated Object.
     */
    public static Boolean updateCustomer(Object obj) {
        Customer customer = (Customer) obj;
        Boolean statusOperation = false;
        Connection con = null;
        try {
            con = OracleDAO.getDBConnection();
            CallableStatement proc = null;
            proc = con.prepareCall("{ call updateCustomer(?,?,?,?,?,?) }");
            proc.setInt(1, customer.getPersonId());
            proc.setString(2, customer.getName());
            proc.setString(3, customer.getEmail());
            proc.setString(4, customer.getPassword());
            proc.setInt(5, Integer.parseInt(customer.getInvoice()));
            proc.registerOutParameter(6, Types.VARCHAR);
            proc.execute();
            statusOperation = stringStatusToBoolean(proc.getString(6));
        } catch (Exception e) {
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return statusOperation;
    }

    /**
     * Method update User object. It checks for coincidence mail from other users and customers.
     * If new User has new Boss, method to do a check for the existence of a new boss in database.
     * Can't change ID of object.
     * @param obj updated User.
     */
    public static Boolean updateUser(Object obj){
        User user = (User) obj;
        Boolean statusOperation = false;
        Connection con = null;
        try {
            con = OracleDAO.getDBConnection();
            CallableStatement proc = null;
            proc = con.prepareCall("{ call updateUser(?,?,?,?,?,?,?,?,?,?,?) }");
            proc.setInt(1, user.getPersonId());
            proc.setString(2, user.getName());
            proc.setString(3, user.getSurname());
            proc.setString(4, user.getSecondName());
            proc.setString(5, user.getEmail());
            proc.setString(6, user.getPassword());
            proc.setString(7, String.valueOf(user.getIdentityCode()));
            proc.setString(8, user.getQualification());
            proc.setString(9, user.getRole());
            proc.setInt(10, user.getBoss().getPersonId());
            proc.registerOutParameter(11, Types.VARCHAR);
            proc.execute();
            statusOperation = stringStatusToBoolean(proc.getString(11));
        } catch (Exception e) {
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return statusOperation;
    }

    /**
     * Method update Project object. If new Project has new Customer, method to do a check for the existence of a new customer in database.
     * Can't change ID of object.
     * @param obj updated Project.
     */
    public static Boolean updateProject(Object obj){
        Project project = (Project) obj;
        Boolean statusOperation = false;
        Connection con = null;
        try {
            con = OracleDAO.getDBConnection();
            CallableStatement proc = null;
            proc = con.prepareCall("{ call updateProject(?,?,?,?,?,?,?,?) }");
            proc.setInt(1, project.getProjectId());
            proc.setString(2, project.getName());
            proc.setDouble(3, project.getPrice());
            proc.setString(4, project.getDescription());
            proc.setDate(5, new Date(project.getDateBegin().getTimeInMillis()));
            if (project.getDateEnd() != null) {
                proc.setDate(6, new Date(project.getDateEnd().getTimeInMillis()));
            } else {
                proc.setDate(6, new Date(project.getDateBegin().getTimeInMillis()));
            }
            proc.setInt(7, project.getCustomer().getPersonId());
            proc.registerOutParameter(8, Types.VARCHAR);
            proc.execute();
            statusOperation = stringStatusToBoolean(proc.getString(8));
        } catch (Exception e) {
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return statusOperation;
    }

    /**
     * Method update Sprint object. If new Sprint has new Project, method to do a check for the existence of a new Project in database.
     * Can't change ID of object.
     * @param obj updated Sprint.
     */
    public static Boolean updateSprint(Object obj){
        Sprint sprint = (Sprint) obj;
        Boolean statusOperation = false;
        Connection con = null;
        try {
            con = OracleDAO.getDBConnection();
            CallableStatement proc = null;
            proc = con.prepareCall("{ call updateSprint(?,?,?,?,?,?,?,?) }");
            proc.setInt(1, sprint.getSprintId());
            proc.setString(2, sprint.getName());
            proc.setDouble(3, sprint.getEstimate());
            proc.setString(4, sprint.getDescription());
            proc.setDate(5, new Date(sprint.getDateBegin().getTimeInMillis()));
            if (sprint.getDateEnd() != null) {
                proc.setDate(6, new Date(sprint.getDateEnd().getTimeInMillis()));
            } else {
                proc.setDate(6, new Date(sprint.getDateBegin().getTimeInMillis()));
            }
            proc.setInt(7, sprint.getProject().getProjectId());
            proc.registerOutParameter(8, Types.VARCHAR);
            proc.execute();
            statusOperation = stringStatusToBoolean(proc.getString(8));
        } catch (Exception e) {
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return statusOperation;
    }

    /**
     * Method update Task. If parent task was changed, this task will be removed from Depends on task list of old parent task and add to list of new parent task.
     * Can't change sprint of task.
     *
     * @param obj updated Task.
     */
    public static Boolean updateTask(Object obj){
        Task task = (Task) obj;
        Boolean statusOperation = false;
        Connection con = null;
        try {
            con = OracleDAO.getDBConnection();
            CallableStatement proc = null;
            proc = con.prepareCall("{ call updateTask(?,?,?,?,?,?,?,?) }");
            proc.setInt(1, task.getTaskId());
            if (task.getParentTask() != null) {
                proc.setInt(2, task.getParentTask().getTaskId());
            } else {
                proc.setInt(2, -1);
            }
            proc.setDouble(3, task.getEstimate());
            proc.setString(4, task.getQualification());
            proc.setString(5, task.getStatus());
            proc.setString(6, task.getDescription());
            proc.setInt(7, task.getSprint().getSprintId());
            proc.registerOutParameter(8, Types.VARCHAR);
            proc.execute();
            statusOperation = stringStatusToBoolean(proc.getString(8));
        } catch (Exception e) {
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return statusOperation;
    }

    /**
     * Method adds new dependency between two tasks. DependedTask depends on MainTask.
     * Method {@link this.checkForChains()} checks for chains between dependecies of tasks in a hierarchical chain of main task.
     *
     * @param mainTaskId Main Task Id.
     * @param dependedTaskId Dependent task Id.
     * @throws SQLException
     */
    public static void addDependencyTask(Integer mainTaskId, Integer dependedTaskId) throws SQLException {
        List<Task> dependsOnMainTaskList = getDependsTasks(mainTaskId);
        for (Task task : dependsOnMainTaskList) {
            if (task.getTaskId().equals(dependedTaskId)) {
                return;
            }
        }
        Boolean noChains = checkForChains(mainTaskId,dependedTaskId);
        if (noChains) {
            Connection conn = null;
            try {
                conn = OracleDAO.getDBConnection();
                conn.setAutoCommit(false);
                PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO LINKS (Object1_id, Object2_id, att_id) VALUES (?,?,19)");
                preparedStatement.setInt(1, dependedTaskId);
                preparedStatement.setInt(2, mainTaskId);
                preparedStatement.executeUpdate();
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
        }
    }

    /**
     * Method removes dependency between two tasks if they are not have relations parent-child.
     *
     * @param mainTaskId Main Task Id.
     * @param dependedTaskId Dependent task Id.
     */
    public static void removeDependencyTask(Integer mainTaskId,Integer dependedTaskId){
        Integer parentDependedTaskId = getParentId(dependedTaskId);
        if (!mainTaskId.equals(parentDependedTaskId)) {
            Connection conn = null;
            try {
                conn = OracleDAO.getDBConnection();
                conn.setAutoCommit(false);
                PreparedStatement preparedStatement = conn.prepareStatement("DELETE LINKS WHERE Object1_id = ? AND Object2_id = ? AND att_id = 19");
                preparedStatement.setInt(1, dependedTaskId);
                preparedStatement.setInt(2, mainTaskId);
                preparedStatement.executeUpdate();
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
        }
    }

    /**
     * Method update Request only if description or quantity of hours were changed.
     * Can't change ID of object.
     * @param obj new Updated Request.
     */
    public static Boolean updateRequest(Object obj){
        RequestForIncreasingTime request = (RequestForIncreasingTime) obj;
        Boolean statusOperation = false;
        Connection con = null;
        try {
            con = OracleDAO.getDBConnection();
            CallableStatement proc = null;
            proc = con.prepareCall("{ call updateRequest(?, ?, ?, ?, ?, ?, ?) }");
            proc.setInt(1, request.getRequestId());
            proc.setDate(2, new Date(request.getCurrentDate().getTimeInMillis()));
            proc.setString(3, request.getDescription());
            proc.setDouble(4, request.getHours());
            proc.setInt(5, request.getTask().getTaskId());
            proc.setInt(6, request.getUser().getPersonId());
            proc.registerOutParameter(7, Types.VARCHAR);
            proc.execute();
            statusOperation = stringStatusToBoolean(proc.getString(7));
        } catch (Exception e) {
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return statusOperation;
    }

    /**
     * Method update DoTask object only if date begin or date end were changed.
     * Can't change ID of object.
     * @param obj new updated DoTask.
     */
    public static Boolean updateDoTask(Object obj){
        DoTask doTask = (DoTask) obj;
        Boolean statusOperation = false;
        Connection con = null;
        try {
            con = OracleDAO.getDBConnection();
            CallableStatement proc = null;
            proc = con.prepareCall("{ call updateDoTask(?, ?, ?, ?, ?, ?) }");
            proc.setInt(1, doTask.getDoTaskId());
            proc.setDate(2, new Date(doTask.getDateBegin().getTimeInMillis()));
            proc.setDate(3, new Date(doTask.getDateEnd().getTimeInMillis()));
            proc.setInt(4, doTask.getTask().getTaskId());
            proc.setInt(5, doTask.getUser().getPersonId());
            proc.registerOutParameter(6, Types.VARCHAR);
            proc.execute();
            statusOperation = stringStatusToBoolean(proc.getString(6));
        } catch (Exception e) {
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return statusOperation;
    }

    /**
     * Method checks for chains between dependecies of tasks in a hierarchical chain of main task.
     *
     * @param mainTaskId Main Task Id.
     * @param dependedTaskId Dependent task Id.
     * @return if no loop in hierarchical chain - True, else - false.
     */
    private static Boolean checkForChains(Integer mainTaskId,Integer dependedTaskId){
        Boolean status = true;
        Integer parentId = mainTaskId;
        while (parentId != null) {
            if (parentId.equals(dependedTaskId)) {
            status = false;
            break;
            }
            List<Integer> dependsOnTasksId = getTasksIdOnWhichDependsOnTask(parentId);
            for (int i = 0; i < dependsOnTasksId.size(); i++) {
                if (dependsOnTasksId.get(i).equals(dependedTaskId)) {
                    status = false;
                    break;
                }
            }
            parentId = getParentId(parentId);
        }
        return status;
    }
}
