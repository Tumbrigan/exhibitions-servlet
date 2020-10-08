package com.kucher.util;

import java.util.ArrayList;
import java.util.List;

public class TestList {
    public static void main(String[] args) {
        List<Integer> integers = new ArrayList<>();
        integers.add(0);
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);
        integers.add(5);

        List<Integer> integers2 = integers.subList(0, 3);

        integers2.add(6);
        integers.add(6);

        System.out.println(integers2);
    }
}
