//package com.example.reservationservice.listener;
//
//import com.example.reservationservice.entity.Guest;
//import com.example.reservationservice.entity.Reservation;
//import jakarta.mail.internet.MimeMessage;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//@Service
//public class RabbitMQListener {
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    @RabbitListener(queues = "email-queue")
//    public void consumeReservationMessage(Reservation reservation) {
//        try {
//            Guest mainGuest = reservation.getGuests().stream()
//                    .filter(guest -> guest.getEmail() != null && !guest.getEmail().isBlank())
//                    .findFirst()
//                    .orElseThrow(() -> new RuntimeException("No guest with email found!"));
//
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//            helper.setTo(mainGuest.getEmail());
//            helper.setSubject("Reservation Confirmation");
//
//            String emailContent = "Dear " + mainGuest.getName() + ",\n\n" +
//                    "Your reservation is confirmed!\n\n" +
//                    "Reservation Details:\n" +
//                    "- Room Number: " + reservation.getRoomNumber() + "\n" +
//                    "- Check-in Date: " + reservation.getCheckInDate() + "\n" +
//                    "- Check-out Date: " + reservation.getCheckOutDate() + "\n" +
//                    "- Number of Adults: " + reservation.getNumberOfAdults() + "\n" +
//                    "- Number of Children: " + reservation.getNumberOfChildren() + "\n\n" +
//                    "Billing Details:\n" +
//                    "- Total Amount: ₹" + reservation.getBill().getTotalAmount() + "\n" +
//                    "- Tax (18%): ₹" + reservation.getBill().getTax() + "\n" +
//                    "- Final Amount: ₹" + reservation.getBill().getFinalAmount() + "\n" +
//                    "- Payment Method: " + reservation.getBill().getPaymentMethod() + "\n\n" +
//                    "Thank you for booking with us!\n";
//
//            helper.setText(emailContent);
//
//            mailSender.send(message);
//
//            System.out.println("Email sent to: " + mainGuest.getEmail());
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Failed to send email");
//        }
//    }
//}
