FROM openjdk:8 AS TEMP_BUILD_IMAGE
COPY . .
RUN ./gradlew build

FROM openjdk:8
ENV PROJECT_VERSION=0.0.1-5d75c9b
ENV ARTIFACT_NAME=plex-downloader-api-$PROJECT_VERSION.jar
COPY --from=TEMP_BUILD_IMAGE ./plex-downloader-api/build/libs/$ARTIFACT_NAME .
EXPOSE 8080
CMD java -jar ./$ARTIFACT_NAME
# ENTRYPOINT ["tail", "-f", "/dev/null"]