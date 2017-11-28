package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.ArtifactDAO;
import com.codecool.jlamas.database.DoneQuestDAO;
import com.codecool.jlamas.database.OwnedArtifactDAO;
import com.codecool.jlamas.database.StudentDAO;
import com.codecool.jlamas.models.account.Student;
import com.codecool.jlamas.models.artifact.Artifact;
import com.codecool.jlamas.models.quest.Quest;

public class WalletController {

    Student student;
    private StudentDAO studentDAO = new StudentDAO();
    private ArtifactDAO artifactDAO = new ArtifactDAO();
    private DoneQuestDAO doneQuestsDAO = new DoneQuestDAO();
    private OwnedArtifactDAO ownedArtifactDAO = new OwnedArtifactDAO();
    private ArtifactController artifactController = new ArtifactController();

    public WalletController(Student student) {
        this.student = student;
    }

    public void addDoneQuest(Quest quest) {
        this.student.getWallet().getDoneQuests().add(quest);
        doneQuestsDAO.insert(this.student, quest);
    }

    public boolean buyArtifact(Artifact artifact) {
        try {
            if (student.getWallet().take(artifact.getPrice())) {
                studentDAO.update(student);
                return addOwnedArtifact(artifact);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean addOwnedArtifact(Artifact artifact) {
        this.student.getWallet().getOwnedArtifacts().add(artifact);
        return ownedArtifactDAO.insert(this.student, artifact);
    }

}
