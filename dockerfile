FROM openjdk:8
ENV APP_HOME=/usr/app
WORKDIR $APP_HOME
COPY . .
RUN ./gradlew build
LABEL PROJECT_VERSION=0.0.1-1861250
ENV ARTIFACT_DIR=./plex-downloader-api/build/libs
# RUN chmod 777 run.sh
EXPOSE 8080
HEALTHCHECK CMD curl --fail http://localhost:8080/api/settings/about || exit 1
CMD java -jar ./plex-downloader-api/build/libs/plex-downloader-api-`awk -F= -v key="info.build.version" '$1==key {print $2}' ./plex-downloader-api/src/main/resources/application.properties`.jar
# ENTRYPOINT ["tail", "-f", "/dev/null"]