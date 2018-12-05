package hello.inven.helloinven.ServiceImpl;

import hello.inven.helloinven.model.ItemSerial;
import hello.inven.helloinven.model.MyUser;
import hello.inven.helloinven.model.ResponseAjax;
import hello.inven.helloinven.repository.ItemSerialRepository;
import hello.inven.helloinven.repository.MyUserRepository;
import hello.inven.helloinven.service.ClerkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Service
public class ClerkServiceImpl implements ClerkService {
    @Autowired
    MyUserRepository myUserRepository;

    @Autowired
    ItemSerialRepository itemSerialRepository;

    @Override
    public ResponseAjax findManagerAndEmployee(){
        return new ResponseAjax("Done", myUserRepository.findManagerAndEmployee());
    }

    @Override
    public ResponseAjax assignItemSerial(MyUser clerk, Long itemId, List<Long> employeeValues){
        Long clerkId = clerk.getId();
        List<ItemSerial> itemSerialNotAssigned = itemSerialRepository.findItemSerialNotAssigned(itemId, clerkId);

        if (itemSerialNotAssigned.size() >= employeeValues.size()){
            for (int i = 0; i < employeeValues.size(); i++){
                ItemSerial itemSerial = itemSerialNotAssigned.get(i);
                MyUser myUser = myUserRepository.findById(employeeValues.get(i)).orElse(null);
                itemSerial.setMyUser(myUser);
                itemSerialRepository.save(itemSerial);
            }

            return new ResponseAjax("Done", "Assign Item Serial to Employee Finished");
        }

        else {
            return new ResponseAjax("Failed", "Assign Item Serial to Employee FAILED");
        }

    }
}
