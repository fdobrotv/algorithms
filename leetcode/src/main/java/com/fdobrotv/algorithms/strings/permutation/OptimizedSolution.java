package com.fdobrotv.algorithms.strings.permutation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import static java.lang.System.in;
import static java.lang.System.out;

//Look at https://en.wikipedia.org/wiki/Factorial_number_system
class OptimizedImplementation {
    boolean contains = false;

    public class Pair<F, S> {
        Pair(F first, S second) {
            this.first = first;
            this.second = second;
        }

        public F first;
        public S second;
    }

    protected void permutation(String str, String prefix, String search) {
        if (str.length() == 0 && search.contains(prefix)) {
            contains = true;
        } else {
            for (int i = 0; i < str.length(); i++) {
                String rem = str.substring(0, i) + str.substring(i + 1);
                permutation(rem, prefix + str.charAt(i), search);
            }
        }
    }

    //TODO: Make recursion call
    protected Pair<String, String> shrinkLog(String s1, String s2) {
        var s1Length = s1.length();
        var s1FirstHalf = s1.substring(0, s1Length/2);
        var s1SecondHalf = s1.substring(s1Length/2, s1Length);

        var s2Length = s2.length();
        var s2FirstHalf = s2.substring(0, s2Length/2);
        var s2SecondHalf = s2.substring(s2Length/2, s2Length);

        var firstHalfEquals = s1FirstHalf.equals(s2FirstHalf);
        var secondHalfEquals = s1SecondHalf.equals(s2SecondHalf);

        if (firstHalfEquals)
            return new Pair<>(s1SecondHalf, s2SecondHalf);
        if (secondHalfEquals)
            return new Pair<>(s1FirstHalf, s2FirstHalf);
        return new Pair<>(s1, s2);
    }

    private Pair<String, String> shrinkN(String s1, String s2) {
        var length = Math.min(s1.length(), s2.length());

        var leftShrinkedS1 = s1;
        var leftShrinkedS2 = s2;
        var s1Inverted = new StringBuilder(s1).reverse().toString();
        var s2Inverted = new StringBuilder(s2).reverse().toString();
        for (int i = 0; i < length; i++)
        {
            if (s1Inverted.charAt(i) != s2Inverted.charAt(i))
                break;
            else {
                leftShrinkedS1 = s1Inverted.substring(i + 1, s1Inverted.length());
                leftShrinkedS2 = s2Inverted.substring(i + 1, s2Inverted.length());
            }

        }
        var s1Result = new StringBuilder(leftShrinkedS1).reverse().toString();
        var s2Result = new StringBuilder(leftShrinkedS2).reverse().toString();
        return new Pair<>(s1Result, s2Result);
    }

    public boolean checkInclusion(String s1, String s2) {
        var shrinked = shrinkLog(s1, s2);
        shrinked = shrinkN(shrinked.first, shrinked.second);
        permutation(shrinked.first, "", shrinked.second);
        return contains;
    }
}

public class OptimizedSolution {

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            var n1 = br.readLine();
            var n2 = br.readLine();
            OptimizedImplementation oi = new OptimizedImplementation();
            out.println(oi.checkInclusion(n1, n2));
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