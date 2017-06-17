package com.bulgakov.db.dbJson;

import com.bulgakov.exceptions.UnknownObjectType;
import com.bulgakov.model.*;
import com.bulgakov.model.LightVersion.*;
import com.google.gson.Gson;

import java.util.List;

import static com.bulgakov.db.dbJson.JsonUtils.*;

/**
 * @author Bulgakov
 * @since 27.02.2017
 */
public class JsonUpdateMethods {
    private static JsonDAO jsonDAO = new JsonDAO();

    /**
     * Method update Customer object. It checks for coincidence mail from other users and customers.
     * Can't change ID of object.
     *
     * @param obj updated Object.
     */
    public static void updateCustomer(Object obj) {
        Gson gson = new Gson();
        Boolean canUpdate = true;
        Customer newCustomer = (Customer) obj;
        // check for coincidence User's mail
        List<String> listOfUsers = getRecords("Users");
        for (String user : listOfUsers) {
            if (user.contains("\"email\":\"" + newCustomer.getEmail() + "\"")) {
                canUpdate = false;
            }
        }
        // check for coincidence Customer's mail
        List<String> listOfCustomers = getRecords("Customers");
        for (String customer : listOfCustomers) {
            if (customer.contains("\"email\":\"" + newCustomer.getEmail() + "\"") && (!customer.contains("\"personId\":" + newCustomer.getPersonId()))) {
                canUpdate = false;
            }
        }
        if (canUpdate) {
            // delete from list of customers old information about this customer
            String tmp = null;
            for (String customer : listOfCustomers) {
                if (customer.contains("\"personId\":" + newCustomer.getPersonId())) {
                    tmp = customer;
                    break;
                }
            }
            listOfCustomers.remove(tmp);
            CustomerLight customerLight = new CustomerLight(newCustomer);
            String jsonString = gson.toJson(customerLight);
            listOfCustomers.add(jsonString);
            // rewrite customers with new data
            writeListOfRecords(listOfCustomers, "Customers");
        }
    }

    /**
     * Method update User object. It checks for coincidence mail from other users and customers.
     * If new User has new Boss, method to do a check for the existence of a new boss in the com.bulgakov.Beans.db.
     * Can't change ID of object.
     *
     * @param obj updated User.
     * @throws UnknownObjectType if param of method will be unknown object.
     */
    public static void updateUser(Object obj) throws UnknownObjectType {
        Gson gson = new Gson();
        Boolean canUpdate = true;
        User newUser = (User) obj;
        User oldUser = (User) jsonDAO.read(newUser.getPersonId());
        List<String> listOfUsers = getRecords("Users");
        if (!oldUser.getEmail().equals(newUser.getEmail())) {
            // check for coincidence User's mail
            for (String user : listOfUsers) {
                if (user.contains("\"email\":\"" + newUser.getEmail() + "\"")) {
                    canUpdate = false;
                }
            }
            // check for coincidence Customer's mail
            List<String> listOfCustomers = getRecords("Customers");
            for (String customer : listOfCustomers) {
                if (customer.contains("\"email\":\"" + newUser.getEmail() + "\"")) {
                    canUpdate = false;
                }
            }
        }
        // if new User has new Boss, method to do a check for the existence of a new boss in the com.bulgakov.Beans.db
        if (!oldUser.getBoss().getPersonId().equals(newUser.getBoss().getPersonId())) {
            if (!checkForExistingId(newUser.getBoss().getPersonId())) {
                canUpdate = false;
            } else {
                if (!checkObjectType(newUser.getBoss().getPersonId(), "User")) {
                    canUpdate = false;
                }
            }
        }
        if (canUpdate) {
            String tmp = null;
            for (String user : listOfUsers) {
                if (user.contains("\"personId\":" + newUser.getPersonId())) {
                    tmp = user;
                    break;
                }
            }
            listOfUsers.remove(tmp);
            UserLight userLight = new UserLight(newUser);
            String jsonString = gson.toJson(userLight);
            listOfUsers.add(jsonString);
            // rewrite users with new data
            writeListOfRecords(listOfUsers, "Users");
        }
    }

