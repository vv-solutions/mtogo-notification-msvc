package dk.vv.mtogo.notification.msvc.processors;

import dk.vv.common.data.transfer.objects.Notification.NotificationDTO;
import dk.vv.mtogo.notification.msvc.api.CustomerService;
import dk.vv.mtogo.notification.msvc.pojos.NotificationCustomerWrapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.jboss.logging.Logger;

public class EnrichWithCustomerProcessor implements Processor {

    private final Logger logger;
    private final CustomerService customerService;

    public EnrichWithCustomerProcessor(Logger logger, CustomerService customerService) {
        this.customerService = customerService;
        this.logger = logger;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        NotificationDTO notificationDTO = exchange.getIn().getBody(NotificationDTO.class);

        logger.infof("Enriching customer data for customer with id: [%d]", notificationDTO.getCustomerId());

        var customer = customerService.getCustomerById(notificationDTO.getCustomerId());


        exchange.getIn().setBody(new NotificationCustomerWrapper(notificationDTO, customer));

    }
}
