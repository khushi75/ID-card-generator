import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class client {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the student name whose data you want to view: ");
        String name = sc.nextLine();

        Socket socket = new Socket("localhost", 8000); // connect to server
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        out.writeObject(name); // send student name to server
        Object record;
        while ((record = in.readObject()) != null) { // receive record from server
            System.out.println(record);
        }

        out.close();
        in.close();
        socket.close();
    }
}
