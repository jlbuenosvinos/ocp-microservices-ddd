package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.repository;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockKey;
import org.infinispan.protostream.MessageMarshaller;

import java.io.IOException;

/**
 * Created by jlbuenosvinos.
 */
public class StockKeyMarshaller implements MessageMarshaller<StockKey> {

    @Override
    public String getTypeName() {
        return "stock.StockKey";
    }

    @Override
    public Class<StockKey> getJavaClass() {
        return StockKey.class;
    }

    @Override
    public StockKey readFrom(ProtoStreamReader reader) throws IOException {
        StockKey stockKey = new StockKey();

        Integer storeId = reader.readInt("storeId");
        String productId = reader.readString("productId");
        stockKey.setProductId(productId);
        stockKey.setStoreId(storeId);

        return stockKey;
    }

    @Override
    public void writeTo(MessageMarshaller.ProtoStreamWriter writer, StockKey stockKey) throws IOException {
        writer.writeInt("storeId", stockKey.getStoreId());
        writer.writeString("productId", stockKey.getProductId());
    }

}
