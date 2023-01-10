package br.com.astrosoft.devolucao.view.entrada


import br.com.astrosoft.devolucao.model.beans.FiltroRelatorio
import br.com.astrosoft.devolucao.model.beans.NfPrecEntrada
import br.com.astrosoft.devolucao.view.entrada.columms.UltimaNotaEntradaColumns.notaData
import br.com.astrosoft.devolucao.view.entrada.columms.UltimaNotaEntradaColumns.notaDataEmissao
import br.com.astrosoft.devolucao.view.entrada.columms.UltimaNotaEntradaColumns.notaDescricao
import br.com.astrosoft.devolucao.view.entrada.columms.UltimaNotaEntradaColumns.notaFornCad
import br.com.astrosoft.devolucao.view.entrada.columms.UltimaNotaEntradaColumns.notaFornNota
import br.com.astrosoft.devolucao.view.entrada.columms.UltimaNotaEntradaColumns.notaGrade
import br.com.astrosoft.devolucao.view.entrada.columms.UltimaNotaEntradaColumns.notaLoja
import br.com.astrosoft.devolucao.view.entrada.columms.UltimaNotaEntradaColumns.notaNfe
import br.com.astrosoft.devolucao.view.entrada.columms.UltimaNotaEntradaColumns.notaNi
import br.com.astrosoft.devolucao.view.entrada.columms.UltimaNotaEntradaColumns.notaPedidoCompra
import br.com.astrosoft.devolucao.view.entrada.columms.UltimaNotaEntradaColumns.notaPrecoDif
import br.com.astrosoft.devolucao.view.entrada.columms.UltimaNotaEntradaColumns.notaPrecoPercen
import br.com.astrosoft.devolucao.view.entrada.columms.UltimaNotaEntradaColumns.notaPrecon
import br.com.astrosoft.devolucao.view.entrada.columms.UltimaNotaEntradaColumns.notaPrecop
import br.com.astrosoft.devolucao.view.entrada.columms.UltimaNotaEntradaColumns.notaPrecopc
import br.com.astrosoft.devolucao.view.entrada.columms.UltimaNotaEntradaColumns.notaProd
import br.com.astrosoft.devolucao.view.entrada.columms.comboDiferencaNum
import br.com.astrosoft.devolucao.view.entrada.columms.marcaDiferenca
import br.com.astrosoft.devolucao.viewmodel.entrada.TabPrecoPreRecViewModel
import br.com.astrosoft.framework.view.SubWindowForm
import br.com.astrosoft.framework.view.buttonPlanilha
import br.com.astrosoft.framework.view.selectedItemsSort
import com.flowingcode.vaadin.addons.fontawesome.FontAwesome
import com.github.mvysny.karibudsl.v10.button
import com.github.mvysny.karibudsl.v10.onLeftClick
import com.github.mvysny.karibudsl.v10.textField
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridVariant
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.data.provider.ListDataProvider
import com.vaadin.flow.data.value.ValueChangeMode

@CssImport("./styles/gridTotal.css", themeFor = "vaadin-grid")
class DlgRelatorioPrecoPreRec(val viewModel: TabPrecoPreRecViewModel, val filtro: FiltroRelatorio) {
  private lateinit var gridNota: Grid<NfPrecEntrada>
  private val dataProviderGrid = ListDataProvider<NfPrecEntrada>(mutableListOf())

  fun show() {
    val form = SubWindowForm("Relatório", toolBar = {
      this.button("Relatório") {
        icon = VaadinIcon.PRINT.create()
        onLeftClick {
          viewModel.imprimeRelatorio(gridNota.selectedItemsSort())
        }
      }
      this.button("Relatório Resumo") {
        icon = VaadinIcon.PRINT.create()
        onLeftClick {
          viewModel.imprimeRelatorioResumo(gridNota.selectedItemsSort())
        }
      }
      buttonPlanilha("Planilha", FontAwesome.Solid.FILE_EXCEL.create(), "planilhaNfPrecificacao") {
        viewModel.geraPlanilha(gridNota.selectedItemsSort())
      }
      this.comboDiferencaNum("Preço") {
        value = filtro.preco

        this.addValueChangeListener {
          filtro.preco = it.value
          val list = viewModel.findNotas(filtro)
          gridNota.setItems(list)
        }
      }
      this.textField("Pesquisa") {
        value = filtro.pesquisa
        this.valueChangeMode = ValueChangeMode.LAZY
        this.valueChangeTimeout = 2000

        this.addValueChangeListener {
          filtro.pesquisa = it.value ?: ""
          val list = viewModel.findNotas(filtro)
          gridNota.setItems(list)
        }
      }
    }) {
      gridNota = createGrid(dataProviderGrid)
      val list = viewModel.findNotas(filtro)
      gridNota.setItems(list)
      HorizontalLayout().apply {
        setSizeFull()
        addAndExpand(gridNota)
      }
    }
    form.open()
  }

  private fun createGrid(dataProvider: ListDataProvider<NfPrecEntrada>): Grid<NfPrecEntrada> {
    return Grid(NfPrecEntrada::class.java, false).apply {
      setSizeFull()
      addThemeVariants(GridVariant.LUMO_COMPACT)
      isMultiSort = false
      setSelectionMode(Grid.SelectionMode.MULTI)
      this.dataProvider = dataProvider

      notaLoja()
      notaPedidoCompra()
      notaNi().marcaDiferenca { difGeral(true) }
      notaData()
      notaDataEmissao()
      notaNfe().marcaDiferenca { difGeral(true) }
      notaFornCad()
      notaFornNota()
      notaProd().marcaDiferenca { difGeral(true) }
      notaDescricao()
      notaGrade()
      notaPrecon().marcaDiferenca { precoDif == "N" }
      notaPrecop().marcaDiferenca { precoDif == "N" }
      notaPrecopc().marcaDiferenca { precoDif == "N" }
      notaPrecoDif().marcaDiferenca { precoDif == "N" }
      notaPrecoPercen().marcaDiferenca { precoDif == "N" }
    }
  }
}