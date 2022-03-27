package Graph;

import java.util.*;

@SuppressWarnings("unchecked")
public class directedGraph {

    public class Edge {
        int v, w;

        public Edge(int v, int w) {
            this.v = v;
            this.w = w;
        }
    }

    public class Vertex {
        int u;
        ArrayList<Edge> neighbors;

        public Vertex(int u) {
            this.u = u;
            this.neighbors = new ArrayList<>();
        }
    }

    protected Vertex[] graph;

    public directedGraph(int[][] arr) {
        this.graph = new Vertex[arr.length];
        for (int i = 0; i < arr.length; i++)
            graph[i] = new Vertex(i);
        constructFromEdgeMatrix(arr);
    }

    public void display() {
        for (Vertex vertex : graph) {
            String str = "" + vertex.u + " -> ";
            for (Edge edge : vertex.neighbors)
                str += edge.v + ", ";
            System.out.println(str);
        }
    }

    public void constructFromEdgeMatrix(int[][] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++)
            addEdge(this.graph, arr[i][0], arr[i][1], arr[i][2]);
    }

    public void addEdge(Vertex[] graph, int u, int v, int w) {
        graph[u].neighbors.add(new Edge(v, w));
    }

    public void removeEdge(int u, int v, int w) {
        graph[u].neighbors.removeIf((e) -> e.v == v);
    }

    public boolean containsEdge(int u, int v) {
        for (Edge nbr : graph[u].neighbors)
            if (nbr.v == v)
                return true;
        return false;
    }

    public void removeVertex(int u) {
        graph[u].neighbors.clear();
    }

    public int[] inDegree() {
        int[] list = new int[graph.length];
        for (int i = 0; i < graph.length; i++)
            for (Edge e : graph[i].neighbors)
                list[e.v]++;
        return list;
    }

    public int[] outDegree() {
        int[] list = new int[graph.length];
        for (int i = 0; i < graph.length; i++)
            list[i] = graph[i].neighbors.size();
        return list;
    }

    public boolean isEulerCircuitExist() {
        int[] inDegree = inDegree();
        int[] outDegree = outDegree();
        for (int i = 0; i < graph.length; i++)
            if (inDegree[i] != outDegree[i])
                return false;
        return true;
    }

    public boolean isEulerTourExist() {
        int[] inDegree = inDegree();
        int[] outDegree = outDegree();
        int count = 0;
        for (int i = 0; i < graph.length; i++)
            if (inDegree[i] != outDegree[i] + 1 || inDegree[i] + 1 != outDegree[i])
                count++;
        return count <= 2;
    }

    // ? Application of Euler Path
    // *https://leetcode.com/problems/reconstruct-itinerary/
    public List<String> reconstructItinerary(List<List<String>> list) {
        HashMap<String, PriorityQueue<String>> graph = new HashMap<>();
        for (List<String> edges : list) {
            graph.putIfAbsent(edges.get(0), new PriorityQueue<>());
            graph.get(edges.get(0)).add(edges.get(1));
        }

        String src = "JFK";
        this.itinerary = new ArrayList<>();
        reconstructItinerary(graph, src);

        Collections.reverse(itinerary);
        return itinerary;
    }

    List<String> itinerary;

    public void reconstructItinerary(HashMap<String, PriorityQueue<String>> graph, String u) {
        while (graph.get(u) != null && !graph.get(u).isEmpty())
            reconstructItinerary(graph, graph.get(u).remove());
        itinerary.add(u);
    }

    public Vertex[] reverseGraph(Vertex[] graph) {
        int n = graph.length;
        Vertex[] reversedGraph = new Vertex[n];
        for (int i = 0; i < n; i++)
            reversedGraph[i] = new Vertex(i);

        for (int i = 0; i < n; i++)
            for (Edge e : graph[i].neighbors)
                addEdge(reversedGraph, e.v, i, e.w);

        return reversedGraph;
    }

    public Vertex[] mergeTwoGraphs(Vertex[] g1, Vertex[] g2, int n) {
        Vertex[] graph = new Vertex[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new Vertex(i);
            graph[i].neighbors.addAll(g1[i].neighbors);
            graph[i].neighbors.addAll(g2[i].neighbors);
        }
        return graph;
    }

    public void topologicalSortOfEachComponent(Vertex[] graph, int u, boolean[] vis, ArrayList<Integer> path) {
        vis[u] = true;
        path.add(u);
        for (Edge edge : graph[u].neighbors)
            if (!vis[edge.v])
                topologicalSortOfEachComponent(graph, edge.v, vis, path);
    }

    public ArrayList<Integer> topologicalSortUsingDFS(Vertex[] graph) {
        int n = graph.length;
        ArrayList<Integer> path = new ArrayList<>();

        boolean[] vis = new boolean[n];
        for (int i = 0; i < n; i++)
            if (!vis[i])
                topologicalSortOfEachComponent(graph, i, vis, path);

        return path;
    }

    // ? Khan's Algorithm (We can also check if cycle Exists or not)
    public ArrayList<Integer> topologicalSortUsingBFS() {
        int n = graph.length;
        ArrayList<Integer> path = new ArrayList<>();

        int[] inDegree = inDegree();

        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++)
            if (inDegree[i] == 0)
                queue.addLast(i);

        boolean[] vis = new boolean[n];

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int rv = queue.removeFirst();

                if (vis[rv])
                    return new ArrayList<>();

                path.add(rv);

                vis[rv] = true;
                for (Edge edge : graph[rv].neighbors) {
                    if (!vis[edge.v]) {
                        inDegree[edge.v]--;
                        if (inDegree[edge.v] == 0)
                            queue.addLast(edge.v);
                    }
                }
            }
        }

        return path;
    }

    // *https://www.geeksforgeeks.org/given-sorted-dictionary-find-precedence-characters/
    public ArrayList<Integer> alienDictionary(String[] arr) {

        Vertex[] graph = new Vertex[26];

        for (int i = 0; i < arr.length - 1; i++) {
            int a = 0;
            int b = 0;

            while (a < arr[1].length() && b < arr[i + 1].length()) {
                int c1 = arr[i].charAt(a++) - 'a';
                int c2 = arr[i + 1].charAt(b++) - 'a';

                if (graph[c1] == null)
                    graph[c1] = new Vertex(c1);
                if (graph[c2] == null)
                    graph[c2] = new Vertex(c2);

                if (c1 == c2)
                    continue;

                addEdge(graph, c1, c2, 0);
            }
        }
        return topologicalSortUsingDFS(graph);
    }

    public boolean courseSchedules(int n, int[][] arr) {

        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();

        int[] inDeg = new int[n];
        for (int[] edge : arr) {
            graph[edge[0]].add(edge[1]);
            inDeg[edge[1]]++;
        }

        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++)
            if (inDeg[i] == 0)
                queue.addLast(i);

        int count = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int rn = queue.removeFirst();

                count++;

                for (int nbr : graph[rn]) {
                    inDeg[nbr]--;
                    if (inDeg[nbr] == 0)
                        queue.addLast(nbr);
                }
            }
        }

        return count == n;
    }

    public int[] courseSchedules2(int n, int[][] arr) {

        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();

        int[] inDeg = new int[n];
        for (int[] edge : arr) {
            graph[edge[0]].add(edge[1]);
            inDeg[edge[1]]++;
        }

        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++)
            if (inDeg[i] == 0)
                queue.addLast(i);

        int count = 0;
        int[] res = new int[n];
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int rn = queue.removeFirst();

                res[n - 1 - count] = rn;
                count++;

                for (int nbr : graph[rn]) {
                    inDeg[nbr]--;
                    if (inDeg[nbr] == 0)
                        queue.addLast(nbr);
                }
            }
        }

        return (count != n) ? new int[0] : res;
    }

    // ? KosaRaju Algorithm
    public ArrayList<ArrayList<Integer>> stronglyConnectedComponents() {
        int n = graph.length;

        ArrayList<Integer> topologicalSort = topologicalSortUsingBFS();
        Vertex[] reversedGraph = reverseGraph(this.graph);

        ArrayList<ArrayList<Integer>> components = new ArrayList<>();
        boolean[] vis = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (!vis[topologicalSort.get(i)]) {
                ArrayList<Integer> component = new ArrayList<>();
                topologicalSortOfEachComponent(reversedGraph, topologicalSort.get(i), vis, component);
                components.add(component);
            }
        }
        return components;
    }

    public int motherVertex() {
        int n = graph.length;
        ArrayList<Integer> path = new ArrayList<>();

        boolean[] vis = new boolean[n];
        for (int i = 0; i < n; i++)
            if (!vis[i])
                motherVertex(graph, i, vis, path);

        int vertex = path.get(path.size() - 1);

        Arrays.fill(vis, false);
        motherVertex(graph, vertex, vis, null);

        for (boolean node : vis)
            if (!node)
                return -1;

        return vertex;
    }

    public void motherVertex(Vertex[] graph, int u, boolean[] vis, ArrayList<Integer> path) {
        vis[u] = true;
        for (Edge edge : graph[u].neighbors)
            if (!vis[edge.v])
                motherVertex(graph, edge.v, vis, path);
        if (path != null)
            path.add(u);
    }

    public int[] longestPath(int[][] edges, int src, int n) {

        ArrayList<int[]>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();
        for (int i = 0; i < edges.length; i++)
            graph[edges[i][0]].add(new int[] { edges[i][1], edges[i][2] });

        ArrayList<Integer> topologicalSort = topologicalSortUsingDFS(graph);

        int[] pathCost = new int[n];
        Arrays.fill(pathCost, Integer.MIN_VALUE);

        int srcIdx = -1;
        for (int i = 0; i < topologicalSort.size(); i++) {
            if (topologicalSort.get(i) == src) {
                srcIdx = i;
                pathCost[srcIdx] = 0;
            }
            if (srcIdx == -1)
                continue;
            findLongestPath(graph, topologicalSort.get(i), pathCost);
        }
        return pathCost;
    }

    public void findLongestPath(ArrayList<int[]>[] graph, int u, int[] pathCost) {
        for (int[] nbr : graph[u])
            if (pathCost[nbr[0]] < pathCost[u] + nbr[1]) {
                pathCost[nbr[0]] = pathCost[u] + nbr[1];
                findLongestPath(graph, nbr[0], pathCost);
            }
    }

    public void topologicalSortOfEachComponent(
            ArrayList<int[]>[] graph, int u, boolean[] vis, ArrayList<Integer> path) {
        vis[u] = true;
        path.add(u);
        for (int[] edge : graph[u])
            if (!vis[edge[0]])
                topologicalSortOfEachComponent(graph, edge[0], vis, path);
    }

    public ArrayList<Integer> topologicalSortUsingDFS(ArrayList<int[]>[] graph) {
        int n = graph.length;
        ArrayList<Integer> path = new ArrayList<>();

        boolean[] vis = new boolean[n];
        for (int i = 0; i < n; i++)
            if (!vis[i])
                topologicalSortOfEachComponent(graph, i, vis, path);

        return path;
    }

    // *https://leetcode.com/problems/clone-graph/
    class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }

    public Node cloneGraph(Node node) {
        map = new HashMap<>();
        dfs(node, new HashSet<>());
        clone(node, new HashSet<>());
        return map.get(node);
    }

    public HashMap<Node, Node> map;

    public void clone(Node node, HashSet<Node> vis) {
        vis.add(node);
        for (Node nbr : node.neighbors)
            if (!vis.contains(nbr)) {
                clone(nbr, vis);
                map.get(node).neighbors.add(map.get(nbr));
            }
    }

    public void dfs(Node node, HashSet<Node> vis) {
        map.putIfAbsent(node, new Node(node.val));
        vis.add(node);
        for (Node nbr : node.neighbors)
            if (!vis.contains(nbr))
                dfs(nbr, vis);
    }

}
