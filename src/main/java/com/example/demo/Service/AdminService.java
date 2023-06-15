package com.example.demo.Service;
import java.util.List;
import com.example.demo.Model.Admin;

public interface AdminService {
    public boolean saveAdmin(Admin admin);
    public List<Admin> getAllAdmins();
    public Admin getAdmin(int id);
    public void deleteAdmin(Admin admin);
    public void updateAdmin(Admin admin);
}
