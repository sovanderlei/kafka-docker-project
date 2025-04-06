Execute os seguintes comandos:
 
# Construir e rodar os containers
docker-compose down
docker-compose up --build


Para enviar mensagens ao Kafka:
curl -X POST "http://localhost:8080/api/kafka/send?message=HelloKafka"
 
 
para ver as mensagens 
docker exec -it consumer-python sh
python consumer.py



