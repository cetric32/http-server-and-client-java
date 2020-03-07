import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
  public static void main(String[] args) throws Exception {
    // the host and file arg
    String host_file = args[0];
    //splitting host and file to get on server
    String split[] = host_file.split("/",0);

    //getting the path to get from the server
    String path =  split.length > 1 ? "/" + split[1] : "/ ";

    // getting the port 
    int port = Integer.parseInt(args[1]);

    InetAddress addr = InetAddress.getByName(split[0]);
    Socket socket = new Socket(addr, port);
    boolean autoflush = true;
    PrintWriter out = new PrintWriter(socket.getOutputStream(), autoflush);
    BufferedReader in = new BufferedReader(

    new InputStreamReader(socket.getInputStream()));
    // send an HTTP request to the web server
    out.println("GET " + path + " HTTP/1.1");
    out.println("Host:" + split[0] + ":" + port );
    out.println("Connection: Close");
    out.println();

    // read the response
    boolean loop = true;
    StringBuilder sb = new StringBuilder(8096);
    while (loop) {
      if (in.ready()) {
        int i = 0;
        while (i != -1) {
          i = in.read();
          sb.append((char) i);
        }
        loop = false;
      }
    }
    System.out.println(sb.toString());
    socket.close();
  }
}