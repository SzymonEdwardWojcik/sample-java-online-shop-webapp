package com.codecool.jlamas.models.accountdata;


import com.codecool.jlamas.models.artifact.Artifact;
import com.codecool.jlamas.models.artifact.TeamPurchase;
import com.codecool.jlamas.models.quest.Quest;

import java.util.ArrayList;

public class Wallet {

    private Integer balance;
    private ArrayList<Quest> doneQuests;
    private ArrayList<Artifact> ownedArtifacts;
    private ArrayList<TeamPurchase> pendingPurchases;

    public Wallet() {
        this.balance = 0;
    }

    public Wallet(Integer balance) {
        this.balance = balance;
    }

    public Integer getBalance() {
        return this.balance;
    }

    public void setBalance(Integer balance) { this.balance = balance; }

    public void put(Integer amount) {
        this.balance += amount;
    }

    public boolean has(Integer amount) {
        return this.balance >= amount;
    }

    public boolean take(Integer amount) {
        if (this.has(amount)) {
            this.balance -= amount;
            // if transaction was done return true
            return true;
        }
        // if transaction failed return false
        return false;
    }

    public ArrayList<Quest> getDoneQuests() {
        return this.doneQuests;
    }

    public ArrayList<Artifact> getOwnedArtifacts() {
        return this.ownedArtifacts;
    }

    public void setDoneQuests(ArrayList<Quest> doneQuests) {
        this.doneQuests = doneQuests;
    }

    public void setOwnedArtifacts(ArrayList<Artifact> ownedArtifacts) {
        this.ownedArtifacts = ownedArtifacts;
    }

    public ArrayList<TeamPurchase> getPendingPurchases() {
        return this.pendingPurchases;
    }

    public void setPendingPurchases(ArrayList<TeamPurchase> pendingPurchases) {
        this.pendingPurchases = pendingPurchases;
    }

}
