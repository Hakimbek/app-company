package uz.pdp.appcompany.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WorkerDto {
    @NotNull(message = "name must not be null")
    private String name;

    @NotNull(message = "phoneNumber must not be null")
    private String phoneNumber;

    @NotNull(message = "departmentId must not be null")
    private Integer departmentId;

    @NotNull(message = "street must not be null")
    private String street;

    @NotNull(message = "homeNumber must not be null")
    private Integer homeNumber;
}
