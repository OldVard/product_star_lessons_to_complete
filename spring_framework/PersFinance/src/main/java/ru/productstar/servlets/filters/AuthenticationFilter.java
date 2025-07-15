package ru.productstar.servlets.filters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// Данный фильтр проверяет, авторизован ли пользователь.
// Применяется ко всем URL.
@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI();

        // Не проверяем авторизацию для страницы логина.
        if (path.equals("/") || path.equals("/login")) {
            chain.doFilter(request, response);
            return;
        }

        // Если пользователь не авторизован, то перенаправляем на страницу логина.
        HttpSession session  = req.getSession(false);
        if(session == null) {
            resp.getWriter().println("Authentication required. Please login.");
            return;
        }

        // Идем дальше по цепочке фильтров.
        chain.doFilter(request, response);
    }
}
