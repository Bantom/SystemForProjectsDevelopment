package com.bulgakov.db.dbJson;

import com.bulgakov.exceptions.UnknownObjectType;
import com.bulgakov.model.*;
import com.bulgakov.model.LightVersion.*;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Bulgakov
 * @since 23.02.2017
 */
public class JsonUtils {

    /**
     * Method checks for existing object with such param name and param value in file.
     *
     * @param fileName file name.
     * @param paramName param name.
     * @param value value.
     * @return status of searching.
     */
    private static Boolean checkForExistingObject(String fileName, String paramName, Object value) {
        List<String> objects = getRecords(fileName);
        for (String iter : objects) {
            if (value instanceof Integer) {
                if (iter.contains("\"" + paramName + "\":" + value)) {
                    return true;
                }
            }else {
                if (iter.contains("\"" + paramName + "\":\"" + value)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method uploads ids of all objects and compare each with id from params.
     * If this id is, the method returns true, else false.
     *
     * @param id id to check.
     * @return status of searching.
     */
    public static Boolean checkForExistingId(Integer id) {
        return checkForExistingObject("IdTypes", "id", id);
    }

    /**
     * Method returns True if there is Person with such email and False if no a person with such email.
     * If person is User, it also checks existing boss in database.
     *
     * @param person Person is being checked.
     * @return Status of searching.
     */
    public static Boolean checkForExistingPerson(Person person) {
        Boolean existingCustomer = checkForExistingObject("Customers", "email", person.getEmail());
        Boolean existingUser = checkForExistingObject("Users", "email", person.getEmail());
        if (existingCustomer == true || existingUser == true) {
            return true;
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
     * Method checks customer of project. If customer exists, it returns true else False.
     *
     * @param project project for checking.
     * @return status of checking.
     */
    public static Boolean checkForExistingProject(Project project) {
        Boolean existingCustomer = checkForExistingObject("Customers","personId", project.getCustomer().getPersonId());
        Boolean existingProject = true;
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
        Boolean existingProject = checkForExistingObject("Projects", "projectId", sprint.getProject().getProjectId());
        Boolean existingSprint = true;
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
            if (!checkObjectType(task.getSprint().getSprintId(), "Sprint")) {
                throw new NoSuchElementException("No such sprint in DB.");
            }
        }
        if (task.getParentTask() != null) {
            if (!checkForExistingId(task.getParentTask().getTaskId())) {
                throw new NoSuchElementException("No such parent task in DB.");
            }
            if (!checkObjectType(task.getParentTask().getTaskId(), "Task")) {
                throw new NoSuchElementException("No such parent task in DB.");
            }
        }
        return false;
    }

    /**
     * Method checks for conformity data from params to data from DB.
     *
     * @param id   Object ID.
     * @param type Object type.
     * @return True if date from params and DB are same, False - different.
     */
    public static Boolean checkObjectType(Integer id, String type) {
        List<String> objects = getRecords("IdTypes");
        for (String iter : objects) {
            if (iter.contains("\"id\":" + id)) {
                if (iter.contains("\"type\":\"" + type + "\"")) {
                    return true;
                }
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
        if (!checkObjectType(request.getTask().getTaskId(), "Task")) {
            throw new NoSuchElementException("No such task in DB.");
        }
        if (!checkForExistingId(request.getUser().getPersonId())) {
            throw new NoSuchElementException("No such user in DB.");
        }
        if (!checkObjectType(request.getUser().getPersonId(), "User")) {
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
        if (!checkObjectType(doTask.getTask().getTaskId(), "Task")) {
            throw new NoSuchElementException("No such task in DB.");
        }
        if (!checkForExistingId(doTask.getUser().getPersonId())) {
            throw new NoSuchElementException("No such user in DB.");
        }
        if (!checkObjectType(doTask.getUser().getPersonId(), "User")) {
            throw new NoSuchElementException("No such user in DB.");
        }
        return false;
    }

    /**
     * Method reads all objects from file with name from params and save it in list of strings.
     *
     * @param fileName file name.
     * @return list of strings - objects in JSON format.
     */
    public static List<String> getRecords(String fileName) {
        List<String> listOfObjects = new ArrayList<>();
        try (FileReader reader = new FileReader("src/main/resources/DBfiles/Json/" + fileName + ".txt")) {
            int c;
            StringBuilder stringBuilder = new StringBuilder();
            while ((c = reader.read()) != -1) {
                if ((char) c == '\n') {
                    listOfObjects.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                } else {
                    stringBuilder.append((char) c);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return listOfObjects;
    }

    /**
     * Method creates one string from list of strings from params. List contains objects in JSON form.
     * All objects are separated from each other by a symbol '\n'. This file is overwritten.
     *
     * @param records  list of records in JSON form.
     * @param fileName name of output file.
     */
    public static void writeListOfRecords(List<String> records, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String record : records) {
            stringBuilder.append(record);
            stringBuilder.append('\n');
        }
        try (FileWriter writer = new FileWriter("src/main/resources/DBfiles/Json/" + fileName + ".txt", false)) {
            writer.write(stringBuilder.toString());
            writer.append('\n');
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Method writes object in file with name from param.
     *
     * @param obj      object is saved in file.
     * @param fileName file name.
     */
    public static void writeInFile(Object obj, String fileName) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(obj);
        try (FileWriter writer = new FileWriter("src/main/resources/DBfiles/Json/" + fileName + ".txt", true)) {
            writer.write(jsonString);
            writer.append('\n');
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Method returns object with all relations using light version from params.
     *
     * @param obj light version of object.
     * @return object with all relations.
     * @throws UnknownObjectType if object from params is unknown.
     */
    public static Object getFullObjectVersion(Object obj) throws UnknownObjectType {
        JsonDAO dbJson = new JsonDAO();
        if (obj instanceof CustomerLight) {
            CustomerLight customerLight = (CustomerLight) obj;
            Customer customer = new Customer(customerLight.personId, customerLight.name, customerLight.invoice, customerLight.email, customerLight.password);
            return customer;
        }
        if (obj instanceof UserLight) {
            UserLight userLight = (UserLight) obj;
            if (userLight.bossId == null) {
                User user = new User(userLight.personId, userLight.email, userLight.password, userLight.name, userLight.surname, userLight.secondName, userLight.identityCode, userLight.qualification, userLight.role);
                return user;
            } else {
                User boss = (User) dbJson.read(userLight.bossId);
                User user = new User(userLight.personId, userLight.email, userLight.password, userLight.name, userLight.surname, userLight.secondName, userLight.identityCode, userLight.qualification, userLight.role, boss);
                return user;
            }
        }
        if (obj instanceof ProjectLight) {
            ProjectLight projectLight = (ProjectLight) obj;
            Customer customer = (Customer) dbJson.read(projectLight.customerId);
            Project project = new Project(projectLight.projectId, projectLight.name, projectLight.price, projectLight.description, customer);
            return project;
        }
        if (obj instanceof SprintLight) {
            SprintLight sprintLight = (SprintLight) obj;
            Project project = (Project) dbJson.read(sprintLight.projectId);
            Sprint sprint = new Sprint(sprintLight.sprintId, sprintLight.name, sprintLight.estimate, sprintLight.description, project, sprintLight.dateBegin, sprintLight.dateEnd);
            return sprint;
        }
        if (obj instanceof TaskLight) {
            TaskLight taskLight = (TaskLight) obj;
            Task task = new Task(taskLight.taskId, taskLight.estimate, taskLight.qualification, taskLight.description);
            if (taskLight.parentTaskId != null) {
                Task parentTask = (Task) dbJson.read(taskLight.parentTaskId);
                if (parentTask.getSprint() != null) {
                    task.setSprint(parentTask.getSprint());
                }
            } else {
                if (taskLight.sprintId != null) {
                    Sprint sprint = (Sprint) dbJson.read(taskLight.sprintId);
                    task.setSprint(sprint);
                }
            }
            if (!taskLight.dependsOnTasksId.isEmpty()) {
                List<Task> dependsOnTask = new ArrayList<Task>();
                for (Integer idTask : taskLight.dependsOnTasksId) {
                    Task tmp = (Task) dbJson.read(idTask);
                    dependsOnTask.add(tmp);
                }
                task.setDependsOnTasks(dependsOnTask);
            }
            return task;
        }
        if (obj instanceof RequestForIncreasingTimeLight) {
            RequestForIncreasingTimeLight requestLight = (RequestForIncreasingTimeLight) obj;
            Task task = (Task) dbJson.read(requestLight.taskId);
            User user = (User) dbJson.read(requestLight.userId);
            RequestForIncreasingTime request = new RequestForIncreasingTime(requestLight.requestId, requestLight.currentDate, requestLight.description, requestLight.hours, task, user);
            return request;
        }
        if (obj instanceof DoTasksLight) {
            DoTasksLight doTasksLight = (DoTasksLight) obj;
            Task task = (Task) dbJson.read(doTasksLight.taskId);
            User user = (User) dbJson.read(doTasksLight.userId);
            DoTask doTask = new DoTask(doTasksLight.doTaskId, task, user, doTasksLight.dateBegin, doTasksLight.dateEnd);
            return doTask;
        }
        throw new UnknownObjectType();
    }

    /**
     * Method returns type of object with ID from params.
     *
     * @param id ID of object.
     * @return type of object.
     */
    public static String getObjectType(Integer id) {
        String objectType = null;
        List<String> idType = getRecords("IdTypes");
        for (String idTypeTmp : idType) {
            if (idTypeTmp.contains("\"id\":" + id)) {
                if (idTypeTmp.contains("\"type\":\"Customer\"")) {
                    objectType = "Customer";
                }
                if (idTypeTmp.contains("\"type\":\"User\"")) {
                    objectType = "User";
                }
                if (idTypeTmp.contains("\"type\":\"Project\"")) {
                    objectType = "Project";
                }
                if (idTypeTmp.contains("\"type\":\"Sprint\"")) {
                    objectType = "Sprint";
                }
                if (idTypeTmp.contains("\"type\":\"Task\"")) {
                    objectType = "Task";
                }
                if (idTypeTmp.contains("\"type\":\"Request\"")) {
                    objectType = "Request";
                }
            }
        }
        return objectType;
    }

    /**
     * Method deletes info in file IdTypes about object was deleted.
     *
     * @param id ID of the object that was deleted.
     */
    public static void deleteRecordFromIdTypes(Integer id) {
        List<String> idTypes = getRecords("IdTypes");
        String tmp = null;
        for (String idType : idTypes) {
            if (idType.contains("\"id\":" + id)) {
                tmp = idType;
            }
        }
        idTypes.remove(tmp);
        writeListOfRecords(idTypes, "IdTypes");
    }
}
