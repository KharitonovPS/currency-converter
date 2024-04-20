FROM openjdk:21-jdk-oracle
RUN docker builder prune
RUN echo here
RUN pwd
RUN ls
COPY  build/libs/currency-converter-1.0.0-SNAPSHOT.jar /app/app.jar
WORKDIR /app

CMD java -Xmx1024m -jar /app/app.jar
