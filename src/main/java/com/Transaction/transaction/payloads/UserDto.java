package com.Transaction.transaction.payloads;


import com.Transaction.transaction.entity.Permission;
import com.Transaction.transaction.entity.Role1;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int id;
    @NotNull
    @NotEmpty
    private String email;
//    @NotNull
//    @NotEmpty
    private String password;
    private Role1 role;
}
