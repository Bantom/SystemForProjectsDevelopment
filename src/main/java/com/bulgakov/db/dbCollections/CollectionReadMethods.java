package com.bulgakov.db.dbCollections;

import com.bulgakov.model.*;

import java.util.List;
import java.util.Map;


/**
 * @author Bulgakov
 * @since 26.02.2017
 */
public class CollectionReadMethods {

    /**
     * Method finds customer with ID from params and return it.
     *
     * @param id ID of customer.
     * @return customer object.
     */
    public static Customer readCustomer(Integer id) {
        return CollectionsDB.customers.get(id);
    }

    /**
     * Method finds user with ID from params and return it. It also returns boss object.
     *
     * @param id ID of user.
     * @return user object.
     */
    public static User readUser(Integer id) {
        return CollectionsDB.users.get(id);
    }

    /**
     * Method finds project with ID from params and return it. It also returns customer of this project.
     *
     * @param id ID of project.
     * @return project object.
     */
    public static Project readProject(Integer id) {
        return CollectionsDB.projects.get(id);
    }

    /**
     * Method finds sprint with ID from params and return it. It also returns project of this sprint.
     *
     * @param id ID of sprint.
     * @return sprint object.
     */
    public static Sprint readSprint(Integer id) {
        for (Map.Entry<Integer, Map<Integer, Sprint>> entry : CollectionsDB.sprints.entrySet()) {
            Map<Integer, Sprint> mapTmp = entry.getValue();
            if (mapTmp.containsKey(id)) {
                return mapTmp.get(id);
            }
        }
        return null;
    }

    /**
     * Method finds task with ID from params and return it. It also returns sprint, parent task and list depends on tasks of this task.
     *
     * @param id ID of task.
     * @return task object.
     */
    public static Task readTask(Integer id) {
        for (Map.Entry<Integer, Map<Integer, Task>> entry : CollectionsDB.backLog.entrySet()) {
            Map<Integer, Task> mapTmp = entry.getValue();
            if (mapTmp.containsKey(id)) {
                return mapTmp.get(id);
            }
        }
        for (Map.Entry<Integer, Map<Integer, List<Task>>> entry : CollectionsDB.tasks.entrySet()) {
            Map<Integer, List<Task>> mapTmp = entry.getValue();
            for (Map.Entry<Integer, List<Task>> entryTmp : mapTmp.entrySet()) {
                List<Task> listTmp = entryTmp.getValue();
                for (Task taskIterator : listTmp) {
                    if (taskIterator.getTaskId() == id) {
                        return taskIterator;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Method finds request with ID from params and return it. It also returns task and user of this request.
     *
     * @param id ID of request for increasing time.
     * @return RequestForIncreasingTim object.
     */
    public static RequestForIncreasingTime readRequest(Integer id) {
        return CollectionsDB.requests.get(id);
    }

    /**
     * Method finds doTask with ID from params and return it. It also returns task and user of this doTask.
     *
     * @param id ID of doTask.
     * @return doTask object.
     */
    public static DoTask readDoTask(Integer id) {
        for (Map.Entry<Integer, Map<Integer, DoTask>> entry : CollectionsDB.userDoTasks.entrySet()) {
            Map<Integer, DoTask> mapTmp = entry.getValue();
            if (mapTmp.containsKey(id)) {
                return mapTmp.get(id);
            }
        }
        return null;
    }
}
