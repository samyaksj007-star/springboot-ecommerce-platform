function toggleSearch() {
  const overlay = document.getElementById("searchOverlay");
  overlay.style.display = overlay.style.display === "flex" ? "none" : "flex";
}

document.addEventListener("DOMContentLoaded", function () {
  const wishlistBtn = document.getElementById("wishlistBtn");
  const wishlistModal = document.getElementById("wishlistModal");
  const wishlistContent = document.getElementById("wishlistContent");
  const wishlistLoginMessage = document.getElementById("wishlistLoginMessage");
  const closeModalBtn = document.getElementById("closeModalBtn");

  // Function to load wishlist items
  function openWishlistModal() {
    fetch("/wishlistProducts")
      .then(response => {
        if (response.status === 401) {
          throw new Error("Unauthorized");
        }
        return response.json();
      })
      .then(products => {
        wishlistContent.innerHTML = ""; // Clear previous content
        wishlistLoginMessage.style.display = "none"; // hide login msg

        if (!products || products.length === 0) {
          // Logged in but empty wishlist
          wishlistContent.innerHTML = `
            <div class="wishlist-empty">
              <p>There are no items in this wishlist.</p>
            </div>
          `;
        } else {
          // Render wishlist products
          products.forEach(product => {
            const itemHTML = `
              <div class="wishlist-item">
                <img src="${product.image1 || '/default.jpg'}" alt="${product.name}" class="wishlist-image"/>
                
                <div class="wishlist-details">
                  <a href="/product/${product.id}" class="product-link">
                    <h4>${product.name}</h4>
                  </a>
                  <p>₹${product.price}</p>
                </div>

                <span class="delete-icon" onclick="removeFromWishlist(${product.id})">&times;</span>
              </div>
            `;
            wishlistContent.innerHTML += itemHTML;
          });
        }

        wishlistModal.style.display = "block";
      })
      .catch(error => {
        console.error("Error loading wishlist:", error);

        // Show login message if unauthorized
        wishlistLoginMessage.style.display = "block";
        wishlistContent.innerHTML = ""; // clear content
        wishlistModal.style.display = "block";
      });
  }

  // Bind wishlist icon (heart) to modal
  if (wishlistBtn) {
    wishlistBtn.addEventListener("click", openWishlistModal);
  }

  // Close modal on X
  if (closeModalBtn) {
    closeModalBtn.addEventListener("click", () => {
      wishlistModal.style.display = "none";
    });
  }

  // Close modal on outside click
  window.addEventListener("click", (e) => {
    if (e.target === wishlistModal) {
      wishlistModal.style.display = "none";
    }
  });
});

// Placeholder for cart + remove actions
function addToCart(productId) {
  console.log("Add to cart: ", productId);
}

function removeFromWishlist(productId) {
  console.log("Remove from wishlist: ", productId);
}
