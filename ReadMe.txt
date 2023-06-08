#Guide 

#Telecharger kafka kafka-3.4.0-src.tgz (asc, sha512) 

https://kafka.apache.org/downloads 
 
#Lancer la commande:
./gradlew jar -PscalaVersion=2.13.10 

#Pour démarrer Kafka, vous aurez besoin d'un serveur ZooKeeper. 
#Vous pouvez exécuter ZooKeeper en utilisant les commandes suivantes dans des terminaux séparés :

#Dans le répertoire kafka-3.4.0-src 
#Lancer la commande:
bin/zookeeper-server-start.sh config/zookeeper.properties 

#Ensuite, pour démarrer un serveur Kafka, utilisez les commandes suivantes dans des terminaux séparés : 
#Lancer:
bin/kafka-server-start.sh config/server.properties 

#Creer le topic Temperature-Celsius
bin/kafka-topics.sh --create --topic Temperature-Celsius --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

#Creer le topic Temperature-Fahrenheit
bin/kafka-topics.sh --create --topic Temperature-Fahrenheit --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

#Voir la liste des topics
bin/kafka-topics.sh --list --bootstrap-server localhost:9092

#Lancez le programme et attendez
#Le programme principale(main) se trouve dans le Convertisseur.java 

