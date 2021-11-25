package by.tech.project_management_app.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Project implements Serializable {

    private static final long serialVersionUID = 933167360904255157L;
    private int id;
    private String name;
    private String description;
    private int createdBy;
    private int modifiedBy;
    private boolean isDeleted;
    private List<Task> tasks = new ArrayList<>();

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Project(int id, String name, String description, int createdBy, int modifiedBy, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.isDeleted = isDeleted;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }
}