package com.example.KweekCashLoanApp.services.implementation;

import com.example.KweekCashLoanApp.data.models.Customer;
import com.example.KweekCashLoanApp.data.repositories.CustomerRepository;
import com.example.KweekCashLoanApp.dtos.requests.*;
import com.example.KweekCashLoanApp.dtos.responses.*;
import com.example.KweekCashLoanApp.error.IncorrectDetailsException;
import com.example.KweekCashLoanApp.error.ObjectNotFoundException;
import com.example.KweekCashLoanApp.services.interfaces.ICustomerService;
import com.example.KweekCashLoanApp.services.interfaces.IPendingLoansService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import static com.example.KweekCashLoanApp.utils.AppUtils.validateDetails;
import static com.example.KweekCashLoanApp.utils.HardcodedValues.*;

@Service
@AllArgsConstructor
public class CustomerService implements ICustomerService {
    private final CustomerRepository customerRepository;
    private final IPendingLoansService pendingLoansService;
    private final RepaymentScheduleService repaymentScheduleService;

    @Override
    public RegisterUserResponse registerCustomer(RegisterUserRequest request) throws IncorrectDetailsException {
        validateDetails(request);

        Customer newCustomer = buildNewCustomer(request);

        Customer savedCustomer = customerRepository.save(newCustomer);

        RegisterUserResponse response = new RegisterUserResponse();
        BeanUtils.copyProperties(savedCustomer,response);

        return response;
    }

    public LoginResponse logIn(LoginRequest request) throws ObjectNotFoundException {
        Customer foundCustomer = customerRepository.findCustomerByEmail(request.getEmail()).orElseThrow(()-> new ObjectNotFoundException(EMAIL_NOT_CORRECT));

        LoginResponse response = new LoginResponse();
        if(!foundCustomer.getPassword().equals(request.getPassword())){
            throw new ObjectNotFoundException(PASSWORD_NOT_CORRECT);
        } else {
            response.setLoggedIn(true);
            response.setMessage(LOG_IN_SUCCESSFUL);
        }
        return response;
    }

    @Override
    public LoanApplicationResponse applyForALoan(LoanApplicationRequest request) {
        Customer foundCustomer = customerRepository.findCustomerByEmail(request.getEmail()).orElseThrow(()-> new ObjectNotFoundException(CUSTOMER_NOT_FOUND));
        return pendingLoansService.requestForALoan(request,foundCustomer);
    }

    @Override
    public LoanApplicationResponse checkApplicationStatus(LoanApplicationRequest request) throws IncorrectDetailsException {
        return pendingLoansService.confirmStatus(request);
    }

    @Override
    public FindUserResponse findCustomerById(long id) throws ObjectNotFoundException {
        Optional<Customer> foundCustomer = customerRepository.findById(id);

        if(foundCustomer.isEmpty()){
            throw new ObjectNotFoundException(CUSTOMER_NOT_FOUND);
        }

        FindUserResponse response = new FindUserResponse();

        buildCustomerAddress(foundCustomer.get(), response);

        return response;
    }

    @Override
    public FindUserResponse findCustomerByEmail(String email) throws ObjectNotFoundException {
        Customer foundCustomer = customerRepository.findCustomerByEmail(email).orElseThrow(()-> new ObjectNotFoundException(CUSTOMER_NOT_FOUND));

        FindUserResponse response = new FindUserResponse();

        buildCustomerAddress(foundCustomer, response);

        return response;
    }

