package org.sale.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.sale.project.dto.request.AccountResponse;
import org.sale.project.entity.Account;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "role", target = "role")
    AccountResponse toResponse(Account account);

    List<AccountResponse> toResponseList(List<Account> accounts);
}
