package dk.vv.mtogo.notification.msvc.processors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.vv.common.data.transfer.objects.Notification.NotificationDTO;
import dk.vv.common.data.transfer.objects.order.OrderDTO;
import dk.vv.common.data.transfer.objects.product.ProductDTO;
import dk.vv.mtogo.notification.msvc.api.OrderService;
import dk.vv.mtogo.notification.msvc.pojos.NotificationCustomerWrapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.List;

public class EnrichWithOrderProcessor implements Processor {

    private final Logger logger;
    private final OrderService orderService;

    private final ObjectMapper mapper = new ObjectMapper();

    public EnrichWithOrderProcessor(Logger logger,@RestClient OrderService orderService) {
        this.orderService = orderService;
        this.logger = logger;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        NotificationDTO notificationDTO = exchange.getIn().getBody(NotificationDTO.class);

        logger.infof("Enriching order data for order with id: [%d]", notificationDTO.getOrderId());

        var response = orderService.getOrder(notificationDTO.getOrderId());

        var order = mapper.readValue(response.readEntity(String.class), OrderDTO.class);

        notificationDTO.setCustomerId(order.getCustomerId());

        exchange.getIn().setBody(notificationDTO);

    }
}
