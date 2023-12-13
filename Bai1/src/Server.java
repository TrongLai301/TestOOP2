import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int portName = 8000;
        try {
            System.out.println("Wait to connect...");
            ServerSocket serverSocket = new ServerSocket(portName);
            Socket socket = serverSocket.accept();
            System.out.println("Complete connect to Server");


            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            double receive = Double.parseDouble(dataInputStream.readUTF());
            System.out.println("Data Receive: " + receive);

            double result = calculate(receive);
            dataOutputStream.writeUTF(String.valueOf(result));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static double calculate(double number) {
        return (number - 32) * ((double) 5 / 9);
    }
}
