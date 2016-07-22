package com.tfedorov;

import com.tfedorov.xml.ProductRecordRoot;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.collect.HppcMaps;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by Taras_Fedorov on 6/13/2016.
 */
public class ElasticIndexClient {

    private final Client client;
    private final String index;
    private final String type;


    ElasticIndexClient(String index, String type) {
        this.index = index;
        this.type = type;
        Settings settings = Settings.settingsBuilder().build();

        try {
            client = TransportClient.builder()
                    .settings(settings).build().addTransportAddress(
                            new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
        } catch (UnknownHostException e) {
            throw new IllegalStateException(e);
        }
    }

    String searchAll() {
        SearchResponse searchResponse = client.prepareSearch(index)
                //.addFields("title", "band")
                .setQuery(QueryBuilders.matchAllQuery())
                .execute().actionGet();

        return searchResponse.toString();
    }


    void bulkProducts(List<ProductRecordRoot> data) {
        try {
            bulkProductsUnsafe(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void bulkProductsUnsafe(List<ProductRecordRoot> data) throws IOException {

        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for (ProductRecordRoot product : data) {

            XContentBuilder builder = jsonBuilder();
            builder.startObject();
            for (Map.Entry<String, Object> fields : product.toMap().entrySet()) {
                if (fields != null && fields.getValue() != null)
                    builder.field(fields.getKey(), fields.getValue());
            }

            builder.endObject();
            bulkRequest.add(client.prepareIndex(index, type).setSource(builder));
        }

        bulkRequest.execute().actionGet();
    }

    void deleteById(String id) {
        client.prepareDelete(index,type,id).get();
    }

}
