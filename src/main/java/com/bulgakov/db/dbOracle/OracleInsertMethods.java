package com.bulgakov.db.dbOracle;


import com.bulgakov.model.*;

import java.sql.*;

import static com.bulgakov.db.dbOracle.OracleUtils.*;

/**
 * @author Bulgakov
 * @since 11.03.2017
 */
public class OracleInsertMethods {

    /**
     * Method insert into database new user.
     *
     * @param user object to insert.
     */
    public static Boolean insertUser(User user) {
        Boolean statusOperation = false;
        Connection con = null;
        try {
            con = OracleDAO.getDBConnection();
            CallableStatement proc = null;
            proc = con.prepareCall("{ call addNewUser(?,?,?,?,?,?,?,?,?,?) }");
            proc.setInt(1, user.getPersonId());
            proc.setString(2, user.getName());
            proc.setString(3, user.getSurname());
            proc.setString(4, user.getSecondName());
            proc.setString(5, user.getEmail());
            proc.setString(6, user.getPassword());
            proc.setString(7, String.valueOf(user.getIdentityCode()));
            proc.setString(8, user.getQualification());
            proc.setString(9, user.getRole());
            proc.registerOutParameter(10, Types.VARCHAR);
            proc.execute();
            statusOperation = stringStatusToBoolean(proc.getString(10));
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
     * Method insert into database new customer.
     *
     * @param customer object to insert.
     */
    public static Boolean insertCustomer(Customer customer) {
        Boolean statusOperation = false;
        Connection con = null;
        try {
            con = OracleDAO.getDBConnection();
            CallableStatement proc = null;
            proc = con.prepareCall("{ call addNewCustomer(?,?,?,?,?,?) }");
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
     * Method insert into database new project.
     *
     * @param project object to insert.
     */
    public static Boolean insertProject(Project project) {
        Boolean statusOperation = false;
        Connection con = null;
        try {
            con = OracleDAO.getDBConnection();
            CallableStatement proc = null;
            proc = con.prepareCall("{ call addNewProject(?,?,?,?,?,?,?,?) }");
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
     * Method insert into database new sprint.
     *
     * @param sprint object to insert.
     */
    public static Boolean insertSprint(Sprint sprint) {
        Boolean statusOperation = false;
        Connection con = null;
        try {
            con = OracleDAO.getDBConnection();
            CallableStatement proc = null;
            proc = con.prepareCall("{ call addNewSprint(?,?,?,?,?,?,?,?) }");
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
     * Method insert new task into database.
     *
     * @param task object to insert.
     */
    public static Boolean insertTask(Task task) {
        Boolean statusOperation = false;
        Connection con = null;
        try {
            con = OracleDAO.getDBConnection();
            CallableStatement proc = null;
            proc = con.prepareCall("{ call addNewTask() }");
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
     * Method insert into database new request.
     *
     * @param request object to insert.
     */
    public static Boolean insertRequest(RequestForIncreasingTime request) {
        Boolean statusOperation = false;
        Connection con = null;
        try {
            con = OracleDAO.getDBConnection();
            CallableStatement proc = null;
            proc = con.prepareCall("{ call addNewRequest(?, ?, ?, ?, ?, ?, ?) }");
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
     * Method insert into database new doTask.
     *
     * @param doTask object to insert.
     */
    public static Boolean insertDoTask(DoTask doTask) {
        Boolean statusOperation = false;
        Connection con = null;
        try {
            con = OracleDAO.getDBConnection();
            CallableStatement proc = null;
            proc = con.prepareCall("{ call addNewDoTask(?, ?, ?, ?, ?, ?) }");
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
}
