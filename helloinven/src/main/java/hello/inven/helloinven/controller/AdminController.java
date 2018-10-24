package hello.inven.helloinven.controller;

import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.model.Role;
import hello.inven.helloinven.repository.RoleRepository;
import hello.inven.helloinven.service.AdminService;
import hello.inven.helloinven.service.MyUserDetailsService;
import hello.inven.helloinven.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

//    @Autowired
//    MyUserDetailsService myUserDetailsService;

    @GetMapping(value = "/admin/dashboard")
    public String admin() {
        return "/admin/dashboard";
    }

    @GetMapping(value = "/admin/employeelist")
    public String employeeList(Model model) {
        List<MyUser> employees = adminService.findAll();
        for (MyUser emp : employees) {
            System.out.print(emp.getEmail());
            System.out.print(emp.getName());
            System.out.print("ROLE: " + emp.getRole().getRole());
        }
        model.addAttribute("employees", employees);
        return "admin/employeelist";
    }

    @Autowired
    RoleService roleService;

    @GetMapping(value = "/admin/register")
    public String registerEmployeeForm(Model model) {
        model.addAttribute("newUser", new MyUser());
//        https://o7planning.org/en/11659/thymeleaf-form-select-option-example
        List<Role> roleList = roleService.findAll();
        model.addAttribute("roles", roleList);
        for (Role role: roleList){
            System.out.println(role.getRole());
        }
        return "admin/register";
    }

//    @Autowired
//    RoleRepository roleRepository;
//
////    https://medium.com/@grokwich/spring-boot-thymeleaf-html-form-handling-762ef0d51327
//    @ModelAttribute("roleList")
//    public String[] getRoleList() {
//        List<Role> roleAll = roleRepository.findAll();
//        String[] roleName = new String[5];
//        int i = 0;
//        for (Role role: roleAll) {
//            String roleTemp = role.getRole();
//            roleName[i++] = roleTemp;
//        }
//        return roleName;
//
//    }

//    @Value("${upload.employee.directory}")
//    private String uploadDirectory;


//    https://www.javacodegeeks.com/2017/10/validation-thymeleaf-spring.html
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
    //    https://memorynotfound.com/spring-security-user-registration-example-thymeleaf/
    @PostMapping(value = "/admin/register")
    public String registerEmployee(@Valid @ModelAttribute("newUser") MyUser newUser,BindingResult bindingResult,
                                   @RequestParam MultipartFile file, Model model) throws IOException {
        List<Role> roleList = roleService.findAll();
        model.addAttribute("roles", roleList);

        MyUser existingUser = adminService.findByUsername(newUser.getUsername());
        System.out.print("Sudah nemu existingUser: " + existingUser);
        System.out.print("newUser: " + newUser.getName() + "username: " + newUser.getUsername());
//        model["roleList"] = getRoleList();
        if (existingUser != null) {
            bindingResult.rejectValue("username", null, "Username is already exists");
            return "redirect:/admin/register?failure";
        }
        System.out.println("BINDING RESULT =" + bindingResult.hasErrors());
        if (bindingResult.hasErrors()){
            System.out.println("BINDING RESULT ERROR");
            return "admin/register";
        }
        else {
//            System.out.print("Everything is OKAY to Register");
//        }

        /*
//        http://aralmighty.com/uploading-files-spring-boot
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            InputStream is = file.getInputStream();
//            String uploadDirectory = Paths.get(fileName).toAbsolutePath().normalize().toString(); // akan mengeluarkan absolute path dari helloinven
            String uploadDirectory = "src/main/uploads/employee/";
            String location = uploadDirectory + fileName;
            Files.copy(is, Paths.get(uploadDirectory + fileName).toAbsolutePath().normalize(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("namafileName: " + uploadDirectory + fileName);
            System.out.println("\ngetNamaFile " + file.getName() + ";apaFile " + file.getContentType());
            System.out.println("\ngetFileAbsolutePath: " + Paths.get(fileName).toAbsolutePath().normalize());
            System.out.println("\nFileSUDAHDITAMBAH: " + Paths.get(uploadDirectory + fileName).toAbsolutePath().normalize());
        }
        */

//        myUserDetailsService.save(newUser);
            adminService.save(newUser, file);
            return "redirect:/admin/register?success";
        }
    }
}
