FROM maven:3.9.5-eclipse-temurin-17-alpine as temurin


# fetch dependencies
COPY pom.xml .
RUN mvn verify --fail-never
COPY . .
RUN mvn package -DskipTests


# required for strip-debug to work
RUN apk add --no-cache binutils

# Build small JRE image
RUN $JAVA_HOME/bin/jlink \
         --verbose \
         --add-modules ALL-MODULE-PATH \
         --strip-debug \
         --no-man-pages \
         --no-header-files \
         --compress=2 \
         --output /customjre


# main app image
FROM alpine
ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"

COPY --from=temurin /customjre $JAVA_HOME
COPY --from=temurin ./target/SmartSocketEnergyDashboard-0.0.1-SNAPSHOT.jar ./

RUN apk --no-cache add curl

EXPOSE 8181

ENTRYPOINT ["/jre/bin/java", "-jar", "/SmartSocketEnergyDashboard-0.0.1-SNAPSHOT.jar"]