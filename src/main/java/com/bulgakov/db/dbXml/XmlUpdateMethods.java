package com.bulgakov.db.dbXml;

import com.bulgakov.exceptions.UnknownObjectType;
import com.bulgakov.model.*;
import com.bulgakov.model.LightVersion.*;

import java.util.List;

/**
 * @author Bulgakov
 * @since 05.03.2017
 */
public class XmlUpdateMethods {

    private static XmlDAO dao = new XmlDAO();

    /**
     * Method update Customer object. It checks for coincidence mail from other users and customers.
     * Can't change ID of object.
     * @param obj updated Object.
     */
    public static void updateCustomer(Object obj) {
        Boolean canUpdate = true;
        Customer newCustomer = (Customer) obj;
        // check for coincidence User's mail
        List<UserLight> listOfUsers = (List<UserLight>) XmlUtils.getRecords("Users");
        for (UserLight user : listOfUsers) {
            if (user.email.equals(newCustomer.getEmail())) {
                canUpdate = false;
            }
        }
        // check for coincidence Customer's mail
        List<CustomerLight> listOfCustomers = (List<CustomerLight>) XmlUtils.getRecords("Customers");
        for (CustomerLight customer : listOfCustomers) {
            if (customer.email.equals(newCustomer.getEmail()) && (!customer.personId.equals(newCustomer.getPersonId()))) {
                canUpdate = false;
            }
        }
        if (canUpdate) {
            CustomerLight tmp = null;
            // delete from list of customers old information about this customer
            for (CustomerLight customer : listOfCustomers) {
                if (customer.personId.equals(newCustomer.getPersonId())) {
                    tmp = customer;
                    break;
                }
            }
            listOfCustomers.remove(tmp);
            CustomerLight customerLight = new CustomerLight(newCustomer);
            listOfCustomers.add(customerLight);
            // rewrite customers with new data
            XmlUtils.writeInFile(listOfCustomers, "Customers");
        }
    }

    /**
     * Method update User object. It checks for coincidence mail from other users and customers.
     * If new User has new Boss, method to do a check for the existence of a new boss in the com.bulgakov.Beans.db.
     * Can't change ID of object.
     * @param obj updated User.
     * @throws UnknownObjectType if param of method will be unknown object.
     */
    public static void updateUser(Object obj) throws UnknownObjectType {
        Boolean canUpdate = true;
        User newUser = (User) obj;
        User oldUser = (User) dao.read(newUser.getPersonId());
        List<UserLight> listOfUsers = (List<UserLight>) XmlUtils.getRecords("Users");
        if (!oldUser.getEmail().equals(newUser.getEmail())) {
            // check for coincidence User's mail
            for (UserLight user : listOfUsers) {
                if (user.email.equals(newUser.getEmail())) {
                    canUpdate = false;
                }
            }
            // check for coincidence Customer's mail
            List<CustomerLight> listOfCustomers = (List<CustomerLight>) XmlUtils.getRecords("Customers");
            for (CustomerLight customer : listOfCustomers) {
                if (customer.email.equals(newUser.getEmail())) {
                    canUpdate = false;
                }
            }
        }
        // if new User has new Boss, method to do a check for the existence of a new boss in the com.bulgakov.Beans.db
        if (!oldUser.getBoss().getPersonId().equals(newUser.getBoss().getPersonId())) {
            if (!XmlUtils.checkForExistingId(newUser.getBoss().getPersonId())) {
                canUpdate = false;
            }else{
                if (!XmlUtils.checkObjectType(newUser.getBoss().getPersonId(), "User")) {
                    canUpdate = false;
                }
            }
        }
        if (canUpdate) {
            UserLight tmp = null;
            for (UserLight user : listOfUsers) {
                if (user.personId.equals(newUser.getPersonId())) {
                    tmp = user;
                    break;
                }
            }
            listOfUsers.remove(tmp);
            UserLight userLight = new UserLight(newUser);
            listOfUsers.add(userLight);
            // rewrite users with new data
            XmlUtils.writeInFile(listOfUsers, "Users");
        }
    }

