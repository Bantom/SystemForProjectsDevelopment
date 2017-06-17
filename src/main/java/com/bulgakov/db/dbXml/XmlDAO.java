package com.bulgakov.db.dbXml;

import com.bulgakov.db.CRUD;
import com.bulgakov.exceptions.UnknownObjectType;
import com.bulgakov.model.*;

import static com.bulgakov.db.dbXml.XmlInsertMethods.*;
import static com.bulgakov.db.dbXml.XmlReadMethods.*;
import static com.bulgakov.db.dbXml.XmlUpdateMethods.*;

/**
 * @author Bulgakov
 * @since 13.02.2017
 */
public class XmlDAO implements CRUD {

    @Override
    public synchronized void create(Object obj) throws UnknownObjectType {
        if (obj instanceof Customer) {
            Customer customerTmp = (Customer) obj;
            if (!XmlUtils.checkForExistingId(customerTmp.getPersonId())) {
                insertCustomer(customerTmp);
            }
        } else if (obj instanceof User) {
            User userTmp = (User) obj;
            if (!XmlUtils.checkForExistingId(userTmp.getPersonId())) {
                insertUser(userTmp);
            }
        } else if (obj instanceof Project) {
            Project projectTmp = (Project) obj;
            if (!XmlUtils.checkForExistingId(projectTmp.getProjectId())) {
                insertProject(projectTmp);
            }
        } else if (obj instanceof Sprint) {
            Sprint sprintTmp = (Sprint) obj;
            if (!XmlUtils.checkForExistingId(sprintTmp.getSprintId())) {
                insertSprint(sprintTmp);
            }
        } else if (obj instanceof Task) {
            Task taskTmp = (Task) obj;
            if (!XmlUtils.checkForExistingId(taskTmp.getTaskId())) {
                insertTask(taskTmp);
            }
        } else if (obj instanceof RequestForIncreasingTime) {
            RequestForIncreasingTime requestTmp = (RequestForIncreasingTime) obj;
            if (!XmlUtils.checkForExistingId(requestTmp.getRequestId())) {
                insertRequestForIncreasingTime(requestTmp);
            }
        } else if (obj instanceof DoTask) {
            DoTask doTaskTmp = (DoTask) obj;
            if (!XmlUtils.checkForExistingId(doTaskTmp.getDoTaskId())) {
                insertDoTask(doTaskTmp);
            }
        } else {
            throw new UnknownObjectType();
        }
    }

    @Override
    public Object read(Integer id) throws UnknownObjectType {
        String objectType = XmlUtils.getObjectType(id);
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
            updateCustomer(obj);
        } else if (obj instanceof User) {
            updateUser(obj);
        } else if (obj instanceof Project) {
            updateProject(obj);
        } else if (obj instanceof Sprint) {
            updateSprint(obj);
        } else if (obj instanceof Task) {
            updateTask(obj);
        } else if (obj instanceof RequestForIncreasingTime) {
            updateRequest(obj);
        } else if (obj instanceof DoTask) {
            updateDoTask(obj);
        } else {
            throw new UnknownObjectType();
        }
    }

    @Override
    public synchronized void delete(Integer id) throws UnknownObjectType {
        String objectType = XmlUtils.getObjectType(id);
        switch (objectType) {
            case "Customer":
                XmlDeleteMethods.deleteCustomer(id);
                break;
            case "User":
                XmlDeleteMethods.deleteUser(id);
                break;
            case "Project":
                XmlDeleteMethods.deleteProject(id);
                break;
            case "Sprint":
                XmlDeleteMethods.deleteSprint(id);
                break;
            case "Task":
                XmlDeleteMethods.deleteTask(id);
                break;
            case "Request":
                XmlDeleteMethods.deleteRequest(id);
                break;
            case "DoTask":
                XmlDeleteMethods.deleteDoTask(id);
                break;
        }
    }
}
