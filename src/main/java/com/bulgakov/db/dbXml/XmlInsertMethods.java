package com.bulgakov.db.dbXml;

import com.bulgakov.exceptions.UnknownObjectType;
import com.bulgakov.model.*;
import com.bulgakov.model.LightVersion.*;

import java.util.List;

/**
 * @author Bulgakov
 * @since 05.03.2017
 */
public class XmlInsertMethods {
    /**
     * Method insert into file new customer.
     *
     * @param customer object to insert.
     * @throws UnknownObjectType if unknown object will be in params.
     */
    public static void insertCustomer(Customer customer) throws UnknownObjectType {
        if (!XmlUtils.checkForExistingPerson(customer)) {
            CustomerLight customerLight = new CustomerLight(customer);
            IdTypes idTypes = new IdTypes(customer);
            List<CustomerLight> listCustomers = (List<CustomerLight>) XmlUtils.getRecords("Customers");
            List<IdTypes> listIdTypes = (List<IdTypes>) XmlUtils.getRecords("IdTypes");
            listCustomers.add(customerLight);
            listIdTypes.add(idTypes);
            XmlUtils.writeInFile(listCustomers, "Customers");
            XmlUtils.writeInFile(listIdTypes, "IdTypes");
        }
    }

    /**
     * Method insert into file new user.
     *
     * @param user object to insert.
     * @throws UnknownObjectType if unknown object will be in params.
     */
    public static void insertUser(User user) throws UnknownObjectType {
        if (!XmlUtils.checkForExistingPerson(user)) {
            UserLight userLight = new UserLight(user);
            IdTypes idTypes = new IdTypes(user);
            List<UserLight> listOfUsers = (List<UserLight>) XmlUtils.getRecords("Users");
            List<IdTypes> listIdTypes = (List<IdTypes>) XmlUtils.getRecords("IdTypes");
            listOfUsers.add(userLight);
            listIdTypes.add(idTypes);
            XmlUtils.writeInFile(listOfUsers, "Users");
            XmlUtils.writeInFile(listIdTypes, "IdTypes");
        }
    }

    /**
     * Method insert into file new project.
     *
     * @param project object to insert.
     * @throws UnknownObjectType if unknown object will be in params.
     */
    public static void insertProject(Project project) throws UnknownObjectType {
        if (!XmlUtils.checkForExistingProject(project)) {
            ProjectLight projectLight = new ProjectLight(project);
            IdTypes idTypes = new IdTypes(project);
            List<ProjectLight> listOfProjects = (List<ProjectLight>) XmlUtils.getRecords("Projects");
            List<IdTypes> listIdTypes = (List<IdTypes>) XmlUtils.getRecords("IdTypes");
            listOfProjects.add(projectLight);
            listIdTypes.add(idTypes);
            XmlUtils.writeInFile(listOfProjects, "Projects");
            XmlUtils.writeInFile(listIdTypes, "IdTypes");
        }
    }

    /**
     * Method insert into file new sprint.
     *
     * @param sprint object to insert.
     * @throws UnknownObjectType if unknown object will be in params.
     */
    public static void insertSprint(Sprint sprint) throws UnknownObjectType {
        if (!XmlUtils.checkForExistingSprint(sprint)) {
            SprintLight sprintLight = new SprintLight(sprint);
            IdTypes idTypes = new IdTypes(sprint);
            List<SprintLight> listOfSprints = (List<SprintLight>) XmlUtils.getRecords("Sprints");
            List<IdTypes> listIdTypes = (List<IdTypes>) XmlUtils.getRecords("IdTypes");
            listOfSprints.add(sprintLight);
            listIdTypes.add(idTypes);
            XmlUtils.writeInFile(listOfSprints, "Sprints");
            XmlUtils.writeInFile(listIdTypes, "IdTypes");
        }
    }

    /**
     * Method insert into file new task in.
     *
     * @param task object to insert.
     * @throws UnknownObjectType if unknown object will be in params.
     */
    public static void insertTask(Task task) throws UnknownObjectType {
        if (!XmlUtils.checkForExistingTask(task)) {
            TaskLight taskLight = new TaskLight(task);
            IdTypes idTypes = new IdTypes(task);
            List<TaskLight> listOfTasks = (List<TaskLight>) XmlUtils.getRecords("Tasks");
            List<IdTypes> listIdTypes = (List<IdTypes>) XmlUtils.getRecords("IdTypes");
            listOfTasks.add(taskLight);
            listIdTypes.add(idTypes);
            XmlUtils.writeInFile(listOfTasks, "Tasks");
            XmlUtils.writeInFile(listIdTypes, "IdTypes");
        }
    }

    /**
     * Method insert into file new request.
     *
     * @param request object to insert.
     * @throws UnknownObjectType if unknown object will be in params.
     */
    public static void insertRequestForIncreasingTime(RequestForIncreasingTime request) throws UnknownObjectType {
        if (!XmlUtils.checkForExistingRequestForIncreasingTime(request)) {
            RequestForIncreasingTimeLight requestLight = new RequestForIncreasingTimeLight(request);
            IdTypes idTypes = new IdTypes(request);
            List<RequestForIncreasingTimeLight> listOfRequests = (List<RequestForIncreasingTimeLight>) XmlUtils.getRecords("Requests");
            List<IdTypes> listIdTypes = (List<IdTypes>) XmlUtils.getRecords("IdTypes");
            listOfRequests.add(requestLight);
            listIdTypes.add(idTypes);
            XmlUtils.writeInFile(listOfRequests, "Requests");
            XmlUtils.writeInFile(listIdTypes, "IdTypes");
        }
    }

    /**
     * Method insert into file new doTask.
     *
     * @param doTask object to insert.
     * @throws UnknownObjectType if unknown object will be in params.
     */
    public static void insertDoTask(DoTask doTask) throws UnknownObjectType {
        if (!XmlUtils.checkForExistingDoTask(doTask)) {
            DoTasksLight doTasksLight = new DoTasksLight(doTask);
            IdTypes idTypes = new IdTypes(doTask);
            List<DoTasksLight> listOfDoTasks = (List<DoTasksLight>) XmlUtils.getRecords("DoTasks");
            List<IdTypes> listIdTypes = (List<IdTypes>) XmlUtils.getRecords("IdTypes");
            listOfDoTasks.add(doTasksLight);
            listIdTypes.add(idTypes);
            XmlUtils.writeInFile(listOfDoTasks, "DoTasks");
            XmlUtils.writeInFile(listIdTypes, "IdTypes");
        }
    }
}
