== Running Apache Kafka 

To setup Apache Kafka

=== Running via Docker images

Starting a Zookeeper cluster:

[source,bash]
----
docker run -d --name zookeeper jplock/zookeeper:3.4.6
----

Next, we need to start Kafka and link the Zookeeper Linux container to it:

[source,bash]
----
docker run -d --name kafka --link zookeeper:zookeeper ches/kafka
----

Now, that the broker is running, we need to figure out the IP address of it:

[source,bash]
----
docker inspect --format '{{ .NetworkSettings.IPAddress }}' kafka  
----

Use this IP address when inside the `kafka.bootstrap.servers` configuration that our _Producers_ and _Consumers_ can speak to Apache Kafka.

=== Running on Openshift 

For Apache Kafka on Openshift please check this repository:

use the steps following the tutorial at ../voxxed-microservices-2018/module-02.adoc
