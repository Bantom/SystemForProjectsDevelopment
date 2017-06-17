package com.bulgakov.db.dbJson;

import com.bulgakov.exceptions.UnknownObjectType;
import com.bulgakov.model.LightVersion.*;
import com.google.gson.Gson;
import com.bulgakov.model.Task;

import java.util.List;

import static com.bulgakov.db.dbJson.JsonUtils.deleteRecordFromIdTypes;
import static com.bulgakov.db.dbJson.JsonUtils.getRecords;
import static com.bulgakov.db.dbJson.JsonUtils.writeListOfRecords;

/**
 * @author Bulgakov
 * @since 26.02.2017
 */
public class JsonDeleteMethods {
    private static JsonDAO jsonDAO = new JsonDAO();

    /**
     * Method deletes customer with ID from params from file with records about all customers.
     * It delete short info about this object in file "IdTypes".
     *
     * @param id ID of customer to delete.
     */
    public static void deleteCustomer(Integer id) {
        Gson gson = new Gson();
        List<String> customers = getRecords("Customers");
        CustomerLight customerLight = null;
        String tmp = null;
        for (String iter : customers) {
            if (iter.contains("\"personId\":" + id)) {
                customerLight = gson.fromJson(iter, CustomerLight.class);
                tmp = iter;
            }
        }
        customers.remove(tmp);
        writeListOfRecords(customers, "Customers");
        deleteRecordFromIdTypes(customerLight.personId);
    }

    /**
     * Method deletes user with ID from params from file with records about all user.
     * It deletes all doTasks of this user from file with records about all doTasks.
     * It deletes short info about all of these objects in file "IdTypes".
     *
     * @param id ID of user to delete.
     * @throws UnknownObjectType if method finds unknown object in database.
     */
    public static void deleteUser(Integer id) throws UnknownObjectType {
        Gson gson = new Gson();
        List<String> users = getRecords("Users");
        String userToRead = null;
        for (String iter : users) {
            if (iter.contains("\"personId\":" + id)) {
                userToRead = iter;
            }
        }
        UserLight userLight = gson.fromJson(userToRead, UserLight.class);
        users.remove(userToRead);
        // delete all DoTasks of this user
        List<String> doTasks = getRecords("DoTasks");
        for (String iter : doTasks) {
            if (iter.contains("\"userId\":" + id)) {
                DoTasksLight doTasksLight = gson.fromJson(iter, DoTasksLight.class);
                jsonDAO.delete(doTasksLight.doTaskId);
            }
        }
        writeListOfRecords(users, "Users");
        deleteRecordFromIdTypes(id);
    }

    /**
     * Method deletes project with ID from params from file with records about all projects.
     * It deletes all sprints, tasks, child of these tasks and doTasks of this project from files with records about them.
     * It deletes short info about all of these objects in file "IdTypes".
     *
     * @param id ID of project to delete.
     * @throws UnknownObjectType if method finds unknown object in database.
     */
    public static void deleteProject(Integer id) throws UnknownObjectType {
        Gson gson = new Gson();
        List<String>projects = getRecords("Projects");
        String projectToRead = null;
        for (String iter : projects) {
            if (iter.contains("\"projectId\":" + id)) {
                projectToRead = iter;
            }
        }
        projects.remove(projectToRead);
        // delete all sprints of this project
        List<String> sprints = getRecords("Sprints");
        for (String iter : sprints) {
            if (iter.contains("\"projectId\":" + id)) {
                SprintLight sprintLight = gson.fromJson(iter, SprintLight.class);
                jsonDAO.delete(sprintLight.sprintId);
            }
        }
        writeListOfRecords(projects, "Projects");
        deleteRecordFromIdTypes(id);
    }

    /**
     * Method deletes sprint with ID from params from file with records about all sprints.
     * It deletes all tasks, child of these tasks  and doTasks of this sprint from files with records about them.
     * It deletes short info about all of these objects in file "IdTypes".
     *
     * @param id ID of sprint to delete.
     * @throws UnknownObjectType if method finds unknown object in database.
     */
    public static void deleteSprint(Integer id) throws UnknownObjectType {
        Gson gson = new Gson();
        List<String> sprints = getRecords("Sprints");
        String sprintToRead = null;
        for (String iter : sprints) {
            if (iter.contains("\"sprintId\":" + id)) {
                sprintToRead = iter;
            }
        }
        sprints.remove(sprintToRead);
        // delete all tasks are connected with this sprint
        List<String> tasks = getRecords("Tasks");
        for (String iter : tasks) {
            if (iter.contains("\"sprintId\":" + id)) {
                TaskLight taskLight = gson.fromJson(iter, TaskLight.class);
                jsonDAO.delete(taskLight.taskId);
            }
        }
        writeListOfRecords(sprints, "Sprints");
        deleteRecordFromIdTypes(id);
    }

