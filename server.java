import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.sql.*;

public class server {
    
    public static void main(String[] args)throws Exception  {
    	
    	// Get the local host address
        InetAddress localhost = InetAddress.getLocalHost();
        String ipAddress = localhost.getHostAddress();
        System.out.println("Server running on IP address: " + ipAddress);
    	
    	
        int port = 8000;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started and listening on port " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress().getHostAddress());
                Thread clientThread = new ClientThread(socket);
                clientThread.start();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    // Ignore
                }
            }
        }
    }
    
    private static class ClientThread extends Thread {
        private Socket socket;
        
        public ClientThread(Socket socket) {
            this.socket = socket;
        }
        
        public void run() {
            ObjectInputStream in = null;
            ObjectOutputStream out = null;
            try {
                in = new ObjectInputStream(socket.getInputStream());
                out = new ObjectOutputStream(socket.getOutputStream());
                String name = (String) in.readObject();
                String jdbcurl = "jdbc:mysql://localhost:3306/sampledb";
                String username = "root";
                String password = "Khushi_31";
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(jdbcurl, username, password);
                String sql = "SELECT * FROM student WHERE Name = ?";
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, name);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    out.writeObject(rs.getString("ID"));
                    out.writeObject(rs.getString("Name"));
                    out.writeObject(rs.getString("Branch"));
                    out.writeObject(rs.getString("Passout_year"));
                    out.writeObject(rs.getString("City"));
                    out.writeObject(rs.getString("Contact"));
                    
                }
//                if (!rs.isBeforeFirst()) {
//                    out.writeObject("No records found");
//                }
                out.writeObject(null); // Signal end of data
                rs.close();
                pstmt.close();
                connection.close();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // Ignore
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        // Ignore
                    }
                }
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // Ignore
                    }
                }
            }
        }
    }
}
