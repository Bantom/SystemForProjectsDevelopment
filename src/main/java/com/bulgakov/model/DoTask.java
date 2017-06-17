package com.bulgakov.model;

import java.util.Calendar;

/**
 * @author Bulgakov
 * @since 13.02.2017
 */
public class DoTask {
    private Integer doTaskId;
    private Task task;
    private User user;
    private Calendar dateBegin;
    private Calendar dateEnd;

    public DoTask(Integer doTaskId, Task task, User user, Calendar dateBegin, Calendar dateEnd) {
        this.doTaskId = doTaskId;
        this.task = task;
        this.user = user;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }

    public DoTask(Integer doTaskId, Task task, User user) {
        this.doTaskId = doTaskId;
        this.task = task;
        this.user = user;
    }

    public void setDoTaskId(Integer doTaskId) {
        this.doTaskId = doTaskId;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DoTask() {

    }

    public Integer getDoTaskId() {
        return doTaskId;
    }

    public void setDateBegin(Calendar dateBegin) {
        this.dateBegin = dateBegin;
    }

    public void setDateEnd(Calendar dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Task getTask() {
        return task;
    }

    public User getUser() {
        return user;
    }

    public Calendar getDateBegin() {
        return dateBegin;
    }

    public Calendar getDateEnd() {
        return dateEnd;
    }
}
