package com.bulgakov.db.dbSerializable;

import com.bulgakov.exceptions.UnknownObjectType;
import com.bulgakov.model.*;
import com.bulgakov.model.LightVersion.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Bulgakov
 * @since 04.03.2017
 */
public class SerializableUtils {
    /**
     * Method writes object in file with name from param.
     *
     * @param obj      object is saved in file.
     * @param fileName file name.
     */
    public static void writeInFile(Object obj, String fileName) {

        try (FileOutputStream fos = new FileOutputStream("src/main/resources/DBfiles/Serializable/" + fileName + ".txt")) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.flush();
            oos.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Method returns all objects from file with name from params.
     *
     * @param fileName file name.
     * @return objects from file.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object getRecords(String fileName){
        FileInputStream fis = null;
        Object objects = null;
        try {
            fis = new FileInputStream("src/main/resources/DBfiles/Serializable/" + fileName + ".txt");
            ObjectInputStream oin = new ObjectInputStream(fis);
            objects = oin.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objects;
    }

    /**
     * Method uploads ids of all objects and compare each with id from params.
     * If this id is, the method returns true, else false.
     *
     * @param id id to check.
     * @return status of searching.
     */
    public static Boolean checkForExistingId(Integer id){
        List<IdTypes> listIdTypes = (List<IdTypes>) getRecords("IdTypes");
         for (IdTypes idType : listIdTypes) {
             if (idType.id.equals(id)) {
                 return true;
             }
         }
         return false;
    }

    /**
     * Method returns True if no Person with such email and False if there is a person with such email.
     * If person is User, it also checks existing boss in database.
     *
     * @param person Person is being checked.
     * @return Status of searching.
     */
    public static Boolean checkForExistingPerson(Person person) {
        List<CustomerLight> listCustomers = (List<CustomerLight>) getRecords("Customers");
        List<UserLight> listUsers = (List<UserLight>) getRecords("Users");
        for (CustomerLight customer : listCustomers) {
            if (customer.email.equals(person.getEmail())) {
                return true;
            }
        }
        for (UserLight user : listUsers) {
            if (user.email.equals(person.getEmail())) {
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
     * Method checks customer of project. If customer exists, it returns true else False.
     *
     * @param project project for checking.
     * @return status of checking.
     */
    public static Boolean checkForExistingProject(Project project) {
        Boolean existingCustomer = false;
        Boolean existingProject = true;
        List<CustomerLight> listOfCustomers = (List<CustomerLight>) getRecords("Customers");
        for (CustomerLight customer : listOfCustomers) {
            if (customer.personId.equals(project.getCustomer().getPersonId())) {
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
        List<ProjectLight> listOfProjects = (List<ProjectLight>) getRecords("Projects");
        for (ProjectLight project : listOfProjects) {
            if (project.projectId.equals(sprint.getProject().getProjectId())) {
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
     * @param id Object ID.
     * @param type Object type.
     * @return True if date from params and DB are same, False - different.
     */
    public static Boolean checkObjectType(Integer id, String type) {
        List<IdTypes> objects = (List<IdTypes>) getRecords("IdTypes");
        for (IdTypes iter : objects) {
            if (iter.id.equals(id)) {
                if (iter.type.equals(type)) {
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
     * Method returns type of object with ID from params.
     *
     * @param id ID of object.
     * @return type of object.
     */
    public static String getObjectType(Integer id) {
        String objectType = null;
        List<IdTypes> listOfIdTypes = (List<IdTypes>) getRecords("IdTypes");
        for (IdTypes idTypeTmp : listOfIdTypes) {
            if (idTypeTmp.id.equals(id)) {
                objectType = idTypeTmp.type;
            }
        }
        return objectType;
    }

    /**
     * Method returns object with all relations using light version from params.
     *
     * @param obj light version of object.
     * @return object with all relations.
     * @throws UnknownObjectType if object from params is unknown.
     */
    public static Object getFullObjectVersion(Object obj) throws UnknownObjectType {
        SerializableDAO dao = new SerializableDAO();
        // CUSTOMER
        if (obj instanceof CustomerLight) {
            CustomerLight customerLight = (CustomerLight) obj;
            Customer customer = new Customer(customerLight.personId, customerLight.name, customerLight.invoice, customerLight.email, customerLight.password);
            return customer;
        } else
        // USER
        if (obj instanceof UserLight) {
            UserLight userLight = (UserLight) obj;
            if (userLight.bossId == null) {
                User user = new User(userLight.personId, userLight.email, userLight.password, userLight.name, userLight.surname, userLight.secondName, userLight.identityCode, userLight.qualification, userLight.role);
                return user;
            } else {
                User boss = (User) dao.read(userLight.bossId);
                User user = new User(userLight.personId, userLight.email, userLight.password, userLight.name, userLight.surname, userLight.secondName, userLight.identityCode, userLight.qualification, userLight.role, boss);
                return user;
            }
        } else
        // PROJECT
        if (obj instanceof ProjectLight) {
            ProjectLight projectLight = (ProjectLight) obj;
            Customer customer = (Customer) dao.read(projectLight.customerId);
            Project project = new Project(projectLight.projectId, projectLight.name, projectLight.price, projectLight.description, customer);
            return project;
        } else
        // SPRINT
        if (obj instanceof SprintLight) {
            SprintLight sprintLight = (SprintLight) obj;
            Project project = (Project) dao.read(sprintLight.projectId);
            Sprint sprint = new Sprint(sprintLight.sprintId, sprintLight.name, sprintLight.estimate, sprintLight.description, project, sprintLight.dateBegin, sprintLight.dateEnd);
            return sprint;
        } else
        // TASK
        if (obj instanceof TaskLight) {
            TaskLight taskLight = (TaskLight) obj;
            Task task = new Task(taskLight.taskId, taskLight.estimate, taskLight.qualification, taskLight.description);
            if (taskLight.parentTaskId != null) {
                Task parentTask = (Task) dao.read(taskLight.parentTaskId);
                if (parentTask.getSprint() != null){
                    task.setSprint(parentTask.getSprint());
                }
            } else {
                if (taskLight.sprintId != null) {
                    Sprint sprint = (Sprint) dao.read(taskLight.sprintId);
                    task.setSprint(sprint);
                }
            }
            if (!taskLight.dependsOnTasksId.isEmpty()) {
                List<Task> dependsOnTask = new ArrayList<Task>();
                for (Integer idTask : taskLight.dependsOnTasksId) {
                    Task tmp = (Task) dao.read(idTask);
                    dependsOnTask.add(tmp);
                }
                task.setDependsOnTasks(dependsOnTask);
            }
            return task;
        } else
        // REQUEST
        if (obj instanceof RequestForIncreasingTimeLight) {
            RequestForIncreasingTimeLight requestLight = (RequestForIncreasingTimeLight) obj;
            Task task = (Task) dao.read(requestLight.taskId);
            User user = (User) dao.read(requestLight.userId);
            RequestForIncreasingTime request = new RequestForIncreasingTime(requestLight.requestId, requestLight.currentDate, requestLight.description, requestLight.hours, task, user);
            return request;
        } else
        // DOTASK
        if (obj instanceof DoTasksLight) {
            DoTasksLight doTasksLight = (DoTasksLight) obj;
            Task task = (Task) dao.read(doTasksLight.taskId);
            User user = (User) dao.read(doTasksLight.userId);
            DoTask doTask = new DoTask(doTasksLight.doTaskId, task, user, doTasksLight.dateBegin, doTasksLight.dateEnd);
            return doTask;
        } else {
            throw new UnknownObjectType();
        }
    }

    /**
     * Method deletes info in file IdTypes about object was deleted.
     *
     * @param id ID of the object that was deleted.
     */
    public static void deleteRecordFromIdTypes(Integer id) {
        List<IdTypes> idTypes = (List<IdTypes>) getRecords("IdTypes");
        IdTypes idTypesTmp = null;
        for (IdTypes idType : idTypes) {
            if(idType.id.equals(id)){
                idTypesTmp = idType;
            }
        }
        idTypes.remove(idTypesTmp);
        writeInFile(idTypes, "IdTypes");
    }
}
