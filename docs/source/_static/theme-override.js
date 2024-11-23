// Force dark mode
// document.documentElement.dataset.theme = "dark"; // or "light"
// localStorage.setItem("theme", "dark");

// Set the default theme to dark or light mode
const defaultTheme = "dark"; // Change to "light" for light mode

// Check if the user has already set a preference
const savedTheme = localStorage.getItem("theme");

if (!savedTheme) {
    // If no preference is saved, apply the default theme
    document.documentElement.dataset.theme = defaultTheme;
}

// Ensure the user's choice persists when they use the toggle
document.addEventListener("DOMContentLoaded", function () {
    const toggle = document.querySelector("[data-toggle-theme]");
    if (toggle) {
        toggle.addEventListener("click", function () {
            const currentTheme = document.documentElement.dataset.theme;
            const newTheme = currentTheme === "dark" ? "light" : "dark";
            document.documentElement.dataset.theme = newTheme;
            localStorage.setItem("theme", newTheme);
        });
    }
});