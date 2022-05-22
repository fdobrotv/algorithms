package com.fdobrotv.algorithms.roads_and_libraries;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'roadsAndLibraries' function below.
     *
     * The function is expected to return a LONG_INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER c_lib
     *  3. INTEGER c_road
     *  4. 2D_INTEGER_ARRAY cities
     */

    public static long roadsAndLibraries(int n, int c_lib, int c_road, List<List<Integer>> cities) {
        boolean isRoadsCheaper = c_road < c_lib;

        if (!isRoadsCheaper || cities.isEmpty()) {
            return (long) n * c_lib;
        }

        return getCostByDFS(n, c_lib, c_road, cities);
    }

    private static long getCostByDFS(int n, int c_lib, int c_road, List<List<Integer>> cities) {
        Set<Integer> uniqueRoadConnectableCities = getJoinedCities(cities);

        boolean[] visited = new boolean[n + 1];
        long cost = 0;

        for (Integer cityId : uniqueRoadConnectableCities) {
            if (!visited[cityId]) {
                int sizeOfTheGraph = dfs(cities, visited, cityId);
                cost += (long) (sizeOfTheGraph - 1) * c_road;
                cost += c_lib;
            }
        }

        if (n > uniqueRoadConnectableCities.size())
            cost += (long) (n - uniqueRoadConnectableCities.size()) * c_lib;
        return cost;
    }

    private static long getCostByPathCompressedUnionFind(int n, int libraryCost, int roadCost, List<List<Integer>> cities) {
        Set<Integer> uniqueRoadConnectableCities = getJoinedCities(cities);
        UnionFind unionFind = new UnionFind(uniqueRoadConnectableCities);

        for (List<Integer> join : cities) {
            unionFind.unify(join.get(0), join.get(1));
        }

        long cost = 0;

        for (Integer sizeOfUnion : unionFind.getSizeOfUnion().values()) {
            cost += (long) (sizeOfUnion - 1) * roadCost;
            cost += libraryCost;
        }

        if (n > uniqueRoadConnectableCities.size())
            cost += (long) (n - uniqueRoadConnectableCities.size()) * libraryCost;
        return cost;
    }

    private static Set<Integer> getJoinedCities(List<List<Integer>> cities) {
        Set<Integer> uniqueRoadConnectableCities = new HashSet<>();
        for (List<Integer> road : cities) {
            uniqueRoadConnectableCities.addAll(road);
        }
        return uniqueRoadConnectableCities;
    }

    public static int dfs(List<List<Integer>> cities, boolean[] visited, int cityId) {
        int count = 1;
        visited[cityId] = true;
        for (List<Integer> join : cities) {
            if (join.get(0) != cityId && join.get(1) != cityId)
                continue;
            int neighborCityId;
            if (join.get(0) == cityId)
                neighborCityId = join.get(1);
            else
                neighborCityId = join.get(0);

            if (!visited[neighborCityId])
                count += dfs(cities, visited, neighborCityId);
        }

        return count;
    }
}

class UnionFind {
    private final HashMap<Integer, Integer> sizeOfUnion;
    private final HashMap<Integer, Integer> unionPointers;

    public UnionFind(Set<Integer> uniqueValues) {
        int size = uniqueValues.size();
        this.sizeOfUnion = new HashMap<>(size);
        this.unionPointers = new HashMap<>(size);

        for (Integer value : uniqueValues) {
            this.unionPointers.put(value, value);
            this.sizeOfUnion.put(value, 1);
        }
    }

    public HashMap<Integer, Integer> getSizeOfUnion() {
        return sizeOfUnion;
    }

    public int find(int value) {
        int root = value;
        while (root != unionPointers.get(root))
            root = unionPointers.get(root);

        compressPath(value, root);

        return root;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public void unify(int left, int right) {
        if (connected(left, right)) return;

        int leftRoot = find(left);
        int rightRoot = find(right);

        if (sizeOfUnion.get(leftRoot) < sizeOfUnion.get(rightRoot)) {
            sizeOfUnion.put(rightRoot, sizeOfUnion.get(leftRoot) + sizeOfUnion.get(rightRoot));
            unionPointers.put(leftRoot, rightRoot);
            sizeOfUnion.remove(leftRoot);
        } else {
            sizeOfUnion.put(leftRoot, sizeOfUnion.get(leftRoot) + sizeOfUnion.get(rightRoot));
            unionPointers.put(rightRoot, leftRoot);
            sizeOfUnion.remove(rightRoot);
        }
    }

    private void compressPath(int p, int root) {
        while (p != root) {
            int next = unionPointers.get(p);
            unionPointers.put(p, root);
            p = next;
        }
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String outputPath = System.getenv("OUTPUT_PATH");
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputPath));

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, q).forEach(qItr -> {
            try {
                String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int n = Integer.parseInt(firstMultipleInput[0]);

                int m = Integer.parseInt(firstMultipleInput[1]);

                int c_lib = Integer.parseInt(firstMultipleInput[2]);

                int c_road = Integer.parseInt(firstMultipleInput[3]);

                List<List<Integer>> cities = new ArrayList<>();

                IntStream.range(0, m).forEach(i -> {
                    try {
                        cities.add(
                                Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                        .map(Integer::parseInt)
                                        .collect(toList())
                        );
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                long result = Result.roadsAndLibraries(n, c_lib, c_road, cities);

                bufferedWriter.write(String.valueOf(result));
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
