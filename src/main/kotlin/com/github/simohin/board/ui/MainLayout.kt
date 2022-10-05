package com.github.simohin.board.ui

import com.github.simohin.board.dto.SheetRow
import com.github.simohin.board.service.SheetService
import com.vaadin.flow.component.AbstractField
import com.vaadin.flow.component.Text
import com.vaadin.flow.component.combobox.MultiSelectComboBox
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridVariant
import com.vaadin.flow.component.grid.dataview.GridLazyDataView
import com.vaadin.flow.component.html.Anchor
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route

@Route("")
class MainLayout(
    private val sheetService: SheetService
) : VerticalLayout() {

    private val title = VerticalLayout().apply {
        justifyContentMode = FlexComponent.JustifyContentMode.CENTER
        alignItems = FlexComponent.Alignment.CENTER
        add(
            H1("Навыки и умения приезжих ребят"),
            Span(Text("Для того, чтобы добавить запись в этот список перейдите по "), Anchor("/form", "ссылке"))
        )
    }

    private val citiesSelector = MultiSelectComboBox<String>("Город").apply {
        setItems(sheetService.getCities())
        addValueChangeListener(::onSelect)
    }

    private val search = HorizontalLayout().apply {
        justifyContentMode = FlexComponent.JustifyContentMode.CENTER
        alignItems = FlexComponent.Alignment.CENTER

        add(citiesSelector)
    }

    private val grid = Grid(SheetRow::class.java, false).apply {
        addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT)
        addColumn(SheetRow::time).setHeader("Дата добавления")
        addColumn(SheetRow::name).setHeader("Имя")
        addColumn(SheetRow::city).setHeader("Город")
        addColumn(SheetRow::good).setHeader("Навык")
        addColumn(SheetRow::contact).setHeader("Контакты")
        citiesSelector.value.map { it.toString() }
        setItems {
            sheetService.getByCities(
                it,
                citiesSelector.value.map { value -> value.toString() }
            ).stream()
        }
    }

    init {
        add(title, search, grid)
    }

    private fun onSelect(event: AbstractField.ComponentValueChangeEvent<MultiSelectComboBox<String>, MutableSet<String>>?) =
        doOnSelect(event!!.value)

    private fun doOnSelect(cities: Collection<String>): GridLazyDataView<SheetRow>? =
        grid.setItems {
            sheetService.getByCities(
                it,
                cities
            ).stream()
        }
}
