package Ex2_EranReuvenPkg;

import java.io.*;
import java.util.LinkedHashMap;

/**
 * Class: Survey, extends of JsonResponse
 * Description: This class is to manage survey object.
 *              It will get filename from the construct call (in our case, from ServletSurvey)
 *              and will run over the file and creats Map that the first key is the title (question)
 *              and all other keys is the answers. It also extends of JsonResponse to response the map
 *              as Json object just like 'Results' class.
 *
 * Members: BufferedReader m_reader - to open the file to read
 *          String m_line - will hold each line we call in iterate.
 *          int SurveySize - will hold the survey size
 */
public class Survey extends JsonResponse{
    /**
     * BufferedReader m_reader - to open the file to read
     */
    private BufferedReader m_reader;
    /**
     * String m_line - will hold each line we call in iterate.
     */
    private String m_line;
    /**
     * int SurveySize - will hold the survey size
     */
    private int SurveySize = 0;

    /**
     * Function: Survey constructor
     * @param filename - The file path we get from the construct call
     * @throws IOException - Throws IOE exception if open the file, read, etc has been failed.
     * Description: This is the construct function. it init the map and use surveyMapLoader to
     *              load the survey to the map.
     */
    public Survey(String filename) throws IOException {
            myMap = new LinkedHashMap<>();
            surveyMapLoader(filename);
    }

    /**
     * Function: surveyMapLoader
     * @param filename - filename path
     * @throws IOException - throws when open the file/read from the file has been failed.
     * Description: This function gets from the called section the filepath and open the file
     *              to read. then, we read the first line because we know it the question, and after that
     *              in the loop, we read all the other lines (the answers)
     */
    public void surveyMapLoader(String filename) throws IOException {
        m_reader = new BufferedReader(new FileReader(filename));
        m_line = m_reader.readLine();
        myMap.put(Integer.toString(0), m_line);

        int id = 1;
        while((m_line = m_reader.readLine())!=null) {
            myMap.put(Integer.toString(id), m_line);
            ++id;
        }
        SurveySize = id;
        m_reader.close();
    }

    /**
     * Function: getSurveySize
     * @return - the Survey Size
     */
    public int getSurveySize(){
        return SurveySize;
    }
}
