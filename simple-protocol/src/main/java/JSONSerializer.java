import com.alibaba.fastjson.JSON;

public class JSONSerializer implements Serializer {

    /**
     * 序列化算法标识
     * @return
     */
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.json;
    }

    /**
     * 序列化java对象
     * @param object
     * @return
     */
    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    /**
     * 反序列化java对象
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