    /**
     * Method update Project object. If new Project has new Customer, method to do a check for the existence of a new customer in the com.bulgakov.Beans.db.
     * Can't change ID of object.
     *
     * @param obj updated Project.
     * @throws UnknownObjectType if param of method will be unknown object.
     */
    public static void updateProject(Object obj) throws UnknownObjectType {
        Gson gson = new Gson();
        Boolean canUpdate = true;
        Project newProject = (Project) obj;
        Project oldProject = (Project) jsonDAO.read(newProject.getProjectId());
        // if new Project has new Customer, method to do a check for the existence of a new customer in the com.bulgakov.Beans.db
        if (!oldProject.getCustomer().getPersonId().equals(newProject.getCustomer().getPersonId())) {
            if (!checkForExistingId(newProject.getCustomer().getPersonId())) {
                canUpdate = false;
            } else {
                if (!checkObjectType(newProject.getCustomer().getPersonId(), "Customer")) {
                    canUpdate = false;
                }
            }
        }
        if (canUpdate) {
            List<String> listOfProjects = getRecords("Projects");
            String tmp = null;
            for (String project : listOfProjects) {
                if (project.contains("\"projectId\":" + newProject.getProjectId())) {
                    tmp = project;
                    break;
                }
            }
            listOfProjects.remove(tmp);
            ProjectLight projectLight = new ProjectLight(newProject);
            String jsonString = gson.toJson(projectLight);
            listOfProjects.add(jsonString);
            // rewrite projects with new data
            writeListOfRecords(listOfProjects, "Projects");
        }
    }

    /**
     * Method update Sprint object. If new Sprint has new Project, method to do a check for the existence of a new Project in the com.bulgakov.Beans.db.
     * Can't change ID of object.
     *
     * @param obj updated Sprint.
     * @throws UnknownObjectType if param of method will be unknown object.
     */
    public static void updateSprint(Object obj) throws UnknownObjectType {
        Gson gson = new Gson();
        Boolean canUpdate = true;
        Sprint newSprint = (Sprint) obj;
        Sprint oldSprint = (Sprint) jsonDAO.read(newSprint.getSprintId());
        // if new Sprint has new Project, method to do a check for the existence of a new Project in the com.bulgakov.Beans.db
        if (!oldSprint.getProject().getProjectId().equals(newSprint.getProject().getProjectId())) {
            if (!checkForExistingId(newSprint.getProject().getProjectId())) {
                canUpdate = false;
            } else {
                if (!checkObjectType(newSprint.getProject().getProjectId(), "Project")) {
                    canUpdate = false;
                }
            }
        }
        if (canUpdate) {
            List<String> listOfSprints = getRecords("Sprints");
            String tmp = null;
            for (String sprint : listOfSprints) {
                if (sprint.contains("\"sprintId\":" + newSprint.getSprintId())) {
                    tmp = sprint;
                    break;
                }
            }
            listOfSprints.remove(tmp);
            SprintLight sprintLight = new SprintLight(newSprint);
            String jsonString = gson.toJson(sprintLight);
            listOfSprints.add(jsonString);
            // rewrite sprints with new data
            writeListOfRecords(listOfSprints, "Sprints");
        }
    }

