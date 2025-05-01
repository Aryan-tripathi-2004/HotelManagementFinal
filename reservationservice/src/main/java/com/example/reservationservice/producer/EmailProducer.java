//package com.example.reservationservice.producer;
//
//import com.example.reservationservice.entity.Reservation;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailProducer {
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    private final String EXCHANGE = "email-exchange";
//    private final String ROUTING_KEY = "email-routingKey";
//
//    public void sendEmail(Reservation reservation) {
//        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, reservation);
//    }
//}
