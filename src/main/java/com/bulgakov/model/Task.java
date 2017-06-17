package com.bulgakov.model;

import java.util.List;

/**
 * @author Bulgakov
 * @since 13.02.2017
 */
public class Task {
    private Integer taskId;
    private Task parentTask;
    private Double estimate;
    private String qualification;
    private String status = "open";
    private String description;
    private Sprint sprint;
    private List<Task> dependsOnTasks;

    public Task() {
    }

    public Task(Integer taskId, Double estimate, String qualification, String description) {
        this.taskId = taskId;
        this.estimate = estimate;
        this.qualification = qualification;
        this.description = description;
    }

    public Task(Integer taskId, Double estimate, String qualification, String description, Sprint sprint) {

        this.taskId = taskId;
        this.parentTask = parentTask;
        this.estimate = estimate;
        this.qualification = qualification;
        this.description = description;
        this.sprint = sprint;
    }

    public void addToDependsOnTasksList(Task task){
        if(task == null){
            throw new NullPointerException("Null instead of task.");
        }
        if(!dependsOnTasks.contains(task)) {
            dependsOnTasks.add(task);
        }
    }

    public void removeFromDependsOnTasksList(Task task){
        if(task == null){
            throw new NullPointerException("Null instead of task.");
        }
        if(dependsOnTasks.contains(task)){
            dependsOnTasks.remove(task);
            if(task.getParentTask() == this){
                task.setParentTask(null);
            }
        }
    }

    public void setParentTask(Task parentTask) {
        this.parentTask = parentTask;
        if(parentTask != null) {
            parentTask.addToDependsOnTasksList(this);
        }
    }

    public void setEstimate(Double estimate) {
        this.estimate = estimate;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public Integer getTaskId() {

        return taskId;
    }

    public Task getParentTask() {
        return parentTask;
    }

    public Double getEstimate() {
        return estimate;
    }

    public String getQualification() {
        return qualification;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public List<Task> getDependsOnTasks() {
        return dependsOnTasks;
    }

    public void setDependsOnTasks(List<Task> dependsOnTasks) {
        this.dependsOnTasks = dependsOnTasks;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
}
