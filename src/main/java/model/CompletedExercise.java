package model;

import java.math.BigDecimal;

/**
 * Created by Mephalay on 11/15/2016.
 */
public class CompletedExercise {
    private String guid;
    private Exercise exercise;
    private BigDecimal duration;
    private BigDecimal energyOutput;
    private int hash;

    @Override
    public String toString() {
        return "CompletedExercise{" +
                "exercise=" + exercise +
                ", duration=" + duration +
                ", energyOutput=" + energyOutput +
                '}';
    }

    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
        this.hash = guid.hashCode();
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public BigDecimal getDuration() {
        return duration;
    }

    public void setDuration(BigDecimal duration) {
        this.duration = duration;
    }

    public BigDecimal getEnergyOutput() {
        return energyOutput;
    }

    public void setEnergyOutput(BigDecimal energyOutput) {
        this.energyOutput = energyOutput;
    }
}
