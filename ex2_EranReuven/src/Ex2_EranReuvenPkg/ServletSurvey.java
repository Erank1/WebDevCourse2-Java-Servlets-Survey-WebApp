package Ex2_EranReuvenPkg;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Servlet: ServletSurvey
 * Init param: filepath (to /poll.txt)
 * Description: This servlet is manage request of the client to get the survey.
 *              This servlet is called only once per browser, we keep the data after that.
 *              Also, it init the file and survey object, so any other browser will get the
 *              survey after it already loaded on the first call of the first browser who call it.
 * Member: Survey m_survey - init to null. will construct Survey object by call (for further explanations
 *          About 'Survey' class, please read its javadoc.)
 *          Boolean valid - if we have any problem as requested we will make a msg in the client side.
 */

@WebServlet(name = "ServletSurvey",
        urlPatterns = {"/ServletSurvey"},
        initParams = @WebInitParam(name = "filepath", value = "/poll.txt")
)
public class ServletSurvey extends HttpServlet {
    /**
     * private member m_survey - init to null. will construct Survey object by call
     * (for further explanations About 'Survey' class, please read its javadoc.)
     */
    private Survey m_survey = null;
    /**
     * private bool member valid - if we have any problem as requested we will make a msg in the client side.
     */
    private Boolean valid = true;

    /**
     * Function: init
     * @param config - ServletConfig object
     * Description: This function called only once when the servlet is call the first time.
     *               It will creates the Survey object and construct it with the filepath initParam.
     *               Also, it will create Attribute of PollSize for ServletRequest init.
     * Exception: we will catch Exceptions of IO or gets (context etc) and catch it here.
     */
    public void init(ServletConfig config) {
        try {
            super.init(config);
            m_survey = new Survey(getServletContext().getRealPath(getInitParameter("filepath")));
            getServletContext().setAttribute("PollSize", Integer.toString(m_survey.getSurveySize()));
        } catch (Exception e) {
            e.printStackTrace();
            valid = false;
            return;
        }
    }

    /**
     * Function: doGet
     * @param request - client request
     * @param response - server response to the client
     * Description: This function calls by 'get' request to 'ServletSurvey'.
     *                 It will see if the servlet is over size 2 AND valid is true, means we have
     *                 at least two answers to a question and no IOException has been thrown.
     *                 if yes, we Returns the JSON object. if no, we returns nothing so in the client side we catch
     *                 empty JSON value so we print the error we asked.
     * Exception: we cath IOException if thrown from JsonResponseOut func.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        if(m_survey.getSurveySize() > 2 && valid)
            try {
                m_survey.JsonResponseOut(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
