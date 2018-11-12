//Name: Nikhil Jadhav
//Student ID: 801075504
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ITCS6114
 */
public class FuzzyIntervalSort <E extends Comparable<E>>{
    //swap CInterval object at the index, one, with the object at the index, another
    private void swap(ArrayList<CInterval<E>> intervals, int one, int another){
        CInterval<E> tmp = intervals.get(another);
        intervals.set(another, intervals.get(one));
        intervals.set(one, tmp);
    }
    
    private CInterval<E> findIntersectionWithRandomPivot(ArrayList<CInterval<E>> intervals, int start, int end){
        if (intervals == null) return null;
        
        int size = end - start + 1;
        int randomIndex = (int) (Math.random() * size) + start;
        
        CInterval<E> pivot = intervals.get(randomIndex);
        swap(intervals, randomIndex, end);
        
        CInterval<E> intersection = new CInterval<E>(pivot.getStart(),pivot.getEnd());
        for (int i = start; i <= end - 1; i++){
            CInterval<E> cur = intervals.get(i);
            if (cur.getEnd().compareTo(intersection.getStart()) <= 0
                    && cur.getStart().compareTo(intersection.getEnd()) >= 0){
                //TODO Change the start position of the intersection if in need
            	if(cur.getStart().compareTo(intersection.getStart()) > 0)
            		intersection.setStart(cur.getStart());
            
                //TODO Change the start position of the intersection if in need
            	if(cur.getEnd().compareTo(intersection.getEnd()) < 0)
            		intersection.setEnd(cur.getEnd());    

            }
        }
                
        return intersection;
    }
    
    private int partitionRight(ArrayList<CInterval<E>> intervals, CInterval<E> intersection, int start, int end){
        if (intervals == null || intersection == null || start > end || end < 0) return -1;
        
        int i = start - 1;

        for (int j = start; j <= end - 1; j++){
            CInterval<E> curJ = intervals.get(j);
            if (curJ.getStart().compareTo(intersection.getStart()) <= 0){
                //TODO Shift the element in the region where A[i] < a 
            	i = i + 1;
            	swap(intervals, i, j);

            }
        }
        swap(intervals, i + 1, end);
        return i + 1;
    }
    
    private int partitionLeftMiddle(ArrayList<CInterval<E>> intervals, CInterval<E> intersection, int r, int p, int end){
    	int i = p - 1;
        if (intervals == null || intersection == null || r == -1 || p > end || end < 0) return -1;
        
        for (int j = p; j <= r - 1; j++){
            CInterval<E> curJ = intervals.get(j);
            if (curJ.getEnd().compareTo(intersection.getEnd()) < 0){
                //TODO Shift the element in the region where B[i] < b
                i = i + 1;
                swap(intervals, i, j);

            }
        }
        //TODO Shift the pivot (which is defined as the up-bound variable here) 
        //     to the 'middle' position
        
        swap(intervals, i + 1, r);
        return i + 1;
    }
    
    public void fuzzySort(ArrayList<CInterval<E>> intervals, int start, int end){
        if (start < end){
            //TODO Retrieve intersection, named as the CInterval<E> variable intersection  based on randomly choosen pivot
        	CInterval<E> intersection = this.findIntersectionWithRandomPivot(intervals, start, end);
        	
        	//CInterval<Integer> intersection = new CInterval<Integer>(0, 0);
        	//intersection = (CInterval<Integer>) this.findIntersectionWithRandomPivot(intervals, start, end);

            int splitorEndIndex = this.partitionRight(intervals, intersection, start, end );
            int splitorStartIndex = this.partitionLeftMiddle(intervals, intersection, splitorEndIndex, start, end);
            
            //TODO Recursively fuzzy sorting the left part
            //     which locates absolutely before the intersection 

            fuzzySort(intervals, start, splitorStartIndex - 1);
            //TODO Recursively fuzzy sorting the left part
            //     which locates absolutely after the intersection
            fuzzySort(intervals, splitorEndIndex + 1, end);
        }   
    }
    
    public void printIntervals(ArrayList<CInterval<E>> intervals){
        if (intervals == null) return;
        
        for (int pos = 0; pos < intervals.size(); pos++){
            CInterval<E> cur = intervals.get(pos);
            System.out.println(cur.getStart() + ", " + cur.getEnd());
        }
    }
    
    
    private int partition(ArrayList<CInterval<E>> intervals, CInterval<E> intersection, int p, int end){
    	int i = p - 1;
        if (intervals == null || intersection == null || p > end || end < 0) return -1;
        
        for (int j = p; j <= end - 1; j++){
            CInterval<E> curJ = intervals.get(j);
            if (curJ.getStart().compareTo(intersection.getStart()) < 0){
                //TODO Shift the element in the region where B[i] < b
                i = i + 1;
                swap(intervals, i, j);

            }
        }
        //TODO Shift the pivot (which is defined as the up-bound variable here) 
        //     to the 'middle' position
        
        swap(intervals, i + 1, end);
        return i + 1;
    }
        
