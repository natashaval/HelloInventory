package hello.inven.helloinven.ServiceImpl;

import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.model.Role;
import hello.inven.helloinven.repository.MyUserRepository;
import hello.inven.helloinven.repository.RoleRepository;
import hello.inven.helloinven.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    MyUserRepository myUserRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<MyUser> findAll(){
        return myUserRepository.findAll();
    }

    @Override
    public MyUser findByUsername(String username){
        return myUserRepository.findByUsername(username);
    }

//    https://memorynotfound.com/spring-security-user-registration-example-thymeleaf/
    @Override
    public MyUser save(MyUser user, MultipartFile file) throws IOException {
        MyUser newUser = new MyUser();
        System.out.print("Bikin NEW User");
        newUser.setId(user.getId());
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPhone(user.getPhone());
        newUser.setBirthday(user.getBirthday());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        /*
        Integer roleID = user.getRole().getRoleId();
        Role userRole = roleRepository.findById(roleID).get();
//        System.out.print("roleID: " + roleID + "ROLE: " + userRole);
        System.out.print("ROLE: " + userRole);
        newUser.setRole(userRole);
        */
        newUser.setRole(user.getRole());

        //        http://aralmighty.com/uploading-files-spring-boot
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            InputStream is = file.getInputStream();
//            String uploadDirectory = Paths.get(fileName).toAbsolutePath().normalize().toString(); // akan mengeluarkan absolute path dari helloinven
//            String uploadDirectory = "src/main/uploads/employee/";
//            String uploadDirectory = "src/main/resources/static/img/employee/";

//            https://www.youtube.com/watch?v=Hef5pJkNCvA
            String uploadDirectory = System.getProperty("user.dir") + "/uploads/employee/";

            try {
                Files.copy(is, Paths.get(uploadDirectory + fileName).toAbsolutePath().normalize(), StandardCopyOption.REPLACE_EXISTING);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            newUser.setPhoto(fileName);
            System.out.println("namafileName: " + uploadDirectory + fileName);
            System.out.println("\ngetNamaFile " + file.getName() + ";apaFile " + file.getContentType());
            System.out.println("\ngetFileAbsolutePath: " + Paths.get(fileName).toAbsolutePath().normalize());
            System.out.println("\nFileSUDAHDITAMBAH: " + Paths.get(uploadDirectory + fileName).toAbsolutePath().normalize());
        }
        return myUserRepository.save(newUser);

    }

    @Override
    public List<MyUser> findManagers(Integer roleId){
        return myUserRepository.findByRole(roleId);
    }

}
