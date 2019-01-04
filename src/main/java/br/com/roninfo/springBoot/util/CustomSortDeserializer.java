package br.com.roninfo.springBoot.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.data.domain.Sort;

import java.io.IOException;

public class CustomSortDeserializer extends JsonDeserializer<Sort> {

    @Override
    public Sort deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectNode node = jsonParser.getCodec().readTree(jsonParser);
        Sort.Order[] orders = new Sort.Order[node.size()];

//        int i = 0;
//        for(JsonNode json : node) {
//            orders[i] = new Sort.Order(Sort.Direction.valueOf(json.get("direction").asText()), json.get("property").asText());
//            i++;
//        }

        return new Sort(orders);
    }
}
