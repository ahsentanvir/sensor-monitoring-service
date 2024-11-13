# Sensor Monitoring Service

### How to build and run

* Go to the root folder of this repo and run - "docker build -t sensor-monitoring-service ."
It should result in creation of docker image for this repo
* Run docker compose - "docker-compose up" which should run the following services -
  * RabbitMq
  * A Unix machine with netcat installed
  * Sensor Monitoring Service

### How to test

* Once the above 3 services are successfully running, use the above Unix machine with netcat to send udp messages to Sensor Monitoring Service.
* Eg:- 
  * docker exec -it netcat-service sh
  * nc -u localhost 3344
    * sensor_id=t1; value=75
    * sensor_id=t1; value=21
    * ..
    * ..
* All the data should be logged in Sensor Monitoring Service, including alerts if any.
Check its logs by "docker logs -f sensor-monitoring-service"
