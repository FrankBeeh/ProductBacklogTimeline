package de.frankbeeh.productbacklogtimeline.domain;

import java.util.Comparator;

public class ProductTimestampDataComparator implements Comparator<ProductTimestampData> {
    @Override
    public int compare(ProductTimestampData data1, ProductTimestampData data2) {
        return data1.getDateTime().compareTo(data2.getDateTime());
    }
}
