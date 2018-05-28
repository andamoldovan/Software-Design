package com.example.demo;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.service.ParticularityDTO;
import com.example.demo.service.ParticularityService;
import com.example.demo.service.ProductDTO;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserDTO;
import com.example.demo.service.UserService;

@Controller
public class AdminController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private ParticularityService particularityService;
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/login");
		return modelAndView;
	}
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		UserDTO user = new UserDTO();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid UserDTO user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		UserDTO userExists = userService.getUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.createUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new UserDTO());
			modelAndView.setViewName("registration");
			
		}
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/admin/home", method = RequestMethod.GET)
	public String showPage(Model model) {
		model.addAttribute("list", userService.getAllUsers());
		model.addAttribute("particularities", particularityService.getAllParticularities());
		model.addAttribute("products", productService.getAllProducts());
		return "admin/home";
	}
	
	
	@RequestMapping(value = "/admin/user_actions", method=RequestMethod.GET)
	public String userActions(Model model, @RequestParam(value = "action", required = true) String action) {
			if(action.equals("delete")) {
				model.addAttribute("user", new UserDTO());
				return "admin/delete_user";
			}
			if(action.equals("update")) {
				model.addAttribute("user", new UserDTO());
				return "admin/update_user";
			}
			
			String act = action.substring(0, 9);
			if(act.equals("UpdatePart")) {
				model.addAttribute(new ParticularityDTO());
				return "admin/update_particularity";
			}
			
			model.addAttribute("list", userService.getAllUsers());
			model.addAttribute("particularities", particularityService.getAllParticularities());
			model.addAttribute("products", productService.getAllProducts());
			return "admin/home";
	}
	
	@RequestMapping(value = "/admin/delete_user", method = RequestMethod.POST)
	public ModelAndView deleteUser(@Valid @ModelAttribute("user") UserDTO user,  BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		
			UserDTO userExists = userService.getUserByEmail(user.getEmail());
			if(userExists != null) {
				userService.deleteUser(userExists.getId());
				modelAndView.addObject("successMessage", "User has been deleted successfully");
				modelAndView.addObject("user", new UserDTO());
				modelAndView.setViewName("admin/delete_user");
			}else {
					bindingResult
					.rejectValue("email", "error.user",
							"User with provided credentials does not exist");
					modelAndView.setViewName("delete_user");
			}
			
			return modelAndView;
	}
	
	@RequestMapping(value = "/admin/update_user", method = RequestMethod.POST)
	public ModelAndView updateUser(@Valid @ModelAttribute("user") UserDTO user,  BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		
			UserDTO userExists = userService.getUserByEmail(user.getEmail());
			if(userExists != null) {
				user.setPassword(userExists.getPassword());
				userService.updateUser(userExists.getId(), user);
				modelAndView.addObject("successMessage", "User has been updated successfully");
				modelAndView.addObject("user", new UserDTO());
				modelAndView.setViewName("admin/update_user");
			}else {
					bindingResult
					.rejectValue("email", "error.user",
							"User with provided credentials does not exist");
					modelAndView.setViewName("update_user");
			}
			
			return modelAndView;
	}
	
	@RequestMapping(value = "/admin/admin_actions", method = RequestMethod.POST)
	public String showPage(Model model, @RequestParam(value = "action", required = true) String action) {
		System.out.println("\n\n\n\n\n\n\n" + action + "\n" + action.substring(0, 10) +"\n\n\n\n\n\n\n");
		if(action.substring(0, 10).equals("UpdatePart")) {
			Long id = Long.valueOf(action.substring(10, action.length()));
			ParticularityDTO part = new ParticularityDTO();
			part.setId(id);
			model.addAttribute("particularity",part);
			return "admin/update_particularity";
		}
		if(action.substring(0, 10).equals("UpdateProd")) {
			Long id = Long.valueOf(action.substring(10, action.length()));
			ProductDTO product = new ProductDTO();
			product.setId(id);
			model.addAttribute("product",product);
			return "admin/update_product";
		}
		if(action.substring(0, 10).equals("DeletePart")) {
			Long id = Long.valueOf(action.substring(10, action.length()));
			particularityService.deleteParticularity(id);
			model.addAttribute("list", userService.getAllUsers());
			model.addAttribute("particularities", particularityService.getAllParticularities());
			model.addAttribute("products", productService.getAllProducts());
			return "admin/home";
		}
		if(action.substring(0, 10).equals("DeleteProd")) {
			Long id = Long.valueOf(action.substring(10, action.length()));
			productService.deleteProduct(id);
			model.addAttribute("list", userService.getAllUsers());
			model.addAttribute("particularities", particularityService.getAllParticularities());
			model.addAttribute("products", productService.getAllProducts());
			return "admin/home";
		}
		model.addAttribute("list", userService.getAllUsers());
		model.addAttribute("particularities", particularityService.getAllParticularities());
		model.addAttribute("products", productService.getAllProducts());
		return "admin/home";
	}
	
	@RequestMapping(value = "/admin/update_particularity", method = RequestMethod.POST)
	public ModelAndView updateParticularity(@Valid @ModelAttribute("particularity") ParticularityDTO particularity, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		
			ParticularityDTO part = particularityService.getParticularityById(particularity.getId());
			if(part != null) {
				particularityService.upadateParticularity(part.getId(), particularity);
				modelAndView.addObject("successMessage", "Particularity has been updated successfully");
				modelAndView.addObject("particularity", new ParticularityDTO());
				modelAndView.setViewName("admin/update_particularity");
			}else {
					bindingResult
					.rejectValue("email", "error.user",
							"Particularity with provided credentials does not exist");
					modelAndView.setViewName("update_particularity");
			}
			return modelAndView;
	}
	
	@RequestMapping(value = "/admin/update_product", method = RequestMethod.POST)
	public ModelAndView updateParticularity(@Valid @ModelAttribute("product") ProductDTO product, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		
			ProductDTO prod = productService.getProductById(product.getId());
			if(prod != null) {
				productService.updateProduct(prod.getId(), product);
				modelAndView.addObject("successMessage", "Product has been updated successfully");
				modelAndView.addObject("product", new ProductDTO());
				modelAndView.setViewName("admin/update_product");
			}else {
					bindingResult
					.rejectValue("email", "error.user",
							"Product with provided credentials does not exist");
					modelAndView.setViewName("update_product");
			}
			
			return modelAndView;
	}
}
