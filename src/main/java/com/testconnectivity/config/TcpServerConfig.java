package com.testconnectivity.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpReceivingChannelAdapter;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;

@Configuration
@EnableIntegration
@Slf4j
public class TcpServerConfig {

    @Bean
    public AbstractServerConnectionFactory serverConnectionFactory() {
        return new TcpNetServerConnectionFactory(9876); // Change to your desired port
    }

    @Bean
    public DirectChannel inputChannel() {
        return new DirectChannel();
    }

    @Bean
    public TcpReceivingChannelAdapter inboundAdapter() {
        TcpReceivingChannelAdapter adapter = new TcpReceivingChannelAdapter();
        adapter.setConnectionFactory(serverConnectionFactory());
        adapter.setOutputChannel(inputChannel());
        return adapter;
    }

    @ServiceActivator(inputChannel = "inputChannel")
    public void handleMessage(byte[] messageBytes) {
        String message = new String(messageBytes);
        log.info("Data Recieved from GPS : {}",message);
    }
}
