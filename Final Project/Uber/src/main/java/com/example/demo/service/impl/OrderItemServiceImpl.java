package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.OrderDAO;
import com.example.demo.dao.OrderItemDAO;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.Particularity;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.service.OrderDTO;
import com.example.demo.service.OrderItemDTO;
import com.example.demo.service.OrderItemService;
import com.example.demo.service.ParticularityDTO;
import com.example.demo.service.ProductDTO;
import com.example.demo.service.UserDTO;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService{

	private final OrderItemDAO orderItemDAO;
	private final OrderDAO orderDAO;
	
	@Autowired
	public OrderItemServiceImpl(OrderItemDAO orderItemDAO, OrderDAO orderDAO) {
		this.orderItemDAO = orderItemDAO;
		this.orderDAO = orderDAO;
	}
	
	@Override
	public OrderItemDTO getOrderItemById(Long id) {
		try {
			return transform(orderItemDAO.getOne(id));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<OrderItemDTO> getOrderItemsByOrder(OrderDTO orderDTO) {
		try {
		
			UserDTO customerDTO = orderDTO.getCustomer();
			UserDTO chefDTO = orderDTO.getChef();
			User customer = new User(customerDTO.getId(), customerDTO.getEmail(), customerDTO.getPassword(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getPhone(), customerDTO.getIban());
			User chef = new User(chefDTO.getId(), chefDTO.getEmail(), chefDTO.getPassword(), chefDTO.getName(), chefDTO.getAddress(), chefDTO.getPhone(), chefDTO.getIban());
			Order order = new Order(orderDTO.getId(), customer, chef);
			order.setConfirmed(orderDTO.getConfirmed());
			order.setFinalizeOrder(orderDTO.getFinalizeOrder());
			order.setRating(orderDTO.getRating());
			return transform(orderItemDAO.findOrderItemsByOrder(order));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<OrderItemDTO> getOrderItemsByProduct(ProductDTO productDTO) {
		try {
			OrderItemDTO ord = new OrderItemDTO();
			ord.setProduct(productDTO);
			return transform(orderItemDAO.findOrderItemsByProduct(transform(ord).getProduct()));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO) {
		try {
			orderItemDTO.setFinalPrice();
			OrderItemDTO ord = transform(orderItemDAO.save(transform(orderItemDTO)));
			return ord;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public OrderItemDTO updateOrderItem(Long id, OrderItemDTO orderItemDTO) {
		try {
			orderItemDTO.setFinalPrice();
			OrderItem ord = transform(orderItemDTO);
			ord.setId(id);
			return transform(orderItemDAO.save(ord));
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteOrderItem(Long id) {
		orderItemDAO.delete(orderItemDAO.getOne(id));
	}
	
	private List<OrderItemDTO> transform(List<OrderItem> list){
		List<OrderItemDTO> result = new ArrayList<OrderItemDTO>();
		
		for(OrderItem o : list) {
			Product product = o.getProduct();
			Particularity particularity = product.getParticularity();
			ParticularityDTO part = new ParticularityDTO(particularity.getSpicy(), particularity.getGluten(), particularity.getLactose(), particularity.getHot(), particularity.getNuts(), particularity.getVegan());
			part.setId(particularity.getId());
			ProductDTO productDTO = new ProductDTO(product.getName(), product.getDescription(), product.getOrigin(), part, product.getPrice());
			productDTO.setId(product.getId());
			
			Order order = o.getOrder();
			User customer = order.getCustomer();
			User chef = order.getChef();
			UserDTO customerDTO = new UserDTO(customer.getEmail(), customer.getPassword(), customer.getName(), customer.getAddress(), customer.getPhone(), customer.getIban());
			customerDTO.setId(customer.getId());
			UserDTO chefDTO = new UserDTO(chef.getEmail(), chef.getPassword(), chef.getName(), chef.getAddress(), chef.getPhone(), chef.getIban());
			chefDTO.setId(chef.getId());
			OrderDTO orderDTO = new OrderDTO(customerDTO, chefDTO);
			orderDTO.setId(order.getId());
			orderDTO.setFinalizeOrder(order.getFinalizeOrder());
			orderDTO.setRating(order.getRating());
			
			OrderItemDTO orderItem = new OrderItemDTO(orderDTO, productDTO, o.getQuantity(), o.getFinalPrice());
			orderItem.setId(o.getId());
			result.add(orderItem);
		}
		return result;
	}
	
	private OrderItemDTO transform(OrderItem o) {
		Product product = o.getProduct();
		Particularity particularity = product.getParticularity();
		ParticularityDTO part = new ParticularityDTO(particularity.getSpicy(), particularity.getGluten(), particularity.getLactose(), particularity.getHot(), particularity.getNuts(), particularity.getVegan());
		part.setId(particularity.getId());
		ProductDTO productDTO = new ProductDTO(product.getName(), product.getDescription(), product.getOrigin(), part, product.getPrice());
		productDTO.setId(product.getId());
		
		Order order = o.getOrder();
		User customer = order.getCustomer();
		User chef = order.getChef();
		UserDTO customerDTO = new UserDTO(customer.getEmail(), customer.getPassword(), customer.getName(), customer.getAddress(), customer.getPhone(), customer.getIban());
		customerDTO.setId(customer.getId());
		UserDTO chefDTO = new UserDTO(chef.getEmail(), chef.getPassword(), chef.getName(), chef.getAddress(), chef.getPhone(), chef.getIban());
		chefDTO.setId(chef.getId());
		OrderDTO orderDTO = new OrderDTO(customerDTO, chefDTO);
		orderDTO.setId(order.getId());
		orderDTO.setFinalizeOrder(order.getFinalizeOrder());
		orderDTO.setRating(order.getRating());
		
		OrderItemDTO orderItem = new OrderItemDTO(orderDTO, productDTO, o.getQuantity(), o.getFinalPrice());
		orderItem.setId(o.getId());
		return orderItem;
	}
	
	private OrderItem transform(OrderItemDTO orderItemDTO) {
		ProductDTO prod = orderItemDTO.getProduct();
		ParticularityDTO partDTO = prod.getParticularityDTO();
		Particularity part = new Particularity(partDTO.getId(), partDTO.getSpicy(), partDTO.getGluten(), partDTO.getLactose(), partDTO.getHot(), partDTO.getNuts(), partDTO.getVegan());
		Product p = new Product(prod.getId(), prod.getName(), prod.getDescription(), prod.getOrigin(), part, prod.getPrice());
		
		Order order;
		if(orderItemDTO.getOrder() != null) {
			OrderDTO orderDTO = orderItemDTO.getOrder();
			UserDTO customerDTO = orderDTO.getCustomer();
			UserDTO chefDTO = orderDTO.getChef();
			User customer = new User(customerDTO.getId(), customerDTO.getEmail(), customerDTO.getPassword(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getPhone(), customerDTO.getIban());
			User chef = new User(chefDTO.getId(), chefDTO.getEmail(), chefDTO.getPassword(), chefDTO.getName(), chefDTO.getAddress(), chefDTO.getPhone(), chefDTO.getIban());
			order = new Order(orderDTO.getId(), customer, chef);
		}else {
			order = new Order();
		}
		OrderItem orderItem = new OrderItem();
		orderItem.setOrder(order);
		orderItem.setProduct(p);
		orderItem.setQuantity(orderItemDTO.getQuantity());
		orderItem.setFinalPrice(orderItemDTO.getFinalPrice());
		
		return orderItem;
	}

}
