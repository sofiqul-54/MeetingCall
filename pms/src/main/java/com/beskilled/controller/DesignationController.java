package com.beskilled.controller;

import com.beskilled.entity.Department;
import com.beskilled.entity.Designation;
import com.beskilled.repo.DepartmentRepository;
import com.beskilled.repo.DesignationRepository;
import com.beskilled.repo.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/degt/")
public class DesignationController {
    @Autowired
    private DesignationRepository repo;
    @Autowired
    private OrganizationRepository rogRepo;



    @GetMapping(value = "add")
    public String degtAdd(Model model){
        model.addAttribute("degt",new Designation());
        model.addAttribute("orgList", this.rogRepo.findAll());
        return "designations/add";
    }

    @PostMapping(value = "add")
    public String deptAdd(@Valid Designation designation, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("list",this.repo.findAll());
            return "designations/add";

        }else{
            designation.setShortDesignation(designation.getShortDesignation().toUpperCase());
            this.repo.save(designation);
            model.addAttribute("degt", new Designation());
            model.addAttribute("successMsg","Successfully Saved!");
        }
        model.addAttribute("orgList", this.rogRepo.findAll());
        return "designations/add";
    }

    @GetMapping(value = "edit/{id}")
    public String viewEdit(Model model, @PathVariable("id") Long id){
        model.addAttribute("degt",repo.getOne(id));
        model.addAttribute("orgList", this.rogRepo.findAll());
        return "designations/edit";
    }
    @PostMapping(value = "edit/{id}")
    public String edit(@Valid Designation designation, BindingResult result, Model model,@PathVariable("id") Long id){
        if(result.hasErrors()){
            model.addAttribute("orgList", this.rogRepo.findAll());
            return "designations/edit";
        }
        else{
            designation.setId(id);
            designation.setShortDesignation(designation.getShortDesignation().toUpperCase());

            this.repo.save(designation);
        }
        model.addAttribute("orgList", this.rogRepo.findAll());
        return "redirect:/degt/list";
    }

    @GetMapping(value = "del/{id}")
    public String Delet(@PathVariable("id") Long id){
        if(id != null) {
            this.repo.deleteById(id);
        }
        return "redirect:/degt/list";
    }

    @GetMapping(value = "list")
    public String viewList(Model model){
        model.addAttribute("list",this.repo.findAll());
        return "designations/list";
    }
}