    /**
     * Method update Task. If parent task was changed, this task will be removed from Depends on task list of old parent task and add to list of new parent task.
     *
     * @param obj updated Task.
     * @throws UnknownObjectType if param of method will be unknown object.
     */
    public static void updateTask(Object obj) throws UnknownObjectType {
        Gson gson = new Gson();
        Boolean canUpdate = true;
        Boolean parentTaskWasChange = false;
        Task newTask = (Task) obj;
        Task oldTask = (Task) jsonDAO.read(newTask.getTaskId());
        // if new task has new parent task, method to do a check for the existence of a new parent task in the com.bulgakov.Beans.db
        if (!oldTask.getParentTask().getTaskId().equals(newTask.getParentTask().getTaskId())) {
            parentTaskWasChange = true;
            if (!checkForExistingId(newTask.getParentTask().getTaskId())) {
                canUpdate = false;
            } else {
                if (!checkObjectType(newTask.getParentTask().getTaskId(), "Task")) {
                    canUpdate = false;
                }
            }
        }
        // if new task has new sprint, method to do a check for the existence of a new sprint in the com.bulgakov.Beans.db
        if (oldTask.getSprint().getSprintId().equals(newTask.getSprint().getSprintId())) {
            if (!checkForExistingId(newTask.getSprint().getSprintId())) {
                canUpdate = false;
            } else {
                if (!checkObjectType(newTask.getSprint().getSprintId(), "Sprint")) {
                    canUpdate = false;
                }
            }
        }
        // update all tasks from depends of task list
        if (newTask.getDependsOnTasks() != null) {
            for (Task taskToUpdate : newTask.getDependsOnTasks()) {
                jsonDAO.update(taskToUpdate);
            }
        }

        if (canUpdate) {
            if (parentTaskWasChange) {
                Task oldParentTask = (Task) jsonDAO.read(oldTask.getParentTask().getTaskId());
                Task newParentTask = (Task) jsonDAO.read(newTask.getParentTask().getTaskId());
                Boolean taskAlreadyInDependsList = false;
                // remove this task from Depends on task list of old parent Task
                Task tmp = null;
                for (Task iterator : oldParentTask.getDependsOnTasks()) {
                    if (iterator.getTaskId().equals(newTask.getTaskId())) {
                        tmp = iterator;
                        break;
                    }
                }
                oldParentTask.getDependsOnTasks().remove(tmp);
                jsonDAO.update(oldParentTask);
                // check for existing this task in depends on task list of new parent Task
                for (Task iterator : newParentTask.getDependsOnTasks()) {
                    if (iterator.getTaskId().equals(newTask.getTaskId())) {
                        taskAlreadyInDependsList = true;
                        break;
                    }
                }
                // add this task to depends on task list of new parent Task
                if (!taskAlreadyInDependsList) {
                    jsonDAO.update(newParentTask);
                }
            }
            List<String> listOfTasks = getRecords("Tasks");
            String tmp = null;
            for (String task : listOfTasks) {
                if (task.contains("\"taskId\":" + newTask.getTaskId())) {
                    tmp = task;
                    break;
                }
            }
            listOfTasks.remove(tmp);
            TaskLight taskLight = new TaskLight(newTask);
            String jsonString = gson.toJson(taskLight);
            listOfTasks.add(jsonString);
            // rewrite tasks with new data
            writeListOfRecords(listOfTasks, "Tasks");
        }
    }

    /**
     * Method update Request only if description or quantity of hours were changed.
     * Can't change ID of object.
     *
     * @param obj new Updated Request.
     * @throws UnknownObjectType if param of method will be unknown object.
     */
    public static void updateRequest(Object obj) throws UnknownObjectType {
        Gson gson = new Gson();
        RequestForIncreasingTime newRequest = (RequestForIncreasingTime) obj;
        RequestForIncreasingTime oldRequest = (RequestForIncreasingTime) jsonDAO.read(newRequest.getRequestId());
        // update request only if description or quantity of hours was changed
        if ((!newRequest.getDescription().equals(oldRequest.getDescription())) || (!newRequest.getHours().equals(oldRequest.getHours()))) {
            List<String> listOfRequests = getRecords("Requests");
            String tmp = null;
            for (String request : listOfRequests) {
                if (request.contains("\"requestId\":" + newRequest.getRequestId())) {
                    tmp = request;
                    break;
                }
            }
            listOfRequests.remove(tmp);
            RequestForIncreasingTimeLight requestLight = new RequestForIncreasingTimeLight(newRequest);
            String jsonString = gson.toJson(requestLight);
            listOfRequests.add(jsonString);
            // rewrite requests with new data
            writeListOfRecords(listOfRequests, "Requests");
        }
    }

    /**
     * Method update DoTask object only if date begin or date end were changed.
     * Can't change ID of object.
     *
     * @param obj new updated DoTask.
     * @throws UnknownObjectType if param of method will be unknown object.
     */
    public static void updateDoTask(Object obj) throws UnknownObjectType {
        Gson gson = new Gson();
        DoTask newDoTask = (DoTask) obj;
        DoTask oldDoTask = (DoTask) jsonDAO.read(newDoTask.getDoTaskId());
        if ((!newDoTask.getDateBegin().equals(oldDoTask.getDateBegin())) || (!newDoTask.getDateEnd().equals(oldDoTask.getDateEnd()))) {
            List<String> listOfDoTask = getRecords("DoTasks");
            String tmp = null;
            for (String doTask : listOfDoTask) {
                if (doTask.contains("\"doTaskId\":" + newDoTask.getDoTaskId())) {
                    tmp = doTask;
                    break;
                }
            }
            listOfDoTask.remove(tmp);
            DoTasksLight doTasksLight = new DoTasksLight(newDoTask);
            String jsonString = gson.toJson(doTasksLight);
            listOfDoTask.add(jsonString);
            // rewrite doTasks with new data
            writeListOfRecords(listOfDoTask, "DoTasks");
        }
    }
}
