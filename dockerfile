FROM openjdk:8 AS TEMP_BUILD_IMAGE
COPY build.gradle settings.gradle gradlew .
COPY gradle $APP_HOME/gradle
RUN ./gradlew build || return 0
COPY . .
RUN ./gradlew build

FROM openjdk:8
ENV PROJECT_VERSION=0.0.1-5d75c9b
ENV ARTIFACT_NAME=plex-downloader-api-$PROJECT_VERSION.jar
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/plex-downloader-api/build/libs/$ARTIFACT_NAME .
EXPOSE 8080
CMD java -jar ./$ARTIFACT_NAME
# ENTRYPOINT ["tail", "-f", "/dev/null"]