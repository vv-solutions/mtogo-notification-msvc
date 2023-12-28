package dk.vv.mtogo.notification.msvc;

import com.rabbitmq.client.ConnectionFactory;
import dk.vv.mtogo.notification.msvc.api.CustomerService;
import dk.vv.mtogo.notification.msvc.processors.EnrichWithCustomerProcessor;
import dk.vv.mtogo.notification.msvc.processors.SendMailProcessor;
import io.quarkus.arc.Unremovable;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@ApplicationScoped
public class Producers {
    @Inject
    Configuration configuration;

    @Inject
    Logger logger;

    @Inject
    Mailer mailer;



    @Produces
    @Unremovable
    ConnectionFactory rabbitConnectionFactory(Logger logger) throws NoSuchAlgorithmException, KeyManagementException {
        logger.info("Configure ConnectionFactory for " + configuration.mq().host());
        return new ConnectionFactory() {{
            setHost(configuration.mq().host());
            setPort(configuration.mq().port());
            setUsername(configuration.mq().username());
            setPassword(configuration.mq().password());
            setVirtualHost(configuration.mq().vhost());

            if(configuration.mq().useSsl() == true) {
                useSslProtocol();
            }
        }};
    }


    @Produces
    EnrichWithCustomerProcessor getEnrichWithEmailProcessor (CustomerService customerService){
        return new EnrichWithCustomerProcessor(logger, customerService);
    }

    @Produces
    SendMailProcessor getSendMailProcessor(){
        return new SendMailProcessor(mailer,logger);
    }




}
