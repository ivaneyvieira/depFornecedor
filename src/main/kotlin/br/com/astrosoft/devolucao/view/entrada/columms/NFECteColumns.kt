package br.com.astrosoft.devolucao.view.entrada.columms

import br.com.astrosoft.devolucao.model.beans.NfEntradaFrete
import br.com.astrosoft.devolucao.model.beans.NfPrecEntrada
import br.com.astrosoft.framework.view.addColumnDouble
import br.com.astrosoft.framework.view.addColumnInt
import br.com.astrosoft.framework.view.addColumnLocalDate
import br.com.astrosoft.framework.view.addColumnString
import com.vaadin.flow.component.grid.Grid

object NFECteColumns {
  fun Grid<NfEntradaFrete>.notaLoja() = addColumnInt(NfEntradaFrete::loja) {
    this.setHeader("Loja")
    this.isResizable = true
  }
  fun Grid<NfEntradaFrete>.notaNI() = addColumnInt(NfEntradaFrete::ni) {
    this.setHeader("NI")
    this.isResizable = true
  }
  fun Grid<NfEntradaFrete>.notaNF() = addColumnInt(NfEntradaFrete::nf) {
    this.setHeader("NF")
    this.isResizable = true
  }
  fun Grid<NfEntradaFrete>.notaEmissao() = addColumnLocalDate(NfEntradaFrete::emissao) {
    this.setHeader("Emissão")
    this.isResizable = true
  }
  fun Grid<NfEntradaFrete>.notaEntrada() = addColumnLocalDate(NfEntradaFrete::entrada) {
    this.setHeader("Entrada")
    this.isResizable = true
  }
  fun Grid<NfEntradaFrete>.notaForNf() = addColumnInt(NfEntradaFrete::vendno) {
    this.setHeader("For NF")
    this.isResizable = true
  }
  fun Grid<NfEntradaFrete>.notaTotalPrd() = addColumnDouble(NfEntradaFrete::totalPrd) {
    this.setHeader("R$ Prd")
    this.isResizable = true
  }
  fun Grid<NfEntradaFrete>.notaTotalNf() = addColumnDouble(NfEntradaFrete::valorNF) {
    this.setHeader("R$ NF")
    this.isResizable = true
  }
  fun Grid<NfEntradaFrete>.notaTransp() = addColumnInt(NfEntradaFrete::carrno) {
    this.setHeader("Transp")
    this.isResizable = true
  }
  fun Grid<NfEntradaFrete>.notaTranspName() = addColumnString(NfEntradaFrete::carrName) {
    this.setHeader("Nome")
    this.isResizable = true
  }
  fun Grid<NfEntradaFrete>.notaCte() = addColumnInt(NfEntradaFrete::cte) {
    this.setHeader("CTe")
    this.isResizable = true
  }
  fun Grid<NfEntradaFrete>.notaEmissaoCte() = addColumnLocalDate(NfEntradaFrete::emissaoCte) {
    this.setHeader("Emissão")
    this.isResizable = true
  }
  fun Grid<NfEntradaFrete>.notaEntradaCte() = addColumnLocalDate(NfEntradaFrete::entradaCte) {
    this.setHeader("Entrada")
    this.isResizable = true
  }
  fun Grid<NfEntradaFrete>.notaValorFrete() = addColumnDouble(NfEntradaFrete::valorCte) {
    this.setHeader("R$ Frete")
    this.isResizable = true
  }
  fun Grid<NfEntradaFrete>.notaPBruto() = addColumnInt(NfEntradaFrete::pesoBruto) {
    this.setHeader("P Bruto")
    this.isResizable = true
  }
  fun Grid<NfEntradaFrete>.notaPesoFat() = addColumnDouble(NfEntradaFrete::pesoFat) {
    this.setHeader("Peso Fat")
    this.isResizable = true
  }
  fun Grid<NfEntradaFrete>.notaCub() = addColumnDouble(NfEntradaFrete::cub) {
    this.setHeader("Cub")
    this.isResizable = true
  }
  fun Grid<NfEntradaFrete>.notaFPeso() = addColumnDouble(NfEntradaFrete::fPeso) {
    this.setHeader("F Peso")
    this.isResizable = true
  }
  fun Grid<NfEntradaFrete>.notaAdValore() = addColumnDouble(NfEntradaFrete::adValore) {
    this.setHeader("Advalore")
    this.isResizable = true
  }
  fun Grid<NfEntradaFrete>.notaGris() = addColumnDouble(NfEntradaFrete::gris) {
    this.setHeader("Gris")
    this.isResizable = true
  }
  fun Grid<NfEntradaFrete>.notaTaxa() = addColumnDouble(NfEntradaFrete::taxa) {
    this.setHeader("Taxa")
    this.isResizable = true
  }
  fun Grid<NfEntradaFrete>.notaOutros() = addColumnDouble(NfEntradaFrete::outro) {
    this.setHeader("Outros")
    this.isResizable = true
  }
  fun Grid<NfEntradaFrete>.notaAliquota() = addColumnDouble(NfEntradaFrete::aliquota) {
    this.setHeader("Aliquota")
    this.isResizable = true
  }
  fun Grid<NfEntradaFrete>.notaICMS() = addColumnDouble(NfEntradaFrete::icms) {
    this.setHeader("ICMS")
    this.isResizable = true
  }
  fun Grid<NfEntradaFrete>.notaTotalFrete() = addColumnDouble(NfEntradaFrete::totalFrete) {
    this.setHeader("Total Frete")
    this.isResizable = true
  }
}
