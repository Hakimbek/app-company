package uz.pdp.appcompany.servise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appcompany.entity.Address;
import uz.pdp.appcompany.entity.Department;
import uz.pdp.appcompany.entity.Worker;
import uz.pdp.appcompany.payload.ApiResponse;
import uz.pdp.appcompany.payload.WorkerDto;
import uz.pdp.appcompany.repository.AddressRepository;
import uz.pdp.appcompany.repository.DepartmentRepository;
import uz.pdp.appcompany.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    @Autowired
    WorkerRepository workerRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    AddressRepository addressRepository;


    /**
     * WORKERS LIST
     *
     * @return WORKERS LIST IN RESPONSE ENTITY
     */
    public List<Worker> get() {
        return workerRepository.findAll();
    }


    /**
     * ONE WORKER BY ID
     *
     * @param id INTEGER
     * @return ONE WORKER IN RESPONSE ENTITY
     */
    public Worker getById(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        return optionalWorker.orElse(null);
    }


    /**
     * DELETE ONE WORKER BY ID
     *
     * @param id INTEGER
     * @return API RESPONSE IN RESPONSE ENTITY
     */
    public ApiResponse delete(Integer id) {
        try {
            workerRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
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
    public ApiResponse add(WorkerDto workerDto) {
        boolean existsByPhoneNumber = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());
        if (existsByPhoneNumber) {
            return new ApiResponse("Worker already exist", false);
        }

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent()) {
            return new ApiResponse("Department not found", false);
        }
        Department department = optionalDepartment.get();

        Address address = new Address();
        address.setStreet(workerDto.getStreet());
        address.setHomeNumber(workerDto.getHomeNumber());
        Address savedAddress = addressRepository.save(address);

        Worker worker = new Worker();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setDepartment(department);
        worker.setAddress(savedAddress);
        workerRepository.save(worker);
        return new ApiResponse("Successfully added", true);
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
    public ApiResponse edit(Integer id, WorkerDto workerDto) {
        boolean existsByPhoneNumberAndIdNot = workerRepository.existsByPhoneNumberAndIdNot(workerDto.getPhoneNumber(), id);
        if (existsByPhoneNumberAndIdNot) {
            return new ApiResponse("Worker already exist", false);
        }

        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (!optionalWorker.isPresent()) {
            return new ApiResponse("Worker not found", false);
        }
        Worker worker = optionalWorker.get();

        Optional<Address> optionalAddress = addressRepository.findById(worker.getAddress().getId());
        if (!optionalAddress.isPresent()) {
            return new ApiResponse("Address not found", false);
        }
        Address address = optionalAddress.get();
        address.setStreet(workerDto.getStreet());
        address.setHomeNumber(workerDto.getHomeNumber());
        Address savedAddress = addressRepository.save(address);

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent()) {
            return new ApiResponse("Department not found", false);
        }
        Department department = optionalDepartment.get();

        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setDepartment(department);
        worker.setAddress(savedAddress);
        workerRepository.save(worker);
        return new ApiResponse("Successfully edited", true);
    }
}
