package com.binarfud.challenge7.Service;



import com.binarfud.challenge7.Model.Users;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<Users> findAllUsers();

    Optional<Users> findByUsername(String username);

    Optional<Users> findById(Long id);
    Boolean addNewUser(Users users);

    void deleteUser(Long users_id);

    boolean deleteAllUser();

     boolean updateUserbyId(Users user);
//    Page<User> getUserPaged(int page);
}
