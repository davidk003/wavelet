import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler {
    int numOfStrings = 0;
    String storedStrings = "";
    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Server has added %d messages.\n" + storedStrings, numOfStrings);
        }
        else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().equals("/add-message")) {
                String[] parameters = url.getQuery().split("=");
                if(parameters[0].equals("s"))
                {
                    storedStrings += parameters[1] + "\n";
                    numOfStrings++;
                    return storedStrings;
                }
            }
            return "404 Not Found!";
        }
    }
}

class StringServer {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
