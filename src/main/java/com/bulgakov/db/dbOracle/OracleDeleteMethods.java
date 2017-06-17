package com.bulgakov.db.dbOracle;

import java.sql.*;
import java.util.List;

import static com.bulgakov.db.dbOracle.OracleUtils.getChildsOfObject;
import static com.bulgakov.db.dbOracle.OracleUtils.getObjects1IdFromLinksUsingObject2Id;
import static com.bulgakov.db.dbOracle.OracleUtils.getRequestsIdAreConnectedWithDoTask;

/**
 * @author Bulgakov
 * @since 11.03.2017
 */
public class OracleDeleteMethods {

    /**
     * Method deletes customer with ID from params from database.
     *
     * @param id ID of customer to delete.
     */
    public static void deleteCustomer(Integer id) {
        Connection conn = null;
        try {
            conn = OracleDAO.getDBConnection();
            conn.setAutoCommit(false);

            PreparedStatement preparedStatement2 = conn.prepareStatement( "DELETE DATA WHERE Object_id = ?");
            preparedStatement2.setInt(1, id);
            preparedStatement2.execute();

            PreparedStatement preparedStatement1 = conn.prepareStatement( "DELETE OBJECTS WHERE Object_id = ?");
            preparedStatement1.setInt(1, id);
            preparedStatement1.execute();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method deletes user with ID from params from database.
     * It deletes all doTasks and Requests of this user from database.
     *
     * @param id ID of user to delete.
     */
    public static void deleteUser(Integer id){
        Connection conn = null;
        try {
            conn = OracleDAO.getDBConnection();
            conn.setAutoCommit(false);

            // delete doTasks and Requests of this User
            List<Integer> idDoTasks = getObjects1IdFromLinksUsingObject2Id(id,null, conn);
            for (int i = 0; i < idDoTasks.size(); i++) {
                deleteDoTask(idDoTasks.get(i));
            }

            PreparedStatement preparedStatement2 = conn.prepareStatement( "DELETE DATA WHERE Object_id = ?");
            preparedStatement2.setInt(1, id);
            preparedStatement2.execute();

            PreparedStatement preparedStatement3 = conn.prepareStatement( "DELETE LINKS WHERE Object1_id = ?");
            preparedStatement3.setInt(1, id);
            preparedStatement3.execute();

            PreparedStatement preparedStatement1 = conn.prepareStatement( "DELETE OBJECTS WHERE Object_id = ?");
            preparedStatement1.setInt(1, id);
            preparedStatement1.execute();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method deletes project with ID from params from database.
     * It deletes all sprints, tasks, child of these tasks and doTasks of this project from database.
     *
     * @param id ID of project to delete.
     */
    public static void deleteProject(Integer id){
        Connection conn = null;
        try {
            conn = OracleDAO.getDBConnection();
            conn.setAutoCommit(false);

            // delete all sprints of this project
            List<Integer> idSprints = getObjects1IdFromLinksUsingObject2Id(id,null, conn);
            for (int i = 0; i < idSprints.size(); i++) {
                deleteSprint(idSprints.get(i));
            }

            PreparedStatement preparedStatement2 = conn.prepareStatement( "DELETE DATA WHERE Object_id = ?");
            preparedStatement2.setInt(1, id);
            preparedStatement2.execute();

            PreparedStatement preparedStatement3 = conn.prepareStatement( "DELETE LINKS WHERE Object1_id = ?");
            preparedStatement3.setInt(1, id);
            preparedStatement3.execute();

            PreparedStatement preparedStatement1 = conn.prepareStatement( "DELETE OBJECTS WHERE Object_id = ?");
            preparedStatement1.setInt(1, id);
            preparedStatement1.execute();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method deletes sprint with ID from params from dtabase.
     * It deletes all tasks, child of these tasks  and doTasks of this sprint from database.
     *
     * @param id ID of sprint to delete.
     */
    public static void deleteSprint(Integer id){
        Connection conn = null;
        try {
            conn = OracleDAO.getDBConnection();
            conn.setAutoCommit(false);

            // delete all tasks of this sprint
            List<Integer> idTasks = getObjects1IdFromLinksUsingObject2Id(id,null, conn);
            for (int i = 0; i < idTasks.size(); i++) {
                deleteTask(idTasks.get(i));
            }

            PreparedStatement preparedStatement2 = conn.prepareStatement( "DELETE DATA WHERE Object_id = ?");
            preparedStatement2.setInt(1, id);
            preparedStatement2.execute();

            PreparedStatement preparedStatement3 = conn.prepareStatement( "DELETE LINKS WHERE Object1_id = ?");
            preparedStatement3.setInt(1, id);
            preparedStatement3.execute();

            PreparedStatement preparedStatement1 = conn.prepareStatement( "DELETE OBJECTS WHERE Object_id = ?");
            preparedStatement1.setInt(1, id);
            preparedStatement1.execute();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method deletes task with ID from params from database.
     * It deletes all doTasks and child of this task from database.
     *
     * @param id ID of task to delete.
     */
    public static void deleteTask(Integer id){
        Connection conn = null;
        try {
            conn = OracleDAO.getDBConnection();
            conn.setAutoCommit(false);

            //delete all childs of Task
            List<Integer> idChild = getChildsOfObject(id, conn);
            for (int i = 0; i < idChild.size(); i++) {
                deleteTask(idChild.get(i));
            }

            // delete all doTasks of Task
            List<Integer> idDoTasks = getObjects1IdFromLinksUsingObject2Id(id, 21, conn);
            for (int i = 0; i < idDoTasks.size(); i++) {
                deleteDoTask(idDoTasks.get(i));
            }

            PreparedStatement preparedStatement2 = conn.prepareStatement( "DELETE DATA WHERE Object_id = ?");
            preparedStatement2.setInt(1, id);
            preparedStatement2.execute();

            PreparedStatement preparedStatement3 = conn.prepareStatement( "DELETE LINKS WHERE Object1_id = ? OR Object2_id = ?");
            preparedStatement3.setInt(1, id);
            preparedStatement3.setInt(2, id);
            preparedStatement3.execute();

            PreparedStatement preparedStatement1 = conn.prepareStatement( "DELETE OBJECTS WHERE Object_id = ?");
            preparedStatement1.setInt(1, id);
            preparedStatement1.execute();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method deletes request with ID from params from database.
     *
     * @param id ID of request to delete.
     */
    public static void deleteRequest(Integer id) {
        Connection conn = null;
        try {
            conn = OracleDAO.getDBConnection();
            conn.setAutoCommit(false);

            PreparedStatement preparedStatement2 = conn.prepareStatement( "DELETE DATA WHERE Object_id = ?");
            preparedStatement2.setInt(1, id);
            preparedStatement2.execute();

            PreparedStatement preparedStatement3 = conn.prepareStatement( "DELETE LINKS WHERE Object1_id = ?");
            preparedStatement3.setInt(1, id);
            preparedStatement3.execute();

            PreparedStatement preparedStatement1 = conn.prepareStatement( "DELETE OBJECTS WHERE Object_id = ?");
            preparedStatement1.setInt(1, id);
            preparedStatement1.execute();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method deletes doTask with ID from params from database with records about all doTasks.
     * It deletes all requests this doTask from database.
     *
     * @param id ID of doTask to delete.
     */
    public static void deleteDoTask(Integer id){
        Connection conn = null;
        try {
            conn = OracleDAO.getDBConnection();
            conn.setAutoCommit(false);

            //delete all requests are connected with this DoTask
            List<Integer> idRequests = getRequestsIdAreConnectedWithDoTask(id, conn);
            for (int i = 0; i < idRequests.size(); i++) {
                deleteRequest(idRequests.get(i));
            }

            PreparedStatement preparedStatement2 = conn.prepareStatement( "DELETE DATA WHERE Object_id = ?");
            preparedStatement2.setInt(1, id);
            preparedStatement2.execute();

            PreparedStatement preparedStatement3 = conn.prepareStatement( "DELETE LINKS WHERE Object1_id = ?");
            preparedStatement3.setInt(1, id);
            preparedStatement3.execute();

            PreparedStatement preparedStatement1 = conn.prepareStatement( "DELETE OBJECTS WHERE Object_id = ?");
            preparedStatement1.setInt(1, id);
            preparedStatement1.execute();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
