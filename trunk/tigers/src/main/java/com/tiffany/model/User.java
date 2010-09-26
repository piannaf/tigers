package com.tiffany.model;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Iterator;

/**
 * This class represents the basic "user" object in AppFuse that allows for authentication
 * and user management.  It implements Acegi Security's UserDetails interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *         Updated by Dan Kibler (dan@getrolling.com)
 *         Extended to implement Acegi UserDetails interface
 *         by David Carter david@carter.net
 */
@Entity
@Table(name="app_user")
public class User extends BaseObject implements Serializable, UserDetails {
    private static final long serialVersionUID = 3832626162173359411L;

    private Long id;
    private String username;                    // required
    private String password;                    // required
    private String confirmPassword;
    private String companyName;                   // required
    private String email;                       // required; unique
    private String phoneNumber;
    private Address address = new Address();
    private Integer version;
    private Set<Role> roles = new HashSet<Role>();
    private Set<User> children = new HashSet<User>(0);
    private boolean enabled;
    private boolean accountExpired;
    private boolean accountLocked;
    private boolean credentialsExpired;

    /**
     * Default constructor - creates a new instance with no values set.
     */
    public User() {}

    /**
     * Create a new instance and set the username.
     * @param username login name for user.
     */
    public User(final String username) {
        this.username = username;
    }

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    @Column(nullable=false,length=50,unique=true)
    public String getUsername() {
        return username;
    }

    @Column(nullable=false)
    public String getPassword() {
        return password;
    }

    @Transient
    public String getConfirmPassword() {
        return confirmPassword;
    }

    @Column(name="company_name",nullable=false,length=50)
    public String getCompanyName() {
        return companyName;
    }

    @Column(nullable=false,unique=true)
    public String getEmail() {
        return email;
    }

    @Column(name="phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }    

    @Embedded
    public Address getAddress() {
        return address;
    }

    @ManyToMany(fetch = FetchType.EAGER) 
    @JoinTable(
            name="user_role",
            joinColumns = { @JoinColumn( name="user_id") },
            inverseJoinColumns = @JoinColumn( name="role_id")
    )    
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Convert user roles to LabelValue objects for convenience.
     * @return a list of LabelValue objects with role information
     */
    @Transient
    public List<LabelValue> getRoleList() {
        List<LabelValue> userRoles = new ArrayList<LabelValue>();

        if (this.roles != null) {
            for (Role role : roles) {
                // convert the user's roles to LabelValue Objects
                userRoles.add(new LabelValue(role.getName().substring(5), role.getName()));
            }
        }

        return userRoles;
    }
    
    /**
     * Convert user roles to String[] for convenience.
     * @return an array of String with role information
     */
    @Transient
    public String getCurrentRole() {
    	String currentRole = "";
    	if (this.roles != null) {
    		Iterator<Role> it = roles.iterator();
    		currentRole = it.next().getName().substring(5);
    		while (it.hasNext()) {
    			Role role = it.next();
    			currentRole += "/" + role.getName().substring(5);
    		}
    	}
    	return currentRole;
    }

    /**
     * Adds a role for the user
     * @param role the fully instantiated role
     */
    public void addRole(Role role) {
        getRoles().add(role);
    }
    
    /**
     * @see org.springframework.security.userdetails.UserDetails#getAuthorities()
     * @return GrantedAuthority[] an array of roles.
     */
    @Transient
    public GrantedAuthority[] getAuthorities() {
        return roles.toArray(new GrantedAuthority[0]);
    }

    @Version
    public Integer getVersion() {
        return version;
    }
    
    @Column(name="account_enabled")
    public boolean isEnabled() {
        return enabled;
    }
    
    @Column(name="account_expired",nullable=false)
    public boolean isAccountExpired() {
        return accountExpired;
    }
    
    /**
     * @see org.springframework.security.userdetails.UserDetails#isAccountNonExpired()
     */
    @Transient
    public boolean isAccountNonExpired() {
        return !isAccountExpired();
    }

    @Column(name="account_locked",nullable=false)
    public boolean isAccountLocked() {
        return accountLocked;
    }
    
    /**
     * @see org.springframework.security.userdetails.UserDetails#isAccountNonLocked()
     */
    @Transient
    public boolean isAccountNonLocked() {
        return !isAccountLocked();
    }

    @Column(name="credentials_expired",nullable=false)
    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }
    
    /**
     * @see org.springframework.security.userdetails.UserDetails#isCredentialsNonExpired()
     */
    @Transient
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }
    
    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public void setCredentialsExpired(boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }

        final User user = (User) o;

        return !(username != null ? !username.equals(user.getUsername()) : user.getUsername() != null);

    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return (username != null ? username.hashCode() : 0);
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
                .append("username", this.username)
                .append("enabled", this.enabled)
                .append("accountExpired", this.accountExpired)
                .append("credentialsExpired", this.credentialsExpired)
                .append("accountLocked", this.accountLocked);

        GrantedAuthority[] auths = this.getAuthorities();
        if (auths != null) {
            sb.append("Granted Authorities: ");

            for (int i = 0; i < auths.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(auths[i].toString());
            }
        } else {
            sb.append("No Granted Authorities");
        }
        return sb.toString();
    }
    
    //=============================================
    public void resetPassword() {
    	int size = 6;		
		String chars = "abc";
		List<Character> charList = new ArrayList<Character>();
		for (int i=0; i<chars.length(); i++) {
			charList.add(chars.charAt(i));			
		}
		java.util.Collections.shuffle(charList);
		Random generator = new Random();
		String newPassword = "";
		for (int i=0; i<size; i++) {
			int index = generator.nextInt(charList.size());
			newPassword += charList.get(index);
		}
		this.password = newPassword;
		this.confirmPassword = newPassword;
    }
    //================================================================
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) 
    @JoinTable(
            name="contractor_laboratory",
            joinColumns = { @JoinColumn( name="contractor_id") },
            inverseJoinColumns = @JoinColumn( name="laboratory_id")
    )    
    public Set<User> getChildren() {
        return children;
    }
    public void setChildren(Set<User> children) {
        this.children = children;
    }
    @Transient
    public List<LabelValue> getParentList() {
        List<LabelValue> userParent = new ArrayList<LabelValue>();

        if (this.children != null) {
            for (User user : children) {
                // convert the user's roles to LabelValue Objects
                userParent.add(new LabelValue(user.getUsername(), user.getUsername()));
            }
        }

        return userParent;
    }
    @Transient
    public String getCurrentParent() {
    	String currentChildren = "";
    	if (this.children != null) {
    		Iterator<User> it = children.iterator();
    		currentChildren = it.next().getUsername();
    		while (it.hasNext()) {
    			User user = it.next();
    			currentChildren += "/" + user.getUsername();
    		}
    	}
    	return currentChildren;
    }
    public void addChildren(User user) {
        getChildren().add(user);
    }
    
}
