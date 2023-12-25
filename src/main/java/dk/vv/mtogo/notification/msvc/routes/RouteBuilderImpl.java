package dk.vv.mtogo.notification.msvc.routes;

import dk.vv.common.data.transfer.objects.Notification.NotificationDTO;
import dk.vv.mtogo.notification.msvc.Configuration;
import dk.vv.mtogo.notification.msvc.processors.EnrichWithCustomerProcessor;
import dk.vv.mtogo.notification.msvc.processors.SendMailProcessor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.quarkus.core.FastCamelContext;
import org.jboss.logging.Logger;

@ApplicationScoped
public class RouteBuilderImpl extends EndpointRouteBuilder {

    private final Logger logger;
    private final CamelContext camelContext;

    private final Configuration configuration;

    private final SendMailProcessor sendMailProcessor;

    private final EnrichWithCustomerProcessor enrichWithCustomerProcessor;

    @Inject
    public RouteBuilderImpl(Logger logger, CamelContext camelContext, Configuration configuration, SendMailProcessor sendMailProcessor, EnrichWithCustomerProcessor enrichWithCustomerProcessor) {
        this.logger = logger;
        this.camelContext = camelContext;
        this.configuration = configuration;
        this.sendMailProcessor = sendMailProcessor;
        this.enrichWithCustomerProcessor = enrichWithCustomerProcessor;
    }


    @Override
    public void configure() throws Exception {

        ((FastCamelContext)this.camelContext).setName(configuration.contextName());

        onException(Exception.class)
                .process(exchange -> {
                    logger.error(String.format("recv: failed: %s-%s",exchange.getExchangeId(), exchange.getMessage().getBody()));
                })
                .handled(false)
                .end();


        from(configuration.routes().notification().in()).routeId(configuration.routes().notification().routeId())

                .unmarshal().json(NotificationDTO.class)

                .process(e-> logger.infof("recv: Received information about notification for user with id: [%s]", e.getMessage().getBody(NotificationDTO.class).getCustomerId()))

                //Enrich with customer information
                .process(enrichWithCustomerProcessor)

                //Send mail
                .process(sendMailProcessor)
                ;
    }
}
