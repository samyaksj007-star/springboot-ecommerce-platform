// ========================
// Toggle Cart Panel
// ========================
function toggleCart() {
  const cartPanel = document.getElementById("cartPanel");
  if (cartPanel) {
    cartPanel.classList.toggle("open");

    // When opening cart → load items
    if (cartPanel.classList.contains("open")) {
      loadCart();
    }
  }

  // Optional: Close search if it's open
  const searchOverlay = document.getElementById("searchOverlay");
  if (searchOverlay && searchOverlay.style.display === "block") {
    searchOverlay.style.display = "none";
  }
}

// ========================
// Toggle Search Overlay
// ========================
function toggleSearch() {
  const searchOverlay = document.getElementById("searchOverlay");

  if (searchOverlay.style.display === "block") {
    searchOverlay.style.display = "none";
  } else {
    searchOverlay.style.display = "block";

    // Optional: Close cart if it's open
    const cartPanel = document.getElementById("cartPanel");
    if (cartPanel && cartPanel.classList.contains("open")) {
      cartPanel.classList.remove("open");
    }
  }
}

// ========================
// Add to Cart (Guest)
// ========================
function addToCartGuest(product) {
  // product = { id, name, price, image }
  let cart = JSON.parse(localStorage.getItem("cart")) || {};

  if (cart[product.id]) {
    cart[product.id].quantity += 1;
  } else {
    cart[product.id] = {
      name: product.name,
      price: product.price,
      image: product.image,
      quantity: 1
    };
  }

  localStorage.setItem("cart", JSON.stringify(cart));
}

// ========================
// Increase Quantity
// ========================
function increaseQuantity(productId, userId) {
  if (!userId || userId === "null") {
    let cart = JSON.parse(localStorage.getItem("cart")) || {};
    if (cart[productId]) {
      cart[productId].quantity += 1;
      localStorage.setItem("cart", JSON.stringify(cart));
      document.querySelector(`#cart-qty-${productId}`).innerText = cart[productId].quantity;
      loadCart(); // refresh guest totals
    }
    return;
  }

  // Logged in user → call backend
  fetch(`/cart/increase?productId=${productId}&userId=${userId}`, {
    method: "POST",
    headers: {
      "X-CSRF-TOKEN": document.querySelector('meta[name="_csrf"]').content
    }
  })
    .then(response => response.json())
    .then(data => {
      document.querySelector(`#cart-qty-${productId}`).innerText = data.quantity;
      loadCart(); // refresh logged-in totals
    })
    .catch(error => console.error("Error increasing quantity:", error)); 
}


// ========================
// Decrease Quantity
// ========================
function decreaseQuantity(productId, userId) {
  const qtyEl = document.querySelector(`#cart-qty-${productId}`);
  const currentQty = parseInt(qtyEl.innerText, 10);

  if (currentQty > 1) {
    if (!userId || userId === "null") {
      let cart = JSON.parse(localStorage.getItem("cart")) || {};
      if (cart[productId]) {
        cart[productId].quantity -= 1;  
      localStorage.setItem("cart", JSON.stringify(cart));
	  
	  // Update UI immediately
	        document.querySelector(`#cart-qty-${productId}`).innerText = cart[productId].quantity;
	        loadCart(); // refresh total and cart display
	      }
	      return;
	    }

      fetch(`/cart/decrease?productId=${productId}&userId=${userId}`, {
        method: "POST",
        headers: { "X-CSRF-TOKEN": document.querySelector('meta[name="_csrf"]').content }
      })
        .then(r => r.json())
        .then(data => {
          qtyEl.innerText = data.quantity;
		  loadCart();
        })
        .catch(err => console.error("Error decreasing:", err));
  } else {
    removeFromCart(productId, userId);
  }
}

