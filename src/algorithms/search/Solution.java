package algorithms.search;

import java.util.ArrayList;
import java.util.List;

public class Solution
{
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
