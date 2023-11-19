import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import java.io.FileInputStream;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import controller.HttpRequestHandler;
import model.GameSession;

public class MainServer {
    private static final int PORT = 443;
    public static final ConcurrentHashMap<String, GameSession> sessions = new ConcurrentHashMap<>();

    public static void main(String[] args) throws Exception {
        // Load the keystore
        KeyStore ks = KeyStore.getInstance("PKCS12");
        FileInputStream fis = new FileInputStream("../keystore.p12");
        char[] password = "id1212".toCharArray(); 
        ks.load(fis, password);

        // Set up key manager factory and SSL context
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, password);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), null, null);

        // Create SSL server socket
        SSLServerSocketFactory ssf = sslContext.getServerSocketFactory();
        SSLServerSocket serverSocket = (SSLServerSocket) ssf.createServerSocket(PORT);
        System.out.println("SSL Server is listening on port " + PORT);

        while (true) {
            SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
            new Thread(new HttpRequestHandler(clientSocket, sessions)).start();
        }
    }
}
