from kafka import KafkaAdminClient
from kafka.admin import NewTopic

BROKER = 'kafka:9092'  # Endereço do broker Kafka
TOPIC_NAME = 'meu-topico'

def create_topic():
    # Criando o cliente Admin para interagir com o Kafka
    admin_client = KafkaAdminClient(
        bootstrap_servers=BROKER,
        client_id='python-client'
    )

    # Definindo as configurações do novo tópico
    new_topic = NewTopic(
        name=TOPIC_NAME,    # Nome do tópico
        num_partitions=1,   # Número de partições
        replication_factor=1  # Fator de replicação
    )

    try:
        # Tentando criar o tópico
        admin_client.create_topics([new_topic])
        print(f"Tópico '{TOPIC_NAME}' criado com sucesso!")
    except Exception as e:
        print(f"Erro ao criar o tópico: {e}")
    finally:
        # Fechando o cliente Admin
        admin_client.close()

if __name__ == "__main__":
    create_topic()
