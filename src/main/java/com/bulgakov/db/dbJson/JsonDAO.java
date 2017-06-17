package com.bulgakov.db.dbJson;

import com.bulgakov.db.CRUD;
import com.bulgakov.exceptions.UnknownObjectType;
import com.bulgakov.model.*;

import static com.bulgakov.db.dbJson.JsonDeleteMethods.*;
import static com.bulgakov.db.dbJson.JsonInsertMethods.*;
import static com.bulgakov.db.dbJson.JsonReadMethods.*;
import static com.bulgakov.db.dbJson.JsonUtils.*;

/**
 * @author Bulgakov
 * @since 13.02.2017
 */
public class JsonDAO implements CRUD {

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
    public Object read(Integer id) throws UnknownObjectType {
        String objectType = getObjectType(id);
        switch (objectType) {
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
            case "Request":
                return readRequest(id);
            case "DoTask":
                return readDoTask(id);
            default:
                return null;
        }
    }

    @Override
    public synchronized void update(Object obj) throws UnknownObjectType {
        if (obj instanceof Customer) {
            JsonUpdateMethods.updateCustomer(obj);
        } else if (obj instanceof User) {
            JsonUpdateMethods.updateUser(obj);
        } else if (obj instanceof Project) {
            JsonUpdateMethods.updateProject(obj);
        } else if (obj instanceof Sprint) {
            JsonUpdateMethods.updateSprint(obj);
        } else if (obj instanceof Task) {
            JsonUpdateMethods.updateTask(obj);
        } else if (obj instanceof RequestForIncreasingTime) {
            JsonUpdateMethods.updateRequest(obj);
        } else if (obj instanceof DoTask) {
            JsonUpdateMethods.updateDoTask(obj);
        } else {
            throw new UnknownObjectType();
        }
    }

    @Override
    public synchronized void delete(Integer id) throws UnknownObjectType {
        String objectType = getObjectType(id);
        switch (objectType) {
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
            case "Request":
                deleteRequest(id);
                break;
            case "DoTask":
                deleteDoTask(id);
                break;
        }
    }
}
