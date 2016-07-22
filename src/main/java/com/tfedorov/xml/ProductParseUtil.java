package com.tfedorov.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Created by Taras_Fedorov on 6/15/2016.
 */
public class ProductParseUtil {


    public static List<ProductRecordRoot> parseListProducts(String path) {
        try {
            return parseProducts(path);
        } catch (JAXBException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

    }

    private static List<ProductRecordRoot> parseProducts(String path) throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(Products.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Products products = (Products) jaxbUnmarshaller.unmarshal(new File(path));
        System.out.println(products);
        return products.getProductsoList();
    }


}
