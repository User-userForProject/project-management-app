package by.tech.project_management_app.entities;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User implements Serializable{
    private static final long serialVersionUID = 4672320611368515207L;
    private int id;
    private String username;
    private String password;
    private boolean isEnabled;

}