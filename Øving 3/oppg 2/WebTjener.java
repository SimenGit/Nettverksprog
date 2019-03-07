import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class WebTjener {

    public static void main(String[] args) throws Exception {

        final int PORT = 80;
        try {
            ServerSocket socketServer = new ServerSocket(PORT);
            System.out.println("Connected...");
            Socket forbindelse = socketServer.accept();

            InputStreamReader inputStreamReader = new InputStreamReader(forbindelse.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            PrintWriter printWriter = new PrintWriter(forbindelse.getOutputStream(), true);

            ArrayList<String> headerLinjer = new ArrayList<String>();
            String aLine = bufferedReader.readLine();
            while (aLine != null) {
                System.out.println("Klient skrev: " + aLine);

                if (aLine.equals("")) {
                    printWriter.println("HTTP/1.1 200 OK");
                    printWriter.println("Content-Type: text/html");
                    printWriter.println("\r\n");
                    printWriter.println("<html><body>");
                    printWriter.println("<h1>Hei og velkommen til en enkel webtjener</h1>");
                    printWriter.println("Header fra klient er: ");
                    printWriter.println("<ul>");

                    for (int i = 0; i < headerLinjer.size(); i++) {
                        printWriter.println("<li>" + headerLinjer.get(i) + "</li>");
                    }

                    printWriter.println("</ul>");
                    printWriter.println("</body></html>");

                    bufferedReader.close();
                    printWriter.close();
                    forbindelse.close();
                    return;
                } else {
                    headerLinjer.add(aLine);
                }

                aLine = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
