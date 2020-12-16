package com.waracle.cakemgr.controller;

import com.waracle.cakemgr.entity.Cake;
import com.waracle.cakemgr.service.CakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/ui")
public class FormController {

    CakeService cakeService;

    @Autowired
    public FormController(CakeService cakeService) {
        this.cakeService = cakeService;
    }

    @GetMapping("/list")
    public String listEmployees(Model model) {

        List<Cake> cakes = cakeService.findAll();

        model.addAttribute("cakes", cakes);

        return "ui/list-cakes";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {

        Cake cakeEntity = new Cake();

        model.addAttribute("cake", cakeEntity);

        return "ui/cake-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("cakeId") int cakeId, Model model) {

        // Get the cake by id
        Cake cakeEntity = cakeService.findById(cakeId);

        // set the cake as a model attribute and send it to the view to pre-populate
        model.addAttribute("cake", cakeEntity);

        return "ui/cake-form";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("cakeId") int cakeId, Model model) {

        // delete the cake
        cakeService.delete(cakeId);

        // use a redirect to /ui/list
        return "redirect:/ui/list";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("cake") Cake cakeEntity) {

        // save the cake
        cakeService.save(cakeEntity);

        // use a redirect to prevent duplicate submissions
        return "redirect:/ui/list";
    }

    @GetMapping("/manager-dash")
    public String onlyForManagers() {

        // use a redirect to prevent duplicate submissions
        return "ui/manager-dashboard";
    }

    @GetMapping("/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        //set file name and content type
        String filename = "cakes_" + currentDateTime+ ".csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        downloadCsv(response.getWriter(), cakeService.findAll());

    }

        public static void downloadCsv(PrintWriter writer, List<Cake> cakes) {
            writer.write("Cake ID, Title, Description, Image URL\n");
            for (Cake cake : cakes) {
                writer.write(cake.getCakeId() + "," + cake.getTitle() + "," + cake.getDesc() + "," + cake.getImage() + "\n");
            }
        }

}
