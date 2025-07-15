package ru.productstar.servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import ru.productstar.servlets.model.Transaction;

// Сервлет для добавления доходов.
@WebServlet("/incomes/add")
public class IncomesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = req.getServletContext();

        // Получаем свободные деньги из контекста, чтобы суммировать их с доходами.
        int freeMoney = (int) context.getAttribute("freeMoney");
        // Получаем доходы из контекста.
        List<Transaction> incomes = new ArrayList<>((List<Transaction>)context.getAttribute("incomes"));
        // Добавляем новые доходы в контекст.
        for(String k : req.getParameterMap().keySet()) {
            int value = Integer.parseInt(req.getParameter(k));
            incomes.add(new Transaction(k, value));
            // Суммируем доходы с свободными деньгами.
            freeMoney += value;
        }

        // Обновляем контекст.
        context.setAttribute("incomes", incomes);
        context.setAttribute("freeMoney", freeMoney);
        // Перенаправляем на страницу с суммой.
        resp.sendRedirect("/summary");
    }
}
