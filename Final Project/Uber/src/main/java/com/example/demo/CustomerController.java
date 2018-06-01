package com.example.demo;

import java.util.ArrayList;
import java.util.List;

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
public class CustomerController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ChefProductService chefProductService;
	@Autowired
	private OrderItemService orderItemService;
	
	private UserDTO currentUser;
	private List<ChefProductDTO> cart;
	private List<OrderItemDTO> orderItems;
	private OrderDTO currentOrder;
	private OrderItemDTO currentOrderItem;
	
	@RequestMapping(value = "/customer/{id}/home", method = RequestMethod.GET)
	public String showPage(Model model, @PathVariable("id") String id) {
		UserDTO user = userService.getUserById(Long.valueOf(id));
		model.addAttribute("user", user);
		model.addAttribute("orders", orderService.getOrdersByCustomer(user));
		model.addAttribute("id", id);
		return "customer/home";
	}

	@RequestMapping(value = "/customer/{id}/personal", method = RequestMethod.GET)
	public String getPersonal(Model model, @PathVariable("id") String id) {
		model.addAttribute("user", new UserDTO());
		model.addAttribute("id", id);
		return "customer/personal";
	}
	
	@RequestMapping(value = "/customer/{id}/orders", method = RequestMethod.GET)
	public String choseAction(Model model, @PathVariable("id") String id) {
			UserDTO user = userService.getUserById(Long.valueOf(id));
			OrderDTO currentOrder = new OrderDTO(user, user);
			orderService.createOrder(currentOrder);
			model.addAttribute("products", productService.getAllProducts());
//			OrderItemDTO orderItem = orderItemService.getOrderItemsByOrder(currentOrder).get(0);
//			List<ChefProductDTO> chefs = chefProductService.getChefsByProduct(orderItem.getProduct());
			return "customer/orders";
	}
	
	@RequestMapping(value = "/customer/{id}/personal", method = RequestMethod.POST)
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
			modelAndView.setViewName("registration");
		} else {
			currentUser.setName(user.getName());
			currentUser.setAddress(user.getAddress());
			currentUser.setPhone(user.getPhone());
			currentUser.setIban(user.getIban());
			currentUser.setPassword(user.getPassword());
			userService.updateUser(currentUser.getId(), currentUser);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new UserDTO());
			modelAndView.setViewName("customer/personal");
			
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/customer/customer_actions", method = RequestMethod.POST)
	public String customer_actions(Model model, @RequestParam(value = "action", required = true) String action) {
		if(action.subSequence(0, 11).equals("DeleteOrder")) {
			Long id = Long.valueOf(action.substring(11, action.length()));
			OrderDTO ord = orderService.getOrderById(id);
			List<OrderItemDTO> o = orderItemService.getOrderItemsByOrder(ord);
			if(!o.isEmpty())
				orderItemService.deleteOrderItem(o.get(0).getId());
			orderService.deleteOrder(id);
		}
		
		UserDTO user = userService.getUserByEmail("andamold@gmail.com");
		currentUser = new UserDTO();
		currentUser = user;
		model.addAttribute("user", user);
		model.addAttribute("orders", orderService.getOrdersByCustomer(user));
		return "customer/home";
	}
	
	@RequestMapping(value = "/customer/{id}/product_information", method = RequestMethod.GET)
	public String product_actions(Model model, @RequestParam(value = "action", required = true) String action, @PathVariable("id") String id) {
			List<UserDTO> chefs = userService.getAllUsers();
			ProductDTO product = productService.getProductById(Long.valueOf(action.substring(4, action.length())));
			List<ChefProductDTO> list = chefProductService.getChefsByProduct(product);
			model.addAttribute("chefProducts", list);
			model.addAttribute("id", id);
			return "customer/product_information";
	}
	
