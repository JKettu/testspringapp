package ru.rti.kettu.sbjava.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@EnableWs
public class WebConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    //wsdl address - http://localhost:1001/ws/music.wsdl
    @Bean(name = "music")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema musicSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("MusicWebService");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace("musicEndpoint");
        definition.setSchema(musicSchema);
        return definition;
    }

    @Bean
    public XsdSchema musicSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/music.xsd"));
    }
}
