package stockApp.model;

import lombok.*;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.context.annotation.Primary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document (collection = "User")
public class User {
    @Id
    private String id;
    @NotBlank(message = "Firstname can't be blank")
    @Size(min = 3, message = "Firstname Length Atleast 3 character long")
    private String firstname;
    @NotBlank(message = "Lastname can't be blank")
    @Size(min = 3, message = "Lastname Length Atleast 3 character long")
    private String lastname;
    @Email(message = "Email format miss matched")
    private String username;
    @NotBlank(message = "Password can't be blank")
    @Size(min = 8, message = "Password Length Atleast 8 character long")
    private String password;

    private Collection<Role> roles = new ArrayList<>();

}
