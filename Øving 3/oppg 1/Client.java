import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws UnknownHostException, IOException {

        final int PORT = 8000;
        InetAddress host = InetAddress.getLocalHost();

        Socket socket = new Socket(host.getHostName(), PORT);
        InputStreamReader leseforbindelse = new InputStreamReader(socket.getInputStream());
        BufferedReader leseren = new BufferedReader(leseforbindelse);
        PrintWriter skriveren = new PrintWriter(socket.getOutputStream(), true);

        String innledning1 = leseren.readLine();
        String innledning2 = leseren.readLine();
        System.out.println(innledning1 + "\n" + innledning2);
        Scanner leserFraKommandovindu = new Scanner(System.in);

        String enLinje = leserFraKommandovindu.nextLine();
        while (!enLinje.equals("avslutt".trim().toLowerCase())) {
            skriveren.println(enLinje);  // sender teksten til tjeneren
            String respons = leseren.readLine();  // mottar respons fra tjeneren
            System.out.println("Fra tjenerprogrammet: " + respons);
            enLinje = leserFraKommandovindu.nextLine();
        }
        leseren.close();
        skriveren.close();
        socket.close();
    }
}
