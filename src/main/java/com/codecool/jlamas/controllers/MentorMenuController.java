package com.codecool.jlamas.controllers;

import com.codecool.jlamas.models.account.Mentor;

import com.codecool.jlamas.views.MentorView;
import com.codecool.jlamas.controllers.QuestController;


public class MentorMenuController {

    public static final String[] MENU = {"Print class",
                                         "Print team",
                                         "Create team",
                                         "Add new Student",
                                         "Add new Quest",
                                         "Add new Artifat",
                                         "Show Quests",
                                         "Edit existing Artifact",
                                         "Mark Quest as done",
                                         "Mark Artifact as done"};

    private static final int PRINT_CLASS = 1;
    private static final int PRINT_TEAM = 2;
    private static final int CREATE_TEAM = 3;
    private static final int ADD_STUDENT = 4;
    private static final int ADD_QUEST = 5;
    private static final int ADD_ARTIFACT = 6;
    private static final int SHOW_QUEST = 7;
    private static final int EDIT_ARTIFACT = 8;
    private static final int MARK_QUEST = 9;
    private static final int MARK_ARTIFACT = 10;
    private static final int EXIT = 11;

    private Mentor user;
    private MentorView view;

    public MentorMenuController(Mentor user) {
        this.user = user;
        this.view =  new MentorView();
    }

    public void start() {
        Integer option;

        option = 0;
        while (!option.equals(EXIT)) {
            view.printMenu(MENU);
            option = view.getMenuOption();
            this.resolveOption(option);
        }
    }

    private void resolveOption(Integer option) {
    // adding new option should add also to view MENU
        switch (option) {
            case PRINT_CLASS :
                printClass();
                break;
            case PRINT_TEAM :
                printTeam();
            case CREATE_TEAM :
                createTeam();
                break;
            case ADD_STUDENT :
                addStudent();
                break;
            case ADD_QUEST :
                QuestController questControll = new QuestController();
                questControll.createQuest();
                break;
            case ADD_ARTIFACT :
                addArtifact();
                break;
            case SHOW_QUEST :
                QuestController questControll = new QuestController();
                questControll.showAllQuests();
                break;
            case EDIT_ARTIFACT :
                editArtifact();
                break;
            case MARK_QUEST :
                markQuest();
                break;
            case MARK_ARTIFACT :
                markArtifact();
                break;
        }

    }
    public void printClass() {

    }

    public void printTeam() {

    }

    public void createTeam() {

    }

    public void addStudent() {

    }

    public void addQuest() {

    }

    public void addArtifact() {

    }

    public void editQuest() {

    }

    public void editArtifact() {

    }

    public void markQuest() {

    }

    public void markArtifact() {

    }


}