    /**
     * Method update Project object. If new Project has new Customer, method to do a check for the existence of a new customer in the com.bulgakov.Beans.db.
     * Can't change ID of object.
     * @param obj updated Project.
     * @throws UnknownObjectType if param of method will be unknown object.
     */
    public static void updateProject(Object obj) throws UnknownObjectType {
        Boolean canUpdate = true;
        Project newProject = (Project) obj;
        Project oldProject = (Project) dao.read(newProject.getProjectId());
        // if new Project has new Customer, method to do a check for the existence of a new customer in the com.bulgakov.Beans.db
        if (!oldProject.getCustomer().getPersonId().equals(newProject.getCustomer().getPersonId())) {
            if (!XmlUtils.checkForExistingId(newProject.getCustomer().getPersonId())){
                canUpdate = false;
            }else{
                if (!XmlUtils.checkObjectType(newProject.getCustomer().getPersonId(), "Customer")) {
                    canUpdate = false;
                }
            }
        }
        if (canUpdate){
            List<ProjectLight> listOfProjects = (List<ProjectLight>) XmlUtils.getRecords("Projects");
            ProjectLight tmp = null;
            for (ProjectLight project : listOfProjects) {
                if (project.projectId.equals(newProject.getProjectId())){
                    tmp = project;
                    break;
                }
            }
            listOfProjects.remove(tmp);
            ProjectLight projectLight = new ProjectLight(newProject);
            listOfProjects.add(projectLight);
            // rewrite projects with new data
            XmlUtils.writeInFile(listOfProjects, "Projects");
        }
    }

    /**
     * Method update Sprint object. If new Sprint has new Project, method to do a check for the existence of a new Project in the com.bulgakov.Beans.db.
     * Can't change ID of object.
     * @param obj updated Sprint.
     * @throws UnknownObjectType if param of method will be unknown object.
     */
    public static void updateSprint(Object obj) throws UnknownObjectType {
        Boolean canUpdate = true;
        Sprint newSprint = (Sprint) obj;
        Sprint oldSprint = (Sprint) dao.read(newSprint.getSprintId());
        // if new Sprint has new Project, method to do a check for the existence of a new Project in the com.bulgakov.Beans.db
        if (!oldSprint.getProject().getProjectId().equals(newSprint.getProject().getProjectId())) {
            if (!XmlUtils.checkForExistingId(newSprint.getProject().getProjectId())) {
                canUpdate = false;
            }else{
                if (!XmlUtils.checkObjectType(newSprint.getProject().getProjectId(), "Project")){
                    canUpdate = false;
                }
            }
        }
        if (canUpdate) {
            List<SprintLight> listOfSprints = (List<SprintLight>) XmlUtils.getRecords("Sprints");
            SprintLight tmp = null;
            for (SprintLight sprint : listOfSprints) {
                if (sprint.sprintId.equals(newSprint.getSprintId())) {
                    tmp = sprint;
                    break;
                }
            }
            listOfSprints.remove(tmp);
            SprintLight sprintLight = new SprintLight(newSprint);
            listOfSprints.add(sprintLight);
            // rewrite sprints with new data
            XmlUtils.writeInFile(listOfSprints, "Sprints");
        }
    }

