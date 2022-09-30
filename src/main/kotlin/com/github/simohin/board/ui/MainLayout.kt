package com.github.simohin.board.ui

import com.github.simohin.board.dto.SheetRow
import com.github.simohin.board.service.SheetService
import com.vaadin.flow.component.Component
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
        center()
        add(messageList())
    }

    private fun messageList(): Component = MessageList().apply {
        center()
        setItems(sheetService.getAll().toMessageListItems())
    }

    private fun FlexComponent.center() {
        setSizeFull()
        justifyContentMode = FlexComponent.JustifyContentMode.CENTER
        alignItems = FlexComponent.Alignment.CENTER
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
