package com.bulgakov.model.LightVersion;

import com.bulgakov.model.RequestForIncreasingTime;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Bulgakov
 * @since 21.02.2017
 */
public class RequestForIncreasingTimeLight implements Serializable{
    public Integer requestId;
    public Calendar currentDate;
    public String description;
    public Double hours;
    public Integer taskId;
    public Integer userId;

    public RequestForIncreasingTimeLight(RequestForIncreasingTime request) {
        this.requestId = request.getRequestId();
        this.currentDate = request.getCurrentDate();
        this.description = request.getDescription();
        this.hours = request.getHours();
        this.taskId = request.getTask().getTaskId();
        this.userId = request.getUser().getPersonId();
    }
}
