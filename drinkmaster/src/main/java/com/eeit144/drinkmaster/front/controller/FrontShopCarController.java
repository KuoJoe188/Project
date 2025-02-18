package com.eeit144.drinkmaster.front.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
//import java.util.Optional;
import java.util.Set;

//import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.eeit144.drinkmaster.back.model.OrderItemsService;
import com.eeit144.drinkmaster.back.model.OrderService;
import com.eeit144.drinkmaster.back.model.ProductService;
import com.eeit144.drinkmaster.back.model.StoreService;
import com.eeit144.drinkmaster.back.model.UserService;
//import com.eeit144.drinkmaster.back.service.ProductCategoryServiceImp;
import com.eeit144.drinkmaster.back.service.ProductServiceImp;
import com.eeit144.drinkmaster.bean.OrderBean;
import com.eeit144.drinkmaster.bean.OrderItems;
import com.eeit144.drinkmaster.bean.ProductBean;
import com.eeit144.drinkmaster.bean.ShopcarBean;
import com.eeit144.drinkmaster.bean.ShopcarBuy;
import com.eeit144.drinkmaster.bean.StoreBean;
import com.eeit144.drinkmaster.bean.UserBean;

@Controller
@RequestMapping("front/")
@SessionAttributes(names= {"orderuserBean","shopcarBuy","product","canSeeUser","price","data","address"})
@SuppressWarnings("unchecked")
public class FrontShopCarController {

	@Autowired
	private ProductServiceImp proService;
	
//	@Autowired
//	private ProductCategoryServiceImp categoryService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderItemsService oitemService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private ProductService productService;
	
	public void testUserSession(Model m){
		UserBean user = userService.findById(1).get();
		m.addAttribute("orderuserBean", user);
	}
	
		
	@GetMapping("shopcar/")
	public String carView(Model m) {
//		testUserSession(m);
		
		return "/front/frontshopcar";
	}
	
	@GetMapping("shopcar/before")
	public String carBeforeView() {
		return "/front/frontbeforeshop";
	}
	
	@GetMapping("shopcar/order")
	public String orderView(Model m) {
//		testUserSession(m);
		
		return "/front/frontorder";
	}
	
	
//	@GetMapping("shopcar/before/editproduct")
//	public String updateById(@RequestParam("id") Integer id, Model m) {
//		ProductBean productBean = proService.findById(id);
//		
//		
//		m.addAttribute("productBean", productBean);
//		m.addAttribute("insert", "updateproduct");
//		return "/front/frontbeforeshop";
//	}
	
	
	@GetMapping("shopcar/before/editproduct")
	public String editById(@RequestParam("id") Integer id, Model m,@RequestParam("sid") Integer storeId) {
		ProductBean productBean = proService.findById(id);
		
	
		
		StoreBean storeBean = storeService.findById(storeId).get();
		
		
//		List<ShopcarBean> cart = new ArrayList<ShopcarBean>();
		
		
		
		
		
		m.addAttribute("productBean", productBean);
//		m.addAttribute("shopcarBuy", productBean);
		
		m.addAttribute("storeBean", storeBean);
//		m.addAttribute("shopcarBuy", storeBean);

		return "/front/frontbeforeshop";
	}
	

	@PostMapping("shopcar/buy")
	public String addShopcar(Model m,@RequestParam("shopcarproductId") Integer productId
			,@RequestParam("sugar") String sugar,@RequestParam("coldhot") String coldhot,@RequestParam("number") Integer number,
			@RequestParam("totalprice") Integer totalprice,@RequestParam("storeName") String storeName,
			@RequestParam("storeId") Integer storeId,@ModelAttribute("productBean") ProductBean productBean) {
		
		UserBean userBean = (UserBean) m.getAttribute("canSeeUser");
		if(userBean == null) {
			return "redirect:/front/login";
		}
		
//		// 取出存放在session物件內的shopcarBuy物件
		Map<Integer,ShopcarBean> cart = (Map<Integer,ShopcarBean>) m.getAttribute("shopcarBuy");
		
		ShopcarBean shopcarBean = null;

		

		if(cart == null) {
		cart = new LinkedHashMap< >();
		shopcarBean = new ShopcarBean();

		shopcarBean.setProductId(productId);
		shopcarBean.setProductName(productBean.getProductName());
		shopcarBean.setPrice(productBean.getPrice());
		shopcarBean.setColdhot(coldhot);
		shopcarBean.setQuantity(number);
		shopcarBean.setSweet(sugar);
		shopcarBean.setTotalPrice(totalprice);
		shopcarBean.setStoreName(storeName);
		shopcarBean.setStoreId(storeId);
		cart.put(productId, shopcarBean);
		
		}
		//用原生的方法去判斷有沒有值
		else if(cart.containsKey(productId)){
			
			shopcarBean = new ShopcarBean();
			ShopcarBean newNumber = cart.get(productId);
			shopcarBean.setProductId(productId);
			shopcarBean.setProductName(productBean.getProductName());
			shopcarBean.setPrice(productBean.getPrice());
			shopcarBean.setColdhot(coldhot);
			shopcarBean.setQuantity(number+newNumber.getQuantity());
			shopcarBean.setSweet(sugar);
			shopcarBean.setTotalPrice(totalprice+newNumber.getTotalPrice());
			shopcarBean.setStoreName(storeName);
			shopcarBean.setStoreId(storeId);
			cart.put(productId, shopcarBean);
		}
		else{
			shopcarBean = new ShopcarBean();

			shopcarBean.setProductId(productId);
			shopcarBean.setProductName(productBean.getProductName());
			shopcarBean.setPrice(productBean.getPrice());
			shopcarBean.setColdhot(coldhot);
			shopcarBean.setQuantity(number);
			shopcarBean.setSweet(sugar);
			shopcarBean.setTotalPrice(totalprice);
			shopcarBean.setStoreName(storeName);
			shopcarBean.setStoreId(storeId);
			cart.put(productId, shopcarBean);
		}
		
		m.addAttribute("shopcarBuy", cart);
		Map<Integer,ShopcarBean> cart2 = (Map<Integer,ShopcarBean>) m.getAttribute("shopcarBuy");
		Integer total = 0;
		ShopcarBean shopcarBean2 = new ShopcarBean();
		for(Integer i :cart2.keySet()) {
			total += cart2.get(i).getTotalPrice();
//			System.out.println(total);
		}
		shopcarBean2.setTotalPrice(total);
		shopcarBean2.setStoreId(storeId);
		m.addAttribute("price", shopcarBean2);
		
		

		return "/front/frontshopcar";
	}
	
	
	
