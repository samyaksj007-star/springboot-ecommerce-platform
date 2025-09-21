//Used when the user enter invalid email password then after re-entering the error message will disappear.
document.addEventListener('DOMContentLoaded', function() {
    const emailInput = document.querySelector('input[name="email"]');
    const passwordInput = document.querySelector('input[name="password"]');
    const errorMessage = document.getElementById('error-message');

    // Check if error exists and display it
    if (errorMessage && errorMessage.textContent.trim() !== "") {
      errorMessage.style.display = 'block'; // Show the error if it exists
    }
    
    // Hide error message when the user starts typing
    emailInput.addEventListener('input', function() {
      if (errorMessage) {
        errorMessage.style.display = 'none'; // Hide error on typing
      }
    });

    passwordInput.addEventListener('input', function() {
      if (errorMessage) {
        errorMessage.style.display = 'none'; // Hide error on typing
      }
    });
  });
  
  //Function called when google logo is pressed
	function handleCredentialResponse(response) {
	  const idToken = response.credential;
	  console.log("Google ID Token:", idToken);
	  // Redirect browser directly to backend endpoint
	  //window.location.href = "/auth/google?idToken=" + encodeURIComponent(idToken);

	  fetch("/auth/google", {
	    method: "POST",
	    headers: {
	      "Content-Type": "application/json"
	    },
	    body: JSON.stringify({ idToken: idToken })
	  })
	  .then(res => {
	    if (!res.ok) {
	      throw new Error("Google login failed");
	    }
	    return res.json();
	  })
	  .then(data => {
	    console.log("Google login success:", data);
	    // Redirect to user account page
		if (data.redirectUrl) {
		    window.location.href = data.redirectUrl;
		  } else {
		    alert("Login failed: No redirect URL received");
			}

	  })
	  .catch(err => {
	    console.error("Error during Google login:", err);
	    alert("Login failed. Please try again.");
	  });
	}

