package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.ArtifactDAO;
import com.codecool.jlamas.database.OwnedArtifactDAO;
import com.codecool.jlamas.database.StudentDAO;
import com.codecool.jlamas.models.artifact.Artifact;
import com.codecool.jlamas.views.ArtifactView;

import java.util.ArrayList;

public class ArtifactController {

    private static final String EDIT_NAME = "1";
    private static final String EDIT_PRICE = "2";
    private static final String EDIT_DESCRIPTION = "3";

    private ArtifactDAO artifactDao = new ArtifactDAO();
    private ArtifactView artifactView = new ArtifactView();
    private OwnedArtifactDAO ownedArtifactDAO = new OwnedArtifactDAO();
    private StudentDAO studentDAO = new StudentDAO();

    public ArtifactController() {
    }

    public ArrayList<Artifact> displayArtifacts() {
        ArrayList<Artifact> artifacts = new ArrayList<>();
        artifacts = artifactDao.requestAll();

        return artifacts;
    }

    public void createArtifact(String name, String description, Integer price) {
        Artifact artifact = new Artifact(name, price, description);
        this.artifactDao.insert(artifact);
    }

    public void editArtifact() {
        try {
            Artifact artifact = chooseArtifact(artifactDao.requestAll());
            String oldName = artifact.getName();
            artifactView.displayAttribute();
            String option = artifactView.getString("Your choice: ");

            switch (option) {
                case EDIT_NAME:
                    String name = artifactView.getString("New name: ");
                    artifact.setName(name);
                    break;
                case EDIT_PRICE:
                    Integer price = artifactView.getInt("New price: ");
                    artifact.setPrice(price);
                    break;
                case EDIT_DESCRIPTION:
                    String description = artifactView.getString("New description: ");
                    artifact.setDescription(description);
                    break;
                default:
                    artifactView.printErrorMessage();
                    break;
            }
            artifactDao.update(artifact, oldName);
        } catch (IndexOutOfBoundsException e) {
            e.getMessage();
        }
    }

    public Artifact chooseArtifact(ArrayList<Artifact> artifacts) throws IndexOutOfBoundsException {
        artifactView.printArtifacts(artifacts);
        Integer record = artifactView.getMenuOption();
        Integer index = record - 1;
        if (index >= artifacts.size()) {
            throw new IndexOutOfBoundsException();
        }
        return artifacts.get(index);
    }

//    public boolean useArtifact() throws IndexOutOfBoundsException {
//        StudentController students = new StudentController();
//        try {
//            Student student = students.chooseStudent();
//            Artifact artifact = chooseArtifact();
//            ownedArtifactDAO.delete(artifact, student);
//        } catch (IndexOutOfBoundsException e) {
//            artifactView.printErrorMessage();
//            return false;
//        }
//        return true;
//    }

}

