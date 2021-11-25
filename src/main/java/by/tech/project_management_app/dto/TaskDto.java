package by.tech.project_management_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private String taskName;
    private String taskDescription;
    private int projectId;
    private boolean isDone;
}