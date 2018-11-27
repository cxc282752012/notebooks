package com.books.noteadminribbon.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import com.google.gson.Gson;

public abstract class BaseController {
    public void renderTest(String str, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;UTF-8");
        PrintWriter out = response.getWriter();
        out.print(str);
        out.flush();
        out.close();
    }

    public void renderJson(Object objects, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;UTF-8");
        PrintWriter out = response.getWriter();
        out.print(new Gson().toJson(objects));
        out.flush();
        out.close();
    }

    public void renderNewJson(Object objects, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(new Gson().toJson(objects));
        out.flush();
        out.close();
    }

    public void renderForm(Object objects, HttpServletResponse response) throws IOException {
        response.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(new Gson().toJson(objects));
        out.flush();
        out.close();
    }

    public abstract String list(HttpServletRequest request, Model model);

    public abstract ResponseEntity<?> findList(HttpServletRequest request);

    public abstract String detail(HttpServletRequest request, Model model);

    public abstract ResponseEntity<?> findInfo(HttpServletRequest request);

}
