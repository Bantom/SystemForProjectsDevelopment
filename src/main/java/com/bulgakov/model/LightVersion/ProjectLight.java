package com.bulgakov.model.LightVersion;

import com.bulgakov.model.Project;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Bulgakov
 * @since 21.02.2017
 */
public class ProjectLight implements Serializable{
    public Integer projectId;
    public String name;
    public Calendar dateBegin;
    public Calendar dateEnd;
    public Double price;
    public String description;
    public Integer customerId;

    public ProjectLight(Project project) {
        this.projectId = project.getProjectId();
        this.name = project.getName();
        this.dateBegin = project.getDateBegin();
        this.dateEnd = project.getDateEnd();
        this.price = project.getPrice();
        this.description = project.getDescription();
        this.customerId = project.getCustomer().getPersonId();
    }
}
