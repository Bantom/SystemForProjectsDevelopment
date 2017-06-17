package com.bulgakov.db.dbJson;

import com.bulgakov.exceptions.UnknownObjectType;
import com.bulgakov.model.*;
import com.bulgakov.model.LightVersion.*;

import static com.bulgakov.db.dbJson.JsonUtils.*;


/**
 * @author Bulgakov
 * @since 26.02.2017
 */
public class JsonInsertMethods {

    /**
     * Method insert into file new customer in Json format.
     *
     * @param customer object to insert.
     * @throws UnknownObjectType if unknown object will be in params.
     */
    public static void insertCustomer(Customer customer) throws UnknownObjectType {
        if (!checkForExistingPerson(customer)) {
            CustomerLight customerLight = new CustomerLight(customer);
            writeInFile(customerLight, "Customers");
            IdTypes idTypes = new IdTypes(customer);
            writeInFile(idTypes, "IdTypes");
        }
    }

    /**
     * Method insert into file new user in Json format.
     *
     * @param user object to insert.
     * @throws UnknownObjectType if unknown object will be in params.
     */
    public static void insertUser(User user) throws UnknownObjectType {
        if (!checkForExistingPerson(user)) {
            UserLight userLight = new UserLight(user);
            writeInFile(userLight, "Users");
            IdTypes idTypes = new IdTypes(user);
            writeInFile(idTypes, "IdTypes");
        }
    }

    /**
     * Method insert into file new project in Json format.
     *
     * @param project object to insert.
     * @throws UnknownObjectType if unknown object will be in params.
     */
    public static void insertProject(Project project) throws UnknownObjectType {
        if (!checkForExistingProject(project)) {
            ProjectLight projectLight = new ProjectLight(project);
            writeInFile(projectLight, "Projects");
            IdTypes idTypes = new IdTypes(project);
            writeInFile(idTypes, "IdTypes");
        }
    }

    /**
     * Method insert into file new sprint in Json format.
     *
     * @param sprint object to insert.
     * @throws UnknownObjectType if unknown object will be in params.
     */
    public static void insertSprint(Sprint sprint) throws UnknownObjectType {
        if (!checkForExistingSprint(sprint)) {
            SprintLight sprintLight = new SprintLight(sprint);
            writeInFile(sprintLight, "Sprints");
            IdTypes idTypes = new IdTypes(sprint);
            writeInFile(idTypes, "IdTypes");
        }
    }

    /**
     * Method insert into file new task in Json format.
     *
     * @param task object to insert.
     * @throws UnknownObjectType if unknown object will be in params.
     */
    public static void insertTask(Task task) throws UnknownObjectType {
        if (!checkForExistingTask(task)) {
            TaskLight taskLight = new TaskLight(task);
            writeInFile(taskLight, "Tasks");
            IdTypes idTypes = new IdTypes(task);
            writeInFile(idTypes, "IdTypes");
        }
    }

    /**
     * Method insert into file new request in Json format.
     *
     * @param request object to insert.
     * @throws UnknownObjectType if unknown object will be in params.
     */
    public static void insertRequestForIncreasingTime(RequestForIncreasingTime request) throws UnknownObjectType {
        if (!checkForExistingRequestForIncreasingTime(request)) {
            RequestForIncreasingTimeLight requestLight = new RequestForIncreasingTimeLight(request);
            writeInFile(requestLight, "Requests");
            IdTypes idTypes = new IdTypes(request);
            writeInFile(idTypes, "IdTypes");
        }
    }

    /**
     * Method insert into file new doTask in Json format.
     *
     * @param doTask object to insert.
     * @throws UnknownObjectType if unknown object will be in params.
     */
    public static void insertDoTask(DoTask doTask) throws UnknownObjectType {
        if (!checkForExistingDoTask(doTask)) {
            DoTasksLight doTasksLight = new DoTasksLight(doTask);
            writeInFile(doTasksLight, "DoTask");
            IdTypes idTypes = new IdTypes(doTask);
            writeInFile(idTypes, "IdTypes");
        }
    }
}
