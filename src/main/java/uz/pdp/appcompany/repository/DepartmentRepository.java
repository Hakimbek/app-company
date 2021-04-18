package uz.pdp.appcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcompany.entity.Department;
import uz.pdp.appcompany.entity.Worker;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Integer id);
}
