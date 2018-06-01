package com.example.demo;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.service.ChefProductService;
import com.example.demo.service.OrderDTO;
import com.example.demo.service.OrderService;
import com.example.demo.service.ParticularityDTO;
import com.example.demo.service.ParticularityService;
import com.example.demo.service.ProductDTO;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserDTO;
import com.example.demo.service.UserRoleDTO;
import com.example.demo.service.UserRoleService;
import com.example.demo.service.UserService;

@Controller
public class AdminController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private ParticularityService particularityService;
	@Autowired
	private ProductService productService;
	@Autowired 
	private UserRoleService userRoleService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ChefProductService chefProductService;
//	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
//	public ModelAndView login(){
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("/login");
//		return modelAndView;
//	}
	
	@RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
	public String goTologin(Model model) {
		model.addAttribute("user", new UserDTO());
		return "/login";
	}
	
	@RequestMapping(value = {"/", "/login"}, method = RequestMethod.POST)
	public String login(Model model, @Valid UserDTO user) {
		UserDTO currentUser = userService.getUserByEmail(user.getEmail());
		System.out.println("\n\n\n\n\n\n\n\n\nUser" + user.getId() + "\nEmail: " + user.getEmail() + "\n\n\n\n\n\n");
		UserRoleDTO role = userRoleService.getUserRoleByUser(currentUser);

		if(role.getRole().equals("ADMIN") && passwordEncoder.passwordEncoder().matches(user.getPassword(), currentUser.getPassword())) {
			model.addAttribute("list", userService.getAllUsers());
			model.addAttribute("particularities", particularityService.getAllParticularities());
			model.addAttribute("products", productService.getAllProducts());
			return "redirect:admin/" + currentUser.getId().toString() + "/home";
		}
		if(role.getRole().equals("CUSTOMER") && passwordEncoder.passwordEncoder().matches(user.getPassword(), currentUser.getPassword())) {
			model.addAttribute("user", currentUser);
			model.addAttribute("orders", orderService.getOrdersByCustomer(user));
			return "redirect:customer/" + currentUser.getId().toString() + "/home";
		}
		if(role.getRole().equals("CHEF") && passwordEncoder.passwordEncoder().matches(user.getPassword(), currentUser.getPassword())) {
			model.addAttribute("user", user);
			model.addAttribute("list", chefProductService.getProductsByChef(currentUser));
			List<OrderDTO> list = orderService.getOrdersByChef(currentUser).stream().filter(e -> e.getConfirmed().equals("NO")).collect(Collectors.toList());
			model.addAttribute("unconfirmedOrders", list);
			List<OrderDTO> list1 = orderService.getOrdersByChef(currentUser).stream().filter(e -> e.getConfirmed().equals("YES")).collect(Collectors.toList());
			model.addAttribute("confirmedOrders", list1);
			return "redirect:chef/" + currentUser.getId().toString() + "/home";
		}
		model.addAttribute("user", new UserDTO());
		return "/login";
	}
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		//UserDTO user = new UserDTO();
		UserRoleDTO user = new UserRoleDTO();
		modelAndView.addObject("userRole", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid UserRoleDTO userRole, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		
		//System.out.println("\n\n\n\n\n\n\n\n\n" + user.getUserDTO());
		UserDTO userExists = userService.getUserByEmail(userRole.getUserDTO().getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			UserDTO u = userRole.getUserDTO();
			userService.createUser(u);
			UserDTO created = userService.getUserByEmail(u.getEmail());
			
			UserRoleDTO newUserRole = new UserRoleDTO();
			newUserRole.setUserDTO(created);
			newUserRole.setRole(userRole.getRole());
			userRoleService.createUserRole(newUserRole);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("userRole", new UserRoleDTO());
			modelAndView.setViewName("registration");
			
		}
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/admin/{id}/home", method = RequestMethod.GET)
	public String showHome(Model model,  @PathVariable("id") String id) {
		model.addAttribute("list", userService.getAllUsers());
		model.addAttribute("particularities", particularityService.getAllParticularities());
		model.addAttribute("products", productService.getAllProducts());
		return "admin/home";
	}
	
	@RequestMapping(value = "/admin/{id}/delete_user", method=RequestMethod.GET)
	public String deleteUserGet(Model model, @PathVariable("id") String id) {
		model.addAttribute("user", new UserDTO());
		model.addAttribute("id", id);
		return "admin/delete_user";
	}
	
	@RequestMapping(value = "/admin/{id}/update_user", method=RequestMethod.GET)
	public String updateUserGet(Model model, @PathVariable("id") String id) {
		model.addAttribute("user", new UserDTO());
		model.addAttribute("id", id);
		return "admin/update_user";
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
	
	@RequestMapping(value = "/admin/{id}/update_particularity", method = RequestMethod.GET)
	public String updateParticularityGet(Model model, @RequestParam(value = "action", required = true) String action,  @PathVariable("id") String id) {
		Long partId = Long.valueOf(action.substring(10, action.length()));
		ParticularityDTO part = new ParticularityDTO();
		part.setId(partId);
		model.addAttribute("particularity", part);
		model.addAttribute("id", id);
		return "admin/update_particularity";
	}
	
	@RequestMapping(value = "/admin/{id}/update_product", method = RequestMethod.GET)
	public String updateProductGet(Model model, @RequestParam(value = "action", required = true) String action,  @PathVariable("id") String id) {
		Long prodId = Long.valueOf(action.substring(10, action.length()));
		ProductDTO product = new ProductDTO();
		product.setId(prodId);
		model.addAttribute("product",product);
		model.addAttribute("id", id);
		return "admin/update_product";
	}
	
	@RequestMapping(value = "/admin/{id}/admin_actions", method = RequestMethod.POST)
	public String showPage(Model model, @RequestParam(value = "action", required = true) String action) {
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
	
	@RequestMapping(value = "/admin/{id}/create_product", method = RequestMethod.GET)
	public String createProductGET(Model model,  @PathVariable("id") String id) {
		model.addAttribute("p", new ProductDTO());
		model.addAttribute("id", id);
		return "admin/create_product";
	}
	
	@RequestMapping(value = "/admin/create_product", method = RequestMethod.POST)
	public ModelAndView createProductPOST(@Valid ProductDTO p, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		
		ParticularityDTO part = particularityService.getParticularityById(p.getParticularityDTO().getId());
		System.out.println("\n\n\n\n\n\n\n\n\n" + part.toString() + "\n\n\n\n\n\n\n\n\n\n");
		ProductDTO product = new ProductDTO();
		List<ProductDTO> list= productService.getAllProducts();
		int found = 0;
		for(ProductDTO prod : list) {
			if(prod.getName().equals(p.getName())) {
				found = 1; break;
			}
		}
		System.out.println("\n\n\n\n\n\n\n\n\n" + found + "\n\n\n\n\n\n\n\n\n\n");
		if(found == 0) {
			product.setName(p.getName());
			product.setDescription(p.getDescription());
			product.setOrigin(p.getOrigin());
			product.setParticularityDTO(part);
			product.setPrice(p.getPrice());
			System.out.println("\n\n\n\n\n\n\n\n\n" + product.toString() + "\n\n\n\n\n\n\n\n\n\n");
		}
		
		if (product == null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("admin/create_product");
		} else {
			
			productService.createProduct(product);
			modelAndView.addObject("successMessage", "Product has been registered successfully");
			modelAndView.addObject("p", new ProductDTO());
			modelAndView.setViewName("admin/create_product");
			
		}
		return modelAndView;
	}
}