    /**
     * Method deletes task with ID from params from file with records about all tasks.
     * It deletes all doTasks and child of this task from files with records about them.
     * It deletes short info about all of these objects in file "IdTypes".
     *
     * @param id ID of task to delete.
     * @throws UnknownObjectType if method finds unknown object in database.
     */
    public static void deleteTask(Integer id) throws UnknownObjectType {
        Gson gson = new Gson();
        List<String> tasks = getRecords("Tasks");
        String taskToRead = null;
        for (String iter : tasks) {
            if (iter.contains("\"taskId\":" + id)) {
                taskToRead = iter;
            }
        }
        TaskLight taskLight = gson.fromJson(taskToRead, TaskLight.class);
        tasks.remove(taskToRead);
        // delete all DoTasks with this Task
        List<String> doTasks = getRecords("DoTasks");
        for (String iter : doTasks) {
            if (iter.contains("\"taskId\":" + id)) {
                DoTasksLight doTasksLight = gson.fromJson(iter, DoTasksLight.class);
                jsonDAO.delete(doTasksLight.doTaskId);
            }
        }
        // delete all child of main Task
        for (Integer childId : taskLight.dependsOnTasksId) {
            Task dependsTask = (Task) jsonDAO.read(childId);
            if(dependsTask.getParentTask().getTaskId() == taskLight.taskId){
                jsonDAO.delete(childId);
            }
        }
        // delete task from dependsOfTask list of parent task
        if (taskLight.parentTaskId != null) {
            String tmpTask = null;
            TaskLight taskLightTmp = null;
            for (String task : tasks) {
                if (task.contains("\"taskId\":" + taskLight.parentTaskId)) {
                    tmpTask = task;
                    taskLightTmp = gson.fromJson(task, TaskLight.class);
                    Integer idChildTmp = null;
                    for (Integer idChild : taskLightTmp.dependsOnTasksId) {
                        if (idChild.equals(id)) {
                            idChildTmp = idChild;
                        }
                    }
                    taskLightTmp.dependsOnTasksId.remove(idChildTmp);
                }
            }
            tasks.remove(tmpTask);
            String jsonString = gson.toJson(taskLightTmp);
            tasks.add(jsonString);
        }
        writeListOfRecords(tasks, "Tasks");
        deleteRecordFromIdTypes(id);
    }

    /**
     * Method deletes request with ID from params from file with records about all requests.
     * It deletes short info about this request in file "IdTypes".
     *
     * @param id ID of request to delete.
     */
    public static void deleteRequest(Integer id) {
        List<String> requests = getRecords("Requests");
        String tmp = null;
        for (String iter : requests) {
            if (iter.contains("\"requestId\":" + id)) {
                tmp = iter;
            }
        }
        requests.remove(tmp);
        writeListOfRecords(requests, "Requests");
        deleteRecordFromIdTypes(id);
    }

    /**
     * Method deletes doTask with ID from params from file with records about all doTasks.
     * It deletes all requests this doTask from files with records about them.
     * It deletes short info about all of these objects in file "IdTypes".
     *
     * @param id ID of doTask to delete.
     * @throws UnknownObjectType if method finds unknown object in database.
     */
    public static void deleteDoTask(Integer id) throws UnknownObjectType {
        Gson gson = new Gson();
        List<String> doTasks = getRecords("DoTasks");
        String doTaskToRead = null;
        for (String iter : doTasks) {
            if (iter.contains("\"doTaskId\":" + id)) {
                doTaskToRead = iter;
            }
        }
        DoTasksLight doTasksLight = gson.fromJson(doTaskToRead, DoTasksLight.class);
        doTasks.remove(doTaskToRead);
        // try to find Requests that are associated with the DoTask
        List<String> requests = getRecords("Requests");
        RequestForIncreasingTimeLight requestLightTmp = null;
        for (String iter : requests) {
            if ((iter.contains("\"taskId\":" + doTasksLight.taskId)) && (iter.contains("\"userId\":" + doTasksLight.userId))) {
                requestLightTmp = gson.fromJson(iter, RequestForIncreasingTimeLight.class);
                jsonDAO.delete(requestLightTmp.requestId);
            }
        }
        writeListOfRecords(doTasks, "DoTasks");
        deleteRecordFromIdTypes(id);
    }
}
