package com.ulknow.elasticsearch.base;

import org.apache.http.HttpHost;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.search.SearchModule;
import org.junit.Before;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class BaseTest {
    protected String hostName = "47.98.175.96";
    protected int port = 9200;


    protected static RestHighLevelClient restHighLevelClient;

    protected static List<HttpHost> clusterHosts;

    /**
     * A client for the running Elasticsearch cluster
     */
    protected static RestClient client;

    /**
     * A client for the running Elasticsearch cluster configured to take test administrative actions like remove all indexes after the test
     * completes
     */
    protected static RestClient adminClient;

    @Before
    public void initHighLevelClient() throws IOException {
        initClient();
        if (restHighLevelClient == null) {
            restHighLevelClient = new HighLevelClient(client());
        }
    }

    public void initClient() throws IOException {
        if (client == null) {
            assert adminClient == null;
            assert clusterHosts == null;

            List<HttpHost> hosts = new ArrayList<>(3);
            hosts.add(buildHttpHost(hostName, port));

            clusterHosts = unmodifiableList(hosts);
            client = buildClient(restClientSettings(), clusterHosts.toArray(new HttpHost[clusterHosts.size()]));
            adminClient = buildClient(restClientSettings(), clusterHosts.toArray(new HttpHost[clusterHosts.size()]));
        }
        assert client != null;
        assert adminClient != null;
        assert clusterHosts != null;
    }

    protected Settings restClientSettings() {
        Settings.Builder builder = Settings.builder();
        //builder.put(CLIENT_PATH_PREFIX, System.getProperty("tests.rest.client_path_prefix"));
        return builder.build();
    }

    protected final List<HttpHost> getClusterHosts() {
        return clusterHosts;
    }

    protected RestClient buildClient(Settings settings, HttpHost[] hosts) throws IOException {
        RestClientBuilder builder = RestClient.builder(hosts);
        builder.setStrictDeprecationMode(true);
        return builder.build();
    }


    protected HttpHost buildHttpHost(String host, int port) {
        return new HttpHost(host, port, getProtocol());
    }

    protected String getProtocol() {
        return "http";
    }

    private static class HighLevelClient extends RestHighLevelClient {
        private HighLevelClient(RestClient restClient) {
            super(restClient, (client) -> {},
                    new SearchModule(Settings.EMPTY, false, Collections.emptyList()).getNamedXContents());
        }
    }

    protected static RestClient client() {
        return client;
    }

    protected static RestHighLevelClient highLevelClient() {
        return restHighLevelClient;
    }

    protected static void createIndex(String name, Settings settings) throws IOException {
        createIndex(name, settings, "");
    }

    protected static void createIndex(String name, Settings settings, String mapping) throws IOException {
        final Request request = new Request("PUT", "/" + name);
        request.setJsonEntity("{ \"settings\": " + Strings.toString(settings) + ", \"mappings\" : {" + mapping + "} }");
        adminClient.performRequest(request);
    }
}
