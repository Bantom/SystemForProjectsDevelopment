package com.bulgakov.model.LightVersion;

import com.bulgakov.exceptions.UnknownObjectType;
import com.bulgakov.model.*;

import java.io.Serializable;

/**
 * @author Bulgakov
 * @since 22.02.2017
 */
public class IdTypes implements Serializable{
    public Integer id;
    public String type;

    public IdTypes(Object obj) throws UnknownObjectType {
        if (obj instanceof Customer) {
            Customer customerTmp = (Customer) obj;
            this.id = customerTmp.getPersonId();
            this.type = "Customer";
        } else if (obj instanceof User) {
            User userTmp = (User) obj;
            this.id = userTmp.getPersonId();
            this.type = "User";
        } else if (obj instanceof Project) {
            Project projectTmp = (Project) obj;
            this.id = projectTmp.getProjectId();
            this.type = "Project";
        } else if (obj instanceof Sprint) {
            Sprint sprintTmp = (Sprint) obj;
            this.id = sprintTmp.getSprintId();
            this.type = "Sprint";
        } else if (obj instanceof Task) {
            Task taskTmp = (Task) obj;
            this.id = taskTmp.getTaskId();
            this.type = "Task";
        } else if (obj instanceof RequestForIncreasingTime) {
            RequestForIncreasingTime requestTmp = (RequestForIncreasingTime) obj;
            this.id = requestTmp.getRequestId();
            this.type = "Request";
        } else if (obj instanceof DoTask){
            DoTask doTaskTmp = (DoTask) obj;
            this.id = doTaskTmp.getDoTaskId();
            this.type = "DoTask";
        }else {
            throw new UnknownObjectType();
        }
    }
}
