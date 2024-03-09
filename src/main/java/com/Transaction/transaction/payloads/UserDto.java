package com.Transaction.transaction.payloads;


import com.Transaction.transaction.entity.Permission;
import com.Transaction.transaction.entity.Role1;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int id1;
    @NonNull
    @Email
    @NotEmpty
    private String email;
    @NonNull
    @NotEmpty
    @Size(min = 7, max = 50)
    private String password;
    @NonNull
    private Role1 role;
}
