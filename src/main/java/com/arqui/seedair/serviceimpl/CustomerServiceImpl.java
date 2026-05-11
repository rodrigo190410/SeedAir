package com.arqui.seedair.serviceimpl;

import com.arqui.seedair.dtos.CustomerDTO;
import com.arqui.seedair.dtos.CustomerSummaryDTO;
import com.arqui.seedair.entities.Authority;
import com.arqui.seedair.entities.Customer;
import com.arqui.seedair.entities.User;
import com.arqui.seedair.repositories.CustomerRepository;
import com.arqui.seedair.repositories.UserRepository;
import com.arqui.seedair.services.AuthorityService;
import com.arqui.seedair.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthorityService authorityService;
    @Override
    public Customer add(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> listAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).get();
    }

    private List<Authority> authoritiesFromString(String authorities) {

        List<Authority> authorityList = new ArrayList<>();
        List<String> authorityStringList = Arrays.stream(authorities.split(";")).toList();
        for (String authorityString : authorityStringList) {
            Authority authority = authorityService.findByName(authorityString);
            if (authority != null) {
                authorityList.add(authority);
            }
        }
        return authorityList;
    }
    @Override
    public CustomerDTO addDTO(CustomerDTO customerDTO) {
        List<Authority> authorityList = authoritiesFromString("ROLE_USER");

        User newUser = new User(null, customerDTO.getUsername(),
                new BCryptPasswordEncoder().encode(customerDTO.getPassword()), authorityList, null);

        userRepository.save(newUser);

        Customer newCustomer = new Customer(
                null, customerDTO.getFirstName(), customerDTO.getLastName(),
                customerDTO.getPhone(), newUser, null, null, null
        );

        customerRepository.save(newCustomer);

        return customerDTO;
    }

    @Override
    public List<CustomerSummaryDTO> getCustomersNoReservation() {
        return customerRepository.findCustomersWithParcelButNoReservation();
    }

    @Override
    public List<CustomerSummaryDTO> getCustomersMoreThanParcels(Integer cantidad) {
        return customerRepository.findCustomersWithMoreThanXParcels(cantidad);
    }

    public List<CustomerSummaryDTO> getCustomersWithReservationNoReviews() {
        return customerRepository.findCustomersWithReservationButNoReview();
    }
    @Override
    public List<CustomerSummaryDTO> getCustomersHighRating(Double rating) {
        return customerRepository.findCustomersWithVisibleReviewAndRatingGreaterThan(rating);
    }
}
