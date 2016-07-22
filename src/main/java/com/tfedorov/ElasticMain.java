package com.tfedorov;

import com.tfedorov.xml.ProductParseUtil;
import com.tfedorov.xml.ProductRecordRoot;

import java.util.List;

/**
 * Created by Taras_Fedorov on 6/13/2016.
 */
public class ElasticMain {

    private static final String DEFAULT_PATH = "c:\\Users\\Taras_Fedorov\\Desktop\\elastic\\dataset\\productsActive.xml\\products_0002_1064003_to_1310049.xml";

    public static void main(String... args) {
        System.out.println("****Start****");

        List<ProductRecordRoot> products = ProductParseUtil.parseListProducts(DEFAULT_PATH);

        ElasticIndexClient indexClient = new ElasticIndexClient("index", "type");
        //System.out.println(indexClient.searchAll());
        indexClient.bulkProducts(products);
        indexClient.deleteById("AVVUx8p-aIxuqioVCVuH");
        System.out.println("****End****");
    }
}
