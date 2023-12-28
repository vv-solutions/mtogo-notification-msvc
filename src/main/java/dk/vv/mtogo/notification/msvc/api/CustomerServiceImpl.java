package dk.vv.mtogo.notification.msvc.api;

import dk.vv.common.data.transfer.objects.common.AddressDTO;
import dk.vv.common.data.transfer.objects.customer.CustomerDTO;
import io.smallrye.graphql.client.GraphQLClient;
import io.smallrye.graphql.client.Response;
import io.smallrye.graphql.client.core.Argument;
import io.smallrye.graphql.client.core.Document;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.concurrent.ExecutionException;

import static io.smallrye.graphql.client.core.Document.document;
import static io.smallrye.graphql.client.core.Field.field;
import static io.smallrye.graphql.client.core.Operation.operation;



@ApplicationScoped
public class CustomerServiceImpl implements CustomerService{

    @Inject
    @GraphQLClient("customer-api")
    DynamicGraphQLClient customerService;


    @Override
    public CustomerDTO getCustomerById(int customerId) throws ExecutionException, InterruptedException {
        Document query = document(
                operation(
                        field("getCustomerById", Argument.args(Argument.arg("customerId",customerId)),
                                field("firstName"),
                                field("id"),
                                field("email")
                        )));

        Response response = customerService.executeSync(query);

        return response.getObject(CustomerDTO.class,"getCustomerById");
    }
}
