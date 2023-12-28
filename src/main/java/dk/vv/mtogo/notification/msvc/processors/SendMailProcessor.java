package dk.vv.mtogo.notification.msvc.processors;

import dk.vv.mtogo.notification.msvc.Configuration;
import dk.vv.mtogo.notification.msvc.pojos.NotificationCustomerWrapper;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.jboss.logging.Logger;

public class SendMailProcessor implements Processor {

    private final Logger logger;
     private final Mailer mailer;

    public SendMailProcessor(Mailer mailer, Logger logger) {
        this.mailer = mailer;
        this.logger = logger;
    }



    @Override
    public void process(Exchange exchange) throws Exception {
        NotificationCustomerWrapper notificationCustomerWrapper = exchange.getIn().getBody(NotificationCustomerWrapper.class);

        var notification = notificationCustomerWrapper.getNotificationDTO();
        var customer = notificationCustomerWrapper.getCustomerDTO();

        StringBuilder mailBody = new StringBuilder();

        mailBody.append("Hi ");
        mailBody.append(customer.getFirstName());
        mailBody.append("\n\n");
        mailBody.append(notification.getMessage());
        mailBody.append("\n\n");
        mailBody.append("Kind regards\n");
        mailBody.append("VV MTOGO");


        Mail mail = new Mail();

        mail.addTo(customer.getEmail());
        mail.setText(mailBody.toString());
        mail.setSubject("Thanks for your message");



        logger.infof("Sending mail to: [%s]", customer.getEmail());
        mailer.send(mail);
        logger.infof("Mail sent to: [%s]", customer.getEmail());

    }
}
