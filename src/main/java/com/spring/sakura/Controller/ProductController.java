package com.spring.sakura.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.sakura.dao.ProductRepository;
import com.spring.sakura.entities.Products;
import com.spring.sakura.service.IProductService;

@Controller
public class ProductController {
	
	@Autowired
	private IProductService productService;
	
	@Autowired
	private ProductRepository productRepo;
	
	@GetMapping("/collections")
	public String collectionsPageHandler(Model model) {
		System.out.println("In collections Page Handler");
		List<Products> products = productService.getAllProducts();
		model.addAttribute("products", products);
		return "collections";
	}
	
	@GetMapping("/product/{id}")
	public String showProduct(@PathVariable Long id, Model model) {
	    Products product = productRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Product not found"));
	    model.addAttribute("product", product);
	    return "productDetails"; 
	}
	
	@PostMapping("/product")
	@ResponseBody
	public Products addNewProduct(@RequestBody Products product) {
		Products p = this.productService.addNewProduct(product);
		return p;
	}
	
	@GetMapping("/index")
	public String indexPageHandler(Model model) {
		System.out.println("In index Page Handler");
		
		return "index";
	}

}
