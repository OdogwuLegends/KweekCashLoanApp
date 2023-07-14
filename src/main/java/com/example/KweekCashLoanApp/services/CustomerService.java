package com.example.KweekCashLoanApp.services;

import com.example.KweekCashLoanApp.AppUtils;
import com.example.KweekCashLoanApp.data.models.Customer;
import com.example.KweekCashLoanApp.data.repositories.ApprovedLoanRequestsRepository;
import com.example.KweekCashLoanApp.data.repositories.CustomerRepository;
import com.example.KweekCashLoanApp.dtos.requests.*;
import com.example.KweekCashLoanApp.dtos.responses.*;
import com.example.KweekCashLoanApp.error.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class CustomerService implements ICustomerService{
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    IPendingLoansService pendingLoansService;
    @Autowired
    ApprovedLoanRequestsRepository approvedLoanRequestsRepository;
    @Autowired
    RepaymentScheduleService repaymentScheduleService;

    @Override
    public RegisterUserResponse registerCustomer(RegisterUserRequest request) {
        if(request.getPhoneNumber().length() != 11 && request.getPhoneNumber().length() != 14){
            throw new RuntimeException("Invalid Phone Number entered");
        }
        if(!AppUtils.emailIsCorrect(request.getEmail())){
            throw new RuntimeException("Invalid email");
        }
        if(!AppUtils.passwordIsCorrect(request.getPassword())){
            throw new RuntimeException("Invalid password.");
        }

        Customer newCustomer = new Customer();
        BeanUtils.copyProperties(request,newCustomer);

        newCustomer.setDateRegistered(LocalDate.now());
        Customer savedCustomer = customerRepository.save(newCustomer);

        RegisterUserResponse response = new RegisterUserResponse();
        BeanUtils.copyProperties(savedCustomer,response);

        return response;
    }

    public LoginResponse logIn(LoginRequest request) throws ObjectNotFoundException {
        Customer foundCustomer = customerRepository.findCustomerByEmail(request.getEmail());
        if(Objects.isNull(foundCustomer)){
            throw new ObjectNotFoundException("Email not correct");
        }
        LoginResponse response = new LoginResponse();
        if(!foundCustomer.getPassword().equals(request.getPassword())){
            throw new ObjectNotFoundException("Password incorrect");
        } else {
            response.setLoggedIn(true);
            response.setMessage("Log in successful");
        }
        return response;
    }

    @Override
    public LoanApplicationResponse applyForALoan(LoanApplicationRequest request) {
        Customer foundCustomer = customerRepository.findCustomerByEmail(request.getEmail());
        return pendingLoansService.requestForALoan(request,foundCustomer);
    }

    @Override
    public LoanApplicationResponse checkApplicationStatus(LoanApplicationRequest request) {
        return pendingLoansService.confirmStatus(request);
    }

//    @Override //IS THIS CORRECT?
//    public LoanAgreementResponse viewLoanAgreementForm(LoanApplicationRequest request) {
//        return loanOfficerService.generateLoanAgreementForm(request);
//    }

    @Override
    public FindUserResponse findCustomerById(long id) throws ObjectNotFoundException {
        Customer foundCustomer = customerRepository.findById(id).get();
        if(foundCustomer == null){
            throw new ObjectNotFoundException("Customer Not Found");
        }

        FindUserResponse response = new FindUserResponse();

        String address = foundCustomer.getStreetNumber()+","+foundCustomer.getStreetName()+
        ","+foundCustomer.getTown()+","+foundCustomer.getState()+".";

        response.setFirstName(foundCustomer.getFirstName());
        response.setLastName(foundCustomer.getLastName());
        response.setEmail(foundCustomer.getEmail());
        response.setPhoneNumber(foundCustomer.getPhoneNumber());
        response.setAddress(address);
        response.setOccupation(foundCustomer.getOccupation());
        response.setId(foundCustomer.getCustomerId());

        return response;
    }

    @Override
    public FindUserResponse findCustomerByEmail(String email) {
        Customer foundCustomer = customerRepository.findCustomerByEmail(email);
        if(foundCustomer == null) throw new RuntimeException("Customer not found");

        FindUserResponse response = new FindUserResponse();

        String address = foundCustomer.getStreetNumber()+","+foundCustomer.getStreetName()+
                ","+foundCustomer.getTown()+","+foundCustomer.getState()+".";

        response.setFirstName(foundCustomer.getFirstName());
        response.setLastName(foundCustomer.getLastName());
        response.setEmail(foundCustomer.getEmail());
        response.setPhoneNumber(foundCustomer.getPhoneNumber());
        response.setAddress(address);
        response.setOccupation(foundCustomer.getOccupation());
        response.setId(foundCustomer.getCustomerId());

        return response;
    }

    @Override
    public UpdateUserResponse updateCustomerDetails(UpdateUserRequest request) {
        Customer foundCustomer = customerRepository.findCustomerByEmail(request.getEmail());
        if(Objects.isNull(foundCustomer)){
            throw new RuntimeException("Email not correct");
        }
        if(Objects.nonNull(request.getFirstName()) && !request.getFirstName().equals(""))foundCustomer.setFirstName(request.getFirstName());
        if(Objects.nonNull(request.getLastName()) && !request.getLastName().equals(""))foundCustomer.setLastName(request.getLastName());
        if(Objects.nonNull(request.getNewEmail()) && !request.getNewEmail().equals(""))foundCustomer.setEmail(request.getNewEmail());
        if(Objects.nonNull(request.getNewPassword()) && !request.getNewPassword().equals(""))foundCustomer.setPassword(request.getNewPassword());
        if(Objects.nonNull(request.getPhoneNumber()) && !request.getPhoneNumber().equals(""))foundCustomer.setPhoneNumber(request.getPhoneNumber());
        if(Objects.nonNull(request.getOccupation()) && !request.getOccupation().equals(""))foundCustomer.setOccupation(request.getOccupation());
        if(Objects.nonNull(request.getStreetNumber()) && !request.getStreetNumber().equals(""))foundCustomer.setStreetNumber(request.getStreetNumber());
        if(Objects.nonNull(request.getStreetName()) && !request.getStreetName().equals(""))foundCustomer.setStreetName(request.getStreetName());
        if(Objects.nonNull(request.getTown()) && !request.getTown().equals(""))foundCustomer.setTown(request.getTown());
        if(Objects.nonNull(request.getState()) && !request.getState().equals(""))foundCustomer.setState(request.getState());

        Customer updatedCustomer = customerRepository.save(foundCustomer);
        UpdateUserResponse response = new UpdateUserResponse();
        BeanUtils.copyProperties(updatedCustomer,response);
        response.setMessage("Update successful");
        return response;
    }

    @Override
    public String makePayment(PaymentRequest request) {
        Customer customer = customerRepository.findCustomerByEmail(request.getEmail());
        Long id = customer.getCustomerId();
        return repaymentScheduleService.makePayment(id,request);
    }

    @Override
    public String checkLoanBalance(PaymentRequest request) {
        Customer customer = customerRepository.findCustomerByEmail(request.getEmail());
        Long id = customer.getCustomerId();
        return repaymentScheduleService.checkBalance(id).toString();
    }


}
