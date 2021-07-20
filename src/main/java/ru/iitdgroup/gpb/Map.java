package ru.iitdgroup.gpb;

import java.util.HashMap;

public class Map {

    public static void main(String[] args)
    {
        // Create an empty hash map
        HashMap<String, Integer> map = new HashMap<>();

        // Add elements to the map
        map.put("nero", 10);


        // Print size and content
        System.out.println("Size of map is:- "
                + map.size());
        System.out.println(map);

    }
}