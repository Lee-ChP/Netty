package protocol.impl;

import com.alibaba.fastjson.JSON;
import protocol.Serializer;
import protocol.SerializerAlgorithm;

public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializeAlgorithm() {
        return SerializerAlgorithm.json;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    public <T> T deSerialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
