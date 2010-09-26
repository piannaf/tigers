package com.tiffany.webapp.controller;

import java.util.Map;

import com.tiffany.Constants;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

public class UserControllerTest extends BaseControllerTestCase {

    public void testHandleRequest() throws Exception {
        UserController c = (UserController) applicationContext.getBean("userController");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addUserRole(Constants.ADMIN_ROLE);
        ModelAndView mav = c.handleRequest(request, new MockHttpServletResponse());
        Map m = mav.getModel();
        assertNotNull(m.get(Constants.USER_LIST));
        assertEquals("admin/userList", mav.getViewName());
    }
}
