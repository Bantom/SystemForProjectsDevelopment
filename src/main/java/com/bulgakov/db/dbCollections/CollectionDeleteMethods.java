package com.bulgakov.db.dbCollections;

import com.bulgakov.exceptions.UnknownObjectType;
import com.bulgakov.model.DoTask;
import com.bulgakov.model.RequestForIncreasingTime;
import com.bulgakov.model.Sprint;
import com.bulgakov.model.Task;

import java.util.List;
import java.util.Map;


/**
 * @author Bulgakov
 * @since 26.02.2017
 */
public class CollectionDeleteMethods {
    private static CollectionDAO dao = new CollectionDAO();

    /**
     * Method deletes records from objectsType list with id from params and all object are related with it.
     *
     * @param id ID of object to delete.
     * @param type Type of object to delete.
     * @throws UnknownObjectType if unknown object will be with id from params.
     */
    public static void deleteObjectFromObjectsTypeMap(Integer id, String type) throws UnknownObjectType {
        switch (type) {
            case "Customer":
                CollectionsDB.objectsType.remove(id);
                break;
            case "User":
                CollectionsDB.objectsType.remove(id);
                break;
            case "Project":
                CollectionsDB.objectsType.remove(id);
                Map<Integer, Sprint> sprintsToDelete = CollectionsDB.sprints.get(id);
                // tasks from backlog of this project
                Map<Integer, Task> tasksToDelete = CollectionsDB.backLog.get(id);
                // add tasks from sprints of this project to delete list
                for (Map.Entry<Integer, Sprint> idSprint : sprintsToDelete.entrySet()) {
                    List<Task> tasksToDeleteFromSprints = CollectionsDB.tasks.get(id).get(idSprint.getKey());
                    for (Task taskTmp : tasksToDeleteFromSprints) {
                        tasksToDelete.put(taskTmp.getTaskId(), taskTmp);
                    }
                }
                // delete sprints from ObjectsType
                for (Map.Entry<Integer, Sprint> idSprint : sprintsToDelete.entrySet()) {
                    CollectionsDB.objectsType.remove(idSprint.getKey());
                }
                // delete tasks from ObjectsType
                for (Map.Entry<Integer, Task> idTask : tasksToDelete.entrySet()) {
                    CollectionsDB.objectsType.remove(idTask.getKey());
                }
                break;
            case "Sprint":
                Integer projectId = null;
                for (Map.Entry<Integer, Map<Integer, Sprint>> projectSprints : CollectionsDB.sprints.entrySet()) {
                    if (projectSprints.getValue().containsKey(id)) {
                        CollectionsDB.objectsType.remove(id);
                        projectId = projectSprints.getKey();
                    }
                }
                List<Task> sprintTasks = CollectionsDB.tasks.get(projectId).get(id);
                for (Task taskTmp : sprintTasks) {
                    CollectionsDB.objectsType.remove(taskTmp.getTaskId());
                }
                break;
            case "Task":
                CollectionsDB.objectsType.remove(id);
                break;
            case "RequestForIncreasingTime":
                CollectionsDB.objectsType.remove(id);
                break;
            case "DoTask":
                CollectionsDB.objectsType.remove(id);
                break;
        }
    }

    /**
     * Method deletes customer with ID from params from customers list.
     * It delete short info about this object in objectType list.
     *
     * @param id ID of customer to delete.
     */
    public static void deleteCustomer(Integer id) throws UnknownObjectType {
        String objType = CollectionsDB.objectsType.get(id);
        deleteObjectFromObjectsTypeMap(id, objType);
        CollectionsDB.customers.remove(id);
    }

    /**
     * Method deletes user with ID from params from users list.
     * It deletes all doTasks of this user from userDoTasks list.
     * It deletes short info about all of these objects in objectType list.
     *
     * @param id ID of user to delete.
     * @throws UnknownObjectType if method finds unknown object in database.
     */
    public static void deleteUser(Integer id) throws UnknownObjectType {
        String objType = CollectionsDB.objectsType.get(id);
        deleteObjectFromObjectsTypeMap(id, objType);
        CollectionsDB.users.remove(id);
        // delete all DoTasks of this user
        for (Map.Entry<Integer, DoTask> IdDoTask : CollectionsDB.userDoTasks.get(id).entrySet()) {
            dao.delete(IdDoTask.getKey());
        }
    }

    /**
     * Method deletes project with ID from params from projects list.
     * It deletes all sprints, tasks, child of these tasks and doTasks of this project from sprints, backLog, tasks and userDoTasks lists.
     * It deletes short info about all of these objects in objectsType list.
     *
     * @param id ID of project to delete.
     * @throws UnknownObjectType if method finds unknown object in database.
     */
    public static void deleteProject(Integer id) throws UnknownObjectType {
        String objType = CollectionsDB.objectsType.get(id);
        deleteObjectFromObjectsTypeMap(id, objType);
        CollectionsDB.projects.remove(id);
        Map<Integer, Sprint> sprintsToDelete = CollectionsDB.sprints.get(id);
        for (Map.Entry<Integer, Sprint> sprint : sprintsToDelete.entrySet()) {
            dao.delete(sprint.getKey());
        }
    }

