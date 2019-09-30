package tdt4250.conversion.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.MissingResourceException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardServletPattern;

import tdt4250.conversion.api.ConversionHandler;
import tdt4250.conversion.api.ConversionResult;
import tdt4250.conversion.api.Converter;

@Component
@HttpWhiteboardServletPattern("/conversion/*")
public class UnitConversionServlet extends HttpServlet implements Servlet {
	
	private static final long serialVersionUID = 1L;
	
	private  ConversionHandler conversionHandler = new ConversionHandler();
	
	@Reference(
			cardinality = ReferenceCardinality.MULTIPLE,
			policy = ReferencePolicy.DYNAMIC,
			bind = "addConverter",
			unbind = "removeConverter"
	)
	public void addConverter(Converter converter) {
		this.conversionHandler.addConverter(converter);
	}
	public void removeConverter(Converter converter) {
		this.conversionHandler.removeConverter(converter);
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> segments = new ArrayList<>();
		String path = request.getPathTranslated();
		
		if(path != null) {
			segments.addAll(Arrays.asList(path.split("\\/")));
		}
		if(segments.size() > 0 && segments.get(0).length() == 0) {
			segments.remove(0);
		}
		if(segments.size() > 1) {
			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "Request must contain max. 1 path segment");
			return;
		}
		String src = request.getParameter("src");
		String val = request.getParameter("val");
		String tar = request.getParameter("tar");
		try {
			ConversionResult result = this.conversionHandler.convert(src, tar, val);
			response.setContentType("text/plain");
			PrintWriter writer = response.getWriter();
			writer.print(result.getMessage());
			return;
		} catch(NumberFormatException nfe) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parameter \"v\" has wrong format. Expected number");
			return;
		} catch(MissingResourceException mre) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, mre.getMessage());
			return;
		}
	}
}
