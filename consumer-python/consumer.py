import time

from kafka import KafkaConsumer

TOPIC = "meu-topico"
BROKER = "kafka:9092"

def connect_kafka():
    """Tenta conectar ao Kafka com retries"""
    retries = 5
    for i in range(retries):
        try:
            print(f"Tentando conectar ao Kafka ({i+1}/{retries})...")
            consumer = KafkaConsumer(
                TOPIC,
                bootstrap_servers=[BROKER],
                auto_offset_reset='earliest',
                enable_auto_commit=True,
                group_id='consumer-group-python'
            )
            print("✅ Conectado ao Kafka!")
            return consumer
        except Exception as e:
            print(f"❌ Erro ao conectar: {e}")
            time.sleep(5)  # Espera 5 segundos antes de tentar novamente

    raise Exception("Não foi possível conectar ao Kafka após várias tentativas.")

def main():
    consumer = connect_kafka()
    print(f"🔄 Ouvindo o tópico '{TOPIC}'...")

    for message in consumer:
        print(f"📩 Mensagem recebida: {message.value.decode('utf-8')}")

if __name__ == "__main__":
    main()
