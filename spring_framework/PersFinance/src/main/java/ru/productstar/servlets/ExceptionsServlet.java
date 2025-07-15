package ru.productstar.servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

// Сервлет для обработки ошибок.
@WebServlet("/error")
public class ExceptionsServlet extends HttpServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        // Получаем статус и исключение из контекста.
        Object status = req.getAttribute("jakarta.servlet.error.status_code");
        Object exception = req.getAttribute("jakarta.servlet.error.exception");

        // Устанавливаем тип контента.
        res.setContentType("text/html");

        // Если статус 404
        if (status != null && (int) status == 404) {
            res.getWriter().println("Error (404) — page not found");
            // Если статус 500
        } else if (exception != null) {
            Throwable t = (Throwable) exception;
            res.getWriter().println(String.format(
                    "Error (500) — %s: %s", t.getClass().getName(), t.getMessage()));
        } else {
            res.getWriter().println("Error occured - [Unknown]");
        }
    }
}
