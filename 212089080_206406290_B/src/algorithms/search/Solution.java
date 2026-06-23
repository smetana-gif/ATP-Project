package algorithms.search;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Solution implements Serializable {

    private static final long serialVersionUID = 1L;
    private ArrayList<AState> solPath;

    public Solution(){
        this.solPath = new ArrayList<>();
    }


    public ArrayList<AState> getSolutionPath() {
        return solPath;
    }

    public void setSolPath(ArrayList<AState> solPath) {
        this.solPath = solPath;
    }
}
