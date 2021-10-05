package Ex2_EranReuvenPkg;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet: ServletResults
 * Description: This servlet meant to deal with requests of the vector results to the client side.
 * Members: Results m_resultsObj - init to null, that object of Results class will init the vactor
 *          once it construct.
 */
@WebServlet(name = "ServletResults", urlPatterns = {"/ServletResults"} )
@MultipartConfig
public class ServletResults extends HttpServlet {
    /**
     * private member m_resultsObj - init to null, that object of Results class will init the vactor
     *  *          once it construct.
     */
    private Results m_resultsObj = null;

    /**
     * Function: init
     * @param config - ServletConfig Object
     * Description: This is the init function of the servlet. we try to
     *               creats the Results object with initparam "pollSize" that
     *               ServletSurvey inserted to the ServletContext for us.
     * Exceptions: On the throws functions in the Results param.
     */
    public void init(ServletConfig config) {
        try {
            super.init(config);
            m_resultsObj = new Results(Integer.parseInt(getServletContext().getAttribute("PollSize").toString()));
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function: doPost
     * @param request - client request
     * @param response - server response to the client
     * Description: This function called by "post" request from ServletResults.
     *                 it will send the voted value by formData and count it to the value
     *                 of this specific answer index of the survey. then, it calls to "doGet" to
     *                 use again the get functionally to send the results vector to the client.
     *                 We make security check with Attribute "isOk" since a client could send requests
     *                 directly here and avoid the 'bridge', so its not possible.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        if(getServletContext().getAttribute("isOk") == "1") {
            m_resultsObj.addCount(request.getParameter("hasVote"));
            doGet(request, response);
            getServletContext().setAttribute("isOk", "0");
        }
    }

    /**
     * Function: doGet
     * @param request - client request
     * @param response - server response to the client
     * Description: This function called by 'get' request from ServletResults.
     *                 In this specific case, it called from "doPost" function to
     *                 get the results to the client.
     *                 This function returns the vector as JSON. (explained how in
     *                 Results class and JsonResponse class javadoc, please read it.)
     * Exception: it will try to send it as a Json from JsonResponseOut func. if we gets
     *                 IOException by the functions that throws, we will catch it here.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            m_resultsObj.JsonResponseOut(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
