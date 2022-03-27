package Graph;

import java.util.*;

public class unionFind extends directedGraph {

    // ? We can't find cycle in directed graph using union find unless we are sure
    // ? that their exist a cycle

    public unionFind(int[][] arr) {
        super(arr);
    }

    public int getParent(int[] parent, int vertex) {
        if (parent[vertex] == vertex)
            return vertex;
        return parent[vertex] = getParent(parent, parent[vertex]);
    }

    public void mergeSet(int[] parent, int[] setSize, int s1, int s2) {
        if (setSize[s1] < setSize[s2]) {
            parent[s1] = s2;
            setSize[s2] += setSize[s1];
        } else {
            parent[s2] = s1;
            setSize[s1] += setSize[s2];
        }
    }

    public Vertex[] kruskalAlgorithm(int[][] arr, int n) {

        int[] parent = new int[n];
        int[] setSize = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            setSize[i] = 1;
        }

        Arrays.sort(arr, (a, b) -> a[2] - b[2]);

        Vertex[] mst = new Vertex[n];
        for (int i = 0; i < n; i++)
            mst[i] = new Vertex(i);

        for (int i = 0; i < arr.length; i++) {
            int p1 = getParent(parent, arr[i][0]);
            int p2 = getParent(parent, arr[i][1]);
            if (p1 != p2) {
                mergeSet(parent, setSize, arr[i][0], arr[i][1]);
                addEdge(mst, arr[i][0], arr[i][1], arr[i][2]);
            }
        }
        return mst;
    }

    int[][] dir = { { -1, 0 }, { 0, -1 }, { 0, 1 }, { 1, 0 } };

    // ** Dynamic Islands
    // * https://medium.com/@timothyhuang514/graph-number-of-islands-ii-21fe906e2b57
    public int[] numberOfIslands2(int[][] vertices, int m, int n) {
        int[][] arr = new int[m][n];
        int[] ans = new int[vertices.length];

        int[] parent = new int[m * n];
        int[] setSize = new int[m * n];
        for (int i = 0; i < m * n; i++) {
            parent[i] = i;
            setSize[i] = 0;
        }

        int res = 0;
        for (int i = 0; i < vertices.length; i++) {

            int r = vertices[i][0];
            int c = vertices[i][1];
            int cellNo = r * n + c;

            arr[r][c] = 1;
            res++;

            for (int d = 0; d < dir.length; d++) {
                int x = dir[d][0] + r;
                int y = dir[d][1] + r;
                if (x >= 0 && y >= 0 && x < m && y < n && arr[x][y] == 1) {
                    mergeSet(parent, setSize, cellNo, x * n + y);
                    res--;
                }
            }

            ans[i] = res;
        }

        return ans;
    }

    // *https://leetcode.com/problems/remove-max-number-of-edges-to-keep-graph-fully-traversable/
    public int aliceAndBob(int[][] edges, int n) {
        n++;
        Arrays.sort(edges, (a, b) -> b[0] - a[0]);

        int[] pa = new int[n];
        int[] pb = new int[n];
        int[] sa = new int[n];
        int[] sb = new int[n];

        for (int i = 1; i < n; i++) {
            pa[i] = pb[i] = i;
            sa[i] = sb[i] = 1;
        }

        int count = 0;
        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][1];
            int v = edges[i][2];

            int pau = getParent(pa, u);
            int pav = getParent(pa, v);
            int pbu = getParent(pb, u);
            int pbv = getParent(pb, v);

            boolean temp = false;
            if (pau != pav && (edges[i][0] == 3 || edges[i][0] == 1)) {
                mergeSet(pa, sa, pau, pav);
                temp = true;
            }
            if (pbu != pbv && (edges[i][0] == 3 || edges[i][0] == 2)) {
                mergeSet(pb, sb, pbu, pbv);
                temp = true;
            }
            if (temp)
                count++;
        }
        boolean temp = false;
        for (int i = 1; i < n; i++)
            if (sa[i] == n - 1)
                temp = true;
        if (!temp)
            return -1;
        temp = false;
        for (int i = 1; i < n; i++)
            if (sb[i] == n - 1)
                temp = true;
        if (!temp)
            return -1;
        return edges.length - count;
    }

    // *https://leetcode.com/problems/regions-cut-by-slashes/submissions/
    public int regionsBySlashes(String[] grid) {

        int n = grid.length + 1;

        int[] parent = new int[n * n];
        int[] setSize = new int[n * n];

        for (int i = 0; i < n * n; i++) {
            parent[i] = i;
            setSize[i] = 1;
        }

        int count = 1;
        for (int i = 0; i < n - 1; i++) {
            int pu = getParent(parent, i);
            int pv = getParent(parent, i + 1);
            if (pu != pv)
                mergeSet(parent, setSize, pu, pv);
            pu = getParent(parent, (n - 1) * n + i);
            pv = getParent(parent, (n - 1) * n + i + 1);
            if (pu != pv)
                mergeSet(parent, setSize, pu, pv);
        }
        for (int i = 0; i < n - 1; i++) {
            int pu = getParent(parent, i * n);
            int pv = getParent(parent, (i + 1) * n);
            if (pu != pv)
                mergeSet(parent, setSize, pu, pv);
            pu = getParent(parent, i * n + (n - 1));
            pv = getParent(parent, (i + 1) * n + (n - 1));
            if (pu != pv)
                mergeSet(parent, setSize, pu, pv);
        }

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1; j++) {
                char ch = grid[i].charAt(j);

                int u = -1;
                int v = -1;

                if (ch == (char) (47)) {
                    u = (i + 1) * n + j;
                    v = i * n + (j + 1);
                } else if (ch == (char) (92)) {
                    u = i * n + j;
                    v = (i + 1) * n + (j + 1);
                } else
                    continue;

                int pu = getParent(parent, u);
                int pv = getParent(parent, v);

                if (pu != pv)
                    mergeSet(parent, setSize, pu, pv);
                else
                    count++;
            }
        }

        return count;
    }

    public String getParent(HashMap<String, String> parent, String vertex) {
        if (parent.get(vertex).equals(vertex))
            return vertex;
        return parent.put(vertex, getParent(parent, parent.get(vertex)));
    }

    public void mergeSet(HashMap<String, String> parent, HashMap<String, Integer> setSize, String s1, String s2) {
        if (setSize.get(s1).compareTo(setSize.get(s2)) > 1) {
            parent.put(s1, s2);
            setSize.put(s2, setSize.get(s2) + setSize.get(s1));
        } else {
            parent.put(s2, s1);
            setSize.put(s1, setSize.get(s2) + setSize.get(s1));
        }
    }

    // *https://medium.com/@rebeccahezhang/leetcode-737-sentence-similarity-ii-2ca213f10115
    public boolean sentenceSimilarity2(String[] arr1, String[] arr2, String[][] arr) {

        if (arr1.length != arr2.length)
            return false;

        HashMap<String, String> parent = new HashMap<>();
        HashMap<String, Integer> setSize = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {

            parent.putIfAbsent(arr[i][0], arr[i][0]);
            parent.putIfAbsent(arr[i][1], arr[i][1]);
            setSize.putIfAbsent(arr[i][0], 1);
            setSize.putIfAbsent(arr[i][1], 1);

            String p1 = getParent(parent, arr[i][0]);
            String p2 = getParent(parent, arr[i][1]);

            if (!p1.equals(p2))
                mergeSet(parent, setSize, p1, p2);
        }

        for (int i = 0; i < arr1.length; i++) {
            String p1 = getParent(parent, arr1[i]);
            String p2 = getParent(parent, arr2[i]);

            if (p1 != p2)
                return false;
        }
        return true;
    }

    // *https://leetcode.com/problems/satisfiability-of-equality-equations/submissions/
    public boolean equalityEquations(String[] arr) {
        int[] parent = new int[26];
        int[] setSize = new int[26];

        for (int i = 0; i < 26; i++) {
            parent[i] = i;
            setSize[i] = 1;
        }

        for (String str : arr) {
            if (!str.contains("!")) {
                int p1 = getParent(parent, str.charAt(0) - 'a');
                int p2 = getParent(parent, str.charAt(3) - 'a');

                if (p1 != p2)
                    mergeSet(parent, setSize, p1, p2);
            }
        }
        for (String str : arr) {
            if (str.contains("!")) {
                int p1 = getParent(parent, str.charAt(0) - 'a');
                int p2 = getParent(parent, str.charAt(3) - 'a');

                if (p1 == p2)
                    return false;
            }
        }
        return true;
    }

    // ** Undirected Graph
    // *https://leetcode.com/problems/redundant-connection/submissions/
    public int[] redundantConnections(int[][] edges) {

        int n = edges.length + 1;

        int[] parent = new int[n];
        int[] setSize = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            setSize[i] = 1;
        }

        int[] res = new int[] { -1, -1 };
        for (int i = 0; i < edges.length; i++) {

            int p1 = getParent(parent, edges[i][0]);
            int p2 = getParent(parent, edges[i][1]);

            if (p1 != p2)
                mergeSet(parent, setSize, p1, p2);
            else
                res = edges[i];
        }
        return res;
    }

    // ** Directed Graph
    // * https://leetcode.com/problems/redundant-connection-ii/
    public int[] redundantConnections2(int[][] edges) {

        int n = edges.length + 1;

        int[] inDegree = new int[n];
        Arrays.fill(inDegree, -1);

        int ans1 = -1;
        int ans2 = -1;
        for (int i = 0; i < edges.length; i++) {
            if (inDegree[edges[i][1]] != -1) {
                ans1 = inDegree[edges[i][1]];
                ans2 = i;
            } else
                inDegree[edges[i][1]] = i;
        }

        int[] parent = new int[n];
        int[] setSize = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            setSize[i] = 1;
        }

        if (ans1 == -1 && ans2 == -1) { // * Only Cycle Exist i.e, no node have two children
            int[] res = new int[] { -1, -1 };
            for (int i = 0; i < edges.length; i++) {
                if (ans1 == i)
                    continue;
                int p1 = getParent(parent, edges[i][0]);
                int p2 = getParent(parent, edges[i][1]);

                if (p1 != p2)
                    mergeSet(parent, setSize, p1, p2);
                else
                    res = edges[i];
            }
            return res;
        }

        boolean temp1 = false;
        boolean temp2 = false;
        for (int i = 0; i < edges.length; i++) {
            if (ans1 == i)
                continue;
            int p1 = getParent(parent, edges[i][0]);
            int p2 = getParent(parent, edges[i][1]);

            if (p1 != p2)
                mergeSet(parent, setSize, p1, p2);
            else
                temp1 = true;
        }

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            setSize[i] = 1;
        }

        for (int i = 0; i < edges.length; i++) {
            if (ans2 == i)
                continue;
            int p1 = getParent(parent, edges[i][0]);
            int p2 = getParent(parent, edges[i][1]);

            if (p1 != p2)
                mergeSet(parent, setSize, p1, p2);
            else
                temp2 = true;
        }

        if (!temp1 && !temp2) // ** No Cycle exists
            return edges[ans2];
        return (!temp1) ? edges[ans1] : edges[ans2];
    }

    // *https://leetcode.com/problems/minimize-malware-spread/
    public int minimizeMalware(int[][] arr, int[] initial) {

        int n = arr.length;
        int[] parent = new int[n];
        int[] setSize = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            setSize[i] = 1;
        }

        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++)
                if (arr[i][j] == 1) {
                    int pi = getParent(parent, i);
                    int pj = getParent(parent, j);
                    if (pi != pj)
                        mergeSet(parent, setSize, pi, pj);
                }

        int[] infected = new int[n];
        for (int i = 0; i < initial.length; i++) {
            int p = getParent(parent, initial[i]);
            infected[p]++;
        }

        int max = 0;
        for (int i = 0; i < initial.length; i++) {
            int p = getParent(parent, initial[i]);
            if (infected[p] != 1)
                continue;
            max = Math.max(max, setSize[p]);
        }

        if (max == 0) { // *** All Components have more than 1 infected initials
            int min = Integer.MAX_VALUE;
            for (int ele : initial)
                min = Math.min(min, ele);
            return min;
        }

        int res = Integer.MAX_VALUE;
        for (int i = 0; i < initial.length; i++) {
            int p = getParent(parent, initial[i]);
            if (infected[p] != 1)
                continue;
            if (max == setSize[p])
                res = Math.min(res, initial[i]);
        }
        return res;
    }

    // *https://www.hackerrank.com/challenges/journey-to-the-moon/problem
    public int journeyToTheMoon(int n, int[][] arr) {

        int[] parent = new int[n];
        int[] setSize = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            setSize[i] = 1;
        }

        for (int i = 0; i < arr.length; i++) {
            int p1 = getParent(parent, arr[i][0]);
            int p2 = getParent(parent, arr[i][1]);

            if (p1 != p2)
                mergeSet(parent, setSize, p1, p2);
        }

        int res = n * (n - 1) / 2;

        for (int i = 0; i < n; i++) {
            if (getParent(parent, i) == i) {
                int size = setSize[i];
                res -= (size) * (size - 1) / 2;
            }
        }
        return res;
    }

    public class Node {
        int src;
        int parent;
        int w;
        int wsf;

        public Node(int src, int p, int w, int wsf) {
            this.src = src;
            this.parent = p;
            this.w = w;
            this.wsf = wsf;
        }

    }

    public Vertex[] primsAlgorithm(Vertex[] graph, int src) {
        int n = graph.length;
        Vertex[] mst = new Vertex[n];
        for (int i = 0; i < n; i++)
            mst[i] = new Vertex(i);

        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.w - b.w);
        pq.add(new Node(src, -1, 0, 0));

        boolean[] vis = new boolean[n];

        while (!pq.isEmpty()) {
            int setSize = pq.size();
            while (setSize-- > 0) {
                Node rn = pq.remove();

                if (vis[rn.src])
                    continue;

                if (rn.parent != -1)
                    addEdge(mst, rn.src, rn.parent, rn.w);

                vis[rn.src] = true;
                for (Edge e : graph[rn.src].neighbors)
                    if (!vis[e.v])
                        pq.add(new Node(e.v, rn.src, e.w, 0));
            }
        }

        return mst;
    }

    public int mstCost(Vertex[] graph, int src, boolean[] vis) {
        int res = 0;

        vis[src] = true;
        for (Edge e : graph[src].neighbors)
            if (!vis[e.v])
                res += mstCost(graph, e.v, vis) + e.w;
        return res;
    }

    // *https://www.youtube.com/watch?v=gc6ShDTldb4&list=PL-Jc9J83PIiEuHrjpZ9m94Nag4fwAvtPQ&index=16
    public int optimizeWaterDistribution(int[] wells, int[][] pipes) {
        int n = wells.length + 1;
        Vertex[] graph = new Vertex[n];
        for (int i = 0; i < n; i++)
            graph[i] = new Vertex(i);

        for (int i = 0; i < pipes.length; i++)
            addEdge(graph, pipes[i][0], pipes[i][1], pipes[i][2]);

        // *** addition of one more vertex add connect it with all other vertices with
        // *** cost of wells
        for (int i = 0; i < n - 1; i++)
            graph[n - 1].neighbors.add(new Edge(i, wells[i]));

        Vertex[] mst = primsAlgorithm(graph, 0);
        return mstCost(mst, 0, new boolean[n]);
    }

    public Vertex[] dijkstraAlgorithm(Vertex[] graph, int src) {
        int n = this.graph.length;
        Vertex[] mwt = new Vertex[n];
        for (int i = 0; i < n; i++)
            mwt[i] = new Vertex(i);

        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.wsf - b.wsf);
        pq.add(new Node(src, -1, 0, 0));

        boolean[] vis = new boolean[n];

        while (!pq.isEmpty()) {
            int setSize = pq.size();
            while (setSize-- > 0) {
                Node rn = pq.remove();

                if (vis[rn.src])
                    continue;

                if (rn.parent != -1)
                    addEdge(mwt, rn.src, rn.parent, rn.w);

                vis[rn.src] = true;
                for (Edge e : graph[rn.src].neighbors)
                    if (!vis[e.v])
                        pq.add(new Node(e.v, rn.src, 0, rn.wsf + e.w));
            }
        }

        return mwt;
    }

    public int cost;

    public void distanceBtwNodesInTree(Vertex[] graph, int src, int tar, int c, boolean[] vis) {
        if (src == tar) {
            cost = c;
            return;
        }

        vis[src] = true;
        for (Edge e : graph[src].neighbors)
            if (!vis[e.v])
                distanceBtwNodesInTree(graph, e.v, tar, c + e.w, vis);
    }

    // *https://www.geeksforgeeks.org/0-1-bfs-shortest-path-binary-graph/
    public int _0_1_bfs(Vertex[] graph, int src, int tar) {
        int n = graph.length;

        // *** reverse given graph with all edge weights 1
        Vertex[] reversedGraph = reverseGraph(graph);
        Vertex[] g = mergeTwoGraphs(graph, reversedGraph, n);
        Vertex[] mdt = dijkstraAlgorithm(g, src);

        cost = -1;
        distanceBtwNodesInTree(mdt, src, tar, 0, new boolean[n]);
        return cost;
    }

    public boolean bellmanFord(int[][] arr, int src, int n) {

        int[][] dp = new int[n][n + 1];
        for (int[] d : dp)
            Arrays.fill(d, Integer.MAX_VALUE);
        dp[src][0] = 0;
        boolean isNegativeCycle = false;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < n; j++)
                dp[j][i] = dp[j][i - 1];
            for (int[] edge : arr) {
                if (dp[edge[0]][i - 1] == Integer.MAX_VALUE)
                    continue;
                int temp = dp[edge[1]][i];
                dp[edge[1]][i] = Math.min(dp[edge[1]][i], dp[edge[1]][i - 1] + edge[2]);
                if (i == n && temp != dp[edge[1]][i])
                    isNegativeCycle = true;
            }
        }
        return isNegativeCycle;
    }

    public static int[][] floydWarshall(int[][] matrix) {
        int n = matrix.length;

        int[][] tempMatrix = new int[n][n];
        for (int[] arr : tempMatrix)
            Arrays.fill(arr, Integer.MAX_VALUE);

        // ***** k is the vertex number in whose respect we are relaxing all other edges
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (k % 2 == 0)
                        matrix[i][j] = Math.min(matrix[i][j], tempMatrix[i][k] + tempMatrix[k][j]);
                    else
                        tempMatrix[i][j] = Math.min(tempMatrix[i][j], matrix[i][k] + matrix[k][j]);
                }
            }
        }

        return (n % 2 == 1) ? matrix : tempMatrix;
    }

    public int multiStageGraph(int[][] matrix) {
        int n = this.graph.length;
        int[] dp = new int[n];

        for (int i = n - 2; i >= 0; i--) {
            int min = Integer.MAX_VALUE;
            for (int j = i + 1; j < n; j++)
                if (containsEdge(i, j))
                    min = Math.min(min, dp[j] + matrix[i][j]);
            dp[i] = min;
        }

        return dp[0];
    }

}
