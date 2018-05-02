package com.parking.server.controller;

import com.parking.server.cache.CacheClient;
import com.parking.server.objects.Pojo;
import org.springframework.stereotype.Controller;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Controller
public class ControllerServlet extends HttpServlet {

    CacheClient client = new CacheClient();

    /*@RequestMapping(value = "/Test", method = RequestMethod.GET)
    public String Test(HttpServletRequest req, Model model) throws IOException {
        List<Pojo> list = client.getAll();
        req.setAttribute("list", list);

        model.addAttribute("Pojo", new Pojo(1, "name", 3));
        // model.addAttribute("parking",p);
        req.setAttribute("size", list.size());
        return "Test";
    }*/


    public void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
        List<Pojo> list = client.getAll();
        req.setAttribute("list", list);
        req.setAttribute("size", list.size());
    }

}
