package YSIT.YSit.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserListForm {
    private Boolean student;
    private Boolean teacher;
    public UserListForm(){
    }
}
