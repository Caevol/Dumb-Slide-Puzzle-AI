import java.util.*;

/**
 * Created by Caevol on 3/8/2017.
 */
public class SlidePuzzleSolver {

    //resets the priority_queue after reaching a step that is a multiple of RESET_RATE, keep this smaller than 30 and greater than 10 for good results.
    final int RESET_RATE = 20;
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
        int stepsTaken = 0;
        int resets = 0;
        while(!pointerMap.isSolved()){
            if(pointerMap.getStepNum() - stepsTaken > RESET_RATE){
                resets ++;
                stepsTaken = pointerMap.getStepNum();
                System.out.println("Reset priority Queue : " + resets);
                puzzleQueue.clear();
            }
            addMapCasesToQueue();
            pointerMap = puzzleQueue.poll();
        }

        System.out.println("Puzzle solved in : " + pointerMap.getStepNum() + " steps!");

        pointerMap.printSolution();

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
        ArrayList<Integer> a_1 = new ArrayList<Integer>(Arrays.asList(13, 0, 2, 3));
        ArrayList<Integer> a_2 = new ArrayList<Integer>(Arrays.asList(1, 15, 12, 6));
        ArrayList<Integer> a_3 = new ArrayList<Integer>(Arrays.asList(8, 4, 9, 5));
        ArrayList<Integer> a_4 = new ArrayList<Integer>(Arrays.asList(10, 7, 14, 11));

        ArrayList<ArrayList<Integer>> l = new ArrayList<>(Arrays.asList(a_1, a_2, a_3, a_4));
        MapState rootMap = new MapState(l);

        //print map before beginning
        for(ArrayList<Integer> a : rootMap.getMapState()){
            for(Integer i : a){
                System.out.print(i.toString() + ' ');
            }
            System.out.print('\n');
        }

        SlidePuzzleSolver s = new SlidePuzzleSolver(rootMap);
    }

}
