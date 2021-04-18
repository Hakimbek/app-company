package uz.pdp.appcompany.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DepartmentDto {
    @NotNull(message = "name must not be null")
    private String name;

    @NotNull(message = "company id must not be null")
    private Integer companyId;
}
