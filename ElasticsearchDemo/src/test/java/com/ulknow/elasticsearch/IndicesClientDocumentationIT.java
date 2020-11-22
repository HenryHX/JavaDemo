package com.ulknow.elasticsearch;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.LatchedActionListener;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest.AliasActions;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.admin.indices.refresh.RefreshResponse;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsRequest;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.DefaultShardOperationFailedException;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.BroadcastResponse.Shards;
import org.elasticsearch.client.core.ShardsAcknowledgedResponse;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.client.indices.ReloadAnalyzersResponse.ReloadDetails;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class IndicesClientDocumentationIT extends BaseTest {


    @Test
    public void testAnalyze() throws IOException, InterruptedException {

        RestHighLevelClient client = highLevelClient();

        {
            // tag::analyze-builtin-request
            AnalyzeRequest request = AnalyzeRequest.withGlobalAnalyzer("english", // <1>
                    "Some text to analyze", "Some more text to analyze");       // <2>
            // end::analyze-builtin-request
        }

        {
            // tag::analyze-custom-request
            Map<String, Object> stopFilter = new HashMap<>();
            stopFilter.put("type", "stop");
            stopFilter.put("stopwords", new String[]{ "to" });  // <1>
            AnalyzeRequest request = AnalyzeRequest.buildCustomAnalyzer("standard")  // <2>
                    .addCharFilter("html_strip")    // <3>
                    .addTokenFilter("lowercase")    // <4>
                    .addTokenFilter(stopFilter)     // <5>
                    .build("<b>Some text to analyze</b>");
            // end::analyze-custom-request
        }

        {
            // tag::analyze-custom-normalizer-request
            AnalyzeRequest request = AnalyzeRequest.buildCustomNormalizer()
                    .addTokenFilter("lowercase")
                    .build("<b>BaR</b>");
            // end::analyze-custom-normalizer-request

            // tag::analyze-request-explain
            request.explain(true);                      // <1>
            request.attributes("keyword", "type");      // <2>
            // end::analyze-request-explain

            // tag::analyze-execute
            AnalyzeResponse response = client.indices().analyze(request, RequestOptions.DEFAULT);
            // end::analyze-execute

            // tag::analyze-response-tokens
            List<AnalyzeResponse.AnalyzeToken> tokens = response.getTokens();   // <1>
            // end::analyze-response-tokens
            // tag::analyze-response-detail
            DetailAnalyzeResponse detail = response.detail();                   // <1>
            // end::analyze-response-detail

            assertNull(tokens);
            assertNotNull(detail.tokenizer());
        }

        CreateIndexRequest req = new CreateIndexRequest("my_index");
        CreateIndexResponse resp = client.indices().create(req, RequestOptions.DEFAULT);
        assertTrue(resp.isAcknowledged());

        PutMappingRequest pmReq = new PutMappingRequest("my_index")
                .source(XContentFactory.jsonBuilder().startObject()
                        .startObject("properties")
                        .startObject("my_field")
                        .field("type", "text")
                        .field("analyzer", "english")
                        .endObject()
                        .endObject()
                        .endObject());
        AcknowledgedResponse pmResp = client.indices().putMapping(pmReq, RequestOptions.DEFAULT);
        assertTrue(pmResp.isAcknowledged());

        {
            // tag::analyze-index-request
            AnalyzeRequest request = AnalyzeRequest.withIndexAnalyzer(
                    "my_index",         // <1>
                    "my_analyzer",        // <2>
                    "some text to analyze"
            );
            // end::analyze-index-request

            // tag::analyze-execute-listener
            ActionListener<AnalyzeResponse> listener = new ActionListener<AnalyzeResponse>() {
                @Override
                public void onResponse(AnalyzeResponse analyzeTokens) {
                    // <1>
                    System.out.println(analyzeTokens);
                }

                @Override
                public void onFailure(Exception e) {
                    // <2>
                    System.out.println(e);
                }
            };
            // end::analyze-execute-listener

            // use a built-in analyzer in the test
            request = AnalyzeRequest.withField("my_index", "my_field", "some text to analyze");
            // Use a blocking listener in the test
            final CountDownLatch latch = new CountDownLatch(1);
            listener = new LatchedActionListener<>(listener, latch);

            // tag::analyze-execute-async
            client.indices().analyzeAsync(request, RequestOptions.DEFAULT, listener); // <1>
            // end::analyze-execute-async

            assertTrue(latch.await(30L, TimeUnit.SECONDS));
        }

        {
            // tag::analyze-index-normalizer-request
            AnalyzeRequest request = AnalyzeRequest.withNormalizer(
                    "my_index",             // <1>
                    "my_normalizer",        // <2>
                    "some text to analyze"
            );
            // end::analyze-index-normalizer-request
        }

        {
            // tag::analyze-field-request
            AnalyzeRequest request = AnalyzeRequest.withField("my_index", "my_field", "some text to analyze");
            // end::analyze-field-request
        }

    }

    @Test
    public void testIndicesExist() throws IOException {
        RestHighLevelClient client = highLevelClient();

        {
            CreateIndexResponse createIndexResponse = client.indices().create(new CreateIndexRequest("twitter"),
                RequestOptions.DEFAULT);
            assertTrue(createIndexResponse.isAcknowledged());
        }

        {
            // tag::indices-exists-request
            GetIndexRequest request = new GetIndexRequest("twitter"); // <1>
            // end::indices-exists-request

            IndicesOptions indicesOptions = IndicesOptions.strictExpand();
            // tag::indices-exists-request-optionals
            request.local(false); // <1>
            request.humanReadable(true); // <2>
            request.includeDefaults(false); // <3>
            request.indicesOptions(indicesOptions); // <4>
            // end::indices-exists-request-optionals

            // tag::indices-exists-execute
            boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
            // end::indices-exists-execute
            assertTrue(exists);
        }
    }

    @Test
    public void testIndicesExistAsync() throws Exception {
        RestHighLevelClient client = highLevelClient();

        {
//            CreateIndexResponse createIndexResponse = client.indices().create(new CreateIndexRequest("twitter"), RequestOptions.DEFAULT);
//            assertTrue(createIndexResponse.isAcknowledged());
        }

        {
            GetIndexRequest request = new GetIndexRequest("twitter");

            // tag::indices-exists-execute-listener
            ActionListener<Boolean> listener = new ActionListener<Boolean>() {
                @Override
                public void onResponse(Boolean exists) {
                    // <1>
                    System.out.println(exists);
                }

                @Override
                public void onFailure(Exception e) {
                    // <2>
                    System.out.println(e);
                }
            };
            // end::indices-exists-execute-listener

            // Replace the empty listener by a blocking listener in test
            final CountDownLatch latch = new CountDownLatch(1);
            listener = new LatchedActionListener<>(listener, latch);

            // tag::indices-exists-execute-async
            client.indices().existsAsync(request, RequestOptions.DEFAULT, listener); // <1>
            // end::indices-exists-execute-async

            assertTrue(latch.await(30L, TimeUnit.SECONDS));
        }
    }
    @Test
    public void testDeleteIndex() throws IOException {
        RestHighLevelClient client = highLevelClient();

        {
            CreateIndexResponse createIndexResponse = client.indices().create(new CreateIndexRequest("posts"), RequestOptions.DEFAULT);
            assertTrue(createIndexResponse.isAcknowledged());
        }

        {
            // tag::delete-index-request
            DeleteIndexRequest request = new DeleteIndexRequest("my_index"); // <1>
            // end::delete-index-request

            // tag::delete-index-request-timeout
            request.timeout(TimeValue.timeValueMinutes(2)); // <1>
            request.timeout("2m"); // <2>
            // end::delete-index-request-timeout
            // tag::delete-index-request-masterTimeout
            request.masterNodeTimeout(TimeValue.timeValueMinutes(1)); // <1>
            request.masterNodeTimeout("1m"); // <2>
            // end::delete-index-request-masterTimeout
            // tag::delete-index-request-indicesOptions
            request.indicesOptions(IndicesOptions.lenientExpandOpen()); // <1>
            // end::delete-index-request-indicesOptions

            // tag::delete-index-execute
            AcknowledgedResponse deleteIndexResponse = client.indices().delete(request, RequestOptions.DEFAULT);
            // end::delete-index-execute

            // tag::delete-index-response
            boolean acknowledged = deleteIndexResponse.isAcknowledged(); // <1>
            // end::delete-index-response
            assertTrue(acknowledged);
        }

        {
            // tag::delete-index-notfound
            try {
                DeleteIndexRequest request = new DeleteIndexRequest("does_not_exist");
                client.indices().delete(request, RequestOptions.DEFAULT);
            } catch (ElasticsearchException exception) {
                if (exception.status() == RestStatus.NOT_FOUND) {
                    // <1>
                }
            }
            // end::delete-index-notfound
        }
    }

    @Test
    public void testDeleteIndexAsync() throws Exception {
        final RestHighLevelClient client = highLevelClient();

        {
            CreateIndexResponse createIndexResponse = client.indices().create(new CreateIndexRequest("posts"), RequestOptions.DEFAULT);
            assertTrue(createIndexResponse.isAcknowledged());
        }

        {
            DeleteIndexRequest request = new DeleteIndexRequest("posts");

            // tag::delete-index-execute-listener
            ActionListener<AcknowledgedResponse> listener =
                    new ActionListener<AcknowledgedResponse>() {
                @Override
                public void onResponse(AcknowledgedResponse deleteIndexResponse) {
                    // <1>
                }

                @Override
                public void onFailure(Exception e) {
                    // <2>
                }
            };
            // end::delete-index-execute-listener

            // Replace the empty listener by a blocking listener in test
            final CountDownLatch latch = new CountDownLatch(1);
            listener = new LatchedActionListener<>(listener, latch);

            // tag::delete-index-execute-async
            client.indices().deleteAsync(request, RequestOptions.DEFAULT, listener); // <1>
            // end::delete-index-execute-async

            assertTrue(latch.await(30L, TimeUnit.SECONDS));
        }
    }

    @Test
    public void testCreateIndex() throws IOException {
        RestHighLevelClient client = highLevelClient();

        {
            // tag::create-index-request
            CreateIndexRequest request = new CreateIndexRequest("twitter"); // <1>
            // end::create-index-request

            // tag::create-index-request-settings
            request.settings(Settings.builder() // <1>
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
            );
            // end::create-index-request-settings

            {
                // tag::create-index-request-mappings
                request.mapping(// <1>
                        "{\n" +
                        "  \"properties\": {\n" +
                        "    \"message\": {\n" +
                        "      \"type\": \"text\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}", // <2>
                        XContentType.JSON);
                // end::create-index-request-mappings
                CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
                assertTrue(createIndexResponse.isAcknowledged());
            }

            {
                request = new CreateIndexRequest("twitter2");
                //tag::create-index-mappings-map
                Map<String, Object> message = new HashMap<>();
                message.put("type", "text");
                Map<String, Object> properties = new HashMap<>();
                properties.put("message", message);
                Map<String, Object> mapping = new HashMap<>();
                mapping.put("properties", properties);
                request.mapping(mapping); // <1>
                //end::create-index-mappings-map
                CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
                assertTrue(createIndexResponse.isAcknowledged());
            }
            {
                request = new CreateIndexRequest("twitter3");
                //tag::create-index-mappings-xcontent
                XContentBuilder builder = XContentFactory.jsonBuilder();
                builder.startObject();
                {
                    builder.startObject("properties");
                    {
                        builder.startObject("message");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                    }
                    builder.endObject();
                }
                builder.endObject();
                request.mapping(builder); // <1>
                //end::create-index-mappings-xcontent
                CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
                assertTrue(createIndexResponse.isAcknowledged());
            }

            request = new CreateIndexRequest("twitter5");
            // tag::create-index-request-aliases
            request.alias(new Alias("twitter_alias").filter(QueryBuilders.termQuery("user", "kimchy")));  // <1>
            // end::create-index-request-aliases

            // tag::create-index-request-timeout
            request.setTimeout(TimeValue.timeValueMinutes(2)); // <1>
            // end::create-index-request-timeout
            // tag::create-index-request-masterTimeout
            request.setMasterTimeout(TimeValue.timeValueMinutes(1)); // <1>
            // end::create-index-request-masterTimeout
            // tag::create-index-request-waitForActiveShards
            request.waitForActiveShards(ActiveShardCount.from(2)); // <1>
            request.waitForActiveShards(ActiveShardCount.DEFAULT); // <2>
            // end::create-index-request-waitForActiveShards
            {
                CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
                assertTrue(createIndexResponse.isAcknowledged());
            }

            request = new CreateIndexRequest("twitter6");
            // tag::create-index-whole-source
            request.source("{\n" +
                    "    \"settings\" : {\n" +
                    "        \"number_of_shards\" : 1,\n" +
                    "        \"number_of_replicas\" : 0\n" +
                    "    },\n" +
                    "    \"mappings\" : {\n" +
                    "        \"properties\" : {\n" +
                    "            \"message\" : { \"type\" : \"text\" }\n" +
                    "        }\n" +
                    "    },\n" +
                    "    \"aliases\" : {\n" +
                    "        \"twitter_alias\" : {}\n" +
                    "    }\n" +
                    "}", XContentType.JSON); // <1>
            // end::create-index-whole-source

            // tag::create-index-execute
            CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
            // end::create-index-execute

            // tag::create-index-response
            boolean acknowledged = createIndexResponse.isAcknowledged(); // <1>
            boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged(); // <2>
            // end::create-index-response
            assertTrue(acknowledged);
            assertTrue(shardsAcknowledged);
        }
    }

    @Test
    public void testCreateIndexAsync() throws Exception {
        final RestHighLevelClient client = highLevelClient();

        {
            CreateIndexRequest request = new CreateIndexRequest("twitter");

            // tag::create-index-execute-listener
            ActionListener<CreateIndexResponse> listener =
                    new ActionListener<CreateIndexResponse>() {

                @Override
                public void onResponse(CreateIndexResponse createIndexResponse) {
                    // <1>
                }

                @Override
                public void onFailure(Exception e) {
                    // <2>
                }
            };
            // end::create-index-execute-listener

            // Replace the empty listener by a blocking listener in test
            final CountDownLatch latch = new CountDownLatch(1);
            listener = new LatchedActionListener<>(listener, latch);

            // tag::create-index-execute-async
            client.indices().createAsync(request, RequestOptions.DEFAULT, listener); // <1>
            // end::create-index-execute-async

            assertTrue(latch.await(30L, TimeUnit.SECONDS));
        }
    }

    @Test
    public void testPutMapping() throws IOException {
        RestHighLevelClient client = highLevelClient();

        {
            CreateIndexResponse createIndexResponse = client.indices().create(new CreateIndexRequest("twitter"), RequestOptions.DEFAULT);
            assertTrue(createIndexResponse.isAcknowledged());
        }

        {
            // tag::put-mapping-request
            PutMappingRequest request = new PutMappingRequest("twitter"); // <1>
            // end::put-mapping-request

            {
                // tag::put-mapping-request-source
                request.source(
                    "{\n" +
                    "  \"properties\": {\n" +
                    "    \"message\": {\n" +
                    "      \"type\": \"text\"\n" +
                    "    }\n" +
                    "  }\n" +
                    "}", // <1>
                    XContentType.JSON);
                // end::put-mapping-request-source
                AcknowledgedResponse putMappingResponse = client.indices().putMapping(request, RequestOptions.DEFAULT);
                assertTrue(putMappingResponse.isAcknowledged());
            }

            {
                //tag::put-mapping-map
                Map<String, Object> jsonMap = new HashMap<>();
                Map<String, Object> message = new HashMap<>();
                message.put("type", "text");
                Map<String, Object> properties = new HashMap<>();
                properties.put("message", message);
                jsonMap.put("properties", properties);
                request.source(jsonMap); // <1>
                //end::put-mapping-map
                AcknowledgedResponse putMappingResponse = client.indices().putMapping(request, RequestOptions.DEFAULT);
                assertTrue(putMappingResponse.isAcknowledged());
            }
            {
                //tag::put-mapping-xcontent
                XContentBuilder builder = XContentFactory.jsonBuilder();
                builder.startObject();
                {
                    builder.startObject("properties");
                    {
                        builder.startObject("message");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                    }
                    builder.endObject();
                }
                builder.endObject();
                request.source(builder); // <1>
                //end::put-mapping-xcontent
                AcknowledgedResponse putMappingResponse = client.indices().putMapping(request, RequestOptions.DEFAULT);
                assertTrue(putMappingResponse.isAcknowledged());
            }

            // tag::put-mapping-request-timeout
            request.setTimeout(TimeValue.timeValueMinutes(2)); // <1>
            // end::put-mapping-request-timeout

            // tag::put-mapping-request-masterTimeout
            request.setMasterTimeout(TimeValue.timeValueMinutes(1)); // <1>
            // end::put-mapping-request-masterTimeout

            // tag::put-mapping-execute
            AcknowledgedResponse putMappingResponse = client.indices().putMapping(request, RequestOptions.DEFAULT);
            // end::put-mapping-execute

            // tag::put-mapping-response
            boolean acknowledged = putMappingResponse.isAcknowledged(); // <1>
            // end::put-mapping-response
            assertTrue(acknowledged);
        }
    }

    @Test
    public void testPutMappingAsync() throws Exception {
        final RestHighLevelClient client = highLevelClient();

        {
            CreateIndexResponse createIndexResponse = client.indices().create(new CreateIndexRequest("twitter"), RequestOptions.DEFAULT);
            assertTrue(createIndexResponse.isAcknowledged());
        }

        {
            PutMappingRequest request = new PutMappingRequest("twitter");

            // tag::put-mapping-execute-listener
            ActionListener<AcknowledgedResponse> listener =
                new ActionListener<AcknowledgedResponse>() {
                    @Override
                    public void onResponse(AcknowledgedResponse putMappingResponse) {
                        // <1>
                    }

                    @Override
                    public void onFailure(Exception e) {
                        // <2>
                    }
                };
            // end::put-mapping-execute-listener

            // Replace the empty listener by a blocking listener in test
            final CountDownLatch latch = new CountDownLatch(1);
            listener = new LatchedActionListener<>(listener, latch);

            // tag::put-mapping-execute-async
            client.indices().putMappingAsync(request, RequestOptions.DEFAULT, listener); // <1>
            // end::put-mapping-execute-async

            assertTrue(latch.await(30L, TimeUnit.SECONDS));
        }
    }

    @Test
    public void testGetMapping() throws IOException {
        RestHighLevelClient client = highLevelClient();

        {
            CreateIndexResponse createIndexResponse = client.indices().create(new CreateIndexRequest("twitter"), RequestOptions.DEFAULT);
            assertTrue(createIndexResponse.isAcknowledged());
            PutMappingRequest request = new PutMappingRequest("twitter");
            request.source("{ \"properties\": { \"message\": { \"type\": \"text\" } } }",
                XContentType.JSON
            );
            AcknowledgedResponse putMappingResponse = client.indices().putMapping(request, RequestOptions.DEFAULT);
            assertTrue(putMappingResponse.isAcknowledged());
        }

        {
            // tag::get-mappings-request
            GetMappingsRequest request = new GetMappingsRequest(); // <1>
            request.indices("twitter"); // <2>
            // end::get-mappings-request

            // tag::get-mappings-request-masterTimeout
            request.setMasterTimeout(TimeValue.timeValueMinutes(1)); // <1>
            // end::get-mappings-request-masterTimeout

            // tag::get-mappings-request-indicesOptions
            request.indicesOptions(IndicesOptions.lenientExpandOpen()); // <1>
            // end::get-mappings-request-indicesOptions

            // tag::get-mappings-execute
            GetMappingsResponse getMappingResponse = client.indices().getMapping(request, RequestOptions.DEFAULT);
            // end::get-mappings-execute

            // tag::get-mappings-response

            // end::get-mappings-response

            Map<String, String> type = new HashMap<>();
            type.put("type", "text");
            Map<String, Object> field = new HashMap<>();
            field.put("message", type);
            Map<String, Object> expected = new HashMap<>();
            expected.put("properties", field);
        }
    }

    @Test
    public void testGetMappingAsync() throws Exception {
        final RestHighLevelClient client = highLevelClient();

        {
            CreateIndexResponse createIndexResponse = client.indices().create(new CreateIndexRequest("twitter"), RequestOptions.DEFAULT);
            assertTrue(createIndexResponse.isAcknowledged());
            PutMappingRequest request = new PutMappingRequest("twitter");
            request.source("{ \"properties\": { \"message\": { \"type\": \"text\" } } }",
                XContentType.JSON
            );
            AcknowledgedResponse putMappingResponse = client.indices().putMapping(request, RequestOptions.DEFAULT);
            assertTrue(putMappingResponse.isAcknowledged());
        }

        {
            GetMappingsRequest request = new GetMappingsRequest();
            request.indices("twitter");

            // tag::get-mappings-execute-listener
            ActionListener<GetMappingsResponse> listener =
                new ActionListener<GetMappingsResponse>() {
                    @Override
                    public void onResponse(GetMappingsResponse putMappingResponse) {
                        // <1>
                    }

                    @Override
                    public void onFailure(Exception e) {
                        // <2>
                    }
                };
            // end::get-mappings-execute-listener

            // Replace the empty listener by a blocking listener in test
            final CountDownLatch latch = new CountDownLatch(1);
            final ActionListener<GetMappingsResponse> latchListener = new LatchedActionListener<>(listener, latch);

            // tag::get-mappings-execute-async
            client.indices().getMappingAsync(request, RequestOptions.DEFAULT, listener); // <1>
            // end::get-mappings-execute-async

            assertTrue(latch.await(30L, TimeUnit.SECONDS));
        }
    }

    @SuppressWarnings("unused")
    @Test
    public void testGetFieldMapping() throws IOException, InterruptedException {
        RestHighLevelClient client = highLevelClient();

        {
            CreateIndexResponse createIndexResponse = client.indices().create(new CreateIndexRequest("twitter"), RequestOptions.DEFAULT);
            assertTrue(createIndexResponse.isAcknowledged());
            PutMappingRequest request = new PutMappingRequest("twitter");
            request.source(
                "{\n" +
                    "  \"properties\": {\n" +
                    "    \"message\": {\n" +
                    "      \"type\": \"text\"\n" +
                    "    },\n" +
                    "    \"timestamp\": {\n" +
                    "      \"type\": \"date\"\n" +
                    "    }\n" +
                    "  }\n" +
                    "}", // <1>
                XContentType.JSON);
            AcknowledgedResponse putMappingResponse = client.indices().putMapping(request, RequestOptions.DEFAULT);
            assertTrue(putMappingResponse.isAcknowledged());
        }

        // tag::get-field-mappings-request
        GetFieldMappingsRequest request = new GetFieldMappingsRequest(); // <1>
        request.indices("twitter"); // <2>
        request.fields("message", "timestamp"); // <3>
        // end::get-field-mappings-request

        // tag::get-field-mappings-request-indicesOptions
        request.indicesOptions(IndicesOptions.lenientExpandOpen()); // <1>
        // end::get-field-mappings-request-indicesOptions

        {
            // tag::get-field-mappings-execute
            GetFieldMappingsResponse response =
                client.indices().getFieldMapping(request, RequestOptions.DEFAULT);
            // end::get-field-mappings-execute

            // tag::get-field-mappings-response

            // end::get-field-mappings-response
        }

        {
            // tag::get-field-mappings-execute-listener
            ActionListener<GetFieldMappingsResponse> listener =
                new ActionListener<GetFieldMappingsResponse>() {
                    @Override
                    public void onResponse(GetFieldMappingsResponse putMappingResponse) {
                        // <1>
                    }

                    @Override
                    public void onFailure(Exception e) {
                        // <2>
                    }
                };
            // end::get-field-mappings-execute-listener

            // Replace the empty listener by a blocking listener in test
            final CountDownLatch latch = new CountDownLatch(1);
            final ActionListener<GetFieldMappingsResponse> latchListener = new LatchedActionListener<>(listener, latch);
            listener = ActionListener.wrap(r -> {
                latchListener.onResponse(r);
            }, e -> {
                latchListener.onFailure(e);
                fail("should not fail");
            });

            // tag::get-field-mappings-execute-async
            client.indices().getFieldMappingAsync(request, RequestOptions.DEFAULT, listener); // <1>
            // end::get-field-mappings-execute-async

            assertTrue(latch.await(30L, TimeUnit.SECONDS));
        }


    }


    @Test
    public void testOpenIndex() throws Exception {
        RestHighLevelClient client = highLevelClient();

        {
            CreateIndexResponse createIndexResponse = client.indices().create(new CreateIndexRequest("index"), RequestOptions.DEFAULT);
            assertTrue(createIndexResponse.isAcknowledged());
        }

        {
            // tag::open-index-request
            OpenIndexRequest request = new OpenIndexRequest("index"); // <1>
            // end::open-index-request

            // tag::open-index-request-timeout
            request.timeout(TimeValue.timeValueMinutes(2)); // <1>
            request.timeout("2m"); // <2>
            // end::open-index-request-timeout
            // tag::open-index-request-masterTimeout
            request.masterNodeTimeout(TimeValue.timeValueMinutes(1)); // <1>
            request.masterNodeTimeout("1m"); // <2>
            // end::open-index-request-masterTimeout
            // tag::open-index-request-waitForActiveShards
            request.waitForActiveShards(2); // <1>
            request.waitForActiveShards(ActiveShardCount.DEFAULT); // <2>
            // end::open-index-request-waitForActiveShards

            // tag::open-index-request-indicesOptions
            request.indicesOptions(IndicesOptions.strictExpandOpen()); // <1>
            // end::open-index-request-indicesOptions

            // tag::open-index-execute
            OpenIndexResponse openIndexResponse = client.indices().open(request, RequestOptions.DEFAULT);
            // end::open-index-execute

            // tag::open-index-response
            boolean acknowledged = openIndexResponse.isAcknowledged(); // <1>
            boolean shardsAcked = openIndexResponse.isShardsAcknowledged(); // <2>
            // end::open-index-response
            assertTrue(acknowledged);
            assertTrue(shardsAcked);

            // tag::open-index-execute-listener
            ActionListener<OpenIndexResponse> listener =
                    new ActionListener<OpenIndexResponse>() {
                @Override
                public void onResponse(OpenIndexResponse openIndexResponse) {
                    // <1>
                }

                @Override
                public void onFailure(Exception e) {
                    // <2>
                }
            };
            // end::open-index-execute-listener

            // Replace the empty listener by a blocking listener in test
            final CountDownLatch latch = new CountDownLatch(1);
            listener = new LatchedActionListener<>(listener, latch);

            // tag::open-index-execute-async
            client.indices().openAsync(request, RequestOptions.DEFAULT, listener); // <1>
            // end::open-index-execute-async

            assertTrue(latch.await(30L, TimeUnit.SECONDS));
        }

        {
            // tag::open-index-notfound
            try {
                OpenIndexRequest request = new OpenIndexRequest("does_not_exist");
                client.indices().open(request, RequestOptions.DEFAULT);
            } catch (ElasticsearchException exception) {
                if (exception.status() == RestStatus.BAD_REQUEST) {
                    // <1>
                }
            }
            // end::open-index-notfound
        }
    }

    @SuppressWarnings("unused")
    @Test
    public void testRefreshIndex() throws Exception {
        RestHighLevelClient client = highLevelClient();

        {
//            createIndex("index1", Settings.EMPTY);
        }

        {
            // tag::refresh-request
            RefreshRequest request = new RefreshRequest("index1"); // <1>
            RefreshRequest requestMultiple = new RefreshRequest("index1", "index2"); // <2>
            RefreshRequest requestAll = new RefreshRequest(); // <3>
            // end::refresh-request

            // tag::refresh-request-indicesOptions
            request.indicesOptions(IndicesOptions.lenientExpandOpen()); // <1>
            // end::refresh-request-indicesOptions

            // tag::refresh-execute
            RefreshResponse refreshResponse = client.indices().refresh(request, RequestOptions.DEFAULT);
            // end::refresh-execute

            // tag::refresh-response
            int totalShards = refreshResponse.getTotalShards(); // <1>
            int successfulShards = refreshResponse.getSuccessfulShards(); // <2>
            int failedShards = refreshResponse.getFailedShards(); // <3>
            DefaultShardOperationFailedException[] failures = refreshResponse.getShardFailures(); // <4>
            // end::refresh-response

            // tag::refresh-execute-listener
            ActionListener<RefreshResponse> listener = new ActionListener<RefreshResponse>() {
                @Override
                public void onResponse(RefreshResponse refreshResponse) {
                    // <1>
                }

                @Override
                public void onFailure(Exception e) {
                    // <2>
                }
            };
            // end::refresh-execute-listener

            // Replace the empty listener by a blocking listener in test
            final CountDownLatch latch = new CountDownLatch(1);
            listener = new LatchedActionListener<>(listener, latch);

            // tag::refresh-execute-async
            client.indices().refreshAsync(request, RequestOptions.DEFAULT, listener); // <1>
            // end::refresh-execute-async

            assertTrue(latch.await(30L, TimeUnit.SECONDS));
        }

        {
            // tag::refresh-notfound
            try {
                RefreshRequest request = new RefreshRequest("does_not_exist");
                client.indices().refresh(request, RequestOptions.DEFAULT);
            } catch (ElasticsearchException exception) {
                if (exception.status() == RestStatus.NOT_FOUND) {
                    // <1>
                }
            }
            // end::refresh-notfound
        }
    }
    

    @Test
    public void testGetSettings() throws Exception {
        RestHighLevelClient client = highLevelClient();

        {
            Settings settings = Settings.builder().put("number_of_shards", 3).build();
            CreateIndexResponse createIndexResponse = client.indices().create(
                new CreateIndexRequest("index").settings(settings), RequestOptions.DEFAULT);
            assertTrue(createIndexResponse.isAcknowledged());
        }

        // tag::get-settings-request
        GetSettingsRequest request = new GetSettingsRequest().indices("index"); // <1>
        // end::get-settings-request

        // tag::get-settings-request-names
        request.names("index.number_of_shards"); // <1>
        // end::get-settings-request-names

        // tag::get-settings-request-indicesOptions
        request.indicesOptions(IndicesOptions.lenientExpandOpen()); // <1>
        // end::get-settings-request-indicesOptions

        // tag::get-settings-execute
        GetSettingsResponse getSettingsResponse = client.indices().getSettings(request, RequestOptions.DEFAULT);
        // end::get-settings-execute

        // tag::get-settings-response
        String numberOfShardsString = getSettingsResponse.getSetting("index", "index.number_of_shards"); // <1>
        Settings indexSettings = getSettingsResponse.getIndexToSettings().get("index"); // <2>
        Integer numberOfShards = indexSettings.getAsInt("index.number_of_shards", null); // <3>
        // end::get-settings-response

        assertEquals("3", numberOfShardsString);
        assertEquals(Integer.valueOf(3), numberOfShards);

        assertNull("refresh_interval returned but was never set!",
            getSettingsResponse.getSetting("index", "index.refresh_interval"));

        // tag::get-settings-execute-listener
        ActionListener<GetSettingsResponse> listener =
            new ActionListener<GetSettingsResponse>() {
                @Override
                public void onResponse(GetSettingsResponse GetSettingsResponse) {
                    // <1>
                }

                @Override
                public void onFailure(Exception e) {
                    // <2>
                }
            };
        // end::get-settings-execute-listener

        // Replace the empty listener by a blocking listener in test
        final CountDownLatch latch = new CountDownLatch(1);
        listener = new LatchedActionListener<>(listener, latch);

        // tag::get-settings-execute-async
        client.indices().getSettingsAsync(request, RequestOptions.DEFAULT, listener); // <1>
        // end::get-settings-execute-async

        assertTrue(latch.await(30L, TimeUnit.SECONDS));
    }

    @Test
    public void testGetSettingsWithDefaults() throws Exception {
        RestHighLevelClient client = highLevelClient();

        {
            Settings settings = Settings.builder().put("number_of_shards", 3).build();
            CreateIndexResponse createIndexResponse = client.indices().create(
                new CreateIndexRequest("index").settings(settings), RequestOptions.DEFAULT);
            assertTrue(createIndexResponse.isAcknowledged());
        }

        GetSettingsRequest request = new GetSettingsRequest().indices("index");
        request.indicesOptions(IndicesOptions.lenientExpandOpen());

        // tag::get-settings-request-include-defaults
        request.includeDefaults(true); // <1>
        // end::get-settings-request-include-defaults

        GetSettingsResponse getSettingsResponse = client.indices().getSettings(request, RequestOptions.DEFAULT);
        String numberOfShardsString = getSettingsResponse.getSetting("index", "index.number_of_shards");
        Settings indexSettings = getSettingsResponse.getIndexToSettings().get("index");
        Integer numberOfShards = indexSettings.getAsInt("index.number_of_shards", null);

        // tag::get-settings-defaults-response
        String refreshInterval = getSettingsResponse.getSetting("index", "index.refresh_interval"); // <1>
        Settings indexDefaultSettings = getSettingsResponse.getIndexToDefaultSettings().get("index"); // <2>
        // end::get-settings-defaults-response

        assertEquals("3", numberOfShardsString);
        assertEquals(Integer.valueOf(3), numberOfShards);
        assertNotNull("with defaults enabled we should get a value for refresh_interval!", refreshInterval);

        assertEquals(refreshInterval, indexDefaultSettings.get("index.refresh_interval"));
        ActionListener<GetSettingsResponse> listener =
            new ActionListener<GetSettingsResponse>() {
                @Override
                public void onResponse(GetSettingsResponse GetSettingsResponse) {
                }

                @Override
                public void onFailure(Exception e) {
                }
            };

        // Replace the empty listener by a blocking listener in test
        final CountDownLatch latch = new CountDownLatch(1);
        listener = new LatchedActionListener<>(listener, latch);

        client.indices().getSettingsAsync(request, RequestOptions.DEFAULT, listener);
        assertTrue(latch.await(30L, TimeUnit.SECONDS));
    }

    

    

    @Test
    public void testFreezeIndex() throws Exception {
        RestHighLevelClient client = highLevelClient();

        {
            CreateIndexResponse createIndexResponse = client.indices().create(new CreateIndexRequest("index"), RequestOptions.DEFAULT);
            assertTrue(createIndexResponse.isAcknowledged());
        }

        {
            // tag::freeze-index-request
            FreezeIndexRequest request = new FreezeIndexRequest("index"); // <1>
            // end::freeze-index-request

            // tag::freeze-index-request-timeout
            request.setTimeout(TimeValue.timeValueMinutes(2)); // <1>
            // end::freeze-index-request-timeout
            // tag::freeze-index-request-masterTimeout
            request.setMasterTimeout(TimeValue.timeValueMinutes(1)); // <1>
            // end::freeze-index-request-masterTimeout
            // tag::freeze-index-request-waitForActiveShards
            request.setWaitForActiveShards(ActiveShardCount.DEFAULT); // <1>
            // end::freeze-index-request-waitForActiveShards

            // tag::freeze-index-request-indicesOptions
            request.setIndicesOptions(IndicesOptions.strictExpandOpen()); // <1>
            // end::freeze-index-request-indicesOptions

            // tag::freeze-index-execute
            ShardsAcknowledgedResponse openIndexResponse = client.indices().freeze(request, RequestOptions.DEFAULT);
            // end::freeze-index-execute

            // tag::freeze-index-response
            boolean acknowledged = openIndexResponse.isAcknowledged(); // <1>
            boolean shardsAcked = openIndexResponse.isShardsAcknowledged(); // <2>
            // end::freeze-index-response
            assertTrue(acknowledged);
            assertTrue(shardsAcked);

            // tag::freeze-index-execute-listener
            ActionListener<ShardsAcknowledgedResponse> listener =
                new ActionListener<ShardsAcknowledgedResponse>() {
                    @Override
                    public void onResponse(ShardsAcknowledgedResponse freezeIndexResponse) {
                        // <1>
                    }

                    @Override
                    public void onFailure(Exception e) {
                        // <2>
                    }
                };
            // end::freeze-index-execute-listener

            // Replace the empty listener by a blocking listener in test
            final CountDownLatch latch = new CountDownLatch(1);
            listener = new LatchedActionListener<>(listener, latch);

            // tag::freeze-index-execute-async
            client.indices().freezeAsync(request, RequestOptions.DEFAULT, listener); // <1>
            // end::freeze-index-execute-async

            assertTrue(latch.await(30L, TimeUnit.SECONDS));
        }

        {
            // tag::freeze-index-notfound
            try {
                FreezeIndexRequest request = new FreezeIndexRequest("does_not_exist");
                client.indices().freeze(request, RequestOptions.DEFAULT);
            } catch (ElasticsearchException exception) {
                if (exception.status() == RestStatus.BAD_REQUEST) {
                    // <1>
                }
            }
            // end::freeze-index-notfound
        }
    }

    @Test
    public void testUnfreezeIndex() throws Exception {
        RestHighLevelClient client = highLevelClient();

        {
            CreateIndexResponse createIndexResponse = client.indices().create(new CreateIndexRequest("index"), RequestOptions.DEFAULT);
            assertTrue(createIndexResponse.isAcknowledged());
        }

        {
            // tag::unfreeze-index-request
            UnfreezeIndexRequest request = new UnfreezeIndexRequest("index"); // <1>
            // end::unfreeze-index-request

            // tag::unfreeze-index-request-timeout
            request.setTimeout(TimeValue.timeValueMinutes(2)); // <1>
            // end::unfreeze-index-request-timeout
            // tag::unfreeze-index-request-masterTimeout
            request.setMasterTimeout(TimeValue.timeValueMinutes(1)); // <1>
            // end::unfreeze-index-request-masterTimeout
            // tag::unfreeze-index-request-waitForActiveShards
            request.setWaitForActiveShards(ActiveShardCount.DEFAULT); // <1>
            // end::unfreeze-index-request-waitForActiveShards

            // tag::unfreeze-index-request-indicesOptions
            request.setIndicesOptions(IndicesOptions.strictExpandOpen()); // <1>
            // end::unfreeze-index-request-indicesOptions

            // tag::unfreeze-index-execute
            ShardsAcknowledgedResponse openIndexResponse = client.indices().unfreeze(request, RequestOptions.DEFAULT);
            // end::unfreeze-index-execute

            // tag::unfreeze-index-response
            boolean acknowledged = openIndexResponse.isAcknowledged(); // <1>
            boolean shardsAcked = openIndexResponse.isShardsAcknowledged(); // <2>
            // end::unfreeze-index-response
            assertTrue(acknowledged);
            assertTrue(shardsAcked);

            // tag::unfreeze-index-execute-listener
            ActionListener<ShardsAcknowledgedResponse> listener =
                new ActionListener<ShardsAcknowledgedResponse>() {
                    @Override
                    public void onResponse(ShardsAcknowledgedResponse freezeIndexResponse) {
                        // <1>
                    }

                    @Override
                    public void onFailure(Exception e) {
                        // <2>
                    }
                };
            // end::unfreeze-index-execute-listener

            // Replace the empty listener by a blocking listener in test
            final CountDownLatch latch = new CountDownLatch(1);
            listener = new LatchedActionListener<>(listener, latch);

            // tag::unfreeze-index-execute-async
            client.indices().unfreezeAsync(request, RequestOptions.DEFAULT, listener); // <1>
            // end::unfreeze-index-execute-async

            assertTrue(latch.await(30L, TimeUnit.SECONDS));
        }

        {
            // tag::unfreeze-index-notfound
            try {
                UnfreezeIndexRequest request = new UnfreezeIndexRequest("does_not_exist");
                client.indices().unfreeze(request, RequestOptions.DEFAULT);
            } catch (ElasticsearchException exception) {
                if (exception.status() == RestStatus.BAD_REQUEST) {
                    // <1>
                }
            }
            // end::unfreeze-index-notfound
        }
    }

    

    @Test
    public void testReloadSearchAnalyzers() throws Exception {
        RestHighLevelClient client = highLevelClient();
        {
            CreateIndexResponse createIndexResponse = client.indices().create(new CreateIndexRequest("index"), RequestOptions.DEFAULT);
            assertTrue(createIndexResponse.isAcknowledged());
        }

        {
            // tag::reload-analyzers-request
            ReloadAnalyzersRequest request = new ReloadAnalyzersRequest("index"); // <1>
            // end::reload-analyzers-request

            // tag::reload-analyzers-request-indicesOptions
            request.setIndicesOptions(IndicesOptions.strictExpandOpen()); // <1>
            // end::reload-analyzers-request-indicesOptions

            // tag::reload-analyzers-execute
            ReloadAnalyzersResponse reloadResponse = client.indices().reloadAnalyzers(request, RequestOptions.DEFAULT);
            // end::reload-analyzers-execute

            // tag::reload-analyzers-response
            Shards shards = reloadResponse.shards(); // <1>
            Map<String, ReloadDetails> reloadDetails = reloadResponse.getReloadedDetails(); // <2>
            ReloadDetails details = reloadDetails.get("index"); // <3>
            String indexName = details.getIndexName(); // <4>
            Set<String> indicesNodes = details.getReloadedIndicesNodes(); // <5>
            Set<String> analyzers = details.getReloadedAnalyzers();  // <6>
            // end::reload-analyzers-response
            assertNotNull(shards);
            assertEquals("index", indexName);
            assertEquals(1, indicesNodes.size());
            assertEquals(0, analyzers.size());

            // tag::reload-analyzers-execute-listener
            ActionListener<ReloadAnalyzersResponse> listener =
                new ActionListener<ReloadAnalyzersResponse>() {
                    @Override
                    public void onResponse(ReloadAnalyzersResponse reloadResponse) {
                        // <1>
                    }

                    @Override
                    public void onFailure(Exception e) {
                        // <2>
                    }
                };
            // end::reload-analyzers-execute-listener

            // Replace the empty listener by a blocking listener in test
            final CountDownLatch latch = new CountDownLatch(1);
            listener = new LatchedActionListener<>(listener, latch);

            // tag::reload-analyzers-execute-async
            client.indices().reloadAnalyzersAsync(request, RequestOptions.DEFAULT, listener); // <1>
            // end::reload-analyzers-execute-async

            assertTrue(latch.await(30L, TimeUnit.SECONDS));
        }

        {
            // tag::reload-analyzers-notfound
            try {
                ReloadAnalyzersRequest request = new ReloadAnalyzersRequest("does_not_exist");
                client.indices().reloadAnalyzers(request, RequestOptions.DEFAULT);
            } catch (ElasticsearchException exception) {
                if (exception.status() == RestStatus.BAD_REQUEST) {
                    // <1>
                }
            }
            // end::reload-analyzers-notfound
        }
    }

    @SuppressWarnings("unused")
    @Test
    public void testDeleteAlias() throws Exception {
        RestHighLevelClient client = highLevelClient();

        {
            CreateIndexResponse createIndexResponse = client.indices().create(new CreateIndexRequest("index1"), RequestOptions.DEFAULT);
            assertTrue(createIndexResponse.isAcknowledged());
        }
        {
            IndicesAliasesRequest request = new IndicesAliasesRequest();
            AliasActions aliasAction =
                new AliasActions(AliasActions.Type.ADD)
                    .index("index1")
                    .alias("alias1");
            request.addAliasAction(aliasAction);
            AcknowledgedResponse indicesAliasesResponse =
                client.indices().updateAliases(request, RequestOptions.DEFAULT);
            assertTrue(indicesAliasesResponse.isAcknowledged());
        }
        {
            IndicesAliasesRequest request = new IndicesAliasesRequest();
            AliasActions aliasAction =
                new AliasActions(AliasActions.Type.ADD)
                    .index("index1")
                    .alias("alias2");
            request.addAliasAction(aliasAction);
            AcknowledgedResponse indicesAliasesResponse =
                client.indices().updateAliases(request, RequestOptions.DEFAULT);
            assertTrue(indicesAliasesResponse.isAcknowledged());
        }
        {
            // tag::delete-alias-request
            DeleteAliasRequest request = new DeleteAliasRequest("index1", "alias1");
            // end::delete-alias-request

            // tag::delete-alias-request-timeout
            request.setTimeout(TimeValue.timeValueMinutes(2)); // <1>
            // end::delete-alias-request-timeout
            // tag::delete-alias-request-masterTimeout
            request.setMasterTimeout(TimeValue.timeValueMinutes(1)); // <1>
            // end::delete-alias-request-masterTimeout

            // tag::delete-alias-execute
            org.elasticsearch.client.core.AcknowledgedResponse deleteAliasResponse =
                client.indices().deleteAlias(request, RequestOptions.DEFAULT);
            // end::delete-alias-execute

            // tag::delete-alias-response
            boolean acknowledged = deleteAliasResponse.isAcknowledged(); // <1>
            // end::delete-alias-response
            assertTrue(acknowledged);
        }

        {
            DeleteAliasRequest request = new DeleteAliasRequest("index1", "alias2"); // <1>

            // tag::delete-alias-execute-listener
            ActionListener<org.elasticsearch.client.core.AcknowledgedResponse> listener =
                new ActionListener<org.elasticsearch.client.core.AcknowledgedResponse>() {
                    @Override
                    public void onResponse(org.elasticsearch.client.core.AcknowledgedResponse deleteAliasResponse) {
                        // <1>
                    }

                    @Override
                    public void onFailure(Exception e) {
                        // <2>
                    }
                };
            // end::delete-alias-execute-listener

            // Replace the empty listener by a blocking listener in test
            final CountDownLatch latch = new CountDownLatch(1);
            listener = new LatchedActionListener<>(listener, latch);

            // tag::delete-alias-execute-async
            client.indices().deleteAliasAsync(request, RequestOptions.DEFAULT, listener); // <1>
            // end::delete-alias-execute-async

            assertTrue(latch.await(30L, TimeUnit.SECONDS));
        }
    }
}
