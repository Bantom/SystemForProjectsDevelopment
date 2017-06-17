package com.bulgakov.model.LightVersion;

import com.bulgakov.model.Task;

import java.io.Serializable;
import java.util.List;

/**
 * @author Bulgakov
 * @since 21.02.2017
 */
public class TaskLight implements Serializable{
    public Integer taskId;
    public Integer parentTaskId;
    public Double estimate;
    public String qualification;
    public String status;
    public String description;
    public Integer sprintId;
    public List<Integer> dependsOnTasksId;

    public TaskLight(Task task) {
        this.taskId = task.getTaskId();
        this.parentTaskId = task.getParentTask().getTaskId();
        this.estimate = task.getEstimate();
        this.qualification = task.getQualification();
        this.status = task.getStatus();
        this.description = task.getDescription();
        this.sprintId = task.getSprint().getSprintId();
        for (Task taskTmp : task.getDependsOnTasks()) {
            this.dependsOnTasksId.add(taskTmp.getTaskId());
        }
    }
}
