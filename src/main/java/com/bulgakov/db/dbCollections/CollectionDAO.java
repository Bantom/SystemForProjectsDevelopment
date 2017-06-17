package com.bulgakov.db.dbCollections;

import com.bulgakov.db.CRUD;
import com.bulgakov.exceptions.UnknownObjectType;
import com.bulgakov.model.*;

import java.util.*;

import static com.bulgakov.db.dbCollections.CollectionDeleteMethods.*;
import static com.bulgakov.db.dbCollections.CollectionInsertMethods.*;
import static com.bulgakov.db.dbCollections.CollectionReadMethods.*;
import static com.bulgakov.db.dbCollections.CollectionUtils.*;
import static com.bulgakov.db.dbCollections.CollectionsDB.*;

/**
 * @author Bulgakov
 * @since 13.02.2017
 */
public class CollectionDAO implements CRUD {

    @Override
    public synchronized void create(Object obj) throws UnknownObjectType {
        if (obj instanceof Customer) {
            Customer customerTmp = (Customer) obj;
            if (!checkForExistingId(customerTmp.getPersonId())) {
                insertCustomer(customerTmp);
            }
        } else if (obj instanceof User) {
            User userTmp = (User) obj;
            if (!checkForExistingId(userTmp.getPersonId())) {
                insertUser(userTmp);
            }
        } else if (obj instanceof Project) {
            Project projectTmp = (Project) obj;
            if (!checkForExistingId(projectTmp.getProjectId())) {
                insertProject(projectTmp);
            }
        } else if (obj instanceof Sprint) {
            Sprint sprintTmp = (Sprint) obj;
            if (!checkForExistingId(sprintTmp.getSprintId())) {
                insertSprint(sprintTmp);
            }
        } else if (obj instanceof Task) {
            Task taskTmp = (Task) obj;
            if (!checkForExistingId(taskTmp.getTaskId())) {
                insertTask(taskTmp);
            }
        } else if (obj instanceof RequestForIncreasingTime) {
            RequestForIncreasingTime requestTmp = (RequestForIncreasingTime) obj;
            if (!checkForExistingId(requestTmp.getRequestId())) {
                insertRequestForIncreasingTime(requestTmp);
            }
        } else if (obj instanceof DoTask) {
            DoTask doTaskTmp = (DoTask) obj;
            if (!checkForExistingId(doTaskTmp.getDoTaskId())) {
                insertDoTask(doTaskTmp);
            }
        } else {
            throw new UnknownObjectType();
        }
    }

    @Override
    public Object read(Integer id) {
        if (!checkForExistingId(id)) {
            throw new NoSuchElementException("No such object in DB.");
        }
        String objType = objectsType.get(id);
        switch (objType) {
            case "Customer":
                return readCustomer(id);
            case "User":
                return readUser(id);
            case "Project":
                return readProject(id);
            case "Sprint":
                return readSprint(id);
            case "Task":
                return readTask(id);
            case "RequestForIncreasingTime":
                return readRequest(id);
            case "DoTask":
                return readDoTask(id);
        }
        return null;
    }

    @Override
    public synchronized void update(Object obj) throws UnknownObjectType {
        if (obj instanceof Customer) {
            Customer customerTmp = (Customer) obj;
            insertCustomer(customerTmp);
        } else if (obj instanceof User) {
            User userTmp = (User) obj;
            insertUser(userTmp);
        } else if (obj instanceof Project) {
            Project projectTmp = (Project) obj;
            insertProject(projectTmp);
        } else if (obj instanceof Sprint) {
            Sprint sprintTmp = (Sprint) obj;
            insertSprint(sprintTmp);
        } else if (obj instanceof Task) {
            Task taskTmp = (Task) obj;
            insertTask(taskTmp);
        } else if (obj instanceof RequestForIncreasingTime) {
            RequestForIncreasingTime requestTmp = (RequestForIncreasingTime) obj;
            insertRequestForIncreasingTime(requestTmp);
        } else if (obj instanceof DoTask) {
            DoTask doTaskTmp = (DoTask) obj;
            insertDoTask(doTaskTmp);
        } else {
            throw new UnknownObjectType();
        }
    }

    @Override
    public synchronized void delete(Integer id) throws UnknownObjectType {
        if (!checkForExistingId(id)) {
            throw new NoSuchElementException("No such object in DB.");
        }
        String objType = objectsType.get(id);
        switch (objType) {
            case "Customer":
                deleteCustomer(id);
                break;
            case "User":
                deleteUser(id);
                break;
            case "Project":
                deleteProject(id);
                break;
            case "Sprint":
                deleteSprint(id);
                break;
            case "Task":
                deleteTask(id);
                break;
            case "RequestForIncreasingTime":
                deleteRequest(id);
                break;
            case "DoTask":
                deleteDoTask(id);
                break;
        }
    }
}
