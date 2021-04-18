package uz.pdp.appcompany.servise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appcompany.entity.Company;
import uz.pdp.appcompany.entity.Department;
import uz.pdp.appcompany.payload.ApiResponse;
import uz.pdp.appcompany.payload.DepartmentDto;
import uz.pdp.appcompany.repository.CompanyRepository;
import uz.pdp.appcompany.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CompanyRepository companyRepository;


    /**
     * GET DEPARTMENTS
     *
     * @return DEPARTMENTS LIST IN RESPONSE ENTITY
     */
    public List<Department> get() {
        return departmentRepository.findAll();
    }


    /**
     * GET ONE DEPARTMENT BY ID
     *
     * @param id INTEGER
     * @return ONE DEPARTMENT IN RESPONSE ENTITY
     */
    public Department getById(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElse(null);
    }


    /**
     * DELETE ONE DEPARTMENT BY ID
     *
     * @param id INTEGER
     * @return API RESPONSE IN RESPONSE ENTITY
     */
    public ApiResponse delete(Integer id) {
        try {
            departmentRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }


    /**
     * ADD DEPARTMENT
     * IF DEPARTMENT NAME IS NOT EXIT
     *
     * @param departmentDto NAME (String),
     *                      COMPANY ID (Integer)
     * @return API RESPONSE IN RESPONSE ENTITY
     */
    public ApiResponse add(DepartmentDto departmentDto) {
        boolean existsByName = departmentRepository.existsByName(departmentDto.getName());
        if (existsByName) {
            return new ApiResponse("Department already exist", false);
        }

        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent()) {
            return new ApiResponse("Company not found", false);
        }
        Company company = optionalCompany.get();

        Department department = new Department();
        department.setName(departmentDto.getName());
        department.setCompany(company);
        departmentRepository.save(department);
        return new ApiResponse("Successfully added", true);
    }


    /**
     * @param id            INTEGER
     * @param departmentDto NAME (String),
     *                      COMPANY ID (Integer)
     * @return API RESPONSE IN RESPONSE ENTITY
     */
    public ApiResponse edit(Integer id, DepartmentDto departmentDto) {
        boolean existsByNameAndIdNot = departmentRepository.existsByNameAndIdNot(departmentDto.getName(), id);
        if (existsByNameAndIdNot) {
            return new ApiResponse("Department already exist", false);
        }

        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent()) {
            return new ApiResponse("Company not found", false);
        }
        Company company = optionalCompany.get();

        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent()) {
            return new ApiResponse("Department not found", false);
        }
        Department department = optionalDepartment.get();
        department.setName(departmentDto.getName());
        department.setCompany(company);
        departmentRepository.save(department);
        return new ApiResponse("Successfully edited", true);
    }
}
