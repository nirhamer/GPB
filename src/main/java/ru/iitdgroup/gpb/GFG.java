package ru.iitdgroup.gpb;

import java.util.HashMap;

public class GFG {

    public static void main(String[] args)
    {
        // Create an empty hash map
        HashMap<String, Integer> map = new HashMap<>();

        // Add elements to the map
        map.put("vishal", 10);
        map.put("sachin", 30);
        map.put("vaibhav", 20);

        // Print size and content
        System.out.println("Size of map is:- "
                + map.size());
        System.out.println(map);

    }
}