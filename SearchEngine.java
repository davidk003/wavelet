import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList exampleDatabase = new ArrayList<String>()
    {
        {add("Hello");add("java");add("test");}
    };

    public int dbContains(String searchedString)
    {
        for(int i = 0; i < exampleDatabase.size(); i++)
        {
            if(exampleDatabase.get(i).toString().contains(searchedString))
            {
                return i;
            }
        }
        return -1;
    }

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return "The following searches are availible: \n" + exampleDatabase.toString();
        }
        else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    if(dbContains(parameters[1])==-1)
                    {
                        return "You searched for " + parameters[1] + ", query was not found!";
                    }
                    return "You searched for " + parameters[1] + ", query was found at " + dbContains(parameters[1]); //location of string
                }
            }
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    if(exampleDatabase.add(parameters[1]))
                    {
                        return String.format("Added %s successfully" , parameters[1]);
                    }
                    else
                    {
                        return String.format("Adding %s failed." , parameters[1]);
                    }
                }
                return "invalid input format";
            }
        }
        return "404 Not Found!";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}

