package Graph;

import java.util.*;

@SuppressWarnings("unchecked")
public class undirectedGraph {

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

    private Vertex[] graph;
    protected int SIZE;

    public undirectedGraph(int[][] arr, int n) {
        this.SIZE = n;
        clear();
        constructFromEdgeMatrix(arr);
    }

    public void clear() {
        this.graph = new Vertex[SIZE];
        for (int i = 0; i < SIZE; i++)
            graph[i] = new Vertex(i);
    }

    public void display() {
        for (Vertex vertex : graph) {
            String str = "" + vertex.u + " -> ";
            for (Edge edge : vertex.neighbors)
                str += edge.v + ", ";
            System.out.println(str);
        }
    }

    public Vertex[] constructFromEdgeMatrix(int[][] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++)
            addEdge(graph, arr[i][0], arr[i][1], arr[i][2]);
        return graph;
    }

    public void addEdge(Vertex[] graph, int u, int v, int w) {
        graph[u].neighbors.add(new Edge(v, w));
        graph[v].neighbors.add(new Edge(u, w));
    }

    public void removeEdge(int u, int v, int w) {
        graph[u].neighbors.removeIf((e) -> e.v == v);
        graph[v].neighbors.removeIf((e) -> e.v == u);
    }

    public boolean containsEdge(int u, int v) {
        for (Edge nbr : graph[u].neighbors)
            if (nbr.v == v)
                return true;
        return false;
    }

    public void removeVertex(int u) {
        for (Edge e : graph[u].neighbors)
            graph[e.v].neighbors.removeIf((edge) -> edge.v == u);
        graph[u].neighbors.clear();
    }

    public ArrayList<Integer> dft(int u) {
        ArrayList<Integer> list = new ArrayList<>();
        dft(u, new boolean[graph.length], list);
        return list;
    }

    private void dft(int u, boolean[] vis, ArrayList<Integer> list) {
        vis[u] = true;
        list.add(u);
        for (Edge edge : graph[u].neighbors)
            if (!vis[edge.v])
                dft(edge.v, vis, list);
    }

    // ? DFS
    public boolean hasPath(int u, int v, boolean[] vis) {
        if (u == v)
            return true;
        vis[u] = true;
        boolean res = false;
        for (Edge edge : graph[u].neighbors) {
            if (!vis[edge.v])
                res = res || hasPath(edge.v, v, vis);
        }
        vis[u] = false;
        return res;
    }

    public void allPaths(int u, int v, boolean[] vis, String path) {
        if (u == v) {
            System.out.println(path);
            return;
        }
        vis[u] = true;
        for (Edge edge : graph[u].neighbors) {
            if (!vis[edge.v])
                allPaths(edge.v, v, vis, path + edge.v + " -> ");
        }
        vis[u] = false;
    }

    public void preOrder(int u, boolean[] vis) {
        System.out.println(u + " -> ");
        vis[u] = true;
        for (Edge edge : graph[u].neighbors) {
            if (!vis[edge.v])
                preOrder(edge.v, vis);
        }
        vis[u] = false;
    }

    public void postOrder(int u, boolean[] vis) {
        vis[u] = true;
        for (Edge edge : graph[u].neighbors) {
            if (!vis[edge.v])
                postOrder(edge.v, vis);
        }
        vis[u] = false;
        System.out.println(u + " -> ");
    }

    public boolean hasHamiltonPath(int u, int v, int count, boolean[] vis, String path) {
        if (u == v && count == vis.length) {
            System.out.println(path);
            return true;
        }
        vis[u] = true;
        boolean res = false;
        for (Edge edge : graph[u].neighbors) {
            if (!vis[edge.v])
                res = res || hasHamiltonPath(edge.v, v, count + 1, vis, path + edge.v + " -> ");
        }
        vis[u] = false;
        return res;
    }

    public boolean hasHamiltonCycle(int u, int start, int v, int count, boolean[] vis, String path) {
        if (u == v && count == vis.length && containsEdge(start, v)) {
            System.out.println(path);
            return true;
        }
        vis[u] = true;
        boolean res = false;
        for (Edge edge : graph[u].neighbors) {
            if (!vis[edge.v])
                res = res || hasHamiltonCycle(edge.v, start, v, count + 1, vis, path + edge.v + " -> ");
        }
        vis[u] = false;
        return res;
    }

    public int[] getDegree() {
        int[] degree = new int[graph.length];
        for (int i = 0; i < graph.length; i++)
            degree[i] = graph[i].neighbors.size();
        return degree;
    }

    public boolean isEulerCircuitExist() {
        int[] degree = getDegree();
        for (int i = 0; i < graph.length; i++)
            if (degree[i] % 2 != 0)
                return false;
        return true;
    }

    public boolean isEulerTourExist() {
        int[] degree = getDegree();
        int count = 0;
        for (int i = 0; i < graph.length; i++)
            if (degree[i] % 2 != 0)
                count++;
        return count <= 2;
    }

    // *https://www.hackerearth.com/practice/algorithms/graphs/topological-sort/practice-problems/algorithm/oliver-and-the-game-3/
    public int[] oliverAndBob(int[][] edges, int n, int[][] queries, int src) {

        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();
        for (int i = 0; i < edges.length; i++)
            graph[edges[i][0]].add(edges[i][1]);

        this.disc = this.end = this.vis = new int[n];
        time = 0;

        int[] res = new int[queries.length];
        oliverAndBob(graph, src);

        int i = 0;
        for (int[] q : queries) {
            int x = q[1];
            int y = q[2];

            if (q[0] == 0) {
                if (disc[x] < disc[y] && end[x] > end[y])
                    res[i] = 1;
            } else {
                if (disc[y] < disc[x] && end[y] > end[x])
                    res[i] = 1;
            }

            i++;
        }

        return res;
    }

    int[] end;

    public void oliverAndBob(ArrayList<Integer>[] graph, int u) {
        disc[u] = time++;

        vis[u] = 1;
        for (int nbr : graph[u])
            if (vis[nbr] != 1)
                oliverAndBob(graph, nbr);

        end[u] = time++;
    }

    public ArrayList<Integer> articulationPoint(int[][] edges, int src, int n) {

        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();
        for (int i = 0; i < edges.length; i++)
            graph[edges[i][0]].add(edges[i][1]);

        parent = disc = low = arPt = vis = new int[n];
        time = 0;
        parent[src] = -1;

        articulationPointDFS(graph, src);

        ArrayList<Integer> arPoints = new ArrayList<>();
        int i = 0;
        for (int ele : arPt)
            if (ele == 1) {
                arPoints.add(i);
                i++;
            }
        return arPoints;
    }

    int[] parent, disc, low, arPt, vis;
    int time;

    public void articulationPointDFS(ArrayList<Integer>[] graph, int u) {
        disc[u] = time++;
        int countOfCallsMadeBySource = 0;

        vis[u] = 1;

        for (int nbr : graph[u]) {
            if (parent[u] == nbr) {
            } else if (vis[u] == 1)
                low[u] = Math.min(low[u], disc[nbr]);
            else {
                parent[nbr] = u;
                countOfCallsMadeBySource++;

                articulationPointDFS(graph, nbr);

                low[u] = Math.min(low[u], low[nbr]);

                if (parent[u] == -1) {
                    if (countOfCallsMadeBySource >= 2)
                        arPt[u] = 1;
                } else {
                    if (low[nbr] >= disc[nbr])
                        arPt[u] = 1;
                }
            }
        }

    }

    public ArrayList<int[]> bridges(int[][] edges, int src, int n) {

        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();
        for (int i = 0; i < edges.length; i++)
            graph[edges[i][0]].add(edges[i][1]);

        parent = disc = low = vis = new int[n];
        time = 0;
        parent[src] = -1;
        bridges = new ArrayList<>();

        bridgesDFS(graph, src);

        return bridges;
    }

    ArrayList<int[]> bridges;

    public void bridgesDFS(ArrayList<Integer>[] graph, int u) {
        disc[u] = time++;

        vis[u] = 1;

        for (int nbr : graph[u]) {
            if (parent[u] == nbr) {
            } else if (vis[u] == 1)
                low[u] = Math.min(low[u], disc[nbr]);
            else {
                parent[nbr] = u;

                bridgesDFS(graph, nbr);

                low[u] = Math.min(low[u], low[nbr]);
                if (low[nbr] > disc[u])
                    bridges.add(new int[] { u, nbr });
            }
        }
    }

    // ? Approximation Algorithm
    // ***https://www.geeksforgeeks.org/vertex-cover-problem-set-1-introduction-approximate-algorithm-2/
    public HashSet<Integer> minVertexCover(int[][] edges, int n) {

        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();

        for (int i = 0; i < edges.length; i++)
            graph[edges[i][0]].add(edges[i][1]);

        boolean[] vis = new boolean[n];

        HashSet<Integer> cover = new HashSet<>();
        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];

            if (!graph[u].contains(v))
                continue;

            if (!vis[u]) {
                vis[u] = true;
                cover.add(u);
                graph[u].clear();
            }
            if (!vis[v]) {
                vis[v] = true;
                cover.add(v);
                graph[v].clear();
            }

        }
        return cover;
    }

    public int edgeCover(int n) {
        return (int) Math.ceil((double) n / 2.0);
    }

    public int numberOfIslands(char[][] a) {
        int m = a.length;
        int n = a[0].length;
        int count = 0;

        int[][] arr = new int[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                arr[i][j] = a[i][j] - '0';

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (arr[i][j] == 1) {
                    count++;
                    floodFillUsingDFS(arr, i, j, m, n);
                }
            }
        }
        return count;
    }

    int[][] dir = { { -1, 0 }, { 0, -1 }, { 0, 1 }, { 1, 0 } };

    public void floodFillUsingDFS(int[][] arr, int i, int j, int m, int n) {
        arr[i][j] = 2;

        for (int d = 0; d < dir.length; d++) {
            int x = dir[d][0] + i;
            int y = dir[d][1] + j;
            if (x >= 0 && y >= 0 && x < m && y < n && arr[x][y] == 1)
                floodFillUsingDFS(arr, x, y, m, n);
        }
    }

    // *** Not Efficient
    public void floodFillUsingBFS(int[][] arr, int i, int j, int m, int n) {

        LinkedList<int[]> queue = new LinkedList<>();
        queue.add(new int[] { i, j });

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] rn = queue.removeFirst();

                arr[rn[0]][rn[1]] = 2;

                for (int d = 0; d < dir.length; d++) {
                    int x = dir[d][0] + rn[0];
                    int y = dir[d][1] + rn[1];
                    if (x >= 0 && y >= 0 && x < m && y < n && arr[x][y] == 1)
                        queue.addLast(new int[] { x, y });
                }

            }
        }
    }

    // *** https://leetcode.com/problems/number-of-enclaves/submissions/
    public int numberOfEnclaves(int[][] arr) {
        int m = arr.length;
        int n = arr[0].length;
        int count = 0;

        for (int i = 0; i < m; i++) {
            if (arr[i][0] == 1)
                floodFillUsingDFS(arr, i, 0, m, n);
            if (arr[i][n - 1] == 1)
                floodFillUsingDFS(arr, i, n - 1, m, n);
        }
        for (int i = 0; i < n; i++) {
            if (arr[0][i] == 1)
                floodFillUsingDFS(arr, 0, i, m, n);
            if (arr[m - 1][i] == 1)
                floodFillUsingDFS(arr, m - 1, i, m, n);
        }

        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (arr[i][j] == 1)
                    count++;
        return count;
    }

    // ** https://leetcode.com/problems/coloring-a-border/submissions/
    public int[][] coloringTheBorder(int[][] arr, int row, int col, int color) {
        int m = arr.length;
        int n = arr[0].length;

        int[][] board = new int[m][n];

        for (int i = 0; i < m; i++)
            Arrays.fill(board[i], -1);

        coloringTheBorder(arr, row, col, m, n, color, board);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != -1 && board[i][j] != 4)
                    arr[i][j] = color;
                arr[i][j] = Math.abs(arr[i][j]);
            }
        }

        return arr;
    }

    public void coloringTheBorder(int[][] arr, int i, int j, int m, int n, int color, int[][] board) {
        arr[i][j] = -arr[i][j];
        int count = 0;

        for (int d = 0; d < dir.length; d++) {
            int x = dir[d][0] + i;
            int y = dir[d][1] + j;
            if (x >= 0 && y >= 0 && x < m && y < n) {
                if (arr[x][y] == arr[i][j] || arr[x][y] == Math.abs(arr[i][j]))
                    count++;
                if (arr[x][y] == Math.abs(arr[i][j]))
                    coloringTheBorder(arr, x, y, m, n, color, board);
            }
        }

        board[i][j] = count;
    }

    public boolean knightsTour(int si, int sj) {
        int[][] arr = new int[8][8];

        knightsTourUsingDFS(arr, si, sj);

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (arr[i][j] == 1)
                    return false;
        return true;
    }

    int[][] directions = { { -2, 1 }, { -1, 2 }, { 1, 2 }, { 2, 1 }, { 2, -1 }, { 1, -2 }, { -1, -2 }, { -2, -1 } };

    public void knightsTourUsingDFS(int[][] arr, int i, int j) {
        arr[i][j] = 1;

        for (int d = 0; d < directions.length; d++) {
            int x = directions[d][0] + i;
            int y = directions[d][1] + j;
            if (x >= 0 && y >= 0 && x < 8 && y < 8 && arr[x][y] == 0)
                knightsTourUsingDFS(arr, x, y);
        }
    }

    // *** Not Efficient
    public void knightsTourUsingBFS(int[][] arr, int i, int j) {

        LinkedList<int[]> queue = new LinkedList<>();
        queue.add(new int[] { i, j });

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] rn = queue.removeFirst();

                arr[rn[0]][rn[1]] = 1;

                for (int d = 0; d < directions.length; d++) {
                    int x = directions[d][0] + rn[0];
                    int y = directions[d][1] + rn[1];
                    if (x >= 0 && y >= 0 && x < 8 && y < 8 && arr[x][y] == 0)
                        queue.addLast(new int[] { x, y });
                }

            }
        }
    }

    public void getComponent(int u, boolean[] vis, ArrayList<Integer> list) {
        vis[u] = true;
        list.add(u);
        for (Edge edge : graph[u].neighbors)
            if (!vis[edge.v])
                getComponent(edge.v, vis, list);
    }

    public ArrayList<ArrayList<Integer>> connectedComponents() {
        int n = graph.length;

        ArrayList<ArrayList<Integer>> components = new ArrayList<>();
        boolean[] vis = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (!vis[i]) {
                ArrayList<Integer> component = new ArrayList<>();
                getComponent(i, vis, component);
                components.add(component);
            }
        }
        return components;
    }

    // **https://www.youtube.com/watch?v=_q4hA01ZcVQ&list=PL-Jc9J83PIiHfqDcLZMcO9SsUDY4S3a-v&index=9
    public long perfectFriends(int[][] arr) {
        clear();
        constructFromEdgeMatrix(arr);
        ArrayList<ArrayList<Integer>> components = connectedComponents();

        long res = 0;
        for (int i = 0; i < components.size(); i++)
            for (int j = i + 1; j < components.size(); j++)
                res += components.get(i).size() * components.get(j).size();
        return res;
    }

    // * **https://www.geeksforgeeks.org/number-groups-formed-graph-friends/
    public long friendsCircles(int[][] arr) {
        clear();
        constructFromEdgeMatrix(arr);
        ArrayList<ArrayList<Integer>> components = connectedComponents();
        long res = 0;
        for (int i = 0; i < components.size(); i++)
            res *= components.get(i).size();
        return res;
    }

    // *https://leetcode.com/problems/number-of-provinces/submissions/
    public int numberOfProvinces(int[][] arr) {

        int n = arr.length;
        ArrayList<Integer>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (i != j && arr[i][j] == 1) {
                    graph[i].add(j);
                    graph[j].add(i);
                }

        boolean[] vis = new boolean[n];
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (!vis[i]) {
                count++;
                getOneComponent(graph, i, vis);
            }
        }
        return count;
    }

    public void getOneComponent(ArrayList<Integer>[] graph, int u, boolean[] vis) {
        vis[u] = true;
        for (int edge : graph[u])
            if (!vis[edge])
                getOneComponent(graph, edge, vis);
    }

    // *https://leetcode.com/problems/number-of-operations-to-make-network-connected/
    public int numberOfOperationsToMakeNetworkConnected(int n, int[][] arr) {
        if (n > arr.length + 1)
            return -1;

        ArrayList<Integer>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            graph[arr[i][0]].add(arr[i][1]);
            graph[arr[i][1]].add(arr[i][0]);
        }

        boolean[] vis = new boolean[n];
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (!vis[i]) {
                count++;
                getOneComponent(graph, i, vis);
            }
        }

        return count - 1;
    }

    public int getComponentSize(ArrayList<Integer>[] graph, int u, boolean[] vis) {
        vis[u] = true;
        int res = 0;
        for (int edge : graph[u])
            if (!vis[edge])
                res += getComponentSize(graph, edge, vis);
        return res + 1;
    }

    public static class Node {

        int u;
        String path;
        int level;
        int color;

        public Node(int u, String path) {
            this.u = u;
            this.path = path;
            this.level = 0;
        }

        public Node(int u, String path, int level) {
            this.u = u;
            this.path = path;
            this.level = level;
        }

        public Node(int u, int color) {
            this.u = u;
            this.path = "";
            this.color = color;
        }
    }

    public int bfs(int u, int v) {

        LinkedList<Node> queue = new LinkedList<>();
        queue.addLast(new Node(u, ""));

        boolean[] vis = new boolean[graph.length];
        int noc = 0;
        int nop = 0;
        int level = 1;

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                Node rv = queue.removeFirst();

                if (vis[rv.u]) {
                    System.out.println("Cycle Exists (" + ++noc + ")-> " + rv.path);
                    continue;
                }

                if (rv.u == v)
                    System.out.println("Shortest Path (" + ++nop + ")-> " + rv.path);

                vis[rv.u] = true;
                for (Edge edge : graph[rv.u].neighbors)
                    if (!vis[edge.v])
                        queue.addLast(new Node(edge.v, rv.path + edge.v + " ", rv.level + 1));
            }
            level++;
        }
        System.out.println("No. of Levels : " + level);
        return noc;
    }

    public boolean isConnected() {
        return connectedComponents().size() == 1;
    }

    public boolean isCyclic() {
        return bfs(0, 0) > 0;
    }

    public boolean isGraphATree() {
        return isConnected() && !isCyclic();
    }

    public boolean isBipartite(Vertex[] graph, int src) {

        int n = graph.length;
        LinkedList<Node> queue = new LinkedList<>();
        queue.addLast(new Node(src, 0));

        boolean[] vis = new boolean[n];
        int[] color = new int[n];

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                Node rv = queue.removeFirst();

                if (vis[rv.u]) {
                    if (color[rv.u] != rv.color)
                        return false;
                    continue;
                }

                vis[rv.u] = true;
                for (Edge edge : graph[rv.u].neighbors)
                    if (!vis[edge.v]) {
                        queue.addLast(new Node(edge.v, (rv.color == 0) ? 1 : 0));
                        color[edge.v] = (rv.color == 0) ? 1 : 0;
                    }
            }
        }
        return true;
    }

    public Vertex[] complementGraph(int[][] arr) {
        for (int i = 0; i < arr.length; i++)
            for (int j = 0; j < arr[i].length; j++)
                arr[i][j] = 1 - arr[i][j];
        return constructFromEdgeMatrix(arr);
    }

    public boolean twoClique(int[][] arr) {
        Vertex[] complementGraph = complementGraph(arr);
        return isBipartite(complementGraph, 0);
    }

    public int rottenOranges(char[][] a) {
        int m = a.length;
        int n = a[0].length;

        int[][] arr = new int[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                arr[i][j] = a[i][j] - '0';

        int res = rottenOranges(arr, m, n);

        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (arr[i][j] == 1)
                    return -1;
        return res;
    }

    public int rottenOranges(int[][] arr, int m, int n) {
        LinkedList<int[]> queue = new LinkedList<>();

        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (arr[i][j] == 2)
                    queue.add(new int[] { i, j });

        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] rn = queue.removeFirst();

                for (int d = 0; d < dir.length; d++) {
                    int x = dir[d][0] + rn[0];
                    int y = dir[d][1] + rn[1];
                    if (x >= 0 && y >= 0 && x < m && y < n && arr[x][y] == 1) {
                        arr[rn[0]][rn[1]] = 2;
                        queue.addLast(new int[] { x, y });
                    }
                }
            }
            level++;
        }
        return level;
    }

    // * https://leetcode.com/problems/01-matrix/
    public int[][] zeroOneMatrix(int[][] arr) {
        int m = arr.length;
        int n = arr[0].length;

        LinkedList<int[]> queue = new LinkedList<>();

        int[][] board = new int[m][n];

        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (arr[i][j] == 0)
                    queue.add(new int[] { i, j });

        int level = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] rn = queue.removeFirst();

                for (int d = 0; d < dir.length; d++) {
                    int x = dir[d][0] + rn[0];
                    int y = dir[d][1] + rn[1];
                    if (x >= 0 && y >= 0 && x < m && y < n && arr[x][y] == 1) {
                        arr[x][y] = 0;
                        queue.addLast(new int[] { x, y });
                        board[x][y] = level;
                    }
                }
            }
            level++;
        }
        return board;
    }

    // *https://leetcode.com/problems/as-far-from-land-as-possible/submissions/
    public int manhattanDistance(int[][] arr) {
        int m = arr.length;
        int n = arr[0].length;

        LinkedList<int[]> queue = new LinkedList<>();

        boolean temp = false;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (arr[i][j] == 0)
                    temp = true;

        if (!temp)
            return -1;

        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (arr[i][j] == 1)
                    queue.add(new int[] { i, j });

        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] rn = queue.removeFirst();

                for (int d = 0; d < dir.length; d++) {
                    int x = dir[d][0] + rn[0];
                    int y = dir[d][1] + rn[1];
                    if (x >= 0 && y >= 0 && x < m && y < n && arr[x][y] == 0) {
                        arr[x][y] = 1;
                        queue.addLast(new int[] { x, y });
                    }
                }
            }
            level++;
        }
        return level - 1;
    }

    // *https://leetcode.com/problems/shortest-bridge/
    public int shortestBridge(int[][] arr) {
        int m = arr.length;
        int n = arr[0].length;

        LinkedList<int[]> queue = new LinkedList<>();

        boolean temp = false;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (arr[i][j] == 1) {
                    floodFillUsingDFS(arr, i, j, m, n);
                    temp = true;
                    break;
                }
            }
            if (temp)
                break;
        }

        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (arr[i][j] == 1)
                    queue.addLast(new int[] { i, j });

        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] rn = queue.removeFirst();

                for (int d = 0; d < dir.length; d++) {
                    int x = dir[d][0] + rn[0];
                    int y = dir[d][1] + rn[1];
                    if (x >= 0 && y >= 0 && x < m && y < n) {
                        if (arr[x][y] == 2)
                            return level;
                        if (arr[x][y] == 0) {
                            arr[x][y] = 1;
                            queue.addLast(new int[] { x, y });
                        }
                    }
                }
            }
            level++;
        }
        return level - 1;
    }

    // *https://leetcode.com/problems/bus-routes/submissions/
    public int busRoutes(int[][] arr, int u, int v) {

        // * map : busStationNo -> All Bus Number of Buses which can be taken from their
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                map.putIfAbsent(arr[i][j], new ArrayList<>());
                map.get(arr[i][j]).add(i);
            }
        }

        HashSet<Integer> visStation = new HashSet<>();
        HashSet<Integer> visBus = new HashSet<>();

        LinkedList<Integer> queue = new LinkedList<>();
        queue.addLast(u);
        visStation.add(u);

        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int rn = queue.removeFirst();

                if (rn == v)
                    return level;

                for (int bus : map.get(rn)) {
                    if (!visBus.contains(bus)) {
                        visBus.add(bus);
                        for (int stationNo : arr[bus]) {
                            if (!visStation.contains(stationNo)) {
                                visStation.add(stationNo);
                                queue.addLast(stationNo);
                            }
                        }
                    }
                }
            }
            level++;
        }
        return -1;
    }

    // ***** https://leetcode.com/problems/sliding-puzzle/
    public int slidingPuzzle(int[][] arr) {
        int num = 0;
        int src = -1;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                if (arr[i][j] == 0) {
                    src = i * 3 + j;
                    arr[i][j] = 7;
                }
                num *= 10;
                num += arr[i][j];
            }
        }

        int[][] swaps = { { 1, 3 }, { 0, 2, 4 }, { 1, 5 }, { 0, 4 }, { 1, 3, 5 }, { 2, 4 } };

        HashSet<Integer> vis = new HashSet<>();

        LinkedList<int[]> queue = new LinkedList<>();
        queue.addLast(new int[] { num, src });

        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] rp = queue.removeFirst();

                if (rp[0] == 123457)
                    return level;

                vis.add(rp[0]);
                for (int s : swaps[rp[1]]) {
                    int n = swap(rp[0], rp[1], s);
                    if (!vis.contains(n))
                        queue.addLast(new int[] { n, s });
                }
            }
            level++;
        }
        return -1;
    }

    public int swap(int n, int a, int b) {
        int[] arr = new int[6];
        int num = n;
        for (int i = 0; i < 6; i++) {
            arr[i] = num % 10;
            num /= 10;
        }

        a = 5 - a;
        b = 5 - b;

        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;

        int res = 0;
        for (int i = 5; i >= 0; i--) {
            res *= 10;
            res += arr[i];
        }
        return res;
    }

    // *https://leetcode.com/problems/word-ladder/
    public int wordLadder(String src, String dest, List<String> words) {

        if (!words.contains(dest))
            return 0;
        if (!words.contains(src))
            words.add(src);
        int n = words.size();

        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++)
                if (areAdjacent(words, i, j)) {
                    graph[i].add(j);
                    graph[j].add(i);
                }

        int idx = 0;
        for (int i = 0; i < n; i++)
            if (words.get(i).equals(src))
                idx = i;
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(idx);

        boolean[] vis = new boolean[graph.length];

        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int rv = queue.removeFirst();

                if (words.get(rv).equals(dest))
                    return level + 1;

                if (vis[rv])
                    continue;

                vis[rv] = true;
                for (int nbr : graph[rv])
                    if (!vis[nbr])
                        queue.addLast(nbr);
            }
            level++;
        }
        return 0;
    }

    public boolean areAdjacent(List<String> words, int a, int b) {
        int count = 0;
        for (int i = 0; i < words.get(a).length(); i++)
            if (words.get(a).charAt(i) != words.get(b).charAt(i))
                count++;
        return (count == 1);
    }

    // *https://leetcode.com/problems/snakes-and-ladders/
    public int snakesAndLadder(int[][] arr) {

        int n = arr.length;
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(1);

        boolean[][] board = new boolean[n][n];

        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int rv = queue.removeFirst();

                int[] cellNo = convert(rv, n);
                int r = cellNo[0];
                int c = cellNo[1];

                if (board[r][c])
                    continue;

                board[r][c] = true;

                if (rv == (n * n))
                    return level;

                for (int i = 1; i <= 6; i++) {
                    if (rv + i <= n * n) {

                        int num = rv + i;

                        int[] cell = convert(num, n);
                        int row = cell[0];
                        int col = cell[1];

                        if (arr[row][col] != -1) {
                            cell = convert(arr[row][col], n);
                            num = arr[row][col];
                        }

                        row = cell[0];
                        col = cell[1];
                        if (!board[row][col])
                            queue.addLast(num);

                    }
                }
            }
            level++;
        }
        return -1;
    }

    public int[] convert(int num, int n) {
        num--;
        int row = num / n;
        int col = num % n;

        if (row % 2 != 0)
            col = n - col - 1;
        row = n - row - 1;

        return new int[] { row, col };
    }

    // ? 48/50
    // *https://leetcode.com/problems/cheapest-flights-within-k-stops/
    public int cheapestFlight(int n, int[][] arr, int src, int dest, int k) {

        ArrayList<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();

        for (int i = 0; i < arr.length; i++)
            graph[arr[i][0]].add(new int[] { arr[i][1], arr[i][2] });

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.add(new int[] { src, 0, k + 1 });

        boolean[] vis = new boolean[n];

        int min = Integer.MAX_VALUE;
        while (!pq.isEmpty()) {
            int size = pq.size();
            while (size-- > 0) {
                int[] rp = pq.remove();

                if (rp[0] == dest)
                    min = Math.min(min, rp[1]);

                if (rp[2] == 0)
                    continue;

                vis[rp[0]] = true;
                for (int[] nbr : graph[rp[0]])
                    if (!vis[nbr[0]])
                        pq.add(new int[] { nbr[0], rp[1] + nbr[1], rp[2] - 1 });
            }
        }
        if (min == Integer.MAX_VALUE)
            return -1;
        return min;
    }

    class Pair {
        int[] currState;
        ArrayList<int[]> path;

        public Pair(int[] state) {
            this.currState = state;
            this.path = new ArrayList<>();
        }

        public Pair(int[] state, ArrayList<int[]> p) {
            this.currState = state;
            this.path = p;
            this.path.add(state);
        }
    }

    // ? 10 cases (Important)
    // *https://www.geeksforgeeks.org/water-jug-problem-using-bfs/
    public ArrayList<int[]> waterJugProblem(int jug1, int jug2, int target) {
        LinkedList<Pair> queue = new LinkedList<>();
        queue.add(new Pair(new int[] { 0, 0 }));

        boolean[][] vis = new boolean[jug1 + 1][jug2 + 1];

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                Pair rp = queue.removeFirst();

                int csj1 = rp.currState[0];
                int csj2 = rp.currState[1];

                if (csj1 > jug1 || csj2 > jug1 || vis[csj1][csj2])
                    continue;

                if (csj1 == target || csj2 == target)
                    return rp.path;

                vis[csj1][csj2] = true;

                queue.add(new Pair(new int[] { jug1, 0 }, rp.path));
                queue.add(new Pair(new int[] { 0, jug2 }, rp.path));

                queue.add(new Pair(new int[] { jug1, csj2 }, rp.path));
                queue.add(new Pair(new int[] { csj1, jug2 }, rp.path));

                queue.add(new Pair(new int[] { 0, csj2 }, rp.path));
                queue.add(new Pair(new int[] { csj1, 0 }, rp.path));

                // * j1 to j2
                int emptySpaceInJ2 = jug2 = csj2;
                int waterToBeTransferredFromJ1 = Math.min(csj1, emptySpaceInJ2);
                queue.add(new Pair(new int[] { csj1 - waterToBeTransferredFromJ1, csj1 + waterToBeTransferredFromJ1 },
                        rp.path));

                // * j2 to j1
                int emptySpaceInJ1 = jug1 = csj1;
                int waterToBeTransferredFromJ2 = Math.min(csj2, emptySpaceInJ1);
                queue.add(new Pair(new int[] { csj1 + waterToBeTransferredFromJ2, csj2 - waterToBeTransferredFromJ2 },
                        rp.path));
            }
        }

        return new ArrayList<>();
    }

    // ? Exponential Time Complexity i.e, O(n!) as visited array can't be used
    // *https://www.geeksforgeeks.org/find-if-there-is-a-path-of-more-than-k-length-from-a-source/
    public boolean kOrMoreLengthPath(int[][] edges, int src, int dest, int k, int n) {

        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();

        for (int i = 0; i < edges.length; i++)
            graph[edges[i][0]].add(edges[i][1]);

        LinkedList<int[]> queue = new LinkedList<>();
        queue.add(new int[] { src, 0 });

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] rp = queue.removeFirst();

                if (rp[0] == dest && rp[1] >= k)
                    return true;
                else if (rp[0] == dest)
                    continue;

                for (int nbr : graph[rp[0]])
                    queue.addLast(new int[] { nbr, rp[1] + 1 });

            }
        }
        return false;
    }

    public int graphColoringProblem(int[][] edges, int n, int src, int m) {

        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();

        for (int i = 0; i < edges.length; i++)
            graph[edges[i][0]].add(edges[i][1]);

        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(src);

        boolean[] vis = new boolean[n];
        int[] color = new int[n];

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int rp = queue.removeFirst();

                if (vis[rp])
                    continue;

                ArrayList<Integer> colorList = new ArrayList<>();
                for (int nbr : graph[rp])
                    if (color[nbr] != 0)
                        colorList.add(color[nbr]);

                int i = 1;
                while (colorList.contains(i))
                    i++;

                color[rp] = i;

                vis[rp] = true;
                for (int nbr : graph[rp])
                    if (!vis[nbr])
                        queue.addLast(nbr);
            }
        }

        int res = 1;
        for (int ele : color)
            res = Math.max(res, ele);

        return res;
    }

    // *https://practice.geeksforgeeks.org/problems/m-coloring-problem-1587115620/1
    public boolean mColoringProblem(int[][] edges, int n, int src, int m) {

        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();

        for (int i = 0; i < edges.length; i++)
            graph[edges[i][0]].add(edges[i][1]);

        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(src);

        boolean[] vis = new boolean[n];
        int[] color = new int[n];

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int rp = queue.removeFirst();

                if (vis[rp])
                    continue;

                ArrayList<Integer> colorList = new ArrayList<>();
                for (int nbr : graph[rp])
                    if (color[nbr] != 0)
                        colorList.add(color[nbr]);

                for (int i = 1; i <= m; i++)
                    if (!colorList.contains(i)) {
                        color[rp] = i;
                        break;
                    }

                if (color[rp] == 0)
                    return false;

                vis[rp] = true;
                for (int nbr : graph[rp])
                    if (!vis[nbr])
                        queue.addLast(nbr);
            }
        }
        return true;
    }

    public int swimInRisingWater(int[][] arr) {

        int n = arr.length;
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> {
            return arr[a / n][a % n] - arr[b / n][b % n];
        });
        pq.add(0);

        boolean[] vis = new boolean[n * n];

        int res = Integer.MIN_VALUE;
        while (!pq.isEmpty()) {
            int size = pq.size();
            while (size-- > 0) {
                int rn = pq.remove();

                int i = rn / n;
                int j = rn % n;

                res = Math.max(res, arr[i][j]);

                if (rn == (n * n) - 1)
                    return res;

                vis[rn] = true;
                for (int d = 0; d < dir.length; d++) {
                    int x = dir[d][0] + i;
                    int y = dir[d][1] + j;
                    if (x >= 0 && y >= 0 && x < n && y < n && !vis[x * n + y])
                        pq.add(x * n + y);
                }

            }
        }
        return res;
    }

    // *https://www.geeksforgeeks.org/number-of-triangles-in-directed-and-undirected-graphs/
    public int numberOfTriangles(int[][] matrix, boolean isDirected) {
        int count = 0;
        int n = matrix.length;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                for (int k = 0; k < n; k++)
                    if (matrix[i][j] == 1 && matrix[j][k] == 1 && matrix[k][i] == 1)
                        count++;

        return (isDirected) ? count / 3 : count / 6;
    }

}