    /**
     * Method deletes sprint with ID from params from sprints list.
     * It deletes all tasks, child of these tasks and doTasks of this sprint from backLog, tasks and userDoTasks lists.
     * It deletes short info about all of these objects in objectsType list.
     *
     * @param id ID of sprint to delete.
     * @throws UnknownObjectType if method finds unknown object in database.
     */
    public static void deleteSprint(Integer id) throws UnknownObjectType {
        String objType = CollectionsDB.objectsType.get(id);
        deleteObjectFromObjectsTypeMap(id, objType);
        Integer projectId = null;
        for (Map.Entry<Integer, Map<Integer, Sprint>> projectSprints : CollectionsDB.sprints.entrySet()) {
            if (projectSprints.getValue().containsKey(id)) {
                projectId = projectSprints.getKey();
                projectSprints.getValue().remove(id);
            }
        }
        // delete all tasks of sprint
        List<Task> tasksToDelete = CollectionsDB.tasks.get(projectId).get(id);
        for (Task task : tasksToDelete) {
            dao.delete(task.getTaskId());
        }
    }

    /**
     * Method deletes task with ID from params from tasks or backLog lists.
     * It deletes all doTasks and child of this task from userDoTasks, tasks and backLog lists.
     * It deletes short info about all of these objects in objectsType list.
     *
     * @param id ID of task to delete.
     * @throws UnknownObjectType if method finds unknown object in database.
     */
    public static void deleteTask(Integer id) throws UnknownObjectType {
        String objType = CollectionsDB.objectsType.get(id);
        // remove all child of this task
        Task backUpTask = (Task) dao.read(id);
        List<Task> childOfTask = backUpTask.getDependsOnTasks();
        for (Task child : childOfTask) {
            if (child.getParentTask() != null) {
                if (child.getParentTask().getTaskId() != id){
                    childOfTask.remove(child);
                }
            }else {
                childOfTask.remove(child);
            }
        }
        for (Task child : childOfTask) {
            dao.delete(child.getTaskId());
        }
        // delete task with id from params
        deleteObjectFromObjectsTypeMap(id, objType);
        for (Map.Entry<Integer, Map<Integer, Task>> entry : CollectionsDB.backLog.entrySet()) {
            Map<Integer, Task> mapTmp = entry.getValue();
            if (mapTmp.containsKey(id)) {
                mapTmp.remove(id);
                CollectionsDB.backLog.put(entry.getKey(), mapTmp);
                break;
            }
        }
        for (Map.Entry<Integer, Map<Integer, List<Task>>> sprintTaskList : CollectionsDB.tasks.entrySet()) {
            for (Map.Entry<Integer, List<Task>> taskList : sprintTaskList.getValue().entrySet()) {
                for (Task taskIterator : taskList.getValue()) {
                    if (taskIterator.getTaskId() == id) {
                        taskList.getValue().remove(taskIterator);
                    }
                }
            }
        }
        // remove all DoTasks which Task was deleted
        for (Map.Entry<Integer, Map<Integer, DoTask>> entry : CollectionsDB.userDoTasks.entrySet()) {
            Map<Integer, DoTask> mapTmp = entry.getValue();
            for (Map.Entry<Integer, DoTask> doTaskEntry : mapTmp.entrySet()) {
                if (doTaskEntry.getValue().getTask().getTaskId() == id) {
                    dao.delete(doTaskEntry.getKey());
                }
            }
        }
    }

    /**
     * Method deletes request with ID from params from requests list.
     * It deletes short info about this request in objectsType list.
     *
     * @param id ID of request to delete.
     */
    public static void deleteRequest(Integer id) throws UnknownObjectType {
        String objType = CollectionsDB.objectsType.get(id);
        deleteObjectFromObjectsTypeMap(id, objType);
        CollectionsDB.requests.remove(id);
    }

    /**
     * Method deletes doTask with ID from params from userDoTasks list.
     * It deletes all requests this doTask from Request list.
     * It deletes short info about all of these objects in objectsType list.
     *
     * @param id ID of doTask to delete.
     * @throws UnknownObjectType if method finds unknown object in database.
     */
    public static void deleteDoTask(Integer id) throws UnknownObjectType {
        String objType = CollectionsDB.objectsType.get(id);
        deleteObjectFromObjectsTypeMap(id, objType);
        DoTask doTaskTmp = null;
        for (Map.Entry<Integer, Map<Integer, DoTask>> entry : CollectionsDB.userDoTasks.entrySet()) {
            Map<Integer, DoTask> mapTmp = entry.getValue();
            if (mapTmp.containsKey(id)) {
                doTaskTmp = mapTmp.get(id);
                mapTmp.remove(id);
                CollectionsDB.userDoTasks.put(entry.getKey(), mapTmp);
                break;
            }
        }
        for (Map.Entry<Integer, RequestForIncreasingTime> request : CollectionsDB.requests.entrySet()) {
            if (request.getValue().getTask().getTaskId() == doTaskTmp.getTask().getTaskId() && request.getValue().getUser().getPersonId() == doTaskTmp.getUser().getPersonId()) {
                dao.delete(request.getKey());
            }
        }
    }
}
