/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab7;

import java.security.NoSuchAlgorithmException;
import static javatraining.SHA256Hasher.hashPassword;
import javax.swing.JOptionPane;

/**
 *
 * @author PXC
 */
public class Usermanagement {
    private UserDatabase userdatabase;
   
   
    public boolean login(String userId,String password,String status){
    if (status == "Student") {
            Student s = (userdatabase.findStudentById(userId));
            if (s != null && checkPassword(password,s.getpassword())) {
               return true;
            } else {
               return false;
            }
        } else if (status == "Instructor") {
            InstructorMangement ins = userdatabase.findInstructorManagementById(userId);
            if (ins != null && checkPassword(password,ins.getpassword())) {
                return true;
            } else {
                return false;
            }
        }
    return true;
    }
    public void signup(String userid,String userrole,String useremail,String userpassword,String username){
     try{
        userpassword= hashPassword(userpassword);
        if(userrole=="Student"){
        userdatabase.addStudent(new StudentManagement(userId,userrole,username,useremail,userpassword));
        }
        else{
        userdatabase.addInstructorManagement(new InstructorManagement(userId,userrole,username,useremail,userpassword))
        }
     }
     catch(NoSuchAlgorithmException ex){
         System.out.println(ex);
     }
    }
}
