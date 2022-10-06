package com.github.simohin.board.ui

import com.github.simohin.board.dto.SheetRow
import com.github.simohin.board.service.SheetService
import com.vaadin.flow.component.Text
import com.vaadin.flow.component.combobox.MultiSelectComboBox
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridVariant
import com.vaadin.flow.component.grid.dataview.GridListDataView
import com.vaadin.flow.component.html.Anchor
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.provider.ListDataProvider
import com.vaadin.flow.data.value.ValueChangeMode
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

    private val citiesSelector = MultiSelectComboBox<String>().apply {
        placeholder = "Город"
        setItems(sheetService.getCities())
        addValueChangeListener {
            gridListDataView().refreshAll()
        }
    }

    private val searchField = TextField().apply {
        setWidthFull()
        placeholder = "Поиск"
        prefixComponent = Icon(VaadinIcon.SEARCH)
        valueChangeMode = ValueChangeMode.EAGER
        valueChangeTimeout = 500
        addValueChangeListener { gridListDataView().refreshAll() }
    }

    private val search = HorizontalLayout().apply {
        setWidthFull()
        justifyContentMode = FlexComponent.JustifyContentMode.CENTER
        alignItems = FlexComponent.Alignment.CENTER

        add(citiesSelector, searchField)
    }

    private val grid = Grid(SheetRow::class.java, false).apply {
        addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT)
        addColumn(SheetRow::time).setHeader("Дата добавления")
        addColumn(SheetRow::name).setHeader("Имя")
        addColumn(SheetRow::city).setHeader("Город")
        addColumn(SheetRow::good).setHeader("Навык")
        addColumn(SheetRow::contact).setHeader("Контакты")
        setItems(ListDataProvider(sheetService.getAll()))
        listDataView.apply {
            addFilter(::cityFilter)
            addFilter(::searchFilter)
        }
    }

    init {
        add(title, search, grid)
    }

    private fun gridListDataView(): GridListDataView<SheetRow> = grid.listDataView

    private fun cityFilter(sheetRow: SheetRow): Boolean =
        with(citiesSelector.value.map { value -> value.toString() }.toSet()) {
            if (this.isEmpty()) true
            else this.contains(sheetRow.city)
        }

    private fun searchFilter(sheetRow: SheetRow): Boolean = with(searchField.value) {
        return if (this.isNullOrBlank()) true
        else sheetRow.searchFields().any { it.contains(this, false) }
    }
}
