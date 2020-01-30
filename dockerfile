FROM openjdk:8
ENV APP_HOME=/usr/app
WORKDIR $APP_HOME
COPY . .
RUN ./gradlew build
ENV PROJECT_VERSION=0.0.1-dee39ab
ENV ARTIFACT_DIR=./plex-downloader-api/build/libs
EXPOSE 8080
HEALTHCHECK CMD curl --fail http://localhost:8080/api/settings/about || exit 1
CMD java -jar $ARTIFACT_DIR/plex-downloader-api-$PROJECT_VERSION.jar
# ENTRYPOINT ["tail", "-f", "/dev/null"]