package ru.productstar.servlets.portlets;

import javax.portlet.GenericPortlet;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.PortletException;
import java.io.IOException;

public class SimplePortlet extends GenericPortlet {
    @Override
    protected void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        response.setContentType("text/html");
        response.getWriter().println("<h1>Заголовок портлета</h1>");
        response.getWriter().println("<p>Текст портлета</p>");
    } 
}
