package com.example.demo.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.Model.Admin;
import com.example.demo.Repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    AdminRepository adminRepo;
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    
    @Override
    public boolean saveAdmin(Admin admin) {
        List<Admin> adminList = adminRepo.findAll();
        for(Admin obj : adminList){
            if(obj.getEmail().equals(admin.getEmail())){
                return false;
            }
        }
        admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
        adminRepo.save(admin);
        return true;
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepo.findAll();
    }

    @Override
    public Admin getAdmin(int id) {
        return (Admin) adminRepo.findById(id).get();
    }

    @Override
    public void deleteAdmin(Admin admin) {
        adminRepo.deleteById(admin.getId());
    }

    @Override
    public void updateAdmin(Admin admin) {
       Admin updatedAdmin = adminRepo.findById(admin.getId()).get();
       adminRepo.deleteById(admin.getId());
       if(admin.getName()!=null) {
           updatedAdmin.setName(admin.getName());
       }
       if(admin.getEmail()!=null) {
           updatedAdmin.setEmail(admin.getEmail());
       }
       if(admin.getPassword()!=null) {
           updatedAdmin.setPassword(admin.getPassword());
       }
       if(admin.getAddress()!=null) {
           updatedAdmin.setAddress(admin.getAddress());
       }
       if(admin.getMobileNumber()!=null) {
           updatedAdmin.setMobileNumber(admin.getMobileNumber());
       }
       adminRepo.save(updatedAdmin);
    }
}
