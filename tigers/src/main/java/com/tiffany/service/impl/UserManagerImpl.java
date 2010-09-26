package com.tiffany.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jws.WebService;
import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.tiffany.dao.UserDao;
import com.tiffany.model.Role;
import com.tiffany.model.User;
import com.tiffany.service.UserExistsException;
import com.tiffany.service.UserManager;
import com.tiffany.service.UserService;


/**
 * Implementation of UserManager interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@WebService(serviceName = "UserService", endpointInterface = "com.tiffany.service.UserService")
public class UserManagerImpl extends UniversalManagerImpl implements UserManager, UserService {
    private UserDao dao;
    private PasswordEncoder passwordEncoder;

    /**
     * Set the Dao for communication with the data layer.
     * @param dao the UserDao that communicates with the database
     */
    @Required
    public void setUserDao(UserDao dao) {
        this.dao = dao;
    }

    /**
     * Set the PasswordEncoder used to encrypt passwords.
     * @param passwordEncoder the PasswordEncoder implementation
     */
    @Required
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * {@inheritDoc}
     */
    public User getUser(String userId) {
        return dao.get(new Long(userId));
    }

    /**
     * {@inheritDoc}
     */
    public List<User> getUsers(User user) {
        return dao.getUsers();
    }
    
    
    /**
     * {@inheritDoc}
     */
    public User saveUser(User user) throws UserExistsException {

        if (user.getVersion() == null) {
            // if new user, lowercase userId
            user.setUsername(user.getUsername().toLowerCase());
        }
        
        // Get and prepare password management-related artifacts
        boolean passwordChanged = false;
        if (passwordEncoder != null) {
            // Check whether we have to encrypt (or re-encrypt) the password
            if (user.getVersion() == null) {
                // New user, always encrypt
                passwordChanged = true;
            } else {
                // Existing user, check password in DB
                String currentPassword = dao.getUserPasswordForId(user.getId());
                if (currentPassword == null) {
                    passwordChanged = true;
                } else {
                    if (!currentPassword.equals(user.getPassword())) {
                        passwordChanged = true;
                    }
                }
            }

            // If password was changed (or new user), encrypt it
            if (passwordChanged) {
                user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
            }
        } else {
            log.warn("PasswordEncoder not set, skipping password encryption...");
        }
        
        try {
            return dao.saveUser(user);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new UserExistsException("User '" + user.getUsername() + "' already exists!");
        } catch (EntityExistsException e) { // needed for JPA
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new UserExistsException("User '" + user.getUsername() + "' already exists!");
        }
    }

    /**
     * {@inheritDoc}
     */
    public void removeUser(String userId) {
        log.debug("removing user: " + userId);
        dao.remove(new Long(userId));
    }

    /**
     * {@inheritDoc}
     * @param username the login name of the human
     * @return User the populated user object
     * @throws UsernameNotFoundException thrown when username not found
     */
    public User getUserByUsername(String username) throws UsernameNotFoundException {
        return (User) dao.loadUserByUsername(username);
    }
    
    //=========================================================================================
    public List<User> findUsersByCompanyName(String companyName) {
    	return dao.findUsersByCompanyName(companyName);
    }
    
    public List<User> findUserByUsernameAndCompanyName(String username, String companyName) {
    	return dao.findUserByUsernameAndCompanyName(username, companyName);
    }
    
    public List<User> getContractors() {
    	List<User> contractorList = new ArrayList<User>();
    	List<User> userList = dao.getUsers();
    	Role role = new Role();
    	role.setName("ROLE_CONTRACTOR");
    	Iterator<User> users = userList.iterator();	    	
		while (users.hasNext()) {
			User user = (User)users.next();
			if (user.getRoles().contains(role)) {
				contractorList.add(user);
			}						
		}
    	return contractorList;
    } 
    public List<User> getOfficers() {
    	List<User> contractorList = new ArrayList<User>();
    	List<User> userList = dao.getUsers();
    	Role role = new Role();
    	role.setName("ROLE_OFFICER");
    	Iterator<User> users = userList.iterator();	    	
		while (users.hasNext()) {
			User user = (User)users.next();
			if (user.getRoles().contains(role)) {
				contractorList.add(user);
			}						
		}
    	return contractorList;
    }  
    public List<User> getLaboratories() {
    	List<User> laboratoryList = new ArrayList<User>();
    	List<User> userList = dao.getUsers();
    	Role role = new Role();
    	role.setName("ROLE_LABORATORY");
    	Iterator<User> users = userList.iterator();	    	
		while (users.hasNext()) {
			User user = (User)users.next();
			if (user.getRoles().contains(role)) {
				laboratoryList.add(user);
			}						
		}
    	return laboratoryList;
    }  
    
    public List<User> getMyLaboratories(String username) {
    	List<User> laboratoryList = new ArrayList<User>();
    	User con = getUserByUsername(username);
    	laboratoryList.addAll(con.getChildren());
    	return laboratoryList;
    }
    
    public List<User> getAvailableLaboratories(String username) {
    	List<User> laboratoryList = new ArrayList<User>();
    	List<User> allLaboratories = getLaboratories();
    	List<User> myLaboratories = getMyLaboratories(username);
    	for (User lab : allLaboratories) {
    		if (!myLaboratories.contains(lab)) laboratoryList.add(lab);
    	}
    	return laboratoryList;
    }
 
    public List<User> getMyContractors(String username) {
    	User lab = getUserByUsername(username);
    	List<User> contractorList = new ArrayList<User>();
    	List<User> contractors = getContractors();
    	for(User con : contractors) {  
    		if (con.getChildren().contains(lab)) contractorList.add(con);
    	}
    	return contractorList;
    }
    
    public boolean isMyLaboratory(String username, User lab) {
    	return getMyLaboratories(username).contains(lab);
    }
    public boolean isMyContractor(String username, User con) {
    	return getMyContractors(username).contains(con);
    }
}
