FROM python:3.10

WORKDIR /app

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY . .

RUN mkdir -p /app/FOLDEREXCEL

CMD ["python", "consumer_to_excel.py"]
