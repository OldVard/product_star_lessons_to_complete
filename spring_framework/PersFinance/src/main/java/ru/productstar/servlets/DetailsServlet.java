package ru.productstar.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ru.productstar.servlets.model.Transaction;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class DetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var context = req.getServletContext();

        resp.getWriter().println("Expenses:");
        List<Transaction> expenses = new ArrayList<>((List<Transaction>) context.getAttribute("expenses"));
        if (!expenses.isEmpty() && expenses != null) {
            for (Transaction e : expenses) {
                resp.getWriter().println(String.format("- [%s] : %d", e.getName(), e.getSum()));
            }
            resp.getWriter().println("\n");
        } else {
            // В нашем случае расходы никогда не пусты, но... who knows?
            resp.getWriter().println("No expenses. Yet.\n");
        }


        // Поскольку теперь мы отслеживаем доходы, то добавляем их в детали.
        resp.getWriter().println("Incomes:");
        // Получаем доходы из контекста.
        List<Transaction> incomes = new ArrayList<>((List<Transaction>) context.getAttribute("incomes"));
        // Если есть доходы, то выводим их.
        if(!incomes.isEmpty() && incomes != null) {
            for(Transaction i : incomes) {
                resp.getWriter().println(String.format("- [%s] : %d", i.getName(), i.getSum()));
            }
        } else {
            // Осведомляем если таковых нет.
            resp.getWriter().println("No incomes for now :(\n");
        }
    }
}
