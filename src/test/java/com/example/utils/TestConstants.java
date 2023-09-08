package com.example.utils;



import com.example.jfx_project.Person;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TestConstants {

    public static final List<Person> TEST_DATA_LIST = loadDataTest();
    public static final Person NEW_DATA = new Person(-1, "32-301", "Katowice_addTest", "+48 111 111 112", "Kowalski_addTest", Date.valueOf("2023-07-26"), Date.valueOf("2023-07-28"), "Ges Szara", 10, 10, 2, "n/a");

    private static List<Person> loadDataTest() {
        List<Person> list = new ArrayList<>();
        list.add(new Person(94, "11-111", "Krakow", "000 000 000", "kowalski", Date.valueOf("2023-07-25"), Date.valueOf("2023-07-30"), "Ges Biala", 10, 1, 1, "brak"));
        list.add(new Person(95,	"32-300",	"Katowice",	"+48 112 223 334",	"Siwitza",	Date.valueOf("2023-08-29"),	Date.valueOf("2023-09-26"),	"Ges Szara", 20,	10, 1,"brak"));
        list.add(new Person(96, "32-300", "Katowice", "+48 123 123 123", "Światłoń", Date.valueOf("2023-08-29"), Date.valueOf("2023-09-30"), "Pekin", 25, 10, 1, "brak"));
        list.add(new Person(97, "32-300", "Katowice", "+48 456 567 678", "Kowalska", Date.valueOf("2023-08-29"), Date.valueOf("2023-09-11"), "Barbarie", 16, 10, 1, "brak"));
        list.add(new Person(98, "32-300", "Katowice", "+48 111 111 111", "Czesław", Date.valueOf("2023-08-29"), Date.valueOf("2023-09-19"), "Ges Biala", 20, 10, 1, "brak"));
        list.add(new Person(99, "32-301", "Kraków", "+48 500 600 700", "Barczyk", Date.valueOf("2023-08-29"), Date.valueOf("2023-08-31"), "Ges Biala", 11, 50, 1, "n/a"));
        list.add(new Person(100, "00-001", "Warszawa", "+48 500 500 500", "Budzyń", Date.valueOf("2023-08-29"), Date.valueOf("2023-08-31"), "Barbarie", 16, 15, 1, "Prośba o wcześniejszy kontakt"));
        list.add(new Person(101, "32-301", "Katowice", "+48 111 111 112", "Kowalski", Date.valueOf("2023-07-26"), Date.valueOf("2023-07-28"), "Ges Szara", 11, 10, 2, "n/a"));
        return list;
    }



}