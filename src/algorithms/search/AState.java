package algorithms.search;

import java.io.Serializable;

public abstract class AState implements Serializable {

    private static final long serialVersionUID = 1L;
    String state;
    AState cameFrom;
    double cost;

    public AState(String state){
        this.state =state;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public AState getCameFrom() {
        return cameFrom;
    }

    public void setCameFrom(AState cameFrom) {
        this.cameFrom = cameFrom;
    }
}
