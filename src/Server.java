import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server {
    DatagramSocket socket;
    private final int port = 5555;

    public Server() throws SocketException {
        socket = new DatagramSocket(port);
        Thread t = new Thread(() -> {
            try {
                receive();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        t.start();
    }


    void receive() throws IOException {
        DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
        socket.receive(packet);
        byte[] data = packet.getData();
        String str = new String(data, 0, packet.getLength());
        int size = packet.getLength();
        InetAddress srcAddr = packet.getAddress();
        int srcPort = packet.getPort();

        System.out.println(str);

        InetAddress localhost = InetAddress.getLocalHost();
        if (packet.getAddress().equals(localhost)) return;


    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();

        while (true) {
            System.out.println("Waiting...");
            server.receive();

        }
    }




}
