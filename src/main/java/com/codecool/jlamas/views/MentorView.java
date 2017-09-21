package com.codecool.jlamas.views;

import java.util.Scanner;
import java.util.ArrayList;


public class MentorView extends CodecoolerView {

    public static final ArrayList<String> MENU = {"Print class",
                                                  "Print team",
                                                  "Create team",
                                                  "Add new Student",
                                                  "Add new Quest",
                                                  "Add new Artifat",
                                                  "Edit existing Quest",
                                                  "Edit existing Artifact",
                                                  "Mark Quest as done",
                                                  "Mark Artifact as done"};

    private Scanner input;

    public MentorView() {
        this.input = new Scanner(System.in);
    }

    private void printMenu() {
        String output;

        output = "\nMENU";
        for (int i = 0; i < menu.length; i++) {
            output += String.format("  %d) %s.\n", i+1, menu[i]);
        }
        output += "  0) Exit.\n";

        System.out.println(output);
    }

    public String getMenuOption() {
        String option;

        this.printMenu();
        System.out.print("Choose option: ");

        option = "";
        try {
            option = Integer.parseInt(this.input.nextLine());
            option = MENU[option-1];
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {

        }

        return option;
    }


}