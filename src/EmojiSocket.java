import java.io.*;
import java.net.*;
import java.util.*;

public class EmojiSocket {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);

        while (true) {
            Socket socket = serverSocket.accept();
            new RequestHandler(socket).start();
        }
    }
}

class RequestHandler extends Thread {
    Socket socket;

    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        receiveRequest();
        sendAnswer();
        closeSocket();
    }

    private void receiveRequest() {
        try {
            InputStream in = socket.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            while (reader.read() != -1) {
                if (!reader.ready()) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendAnswer() {
        try {
            OutputStreamWriter writer = new OutputStreamWriter(
                socket.getOutputStream());
            writer.write(Emojis.getRandom() + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void closeSocket() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Emojis {
    private static String[] emojilist = {"🤠", "🚀", "🐭", "🐹", "🐰", "🦊", "🐻",
                                         "🐥", "🐝", "🐌", "🍪", "🔥", "🌟", "🍟",
                                         "🌮", "🍖", "🍕", "🥑", "🍌", "🍊", "🍉"};
    private static int previousEmoji;

    public static String getRandom() {
        int randomEmoji;
        do {
            randomEmoji = new Random().nextInt(emojilist.length);
        } while (randomEmoji == previousEmoji);
        previousEmoji = randomEmoji;
        return emojilist[randomEmoji];
    }
}