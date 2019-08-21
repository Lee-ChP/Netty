import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * 传统的阻塞式io服务器
 * 阻塞模式在大连接数的情况就会有很严重的问题，如客户端连接超时，服务器响应严重延迟，性能无法扩展
 */
public class PlainOioServer {

    public void server(int port) throws Exception {
        final ServerSocket socket = new ServerSocket(port); //端口绑定
        try {
            for (;;) {
                final Socket clientSocket = socket.accept(); //接受一个连接请求
                System.out.println("Accepted Connection from : " + clientSocket);

                //启动一个线程去处理连接
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OutputStream outputStream;
                        try {
                            outputStream = clientSocket.getOutputStream();
                            outputStream.write("Hi!\r\n".getBytes(Charset.forName("UTF-8"))); //将消息发到客户端
                            outputStream.flush(); //刷新缓冲区
                            clientSocket.close(); //写完刷新完关闭连接
                        } catch (IOException e) {
                            e.printStackTrace();
                            try {
                                clientSocket.close();
                            }catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
