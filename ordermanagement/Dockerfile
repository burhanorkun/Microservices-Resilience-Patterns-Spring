FROM adoptopenjdk/openjdk11 as build
# Set the current working directory inside the image
WORKDIR /app2
# Copy maven executable to the image
COPY mvnw .
COPY .mvn .mvn
# Copy the pom.xml file
COPY pom.xml .
# Build all the dependencies in preparation to go offline.
# This is a separate step so the dependencies will be cached unless
# the pom.xml file has changed.
RUN ./mvnw dependency:go-offline -B
# Copy the project source
COPY src src
# Package the application
RUN ./mvnw package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
#### Stage 2: A minimal docker image with command to run the app
FROM adoptopenjdk/openjdk11
ARG DEPENDENCY=/app2/target/dependency
# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app2/lib
COPY --from=build ${DEPENDENCY}/META-INF /app2/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app2

ENTRYPOINT ["java","-cp","app2:app2/lib/*","com.orkun.ordermanagement.OrdermanagementApplication"]

#ADD target/*.jar app2.jar
#ENTRYPOINT ["java", "-jar", "app2.jar"]
