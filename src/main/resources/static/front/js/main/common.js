window.addEventListener("DOMContentLoaded", function() {
    new Swiper(".main-banner", "banners" {
          navigation: {
            nextEl: ".swiper-button-next",
            prevEl: ".swiper-button-prev",
          },
          loop: true,
          autoplay: {
            delay: 1000
          },
          speed: 1000
        });
})