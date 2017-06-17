package com.bulgakov.model;

import java.util.Calendar;

/**
 * @author Bulgakov
 * @since 13.02.2017
 */
public class RequestForIncreasingTime {
    private Integer requestId;
    private Calendar currentDate;
    private String description;
    private Double hours;
    private Task task;
    private User user;

    public RequestForIncreasingTime() {
    }

    public RequestForIncreasingTime(Integer requestId, Calendar currentDate, String description, Double hours, Task task, User user) {
        this.requestId = requestId;
        this.currentDate = currentDate;
        this.description = description;
        this.hours = hours;
        this.task = task;
        this.user = user;
    }

    public RequestForIncreasingTime(Integer requestId, String description, Double hours, User user, Task task) {
        this.requestId = requestId;
        this.description = description;
        this.hours = hours;
        this.user = user;
        this.task = task;
        this.currentDate = Calendar.getInstance();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public Integer getRequestId() {

        return requestId;
    }

    public Calendar getCurrentDate() {
        return currentDate;
    }

    public String getDescription() {
        return description;
    }

    public Double getHours() {
        return hours;
    }

    public User getUser() {
        return user;
    }

    public Task getTask() {
        return task;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public void setCurrentDate(Calendar currentDate) {
        this.currentDate = currentDate;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
