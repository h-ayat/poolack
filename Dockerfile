FROM hub.hamdocker.ir/sbtscala/scala-sbt:openjdk-17.0.2_1.8.1_2.13.10 AS BUILDER

WORKDIR /app


COPY build.sbt /app/
COPY project/build.properties project/plugins.sbt /app/project/

RUN sbt update

COPY src/ /app/src/

RUN sbt 'universal:stage'

# ----------------------------------------------

FROM hub.hamdocker.ir/openjdk:17.0.2-buster

WORKDIR /app

COPY --from=BUILDER  /app/target/universal/stage/ /app/

# chert and pert
#
CMD ["sh" , "-c" , "bin/poolack"]
#
