# SSMProvider  

Retrieve credentials from AWS Parameter Store when running a connector.

## Usage

To retrieve credentials from SSM, you first need to configure the provider in the worker's settings. Depending on whether you're running the worker in distributed mode or in standalone mode, the specifics will change. 

You'd need to add the following lines to the properties file used by the worker.

```properties
config.providers=ssm
config.providers.ssm.class=com.providers.aws.SSMProvider
``` 

In distributed mode, this can be done by setting appropriate `CONNECT_...` environment variables as shown below. You'll find the complete example in the docker-compose file.

```
- CONNECT_CONFIG_PROVIDERS=ssm
- CONNECT_CONFIG_PROVIDERS_SSM_CLASS=com.providers.aws.SSMProvider
```  

Since the code is packaged in a JAR, it needs to be available in the `CLASSPATH` of the worker. An example of how to do this can be found in the Dockerfile. 

Once your worker is up, and you'd like to configure a connector, replace the sensitive credentials with path to an SSM parameter as shown below.  

```
"database.password": "${ssm:/eng/prod/tenant/password}"
```  

## Build  

You can build the JAR file with Maven.  

```
mvn clean package
```  

## References  

To learn more about securing your Kafka Connect deployment, refer the following pages.

https://docs.confluent.io/platform/current/connect/security.html  
https://debezium.io/blog/2019/12/13/externalized-secrets/