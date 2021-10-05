package Ex2_EranReuvenPkg;

import java.util.LinkedHashMap;

/**
 * Class: Results, extends from JsonResponse.
 * Description: This class managing the results vector of the survey.
 *              It's flexible class that can be also use to add and remove (it possible, but we are not asked to do so)
 *              It has two functions that manage calls for the vector.
 * Members: m_size - to hold the size of the vector (nums of survey answers)
 */
public class Results extends JsonResponse{
    /**
     * private member m_size - to hold the size of the vector (nums of survey answers)
     */
    private int m_size;

    /**
     * Function: Results Constructor
     * @param size - to init the vector to a fixed size.
     * Desctiption: This function gets by construct "Results" and build a map object by '0' value
     *             for the results vector.
     */
    public Results(int size){
        m_size = size;
        myMap = new LinkedHashMap<>();
        for(int i = 0; i< m_size; i++)
            myMap.put(Integer.toString(i), "0");
    }

    /**
     * Function: addCount
     * @param gotValue - the key of the value of the voted request
     * Description: This function gets the key of the vote request in the Results vector and increase
     *                 it by one.
     */
    public void addCount(String gotValue){
        myMap.put(gotValue, Integer.toString(Integer.parseInt(myMap.get(gotValue)) + 1));
    }
}
