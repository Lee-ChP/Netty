public interface Serializer {
    /**
     * JSON序列化
     */
    byte JSON_SERIALIZER = 1;
    //实例化实现对象
    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     * 获取序列化算法的标识
     */
    byte getSerializerAlgorithm();

    /**
     * java对象转换成二进制
     * 转成字节数组
     */
    byte[] serialize(Object object);

    /**
     * 二进制转java对象
     * 转成某种类型的java对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);

}
