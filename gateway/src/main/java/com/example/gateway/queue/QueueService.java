package com.example.gateway.queue;

import com.example.lab2.gateway.CarAndPaymentInfo;
import com.example.lab2.gateway.CarServiceProxy;
import com.example.lab2.gateway.PaymentServiceProxy;
import com.example.lab2.gateway.RentalServiceProxy;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.*;

@Component
public class QueueService {
    private static BlockingQueue<QueueRequest> eventQueue = null;

    private final CarServiceProxy carServiceProxy;
    private final RentalServiceProxy rentalServiceProxy;
    private final PaymentServiceProxy paymentServiceProxy;

    public QueueService(CarServiceProxy carServiceProxy, RentalServiceProxy rentalServiceProxy, PaymentServiceProxy paymentServiceProxy) {
        this.carServiceProxy = carServiceProxy;
        this.rentalServiceProxy = rentalServiceProxy;
        this.paymentServiceProxy = paymentServiceProxy;
    }

    public void putQueueRequest(QueueRequest queueRequest) {
        try {
            if(eventQueue == null){
                eventQueue = new LinkedBlockingQueue<>();
                RequestProcessor requestProcessor = new RequestProcessor();
                requestProcessor.start();
            }
            eventQueue.put(queueRequest);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }


    class RequestProcessor extends Thread {
        @Override
        public void run() {
            while(true) {
                QueueRequest queueRequest = null;
                try {
                    int timesleep = 10000;
                    Thread.sleep(timesleep);
                    queueRequest = eventQueue.take();
                    //System.out.println(queueRequest.getRequestType());
                    //System.out.println(RequestType.CANCEL_USER_RENTAL);
                    //System.out.println(RequestType.CANCEL_PAYMENT);
                    //System.out.println(RequestType.FINISH_USER_RENTAL);
                    //System.out.println(RequestType.UPDATE_CAR_RESERVE);

                    if (queueRequest.getRequestType().equals(RequestType.CANCEL_USER_RENTAL)){
                        ResponseEntity<CarAndPaymentInfo> cancel_response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                        try {
                            cancel_response = rentalServiceProxy.cancelRental(queueRequest.getUsername(), queueRequest.getRentalUid());
                        } catch (Exception e) {
                            QueueService.this.putQueueRequest(queueRequest);
                        }

                        try {
                            carServiceProxy.updateCar(cancel_response.getBody().getCarUid(), true);
                        } catch (Exception e) {
                            QueueService.this.putQueueRequest(queueRequest);
                        }

                        try {
                            paymentServiceProxy.cancelPayment(cancel_response.getBody().getPaymentUid());
                        } catch (Exception e) {
                            QueueService.this.putQueueRequest(queueRequest);
                        }
                    }

                    if (queueRequest.getRequestType().equals(RequestType.FINISH_USER_RENTAL)){
                        ResponseEntity<UUID> finish_response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                        try {
                            finish_response = rentalServiceProxy.finishRental(queueRequest.getUsername(), queueRequest.getRentalUid());
                            carServiceProxy.updateCar(finish_response.getBody(), true);
                        } catch (Exception e) {
                            QueueService.this.putQueueRequest(queueRequest);
                        }

                        try {
                            carServiceProxy.updateCar(finish_response.getBody(), true);
                        } catch (Exception e) {
                            QueueService.this.putQueueRequest(queueRequest);
                        }
                    }

                    if (queueRequest.getRequestType().equals(RequestType.UPDATE_CAR_RESERVE)){
                        try {
                            carServiceProxy.updateCar(queueRequest.getCarUid(), queueRequest.getAvailability());
                        } catch (Exception e) {
                            QueueService.this.putQueueRequest(queueRequest);
                        }
                    }

                    if (queueRequest.getRequestType().equals(RequestType.CANCEL_PAYMENT)){
                        try {
                            paymentServiceProxy.cancelPayment(queueRequest.getPaymentUid());
                        } catch (Exception e) {
                            QueueService.this.putQueueRequest(queueRequest);
                        }
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}