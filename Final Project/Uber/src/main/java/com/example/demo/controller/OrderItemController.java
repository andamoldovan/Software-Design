package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.OrderDTO;
import com.example.demo.service.OrderItemDTO;
import com.example.demo.service.OrderItemService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductDTO;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("/orderItem")
public class OrderItemController {
	
	private final OrderItemService orderItemService;
	private OrderService orderService;
	private ProductService productService;
	
	@Autowired
	public OrderItemController(OrderItemService orderItemService, OrderService orderService, ProductService productService) {
		this.orderItemService = orderItemService;
		this.orderService = orderService;
		this.productService = productService;
	}
	
	@GetMapping(value = "/{id}")
	public OrderItemDTO getOrderItemById(@RequestParam("id") Long id) {
		try {
			return orderItemService.getOrderItemById(id);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "/{order_id}")
	public List<OrderItemDTO> getOrderItemByOrderId(@RequestParam("order_item") Long orderId){
		try {
			OrderDTO orderDTO = orderService.getOrderById(orderId);
			return orderItemService.getOrderItemsByOrder(orderDTO);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping(value = "/{product_id}")
	public List<OrderItemDTO> getOrderItemByProductId(@RequestParam("product_id") Long productId){
		try {
			ProductDTO productDTO = productService.getProductById(productId);
			return orderItemService.getOrderItemsByProduct(productDTO);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PostMapping()
	public OrderItemDTO createOrderItem(@RequestParam("order_id") Long orderId, @RequestParam("product_id") Long productId, @RequestParam("quantity") int quantity) {
		try {
			OrderDTO orderDTO = orderService.getOrderById(orderId);
			System.out.println("\n\n" + orderDTO.toString() + "\n\n");
			ProductDTO productDTO = productService.getProductById(productId);
			System.out.println("\n\n" + productDTO.toString() + "\n\n");
			OrderItemDTO orderItemDTO = new OrderItemDTO(orderDTO, productDTO, quantity);
			return orderItemService.createOrderItem(orderItemDTO);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@PutMapping()
	public OrderItemDTO updateOrderItem(@RequestParam("id") Long id, @RequestParam("order_id") Long orderId, @RequestParam("product_id") Long productId, @RequestParam("quantity") int quantity) {
		try {
			OrderDTO orderDTO = orderService.getOrderById(orderId);
			ProductDTO productDTO = productService.getProductById(productId);
			OrderItemDTO orderItemDTO = new OrderItemDTO(orderDTO, productDTO, quantity);
			return orderItemService.updateOrderItem(id, orderItemDTO);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@DeleteMapping()
	public String deleteOrderItem(@RequestParam("id") Long id) {
		try {
			orderItemService.deleteOrderItem(id);
			return "OrderItem at id: " + id + " was deleted";
		}catch(Exception e) {
			e.printStackTrace();
			return "OrderItem at id: " + id + " was not deleted";
		}
	}
}
