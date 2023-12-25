package dk.vv.mtogo.notification.msvc;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "notification.msvc", namingStrategy = ConfigMapping.NamingStrategy.VERBATIM)
public interface Configuration {

    QueueConfig mq();

    MainConfig routes();

    String contextName();
    interface QueueConfig {
        String host();
        String username();
        String password();
        int port();
        String vhost();
        boolean useSsl();
        int redeliveryBaseDelaySec();
        int redeliveryMultiplier();
        int redeliveryMaxDelaySec();
    }
    public interface MainConfig {
        interface InRoute{
            String in();
            String routeId();
        }
        interface InOutRoute{
            String in();
            String out();
            String routeId();
        }
        InRoute notification();
    }
}
