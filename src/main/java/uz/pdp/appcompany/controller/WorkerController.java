package uz.pdp.appcompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcompany.entity.Worker;
import uz.pdp.appcompany.payload.ApiResponse;
import uz.pdp.appcompany.payload.WorkerDto;
import uz.pdp.appcompany.servise.WorkerService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/worker")
public class WorkerController {
    @Autowired
    WorkerService workerService;


    /**
     * WORKERS LIST
     *
     * @return WORKERS LIST IN RESPONSE ENTITY
     */
    @GetMapping
    public ResponseEntity<List<Worker>> get() {
        List<Worker> workers = workerService.get();
        return ResponseEntity.ok(workers);
    }


    /**
     * ONE WORKER BY ID
     *
     * @param id INTEGER
     * @return ONE WORKER IN RESPONSE ENTITY
     */
    @GetMapping("/{id}")
    public ResponseEntity<Worker> getById(@PathVariable Integer id) {
        Worker worker = workerService.getById(id);
        return ResponseEntity.ok(worker);
    }


    /**
     * DELETE ONE WORKER BY ID
     *
     * @param id INTEGER
     * @return API RESPONSE IN RESPONSE ENTITY
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id) {
        ApiResponse response = workerService.delete(id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 405).body(response);
    }


    /**
     * ADD WORKER
     * IF PHONE NUMBER IS NOT EXIST
     *
     * @param workerDto NAME (String),
     *                  PHONE NUMBER (String),
     *                  DEPARTMENT ID (Integer),
     *                  STREET (String),
     *                  HOME NUMBER (Integer)
     * @return API RESPONSE IN RESPONSE ENTITY
     */
    @PostMapping
    public ResponseEntity<ApiResponse> add(@Valid @RequestBody WorkerDto workerDto) {
        ApiResponse response = workerService.add(workerDto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }


    /**
     * EDIT WORKER BY ID
     * IF PHONE NUMBER IS NOT EXIST
     *
     * @param id        INTEGER
     * @param workerDto NAME (String),
     *                  PHONE NUMBER (String),
     *                  DEPARTMENT ID (Integer),
     *                  STREET (String),
     *                  HOME NUMBER (Integer)
     * @return API RESPONSE IN RESPONSE ENTITY
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> edit(@PathVariable Integer id, @Valid @RequestBody WorkerDto workerDto) {
        ApiResponse response = workerService.edit(id, workerDto);
        return ResponseEntity.status(response.isSuccess() ? 202 : 409).body(response);
    }
}
