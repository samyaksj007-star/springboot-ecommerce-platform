//package com.spring.sakura.Controller;
//
//import java.util.Map;
//
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.razorpay.Order;
//import com.razorpay.RazorpayClient;
//
//@RestController
//@RequestMapping("/api/payment")
//public class PaymentController {
//
//    @Value("${razorpay.key.id}")
//    private String razorpayKeyId;
//
//    @Value("${razorpay.key.secret}")
//    private String razorpaySecret;
//
//    @PostMapping("/create-order")
//    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> data) {
//        try {
//            RazorpayClient client = new RazorpayClient(razorpayKeyId, razorpaySecret);
//
//            JSONObject orderRequest = new JSONObject();
//            int amount = (int) data.get("amount"); // amount in paise
//            orderRequest.put("amount", amount);
//            orderRequest.put("currency", "INR");
//            orderRequest.put("receipt", "txn_" + System.currentTimeMillis());
//            orderRequest.put("payment_capture", 1);
//
//            Order order = client.orders.create(orderRequest);
//
//            return ResponseEntity.ok(order.toString());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }
//}
