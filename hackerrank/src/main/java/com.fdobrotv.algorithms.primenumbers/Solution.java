package com.fdobrotv.algorithms.primenumbers;

import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import java.util.stream.Collectors;

import static java.lang.System.*;

class Prime {
    protected void checkPrime(int... numbers) {
        List<Integer> result = new ArrayList<>();

        if (numbers.length == 0) {
            throw new IllegalArgumentException("No numbers supplied.");
        }

        for (int num : numbers) {
            if (isPrime(num)) {
                result.add(num);
            }
        }

        printResults(result);
    }

    private void printResults(List<Integer> result) {
        String preparedResult = result
                                .stream()
                                .map(Object::toString)
                                .collect(Collectors.joining(" "));
        out.println(preparedResult);
    }

    private boolean isPrime(int num) {
        if (num <= 1)
            return false;
        for(int x = 2; x*x <= num; x++) {
            if (num % x == 0) {
                return false;
            }
        }
        return true;
    }
}

public class Solution {

    private static final String testValues = """
            2
            1
            3
            4
            5""";

    //JavaDoc: Closing a {@code ByteArrayInputStream} has no effect.
    private static final InputStream in = new ByteArrayInputStream(testValues.getBytes());

    public static void main(String[] args) {
        try{
            BufferedReader br=new BufferedReader(new InputStreamReader(in));
            int n1=Integer.parseInt(br.readLine());
            int n2=Integer.parseInt(br.readLine());
            int n3=Integer.parseInt(br.readLine());
            int n4=Integer.parseInt(br.readLine());
            int n5=Integer.parseInt(br.readLine());
            Prime ob=new Prime();
            ob.checkPrime(n1);
            ob.checkPrime(n1,n2);
            ob.checkPrime(n1,n2,n3);
            ob.checkPrime(n1,n2,n3,n4,n5);
            Method[] methods=Prime.class.getDeclaredMethods();
            Set<String> set=new HashSet<>();
            boolean overload=false;
            for(int i=0;i<methods.length;i++)
            {
                if(set.contains(methods[i].getName()))
                {
                    overload=true;
                    break;
                }
                set.add(methods[i].getName());

            }
            if(overload)
            {
                throw new Exception("Overloading not allowed");
            }
        }
        catch(Exception e)
        {
            out.println(e);
        }
    }

}