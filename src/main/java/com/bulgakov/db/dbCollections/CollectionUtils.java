package com.bulgakov.db.dbCollections;

import com.bulgakov.model.*;

import java.util.Map;
import java.util.NoSuchElementException;

import static com.bulgakov.db.dbCollections.CollectionsDB.*;

/**
 * @author Bulgakov
 * @since 19.02.2017
 */
public class CollectionUtils {

    /**
     * Method returns True if no Person with such email and False if there is a person with such email.
     * If person is User, it also checks existing boss in database.
     *
     * @param person Person is being checked.
     * @return Status of searching.
     */
    public static Boolean checkForExistingPerson(Person person) {
        for (Map.Entry<Integer, User> entry : users.entrySet()) {
            if (entry.getValue().getEmail().equals(person.getEmail())) {
                return true;
            }
        }
        for (Map.Entry<Integer, Customer> entry : customers.entrySet()) {
            if (entry.getValue().getEmail().equals(person.getEmail())) {
                return true;
            }
        }
        if (person instanceof User) {
            User userTmp = (User) person;
            if (!checkForExistingId(userTmp.getBoss().getPersonId())) {
                throw new NoSuchElementException("No boss with such id.");
            }
        }
        return false;
    }

    /**
     * Method check customer of project. If customer exists, it returns true, else False.
     *
     * @param project project for checking.
     * @return status of checking.
     */
    public static Boolean checkForExistingProject(Project project) {
        Boolean existingCustomer = false;
        Boolean existingProject = true;
        for (Map.Entry<Integer, Customer> entry : customers.entrySet()) {
            if (entry.getKey() == project.getCustomer().getPersonId()) {
                existingCustomer = true;
            }
        }
        if (existingCustomer) {
            existingProject = false;
        }
        return existingProject;
    }

    /**
     * Method checks sprint. If project of this sprint exists in DB, it returns true else false.
     *
     * @param sprint sprint for checking.
     * @return status of checking.
     */
    public static Boolean checkForExistingSprint(Sprint sprint) {
        Boolean existingProject = false;
        Boolean existingSprint = true;
        for (Map.Entry<Integer, Project> entry : projects.entrySet()) {
            if (entry.getKey() == sprint.getProject().getProjectId()) {
                existingProject = true;
            }
        }
        if (existingProject) {
            existingSprint = false;
        }
        return existingSprint;
    }

    /**
     * Method checks task. If sprint of task and parent task of task are exist in DB, it returns true else false.
     *
     * @param task task for checking.
     * @return status of checking.
     */
    public static Boolean checkForExistingTask(Task task) {
        if (task.getSprint() != null) {
            if (!checkForExistingId(task.getSprint().getSprintId())) {
                throw new NoSuchElementException("No such sprint in DB.");
            }
            if (!objectsType.get(task.getSprint().getSprintId()).equals("Sprint")) {
                throw new NoSuchElementException("No such sprint in DB.");
            }
        }
        if (task.getParentTask() != null) {
            if (!checkForExistingId(task.getParentTask().getTaskId())) {
                throw new NoSuchElementException("No such parent task in DB.");
            }
            if (!objectsType.get(task.getParentTask().getTaskId()).equals("Task")) {
                throw new NoSuchElementException("No such parent task in DB.");
            }
        }
        return false;
    }

    /**
     * Method checks request. If task of request and user of request are exist in DB, it returns true else false.
     *
     * @param request request for checking.
     * @return status of checking.
     */
    public static Boolean checkForExistingRequestForIncreasingTime(RequestForIncreasingTime request) {
        if (!checkForExistingId(request.getTask().getTaskId())) {
            throw new NoSuchElementException("No such task in DB.");
        }
        if (!objectsType.get(request.getTask().getTaskId()).equals("Task")) {
            throw new NoSuchElementException("No such task in DB.");
        }
        if (!checkForExistingId(request.getUser().getPersonId())) {
            throw new NoSuchElementException("No such user in DB.");
        }
        if (!objectsType.get(request.getUser().getPersonId()).equals("User")) {
            throw new NoSuchElementException("No such user in DB.");
        }
        return false;
    }

    /**
     * Method checks doTask object. If task of doTask and user of doTask are exist in DB, it returns true else false.
     *
     * @param doTask doTask for checking.
     * @return status of checking.
     */
    public static Boolean checkForExistingDoTask(DoTask doTask) {
        if (!checkForExistingId(doTask.getTask().getTaskId())) {
            throw new NoSuchElementException("No such task in DB.");
        }
        if (!objectsType.get(doTask.getTask().getTaskId()).equals("Task")) {
            throw new NoSuchElementException("No such task in DB.");
        }
        if (!checkForExistingId(doTask.getUser().getPersonId())) {
            throw new NoSuchElementException("No such user in DB.");
        }
        if (!objectsType.get(doTask.getUser().getPersonId()).equals("User")) {
            throw new NoSuchElementException("No such user in DB.");
        }
        return false;
    }

    /**
     * Method check records on uniqueness.
     *
     * @param id ID which will be checked on the uniqueness.
     * @return True - if object exists, False - if object with such id doesn't exist.
     */
    public static boolean checkForExistingId(Integer id) {
        Boolean exist = false;
        if (objectsType.containsKey(id)) {
            exist = true;
        }
        return exist;
    }
}
