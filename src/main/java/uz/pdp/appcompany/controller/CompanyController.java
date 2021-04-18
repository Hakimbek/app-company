package uz.pdp.appcompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcompany.entity.Company;
import uz.pdp.appcompany.payload.ApiResponse;
import uz.pdp.appcompany.payload.CompanyDto;
import uz.pdp.appcompany.servise.CompanyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;


    /**
     * GET COMPANIES LIST
     *
     * @return COMPANIES LIST IN RESPONSE ENTITY
     */
    @GetMapping
    public ResponseEntity<List<Company>> get() {
        List<Company> companies = companyService.get();
        return ResponseEntity.ok(companies);
    }


    /**
     * GET ONE COMPANY BY ID
     *
     * @param id INTEGER
     * @return ONE COMPANY IN RESPONSE ENTITY
     */
    @GetMapping("/{id}")
    public ResponseEntity<Company> getById(@PathVariable Integer id) {
        Company company = companyService.getById(id);
        return ResponseEntity.ok(company);
    }


    /**
     * DELETE ONE COMPANY BY ID
     *
     * @param id INTEGER
     * @return API RESPONSE IN RESPONSE ENTITY
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id) {
        ApiResponse response = companyService.delete(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 405).body(response);
    }


    /**
     * ADD COMPANY
     * IF COMPANY NAME IS NOT EXIST
     *
     * @param companyDto COMPANY NAME (String),
     *                   DIRECTOR NAME (String),
     *                   STREET (String),
     *                   HOME NUMBER (Integer)
     * @return API RESPONSE IN RESPONSE ENTITY
     */
    @PostMapping
    public ResponseEntity<ApiResponse> add(@Valid @RequestBody CompanyDto companyDto) {
        ApiResponse response = companyService.add(companyDto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }


    /**
     * EDIT COMPANY
     * IF EDITED COMPANY NAME IS NOT EXIST
     *
     * @param id         INTEGER
     * @param companyDto COMPANY NAME (String),
     *                   DIRECTOR NAME (String),
     *                   STREET (String),
     *                   HOME NUMBER (Integer)
     * @return API RESPONSE IN RESPONSE ENTITY
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> edit(@PathVariable Integer id,
                                            @Valid @RequestBody CompanyDto companyDto) {
        ApiResponse response = companyService.edit(id, companyDto);
        return ResponseEntity.status(response.isSuccess() ? 202 : 409).body(response);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
