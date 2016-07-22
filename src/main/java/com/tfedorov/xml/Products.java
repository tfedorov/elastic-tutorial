package com.tfedorov.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Created by Taras_Fedorov on 6/15/2016.
 */
@XmlRootElement(name = "products")
public class Products {

    @XmlElement(name = "product")
    private ArrayList<ProductRecordRoot> productList = null;


    public ArrayList<ProductRecordRoot> getProductsoList() {
        return productList;
    }

    public void setProductsoList(ArrayList<ProductRecordRoot> productList) {
        this.productList = productList;
    }

}
