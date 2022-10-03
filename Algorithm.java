import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class Algorithm {
    public static Map<Integer, ArrayList<Integer>> coordMap;
    public static Map<Integer, Map<Integer, adjNode>> adjList;
    public static int budget = 0;
    public static class adjNode {
        public int cost = -1;
        public float dist = -1;
    }
    public static String readFileAsString(String file)throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    public static void initializeData() {
        String jsonString;

        // Coord.json
        try {
            jsonString = readFileAsString("data/Coord.json");
            coordMap = new Gson().fromJson(
                jsonString, new TypeToken<HashMap<Integer, ArrayList<Integer>>>() {}.getType()
            );
        } catch(Exception e) {
            System.out.println("Loading of Coord.json failed");
        }
        
        // G.json
        try {
            jsonString = readFileAsString("data/G.json");
            Map<Integer, ArrayList<Integer>> gMap = new Gson().fromJson(
                jsonString, new TypeToken<HashMap<Integer, ArrayList<Integer>>>() {}.getType()
            );
            Map<Integer, adjNode> innerMap;
            adjList = new HashMap<Integer,Map<Integer, adjNode>>();
            for (Map.Entry<Integer,ArrayList<Integer>> entry : gMap.entrySet()){
                innerMap = new HashMap<Integer, adjNode>();
                for (Integer dest: entry.getValue()){
                    innerMap.put(dest, new adjNode());
                }
                adjList.put(entry.getKey(), innerMap);
            }
        } catch(Exception e) {
            System.out.println("Loading of G.json failed");
        }
        
        // Cost.json
        try {
            jsonString = readFileAsString("data/Cost.json");
            Map<String, Integer> tempMap = new Gson().fromJson(
                jsonString, new TypeToken<HashMap<String, Integer>>() {}.getType()
            );
            String[] adj;
            Map<Integer, adjNode> innerMap; 
            for (Map.Entry<String, Integer> mapping :tempMap.entrySet()){
                adj = mapping.getKey().split(",");
                innerMap = adjList.get(Integer.parseInt(adj[0]));
                innerMap.get(Integer.parseInt(adj[1])).cost = mapping.getValue();
            }
        } catch(Exception e) {
            System.out.println("Loading of Cost.json failed");
        }

        // Dist.json
        try {
            jsonString = readFileAsString("data/Dist.json");
            Map<String, Float> tempMap = new Gson().fromJson(
                jsonString, new TypeToken<HashMap<String, Float>>() {}.getType()
            );
            String[] adj; 
            Map<Integer, adjNode> innerMap; 
            for (Map.Entry<String, Float> mapping :tempMap.entrySet()){
                adj = mapping.getKey().split(",");
                innerMap = adjList.get(Integer.parseInt(adj[0]));
                innerMap.get(Integer.parseInt(adj[1])).dist = mapping.getValue();
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Loading of data completed");
    }

    public abstract void run(int startNode, int endNode);