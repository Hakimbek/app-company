package uz.pdp.appcompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcompany.entity.Department;
import uz.pdp.appcompany.payload.ApiResponse;
import uz.pdp.appcompany.payload.DepartmentDto;
import uz.pdp.appcompany.servise.DepartmentService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;


    /**
     * GET DEPARTMENTS
     *
     * @return DEPARTMENTS LIST IN RESPONSE ENTITY
     */
    @GetMapping
    public ResponseEntity<List<Department>> get() {
        List<Department> departments = departmentService.get();
        return ResponseEntity.ok(departments);
    }


    /**
     * GET ONE DEPARTMENT BY ID
     *
     * @param id INTEGER
     * @return ONE DEPARTMENT IN RESPONSE ENTITY
     */
    @GetMapping("/{id}")
    public ResponseEntity<Department> getById(@PathVariable Integer id) {
        Department department = departmentService.getById(id);
        return ResponseEntity.ok(department);
    }


    /**
     * DELETE ONE DEPARTMENT BY ID
     *
     * @param id INTEGER
     * @return API RESPONSE IN RESPONSE ENTITY
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id) {
        ApiResponse response = departmentService.delete(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 405).body(response);
    }


    /**
     * ADD DEPARTMENT
     * IF DEPARTMENT NAME IS NOT EXIT
     *
     * @param departmentDto NAME (String),
     *                      COMPANY ID (Integer)
     * @return API RESPONSE IN RESPONSE ENTITY
     */
    @PostMapping
    public ResponseEntity<ApiResponse> add(@Valid @RequestBody DepartmentDto departmentDto) {
        ApiResponse response = departmentService.add(departmentDto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }


    /**
     * @param id            INTEGER
     * @param departmentDto NAME (String),
     *                      COMPANY ID (Integer)
     * @return API RESPONSE IN RESPONSE ENTITY
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> edit(@PathVariable Integer id,
                                            @Valid @RequestBody DepartmentDto departmentDto) {
        ApiResponse response = departmentService.edit(id, departmentDto);
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