	@PostMapping("shopcar/writeData")
	public String orderData(@RequestParam(name="totalpricefinal",defaultValue = "0") Integer totalPriceFinal,Model m) {
//		System.out.println(totalPriceFinal);
//		ShopcarBean shopcarBean = new ShopcarBean();
//		m.addAttribute("shopcarBuy", shopcarBean);
		m.addAttribute("product", totalPriceFinal);
		
		return "/front/frontshopcardata";
	}
	
	

	
	
	@PostMapping("shopcar/confirmOrder")
	public String confirmOrder(Model m,
			@RequestParam("shopcarphone") String shopcarphone
			,@RequestParam("shopcaraddress") String shopcaraddress
			,@RequestParam("shopcarname") String shopcarname
			,@RequestParam("shopcarprice") Integer shopcarprice
			,@RequestParam("shopcarquantity") Integer shopcarquantity
			,@RequestParam("shopcarsweet") String shopcarsweet
			,@RequestParam("shopcarcoldhot") String shopcarcoldhot
			,@RequestParam("shopcartotalPrice") Integer shopcartotalPrice
			,@RequestParam("userId") Integer userId
			,@RequestParam("productId") Integer productId
			,@RequestParam("storeName") String storeName
			,@RequestParam("storeId") Integer storeId
			) {
						
		Map<Integer,ShopcarBean> cart = (Map<Integer,ShopcarBean>) m.getAttribute("shopcarBuy");
		Integer totalPriceFinal = (Integer) m.getAttribute("product");
		
		Set<Integer> storeIdSet = new HashSet<Integer>();
		
		Date today = new Date();
		//可以從set知道有幾家店
		for(Integer i : cart.keySet()) {
			storeIdSet.add(cart.get(i).getStoreId());
			
		}
		//用大迴圈把店家拿出來 知道有哪些店
		for(Integer store : storeIdSet) {
			
		StoreBean storeBean = storeService.findById(store).get();
		OrderBean orderBean = new OrderBean();
		UserBean user = new UserBean();
		user.setUserId(userId);
		orderBean.setStoreBean(storeBean);
		orderBean.setCreateTime(today);
		orderBean.setOrderAddress(shopcaraddress);
		orderBean.setOrderPhone(shopcarphone);
		orderBean.setOrderStatus("待付款");
		//預設0 之後再由item的價格加總
		Integer totalprice  = 0;
		orderBean.setTotalPrice(totalprice);
		orderBean.setUserBean(user);
		orderService.insertOrder(orderBean);
		//小迴圈把同一店家的item塞到同一筆訂單
			for(Integer oi : cart.keySet()) {
				ProductBean	product = productService.findById(cart.get(oi).getProductId());
				if(product.getProductCategoryBean().getStoreBean().getStoreId() != storeBean.getStoreId()) {
					continue;
				}
				
				OrderItems oitems = new OrderItems();
				oitems.setPrice(cart.get(oi).getPrice());
				oitems.setQuantity(cart.get(oi).getQuantity());
				oitems.setSweet(cart.get(oi).getSweet());
				oitems.setColdhot(cart.get(oi).getColdhot());
				oitems.setProductBean(product);
				oitems.setOrderBean(orderBean);
				oitemService.insertOrderItems(oitems);
				
				totalprice += oitems.getPrice();
			}
			orderBean.setTotalPrice(totalPriceFinal);
			orderService.insertOrder(orderBean);
		}
		
		m.addAttribute("shopcarBuy", cart);
		
		OrderBean orderBean2 = new OrderBean();
		orderBean2.setOrderAddress(shopcaraddress);
		orderBean2.setOrderPhone(shopcarphone);
		orderBean2.setOrderStatus("待付款");
		orderBean2.setCreateTime(today);
		orderBean2.setTotalPrice(totalPriceFinal);
		m.addAttribute("data", orderBean2);
		
		return "redirect:/front/shopcar/deleteCar";
	}
	
	
	
	
	
	@GetMapping("shopcar/delete")
	public String daleteShopcar(@RequestParam("productId") Integer productId,Model m) {
		Map<Integer,ShopcarBean> cart = (Map<Integer,ShopcarBean>) m.getAttribute("shopcarBuy");
		cart.remove(productId);
		m.addAttribute("shopcarBuy", cart);
		Integer total = 0;
		ShopcarBean shopcarBean2 = new ShopcarBean();
		for(Integer i :cart.keySet()) {
			total += cart.get(i).getTotalPrice();
//			System.out.println(total);
		}
		shopcarBean2.setTotalPrice(total);
		m.addAttribute("price", shopcarBean2);
		
		return "/front/frontshopcar";
	}
	
	

}
