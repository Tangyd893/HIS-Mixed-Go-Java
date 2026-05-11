FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

COPY pom.xml ./
COPY common/pom.xml common/
COPY his-auth/pom.xml his-auth/
COPY his-user/pom.xml his-user/
COPY his-clinic/pom.xml his-clinic/
COPY his-emr/pom.xml his-emr/
COPY his-prescription/pom.xml his-prescription/
COPY his-billing/pom.xml his-billing/
COPY his-inpatient/pom.xml his-inpatient/
COPY his-health-record/pom.xml his-health-record/
COPY his-system/pom.xml his-system/

RUN mvn dependency:go-offline -B

COPY . .

RUN mvn package -DskipTests -B

FROM eclipse-temurin:21-jre-alpine

RUN apk add --no-cache tzdata

ENV TZ=Asia/Shanghai

COPY --from=builder /app/*/target/*.jar /app/app.jar

EXPOSE 8096

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
