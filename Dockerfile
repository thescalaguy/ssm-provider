FROM debezium/connect:latest

# -- See https://debezium.io/blog/2019/12/13/externalized-secrets/
# -- See https://docs.confluent.io/platform/current/connect/security.html
COPY target/ssm-provider-1.0-jar-with-dependencies.jar libs/