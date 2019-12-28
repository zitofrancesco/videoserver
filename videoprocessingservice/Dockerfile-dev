FROM java:8
WORKDIR /app
EXPOSE 9090
ADD ./target/videoprocessingservice-0.0.1-SNAPSHOT.jar ./videoprocessingservice.jar
ADD ./script.sh ./script.sh
CMD java -jar videoprocessingservice.jar
