package lt.almantas.booksite.model.fdto;

import lombok.Data;

@Data
public class UserLoginFDTO {
    private String username;
    private String password;
}