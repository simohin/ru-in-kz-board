package com.github.simohin.board.ui

import com.github.simohin.board.dto.SheetRow
import com.github.simohin.board.service.SheetService
import com.github.simohin.board.ui.util.center
import com.vaadin.flow.component.Text
import com.vaadin.flow.component.html.Anchor
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.messages.MessageList
import com.vaadin.flow.component.messages.MessageListItem
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route
import java.time.ZoneId

@Route("")
class MainLayout(
    private val sheetService: SheetService
) : VerticalLayout() {

    init {
        add(title(), messageList())
    }

    private fun title() = VerticalLayout().apply {
        justifyContentMode = FlexComponent.JustifyContentMode.CENTER
        alignItems = FlexComponent.Alignment.CENTER
        add(
            H1("Навыки и умения приезжих ребят"),
            Span(Text("Для того, чтобы добавить запись в этот список перейдите по "), Anchor("/form", "ссылке"))
        )
    }

    private fun messageList() = MessageList().apply {
        setHeightFull()
        setItems(sheetService.getAll().toMessageListItems())
    }

    private fun List<SheetRow>.toMessageListItems() = this.map {
        MessageListItem(
            """
                Город: ${it.city},
                Навык:
                ${it.good}
                Контакты:
                ${it.contact}
            """.trimIndent(),
            it.time.atZone(ZoneId.systemDefault()).toInstant(),
            it.name
        ).apply {
            center()
        }
    }
}
