package com.bulgakov.db.dbSerializable;

import com.bulgakov.exceptions.UnknownObjectType;
import com.bulgakov.model.LightVersion.*;
import com.bulgakov.model.Task;

import java.util.List;

/**
 * @author Bulgakov
 * @since 04.03.2017
 */
public class SerializableDeleteMethods {
    private static SerializableDAO dao = new SerializableDAO();

    /**
     * Method deletes customer with ID from params from file with records about all customers.
     * It delete short info about this object in file "IdTypes".
     *
     * @param id ID of customer to delete.
     */
    public static void deleteCustomer(Integer id) {
        List<CustomerLight> customers = (List<CustomerLight>) SerializableUtils.getRecords("Customers");
        CustomerLight customerLight = null;
        for (CustomerLight iter : customers) {
            if (iter.personId.equals(id)) {
                customerLight = iter;
            }
        }
        customers.remove(customerLight);
        SerializableUtils.writeInFile(customers, "Customers");
        SerializableUtils.deleteRecordFromIdTypes(id);
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
        List<UserLight> users = (List<UserLight>) SerializableUtils.getRecords("Users");
        UserLight userLight = null;
        for (UserLight iter : users) {
            if (iter.personId.equals(id)) {
                userLight = iter;
            }
        }
        users.remove(userLight);
        // delete all DoTasks of this user
        List<DoTasksLight> doTasks = (List<DoTasksLight>) SerializableUtils.getRecords("DoTasks");
        for (DoTasksLight iter : doTasks) {
            if (iter.userId.equals(id)) {
                dao.delete(iter.doTaskId);
            }
        }
        SerializableUtils.writeInFile(users, "Users");
        SerializableUtils.deleteRecordFromIdTypes(id);
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
        List<ProjectLight> projects = (List<ProjectLight>) SerializableUtils.getRecords("Projects");
        ProjectLight projectLight = null;
        for (ProjectLight iter : projects) {
            if (iter.projectId.equals(id)) {
                projectLight = iter;
            }
        }
        projects.remove(projectLight);
        // delete all sprints of this project
        List<SprintLight> sprints = (List<SprintLight>) SerializableUtils.getRecords("Sprints");
        for (SprintLight iter : sprints) {
            if (iter.projectId.equals(id)) {
                dao.delete(iter.sprintId);
            }
        }
        SerializableUtils.writeInFile(projects, "Projects");
        SerializableUtils.deleteRecordFromIdTypes(id);
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
        List<SprintLight> sprints = (List<SprintLight>) SerializableUtils.getRecords("Sprints");
        SprintLight sprintLight = null;
        for (SprintLight iter : sprints) {
            if (iter.sprintId.equals(id)) {
                sprintLight = iter;
            }
        }
        sprints.remove(sprintLight);
        // delete all tasks are connected with this sprint
        List<TaskLight> tasks = (List<TaskLight>) SerializableUtils.getRecords("Tasks");
        for (TaskLight iter : tasks) {
            if (iter.sprintId.equals(id)) {
                dao.delete(iter.taskId);
            }
        }
        SerializableUtils.writeInFile(sprints, "Sprints");
        SerializableUtils.deleteRecordFromIdTypes(id);
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
        List<TaskLight> tasks = (List<TaskLight>) SerializableUtils.getRecords("Tasks");
        TaskLight taskLight = null;
        for (TaskLight iter : tasks) {
            if (iter.taskId.equals(id)) {
                taskLight = iter;
            }
        }
        // delete all DoTasks with this Task
        List<DoTasksLight> doTasks = (List<DoTasksLight>) SerializableUtils.getRecords("DoTasks");
        for (DoTasksLight iter : doTasks) {
            if (iter.taskId.equals(id)) {
                dao.delete(iter.doTaskId);
            }
        }
        // delete all child of main Task
        for (Integer childId : taskLight.dependsOnTasksId) {
            Task dependsTask = (Task) dao.read(childId);
            if(dependsTask.getParentTask().getTaskId() == taskLight.taskId){
                dao.delete(childId);
            }
        }
        // delete task from dependsOfTask list of parent task
        if (taskLight.parentTaskId != null) {
            TaskLight parentTaskTmp = null;
            Integer idChildTmp = null;
            for (TaskLight iter : tasks) {
                if (iter.taskId.equals(taskLight.parentTaskId)) {
                    parentTaskTmp = iter;
                    for (Integer idChild : iter.dependsOnTasksId) {
                        if (idChild.equals(id)){
                            idChildTmp = idChild;
                            break;
                        }
                    }
                }
            }
            parentTaskTmp.dependsOnTasksId.remove(idChildTmp);
        }
        tasks.remove(taskLight);
        SerializableUtils.writeInFile(tasks, "Tasks");
        SerializableUtils.deleteRecordFromIdTypes(id);
    }

    /**
     * Method deletes request with ID from params from file with records about all requests.
     * It deletes short info about this request in file "IdTypes".
     *
     * @param id ID of request to delete.
     */
    public static void deleteRequest(Integer id) {
        List<RequestForIncreasingTimeLight> requests = (List<RequestForIncreasingTimeLight>) SerializableUtils.getRecords("Requests");
        RequestForIncreasingTimeLight requestForIncreasingTimeLight = null;
        for (RequestForIncreasingTimeLight iter : requests) {
            if (iter.requestId.equals(id)) {
                requestForIncreasingTimeLight = iter;
            }
        }
        requests.remove(requestForIncreasingTimeLight);
        SerializableUtils.writeInFile(requests, "Requests");
        SerializableUtils.deleteRecordFromIdTypes(id);
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
        List<DoTasksLight> doTasks = (List<DoTasksLight>) SerializableUtils.getRecords("DoTasks");
        DoTasksLight doTasksLight = null;
        for (DoTasksLight iter : doTasks) {
            if (iter.doTaskId.equals(id)) {
                doTasksLight = iter;
            }
        }
        // try to find Requests that are associated with the DoTask
        List<RequestForIncreasingTimeLight> requests = (List<RequestForIncreasingTimeLight>) SerializableUtils.getRecords("Requests");
        for (RequestForIncreasingTimeLight iter : requests) {
            if ((iter.taskId.equals(doTasksLight.taskId)) && (iter.userId.equals(doTasksLight.userId))) {
                dao.delete(iter.requestId);
            }
        }
        doTasks.remove(doTasksLight);
        SerializableUtils.writeInFile(doTasks, "DoTasks");
        SerializableUtils.deleteRecordFromIdTypes(id);
    }
}
