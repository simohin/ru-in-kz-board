package com.github.simohin.board.ui

import com.github.simohin.board.ui.util.center
import com.github.simohin.board.ui.util.full
import com.vaadin.flow.component.html.IFrame
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route

@Route("form")
class FormLayout : VerticalLayout() {

    init {
        center()
        add(formFrame())
    }

    private fun formFrame() =
        IFrame("https://docs.google.com/forms/d/e/1FAIpQLSdEsOvf4uMky9EllazmJBDrQTMzmeEtFx2wDF0zLkO57oEkqA/viewform?embedded=true")
            .full()
}
