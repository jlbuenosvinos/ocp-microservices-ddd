package com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.infrastructure.repository;

import com.redhat.jbuenosv.ocpmicroservicesddd.sales.stock.domain.model.StockValue;
import org.infinispan.protostream.MessageMarshaller;

import java.io.IOException;

/**
 * Created by jlbuenosvinos.
 */
public class StockValueMarshaller implements MessageMarshaller<StockValue> {

    @Override
    public String getTypeName() {
        return "stock.StockValue";
    }

    @Override
    public Class<StockValue> getJavaClass() {
        return StockValue.class;
    }

    @Override
    public StockValue readFrom(ProtoStreamReader reader) throws IOException {
        StockValue stockValue = new StockValue();

        Integer storeId = reader.readInt("storeId");
        String productId = reader.readString("productId");
        Integer units = reader.readInt("units");

        stockValue.setProductId(productId);
        stockValue.setStoreId(storeId);
        stockValue.setUnits(units);

        return stockValue;
    }

    @Override
    public void writeTo(ProtoStreamWriter writer, StockValue stockValue) throws IOException {
        writer.writeInt("storeId", stockValue.getStoreId());
        writer.writeString("productId", stockValue.getProductId());
        writer.writeInt("units", stockValue.getUnits());
    }

}
