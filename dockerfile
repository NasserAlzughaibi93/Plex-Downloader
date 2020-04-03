FROM openjdk:8 AS BUILD
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY build.gradle settings.gradle gradlew $APP_HOME
COPY gradle $APP_HOME/gradle
RUN ./gradlew build || return 0
RUN ./gradlew build || return 0
COPY . .
RUN ./gradlew build

# Reduce final image size.
FROM openjdk:8-alpine
LABEL PROJECT_VERSION=0.0.1-662d600
ENV ARTIFACT_DIR=/plex-downloader-api/build/libs
ENV APP_HOME=/usr/app
WORKDIR $APP_HOME
COPY --from=BUILD $APP_HOME/$ARTIFACT_DIR .
COPY --from=BUILD $APP_HOME/plex-downloader-api/src/main/resources/application.properties .
EXPOSE 8080
HEALTHCHECK CMD curl --fail http://localhost:8080/api/settings/about || exit 1
CMD java -jar ./plex-downloader-api-`awk -F= -v key="info.build.version" '$1==key {print $2}' ./application.properties`.jar
# CMD ["java","-jar", "PlexDownloader-0.0.1-SNAPSHOT.jar"]
# CMD java -jar ./$ARTIFACT_NAME
# ENTRYPOINT ["tail", "-f", "/dev/null"]