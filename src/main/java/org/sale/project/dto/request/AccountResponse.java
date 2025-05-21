package org.sale.project.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.sale.project.entity.Role;
import org.sale.project.entity.User;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    String id;
    String email;
    User user;
    Role role;
}
