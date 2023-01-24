package br.com.astrosoft.devolucao.view.demanda

import br.com.astrosoft.devolucao.model.beans.AgendaDemanda
import br.com.astrosoft.devolucao.model.beans.UserSaci
import br.com.astrosoft.devolucao.view.demanda.columns.DemandaColumns.colDemandaConteudo
import br.com.astrosoft.devolucao.view.demanda.columns.DemandaColumns.colDemandaData
import br.com.astrosoft.devolucao.view.demanda.columns.DemandaColumns.colDemandaTitulo
import br.com.astrosoft.devolucao.viewmodel.demanda.ITabAgendaDemanda
import br.com.astrosoft.devolucao.viewmodel.demanda.TabAgendaDemandaViewModel
import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.TabPanelGrid
import br.com.astrosoft.framework.view.addColumnButton
import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.onLeftClick
import com.github.mvysny.karibudsl.v10.textField
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.Grid.SelectionMode
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.value.ValueChangeMode
import org.claspina.confirmdialog.ConfirmDialog
import java.time.LocalDate

class TabAgendaDemanda(val viewModel: TabAgendaDemandaViewModel) : TabPanelGrid<AgendaDemanda>(AgendaDemanda::class),
        ITabAgendaDemanda {
  private lateinit var edtFiltro: TextField
  override fun HorizontalLayout.toolBarConfig() {
    button("Adicionar") {
      icon = VaadinIcon.PLUS.create()
      onLeftClick {
        viewModel.adicionar()
      }
    }

    edtFiltro = textField("Filtro") {
      this.placeholder = "Ainda não está funcionando"
      width = "400px"
      valueChangeMode = ValueChangeMode.LAZY
      this.valueChangeTimeout = 2000
      addValueChangeListener {
        viewModel.updateView()
      }
    }
  }

  override fun Grid<AgendaDemanda>.gridPanel() {
    setSelectionMode(SelectionMode.MULTI)

    addColumnButton(iconButton = VaadinIcon.EDIT, tooltip = "Editar", header = "Editar") { demanda ->
      viewModel.editar(demanda)
    }

    addColumnButton(iconButton = VaadinIcon.TRASH, tooltip = "Remover", header = "Remover") { demanda ->
      viewModel.remover(demanda)
    }

    addColumnButton(iconButton = VaadinIcon.FILE, tooltip = "Anexo", header = "Anexo") { demanda ->
      viewModel.anexo(demanda)
    }

    colDemandaData()
    colDemandaTitulo()
    colDemandaConteudo()
  }

  private fun showForm(demanda: AgendaDemanda?,
                       title: String,
                       isReadOnly: Boolean,
                       exec: (demanda: AgendaDemanda?) -> Unit) {
    val form = FormAgendaDemanda(demanda, isReadOnly)
    ConfirmDialog.create().withCaption(title).withMessage(form).withOkButton({
                                                                               val bean = form.bean
                                                                               exec(bean)
                                                                             }).withCancelButton().open()
  }

  override fun showAnexoForm(demanda: AgendaDemanda, execInsert: (demanda: AgendaDemanda?) -> Unit) {
    TODO("Not yet implemented")
  }

  override fun showInsertForm(execInsert: (demanda: AgendaDemanda?) -> Unit) {
    val bean = AgendaDemanda(id = 0, date = LocalDate.now(), titulo = "", conteudo = "")
    showForm(demanda = bean, title = "Adiciona", isReadOnly = false, exec = execInsert)
  }

  override fun showUpdateForm(demanda: AgendaDemanda, execUpdate: (demanda: AgendaDemanda?) -> Unit) {
    showForm(demanda = demanda, title = "Edita", isReadOnly = false, exec = execUpdate)
  }

  override fun showDeleteForm(demanda: AgendaDemanda, execDelete: (demanda: AgendaDemanda?) -> Unit) {
    showForm(demanda = demanda, title = "Remove", isReadOnly = true, exec = execDelete)
  }

  override fun isAuthorized(user: IUser): Boolean {
    val username = user as? UserSaci
    return username?.demandaAgenda == true
  }

  override val label: String
    get() = "Agenda"

  override fun updateComponent() {
    viewModel.updateView()
  }
}

