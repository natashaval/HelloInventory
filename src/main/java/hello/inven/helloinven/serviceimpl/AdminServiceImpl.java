package hello.inven.helloinven.serviceimpl;

import hello.inven.helloinven.exceptionhandler.NotFoundException;
import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.response.ResponseAjax;
import hello.inven.helloinven.repository.MyUserRepository;
import hello.inven.helloinven.repository.RoleRepository;
import hello.inven.helloinven.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    @Override
    public MyUser save(MyUser user, MultipartFile file) throws IOException {
        MyUser newUser = new MyUser();
        newUser.setId(user.getId());
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPhone(user.getPhone());
        newUser.setBirthday(user.getBirthday());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getManagerId() != null) newUser.setManagerId(user.getManagerId());

        newUser.setRole(user.getRole());

        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            InputStream is = file.getInputStream();
            String uploadDirectory = System.getProperty("user.dir") + "/uploads/employee/";

            try {
                Files.copy(is, Paths.get(uploadDirectory + fileName).toAbsolutePath().normalize(), StandardCopyOption.REPLACE_EXISTING);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            newUser.setPhoto(fileName);
        }
        return myUserRepository.save(newUser);

    }

    @Override
    public List<MyUser> findManagers(Integer roleId){
        return myUserRepository.findByRole(roleId);
    }

    @Override
    public MyUser findByEmployeeId(Long employeeId){
        MyUser myUser = myUserRepository.findById(employeeId).orElse(null);
        if (myUser != null) return myUser;
        else throw new NotFoundException("Employee Not Found!");
    }

    @Override
    public MyUser deleteEmployee(Long employeeId){
        MyUser myUser = myUserRepository.findById(employeeId).orElse(null);
        if (myUser != null) {
            if (!myUser.getPhoto().isEmpty()){
                String photoDirectory = System.getProperty("user.dir") + "/uploads/employee/";
                try {
                    File file = new File(photoDirectory + myUser.getPhoto());

                    if (file.delete()) {
                        System.out.println(file.getName() + "is deleted");
                    } else {
                        System.out.println("Delete file error");
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            myUserRepository.deleteById(employeeId);
            return myUser;
        }
        else
        throw new NotFoundException("Employee not found and failed in delete employee!");
    }

}
