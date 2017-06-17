package com.bulgakov.db.dbCollections;

import com.bulgakov.model.*;

import java.util.List;
import java.util.Map;

import static com.bulgakov.db.dbCollections.CollectionsDB.*;

/**
 * @author Bulgakov
 * @since 26.02.2017
 */
public class CollectionInsertMethods {

    /**
     * Method insert customer into customer's list and short info about customer in objectsType list.
     *
     * @param customer object to insert.
     */
    public static void insertCustomer(Customer customer) {
        if (!CollectionUtils.checkForExistingPerson(customer)) {
            customers.put(customer.getPersonId(), customer);
            objectsType.put(customer.getPersonId(), "Customer");
        }
    }

    /**
     * Method insert user into user's list and short info about user in objectsType list.
     *
     * @param user object to insert.
     */
    public static void insertUser(User user) {
        if (!CollectionUtils.checkForExistingPerson(user)) {
            users.put(user.getPersonId(), user);
            objectsType.put(user.getPersonId(), "User");
        }
    }

    /**
     * Method insert project into project's list and short info about project in objectsType list.
     *
     * @param project object to insert.
     */
    public static void insertProject(Project project) {
        if (!CollectionUtils.checkForExistingProject(project)) {
            projects.put(project.getProjectId(), project);
            objectsType.put(project.getProjectId(), "Project");
        }
    }

    /**
     * Method insert sprint into sprint's list and short info about sprint in objectsType list.
     *
     * @param sprint object to insert.
     */
    public static void insertSprint(Sprint sprint) {
        if (!CollectionUtils.checkForExistingSprint(sprint)) {
            Map<Integer, Sprint> mapSprints = sprints.get(sprint.getProject().getProjectId());
            mapSprints.put(sprint.getSprintId(), sprint);
            sprints.put(sprint.getProject().getProjectId(), mapSprints);
            objectsType.put(sprint.getSprintId(), "Sprint");
        }
    }

    /**
     * Method insert task into task's list and short info about task in objectsType list.
     *
     * @param task object to insert.
     */
    public static void insertTask(Task task) {
        if (!CollectionUtils.checkForExistingTask(task)) {
            if (task.getSprint() == null) {
                Map<Integer, Task> map = backLog.get(task.getSprint().getProject().getProjectId());
                map.put(task.getTaskId(), task);
                backLog.put(task.getSprint().getProject().getProjectId(), map);
            } else {
                List taskList = tasks.get(task.getSprint().getProject().getProjectId()).get(task.getSprint().getSprintId());
                taskList.add(task);
                Map<Integer, List<Task>> map = tasks.get(task.getSprint().getProject().getProjectId());
                map.put(task.getSprint().getSprintId(), taskList);
                tasks.put(task.getSprint().getProject().getProjectId(), map);
            }
            objectsType.put(task.getTaskId(), "Task");
        }
    }

    /**
     * Method insert request into request's list and short info about request in objectsType list.
     *
     * @param request object to insert.
     */
    public static void insertRequestForIncreasingTime(RequestForIncreasingTime request) {
        if (!CollectionUtils.checkForExistingRequestForIncreasingTime(request)) {
            requests.put(request.getRequestId(), request);
            objectsType.put(request.getRequestId(), "RequestForIncreasingTime");
        }
    }

    /**
     * Method insert doTask into doTask's list and short info about doTask in objectsType list.
     *
     * @param doTask object to insert.
     */
    public static void insertDoTask(DoTask doTask) {
        if (!CollectionUtils.checkForExistingDoTask(doTask)) {
            Map<Integer, DoTask> mapOfDoTasks = userDoTasks.get(doTask.getUser().getPersonId());
            mapOfDoTasks.put(doTask.getDoTaskId(), doTask);
            userDoTasks.put(doTask.getUser().getPersonId(), mapOfDoTasks);
            objectsType.put(doTask.getDoTaskId(), "DoTask");
        }
    }
}
