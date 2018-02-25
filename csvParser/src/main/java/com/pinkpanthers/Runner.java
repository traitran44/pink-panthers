package com.pinkpanthers;

import java.util.List;

public class Runner {
    public static void main(String[] args) {
        for (String file : args) {
            Parser        parser   = new Parser(file);
            List<Shelter> shelters = parser.getShelters();
            System.out.println(shelters);
        }
    }
}
