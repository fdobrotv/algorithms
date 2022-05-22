package com.fdobrotv.algorithms.strings.permutation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.System.in;
import static java.lang.System.out;

class Implementation {
    protected List<String> permute(String str) {
        final List<String> permutations = new ArrayList<>();
        permutation(str, "", permutations);
        return permutations;
    }

    protected void permutation(String str, String prefix, List<String> permutations) {
        if (str.length() == 0) {
            permutations.add(prefix);
        } else {
            for (int i = 0; i < str.length(); i++) {
                String rem = str.substring(0, i) + str.substring(i + 1);
                permutation(rem, prefix + str.charAt(i), permutations);
            }
        }
    }

    protected boolean isContains(String input, List<String> permutations) {
        for (String permutation : permutations) {
            if (input.contains(permutation)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkInclusion(String s1, String s2) {
        var s1Permutations = permute(s1);
        return isContains(s2, s1Permutations);
    }
}

public class Solution {

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            var n1 = br.readLine();
            var n2 = br.readLine();
            Implementation impl = new Implementation();
            out.println(impl.checkInclusion(n1, n2));
            Method[] methods = Implementation.class.getDeclaredMethods();
            Set<String> set = new HashSet<>();
            boolean overload = false;
            for (int i = 0; i < methods.length; i++) {
                if (set.contains(methods[i].getName())) {
                    overload = true;
                    break;
                }
                set.add(methods[i].getName());

            }
            if (overload) {
                throw new Exception("Overloading not allowed");
            }
        } catch (Exception e) {
            out.println(e);
        }
    }

}