    /**
     * Method update Task. If parent task was changed, this task will be removed from Depends on task list of old parent task and add to list of new parent task.
     *
     * @param obj updated Task.
     * @throws UnknownObjectType if param of method will be unknown object.
     */
    public static void updateTask(Object obj) throws UnknownObjectType {
        Boolean canUpdate = true;
        Boolean parentTaskWasChange = false;
        Task newTask = (Task) obj;
        Task oldTask = (Task) dao.read(newTask.getTaskId());
        // if new task has new parent task, method to do a check for the existence of a new parent task in the com.bulgakov.Beans.db
        if (!oldTask.getParentTask().getTaskId().equals(newTask.getParentTask().getTaskId())) {
            parentTaskWasChange = true;
            if (!XmlUtils.checkForExistingId(newTask.getParentTask().getTaskId())) {
                canUpdate = false;
            } else {
                if (!XmlUtils.checkObjectType(newTask.getParentTask().getTaskId(), "Task")) {
                    canUpdate = false;
                }
            }
        }
        // if new task has new sprint, method to do a check for the existence of a new sprint in the com.bulgakov.Beans.db
        if (oldTask.getSprint().getSprintId().equals(newTask.getSprint().getSprintId())) {
            if (!XmlUtils.checkForExistingId(newTask.getSprint().getSprintId())) {
                canUpdate = false;
            } else {
                if (!XmlUtils.checkObjectType(newTask.getSprint().getSprintId(), "Sprint")) {
                    canUpdate = false;
                }
            }
        }
        // update all tasks from depends of task list
        if (newTask.getDependsOnTasks() != null) {
            for (Task taskToUpdate : newTask.getDependsOnTasks()) {
                dao.update(taskToUpdate);
            }
        }

        if (canUpdate) {
            if (parentTaskWasChange) {
                Task oldParentTask = (Task) dao.read(oldTask.getParentTask().getTaskId());
                Task newParentTask = (Task) dao.read(newTask.getParentTask().getTaskId());
                Boolean taskAlreadyInDependsList = false;
                // remove this task from Depends on task list of old parent Task
                Task taskTmp = null;
                for (Task iterator : oldParentTask.getDependsOnTasks()) {
                    if (iterator.getTaskId().equals(newTask.getTaskId())) {
                        taskTmp = iterator;
                        break;
                    }
                }
                oldParentTask.getDependsOnTasks().remove(taskTmp);
                dao.update(oldParentTask);
                // check for existing this task in depends on task list of new parent Task
                for (Task iterator : newParentTask.getDependsOnTasks()) {
                    if (iterator.getTaskId().equals(newTask.getTaskId())) {
                        taskAlreadyInDependsList = true;
                        break;
                    }
                }
                // add this task to depends on task list of new parent Task
                if (!taskAlreadyInDependsList) {
                    dao.update(newParentTask);
                }
            }
            List<TaskLight> listOfTasks = (List<TaskLight>) XmlUtils.getRecords("Tasks");
            TaskLight tmp = null;
            for (TaskLight task : listOfTasks) {
                if (task.taskId.equals(newTask.getTaskId())) {
                    tmp = task;
                    break;
                }
            }
            listOfTasks.remove(tmp);
            TaskLight taskLight = new TaskLight(newTask);
            listOfTasks.add(taskLight);
            // rewrite tasks with new data
            XmlUtils.writeInFile(listOfTasks, "Tasks");
        }
    }

    /**
     * Method update Request only if description or quantity of hours were changed.
     * Can't change ID of object.
     * @param obj new Updated Request.
     * @throws UnknownObjectType if param of method will be unknown object.
     */
    public static void updateRequest(Object obj) throws UnknownObjectType {
        RequestForIncreasingTime newRequest = (RequestForIncreasingTime) obj;
        RequestForIncreasingTime oldRequest = (RequestForIncreasingTime) dao.read(newRequest.getRequestId());
        // update request only if description or quantity of hours was changed
        if ((!newRequest.getDescription().equals(oldRequest.getDescription())) || (!newRequest.getHours().equals(oldRequest.getHours()))) {
            List<RequestForIncreasingTimeLight> listOfRequests = (List<RequestForIncreasingTimeLight>) XmlUtils.getRecords("Requests");
            RequestForIncreasingTimeLight tmp = null;
            for (RequestForIncreasingTimeLight request : listOfRequests) {
                if (request.requestId.equals(newRequest.getRequestId())) {
                    tmp = request;
                    break;
                }
            }
            listOfRequests.remove(tmp);
            RequestForIncreasingTimeLight requestLight = new RequestForIncreasingTimeLight(newRequest);
            listOfRequests.add(requestLight);
            // rewrite requests with new data
            XmlUtils.writeInFile(listOfRequests, "Requests");
        }
    }

    /**
     * Method update DoTask object only if date begin or date end were changed.
     * Can't change ID of object.
     * @param obj new updated DoTask.
     * @throws UnknownObjectType if param of method will be unknown object.
     */
    public static void updateDoTask(Object obj) throws UnknownObjectType {
        DoTask newDoTask = (DoTask) obj;
        DoTask oldDoTask = (DoTask) dao.read(newDoTask.getDoTaskId());
        if ((!newDoTask.getDateBegin().equals(oldDoTask.getDateBegin())) || (!newDoTask.getDateEnd().equals(oldDoTask.getDateEnd()))) {
            List<DoTasksLight> listOfDoTask = (List<DoTasksLight>) XmlUtils.getRecords("DoTasks");
            DoTasksLight tmp = null;
            for (DoTasksLight doTask : listOfDoTask) {
                if (doTask.doTaskId.equals(newDoTask.getDoTaskId())) {
                    tmp = doTask;
                    break;
                }
            }
            listOfDoTask.remove(tmp);
            DoTasksLight doTasksLight = new DoTasksLight(newDoTask);
            listOfDoTask.add(doTasksLight);
            // rewrite doTasks with new data
            XmlUtils.writeInFile(listOfDoTask, "DoTasks");
        }
    }
}