// ========================
// Load Cart Items
// ========================
function loadCart() {
  const userId = document.body.getAttribute("data-user-id"); // null if guest
  const cartDiv = document.querySelector(".cart-content");
  if (!cartDiv) return;

  cartDiv.innerHTML = "";
  let total = 0;

  // Guest user → from localStorage
  if (!userId || userId === "null") {
    const cart = JSON.parse(localStorage.getItem("cart")) || {};
    const productIds = Object.keys(cart);

    if (productIds.length === 0) {
      cartDiv.innerHTML = "<p class='empty-cart'>Your cart is empty.</p>";
    } else {
      productIds.forEach(productId => {
        const { name, price, image, quantity } = cart[productId];
        const itemTotal = price * quantity;
        total += itemTotal;

        cartDiv.innerHTML += `
          <div class="cart-item">
            <img src="${image}" alt="${name}" class="cart-item-img">
            <div class="cart-item-details">
              <h3 class="cart-item-name">${name}</h3>
              <p class="cart-item-price">₹${price}</p>
              <div class="cart-qty">
                <button class="qty-btn" onclick="decreaseQuantity(${productId}, null)">−</button>
                <span id="cart-qty-${productId}" class="qty-value">${quantity}</span>
                <button class="qty-btn" onclick="increaseQuantity(${productId}, null)">+</button>
              </div>
              <button onclick="removeFromCart(${productId}, null)" class="remove-btn">REMOVE</button>
            </div>
          </div>
        `;
      });

      cartDiv.innerHTML += `<hr><strong>Total: ₹${total}</strong>`;
    }

    const checkoutBtn = document.querySelector(".checkout-btn");
    if (checkoutBtn) {
      checkoutBtn.textContent = `CHECKOUT — ₹${total}`;
    }
    return;
  }

  // Logged in user → backend
  let url = "/cart";
  if (userId) {
    url += `?userId=${userId}`;
  }

  fetch(url)
    .then(res => res.json())
    .then(products => {
      cartDiv.innerHTML = "";
      let total = 0;

      products.forEach(p => {
        const productId = p.productId || p.id;
        const name = p.name || p.productName || "Unnamed";
        const quantity = p.quantity || 1;
        const price = p.price || 0;
        //const itemTotal = price * quantity;
		const itemTotal = p.totalPrice !== undefined ? p.totalPrice : price * quantity;
        total += itemTotal;

        cartDiv.innerHTML += `
          <div class="cart-item">
            <img src="${p.productImage || '/image/default.png'}" alt="${name}" class="cart-item-img">
            <div class="cart-item-details">
              <h3 class="cart-item-name">${name}</h3>
              <p class="cart-item-price">₹${price}</p>
              <div class="cart-qty">
                <button class="qty-btn" onclick="decreaseQuantity(${productId}, ${userId})">−</button>
                <span id="cart-qty-${productId}" class="qty-value">${quantity}</span>
                <button class="qty-btn" onclick="increaseQuantity(${productId}, ${userId})">+</button>
              </div>
              <button onclick="removeFromCart(${productId}, ${userId})" class="remove-btn">REMOVE</button>
            </div>
          </div>
        `;
      });

      if (products.length > 0) {
        cartDiv.innerHTML += `<hr><strong>Total: ₹${total}</strong>`;
      } else {
        cartDiv.innerHTML = "<p class='empty-cart'>Your cart is empty.</p>";
      }

      const checkoutBtn = document.querySelector(".checkout-btn");
      if (checkoutBtn) {
        checkoutBtn.textContent = `CHECKOUT — ₹${total}`;
		checkoutBtn.setAttribute("data-amount", total); // store amount
      }
    })
    .catch(err => console.error("Error loading cart:", err));
}

// ========================
// Remove Item From Cart
// ========================
function removeFromCart(productId, userId) {
  if (!userId || userId === "null") {
    let cart = JSON.parse(localStorage.getItem("cart")) || {};
    delete cart[productId];
    localStorage.setItem("cart", JSON.stringify(cart));
    loadCart();
    return;
  }

  let url = `/cart/remove/${productId}`;
  if (userId && userId !== "null") {
    url += `?userId=${userId}`;
  }

  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute("content");
  const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");

  fetch(url, {
    method: "DELETE",
    headers: { [csrfHeader]: csrfToken }
  })
    .then(res => {
      if (!res.ok) throw new Error("Failed to remove item");
      return res.text();
    })
    .then(msg => {
      console.log(msg);
      loadCart();
    })
    .catch(err => console.error("Error removing item:", err));
}

// ========================
// Auto-load cart on pages with cart panel
// ========================
document.addEventListener("DOMContentLoaded", () => {
  if (document.querySelector(".cart-content")) {
    loadCart();
  }
});

// Razorpay integration
document.addEventListener("click", async function (e) {
  if (e.target.classList.contains("checkout-btn")) {
    const btn = e.target;
    const amount = parseFloat(btn.getAttribute("data-amount")) || 0;
    if (amount <= 0) {
      alert("Your cart is empty!");
      return;
    }

    // Convert to paise for Razorpay (₹1 = 100 paise)
    const amountInPaise = amount * 100;

    try {
      // 1. Create Razorpay Order from backend
      const response = await fetch("/api/payment/create-order", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ amount: amountInPaise })
      });

      const order = await response.json();

      // 2. Open Razorpay Checkout
      const options = {
        key: "YOUR_RAZORPAY_KEY_ID",  // replace with your key
        amount: order.amount,
        currency: order.currency,
        name: "Nappa Dori",
        description: "Order Payment",
        order_id: order.id,
        prefill: {
          name: "Customer Name",  // you can pull from logged-in user
          email: "customer@email.com",
          contact: "9876543210"
        },
        theme: { color: "#3399cc" },
        handler: function (response) {
          alert("Payment Successful. Payment ID: " + response.razorpay_payment_id);
          // TODO: call backend to verify payment & update Order status
        }
      };

      const rzp = new Razorpay(options);
      rzp.open();

    } catch (err) {
      console.error("Error in checkout:", err);
      alert("Something went wrong while starting payment.");
    }
  }
});

