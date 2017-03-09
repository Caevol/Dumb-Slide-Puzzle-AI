import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Caevol on 3/8/2017.
 */
public class MapState {
    private ArrayList<ArrayList<Integer>> map;
    private int size;
    private int distance;
    private int stepNum;
    private boolean isSolved;

    public MapState(int s){
        size = s;
        map = new ArrayList<>();
        //create and randomize map
        ArrayList<Integer> tmpMap = new ArrayList<>();
        for(int i = 0; i < size*size; ++i){
            tmpMap.add(i);
        }
        Collections.shuffle(tmpMap);

        //take randomized map and add it to map ArrayList
        for(int i = 0; i < size; ++i){
        map.add(new ArrayList<>());
        }
        for(int i = 0; i < size*size; ++i){
            map.get(i/size).add(tmpMap.get(i));
        }

        distance = getDistanceFromCompletion();
        isSolved = checkIfFinished();
        stepNum = 0;

    }

    public int getSize(){
        return this.size;
    }

    public MapState(ArrayList<ArrayList<Integer>> m){
        //create map from clone
        size = m.size();
        map = m;
        distance = getDistanceFromCompletion();
        isSolved = checkIfFinished();
        stepNum = 0;
    }

    public MapState(ArrayList<ArrayList<Integer>> m, int stepi){
        this(m);
        stepNum = stepi;
    }

    public ArrayList<ArrayList<Integer>> getMapState(){
        return map;
    }

    public int getValueAtPosition(int x, int y){
     return map.get(y).get(x);
    }

    public int getStepNum(){
        return this.stepNum;
    }

    public int getDistance(){
        return this.distance;
    }

    public boolean isSolved(){
        return this.isSolved;
    }

    private boolean checkIfFinished(){
        for(int i = 0; i < this.size * this.size; ++i){
            if(map.get(i/this.size).get(i%this.size) != i){
                return false;
            }
        }
        return true;
    }

    private int getDistanceFromCompletion(){
        int sum = 0;
        for(int y = 0; y < this.size; ++y){
            for(int x = 0; x < this.size; ++x){
                sum += getSingleSlideDistance(x, y);
            }
        }
        return sum;
    }

    private int getSingleSlideDistance(int positionX, int positionY){

        int val = getValueAtPosition(positionX, positionY);
        if(val == size * size - 1){
            return 0; //algorithm should not try to place the empty slide until last
        }

        int goalPosX = val % this.size;
        int goalPosY = val / this.size;
        return Math.abs(positionX - goalPosX) + Math.abs(positionY - goalPosY);
    }

}
