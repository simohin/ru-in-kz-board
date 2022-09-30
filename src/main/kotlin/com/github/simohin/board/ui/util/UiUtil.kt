package com.github.simohin.board.ui.util

import com.vaadin.flow.component.html.IFrame
import com.vaadin.flow.component.orderedlayout.FlexComponent

fun FlexComponent.center() {
    setSizeFull()
    justifyContentMode = FlexComponent.JustifyContentMode.CENTER
    alignItems = FlexComponent.Alignment.CENTER
}

fun IFrame.full() = this.apply {
    setSizeFull()
    element.setAttribute("frameborder", "0")
}
