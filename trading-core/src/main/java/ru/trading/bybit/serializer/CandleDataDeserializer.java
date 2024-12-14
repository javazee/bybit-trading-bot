package ru.trading.bybit.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.trading.bybit.dto.rs.market.CandleData;

import java.io.IOException;
import java.util.Iterator;

public class CandleDataDeserializer extends StdDeserializer<CandleData> {

    protected CandleDataDeserializer() {
        super(CandleData.class);
    }

    @Override
    public CandleData deserialize(final JsonParser jsonParser,
                                  final DeserializationContext deserializationContext) throws IOException, JacksonException {
        final JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        for (Iterator<JsonNode> it = jsonNode.elements(); it.hasNext(); ) {
            JsonNode jn = it.next();
            System.out.println(jn);
        }
        ;
        return CandleData.builder().build();
    }
}
