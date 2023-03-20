package demo.config;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import lombok.Generated;
import org.junit.platform.commons.util.StringUtils;

/**
 * @author senreysong
 */
@Generated
public class GsonLong implements JsonSerializer<Long>, JsonDeserializer<Long> {

  @Override
  public Long deserialize(
      JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
    String string = jsonElement.getAsString();
    if (StringUtils.isNotBlank(string)) {
      return Long.valueOf(string);
    }
    return null;
  }

  @Override
  public JsonElement serialize(
      Long aLong, Type type, JsonSerializationContext jsonSerializationContext) {
    return new JsonPrimitive(String.valueOf(aLong));
  }
}
