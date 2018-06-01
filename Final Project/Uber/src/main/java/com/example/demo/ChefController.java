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
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@RequestMapping(value = "/chef/{id}/home", method = RequestMethod.GET)
	public String showPage(Model model, @PathVariable("id") String id) {
		UserDTO user = userService.getUserById(Long.valueOf(id));
		model.addAttribute("user", user);
		model.addAttribute("list", chefProductService.getProductsByChef(user));
		List<OrderDTO> list = orderService.getOrdersByChef(user).stream().filter(e -> e.getConfirmed().equals("NO")).collect(Collectors.toList());
		model.addAttribute("unconfirmedOrders", list);
		List<OrderDTO> list1 = orderService.getOrdersByChef(user).stream().filter(e -> e.getConfirmed().equals("YES")).collect(Collectors.toList());
		model.addAttribute("confirmedOrders", list1);
		return "chef/home";
	}
	
	@RequestMapping(value = "/chef/{id}/personal", method = RequestMethod.GET)
	public String showPersonalPage(Model model, @PathVariable("id") String id) {
		model.addAttribute("user", new UserDTO());
		model.addAttribute("id", id);
		return "chef/personal";
	}

	
	@RequestMapping(value = "/chef/{id}/personal", method = RequestMethod.POST)
	public ModelAndView updateUser(@Valid UserDTO user, BindingResult bindingResult, @PathVariable("id") String id) {
		ModelAndView modelAndView = new ModelAndView();
		UserDTO currentUser = userService.getUserById(Long.valueOf(id));
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
	
	@RequestMapping(value = "/chef/{id}/order_information", method = RequestMethod.GET)
	public String getProductInformationGet(Model model,  @RequestParam(value = "action", required = true) String action, @PathVariable("id") String id) {
		OrderDTO order = orderService.getOrderById(Long.valueOf(action.substring(8, action.length())));
		List<OrderItemDTO> orderItem = orderItemService.getOrderItemsByOrder(order);
		List<ProductDTO> list = new ArrayList<ProductDTO>();
		for(OrderItemDTO ord : orderItem) {
			ProductDTO prod = productService.getProductById(ord.getProduct().getId());
			list.add(prod);
		}
		model.addAttribute("orderItem", orderItem);
		model.addAttribute("products", list);
		model.addAttribute("id", id);
		return "chef/order_information";
	}
	
	@RequestMapping(value = "/chef/{id}/order_action", method = RequestMethod.POST)
	public String choseInformationAction(Model model, @RequestParam(value = "action", required = true) String action, @PathVariable("id") String id) {
		if(action.substring(0, 8).equals("ConfirmO")) {
			Long orderId = Long.valueOf(action.substring(8, action.length()));
			OrderDTO order = orderService.getOrderById(orderId);
			order.setConfirmed("YES");
			
			orderService.updateOrder(orderId, order);
		}
		if(action.substring(0, 8).equals("DeleteUn") || action.substring(0, 8).equals("DeleteCo")) {
			Long orderId = Long.valueOf(action.substring(8, action.length()));
			orderService.deleteOrder(orderId);
		}
		if(action.substring(0, 8).equals("Finalize")) {
			Long orderId = Long.valueOf(action.substring(8, action.length()));
			OrderDTO order = orderService.getOrderById(orderId);
			order.setFinalizeOrder("YES");
			
			orderService.updateOrder(orderId, order);
		}
		UserDTO currentUser = userService.getUserById(Long.valueOf(id));
		model.addAttribute("user", currentUser);
		model.addAttribute("list", chefProductService.getProductsByChef(currentUser));
		List<OrderDTO> list = orderService.getOrdersByChef(currentUser).stream().filter(e -> e.getConfirmed().equals("NO")).collect(Collectors.toList());
		model.addAttribute("unconfirmedOrders", list);
		List<OrderDTO> list1 = orderService.getOrdersByChef(currentUser).stream().filter(e -> e.getConfirmed().equals("YES")).collect(Collectors.toList());
		model.addAttribute("confirmedOrders", list1);
		return "chef/home";
	}
	
	
	@RequestMapping(value = "/chef/{id}/products", method = RequestMethod.GET)
	public String viewProducts(Model model, @PathVariable("id") String id) {
		List<ProductDTO> products = productService.getAllProducts();
		model.addAttribute("list", products);
		model.addAttribute("id", id);
		return "chef/products";
	}
	
	@RequestMapping(value = "/chef/{id}/products", method = RequestMethod.POST)
	public String addProducts(Model model, @RequestParam(value = "action", required = true) String action, @PathVariable("id") String id) {
		if(action.substring(0, 3).equals("Add")) {
			UserDTO user = userService.getUserById(Long.valueOf(id));
			Long prodId = Long.valueOf(action.substring(3,  action.length()));
			ProductDTO product = productService.getProductById(prodId);
			ChefProductDTO ch = new ChefProductDTO(user, product, 0);
			chefProductService.createChefProduct(ch);
			
			List<ProductDTO> products = productService.getAllProducts();
			model.addAttribute("list", products);
			
			return "chef/products";
		}
		List<ProductDTO> products = productService.getAllProducts();
		model.addAttribute("list", products);
		return "chef/products";
	}
	
	@RequestMapping(value = "/chef/{id}/quantity", method = RequestMethod.GET)
	public String updateQuantityOfProduct(Model model, @RequestParam(value = "action", required = true) String action, @PathVariable("id") String id) {
		Long prodId = Long.valueOf(action.substring(6, action.length()));
		ChefProductDTO ch = chefProductService.getChefProductById(prodId);
		currentCh = new ChefProductDTO();
		currentCh.setId(prodId);
		model.addAttribute("ch", ch);
		model.addAttribute("id", id);
		return "chef/quantity";
	}
	
	@RequestMapping(value = "chef/{id}/quantity", method = RequestMethod.POST)
	public ModelAndView updateProductQuantity(@Valid @ModelAttribute("ch") ChefProductDTO ch, BindingResult bindingResult, @PathVariable("id") String id) {
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
					modelAndView.setViewName("chef/quantity");
			}
			
			return modelAndView;
	}
	
	@RequestMapping(value = "/chef/{id}/product_actions", method = RequestMethod.POST)
	public String updateQuantity(Model model, @RequestParam(value = "action", required = true) String action,@PathVariable("id") String id) {
		if(action.substring(0, 6).equals("Delete")) {
			Long chId = Long.valueOf(action.substring(6, action.length()));
			chefProductService.deleteChefProduct(chId);
		}
		UserDTO currentUser = userService.getUserById(Long.valueOf(id));
		model.addAttribute("user", currentUser);
		model.addAttribute("list", chefProductService.getProductsByChef(currentUser));
		List<OrderDTO> list = orderService.getOrdersByChef(currentUser).stream().filter(e -> e.getConfirmed().equals("NO")).collect(Collectors.toList());
		model.addAttribute("unconfirmedOrders", list);
		List<OrderDTO> list1 = orderService.getOrdersByChef(currentUser).stream().filter(e -> e.getConfirmed().equals("YES")).collect(Collectors.toList());
		model.addAttribute("confirmedOrders", list1);
		return "chef/home";
	}
	
	
}
