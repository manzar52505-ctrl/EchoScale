// script.js - interactive landing page for EchoScale
document.addEventListener("DOMContentLoaded", () => {
    // Mobile menu toggle
    const toggleBtn = document.getElementById("mobileToggle");
    const navLinks = document.getElementById("navLinks");
    if (toggleBtn && navLinks) {
        toggleBtn.addEventListener("click", (e) => {
            e.stopPropagation();
            navLinks.classList.toggle("show");
        });
    }

    // Smooth scroll for anchor links + active nav highlighting
    const navItems = document.querySelectorAll(".nav-link");
    const sections = document.querySelectorAll("section[id]");

    function updateActiveNav() {
        let scrollPos = window.scrollY + 120;
        sections.forEach(section => {
            const top = section.offsetTop;
            const bottom = top + section.offsetHeight;
            if (scrollPos >= top && scrollPos < bottom) {
                const currentId = section.getAttribute("id");
                navItems.forEach(link => {
                    link.classList.remove("active");
                    if (link.getAttribute("href") === `#${currentId}`) {
                        link.classList.add("active");
                    }
                });
            }
        });
    }

    window.addEventListener("scroll", updateActiveNav);
    updateActiveNav();

    // smooth scroll for all internal links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener("click", function(e) {
            const targetId = this.getAttribute("href");
            if (targetId === "#" || targetId === "") return;
            const targetEl = document.querySelector(targetId);
            if (targetEl) {
                e.preventDefault();
                targetEl.scrollIntoView({ behavior: "smooth", block: "start" });
                // close mobile menu if open
                if (navLinks.classList.contains("show")) navLinks.classList.remove("show");
            }
        });
    });

    // Waitlist form handling with fake API simulation + success animation
    const waitlistForm = document.getElementById("waitlistForm");
    const feedbackDiv = document.getElementById("formFeedback");
    const submitBtn = document.getElementById("submitBtn");

    if (waitlistForm) {
        waitlistForm.addEventListener("submit", async (e) => {
            e.preventDefault();
            const name = document.getElementById("nameInput").value.trim();
            const email = document.getElementById("emailInput").value.trim();
            const company = document.getElementById("companyInput").value.trim();

            if (!name || !email) {
                showFeedback("Please provide name and email address.", "error");
                return;
            }
            if (!email.includes("@") || !email.includes(".")) {
                showFeedback("Enter a valid email address.", "error");
                return;
            }

            // Simulate backend call (Java Spring Boot would receive JSON)
            // For demo we just mimic success
            showFeedback("⏳ Registering... Please wait", "info");
            submitBtn.disabled = true;
            submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Processing';

            // simulate network delay
            setTimeout(() => {
                // In real scenario: POST to /api/waitlist
                // Here we emulate success response from Java backend
                const mockResponse = { success: true, message: "🎉 You're on the EchoScale early access list! We'll reach out soon." };
                if (mockResponse.success) {
                    showFeedback(mockResponse.message, "success");
                    waitlistForm.reset();
                } else {
                    showFeedback("Server error. Please try again later.", "error");
                }
                submitBtn.disabled = false;
                submitBtn.innerHTML = 'Secure early access <i class="fas fa-lock"></i>';
            }, 800);
        });
    }

    function showFeedback(msg, type) {
        feedbackDiv.innerHTML = msg;
        feedbackDiv.style.color = type === "error" ? "#f87171" : (type === "success" ? "#4ade80" : "#facc15");
        setTimeout(() => {
            if (type !== "error") setTimeout(() => { feedbackDiv.innerHTML = ""; }, 3000);
        }, 4000);
    }

    // simple parallax / waveform effect (decorative)
    const waveBars = document.querySelectorAll(".wave-bar");
    if (waveBars.length) {
        setInterval(() => {
            waveBars.forEach(bar => {
                const randomH = Math.floor(Math.random() * 45) + 18;
                bar.style.height = `${randomH}px`;
            });
        }, 900);
    }
});
