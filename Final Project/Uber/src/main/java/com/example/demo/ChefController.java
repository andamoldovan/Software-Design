package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

import com.example.demo.service.ChefProductDTO;
import com.example.demo.service.ChefProductService;
import com.example.demo.service.OrderDTO;
import com.example.demo.service.OrderItemDTO;
import com.example.demo.service.OrderItemService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductDTO;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserDTO;
import com.example.demo.service.UserService;

@Controller
public class ChefController {
	
	@Autowired
	private ChefProductService chefProductService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderItemService orderItemService;
	@Autowired
	private ProductService productService;
	
	private UserDTO currentUser;
	private ChefProductDTO currentCh;
	
	@RequestMapping(value = "/chef/home", method = RequestMethod.GET)
	public String showPage(Model model) {
		UserDTO user = userService.getUserByEmail("b");
		currentUser = new UserDTO();
		currentUser = user;
		model.addAttribute("user", user);
		model.addAttribute("list", chefProductService.getProductsByChef(currentUser));
		List<OrderDTO> list = orderService.getOrdersByChef(currentUser).stream().filter(e -> e.getConfirmed().equals("NO")).collect(Collectors.toList());
		model.addAttribute("unconfirmedOrders", list);
		List<OrderDTO> list1 = orderService.getOrdersByChef(currentUser).stream().filter(e -> e.getConfirmed().equals("YES")).collect(Collectors.toList());
		model.addAttribute("confirmedOrders", list1);
		return "chef/home";
	}
	
	@RequestMapping(value = "/chef/personal", method = RequestMethod.GET)
	public String showPersonalPage(Model model) {
		model.addAttribute("user", new UserDTO());
		return "chef/personal";
	}
	
