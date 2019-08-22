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

    /**
     * <T> T : <T> 是一种形式，表示用的是泛型，不受类型约束，相当于告诉系统，老子要用泛型编程了； 而 T 代表任意一种类型
     * 反序列化
     */
    public <T> T deSerialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
