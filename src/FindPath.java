import java.io.IOException;

public class FindPath {
    private Map pyramidMap;

    public FindPath(String fileName) {
        //Uses try/catch because we need to have a solution for an IOException
        try {
            pyramidMap = new Map(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DLStack path() {
        DLStack <Chamber> stack = new DLStack<Chamber>();
        Chamber startingChamber = pyramidMap.getEntrance();
        int numTreasures= pyramidMap.getNumTreasures();
        int treasureCount = 0;

        //Pushes and is marked Pushed
        stack.push(startingChamber);
        startingChamber.markPushed();

        // While stack not empty
        while(!stack.isEmpty()){
            Chamber current = stack.peek();

            //when treasure is found
            if (current.isTreasure()){
                treasureCount++;
                if (treasureCount == numTreasures){
                    break;
                }
            }
            //moving around the stack
            Chamber nextChamber = bestChamber(current);
            if (nextChamber != null){
                stack.push(nextChamber);
                nextChamber.markPushed();
            } else {
                Chamber poppedChamber = stack.pop();
                poppedChamber.markPopped();
            }
        }
        return stack;
    }

    public Map getMap() {
        return pyramidMap;
    }

    /*Checks based off constraints given by assignment
        boolean starts as false, checks for the first 3 criteria
        if those are true it executes for loop to check if any of the neighbours are lighted
        ends loop and returns true, else returns false;

     */
    public boolean isDim(Chamber currentChamber) {
        boolean boolVal = false;

        boolVal = currentChamber != null && !currentChamber.isSealed() &&
                !currentChamber.isLighted();
        if (boolVal){
            for (int i = 0; i < 6; i++) {
                if (currentChamber.getNeighbour(i) != null) {
                    if (currentChamber.getNeighbour(i).isLighted()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /*
    selects the best chamber to move to based off certain criteria
    Uses some helper methods that determine in order if criteria is filled
     */
    public Chamber bestChamber(Chamber currentChamber) {
        if(treasureCheck(currentChamber) != null){
            return treasureCheck(currentChamber);
        }
        else if(lightedCheck(currentChamber) != null){
            return lightedCheck(currentChamber);
        }
        else if(dimCheck(currentChamber) != null){
            return dimCheck(currentChamber);
        }
        return null;
    }

    //iterates through neighbours of chamber to find treasure, if none it retuns null
    // if there is one it is instantly returned and method is stopped
    private Chamber treasureCheck(Chamber currentChamber) {
        for (int i = 0; i < 6; i++) {
            //Treasure is the first this that we check for, checks if it's null
            if (currentChamber.getNeighbour(i) != null) {
                if (!currentChamber.getNeighbour(i).isMarked()){
                    if (currentChamber.getNeighbour(i).isTreasure()) {
                        return currentChamber.getNeighbour(i);
                    }
                }
            }
        }
        return null;
    }

    private Chamber lightedCheck(Chamber currentChamber) {
        for (int i = 0; i < 6; i++) {
            //Treasure is the first this that we check for, checks if it's null
            if (currentChamber.getNeighbour(i) != null) {
                if (!currentChamber.getNeighbour(i).isMarked()){
                    if (currentChamber.getNeighbour(i).isLighted()) {
                        return currentChamber.getNeighbour(i);
                    }
                }
            }
        }
        return null;
    }

    private Chamber dimCheck(Chamber currentChamber) {
        for (int i = 0; i < 6; i++) {
            //Treasure is the first this that we check for, checks if it's null
            if (currentChamber.getNeighbour(i) != null) {
                if (!currentChamber.getNeighbour(i).isMarked()){
                    if (isDim(currentChamber.getNeighbour(i))) {
                        return currentChamber.getNeighbour(i);
                    }
                }
            }
        }
        return null;
    }
}
