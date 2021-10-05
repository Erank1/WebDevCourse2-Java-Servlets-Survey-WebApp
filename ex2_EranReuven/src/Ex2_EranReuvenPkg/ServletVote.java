package Ex2_EranReuvenPkg;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet: ServletVote
 * Description: This servlet handle requests of votes. As we know, a client can only vote once. So
 *              This servlet is a bridge to 'ServletResults'. before calling and post the vote value,
 *              ServletVote checks if theres any cookie that we sign, if no - the client can vote!
 *              if yes, we will not forward the request, and the cilent will get an empty JSON value,
 *              so we catch it there and we know to print that way to the client that he already voted.
 */

@WebServlet(name = "ServletVote", urlPatterns = {"/ServletVote"} )
@MultipartConfig
public class ServletVote extends HttpServlet {
    /**
     * Function: VoteValidate
     * @param req - gets the request object of the client
     * @return - returns bool value: true - the client can vote. false - the client cannot vote.
     * Description: This function will iterate over the cookies array of the request.
     *              If we find the assign cookie that we put once a client votes, we will get false.
     *              if none of it found, we will get true (pass to continue and forwarding to ServletResults)
     */
    private boolean VoteValidate(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if(cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName() == "is");
                return false;
            }
        }
            return true;
    }

    /**
     * Function doPost
     * @param request - client request
     * @param response - server response to the client
     * Description: This function called by 'post' request to ServletVote.
     *              It will checks if the client already voted (VoteValidate function)
     *              If no - we will do nothing so the client gets empty Json Object.
     *              If yes - we will create and attach the cookie to the response.
     *              Then, we forward the request to 'ServletResults'.
     *                 (Notice: since the request is 'post', it will also call 'doPost' in
     *                 ServletResults.)
     *                 isOk attribute is to ensure that a client will not send directly
     *                 post request to ServletResults
     *
     * Exceptions: We try and catch if we have any ServletException or IOException.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
               if (VoteValidate(request)) {
                   response.addCookie(new Cookie("is", "1"));
                   getServletContext().setAttribute("isOk", "1");
                   RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ServletResults");
                   if (dispatcher != null)
                       dispatcher.forward(request, response);
               }
           } catch (ServletException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }
    }
}
