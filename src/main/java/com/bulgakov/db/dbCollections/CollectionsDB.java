package com.bulgakov.db.dbCollections;

import com.bulgakov.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Bulgakov
 * @since 13.02.2017
 */
public class CollectionsDB{
    //Map<ObjectId, object type>
    public static Map<Integer, String> objectsType = new HashMap<Integer, String>();

    public static Map<Integer, User> users = new HashMap<Integer,User>();
    public static Map<Integer, Customer> customers = new HashMap<Integer, Customer>();
    public static Map<Integer, RequestForIncreasingTime> requests = new HashMap<Integer, RequestForIncreasingTime>();
    public static Map<Integer, Project> projects = new HashMap<Integer, Project>();

    // Map<ProjectId, Map<TaskId, Task>>
    public static Map<Integer, Map<Integer, Task>> backLog = new HashMap<Integer, Map<Integer,Task>>();

    //Map<ProjectId, Map<SprintId, Sprint>>
    public static Map<Integer, Map<Integer, Sprint>> sprints = new HashMap<Integer, Map<Integer,Sprint>>();

    //Map<ProjectId, Map<SprintId, List<Task>>>
    public static Map<Integer, Map<Integer, List<Task>>> tasks = new HashMap<Integer, Map<Integer, List<Task>>>();

    //Map<UserId,Map<DoTaskId,DoTask>>
    public static Map<Integer, Map<Integer, DoTask>> userDoTasks = new HashMap<Integer, Map<Integer, DoTask>>();
}