    @Override
    public UpdateUserResponse updateCustomerDetails(UpdateUserRequest request) throws ObjectNotFoundException {
        Customer foundCustomer = customerRepository.findCustomerByEmail(request.getEmail()).orElseThrow(()-> new ObjectNotFoundException(EMAIL_NOT_CORRECT));


        if(Objects.nonNull(request.getFirstName()) && !request.getFirstName().equals(EMPTY_STRING))foundCustomer.setFirstName(request.getFirstName().toUpperCase());
        if(Objects.nonNull(request.getLastName()) && !request.getLastName().equals(EMPTY_STRING))foundCustomer.setLastName(request.getLastName().toUpperCase());
        if(Objects.nonNull(request.getNewEmail()) && !request.getNewEmail().equals(EMPTY_STRING))foundCustomer.setEmail(request.getNewEmail());
        if(Objects.nonNull(request.getNewPassword()) && !request.getNewPassword().equals(EMPTY_STRING))foundCustomer.setPassword(request.getNewPassword());
        if(Objects.nonNull(request.getPhoneNumber()) && !request.getPhoneNumber().equals(EMPTY_STRING))foundCustomer.setPhoneNumber(request.getPhoneNumber());
        if(Objects.nonNull(request.getStreetNumber()) && !request.getStreetNumber().equals(EMPTY_STRING))foundCustomer.setStreetNumber(request.getStreetNumber().toUpperCase());
        if(Objects.nonNull(request.getStreetName()) && !request.getStreetName().equals(EMPTY_STRING))foundCustomer.setStreetName(request.getStreetName().toUpperCase());
        if(Objects.nonNull(request.getTown()) && !request.getTown().equals(EMPTY_STRING))foundCustomer.setTown(request.getTown().toUpperCase());
        if(Objects.nonNull(request.getState()) && !request.getState().equals(EMPTY_STRING))foundCustomer.setState(request.getState().toUpperCase());
        if(Objects.nonNull(request.getOccupation()) && !request.getOccupation().equals(EMPTY_STRING))foundCustomer.setOccupation(request.getOccupation().toUpperCase());

        Customer updatedCustomer = customerRepository.save(foundCustomer);
        UpdateUserResponse response = new UpdateUserResponse();
        BeanUtils.copyProperties(updatedCustomer,response);
        response.setMessage(UPDATE_SUCCESSFUL);
        return response;
    }

    @Override
    public String makePayment(PaymentRequest request) throws ObjectNotFoundException {
        Customer customer = customerRepository.findCustomerByEmail(request.getEmail()).orElseThrow(()-> new ObjectNotFoundException(CUSTOMER_NOT_FOUND));
        Long id = customer.getCustomerId();
        return repaymentScheduleService.makePayment(id,request);
    }

    @Override
    public String checkLoanBalance(PaymentRequest request) throws ObjectNotFoundException {
        Customer customer = customerRepository.findCustomerByEmail(request.getEmail()).orElseThrow(()-> new ObjectNotFoundException(CUSTOMER_NOT_FOUND));
        Long id = customer.getCustomerId();
        return repaymentScheduleService.checkBalance(id).toString();
    }

    private static Customer buildNewCustomer(RegisterUserRequest request) {
        Customer newCustomer = new Customer();

        newCustomer.setFirstName(request.getFirstName().toUpperCase());
        newCustomer.setLastName(request.getLastName().toUpperCase());
        newCustomer.setEmail(request.getEmail());
        newCustomer.setPassword(request.getPassword());
        newCustomer.setPhoneNumber(request.getPhoneNumber());
        newCustomer.setOccupation(request.getOccupation().toUpperCase());
        newCustomer.setStreetNumber(request.getStreetNumber());
        newCustomer.setStreetName(request.getStreetName().toUpperCase());
        newCustomer.setTown(request.getTown().toUpperCase());
        newCustomer.setState(request.getState().toUpperCase());
        newCustomer.setDateRegistered(LocalDate.now());
        return newCustomer;
    }

    private static void buildCustomerAddress(Customer foundCustomer, FindUserResponse response) {
        String address = foundCustomer.getStreetNumber()+COMMA+ foundCustomer.getStreetName()+
                COMMA+ foundCustomer.getTown()+COMMA+ foundCustomer.getState()+FULL_STOP;

        response.setFirstName(foundCustomer.getFirstName());
        response.setLastName(foundCustomer.getLastName());
        response.setEmail(foundCustomer.getEmail());
        response.setPhoneNumber(foundCustomer.getPhoneNumber());
        response.setAddress(address);
        response.setOccupation(foundCustomer.getOccupation());
    }
}
