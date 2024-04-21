FROM ubuntu:20.04
RUN docker system prune
RUN docker build --no-cache
RUN  pwd && ls -l

COPY  build/libs/currency-converter-1.0.0-SNAPSHOT.jar /app/app.jar
WORKDIR /app

CMD java -Xmx1024m -jar /app/app.jar
