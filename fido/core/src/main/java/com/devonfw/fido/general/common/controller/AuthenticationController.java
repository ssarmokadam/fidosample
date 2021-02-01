// package com.devonfw.fido.general.common.controller;
//
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.servlet.ModelAndView;
//
// import com.devonfw.fido.usermanagement.dataaccess.api.UserEntity;
//
/// **
// * TODO snesarmo This type ...
// *
// */
// @Controller
// public class AuthenticationController {
//
// @RequestMapping(value = "/register", method = RequestMethod.GET)
// public ModelAndView register() {
//
// ModelAndView modelAndView = new ModelAndView();
// modelAndView.setViewName("register.html");
// return modelAndView;
// }
//
// // @RequestMapping(value = "/login", method = RequestMethod.GET)
// // public ModelAndView login() {
// //
// // ModelAndView modelAndView = new ModelAndView();
// // modelAndView.setViewName("login.html");
// // return modelAndView;
// // }
//
// @RequestMapping(value = "/home", method = RequestMethod.GET)
// public ModelAndView home() {
//
// ModelAndView modelAndView = new ModelAndView();
// modelAndView.setViewName("home.html");
// return modelAndView;
// }
//
// @RequestMapping(value = "/register", method = RequestMethod.POST)
// public ModelAndView register(UserEntity user) {
//
// ModelAndView modelAndView = new ModelAndView();
// System.out.println("Inside register method" + user.getFirstName());
// return modelAndView;
// }
// }
