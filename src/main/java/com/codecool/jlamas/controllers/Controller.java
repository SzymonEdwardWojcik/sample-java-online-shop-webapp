package com.codecool.jlamas.controllers;

import java.util.ArrayList;
import java.util.Map;


public interface Controller<T> {

    T get(String login);

    ArrayList<T> getAll();

    void remove(String login);

    void createFromMap(Map<String, String> attrs) throws Exception;

    void editFromMap(Map<String, String> attrs, String login) throws Exception;
}
