package de.frankbeeh.productbacklogtimeline.service.database;

import java.sql.Connection;

import de.frankbeeh.productbacklogtimeline.domain.ProductBacklogItemData;
import de.frankbeeh.productbacklogtimeline.service.database.mapper.ProductBacklogItemMapper;

public class ProductBacklogItemService {

    private ProductBacklogItemMapper mapper;

	public ProductBacklogItemService(Connection connection) {
    	mapper = new ProductBacklogItemMapper(connection);
    }
    
    public void create(ProductBacklogItemData productBacklogItemData){
    	mapper.insert(productBacklogItemData);
    }

}
