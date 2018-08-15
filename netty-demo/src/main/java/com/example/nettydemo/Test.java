package com.example.nettydemo;

public class Test {

    private int a = 100;

    public static void main(String[] args) {
        byte[] b = "query time order".getBytes();
        for (int i = 0; i < b.length; i++) {
            System.out.print(b[i] + " ");
        }
        System.out.println(System.getProperty("line.separator") + "...");

        System.out.println(new Test().a);
        Object obj = new Test();
        Test test_b = (Test)obj;
        System.out.println(test_b.a);
    }
}
