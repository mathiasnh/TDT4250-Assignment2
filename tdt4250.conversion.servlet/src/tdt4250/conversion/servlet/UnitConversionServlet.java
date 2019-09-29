package tdt4250.conversion.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import tdt4250.conversion.api.Unit;
import tdt4250.conversion.api.UnitSearch;
import tdt4250.conversion.api.UnitSearchResult;

import org.osgi.service.component.annotations.*;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardServletPattern;

@Component
@HttpWhiteboardServletPattern("/unit/*")
public class UnitConversionServlet extends HttpServlet implements Servlet {

	private static final long serialVersionUID = 1L;

	private UnitSearch unitSearch = new UnitSearch();

	@Reference(
			cardinality = ReferenceCardinality.MULTIPLE,
			policy = ReferencePolicy.DYNAMIC,
			bind = "addConversion",
			unbind = "removeConversion"
	)
	public void addConversion(Unit unit) {
		unitSearch.addConversion(unit);
	}
	public void removeConversion(Unit unit) {
		unitSearch.removeConversion(unit);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> segments = new ArrayList<>();
		String path = request.getPathInfo();
		if (path != null) {
			segments.addAll(Arrays.asList(path.split("\\/")));
		}
		if (segments.size() > 0 && segments.get(0).length() == 0) {
			segments.remove(0);
		}
		
		if (segments.size() != 1 ) {
			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "Request must be on format: unit/{conversion}?q={number}");
			return;
		}
	
		String q = request.getParameter("q");
	
		
		UnitSearchResult result = unitSearch.search(segments.get(0), q);
		response.setContentType("text/plain");
		PrintWriter writer = response.getWriter();
		if (result.getLink() != null) {
			writer.print(result.getLink());
		}
		writer.print(result.getMessage());
	}

}