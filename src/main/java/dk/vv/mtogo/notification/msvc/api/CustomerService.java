package dk.vv.mtogo.notification.msvc.api;

import dk.vv.common.data.transfer.objects.common.AddressDTO;
import dk.vv.common.data.transfer.objects.customer.CustomerDTO;

import java.util.concurrent.ExecutionException;


public interface CustomerService {
    CustomerDTO getCustomerById(int customerId) throws ExecutionException, InterruptedException;

}
