Guide----------------------------------------------------------------------------------------------------------------------------------------------------------

Outils utilisés :  
 
- openjdk 19.0.2 2023-01-17 
- IntelliJ 
- Framework Kafka 
- Prometheus 
- Grafana 

Télécharger Kafka kafka-3.4.0-src.tgz (asc, sha512) : 

Lien de téléchargement : https://kafka.apache.org/downloads 

Exécuter la commande suivante pour construire Kafka : 

./gradlew jar -PscalaVersion=2.13.10 

Pour démarrer Kafka, vous aurez besoin d'un serveur ZooKeeper. Vous pouvez exécuter ZooKeeper en utilisant les commandes suivantes dans des terminaux séparés : 
 
Dans le répertoire kafka-3.4.0-src 

bin/zookeeper-server-start.sh config/zookeeper.properties 

Ensuite, pour démarrer un serveur Kafka, utilisez les commandes suivantes dans des terminaux séparés : 

bin/kafka-server-start.sh config/server.properties 

Créer les topics sur Kafka : 

Temperature-Celsius 
./bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --topic Temperature-Celsius 

Temperature-Fahrenheit 
./bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --topic Temperature-Fahrenheit 

Télécharger Prometheus : 

Lien de téléchargement : https://prometheus.io/download/ 

Modifier le fichier prometheus.yml par celui se trouvant sur GitHub : 

Lien GitHub : https://github.com/OdioumaCoulibaly/Projet_Prog_Even.git

Accéder à Prometheus via : http://127.0.0.1:9090/ 

Dans l'interface de Prometheus, ajouter les graphiques suivants : 

- nombre_de_production 
- temperature_en_fahrenheit 

Télécharger Grafana pour Debian et Ubuntu depuis la source : 

Exécuter les commandes suivantes : 

sudo apt-get install -y adduser libfontconfig1 
wget https://dl.grafana.com/oss/release/grafana_10.0.0~preview_amd64.deb 
sudo dpkg -i grafana_10.0.0~preview_amd64.deb 
 
Démarrer Grafana : 
sudo /bin/systemctl daemon-reload 

sudo /bin/systemctl enable grafana-server 

### You can start grafana-server by executing 

sudo /bin/systemctl start grafana-server 

Accéder à Grafana via : http://127.0.0.1:3000/ 

Identifiants : 
- Utilisateur : admin 
- Mot de passe : admin 
 
Importer le tableau de bord (Dashboard) se trouvant sur GitHub avec le nom : 

- dashbord_java_app-graphana.json 

Lien GitHub : https://github.com/OdioumaCoulibaly/Projet_Prog_Even.git
 
