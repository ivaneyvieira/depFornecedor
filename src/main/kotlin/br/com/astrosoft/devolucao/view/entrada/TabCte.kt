package br.com.astrosoft.devolucao.view.entrada

import br.com.astrosoft.devolucao.model.beans.FiltroNFEntradaFrete
import br.com.astrosoft.devolucao.model.beans.Loja
import br.com.astrosoft.devolucao.model.beans.UserSaci
import br.com.astrosoft.devolucao.viewmodel.entrada.ITabCteViewModel
import br.com.astrosoft.devolucao.viewmodel.entrada.TabCteViewModel
import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.view.ITabPanel
import br.com.astrosoft.framework.view.localePtBr
import com.github.mvysny.karibudsl.v10.*
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.IntegerField
import com.vaadin.flow.component.textfield.TextField
import java.time.LocalDate

@CssImport("./styles/gridTotal.css", themeFor = "vaadin-grid")
class TabCte(val viewModel: TabCteViewModel) : ITabCteViewModel, ITabPanel {
  private lateinit var edtLoja: ComboBox<Loja>
  private lateinit var edtDataF: DatePicker
  private lateinit var edtDataI: DatePicker
  private lateinit var edtFornecedorNota: IntegerField
  private lateinit var edtNi: IntegerField
  private lateinit var edtNota: TextField

  private val lojas: List<Loja> = viewModel.findLojas() + Loja(0, "Todas", "")

  override fun setFiltro(filtro: FiltroNFEntradaFrete) {
    edtLoja.value = lojas.firstOrNull { it.no == filtro.loja }
    edtDataI.value = filtro.di
    edtDataF.value = filtro.df
    edtFornecedorNota.value = if (filtro.vend == 0) null else filtro.vend
    edtNi.value = if (filtro.ni == 0) null else filtro.ni
    edtNota.value = filtro.nfno
  }

  override fun getFiltro(): FiltroNFEntradaFrete {
    return FiltroNFEntradaFrete(
      loja = edtLoja.value?.no ?: 0,
      di = edtDataI.value ?: LocalDate.now(),
      df = edtDataF.value ?: LocalDate.now(),
      ni = edtNi.value ?: 0,
      nfno = edtNota.value ?: "",
      vend = edtFornecedorNota.value ?: 0,
                               )
  }

  override fun openRelatorio() {
    DlgRelatorioCte(viewModel, getFiltro()).show()
  }

  override val createComponent = VerticalLayout().apply {
    horizontalLayout {
      edtLoja = comboBox("Loja") {
        setItems(lojas.sortedBy { it.no })
        setItemLabelGenerator {
          if (it == null) "Todas as lojas"
          else "${it.no} - ${it.sname}"
        }
        isAllowCustomValue = false
        isClearButtonVisible = true
      }
      edtDataI = datePicker("Data Inicial") {
        localePtBr()
      }
      edtDataF = datePicker("Data Final") {
        localePtBr()
      }
    }
    horizontalLayout {
      edtFornecedorNota = integerField("Fornecedor Nota")
      edtNi = integerField("NI")
      edtNota = textField("Nota Fiscal")
    }
    br()
    button("Relatório") {
      icon = VaadinIcon.RECORDS.create()
      onLeftClick {
        viewModel.openDlgRelatorio()
      }
    }
    setFiltro(getFiltro())
  }

  override fun isAuthorized(user: IUser): Boolean {
    val username = user as? UserSaci
    return username?.entradaCte == true
  }

  override val label: String
    get() = "CTe"

  override fun updateComponent() {
  }
}
