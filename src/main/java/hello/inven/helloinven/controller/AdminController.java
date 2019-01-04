package hello.inven.helloinven.controller;

import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.response.ResponseAjax;
import hello.inven.helloinven.model.Role;
import hello.inven.helloinven.service.AdminService;
import hello.inven.helloinven.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    RoleService roleService;

    @GetMapping(value = "/admin/dashboard")
    public String admin() {
        return "/admin/dashboard";
    }

    // Show list of all employee
    @GetMapping(value = "/admin/employeelist")
    public String employeeList() {
        return "admin/employeelist";
    }

    @GetMapping(value = "/admin/employee")
    @ResponseBody
    public ResponseAjax employeeJSON(){
        List<MyUser> employees = adminService.findAll();
        return new ResponseAjax("Success", employees);
    }

    // redirect to register employee form with role as option list
    @GetMapping(value = "/admin/register")
    public String registerEmployeeForm(Model model) {
        model.addAttribute("newUser", new MyUser());
        List<Role> roleList = roleService.findAll();
        model.addAttribute("roles", roleList);
        return "admin/register";
    }

    // Init Binder for @Valid form
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    // Admin register employee and allowed to upload image
    @PostMapping(value = "/admin/register")
    public String registerEmployee(@Valid @ModelAttribute("newUser") MyUser newUser,BindingResult bindingResult,
                                   @RequestParam MultipartFile file, Model model) throws IOException {
        List<Role> roleList = roleService.findAll();
        model.addAttribute("roles", roleList);
        MyUser existingUser = adminService.findByUsername(newUser.getUsername());

        if (existingUser != null) {
            bindingResult.rejectValue("username", null, "Username is already exists");
            return "redirect:/admin/register?failure";
        }
        if (bindingResult.hasErrors()){
            return "admin/register";
        }
        else {
            adminService.save(newUser, file);
            return "redirect:/admin/register?success";
        }
    }

    // Membuat json untuk memilih manager pada "Select" register employee role 'EMPLOYEE'
    @GetMapping(value = "/admin/list/manager", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseAjax getManagerList(){
        List<MyUser> managerList = adminService.findManagers(1);
        return new ResponseAjax("Managers", managerList);
    }

    // Admin see employee details
    @GetMapping(value = "/admin/employee/{id}")
    public String employeeList(@PathVariable Long id, Model model) {
        MyUser myUser = adminService.findByEmployeeId(id);
        if (myUser != null)
        model.addAttribute("employee", myUser);
        return "admin/employeedetails";
    }

    // Admin can delete employee
    @DeleteMapping(value = "/admin/employee/{id}/delete")
    public @ResponseBody ResponseAjax employeeDelete(@PathVariable Long id){
        adminService.deleteEmployee(id);
        return new ResponseAjax("Deleted", "Employee has been deleted!");
    }
}
