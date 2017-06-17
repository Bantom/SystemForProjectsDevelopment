package com.bulgakov.model;

import java.util.Calendar;

/**
 * @author Bulgakov
 * @since 13.02.2017
 */
public class Sprint {
    private Integer sprintId;
    private String name;
    private Double estimate;
    private String description;
    private Project project;
    private Calendar dateBegin;
    private Calendar dateEnd;

    public Sprint(Integer sprintId, String name, Double estimate, String description, Project project, Calendar dateBegin, Calendar dateEnd) {
        this.sprintId = sprintId;
        this.name = name;
        this.estimate = estimate;
        this.description = description;
        this.project = project;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }

    public Sprint(Integer sprintId, String name, Double estimate, String description, Project project, Calendar dateBegin) {
        this.sprintId = sprintId;
        this.name = name;
        this.estimate = estimate;
        this.description = description;
        this.project = project;
        this.dateBegin = dateBegin;
    }

    public Sprint(Integer sprintId, String name, Project project) {

        this.sprintId = sprintId;
        this.name = name;
        this.project = project;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEstimate(Double estimate) {
        this.estimate = estimate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateBegin(Calendar dateBegin) {
        this.dateBegin = dateBegin;
    }

    public void setDateEnd(Calendar dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Integer getSprintId() {

        return sprintId;
    }

    public String getName() {
        return name;
    }

    public Double getEstimate() {
        return estimate;
    }

    public String getDescription() {
        return description;
    }

    public Project getProject() {
        return project;
    }

    public Calendar getDateBegin() {
        return dateBegin;
    }

    public Calendar getDateEnd() {
        return dateEnd;
    }

    public Sprint() {}

    public void setSprintId(Integer sprintId) {

        this.sprintId = sprintId;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
