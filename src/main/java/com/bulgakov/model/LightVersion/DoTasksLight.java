package com.bulgakov.model.LightVersion;

import com.bulgakov.model.DoTask;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Bulgakov
 * @since 21.02.2017
 */
public class DoTasksLight implements Serializable{
    public Integer doTaskId;
    public Integer taskId;
    public Integer userId;
    public Calendar dateBegin;
    public Calendar dateEnd;

    public DoTasksLight(DoTask doTask) {
        this.doTaskId = doTask.getDoTaskId();
        this.taskId = doTask.getTask().getTaskId();
        this.userId = doTask.getUser().getPersonId();
        this.dateBegin = doTask.getDateBegin();
        this.dateEnd = doTask.getDateEnd();
    }
}
