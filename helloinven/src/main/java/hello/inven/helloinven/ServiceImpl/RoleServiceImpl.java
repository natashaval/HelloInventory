package hello.inven.helloinven.ServiceImpl;

import hello.inven.helloinven.model.Role;
import hello.inven.helloinven.repository.RoleRepository;
import hello.inven.helloinven.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAll(){
        return roleRepository.findAll();
    }
}
