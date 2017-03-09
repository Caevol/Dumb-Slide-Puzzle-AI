import java.util.*;

/**
 * Created by Caevol on 3/8/2017.
 */
public class SlidePuzzleSolver {

    //resets the priority_queue after reaching a step that is a multiple of RESET_RATE, keep this smaller than 30
    //as the puzzle size gets bigger, consider making the reset_rate smaller
    final int RESET_RATE = 7;
    final int SAVED_FRONTIER = 25;

    private int stepsTaken = 0;
    private int resets = 0;
    //heuristic used to compare map states
    Comparator<MapState> compareMaps = (MapState o1, MapState o2)->
            new Integer(o1.getDistance()+o1.getStepNum()).compareTo(o2.getDistance()+o2.getStepNum());

    PriorityQueue<MapState> puzzleQueue;
    MapState pointerMap;
    /**
     * constructor runs the entire function
     * @param root first state to build from
     */
    public SlidePuzzleSolver(MapState root){
        puzzleQueue = new PriorityQueue<MapState>(compareMaps);
        pointerMap = root;
        while(!pointerMap.isSolved()){
            if(pointerMap.getStepNum() - stepsTaken > RESET_RATE){
                resetQueue();
                System.out.println("Distance: " + pointerMap.getDistance());
            }
            addMapCasesToQueue();
            pointerMap = puzzleQueue.poll();
        }
        pointerMap.printSolution();
        System.out.println("Puzzle solved in : " + pointerMap.getStepNum() + " steps!");

    }

    private void resetQueue(){
        resets ++;
        stepsTaken = pointerMap.getStepNum();
        System.out.println("Reset priority Queue : " + resets);
        Stack<MapState> saveList = new Stack<MapState>();
        for(int i = 0; i < SAVED_FRONTIER; ++i){
            saveList.add(puzzleQueue.poll());
        }
        puzzleQueue.clear();
        for(int i = 0; i < SAVED_FRONTIER; ++i){
            puzzleQueue.add(saveList.pop());
        }
    }

    private void addMapCasesToQueue(){

        //deep copy pointerMaps mapstate
        ArrayList<ArrayList<Integer>> cloneMap = copyMap(pointerMap.getMapState());
        int posX = 0;
        int posY = 0;

        //find slider value
        for(int y = 0; y < cloneMap.size(); ++y){
            for(int x = 0; x < cloneMap.get(y).size(); ++x){
                if(cloneMap.get(y).get(x) == cloneMap.size() * cloneMap.get(y).size() - 1){
                    posX = x;
                    posY = y;
                }
            }
        }

        //up
        if(posY != 0){
            ArrayList<ArrayList<Integer>> upMap = copyMap(cloneMap);
            swapIndexes(upMap, posX, posY, posX, posY -1);
            puzzleQueue.add(new MapState(upMap, pointerMap.getStepNum() + 1, pointerMap));
        }
        //down
        if(posY != pointerMap.getSize() -1){
            ArrayList<ArrayList<Integer>> downMap = copyMap(cloneMap);
            swapIndexes(downMap, posX, posY, posX, posY +1);
            puzzleQueue.add(new MapState(downMap, pointerMap.getStepNum() + 1, pointerMap));
        }
        //left
        if(posX != 0){
            ArrayList<ArrayList<Integer>> leftMap = copyMap(cloneMap);
            swapIndexes(leftMap, posX, posY, posX -1, posY);
            puzzleQueue.add(new MapState(leftMap, pointerMap.getStepNum() + 1, pointerMap));
        }
        //right
        if(posX != pointerMap.getSize() - 1){
            ArrayList<ArrayList<Integer>> rightMap = copyMap(cloneMap);
            swapIndexes(rightMap, posX, posY, posX +1, posY);
            puzzleQueue.add(new MapState(rightMap, pointerMap.getStepNum() + 1, pointerMap));
        }



    }

    private void swapIndexes( ArrayList<ArrayList<Integer>> list, int posX1, int posY1, int posX2, int posY2){
        Integer tmp = list.get(posY1).get(posX1);
        list.get(posY1).set(posX1, list.get(posY2).get(posX2));
        list.get(posY2).set(posX2, tmp);
    }

    private ArrayList<ArrayList<Integer>> copyMap(ArrayList<ArrayList<Integer>> a){
        ArrayList<ArrayList<Integer>> cloneMap = new ArrayList<>();
        for(int y = 0; y < a.size(); ++y){
            cloneMap.add(new ArrayList<>());
            for(int x = 0; x < a.get(y).size(); ++x){
                cloneMap.get(y).add(a.get(y).get(x));
            }
        }
        return cloneMap;
    }

    public static void main(String[] args) {
       /*
        4x4 puzzle
        ArrayList<Integer> a_1 = new ArrayList<Integer>(Arrays.asList(13, 0, 2, 3));
        ArrayList<Integer> a_2 = new ArrayList<Integer>(Arrays.asList(1, 15, 12, 6));
        ArrayList<Integer> a_3 = new ArrayList<Integer>(Arrays.asList(8, 4, 9, 5));
        ArrayList<Integer> a_4 = new ArrayList<Integer>(Arrays.asList(10, 7, 14, 11));

        ArrayList<ArrayList<Integer>> l = new ArrayList<>(Arrays.asList(a_1, a_2, a_3, a_4));
        */

        // 5x5 puzzle
        ArrayList<Integer> a_1 = new ArrayList<Integer>(Arrays.asList(5,22,18,24,14));
        ArrayList<Integer> a_2 = new ArrayList<Integer>(Arrays.asList(1,3,11,2,13));
        ArrayList<Integer> a_3 = new ArrayList<Integer>(Arrays.asList(7,0,21,4,9));
        ArrayList<Integer> a_4 = new ArrayList<Integer>(Arrays.asList(10,6,15,17,12));
        ArrayList<Integer> a_5 = new ArrayList<Integer>(Arrays.asList(20,8,19,16,23));

        ArrayList<ArrayList<Integer>> l = new ArrayList<>(Arrays.asList(a_1, a_2, a_3, a_4, a_5));
        MapState rootMap = new MapState(l);

        SlidePuzzleSolver s = new SlidePuzzleSolver(rootMap);
    }

}
