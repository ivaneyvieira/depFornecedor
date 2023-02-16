package br.com.astrosoft.devolucao.view.entrada


import br.com.astrosoft.devolucao.model.beans.FiltroNFEntradaFrete
import br.com.astrosoft.devolucao.model.beans.NfEntradaFrete
import br.com.astrosoft.devolucao.model.beans.NfPrecEntrada
import br.com.astrosoft.devolucao.view.entrada.columms.NFECteColumns.notaAdValore
import br.com.astrosoft.devolucao.view.entrada.columms.NFECteColumns.notaAliquota
import br.com.astrosoft.devolucao.view.entrada.columms.NFECteColumns.notaCte
import br.com.astrosoft.devolucao.view.entrada.columms.NFECteColumns.notaCub
import br.com.astrosoft.devolucao.view.entrada.columms.NFECteColumns.notaEmissao
import br.com.astrosoft.devolucao.view.entrada.columms.NFECteColumns.notaEmissaoCte
import br.com.astrosoft.devolucao.view.entrada.columms.NFECteColumns.notaEntrada
import br.com.astrosoft.devolucao.view.entrada.columms.NFECteColumns.notaEntradaCte
import br.com.astrosoft.devolucao.view.entrada.columms.NFECteColumns.notaFPeso
import br.com.astrosoft.devolucao.view.entrada.columms.NFECteColumns.notaForNf
import br.com.astrosoft.devolucao.view.entrada.columms.NFECteColumns.notaGris
import br.com.astrosoft.devolucao.view.entrada.columms.NFECteColumns.notaICMS
import br.com.astrosoft.devolucao.view.entrada.columms.NFECteColumns.notaLoja
import br.com.astrosoft.devolucao.view.entrada.columms.NFECteColumns.notaNF
import br.com.astrosoft.devolucao.view.entrada.columms.NFECteColumns.notaNI
import br.com.astrosoft.devolucao.view.entrada.columms.NFECteColumns.notaOutros
import br.com.astrosoft.devolucao.view.entrada.columms.NFECteColumns.notaPBruto
import br.com.astrosoft.devolucao.view.entrada.columms.NFECteColumns.notaPesoFat
import br.com.astrosoft.devolucao.view.entrada.columms.NFECteColumns.notaTaxa
import br.com.astrosoft.devolucao.view.entrada.columms.NFECteColumns.notaTotalFrete
import br.com.astrosoft.devolucao.view.entrada.columms.NFECteColumns.notaTotalNf
import br.com.astrosoft.devolucao.view.entrada.columms.NFECteColumns.notaTotalPrd
import br.com.astrosoft.devolucao.view.entrada.columms.NFECteColumns.notaTransp
import br.com.astrosoft.devolucao.view.entrada.columms.NFECteColumns.notaTranspName
import br.com.astrosoft.devolucao.view.entrada.columms.NFECteColumns.notaValorFrete
import br.com.astrosoft.devolucao.view.entrada.columms.marcaDiferenca
import br.com.astrosoft.devolucao.viewmodel.entrada.TabCteViewModel
import br.com.astrosoft.framework.util.format
import br.com.astrosoft.framework.view.*
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridVariant
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.data.provider.ListDataProvider

@CssImport("./styles/gridTotal.css", themeFor = "vaadin-grid")
class DlgRelatorioCte(val viewModel: TabCteViewModel, val filtro: FiltroNFEntradaFrete) {
  private lateinit var gridNota: Grid<NfEntradaFrete>
  private val dataProviderGrid = ListDataProvider<NfEntradaFrete>(mutableListOf())

  fun show() {
    val form = SubWindowForm("Relatório", toolBar = {


    }) {
      gridNota = createGrid(dataProviderGrid)
      val list = viewModel.findNotas()
      gridNota.setItems(list)
      HorizontalLayout().apply {
        setSizeFull()
        addAndExpand(gridNota)
      }
    }
    form.open()
  }

  private fun createGrid(dataProvider: ListDataProvider<NfEntradaFrete>): Grid<NfEntradaFrete> {
    return Grid(NfEntradaFrete::class.java, false).apply {
      setSizeFull()
      addThemeVariants(GridVariant.LUMO_COMPACT)
      isMultiSort = false
      setSelectionMode(Grid.SelectionMode.MULTI)
      this.dataProvider = dataProvider

      notaLoja()
      notaNI()
      notaNF()
      notaEmissao()
      notaEntrada()
      notaForNf()
      notaTotalPrd()
      notaTotalNf()
      notaTransp()
      notaTranspName()
      notaCte()
      notaEmissaoCte()
      notaEntradaCte()
      notaValorFrete().marcaDiferenca { totalFrete.format() != valorCte.format() }
      notaPBruto()
      notaPesoFat()
      notaCub()
      notaFPeso()
      notaAdValore()
      notaGris()
      notaTaxa()
      notaOutros()
      notaAliquota()
      notaICMS()
      notaTotalFrete().marcaDiferenca { totalFrete.format() != valorCte.format() }
    }
  }
}

fun Grid.Column<NfEntradaFrete>.marcaDiferenca(predicado: NfEntradaFrete.() -> Boolean) {
  this.setClassNameGenerator {
    if (it.predicado()) "marcaDiferenca" else null
  }
}