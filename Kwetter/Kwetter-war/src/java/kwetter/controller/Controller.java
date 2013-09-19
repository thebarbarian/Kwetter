package kwetter.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kwetter.service.KwetterService;


/**
 *
 * @author peterk
 */

@WebServlet (
        name="Controller",
        loadOnStartup=1,
        urlPatterns ={
          "/newTweet",
                
        })        

public class Controller extends HttpServlet {
   
    //KwetterService kws = new KwetterService();
    private KwetterService kws;
    
    @Override
    public void init()
    {
    }
       @Override
     public void doGet (HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
      {
      String userpath = request.getServletPath();     
      String url;
      url = "";
       Long postNr;
      switch (userpath) {
        case "/goWeblog":
             request.setAttribute("allPostings", kws.findAll());
            url="WebLog.jsp";
      
      }
      request.getRequestDispatcher(url).forward(request, response);
      }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String userpath = request.getServletPath();                    
        String url;     
        url="";        
       
         switch (userpath) {
            case "/postMessage":                 
                break;            
         }        
    request.setAttribute("allPostings", kws.findAll());    
    String lijstje = request.getAttribute("allPostings").toString();
    RequestDispatcher view = request.getRequestDispatcher(url);
    view.forward(request, response);   
    }
}
