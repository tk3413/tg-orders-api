//package com.tradegecko.ordersdbapi.converter;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.support.ConversionServiceFactoryBean;
//import org.springframework.core.convert.ConversionService;
//import org.springframework.core.convert.converter.Converter;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Configuration
//public class ConversionServiceProvider {
//
//    @Autowired
//    private ConvertOrderInfoToResponse convertOrderInfoToResponse;
//
//    @Autowired
//    private ConvertRequestOrderToOrder convertRequestOrderToOrder;
//
//    @Bean
//    public ConversionService getConversionService() {
//        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
//        bean.setConverters( getConverters() );
//        bean.afterPropertiesSet();
//        return bean.getObject();
//    }
//
//    private Set<Converter<?, ?>> getConverters(){
//
//        Set<Converter<?, ?>> converters = new HashSet<Converter<?, ?>>();
//            converters.add(convertOrderInfoToResponse);
//            converters.add(convertRequestOrderToOrder);
//        return converters;
//    }
//}
