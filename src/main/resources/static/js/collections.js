// Refresh all wishlist heart icons based on backend data
function refreshWishlistIcons() {
  fetch('/wishlistProducts')
    .then(res => {
      if (!res.ok) throw new Error("Failed to fetch wishlist");
      return res.json();
    })
    .then(wishlist => {
      const wishlistIds = wishlist.map(p => p.id);
      document.querySelectorAll('.wishlist-icon').forEach(icon => {
        const productId = parseInt(icon.getAttribute('data-product-id'));
        if (wishlistIds.includes(productId)) {
          icon.classList.add("active", "fas"); // solid red
          icon.classList.remove("far");
        } else {
          icon.classList.remove("active", "fas");
          icon.classList.add("far"); // empty heart
        }
      });
    })
    .catch(err => console.error("Error refreshing wishlist:", err));
}

document.addEventListener("DOMContentLoaded", () => {
  const wishlistIcons = document.querySelectorAll(".wishlist-icon");
  const userId = document.body.dataset.userId; // <body data-user-id="...">

  // Initial refresh from backend
  if (userId) {
    refreshWishlistIcons();
  }

  wishlistIcons.forEach(icon => {
    icon.addEventListener("click", async (e) => {
      e.preventDefault();

      // Check login
      if (!userId) {
        alert("You need to log in to use wishlist.");
        return;
      }

      const productId = icon.getAttribute("data-product-id");
      const isActive = icon.classList.contains("active");

      try {
        let response;
        if (isActive) {
          // REMOVE product from wishlist
          response = await fetch(
            `/removeProductInWishlist?userId=${userId}&productId=${productId}`,
            { method: "DELETE" }
          );
        } else {
          // ADD product to wishlist
          response = await fetch(
            `/addProductInWishlist?userId=${userId}&productId=${productId}`,
            { method: "POST" }
          );
        }

        if (response.ok) {
          // Re-sync all wishlist icons after update
          refreshWishlistIcons();
        } else {
          const msg = await response.text();
          alert("Wishlist update failed: " + msg);
          console.error("Failed to update wishlist:", msg);
        }
      } catch (error) {
        console.error("Error updating wishlist", error);
      }
    });
  });
});
