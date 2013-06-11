package charts;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.ChartDAO;
import bean.ChartBean;

/**
 * Servlet implementation class ViewChartsRegisteredPerMun
 */
@WebServlet("/ViewChartsRegisteredPerMun")
public class ViewChartsRegisteredPerMun extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewChartsRegisteredPerMun() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String destination = "";
		if(session==null){
			destination = "/index.jsp";
		}
		else{
			if(session.getAttribute("username")==null){
				destination = "/index.jsp";
			}
			else{
				ArrayList<ChartBean> list = new ArrayList<ChartBean>();
				try {
					ChartDAO cDAO = new ChartDAO();
					list = cDAO.getChartReport(false);
					request.setAttribute("list", list);
				} catch (Exception e) {
					e.printStackTrace();
				}
				destination = "/highcharts/registeredchartPerMun.jsp";
			}
		}
		ServletContext sc = this.getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher(destination);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
