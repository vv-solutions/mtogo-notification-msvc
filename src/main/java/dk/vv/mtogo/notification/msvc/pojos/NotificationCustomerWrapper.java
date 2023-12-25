package dk.vv.mtogo.notification.msvc.pojos;

import dk.vv.common.data.transfer.objects.Notification.NotificationDTO;
import dk.vv.common.data.transfer.objects.customer.CustomerDTO;

public class NotificationCustomerWrapper {
    private NotificationDTO notificationDTO;

    private CustomerDTO customerDTO;


    public NotificationCustomerWrapper(NotificationDTO notificationDTO, CustomerDTO customerDTO) {
        this.notificationDTO = notificationDTO;
        this.customerDTO = customerDTO;
    }

    public NotificationDTO getNotificationDTO() {
        return notificationDTO;
    }

    public void setNotificationDTO(NotificationDTO notificationDTO) {
        this.notificationDTO = notificationDTO;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }
}
