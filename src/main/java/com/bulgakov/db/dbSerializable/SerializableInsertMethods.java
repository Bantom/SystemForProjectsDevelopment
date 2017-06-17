package com.bulgakov.db.dbSerializable;

import com.bulgakov.exceptions.UnknownObjectType;
import com.bulgakov.model.*;
import com.bulgakov.model.LightVersion.*;

import java.util.List;

/**
 * @author Bulgakov
 * @since 04.03.2017
 */
public class SerializableInsertMethods {

    /**
     * Method insert into file new customer.
     *
     * @param customer object to insert.
     * @throws UnknownObjectType if unknown object will be in params.
     */
    public static void insertCustomer(Customer customer) throws UnknownObjectType {
        if (!SerializableUtils.checkForExistingPerson(customer)) {
            CustomerLight customerLight = new CustomerLight(customer);
            IdTypes idTypes = new IdTypes(customer);
            List<CustomerLight> listCustomers = (List<CustomerLight>) SerializableUtils.getRecords("Customers");
            List<IdTypes> listIdTypes = (List<IdTypes>) SerializableUtils.getRecords("IdTypes");
            listCustomers.add(customerLight);
            listIdTypes.add(idTypes);
            SerializableUtils.writeInFile(listCustomers, "Customers");
            SerializableUtils.writeInFile(listIdTypes, "IdTypes");
        }
    }

    /**
     * Method insert into file new user.
     *
     * @param user object to insert.
     * @throws UnknownObjectType if unknown object will be in params.
     */
    public static void insertUser(User user) throws UnknownObjectType {
        if (!SerializableUtils.checkForExistingPerson(user)) {
            UserLight userLight = new UserLight(user);
            IdTypes idTypes = new IdTypes(user);
            List<UserLight> listOfUsers = (List<UserLight>) SerializableUtils.getRecords("Users");
            List<IdTypes> listIdTypes = (List<IdTypes>) SerializableUtils.getRecords("IdTypes");
            listOfUsers.add(userLight);
            listIdTypes.add(idTypes);
            SerializableUtils.writeInFile(listOfUsers, "Users");
            SerializableUtils.writeInFile(listIdTypes, "IdTypes");
        }
    }

    /**
     * Method insert into file new projectt.
     *
     * @param project object to insert.
     * @throws UnknownObjectType if unknown object will be in params.
     */
    public static void insertProject(Project project) throws UnknownObjectType {
        if (!SerializableUtils.checkForExistingProject(project)) {
            ProjectLight projectLight = new ProjectLight(project);
            IdTypes idTypes = new IdTypes(project);
            List<ProjectLight> listOfProjects = (List<ProjectLight>) SerializableUtils.getRecords("Projects");
            List<IdTypes> listIdTypes = (List<IdTypes>) SerializableUtils.getRecords("IdTypes");
            listOfProjects.add(projectLight);
            listIdTypes.add(idTypes);
            SerializableUtils.writeInFile(listOfProjects, "Projects");
            SerializableUtils.writeInFile(listIdTypes, "IdTypes");
        }
    }

    /**
     * Method insert into file new sprint.
     *
     * @param sprint object to insert.
     * @throws UnknownObjectType if unknown object will be in params.
     */
    public static void insertSprint(Sprint sprint) throws UnknownObjectType {
        if (!SerializableUtils.checkForExistingSprint(sprint)) {
            SprintLight sprintLight = new SprintLight(sprint);
            IdTypes idTypes = new IdTypes(sprint);
            List<SprintLight> listOfSprints = (List<SprintLight>) SerializableUtils.getRecords("Sprints");
            List<IdTypes> listIdTypes = (List<IdTypes>) SerializableUtils.getRecords("IdTypes");
            listOfSprints.add(sprintLight);
            listIdTypes.add(idTypes);
            SerializableUtils.writeInFile(listOfSprints, "Sprints");
            SerializableUtils.writeInFile(listIdTypes, "IdTypes");
        }
    }

    /**
     * Method insert into file new task in.
     *
     * @param task object to insert.
     * @throws UnknownObjectType if unknown object will be in params.
     */
    public static void insertTask(Task task) throws UnknownObjectType {
        if (!SerializableUtils.checkForExistingTask(task)) {
            TaskLight taskLight = new TaskLight(task);
            IdTypes idTypes = new IdTypes(task);
            List<TaskLight> listOfTasks = (List<TaskLight>) SerializableUtils.getRecords("Tasks");
            List<IdTypes> listIdTypes = (List<IdTypes>) SerializableUtils.getRecords("IdTypes");
            listOfTasks.add(taskLight);
            listIdTypes.add(idTypes);
            SerializableUtils.writeInFile(listOfTasks, "Tasks");
            SerializableUtils.writeInFile(listIdTypes, "IdTypes");
        }
    }

    /**
     * Method insert into file new request.
     *
     * @param request object to insert.
     * @throws UnknownObjectType if unknown object will be in params.
     */
    public static void insertRequestForIncreasingTime(RequestForIncreasingTime request) throws UnknownObjectType {
        if (!SerializableUtils.checkForExistingRequestForIncreasingTime(request)) {
            RequestForIncreasingTimeLight requestLight = new RequestForIncreasingTimeLight(request);
            IdTypes idTypes = new IdTypes(request);
            List<RequestForIncreasingTimeLight> listOfRequests = (List<RequestForIncreasingTimeLight>) SerializableUtils.getRecords("Requests");
            List<IdTypes> listIdTypes = (List<IdTypes>) SerializableUtils.getRecords("IdTypes");
            listOfRequests.add(requestLight);
            listIdTypes.add(idTypes);
            SerializableUtils.writeInFile(listOfRequests, "Requests");
            SerializableUtils.writeInFile(listIdTypes, "IdTypes");
        }
    }

    /**
     * Method insert into file new doTask.
     *
     * @param doTask object to insert.
     * @throws UnknownObjectType if unknown object will be in params.
     */
    public static void insertDoTask(DoTask doTask) throws UnknownObjectType {
        if (!SerializableUtils.checkForExistingDoTask(doTask)) {
            DoTasksLight doTasksLight = new DoTasksLight(doTask);
            IdTypes idTypes = new IdTypes(doTask);
            List<DoTasksLight> listOfDoTasks = (List<DoTasksLight>) SerializableUtils.getRecords("DoTasks");
            List<IdTypes> listIdTypes = (List<IdTypes>) SerializableUtils.getRecords("IdTypes");
            listOfDoTasks.add(doTasksLight);
            listIdTypes.add(idTypes);
            SerializableUtils.writeInFile(listOfDoTasks, "DoTasks");
            SerializableUtils.writeInFile(listIdTypes, "IdTypes");
        }
    }

}
