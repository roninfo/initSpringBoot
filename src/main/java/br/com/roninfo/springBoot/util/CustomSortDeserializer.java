package br.com.roninfo.springBoot.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.IOException;
import java.util.Set;

public class CustomSortDeserializer extends JsonDeserializer<Sort> {

    @Override
    public Sort deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectNode node = jsonParser.getCodec().readTree(jsonParser);
        Sort.Order[] orders = new Sort.Order[node.size()];

        ObjectMapper mapper = (ObjectMapper)jsonParser.getCodec();
        JsonNode jsonNode = (JsonNode)mapper.readTree(jsonParser);

        int i = 0;
        for(JsonNode json : node) {
            JsonNode propertie = this.readJsonNode(jsonNode, "direction");
            if (propertie.asText((String)null) == null) {
                continue;
            }

            orders[i] = new Sort.Order(Sort.Direction.valueOf(json.get("direction").asText()), json.get("property").asText());
            i++;
        }

        return new Sort(orders);
    }

    private JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return (JsonNode)(jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance());
    }
}
