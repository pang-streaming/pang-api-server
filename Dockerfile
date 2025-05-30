FROM amazoncorretto:17-alpine

COPY build/libs/pang-api-server-0.0.1-SNAPSHOT.jar app.jar

ENV TZ=Asia/Seoul

WORKDIR /app
COPY .env .env

ENTRYPOINT ["java","-jar","/app.jar","-Duser.timezone=Asia/Seoul"]