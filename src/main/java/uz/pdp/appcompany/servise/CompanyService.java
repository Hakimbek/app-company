package uz.pdp.appcompany.servise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appcompany.entity.Address;
import uz.pdp.appcompany.entity.Company;
import uz.pdp.appcompany.payload.ApiResponse;
import uz.pdp.appcompany.payload.CompanyDto;
import uz.pdp.appcompany.repository.AddressRepository;
import uz.pdp.appcompany.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    AddressRepository addressRepository;


    /**
     * GET COMPANIES LIST
     *
     * @return COMPANIES LIST IN RESPONSE ENTITY
     */
    public List<Company> get() {
        return companyRepository.findAll();
    }


    /**
     * GET ONE COMPANY BY ID
     *
     * @param id INTEGER
     * @return COMPANY IN RESPONSE ENTITY
     */
    public Company getById(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        return optionalCompany.orElse(null);
    }


    /**
     * DELETE COMPANY BY ID
     *
     * @param id INTEGER
     * @return API RESPONSE IN RESPONSE ENTITY
     */
    public ApiResponse delete(Integer id) {
        try {
            companyRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
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
    public ApiResponse add(CompanyDto companyDto) {
        boolean existsByCorpName = companyRepository.existsByCorpName(companyDto.getCorpName());
        if (existsByCorpName) {
            return new ApiResponse("CorpName already exist", false);
        }

        Address address = new Address();
        address.setStreet(companyDto.getStreet());
        address.setHomeNumber(companyDto.getHomeNumber());
        Address savedAddress = addressRepository.save(address);

        Company company = new Company();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        company.setAddress(savedAddress);
        companyRepository.save(company);
        return new ApiResponse("Successfully added", true);
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
    public ApiResponse edit(Integer id, CompanyDto companyDto) {
        boolean existsByCorpNameAndIdNot = companyRepository.existsByCorpNameAndIdNot(companyDto.getCorpName(), id);
        if (existsByCorpNameAndIdNot) {
            return new ApiResponse("CorpName already exist", false);
        }

        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent()) {
            return new ApiResponse("Company not found", false);
        }
        Company company = optionalCompany.get();

        Optional<Address> optionalAddress = addressRepository.findById(company.getAddress().getId());
        if (!optionalAddress.isPresent()) {
            return new ApiResponse("Address not found", false);
        }
        Address address = optionalAddress.get();
        address.setStreet(companyDto.getStreet());
        address.setHomeNumber(companyDto.getHomeNumber());
        Address savedAddress = addressRepository.save(address);

        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        company.setAddress(savedAddress);
        companyRepository.save(company);
        return new ApiResponse("Successfully edited", true);
    }
}
