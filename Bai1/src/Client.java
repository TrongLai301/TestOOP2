import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String hostName = "localhost";
        int portName = 8000;
        Socket socket;

        {
            try {
                socket = new Socket(hostName,portName);
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter the number: ");
                double number = scanner.nextDouble();

                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF(String.valueOf(number));

                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                double result = Double.parseDouble(dataInputStream.readUTF());
                System.out.println("Result is: "+ result);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
