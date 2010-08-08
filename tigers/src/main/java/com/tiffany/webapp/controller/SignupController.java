package com.tiffany.webapp.controller;

import org.springframework.security.AccessDeniedException;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import com.tiffany.Constants;
import com.tiffany.model.User;
import com.tiffany.service.RoleManager;
import com.tiffany.service.UserExistsException;
import com.tiffany.webapp.util.RequestUtil;
import org.springframework.mail.MailException;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

/**
 * Controller to signup new users.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class SignupController extends BaseFormController {
    private RoleManager roleManager;

    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    public SignupController() {
        setCommandName("user");
        setCommandClass(User.class);
    }

    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
                                 Object command, BindException errors)
            throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("entering 'onSubmit' method at SignupController");
        }

        User user = (User) command;
        Locale locale = request.getLocale();
        
        user.setEnabled(true);
        log.debug("@@"+user.getAddress().getCountry()+"@@"+user.getAddress().getAddress()+"@@");
        // Set the default user role on this new user
        user.addRole(roleManager.getRole(Constants.USER_ROLE));

        try {
            this.getUserManager().saveUser(user);
        } catch (AccessDeniedException ade) {
            // thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
            log.warn(ade.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null; 
        } catch (UserExistsException e) {
            errors.rejectValue("username", "errors.existing.user",
                    new Object[]{user.getUsername(), user.getEmail()}, "duplicate user");

            // redisplay the unencrypted passwords
            user.setPassword(user.getConfirmPassword());
            return showForm(request, response, errors);
        }

        saveMessage(request, getText("user.registered", user.getUsername(), locale));
        request.getSession().setAttribute(Constants.REGISTERED, Boolean.TRUE);

        // log user in automatically
//        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
//                user.getUsername(), user.getConfirmPassword(), user.getAuthorities());
//        auth.setDetails(user);
//        SecurityContextHolder.getContext().setAuthentication(auth);

        // Send user an e-mail
        if (log.isDebugEnabled()) {
            log.debug("Sending user '" + user.getUsername() + "' an account information e-mail");
        }

        // Send an account information e-mail
        message.setSubject(getText("signup.email.subject", locale));
        
        Map<String, Serializable> model = new HashMap<String, Serializable>();
        model.put("message", getText("signup.email.message", locale));
        
        try {
            //sendUserMessage(user, getText("signup.email.message", locale), RequestUtil.getAppURL(request));
        	sendUserMessage(user, model);
        } catch (MailException me) {
            saveError(request, me.getMostSpecificCause().getMessage());
        }
        
        return new ModelAndView(getSuccessView());
    }
}
