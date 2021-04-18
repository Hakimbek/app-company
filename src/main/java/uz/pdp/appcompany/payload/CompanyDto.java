package uz.pdp.appcompany.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CompanyDto {
    @NotNull(message = "corpName must not be null")
    private String corpName;

    @NotNull(message = "directorName must not be null")
    private String directorName;

    @NotNull(message = "street must not be null")
    private String street;

    @NotNull(message = "homeNumber must not be null")
    private Integer homeNumber;
}
