package com.bulgakov.db.dbJson;

import com.bulgakov.exceptions.UnknownObjectType;
import com.bulgakov.model.*;
import com.bulgakov.model.LightVersion.*;
import com.google.gson.Gson;

import java.util.List;

import static com.bulgakov.db.dbJson.JsonUtils.getFullObjectVersion;
import static com.bulgakov.db.dbJson.JsonUtils.getRecords;

/**
 * @author Bulgakov
 * @since 26.02.2017
 */
public class JsonReadMethods {

    /**
     * Method read object with such param name and param value in json type from file.
     *
     * @param fileName  file name.
     * @param paramName param name.
     * @param value     value.
     * @return object in json format.
     */
    private static String readExistingObject(String fileName, String paramName, Object value) {
        List<String> objects = getRecords(fileName);
        String objectString = null;
        for (String iter : objects) {
            if (iter.contains("\"" + paramName + "\":" + value)) {
                objectString = iter;
            }
        }
        return objectString;
    }

    /**
     * Method finds customer with ID from params and return it.
     *
     * @param id ID of customer.
     * @return customer object.
     * @throws UnknownObjectType if method finds unknown object in database.
     */
    public static Customer readCustomer(Integer id) throws UnknownObjectType {
        Gson gson = new Gson();
        String customerToRead = readExistingObject("Customers", "personId", id);
        CustomerLight customerLight = gson.fromJson(customerToRead, CustomerLight.class);
        Customer customer = (Customer) getFullObjectVersion(customerLight);
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
        Gson gson = new Gson();
        String userToRead = readExistingObject("Users", "personId", id);
        UserLight userLight = gson.fromJson(userToRead, UserLight.class);
        User user = (User) getFullObjectVersion(userLight);
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
        Gson gson = new Gson();
        String projectToRead = readExistingObject("Projects", "projectId", id);
        ProjectLight projectLight = gson.fromJson(projectToRead, ProjectLight.class);
        Project project = (Project) getFullObjectVersion(projectLight);
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
        Gson gson = new Gson();
        String sprintToRead = readExistingObject("Sprints", "sprintId", id);
        SprintLight sprintLight = gson.fromJson(sprintToRead, SprintLight.class);
        Sprint sprint = (Sprint) getFullObjectVersion(sprintLight);
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
        Gson gson = new Gson();
        String taskToRead = readExistingObject("Tasks", "taskId", id);
        TaskLight taskLight = gson.fromJson(taskToRead, TaskLight.class);
        Task task = (Task) getFullObjectVersion(taskLight);
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
        Gson gson = new Gson();
        String requestToRead = readExistingObject("Requests", "requestId", id);
        RequestForIncreasingTimeLight requestLight = gson.fromJson(requestToRead, RequestForIncreasingTimeLight.class);
        RequestForIncreasingTime request = (RequestForIncreasingTime) getFullObjectVersion(requestLight);
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
        Gson gson = new Gson();
        String doTaskToRead = readExistingObject("DoTasks", "doTaskId", id);
        DoTasksLight doTasksLight = gson.fromJson(doTaskToRead, DoTasksLight.class);
        DoTask doTask = (DoTask) getFullObjectVersion(doTasksLight);
        return doTask;
    }
}
