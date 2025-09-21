function changeImage(img) {
  document.getElementById('mainImage').src = img.src;
}

function selectThumbnail(img) {
  changeImage(img); // Set as main image
  document.querySelectorAll(".thumbnail").forEach(t => 
    t.classList.remove("selected-thumbnail")
  );
  img.classList.add("selected-thumbnail");
}

// ---------- CART FUNCTIONALITY (Connected to Backend) ----------

// Increase / decrease quantity
function adjustQuantity(delta) {
  const q = document.getElementById('quantity');
  let val = parseInt(q.textContent);
  q.textContent = Math.max(1, val + delta);
}

// Add to cart (calls backend)
document.querySelector(".add-to-cart").addEventListener("click", function() {
  const quantity = parseInt(document.getElementById("quantity").textContent, 10);
  const productId = this.getAttribute("data-id");
  const userId = document.body.getAttribute("data-user-id"); //fixed
 //ATYO6XU
 const productName = this.getAttribute("data-name");
 const productPrice = parseFloat(this.getAttribute("data-price"));
 const productImage = document.querySelector(".main-image").src; // main product image//this.getAttribute("data-image") || "/image/default.png";
 
 if (!userId || userId === "null") {
   // Guest → store in localStorage
   let cart = JSON.parse(localStorage.getItem("cart")) || {};

   if (cart[productId]) {
     cart[productId].quantity += quantity;
   } else {
     cart[productId] = {
       name: productName,
       price: productPrice,
       image: productImage || "/image/default.png", // fallback image,
       quantity: quantity
     };
   }

   localStorage.setItem("cart", JSON.stringify(cart));
   alert("Added to cart!");
   loadCart(); // refresh guest cart
   document.getElementById("quantity").textContent = "1"; // reset qty
   return;
 }
 //ATYAO6XU
 
  const cartData = {
    userId: userId ? parseInt(userId, 10) : null,
    productId: parseInt(productId, 10),
    quantity: quantity
  };

  const csrfToken = document.querySelector('meta[name="_csrf"]').content;
  const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
  
  fetch("/cart/add", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
	  [csrfHeader]: csrfToken
     // "X-CSRF-TOKEN": document.querySelector('meta[name="_csrf"]').content // only if CSRF enabled
    },
    body: JSON.stringify(cartData)
  })
  .then(res => {
    if (!res.ok) throw new Error("Failed to add to cart");
    return res.json();
  })
  .then(data => {
    alert(" Added to cart!");
    loadCart(); // refresh cart panel
    document.getElementById("quantity").textContent = "1"; // reset qty
  })
  .catch(err => {
    console.error("Error:", err);
    alert(" Could not add to cart");
  });
});

// Fetch and display cart items for logged-in and guest user
function loadCart() {
  const userId = document.body.getAttribute("data-user-id"); // null if guest
  let url = "/cart";

  if (userId) {
    url += `?userId=${userId}`;
  }

  fetch(url)
    .then(res => res.json())
    .then(products => {
      const cartDiv = document.querySelector(".cart-content");
      cartDiv.innerHTML = "";

      let total = 0;
      if (!products || products.length === 0) {
        cartDiv.innerHTML = "<p class='empty-cart'>Your cart is empty.</p>";
      } else {
		products.forEach(p => {
		  // Normalize productId for both cases (guest vs logged-in)
		  const productId = p.productId || p.id;  
		  const name = p.name || p.productName || "Unnamed prod";
		  const quantity = p.quantity || 1;
		  const price = p.price || 0;
		  const itemTotal = price * quantity;
		  total += itemTotal;

		  cartDiv.innerHTML += `
		    <div style="margin-bottom: 10px;">
		      ${name} - ${quantity} x ₹${price} = ₹${itemTotal}
		      <button onclick="removeFromCart(${productId}, ${userId ? userId : 'null'})" style="margin-left:10px;">❌</button>
		    </div>
		  `;
		});


        cartDiv.innerHTML += `<hr><strong>Total: ₹${total}</strong>`;
      }

      // Update checkout button total
      document.querySelector(".checkout-btn").textContent =
        `CHECK OUT — ₹${total}`;
    })
    .catch(err => console.error("Error loading cart:", err));
}


// Remove item from cart
function removeFromCart(productId, userId) {
  let url = `/cart/remove/${productId}`;
  if (userId && userId !== "null") {
    url += `?userId=${userId}`;
  }

  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute("content");
  const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");

  fetch(url, { method: "DELETE",   headers: {
      [csrfHeader]: csrfToken
    }})
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


// ---------- OTHER EXISTING FUNCTIONS ----------
function toggleSection(id) {
  const content = document.getElementById(id);
  const toggle = document.getElementById(id + '-toggle');
  const active = content.classList.toggle('active');
  toggle.textContent = active ? '−' : '+';
}

function selectFragrance(el) {
  const name = el.getAttribute("data-name");
  document.getElementById("selectedFragrance").textContent = name;

  document.querySelectorAll(".fragrance-item").forEach(item =>
    item.classList.remove("selected-fragrance")
  );
  el.classList.add("selected-fragrance");
}

window.onload = function () {
  // Select first small image thumbnail
  const firstThumbnail = document.querySelector(".thumbnail");
  if (firstThumbnail) {
    selectThumbnail(firstThumbnail);
  }

  // Select first fragrance image and name
  const firstFragrance = document.querySelector(".fragrance-item");
  if (firstFragrance) {
    selectFragrance(firstFragrance);
  }

  // Load cart on page load
  loadCart();
};