    public void quickSort(ArrayList<CInterval<E>> intervals, int start, int end){
        if (start < end){
            //TODO Retrieve intersection, named as the CInterval<E> variable intersection  based on randomly choosen pivot
        	CInterval<E> intersection = this.findIntersectionWithRandomPivot(intervals, start, end);
        	
        	//CInterval<Integer> intersection = new CInterval<Integer>(0, 0);
        	//intersection = (CInterval<Integer>) this.findIntersectionWithRandomPivot(intervals, start, end);
            int splitorStartIndex = this.partition(intervals, intersection, start, end);
            
            //TODO Recursively fuzzy sorting the left part
            //     which locates absolutely before the intersection 

            quickSort(intervals, start, splitorStartIndex - 1);
            //TODO Recursively fuzzy sorting the left part
            //     which locates absolutely after the intersection
            quickSort(intervals, splitorStartIndex + 1, end);
        }   
    }
    
    public static void main(String [] argv){
        ArrayList<CInterval<Integer>> list_fuzzysort = new ArrayList<CInterval<Integer>>();
        ArrayList<CInterval<Integer>> list_quicksort = new ArrayList<CInterval<Integer>>();
        
        System.out.println("Please type integer intervals line by line (start and end of each interval is separated by a white space:");
        Scanner keyboard = new Scanner(System.in);
        while (keyboard.hasNextLine()){
            String line = keyboard.nextLine();
            if (line.indexOf(",") >= 0){
                String[] intervalStr = line.split(",");
                list_fuzzysort.add(new CInterval<Integer>(new Integer(intervalStr[0].trim()), 
                    new Integer(intervalStr[1].trim())));
                list_quicksort.add(new CInterval<Integer>(new Integer(intervalStr[0].trim()), 
                        new Integer(intervalStr[1].trim())));
            }else
                break;
        }
        
        FuzzyIntervalSort<Integer> isort = new FuzzyIntervalSort<Integer>();
        
        System.out.println("Before the sorting: ");
        isort.printIntervals(list_fuzzysort);
        
        isort.fuzzySort(list_fuzzysort, 0, list_fuzzysort.size()-1);
        isort.quickSort(list_quicksort, 0, list_quicksort.size()-1);
        
        System.out.println("After fuzzy interval sorting: ");
        isort.printIntervals(list_fuzzysort);
        
        System.out.println("After quick interval sorting: ");
        isort.printIntervals(list_quicksort);
        
        
        int iteration_counter = 1;
        int size_of_input_array = 5000;
        while(iteration_counter < 10)
        {
        //Test Suite for large input array
        ArrayList<CInterval<Integer>> list_fuzzysort_test = new ArrayList<CInterval<Integer>>();
        ArrayList<CInterval<Integer>> list_quicksort_test = new ArrayList<CInterval<Integer>>();        
        
        size_of_input_array += 1000;
        
        int size = size_of_input_array + 1;
        int randomIndex_start = (int) (Math.random() * size) + 0;
        int randomIndex_end   = (int) (Math.random() * size) + 0;
        int counter = 0;
        
        while(counter < size_of_input_array)
        {
        	list_fuzzysort_test.add(new CInterval<Integer>(new Integer(randomIndex_start), 
                    new Integer(randomIndex_end)));
        	list_quicksort_test.add(new CInterval<Integer>(new Integer(randomIndex_start), 
                    new Integer(randomIndex_end)));
        	counter = counter + 1;
        }
        
        long startTimeFuzzy = System.nanoTime();
        isort.fuzzySort(list_fuzzysort_test, 0, list_fuzzysort_test.size()-1);
        long stopTimeFuzzy = System.nanoTime();
        long fuzzyTimeinMS = (stopTimeFuzzy - startTimeFuzzy) / 1000000;
        
        long startTimeQuick = System.nanoTime();
        isort.quickSort(list_quicksort_test, 0, list_quicksort_test.size()-1);
        long stopTimeQuick = System.nanoTime();
        long quickTimeinMS = (stopTimeQuick - startTimeQuick) / 1000000;
        
        System.out.println("iteration_counter = " + iteration_counter + "\tfuzzyTimeinMS = " + fuzzyTimeinMS + "\tquickTimeinMS = " + quickTimeinMS);
        iteration_counter++;
        }

    }
}
