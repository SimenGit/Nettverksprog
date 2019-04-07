import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread{

    Socket socket;
    ServerThread(Socket socket) {
        this.socket = socket;
    }

    private static int adder(int tall1, int tall2) {
        return tall1 + tall2;
    }
    private static int subtraher(int tall1, int tall2) {
        return tall1 - tall2;
    }

    public void run() {
        System.out.println("Ny Klient koplet til pÃ¥ thread id: " + Thread.currentThread().getId());
        try {
            InputStreamReader leseforbindelse = new InputStreamReader(socket.getInputStream());
            BufferedReader leseren = new BufferedReader(leseforbindelse);
            PrintWriter skriveren = new PrintWriter(socket.getOutputStream(), true);
            skriveren.println("Hei, du har kontakt med tjenersiden!");
            do {
                int svar = -99999999;
                skriveren.println("Skriv tall nr 1");
                int tall1 = Integer.parseInt(leseren.readLine());
                skriveren.println("Skriv tall nr 2");
                int tall2 = Integer.parseInt(leseren.readLine());
                skriveren.println("Velg operasjon, skriv '+' eller '-'");
                String operasjon = leseren.readLine();
                if(operasjon.equals("+".trim())) {
                    svar = adder(tall1, tall2);
                }else if(operasjon.equals("-".trim())) {
                    svar = subtraher(tall1, tall2);
                }else{
                    skriveren.println("Du kan kun bruke '+' eller '-', opprett ny forbindelse.");
                    leseren.close();
                    skriveren.close();
                    socket.close();
                }
                System.out.println("En klient utførte en operasjon, svaret ble: " + svar + " (thread " + Thread.currentThread().getId() + ")");
                skriveren.println("Svaret blir: " + svar); // sender svar til klienten
            }while (leseren.readLine() != null);
            
            skriveren.println("-- Lukker forbindelsen --");
            leseren.close();
            skriveren.close();
            socket.close();

        }catch(IOException ioe) {
            ioe.printStackTrace();
            System.out.println("Error");
        }
    }
}
