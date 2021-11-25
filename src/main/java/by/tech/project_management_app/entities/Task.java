package by.tech.project_management_app.entities;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Task implements Serializable {

    private static final long serialVersionUID = -4039451225310775717L;
    private int id;
    private String taskName;
    private String taskDescription;
    private int createdBy;
    private int modifiedBy;
    private boolean isDeleted;
    private boolean isDone;
    private int projectId;

}