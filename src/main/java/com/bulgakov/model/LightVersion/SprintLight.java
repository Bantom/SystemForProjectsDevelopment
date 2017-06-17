package com.bulgakov.model.LightVersion;

import com.bulgakov.model.Sprint;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Bulgakov
 * @since 21.02.2017
 */
public class SprintLight implements Serializable{
    public Integer sprintId;
    public String name;
    public Double estimate;
    public String description;
    public Integer projectId;
    public Calendar dateBegin;
    public Calendar dateEnd;

    public SprintLight(Sprint sprint) {
        this.sprintId = sprint.getSprintId();
        this.name = sprint.getName();
        this.estimate = sprint.getEstimate();
        this.description = sprint.getDescription();
        this.projectId = sprint.getProject().getProjectId();
        this.dateBegin = sprint.getDateBegin();
        this.dateEnd = sprint.getDateEnd();
    }
}
