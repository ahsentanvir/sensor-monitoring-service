package com.ahsen.sensormonitoring.warehouse;

import com.ahsen.sensormonitoring.broker.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

@Component
public class SensorListener {

    @Autowired
    private ProducerService producerService;

    public void listenToPortAndPublish(Integer port, String queueName) {
        // Forever listen to udp server with given port
        try (DatagramSocket socket = new DatagramSocket(port)) {
            String sensorType = queueName.startsWith("temp") ? "Temperature" : "Humidity";
            byte[] receiveBuffer = new byte[1024];
            System.out.println(sensorType + " Sensor started listening on udp port " + port);

            while (true) {
                DatagramPacket packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(packet);
                String receivedData = new String(packet.getData(), 0, packet.getLength());

                System.out.println(sensorType + " Sensor Received data: " + receivedData);

                // publish the data onto appropriate rabbit mq topic
                producerService.sendMessage(queueName, receivedData);
            }
        } catch (Exception e) {
            System.out.println("Not able to publish data from port " + port);
            e.printStackTrace();
        }
    }

}
