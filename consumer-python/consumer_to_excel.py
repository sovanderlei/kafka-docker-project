import json
import os
from datetime import datetime

import pandas as pd
from kafka import KafkaConsumer
from openpyxl import Workbook
from openpyxl.chart import BarChart, Reference
from openpyxl.utils.dataframe import dataframe_to_rows

# Configuração das pastas
FOLDEREXCEL = "FOLDEREXCEL"
os.makedirs(FOLDEREXCEL, exist_ok=True)  # Cria a pasta se não existir

# Configuração do Kafka
TOPIC_NAME = "meu-topico"
KAFKA_BROKER = "kafka:9092"

consumer = KafkaConsumer(
    TOPIC_NAME,
    bootstrap_servers=KAFKA_BROKER,
    auto_offset_reset="earliest",
    value_deserializer=lambda x: json.loads(x.decode("utf-8"))
)

print("✅ Conectado ao Kafka. Aguardando novas vendas...")

# Lista para armazenar os dados das vendas
sales_data = []

for message in consumer:
    sale = message.value
    sales_data.append(sale)

    # Converte para DataFrame
    df = pd.DataFrame(sales_data)

    # Converte a coluna de data para datetime
    df["saleDate"] = pd.to_datetime(df["saleDate"])
    df["Month"] = df["saleDate"].dt.strftime("%Y-%m")  # Extrai Ano-Mês

    # Agrupa os dados por mês e soma os valores
    df_grouped = df.groupby("Month")["total"].sum().reset_index()

    # Criar arquivo Excel
    file_path = os.path.join(FOLDEREXCEL, f"sales_report_{datetime.now().strftime('%Y%m%d_%H%M%S')}.xlsx")
    wb = Workbook()
    ws = wb.active
    ws.title = "Sales Data"

    # Adiciona os dados ao Excel
    for r in dataframe_to_rows(df, index=False, header=True):
        ws.append(r)

    # Criar gráfico
    chart = BarChart()
    chart.title = "Total Sales per Month"
    chart.x_axis.title = "Month"
    chart.y_axis.title = "Total Sales"

    data = Reference(ws, min_col=7, min_row=1, max_row=len(df_grouped) + 1, max_col=7)
    categories = Reference(ws, min_col=6, min_row=2, max_row=len(df_grouped) + 1)
    chart.add_data(data, titles_from_data=True)
    chart.set_categories(categories)

    ws.add_chart(chart, "J5")

    # Salvar Excel
    wb.save(file_path)
    print(f"📁 Arquivo Excel gerado: {file_path}")
