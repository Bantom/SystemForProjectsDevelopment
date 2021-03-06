package com.bulgakov.db.dbXml;

import com.bulgakov.exceptions.UnknownObjectType;
import com.bulgakov.model.*;
import com.bulgakov.model.LightVersion.*;

import java.util.List;

/**
 * @author Bulgakov
 * @since 05.03.2017
 */
public class XmlReadMethods {

    /**
     * Method finds customer with ID from params and return it.
     *
     * @param id ID of customer.
     * @return customer object.
     * @throws UnknownObjectType if method finds unknown object in database.
     */
    public static Customer readCustomer(Integer id) throws UnknownObjectType {
        List<CustomerLight> listCustomers = (List<CustomerLight>) XmlUtils.getRecords("Customers");
        CustomerLight customerToRead = null;
        for (CustomerLight iter : listCustomers) {
            if (iter.personId.equals(id)) {
                customerToRead = iter;
            }
        }
        Customer customer = (Customer) XmlUtils.getFullObjectVersion(customerToRead);
        return customer;
    }

    /**
     * Method finds user with ID from params and return it. It also returns boss object.
     *
     * @param id ID of user.
     * @return user object.
     * @throws UnknownObjectType if method finds unknown object in database.
     */
    public static User readUser(Integer id) throws UnknownObjectType {
        List<UserLight> listUsers = (List<UserLight>) XmlUtils.getRecords("Users");
        UserLight userToRead = null;
        for (UserLight iter : listUsers) {
            if (iter.personId.equals(id)) {
                userToRead = iter;
            }
        }
        User user = (User) XmlUtils.getFullObjectVersion(userToRead);
        return user;
    }

    /**
     * Method finds project with ID from params and return it. It also returns customer of this project.
     *
     * @param id ID of project.
     * @return project object.
     * @throws UnknownObjectType if method finds unknown object in database.
     */
    public static Project readProject(Integer id) throws UnknownObjectType {
        List<ProjectLight> listOfProjects = (List<ProjectLight>) XmlUtils.getRecords("Projects");
        ProjectLight projectToRead = null;
        for (ProjectLight iter : listOfProjects) {
            if (iter.projectId.equals(id)) {
                projectToRead = iter;
            }
        }
        Project project = (Project) XmlUtils.getFullObjectVersion(projectToRead);
        return project;
    }

    /**
     * Method finds sprint with ID from params and return it. It also returns project of this sprint.
     *
     * @param id ID of sprint.
     * @return sprint object.
     * @throws UnknownObjectType if method finds unknown object in database.
     */
    public static Sprint readSprint(Integer id) throws UnknownObjectType {
        List<SprintLight> sprints = (List<SprintLight>) XmlUtils.getRecords("Sprints");
        SprintLight sprintToRead = null;
        for (SprintLight iter : sprints) {
            if (iter.sprintId.equals(id)) {
                sprintToRead = iter;
            }
        }
        Sprint sprint = (Sprint) XmlUtils.getFullObjectVersion(sprintToRead);
        return sprint;
    }

    /**
     * Method finds task with ID from params and return it. It also returns sprint, parent task and list depends on tasks of this task.
     *
     * @param id ID of task.
     * @return task object.
     * @throws UnknownObjectType if method finds unknown object in database.
     */
    public static Task readTask(Integer id) throws UnknownObjectType {
        List<TaskLight> tasks = (List<TaskLight>) XmlUtils.getRecords("Tasks");
        TaskLight taskToRead = null;
        for (TaskLight iter : tasks) {
            if (iter.taskId.equals(id)) {
                taskToRead = iter;
            }
        }
        Task task = (Task) XmlUtils.getFullObjectVersion(taskToRead);
        return task;
    }

    /**
     * Method finds request with ID from params and return it. It also returns task and user of this request.
     *
     * @param id ID of request for increasing time.
     * @return RequestForIncreasingTim object.
     * @throws UnknownObjectType if method finds unknown object in database.
     */
    public static RequestForIncreasingTime readRequest(Integer id) throws UnknownObjectType {
        List<RequestForIncreasingTimeLight> requests = (List<RequestForIncreasingTimeLight>) XmlUtils.getRecords("Requests");
        RequestForIncreasingTimeLight requestToRead = null;
        for (RequestForIncreasingTimeLight iter : requests) {
            if (iter.requestId.equals(id)) {
                requestToRead = iter;
            }
        }
        RequestForIncreasingTime request = (RequestForIncreasingTime) XmlUtils.getFullObjectVersion(requestToRead);
        return request;
    }

    /**
     * Method finds doTask with ID from params and return it. It also returns task and user of this doTask.
     *
     * @param id ID of doTask.
     * @return doTask object.
     * @throws UnknownObjectType if method finds unknown object in database.
     */
    public static DoTask readDoTask(Integer id) throws UnknownObjectType {
        List<DoTasksLight> doTasks = (List<DoTasksLight>) XmlUtils.getRecords("DoTasks");
        DoTasksLight doTaskToRead = null;
        for (DoTasksLight iter : doTasks) {
            if (iter.doTaskId.equals(id)) {
                doTaskToRead = iter;
            }
        }
        DoTask doTask = (DoTask) XmlUtils.getFullObjectVersion(doTaskToRead);
        return doTask;
    }
}
