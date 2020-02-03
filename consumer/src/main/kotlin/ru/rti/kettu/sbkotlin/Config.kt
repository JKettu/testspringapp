package ru.rti.kettu.sbkotlin

import feign.Feign
import feign.codec.Decoder
import feign.codec.Encoder
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import feign.jaxb.JAXBContextFactory
import feign.soap.SOAPDecoder
import feign.soap.SOAPEncoder
import org.springframework.cloud.openfeign.support.SpringMvcContract
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.rti.kettu.sbkotlin.client.DbConnectorClient
import ru.rti.kettu.sbkotlin.client.DbConnectorSoapClient

@Configuration
class Config {

    @Bean (value = ["client"])
    fun client(): DbConnectorClient? {
        return Feign.builder()
                .contract(SpringMvcContract())
                .decoder(JacksonDecoder())
                .encoder(JacksonEncoder())
                .target(DbConnectorClient::class.java, "http://localhost:1001")
    }

    @Bean (value = ["soapClient"])
    fun soapClient(): DbConnectorSoapClient? {
        return Feign.builder()
                .contract(SpringMvcContract())
                .decoder(feignDecoder())
                .encoder(feignEncoder())
                .target(DbConnectorSoapClient::class.java, "http://localhost:1001/ws/music.wsdl")
    }

    @Bean(value = ["soapJaxbContextFactory"])
    fun jaxbFactory(): JAXBContextFactory {
        return JAXBContextFactory.Builder()
                .withMarshallerJAXBEncoding("UTF-8")
                .withMarshallerSchemaLocation("./src/main/resources/xsd/music.xsd")
                .build()
    }

    @Bean (value = ["soapEncoder"])
    fun feignEncoder(): Encoder? {
        return SOAPEncoder(jaxbFactory())
    }

    @Bean (value = ["soapDecoder"])
    fun feignDecoder(): Decoder? {
        return SOAPDecoder(jaxbFactory())
    }
}