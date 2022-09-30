package com.github.simohin.board.ui.util

import com.vaadin.flow.component.html.IFrame
import com.vaadin.flow.component.orderedlayout.FlexComponent

fun FlexComponent.center() = this.apply {
    this.setSizeFull()
    this.justifyContentMode = FlexComponent.JustifyContentMode.CENTER
    this.alignItems = FlexComponent.Alignment.CENTER
}

fun IFrame.full() = this.apply {
    this.setSizeFull()
    this.element.setAttribute("frameborder", "0")
}
