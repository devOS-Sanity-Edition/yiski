FROM gcr.io/distroless/java21-debian12:latest

ENV APP_HOME=/app

COPY ./yiski-all.jar $APP_HOME/

WORKDIR $APP_HOME

CMD ["yiski-all.jar"]