package com.providers.aws;

import org.apache.kafka.common.config.ConfigData;
import org.apache.kafka.common.config.provider.ConfigProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Retrieve configuration information from AWS SSM.
 */
public class SSMProvider implements ConfigProvider {

    @Override
    public ConfigData get(String path) {
        Map<String, String> data = new HashMap<>();
        return new ConfigData(data);
    }

    /**
     * Retrieve values of all the parameters from SSM.
     *
     * @param path An empty string.
     * @param keys Names of the SSM parameters.
     * @return The values of SSM parameters.
     */
    @Override
    public ConfigData get(String path, Set<String> keys) {
        Map<String, String> data = new HashMap<>();

        for (String key : keys) {
            data.put(key, getParameter(key));
        }

        return new ConfigData(data);
    }

    /**
     * Retrieve the value of a parameter from SSM.
     *
     * @param name Name of the parameter.
     * @return The value of the parameter.
     */
    private String getParameter(String name) {
        Region region = Region.of(System.getenv("AWS_REGION"));

        try (SsmClient client = SsmClient.builder().region(region).build()) {
            GetParameterRequest request = GetParameterRequest.builder()
                    .name(name)
                    .withDecryption(true)
                    .build();
            GetParameterResponse response = client.getParameter(request);
            return response.parameter().value();
        }
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public void configure(Map<String, ?> map) {
    }
}
