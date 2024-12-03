package com.insurtech.kanguro.ui.epoxy

import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModelBuilder
import com.airbnb.epoxy.carousel

inline fun com.airbnb.epoxy.ModelCollector.kanguroCarousel(
    carouselPadding: Carousel.Padding,
    modelInitializer: CarouselModelBuilder.() -> Unit
) {
    carousel {
        padding(carouselPadding)
        onBind { model, view, position ->
            view.post {
                view.scrollToPosition(0)
            }
        }
        modelInitializer()
    }
}
