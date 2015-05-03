package de.frankbeeh.productbacklogtimeline.service.database;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

import de.frankbeeh.productbacklogtimeline.domain.ProductTimestamp;
import de.frankbeeh.productbacklogtimeline.service.database.mapper.ProductTimestampMapper;

public class ProductTimestampService {
    private ProductTimestampMapper productTimestampMapper;

    public ProductTimestampService(Connection connection) {
        productTimestampMapper = new ProductTimestampMapper(connection);
    }

    public void insert(ProductTimestamp productTimestamp) {
        productTimestampMapper.insert(productTimestamp);
    }

    public ProductTimestamp get(LocalDateTime localDateTime) {
        return productTimestampMapper.get(localDateTime);
    }

    public List<LocalDateTime> getAllIds() {
        return productTimestampMapper.getAllIds();
    }
}