//	@RequestMapping(value = "/customer/add_actions", method = RequestMethod.POST)
//	public String add_actions(Model model, @RequestParam(value = "action", required = true) String action) {
//		if(action.substring(0, 7).equals("AddProd")) {
//			//add product to cart form any chef
//			OrderItemDTO ord = new OrderItemDTO();
//			ord.setProduct(productService.getProductById(Long.valueOf(action.substring(7, action.length()))));
//			ord.setOrder(getCurrentOrder("andamold@gmail.com"));
//			System.out.println("\n\n\n\n\n\n\n\n\n orderItem: " + ord.getOrder().getId() + " " + ord.getProduct().getId());
//			orderItemService.createOrderItem(ord);
//			
//			model.addAttribute("ch", getCurrentOrderItem(ord.getProduct(), ord.getOrder()));
//			return "customer/quantity";
//		}
//		if(action.substring(0, 7).equals("AddChef")) {
//			//add product to cart form a specific chef
//		}
//		UserDTO user = userService.getUserByEmail("andamold@gmail.com");
//		currentUser = new UserDTO();
//		currentUser = user;
//		model.addAttribute("user", user);
//		model.addAttribute("orders", orderService.getOrdersByCustomer(user));
//		return "customer/home";
//	}
	
	@RequestMapping(value = "/customer/{id}/quantity", method = RequestMethod.GET)
	public String choseQuantity(Model model, @PathVariable("id") String id, @RequestParam(value = "action", required = true) String action) {
		
		OrderItemDTO ord = new OrderItemDTO();
		UserDTO user = userService.getUserById(Long.valueOf(id));
		ord.setProduct(productService.getProductById(Long.valueOf(action.substring(7, action.length()))));
		ord.setOrder(getCurrentOrder(user.getEmail()));
		orderItemService.createOrderItem(ord);
		model.addAttribute("ch", getCurrentOrderItem(ord.getProduct(), ord.getOrder()));
		model.addAttribute("id", id);
		return "customer/quantity";
	}
	
	@RequestMapping(value = "customer/{id}/quantity", method = RequestMethod.POST)
	public ModelAndView updateProductQuantity(@Valid @ModelAttribute("ch") OrderItemDTO ch, BindingResult bindingResult, @PathVariable("id") String id) {
			ModelAndView modelAndView = new ModelAndView();
			ChefProductDTO prod = new ChefProductDTO();
			OrderItemDTO item = new OrderItemDTO();
			UserDTO user = userService.getUserById(Long.valueOf(id));
			OrderDTO currentOrder = getCurrentOrder(user.getEmail());
			OrderItemDTO currentOrderItem = getOrderItemByProductName(ch.getProduct().getName(), user.getEmail());
			UserDTO chef = new UserDTO();
			System.out.println("\n\n\n\n\n\n\n\n" + currentOrderItem.getId() + "\n\n\n\n\n\n\n\n\n");
			if(currentOrderItem.getId() != null) {
				List<ChefProductDTO> chefs = chefProductService.getChefsByProduct(getProductByName(ch.getProduct().getName()));
				for(ChefProductDTO c : chefs) {
					if(c.getQuantity() >= ch.getQuantity()) {
					
						currentOrder.setConfirmed("NO");
						currentOrder.setFinalizeOrder("NO");
						currentOrder.setChef(c.getChef());
						orderService.updateOrder(currentOrder.getId(), currentOrder);
			
						item.setOrder(currentOrder);
						item.setProduct(c.getProduct());
						item.setQuantity(ch.getQuantity());
						item.setFinalPrice();
						orderItemService.createOrderItem(item);
						
						
						break;
					}
				}
				
			}
			
			if(prod != null) {

				modelAndView.addObject("successMessage", "Product has been added to the cart successfully");
				modelAndView.addObject("ch", new OrderItemDTO());
				modelAndView.setViewName("customer/quantity");
			}else {
					bindingResult
					.rejectValue("quantity", "error.ch",
							"Product with provided credentials does not exist");
					modelAndView.setViewName("customer/" + id + "/quantity");
			}
			
			return modelAndView;
	}
	
//	@RequestMapping(value = "customer/{id}/orders", method = RequestMethod.GET)
//	public String finalizeOrder(Model model, @PathVariable("id") String id) {
//		
//		OrderDTO currentOrder = getCurrentOrder("andamold@gmail.com");
//		OrderItemDTO orderItem = orderItemService.getOrderItemsByOrder(currentOrder).get(0);
//		List<ChefProductDTO> chefs = chefProductService.getChefsByProduct(orderItem.getProduct());
//		for(ChefProductDTO c : chefs) {
//			
//		}
//		
//		
//		UserDTO currentUser = userService.getUserByEmail("andamold@gmail.com");
//		model.addAttribute("user", currentUser);
//		model.addAttribute("orders", orderService.getOrdersByCustomer(currentUser));
//		return "customer/home";
//	}
	
	@RequestMapping(value = "/customer/cart_action", method = RequestMethod.POST)
	public String goBackToTheCart(Model model, @RequestParam(value = "action", required = true) String action) {
		if(action.substring(0, 4).equals("cart")) {
			model.addAttribute("products", productService.getAllProducts());
			return "customer/orders";
		}
		UserDTO currentUser = userService.getUserByEmail("andamold@gmail.com");
		model.addAttribute("user", currentUser);
		model.addAttribute("orders", orderService.getOrdersByCustomer(currentUser));
		return "customer/home";
	}
	
	private OrderDTO getCurrentOrder(String email) {
		UserDTO user = userService.getUserByEmail(email); 
		List<OrderDTO> chefs = orderService.getOrdersByChef(user);
		List<OrderDTO> customers = orderService.getOrdersByCustomer(user);
		for(OrderDTO o1 : chefs) {
			for(OrderDTO o2 : customers) {
				if(o1.getId() == o2.getId())
					return o1;
			}
		}
		return null;
	}
	
	private OrderItemDTO getCurrentOrderItem(ProductDTO product, OrderDTO order) {
		
		List<OrderItemDTO> prod = orderItemService.getOrderItemsByProduct(product);
		List<OrderItemDTO> ord = orderItemService.getOrderItemsByOrder(order);
//		System.out.println("\n\n\n\n\n\n\n\n");
//		prod.stream().forEach(System.out::println);
//		System.out.println("\n");
//		prod.stream().forEach(System.out::println);
//		System.out.println("\n\n\n\n\n\n\n\n");
		for(OrderItemDTO o : ord) {
			for(OrderItemDTO p : prod) {
				if(o.getId() == p.getId()) {
					//System.out.println(o.toString() + "\n\n");
					return o;
				}
			}
		}
		return null;
	}
	
	private OrderItemDTO getOrderItemByProductName(String name, String email) {
		List<ProductDTO> prod = productService.getAllProducts();
		List<OrderItemDTO> list;
		
		for(ProductDTO p : prod) {
			if(p.getName().equals(name)) {
				list = orderItemService.getOrderItemsByProduct(p);
				for(OrderItemDTO o : list) {
					if(o.getOrder().getCustomer().getEmail().equals(email)) {
						return o;
					}
				}
			}
		}
		
		 return null;
	}
	
	private ProductDTO getProductByName(String name) {
		List<ProductDTO> list = productService.getAllProducts();
		for(ProductDTO p : list) {
			if(p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}
}
