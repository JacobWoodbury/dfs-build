import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Build {

  /**
   * Prints words that are reachable from the given vertex and are strictly
   * shorter than k characters.
   * If the vertex is null or no reachable words meet the criteria, prints
   * nothing.
   *
   * @param vertex the starting vertex
   * @param k      the maximum word length (exclusive)
   */
  public static void printShortWords(Vertex<String> vertex, int k) {
    Set<Vertex<String>> set = new HashSet<>();
    printShortWords(vertex, k, set);

  }

  public static void printShortWords(Vertex<String> vertex, int k, Set<Vertex<String>> unique) {
    if (vertex == null || unique.contains(vertex))
      return;

    if (vertex.data.length() < k) {
      System.out.println(vertex.data);
    }
    unique.add(vertex);
    for (Vertex<String> v : vertex.neighbors) {
      printShortWords(v, k, unique);

    }
    return;
  }

  /**
   * Returns the longest word reachable from the given vertex, including its own
   * value.
   *
   * @param vertex the starting vertex
   * @return the longest reachable word, or an empty string if the vertex is null
   */
  public static String longestWord(Vertex<String> vertex) {
    Set<Vertex<String>> set = getAllV(vertex);
    String longest = "";
    for(Vertex<String> v : set){
      if(v.data.length()>longest.length()) longest = v.data;
    }
    return longest;
  }

  /**
   * Prints the values of all vertices that are reachable from the given vertex
   * and
   * have themself as a neighbor.
   *
   * @param vertex the starting vertex
   * @param <T>    the type of values stored in the vertices
   */
  public static <T> void printSelfLoopers(Vertex<T> vertex) {
    Set<Vertex<T>> set = getAllV(vertex);
    
    for(Vertex<T> v : set){
      if(v.neighbors.contains(v)) System.out.println(v.data);
    }
  }

  /**
   * Determines whether it is possible to reach the destination airport through a
   * series of flights
   * starting from the given airport. If the start and destination airports are
   * the same, returns true.
   *
   * @param start       the starting airport
   * @param destination the destination airport
   * @return true if the destination is reachable from the start, false otherwise
   */
  public static boolean canReach(Airport start, Airport destination) {
    Set<Airport> set = new HashSet<>();
    return canReach(start, destination, set);
    
  }

  public static boolean canReach(Airport current, Airport destination, Set<Airport> unique) {
    if(current == null || unique.contains(current)) return false;
    if (current == destination) return true;
    boolean isFound = false;
    unique.add(current);
    for(Airport a: current.getOutboundFlights()){
      if(a == destination) return true;
      if(canReach(a, destination, unique)) isFound = true;
    }

    return isFound;
  }



  /**
   * Returns the set of all values in the graph that cannot be reached from the
   * given starting value.
   * The graph is represented as a map where each vertex is associated with a list
   * of its neighboring values.
   *
   * @param graph    the graph represented as a map of vertices to neighbors
   * @param starting the starting value
   * @param <T>      the type of values stored in the graph
   * @return a set of values that cannot be reached from the starting value
   */
  public static <T> Set<T> unreachable(Map<T, List<T>> graph, T starting) {
    
    Set<T> newSet = new HashSet<>();
    Set<T> unique = reachable(graph, starting, newSet);
    Set<T> returnSet = new HashSet<>();
  
    for (T val : graph.keySet()){
      if(!unique.contains(val)) returnSet.add(val);
    }
    
    return returnSet;

  }

   public static <T> Set<T> reachable(Map<T, List<T>> graph, T starting, Set<T> reachable) {
    if(starting == null || reachable.contains(starting))  return reachable;
    reachable.add(starting);
    if(graph.get(starting) == null){return reachable;}
    for(T val: graph.get(starting)){
      reachable(graph, val, reachable);
    }
    return reachable;

  }
  

  public static <T> Set<Vertex<T>> getAllV(Vertex<T> vertex) {
    Set<Vertex<T>> set = new HashSet<>();
    return getAllV(vertex, set);
  }

  public static <T> Set<Vertex<T>> getAllV(Vertex<T> vertex, Set<Vertex<T>> unique) {
    if(vertex == null || unique.contains(vertex)) return unique;
    unique.add(vertex);
    for(Vertex<T> v : vertex.neighbors){
      getAllV(v, unique);
    }
    return unique;
  }
}