	@RequestMapping(value = "/chef/personal", method = RequestMethod.POST)
	public ModelAndView updateUser(@Valid UserDTO user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		UserDTO userExists = userService.getUserByEmail(currentUser.getEmail());
		if (userExists == null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("");
		} else {
			currentUser.setName(user.getName());
			currentUser.setAddress(user.getAddress());
			currentUser.setPhone(user.getPhone());
			currentUser.setIban(user.getIban());
			currentUser.setPassword(user.getPassword());
			userService.updateUser(currentUser.getId(), currentUser);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new UserDTO());
			modelAndView.setViewName("chef/personal");
			
		}
		return modelAndView;
	}
	

	@RequestMapping(value = "/chef/order_action", method = RequestMethod.POST)
	public String choseInformationAction(Model model, @RequestParam(value = "action", required = true) String action) {
		if(action.substring(0, 8).equals("InfoUnco") || action.substring(0, 8).equals("InfoConf")) {
			OrderDTO order = orderService.getOrderById(Long.valueOf(action.substring(8, action.length())));
			List<OrderItemDTO> orderItem = orderItemService.getOrderItemsByOrder(order);
			List<ProductDTO> list = new ArrayList<ProductDTO>();
			for(OrderItemDTO ord : orderItem) {
				ProductDTO prod = productService.getProductById(ord.getProduct().getId());
				list.add(prod);
			}
			model.addAttribute("orderItem", orderItem);
			model.addAttribute("products", list);
			return "chef/order_information";
		}
		if(action.substring(0, 8).equals("ConfirmO")) {
			Long id = Long.valueOf(action.substring(8, action.length()));
			OrderDTO order = orderService.getOrderById(id);
			order.setConfirmed("YES");
			
			orderService.updateOrder(id, order);
		}
		if(action.substring(0, 8).equals("DeleteUn") || action.substring(0, 8).equals("DeleteCo")) {
			Long id = Long.valueOf(action.substring(8, action.length()));
			orderService.deleteOrder(id);
		}
		if(action.substring(0, 8).equals("Finalize")) {
			Long id = Long.valueOf(action.substring(8, action.length()));
			OrderDTO order = orderService.getOrderById(id);
			order.setFinalizeOrder("YES");
			
			orderService.updateOrder(id, order);
		}
		model.addAttribute("user", currentUser);
		model.addAttribute("list", chefProductService.getProductsByChef(currentUser));
		List<OrderDTO> list = orderService.getOrdersByChef(currentUser).stream().filter(e -> e.getConfirmed().equals("NO")).collect(Collectors.toList());
		model.addAttribute("unconfirmedOrders", list);
		List<OrderDTO> list1 = orderService.getOrdersByChef(currentUser).stream().filter(e -> e.getConfirmed().equals("YES")).collect(Collectors.toList());
		model.addAttribute("confirmedOrders", list1);
		return "chef/home";
	}
	
	@RequestMapping(value = "/chef/products", method = RequestMethod.GET)
	public String viewProducts(Model model) {
		List<ProductDTO> products = productService.getAllProducts();
		model.addAttribute("list", products);
		return "chef/products";
	}
	
	@RequestMapping(value = "/chef/products", method = RequestMethod.POST)
	public String addProducts(Model model, @RequestParam(value = "action", required = true) String action) {
		if(action.substring(0, 3).equals("Add")) {
			Long id = Long.valueOf(action.substring(3,  action.length()));
			ProductDTO product = productService.getProductById(id);
			ChefProductDTO ch = new ChefProductDTO(currentUser, product, 0);
			chefProductService.createChefProduct(ch);
			
			List<ProductDTO> products = productService.getAllProducts();
			model.addAttribute("list", products);
			return "chef/products";
		}
		List<ProductDTO> products = productService.getAllProducts();
		model.addAttribute("list", products);
		return "chef/products";
	}
	
	@RequestMapping(value = "/chef/product_actions", method = RequestMethod.POST)
	public String updateQuantity(Model model, @RequestParam(value = "action", required = true) String action) {
		if(action.substring(0, 6).equals("Inform")) {
			Long id = Long.valueOf(action.substring(6, action.length()));
			ChefProductDTO ch = chefProductService.getChefProductById(id);
			currentCh = new ChefProductDTO();
			currentCh.setId(id);
			model.addAttribute("ch", ch);
			return "chef/quantity";
		}
		if(action.substring(0, 6).equals("Delete")) {
			Long id = Long.valueOf(action.substring(6, action.length()));
			chefProductService.deleteChefProduct(id);
		}
		model.addAttribute("user", currentUser);
		model.addAttribute("list", chefProductService.getProductsByChef(currentUser));
		List<OrderDTO> list = orderService.getOrdersByChef(currentUser).stream().filter(e -> e.getConfirmed().equals("NO")).collect(Collectors.toList());
		model.addAttribute("unconfirmedOrders", list);
		List<OrderDTO> list1 = orderService.getOrdersByChef(currentUser).stream().filter(e -> e.getConfirmed().equals("YES")).collect(Collectors.toList());
		model.addAttribute("confirmedOrders", list1);
		return "chef/home";
	}
	
	@RequestMapping(value = "chef/quantity", method = RequestMethod.POST)
	public ModelAndView updateProductQuantity(@Valid @ModelAttribute("ch") ChefProductDTO ch, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		
			ChefProductDTO prod = chefProductService.getChefProductById(currentCh.getId());
			if(prod != null) {
				System.out.println("\n\n\n\n\n\n" + prod.toString() + "\n\n\n\n\n\n\n\n");
				prod.setQuantity(ch.getQuantity());
				chefProductService.updateChefProduct(currentCh.getId(), prod);
				modelAndView.addObject("successMessage", "Product has been updated successfully");
				modelAndView.addObject("ch", new ChefProductDTO());
				modelAndView.setViewName("chef/quantity");
			}else {
					bindingResult
					.rejectValue("quantity", "error.ch",
							"Product with provided credentials does not exist");
					modelAndView.setViewName("quantity");
			}
			
			return modelAndView;
	}
}
